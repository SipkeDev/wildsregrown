package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildsregrown.entities.block.PortableWorkbenchEntity;
import com.wildsregrown.entity.blockEntities.renderstates.SingleItemRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PortableWorkbenchRender implements BlockEntityRenderer<PortableWorkbenchEntity, SingleItemRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public PortableWorkbenchRender(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public SingleItemRenderState createRenderState() {
        return new SingleItemRenderState();
    }

    @Override
    public void extractRenderState(PortableWorkbenchEntity blockEntity, SingleItemRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@org.jspecify.annotations.Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getBlockPos().asLong());
        renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
    }

    @Override
    public void submit(SingleItemRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraRenderState) {
        matrices.pushPose();
        matrices.translate(0.5f, 1.25f, 0.5f);
        state.renderState.submit(matrices, queue, state.light*17, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

}
