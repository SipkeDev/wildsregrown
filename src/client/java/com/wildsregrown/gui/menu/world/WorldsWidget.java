package com.wildsregrown.gui.menu.world;

import com.sipke.api.grid.GridLoader;
import com.sipke.api.grid.WorldGrid;
import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.KeyBindings;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.gui.menu.builder.WorldTypeScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.world.*;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.input.KeyInput;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.client.util.InputUtil;
import net.minecraft.nbt.NbtCrashException;
import net.minecraft.nbt.NbtException;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.path.SymlinkEntry;
import net.minecraft.util.path.SymlinkValidationException;
import net.minecraft.world.level.storage.LevelStorage;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelSummary;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Optional;

import static com.wildsregrown.WildsRegrown.LOGGER;

public class WorldsWidget extends AlwaysSelectedEntryListWidget<WorldsWidget.Entry> {

    public static final DateTimeFormatter DATE_FORMAT;
    static final Identifier OVERLAY;
    static final Identifier ERROR_HIGHLIGHTED_TEXTURE;
    static final Identifier ERROR_TEXTURE;
    static final Identifier MARKED_JOIN_HIGHLIGHTED_TEXTURE;
    static final Identifier MARKED_JOIN_TEXTURE;
    static final Identifier WARNING_HIGHLIGHTED_TEXTURE;
    static final Identifier WARNING_TEXTURE;
    static final Identifier JOIN_HIGHLIGHTED_TEXTURE;
    static final Identifier JOIN_TEXTURE;

    private List<LevelSummary> levels;
    private final SelectWorld parent;
    private final MinecraftClient client;
    private final int rowWidth;

    public WorldsWidget(SelectWorld parent, MinecraftClient client) {
        super(client, parent.width-160, parent.height, 0 , 68);
        this.parent = parent;
        this.rowWidth = parent.width-160;
        this.client = client;
        load();
    }

    private void load() {
        this.levels = this.loadLevels();
        for (LevelSummary summary : levels){
            this.addEntry(new LevelEntry(this, summary));
        }
    }

    /**
     * Renders
     */
    @Override
    protected void drawMenuListBackground(DrawContext context) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, OVERLAY, this.getX(), this.getY(), (float)this.getRight(), (float)(this.getBottom() + (int)this.getScrollY()), this.getWidth(), this.getHeight(), 32, 32);
    }

    @Override
    protected int getScrollbarX() {
        return rowWidth-6;
    }

    @Override
    public int getRowLeft() {
        return 3;
    }

    @Override
    public int getRowWidth() {
        return rowWidth;
    }

    @Override
    protected void drawSelectionHighlight(DrawContext context, WorldsWidget.Entry entry, int color) {
        int i = entry.getX();
        int j = entry.getY();
        int k = i + entry.getWidth();
        int l = j + entry.getHeight();
        context.fill(i, j, k, l, color);
        context.fill(i + 1, j + 1, k - 1, l - 1, -16777216);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (KeyBindings.hasKeyDown(input)) {
            Optional<LevelEntry> optional = this.getSelectedAsOptional();
            if (optional.isPresent()) {
                if ((optional.get()).isLevelSelectable()) {
                    this.client.getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    (optional.get()).play();
                }
                return true;
            }
        }
        return super.keyPressed(input);
    }

    public Optional<LevelEntry> getSelectedAsOptional() {
        Entry selectedEntry = this.getSelectedOrNull();
        if (selectedEntry instanceof LevelEntry levelEntry) {
            return Optional.of(levelEntry);
        } else {
            return Optional.empty();
        }
    }

    private List<LevelSummary> loadLevels() {
        LevelStorage.LevelList levelList;
        try {
            levelList = this.client.getLevelStorage().getLevelList();
        } catch (LevelStorageException levelStorageException) {
            LOGGER.error("Couldn't load level list", levelStorageException);
            this.showUnableToLoadScreen(levelStorageException.getMessageText());
            return List.of();
        }

        if (levelList.isEmpty()) {
            client.setScreen(new WorldTypeScreen(new WorldBuilder(), new MainMenu()));
            return List.of();
        } else {
            return this.client.getLevelStorage().loadSummaries(levelList).join();
        }
    }

    private void showUnableToLoadScreen(Text message) {
        this.client.setScreen(new FatalErrorScreen(Text.translatable("selectWorld.unable_to_load"), message));
    }

    public void setSelected(@Nullable WorldsWidget.Entry entry) {
        super.setSelected(entry);
        LevelSummary level = null;
        if (entry instanceof LevelEntry l) {
            level = l.level;
        }
        this.parent.worldSelected(level);
    }

    static {
        DATE_FORMAT = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withZone(ZoneId.systemDefault());
        OVERLAY = Identifier.ofVanilla("textures/gui/menu_list_background.png");
        ERROR_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("world_list/error_highlighted");
        ERROR_TEXTURE = Identifier.ofVanilla("world_list/error");
        MARKED_JOIN_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("world_list/marked_join_highlighted");
        MARKED_JOIN_TEXTURE = Identifier.ofVanilla("world_list/marked_join");
        WARNING_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("world_list/warning_highlighted");
        WARNING_TEXTURE = Identifier.ofVanilla("world_list/warning");
        JOIN_HIGHLIGHTED_TEXTURE = Identifier.ofVanilla("world_list/join_highlighted");
        JOIN_TEXTURE = Identifier.ofVanilla("world_list/join");
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends AlwaysSelectedEntryListWidget.Entry<Entry> implements AutoCloseable {
        public Entry() {}
        public void close() {}
    }

    @Environment(EnvType.CLIENT)
    public final class LevelEntry extends Entry {

        private final MinecraftClient client;
        private final Screen parent;
        final LevelSummary level;
        private final WorldIcon icon;
        @Nullable
        private Path iconPath;
        private long time;

        public LevelEntry(final WorldsWidget levelList, final LevelSummary level) {
            this.client = levelList.client;
            this.parent = levelList.parent;
            this.level = level;
            this.icon = WorldIcon.forWorld(this.client.getTextureManager(), level.getName());
            this.iconPath = level.getIconPath();
            this.validateIconPath();
            this.loadIcon();
        }

        private void validateIconPath() {
            if (this.iconPath != null) {
                try {
                    BasicFileAttributes basicFileAttributes = Files.readAttributes(this.iconPath, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
                    if (basicFileAttributes.isSymbolicLink()) {
                        List<SymlinkEntry> list = this.client.getSymlinkFinder().validate(this.iconPath);
                        if (!list.isEmpty()) {
                            LOGGER.warn("{}", SymlinkValidationException.getMessage(this.iconPath, list));
                            this.iconPath = null;
                        } else {
                            basicFileAttributes = Files.readAttributes(this.iconPath, BasicFileAttributes.class);
                        }
                    }
                    if (!basicFileAttributes.isRegularFile()) {
                        this.iconPath = null;
                    }
                } catch (NoSuchFileException var3) {
                    this.iconPath = null;
                } catch (IOException iOException) {
                    LOGGER.error("could not validate symlink", iOException);
                    this.iconPath = null;
                }

            }
        }


        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {

            String displayName = this.level.getDisplayName();
            long lastActive = this.level.getLastPlayed();

            if (lastActive != -1L) {
                displayName = displayName + " (" + DATE_FORMAT.format(Instant.ofEpochMilli(lastActive)) + ")";
            }

            if (StringUtils.isEmpty(displayName)) {
                displayName = I18n.translate("selectWorld.world");
            }

            Text text = this.level.getDetails();

            int x = this.getContentX(), y = this.getContentY();
            int size = 64, dx = x + size + 3, dy = y, my = 12;

            context.drawTexture(RenderPipelines.GUI_TEXTURED, this.icon.getTextureId(), x, y, 0.0F, 0.0F, size, size, size, size);

            int deltaCoord = mouseX - x;
            boolean isHover = deltaCoord < size;

            context.drawTextWithShadow(this.client.textRenderer, displayName, dx, dy+=my, isHover ? Color.decode("#aec6cf").getRGB() : Color.decode("#cfcfc4").getRGB());
            context.drawTextWithShadow(this.client.textRenderer, text, dx, dy+=my, -8355712);

            //Render Hover
            if (this.client.options.getTouchscreen().getValue() || hovered) {
                context.fill(0, y, getRight(), y + size, -1601138544);
                context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, isHover ? JOIN_HIGHLIGHTED_TEXTURE : JOIN_TEXTURE, x, y, size, size);
            }
        }

        @Override
        public Text getNarration() {
            return Text.of("");
        }

        public boolean mouseClicked(Click click, boolean doubled) {
            if (!this.level.isSelectable()) {
                return true;
            } else {
                WorldsWidget.this.setSelected(this);
                if (!(click.x() - WorldsWidget.this.getRowLeft() <= 64.0) && Util.getMeasuringTimeMs() - this.time >= 250L) {
                    this.time = Util.getMeasuringTimeMs();
                    return super.mouseClicked(click, doubled);
                } else {
                    if (this.isLevelSelectable()) {
                        this.client.getSoundManager().play(PositionedSoundInstance.ui(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                        this.play();
                    }
                    return true;
                }
            }
        }

        public boolean isLevelSelectable() {
            return this.level.isSelectable();
        }

        public void play() {
            if (this.level.isSelectable()) {
                if (this.level instanceof LevelSummary.SymlinkLevelSummary) {
                    this.client.setScreen(SymlinkWarningScreen.world(() -> this.client.setScreen(this.parent)));
                } else {
                    this.client.createIntegratedServerLoader().start(this.level.getName(),
                            () -> { //Callback / Cancel methode
                        WorldsWidget.this.load();
                        this.client.setScreen(this.parent);
                    });
                }
            }
        }

        public void deleteIfConfirmed() {
            this.client.setScreen(new ConfirmScreen((confirmed) -> {
                if (confirmed) {
                    this.client.setScreen(new ProgressScreen(true));
                    this.delete();
                }

                this.client.setScreen(this.parent);
            }, Text.translatable("selectWorld.deleteQuestion"), Text.translatable("selectWorld.deleteWarning", new Object[]{this.level.getDisplayName()}), Text.translatable("selectWorld.deleteButton"), ScreenTexts.CANCEL));
        }

        public void delete() {

            LevelStorage levelStorage = this.client.getLevelStorage();
            String string = this.level.getName();

            try (LevelStorage.Session session = levelStorage.createSessionWithoutSymlinkCheck(string)) {
                session.deleteSessionLock();
            } catch (IOException iOException) {
                SystemToast.addWorldDeleteFailureToast(this.client, string);
                LOGGER.error("Failed to delete world {}", string, iOException);
            }

            WorldsWidget.this.load();
        }

        public void edit() {
            this.openReadingWorldScreen();
            String string = this.level.getName();

            LevelStorage.Session session;
            try {
                session = this.client.getLevelStorage().createSession(string);
            } catch (IOException iOException) {
                SystemToast.addWorldAccessFailureToast(this.client, string);
                LOGGER.error("Failed to access level {}", string, iOException);
                WorldsWidget.this.load();
                return;
            } catch (SymlinkValidationException symlinkValidationException) {
                LOGGER.warn("{}", symlinkValidationException.getMessage());
                this.client.setScreen(SymlinkWarningScreen.world(() -> this.client.setScreen(this.parent)));
                return;
            }

            EditWorldScreen editWorldScreen;
            try {
                editWorldScreen = EditWorldScreen.create(this.client, session, (edited) -> {
                    session.tryClose();
                    if (edited) {
                        WorldsWidget.this.load();
                    }

                    this.client.setScreen(this.parent);
                });
            } catch (NbtException | NbtCrashException | IOException exception) {
                session.tryClose();
                SystemToast.addWorldAccessFailureToast(this.client, string);
                LOGGER.error("Failed to load world data {}", string, exception);
                WorldsWidget.this.load();
                return;
            }

            this.client.setScreen(editWorldScreen);
        }

        public void preview() {

            this.openReadingWorldScreen();

            String string = this.level.getName();
            LevelStorage.Session session;
            try {
                session = this.client.getLevelStorage().createSession(string);
            } catch (IOException iOException) {
                SystemToast.addWorldAccessFailureToast(this.client, string);
                LOGGER.error("Failed to access level {}", string, iOException);
                WorldsWidget.this.load();
                return;
            } catch (SymlinkValidationException symlinkValidationException) {
                LOGGER.warn("{}", symlinkValidationException.getMessage());
                this.client.setScreen(SymlinkWarningScreen.world(() -> this.client.setScreen(this.parent)));
                return;
            }

            WorldGrid grid = GridLoader.read(session.getDirectory().path());

            try {
                session.close();
                this.client.setScreen(PreviewWorld.create(client, grid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private void openReadingWorldScreen() {
            this.client.setScreenAndRender(new MessageScreen(Text.translatable("selectWorld.data_read")));
        }

        private void loadIcon() {
            if (this.iconPath != null && Files.isRegularFile(this.iconPath)) {
                try (InputStream inputStream = Files.newInputStream(this.iconPath)) {
                    this.icon.load(NativeImage.read(inputStream));
                } catch (Throwable throwable) {
                    LOGGER.error("Invalid icon for world {}", this.level.getName(), throwable);
                    this.iconPath = null;
                }
            } else {
                this.icon.destroy();
            }

        }

        public void close() {
            this.icon.close();
        }

        public String getLevelName() {
            return this.level.getDisplayName();
        }

    }

}
