package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.blocks.dungeon.StructureEntity;
import com.wildsregrown.entity.render.renderstates.StructureBlockRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;

public class StructureBlockRenderer implements BlockEntityRenderer<StructureEntity, StructureBlockRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public StructureBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public StructureBlockRenderState createRenderState() {
        return new StructureBlockRenderState();
    }

    @Override
    public void render(StructureBlockRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (MinecraftClient.getInstance().player.isCreativeLevelTwoOp() || MinecraftClient.getInstance().player.isSpectator()) {
            if (state.showOutline) {

                int x0 = state.x0, x1 = state.x1;
                int y0 = state.y0, y1 = state.y1;
                int z0 = state.z0, z1 = state.z1;

                //VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getLines());
                //VertexRendering.drawBox(matrices, vertexConsumer, x0, y0, z0, x1, y1, z1, 0.9F, 0.9F, 0.9F, 1.0F);

            }
        }
    }

}
