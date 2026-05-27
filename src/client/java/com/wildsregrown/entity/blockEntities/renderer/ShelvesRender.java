package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildsregrown.entities.block.ShelvesEntity;
import com.wildsregrown.entity.blockEntities.renderstates.ShelvesRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ShelvesRender implements BlockEntityRenderer<ShelvesEntity, ShelvesRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public ShelvesRender(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ShelvesRenderState createRenderState() {
        return new ShelvesRenderState();
    }

    @Override
    public void extractRenderState(ShelvesEntity blockEntity, ShelvesRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@org.jspecify.annotations.Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.leftState, blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getBlockPos().asLong());
        ctx.itemModelResolver().updateForTopItem(renderState.rightState, blockEntity.getStack(1), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getBlockPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
        }
    }

    @Override
    public void submit(ShelvesRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            renderSlot(0, state, matrices, queue, cameraState);
            renderSlot(1, state, matrices, queue, cameraState);
        }
    }

    private void renderSlot(int slot, ShelvesRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState){
        matrices.pushPose();
        switch (state.facing){
            case NORTH -> {
                matrices.translate(0.3f + (slot == 0 ? 0 : 0.4f), 0.5f, 0.7f);
            }
            case SOUTH -> {
                matrices.translate(0.3f + (slot == 0 ? 0 : 0.4f), 0.5f, 0.3f);
            }
            case EAST -> {
                matrices.translate(0.3f, 0.5f, 0.3f + (slot == 0 ? 0 : 0.4f));
            }
            case WEST -> {
                matrices.translate(0.7f, 0.5f, 0.3f + (slot == 0 ? 0 : 0.4f));
            }
        }
        matrices.scale(0.25f, 0.25f, 0.25f);
        if (slot == 0) {
            state.leftState.submit(matrices, queue, state.light*17, OverlayTexture.NO_OVERLAY, 0);
        }else {
            state.rightState.submit(matrices, queue, state.light*17, OverlayTexture.NO_OVERLAY, 0);
        }
        matrices.popPose();

    }


}