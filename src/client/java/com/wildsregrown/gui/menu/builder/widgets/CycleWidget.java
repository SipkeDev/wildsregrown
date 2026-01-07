package com.wildsregrown.gui.menu.builder.widgets;

import com.google.common.collect.ImmutableList;
import com.wildsregrown.gui.KeyBindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.input.AbstractInput;
import net.minecraft.client.option.SimpleOption;
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

public class CycleWidget<T> extends PressableWidget {

    private static final ButtonTextures textures = new ButtonTextures(Identifier.of(modid, "widget/wood_button"), Identifier.of(modid, "widget/wood_button_disabled"), Identifier.of(modid, "widget/wood_button_highlighted"));
    public static final BooleanSupplier HAS_ALT_DOWN = () -> MinecraftClient.getInstance().isAltPressed();
    private final Text optionText;
    private int index;
    private T value;
    private final CycleWidget.Values<T> values;
    private final Function<T, Text> valueToText;
    private final CycleWidget.UpdateCallback<T> callback;
    private final boolean optionTextOmitted;
    private final SimpleOption.TooltipFactory<T> tooltipFactory;

    CycleWidget(int x, int y, int width, int height, Text message, Text optionText, int index, T value, CycleWidget.Values<T> values, Function<T, Text> valueToText, CycleWidget.UpdateCallback<T> callback, SimpleOption.TooltipFactory<T> tooltipFactory, boolean optionTextOmitted) {
        super(x, y, width, height, message);
        this.optionText = optionText;
        this.index = index;
        this.value = value;
        this.values = values;
        this.valueToText = valueToText;
        this.callback = callback;
        this.optionTextOmitted = optionTextOmitted;
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
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, textures.get(this.active, this.isSelected()), this.getX(), this.getY(), this.getWidth(), this.getHeight(), ColorHelper.getWhite(this.alpha));
        this.drawLabel(context.getHoverListener(this, DrawContext.HoverType.NONE));
    }

    private void refreshTooltip() {
        this.setTooltip(this.tooltipFactory.apply(this.value));
    }

    private void cycle(int amount) {
        List<T> list = this.values.getCurrent();
        this.index = MathHelper.floorMod(this.index + amount, list.size());
        T object = list.get(this.index);
        this.internalSetValue(object);
        this.callback.onValueChange(this, object);
    }

    private T getValue(int offset) {
        List<T> list = this.values.getCurrent();
        return list.get(MathHelper.floorMod(this.index + offset, list.size()));
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        if (verticalAmount > (double)0.0F) {
            this.cycle(-1);
        } else if (verticalAmount < (double)0.0F) {
            this.cycle(1);
        }

        return true;
    }

    public void setValue(T value) {
        List<T> list = this.values.getCurrent();
        int i = list.indexOf(value);
        if (i != -1) {
            this.index = i;
        }
        this.internalSetValue(value);
    }

    private void internalSetValue(T value) {
        Text text = this.composeText(value);
        this.setMessage(text);
        this.value = value;
        this.refreshTooltip();
    }

    private Text composeText(T value) {
        return (Text)(this.optionTextOmitted ? (Text)this.valueToText.apply(value) : this.composeGenericOptionText(value));
    }

    private MutableText composeGenericOptionText(T value) {
        return ScreenTexts.composeGenericOptionText(this.optionText, (Text)this.valueToText.apply(value));
    }

    public T getValue() {
        return this.value;
    }

    public static <T> CycleWidget.Builder<T> builder(Function<T, Text> valueToText) {
        return new CycleWidget.Builder<T>(valueToText);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {}

    @Environment(EnvType.CLIENT)
    public static class Builder<T> {
        private int initialIndex;
        @Nullable
        private T value;
        private final Function<T, Text> valueToText;
        private SimpleOption.TooltipFactory<T> tooltipFactory = (value) -> null;
        private Values<T> values = CycleWidget.Values.<T>of(ImmutableList.of());
        private boolean optionTextOmitted;

        public Builder(Function<T, Text> valueToText) {
            this.valueToText = valueToText;
        }

        public CycleWidget.Builder<T> values(Collection<T> values) {
            return this.values(CycleWidget.Values.of(values));
        }

        @SafeVarargs
        public final CycleWidget.Builder<T> values(T... values) {
            return this.values(ImmutableList.copyOf(values));
        }

        public CycleWidget.Builder<T> values(List<T> defaults, List<T> alternatives) {
            return this.values(CycleWidget.Values.of(CycleWidget.HAS_ALT_DOWN, defaults, alternatives));
        }

        public CycleWidget.Builder<T> values(BooleanSupplier alternativeToggle, List<T> defaults, List<T> alternatives) {
            return this.values(CycleWidget.Values.of(alternativeToggle, defaults, alternatives));
        }

        public CycleWidget.Builder<T> values(Values<T> values) {
            this.values = values;
            return this;
        }

        public CycleWidget.Builder<T> tooltip(SimpleOption.TooltipFactory<T> tooltipFactory) {
            this.tooltipFactory = tooltipFactory;
            return this;
        }

        public CycleWidget.Builder<T> initially(T value) {
            this.value = value;
            int i = this.values.getDefaults().indexOf(value);
            if (i != -1) {
                this.initialIndex = i;
            }

            return this;
        }

        public CycleWidget.Builder<T> omitKeyText() {
            this.optionTextOmitted = true;
            return this;
        }

        public CycleWidget<T> build(Text optionText, UpdateCallback<T> callback) {
            return this.build(0, 0, 150, 20, optionText, callback);
        }

        public CycleWidget<T> build(int x, int y, int width, int height, Text optionText) {
            return this.build(x, y, width, height, optionText, (button, value) -> {
            });
        }

        public CycleWidget<T> build(int x, int y, int width, int height, Text optionText, UpdateCallback<T> callback) {
            List<T> list = this.values.getDefaults();
            if (list.isEmpty()) {
                throw new IllegalStateException("No values for cycle button");
            } else {
                T object = (T)(this.value != null ? this.value : list.get(this.initialIndex));
                Text text = (Text)this.valueToText.apply(object);
                Text text2 = (Text)(this.optionTextOmitted ? text : ScreenTexts.composeGenericOptionText(optionText, text));
                return new CycleWidget<>(x, y, width, height, text2, optionText, this.initialIndex, object, this.values, this.valueToText, callback, this.tooltipFactory, this.optionTextOmitted);
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public interface Values<T> {
        List<T> getCurrent();

        List<T> getDefaults();

        static <T> CycleWidget.Values<T> of(Collection<T> values) {
            final List<T> list = ImmutableList.copyOf(values);
            return new CycleWidget.Values<>() {
                public List<T> getCurrent() {
                    return list;
                }

                public List<T> getDefaults() {
                    return list;
                }
            };
        }

        static <T> CycleWidget.Values<T> of(final BooleanSupplier alternativeToggle, List<T> defaults, List<T> alternatives) {
            final List<T> list = ImmutableList.copyOf(defaults);
            final List<T> list2 = ImmutableList.copyOf(alternatives);
            return new CycleWidget.Values<>() {
                public List<T> getCurrent() {
                    return alternativeToggle.getAsBoolean() ? list2 : list;
                }

                public List<T> getDefaults() {
                    return list;
                }
            };
        }
    }

    @Environment(EnvType.CLIENT)
    public interface UpdateCallback<T> {
        void onValueChange(CycleWidget<T> button, T value);
    }
}
