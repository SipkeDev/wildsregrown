package com.wildsregrown.gui.radial.stack.stacks;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.RadialRenderer;
import com.wildsregrown.gui.radial.RadialScreen;
import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Property;
import org.joml.Matrix3x2fStack;

import java.util.Map;

public class PropertyScreenStack<T extends Comparable<T>> extends ScreenStack{

    public Property<T> property;

    public PropertyScreenStack(Property<T> property, ScreenEntry... items) {
        super(items);
        this.property = property;
    }

    @Override
    public void drawScreen(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {

        //centerPos
        int cx = RadialScreen.INSTANCE.width/2, cy = RadialScreen.INSTANCE.height/2;

        handleMouse(cx, cy, mouseX, mouseY);

        RadialRenderer.drawCircle(context, cx, cy, radius, Colors.grey);
        for(int i = 0; i < entries.length; i++) {

            double angle0 = angleIncrement * i;
            double angle1 = angle0 + angleIncrement;

            RadialRenderer.drawArc(context, cx, cy, angle0, angle1, radius, entries[i].rgb);
            if (i == itemIndice){
                RadialRenderer.drawArcOverlay(context, cx, cy, angle0, angle1, radius);
            }

        }

        context.drawCenteredTextWithShadow(textRenderer, entries[itemIndice].text, cx, cy + (int)(radius*1.2f), entries[itemIndice].rgb);

    }

    public BlockState update(BlockState state){
        return state.with(property, property.getValues().get(itemIndice));
    }

    public void update(Map<String, String> propertyMap) {
        propertyMap.put(property.getName(), property.getValues().get(itemIndice).toString());
    }
}
