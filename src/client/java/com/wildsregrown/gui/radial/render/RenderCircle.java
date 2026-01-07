package com.wildsregrown.gui.radial.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2f;
import org.joml.Matrix3x2fStack;

public record RenderCircle(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack,
                           int cx, int cy, float radius, int color,
                           @Nullable ScreenRect scissorArea, @Nullable ScreenRect bounds) implements GuiElementRenderState, SimpleGuiElementRenderState {

    public RenderCircle(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack, int cx, int cy, float radius, int color, @Nullable ScreenRect bounds) {
        this(pipeline, textureSetup, stack, cx, cy, radius, color, bounds, getBounds(cx, cy, stack, bounds));
    }

    @Nullable
    private static ScreenRect getBounds(int x, int y, Matrix3x2fStack pose, @Nullable ScreenRect rect) {
        ScreenRect rectangle = new ScreenRect(x, y, 0, 0).transform(pose);
        return rect != null ? rect.intersection(rectangle) : rectangle;
    }

        @Override
        public void setupVertices(VertexConsumer vertexConsumer) {

            stack.pushMatrix();
            double steps = 360.0/12.0;
            for (double i = 0; i < steps; i++) {

                double angle0 = Math.toRadians(i*steps);
                double angle1 = Math.toRadians((i*steps)+steps);

                float x0 = cx + (float) (Math.cos(angle0) * radius);
                float y0 = cy + (float) (Math.sin(angle0) * radius);
                float x1 = cx + (float) (Math.cos(angle1) * radius);
                float y1 = cy + (float) (Math.sin(angle1) * radius);
                vertexConsumer.vertex(this.stack(), cx, cy).color(color);
                vertexConsumer.vertex(this.stack(), x1, y1).color(color);
                vertexConsumer.vertex(this.stack(), x0, y0).color(color);
            }
            stack.popMatrix();

        }
    }