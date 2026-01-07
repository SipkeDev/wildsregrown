package com.wildsregrown.gui.radial;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.gui.radial.render.RenderCircle;
import com.wildsregrown.gui.radial.render.RenderArch;
import com.wildsregrown.gui.radial.render.RenderHollowArch;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.TextureSetup;

public class RadialRenderer {

    private static final int overlay = Colors.darkYellow;

    public static void drawCircle(DrawContext context, int cx, int cy, float radius, int color){
        context.state.addSimpleElement(new RenderCircle(RenderPipelines.GUI, TextureSetup.empty(), context.getMatrices(), cx, cy, 1.12f*radius, color, null));
    }

    public static void drawArc(DrawContext context, int cx, int cy, double start, double end, float radius, int color){
        context.state.addSimpleElement(new RenderArch(RenderPipelines.GUI, TextureSetup.empty(), context.getMatrices(), cx, cy, start, end, radius, color,null));
    }

    public static void drawArcOverlay(DrawContext context, int cx, int cy, double start, double end, float radius) {
        context.state.addSimpleElement(new RenderHollowArch(RenderPipelines.GUI, TextureSetup.empty(), context.getMatrices(), cx, cy, start, end, radius, overlay,null));
    }

    public static double mouseAngle(int cx, int cy, int mouseX, int mouseY) {
        return MathUtil.clampAngle(Math.toDegrees(MathUtil.angle(cx, cy, mouseX, mouseY))-90);
    }

}
