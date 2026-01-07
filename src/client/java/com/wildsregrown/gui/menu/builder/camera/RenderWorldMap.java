package com.wildsregrown.gui.menu.builder.camera;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

public record RenderWorldMap(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack,
                             int[] image, int size, int res,
                             @Nullable ScreenRect scissorArea, @Nullable ScreenRect bounds) implements GuiElementRenderState, SimpleGuiElementRenderState {

        public RenderWorldMap(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack, int[] image, int size, int res, @Nullable ScreenRect bounds) {
            this(pipeline, textureSetup, stack, image, size, res, bounds, getBounds(0, 0, stack, bounds));
        }

        @Nullable
        private static ScreenRect getBounds(int x, int y, Matrix3x2fStack pose, @Nullable ScreenRect rect) {
            ScreenRect rectangle = new ScreenRect(x, y, 0, 0).transform(pose);
            return rect != null ? rect.intersection(rectangle) : rectangle;
        }

        @Override
        public void setupVertices(VertexConsumer vertexConsumer) {

            stack.pushMatrix();
            for (int i = 0; i < res; i++) {
                for (int j = 0; j < res; j++) {

                    int dx = (int) MathUtil.range(i, 0, res, 0, size);
                    int dz = (int) MathUtil.range(j, 0, res, 0, size);
                    int color = image[i*res+j];

                    vertexConsumer.vertex(this.stack(), dx, dz).color(color);
                    vertexConsumer.vertex(this.stack(), dx, dz+8).color(color);
                    vertexConsumer.vertex(this.stack(), dx+8, dz).color(color);

                    vertexConsumer.vertex(this.stack(), dx, dz).color(color);
                    vertexConsumer.vertex(this.stack(), dx, dz+8).color(color);
                    vertexConsumer.vertex(this.stack(), dx+8, dz+8).color(color);
                }
            }
            stack.popMatrix();

        }
    }