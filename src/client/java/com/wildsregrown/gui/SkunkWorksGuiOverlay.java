package com.wildsregrown.gui;

public class SkunkWorksGuiOverlay {

    private static float totalTickDelta;

    public static void testingTheBlackBird(){

        /**
        HudRenderCallback.EVENT.register((context, tickDelta) -> {
            int color = Color.TRANSLUCENT; // Red
            int targetColor = Color.lightGray.getRGB(); // Green

            totalTickDelta += tickDelta.getTickDelta(false);

            float lerpedAmount = MathUtil.abs(MathUtil.sin(totalTickDelta / 50F));
            int lerpedColor = ColorHelper.lerp(lerpedAmount, color, targetColor);

            context.fill(0, 0, 8, MinecraftClient.getInstance().getWindow().getHeight(), 0, lerpedColor);

            // Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
            Matrix4f transformationMatrix = context.getMatrices().peek().getPositionMatrix();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);

            // Write our vertices, Z doesn't really matter since it's on the HUD.
            buffer.vertex(transformationMatrix, 20, 20, 5).color(0xFF414141);
            buffer.vertex(transformationMatrix, 5, 40, 5).color(0xFF000000);
            buffer.vertex(transformationMatrix, 35, 40, 5).color(0xFF000000);
            buffer.vertex(transformationMatrix, 20, 60, 5).color(0xFF414141);

            RenderSystem.setShader(RenderSystem.getShader());
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            // Draw the buffer onto the screen.
            BufferRenderer.drawWithGlobalProgram(buffer.end());
        });
         */
    }

}
