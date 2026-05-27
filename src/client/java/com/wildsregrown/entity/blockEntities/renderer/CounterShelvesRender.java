package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildsregrown.entities.block.CounterShelvesEntity;
import com.wildsregrown.entity.blockEntities.renderstates.CounterShelvesRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class CounterShelvesRender implements BlockEntityRenderer<CounterShelvesEntity, CounterShelvesRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public CounterShelvesRender(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    private float[] x = {0.3f, 0.7f, 0.3f, 0.7f};
    private float[] y = {0.25f, 0.25f, 0.7f, 0.7f};

    @Override
    public CounterShelvesRenderState createRenderState() {
        return new CounterShelvesRenderState().init();
    }

    @Override
    public void extractRenderState(CounterShelvesEntity blockEntity, CounterShelvesRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.state[0], blockEntity.getItem(0), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelResolver().updateForTopItem(renderState.state[1], blockEntity.getItem(1), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelResolver().updateForTopItem(renderState.state[2], blockEntity.getItem(2), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelResolver().updateForTopItem(renderState.state[3], blockEntity.getItem(3), ItemDisplayContext.FIXED, world, null, 0);
        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
        }
    }

    @Override
    public void submit(CounterShelvesRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            renderItem(0, state, matrices, queue, cameraState);
            renderItem(1, state, matrices, queue, cameraState);
            renderItem(2, state, matrices, queue, cameraState);
            renderItem(3, state, matrices, queue, cameraState);
        }
    }

    private void renderItem(int i, CounterShelvesRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState){
        if (!state.state[i].isEmpty()) {
            matrices.pushPose();
            if (state.facing.getAxis() == Direction.Axis.Z) {
                matrices.translate(x[i], y[i], 0.5f);
            } else {
                matrices.translate(0.5f, y[i], x[i]);
            }
            matrices.scale(0.25f, 0.25f, 0.25f);
            state.state[i].submit(matrices, queue, state.light * 17, OverlayTexture.NO_OVERLAY, 0);
            matrices.popPose();
        }

    }

}