package com.wildsregrown.gui.menu.builder.camera;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.sipke.math.MathUtil;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.render.state.GuiElementRenderState;
import net.minecraft.client.gui.render.state.SimpleGuiElementRenderState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.texture.TextureSetup;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

public record RenderWorldMap(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack,
                             int[] image, int size, int res, int x, int y,
                             @Nullable ScreenRect scissorArea, @Nullable ScreenRect bounds) implements GuiElementRenderState, SimpleGuiElementRenderState {

        public RenderWorldMap(RenderPipeline pipeline, TextureSetup textureSetup, Matrix3x2fStack stack, int[] image, int size, int res, int x, int y, @Nullable ScreenRect bounds) {
            this(pipeline, textureSetup, stack, image, size, res, x, y, bounds, getBounds(x, y, stack, bounds));
        }

        @Nullable
        private static ScreenRect getBounds(int x, int y, Matrix3x2fStack pose, @Nullable ScreenRect rect) {
            ScreenRect rectangle = new ScreenRect(x, y, 0, 0).transform(pose);
            return  rect != null ? rect.intersection(rectangle) : rectangle;
        }

        @Override
        public void setupVertices(VertexConsumer vertexConsumer) {

            stack.pushMatrix();
            int k = 3;
            for (int i = 0; i < res; i++) {
                for (int j = 0; j < res; j++) {

                    //if (j < res-1){k = 1;}

                    int dx = x + (int) MathUtil.range(i, 0, res, 0, size);
                    int dz = y + (int) MathUtil.range(j, 0, res, 0, size);
                    int color = image[i*res+j];

                    vertexConsumer.vertex(this.stack(), dx, dz).color(color);
                    vertexConsumer.vertex(this.stack(), dx, dz+k).color(color);
                    vertexConsumer.vertex(this.stack(), dx+k, dz).color(color);

                    vertexConsumer.vertex(this.stack(), dx, dz).color(color);
                    vertexConsumer.vertex(this.stack(), dx, dz+k).color(color);
                    vertexConsumer.vertex(this.stack(), dx+k, dz+k).color(color);
                }
            }
            stack.popMatrix();

        }
    }