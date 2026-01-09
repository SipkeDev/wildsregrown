package com.wildsregrown.entity.blockEntities.renderer;

import com.wildsregrown.entities.block.PortableWorkbenchEntity;
import com.wildsregrown.entity.blockEntities.renderstates.SingleItemRenderState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PortableWorkbenchRender implements BlockEntityRenderer<PortableWorkbenchEntity, SingleItemRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public PortableWorkbenchRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public SingleItemRenderState createRenderState() {
        return new SingleItemRenderState();
    }

    @Override
    public void updateRenderState(PortableWorkbenchEntity blockEntity, SingleItemRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        renderState.light = world.getLightLevel(blockEntity.getPos());
    }

    @Override
    public void render(SingleItemRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();
        matrices.translate(0.5f, 1.25f, 0.5f);
        state.renderState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }

}
