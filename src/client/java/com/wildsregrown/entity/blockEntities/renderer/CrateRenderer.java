package com.wildsregrown.entity.blockEntities.renderer;

import com.wildsregrown.entities.block.CrateEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.sipke.math.MathUtil;
import com.wildsregrown.entity.blockEntities.renderstates.CrateRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrateRenderer implements BlockEntityRenderer<CrateEntity, CrateRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public CrateRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public CrateRenderState createRenderState() {
        return new CrateRenderState();
    }

    @Override
    public void extractRenderState(CrateEntity blockEntity, CrateRenderState renderState, float f, Vec3 vec3, ModelFeatureRenderer.@org.jspecify.annotations.Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, f, vec3, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getBlockPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.hasLid = state.getValue(BlockStateProperties.OPEN);
            renderState.count = blockEntity.count;
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
        }
    }

    @Override
    public void submit(CrateRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState renderState) {
        matrices.pushPose();
        //Render contents
        if (state.hasLid) {
            float range = MathUtil.range(state.count, 0f, 1024, 0f, 0.3f);
            matrices.translate(0.5f, 0.2f+range, 0.5f);
            matrices.scale(0.75f, 0.75f, 0.75f);
            state.renderState.submit(matrices, queue, state.light, 0, 0);
        }
        //render lid
        else {
            matrices.translate(0.5f, 1f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            state.renderState.submit(matrices, queue, state.light, 0, 0);
        }

        matrices.popPose();
    }

}