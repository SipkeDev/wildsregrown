package com.wildsregrown.gui.menu.builder.widgets;

import com.google.common.collect.ImmutableList;
import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.KeyBindings;
import com.wildsregrown.world.builder.SpawnPicker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.input.AbstractInput;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

import static com.wildsregrown.WildsRegrown.modid;

public class ClimateWidget extends PressableWidget {

    private final Text optionText;
    private int index;
    private SpawnPicker value;
    private final ClimateWidget.Values<SpawnPicker> values;
    private final Function<SpawnPicker, Text> valueToText;
    private final ClimateWidget.UpdateCallback<SpawnPicker> callback;
    private final SimpleOption.TooltipFactory<SpawnPicker> tooltipFactory;

    ClimateWidget(int x, int y, int width, int height, Text message, Text optionText, int index, SpawnPicker value, ClimateWidget.Values<SpawnPicker> values, Function<SpawnPicker, Text> valueToText, ClimateWidget.UpdateCallback<SpawnPicker> callback, SimpleOption.TooltipFactory<SpawnPicker> tooltipFactory) {
        super(x, y, width, height, message);
        this.optionText = optionText;
        this.index = index;
        this.value = value;
        this.values = values;
        this.valueToText = valueToText;
        this.callback = callback;
        this.tooltipFactory = tooltipFactory;
        this.refreshTooltip();
    }

    @Override
    public void onPress(AbstractInput input) {
        if (KeyBindings.hasKeyDown(InputUtil.GLFW_KEY_LEFT_SHIFT)) {
            this.cycle(-1);
        } else {
            this.cycle(1);
        }
    }

    @Override
    protected void drawIcon(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        context.fill(this.getX(), this.getY(), this.getX() + this.getWidth(), this.getY() + this.getHeight(), value.getClimate().getRgb());
        this.drawLabel(context.getHoverListener(this, DrawContext.HoverType.NONE));
    }

    private void refreshTooltip() {
        this.setTooltip(this.tooltipFactory.apply(this.value));
    }

    private void cycle(int amount) {
        List<SpawnPicker> list = this.values.getCurrent();
        this.index = MathHelper.floorMod(this.index + amount, list.size());
        SpawnPicker object = list.get(this.index);
        this.internalSetValue(object);
        this.callback.onValueChange(this, object);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > (double)0.0F) {
            this.cycle(-1);
        } else if (verticalAmount < (double)0.0F) {
            this.cycle(1);
        }

        return true;
    }

    private void internalSetValue(SpawnPicker value) {
        Text text = this.composeText(value);
        this.setMessage(text);
        this.value = value;
        this.refreshTooltip();
    }

    private Text composeText(SpawnPicker value) {
        return ScreenTexts.composeGenericOptionText(this.optionText, this.valueToText.apply(value));
    }

    public static ClimateWidget.Builder builder(Function<SpawnPicker, Text> valueToText) {
        return new ClimateWidget.Builder(valueToText);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    @Environment(EnvType.CLIENT)
    public static class Builder {

        private int initialIndex;
        @Nullable
        private SpawnPicker value;
        private final Function<SpawnPicker, Text> valueToText;
        private SimpleOption.TooltipFactory<SpawnPicker> tooltipFactory = (value) -> null;
        private Values<SpawnPicker> values = ClimateWidget.Values.of(ImmutableList.of());

        public Builder(Function<SpawnPicker, Text> valueToText) {
            this.valueToText = valueToText;
        }

        public ClimateWidget.Builder values(Collection<SpawnPicker> values) {
            return this.values(ClimateWidget.Values.of(values));
        }

        @SafeVarargs
        public final ClimateWidget.Builder values(SpawnPicker... values) {
            return this.values(ImmutableList.copyOf(values));
        }

        public ClimateWidget.Builder values(Values<SpawnPicker> values) {
            this.values = values;
            return this;
        }

        public ClimateWidget.Builder tooltip(SimpleOption.TooltipFactory<SpawnPicker> tooltipFactory) {
            this.tooltipFactory = tooltipFactory;
            return this;
        }

        public ClimateWidget build(int x, int y, int width, int height, Text optionText, UpdateCallback<SpawnPicker> callback) {
            List<SpawnPicker> list = this.values.getDefaults();
            if (list.isEmpty()) {
                throw new IllegalStateException("No values for cycle button");
            } else {
                SpawnPicker object = (this.value != null ? this.value : list.get(this.initialIndex));
                Text text = this.valueToText.apply(object);
                return new ClimateWidget(x, y, width, height, text, optionText, this.initialIndex, object, this.values, this.valueToText, callback, this.tooltipFactory);
            }
        }

        public ClimateWidget.Builder initially(SpawnPicker spawnPicker) {
            this.initialIndex = spawnPicker.getId();
            return this;
        }
    }

    @Environment(EnvType.CLIENT)
    public interface Values<T> {
        List<T> getCurrent();

        List<T> getDefaults();

        static ClimateWidget.Values<SpawnPicker> of(Collection<SpawnPicker> values) {
            final List<SpawnPicker> list = ImmutableList.copyOf(values);
            return new ClimateWidget.Values<>() {
                public List<SpawnPicker> getCurrent() {
                    return list;
                }
                public List<SpawnPicker> getDefaults() {
                    return list;
                }
            };
        }

        static ClimateWidget.Values<SpawnPicker> of(final BooleanSupplier alternativeToggle, List<SpawnPicker> defaults, List<SpawnPicker> alternatives) {
            final List<SpawnPicker> list = ImmutableList.copyOf(defaults);
            final List<SpawnPicker> list2 = ImmutableList.copyOf(alternatives);
            return new ClimateWidget.Values<>() {
                public List<SpawnPicker> getCurrent() {
                    return alternativeToggle.getAsBoolean() ? list2 : list;
                }

                public List<SpawnPicker> getDefaults() {
                    return list;
                }
            };
        }
    }

    @Environment(EnvType.CLIENT)
    public interface UpdateCallback<SpawnPicker> {
        void onValueChange(ClimateWidget button, com.wildsregrown.world.builder.SpawnPicker value);
    }
}
