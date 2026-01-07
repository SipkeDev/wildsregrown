package com.wildsregrown.gui.radial.stack;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.RadialRenderer;
import com.wildsregrown.gui.radial.RadialScreen;
import com.sipke.math.MathUtil;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public class ScreenStack{

    public final ScreenEntry[] entries;
    public final List<ScreenStack> children;

    public final float radius = 60f;
    public final double angleIncrement;
    public int itemIndice;

    public ScreenStack(ScreenEntry... entries) {
        this.entries = entries;
        this.angleIncrement = 360.0 / entries.length;
        this.children = new ArrayList<>();
        this.itemIndice = 0;
    }

    public void handleMouse(int cx, int cy, int mouseX, int mouseY) {
        if (!MathUtil.isInside(cx, cy, mouseX, mouseY, 16)) {
            double mouseAngle = RadialRenderer.mouseAngle(cx, cy, mouseX, mouseY);
            for (int i = 0; i < entries.length; i++) {
                double angle0 = i*angleIncrement;
                double angle1 = angle0 + angleIncrement;
                if (mouseAngle > angle0 && mouseAngle < angle1) {
                    itemIndice = i;
                    break;
                }
            }
        }
    }

    public void drawScreen(DrawContext context, TextRenderer textRenderer, int mouseX, int mouseY) {

        //centerPos
        int cx = RadialScreen.INSTANCE.width/2, cy = RadialScreen.INSTANCE.height/2;

        handleMouse(cx, cy, mouseX, mouseY);

        RadialRenderer.drawCircle(context, cx, cy, radius, Colors.darkGrey);
        for(int i = 0; i < entries.length; i++) {

            double angle0 = i * angleIncrement;
            double angle1 = angle0 + angleIncrement;

            RadialRenderer.drawArc(context, cx, cy, angle0, angle1, radius, entries[i].rgb);
            if (i == itemIndice){
                RadialRenderer.drawArcOverlay(context, cx, cy, angle0, angle1, radius);
            }

        }

        context.drawCenteredTextWithShadow(textRenderer, entries[itemIndice].text, cx, cy + (int)(radius*1.2f), entries[itemIndice].rgb);

    }

    public void add(ScreenStack stack) {
        this.children.add(stack);
    }

    public void mouseClicked(int index) {}

}
