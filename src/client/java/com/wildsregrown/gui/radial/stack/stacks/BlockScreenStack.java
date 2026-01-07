package com.wildsregrown.gui.radial.stack.stacks;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.RadialRenderer;
import com.wildsregrown.gui.radial.RadialScreen;
import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;
import net.minecraft.block.Block;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class BlockScreenStack extends ScreenStack {

    public Block block;
    public Map<String, String> propertyMap = new HashMap<>();

    public BlockScreenStack(Block block, ScreenEntry... items) {
        super(items);
        this.block = block;
    }

    public void drawBlockState(DrawContext context, TextRenderer textRenderer){

        int y = 24, s = 12;
        context.drawTooltip(textRenderer, Text.literal(block.getTranslationKey()), 12, y);
        y += s;
        for (int i = 0; i < children.size(); i++) {
            PropertyScreenStack stack = (PropertyScreenStack) children.get(i);
            context.drawTooltip(textRenderer, Text.literal(stack.property.getName() + " / " + stack.property.getValues().get(stack.itemIndice)), 12, y+(i*s));
        }

    }

    @Override
    public void drawScreen(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {

        drawBlockState(context, textRenderer);

        //centerPos
        int cx = RadialScreen.INSTANCE.width/2, cy = RadialScreen.INSTANCE.height/2;

        handleMouse(cx, cy, mouseX, mouseY);
        RadialRenderer.drawCircle(context, cx, cy, radius, Colors.black);
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

    @Override
    public void mouseClicked(int index) {
        if (children.get(index) instanceof PropertyScreenStack<?> stack) {
            stack.update(propertyMap);
        }
    }

}
