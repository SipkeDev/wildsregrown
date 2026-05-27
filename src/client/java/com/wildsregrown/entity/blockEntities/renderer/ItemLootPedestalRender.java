package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.ItemLootPedestalEntity;
import com.wildsregrown.entity.blockEntities.renderstates.ItemLootPedestalRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Util;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ItemLootPedestalRender implements BlockEntityRenderer<ItemLootPedestalEntity, ItemLootPedestalRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public ItemLootPedestalRender(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ItemLootPedestalRenderState createRenderState() {
        return new ItemLootPedestalRenderState();
    }

    @Override
    public void extractRenderState(ItemLootPedestalEntity blockEntity, ItemLootPedestalRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@org.jspecify.annotations.Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getBlockPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (state != Blocks.AIR.defaultBlockState()) {
            renderState.var = state.getValue(ModProperties.VARIATIONS_2);
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
        }
    }

    @Override
    public void submit(ItemLootPedestalRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.var == 1){
            renderVar1(state, matrices, queue, cameraState);
        }else {
            renderVar2(state, matrices, queue, cameraState);
        }
    }

    private void renderVar1(ItemLootPedestalRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState){

        matrices.pushPose();
        matrices.translate(0.5f, 0.75f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        float currentTime = Util.getMillis() / 1000f;
        float lerpedAmount = MathUtil.clampAngle(currentTime);
        matrices.mulPose(Axis.YP.rotationDegrees(lerpedAmount));
        state.renderState.submit(matrices, queue, state.light*17, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();

    }

    private void renderVar2(ItemLootPedestalRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState){

        matrices.pushPose();
        matrices.translate(0.5f, 0.8f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);

        if (state.facing != null) {
            switch (state.facing) {
                case NORTH -> matrices.mulPose(Axis.XN.rotationDegrees(67.5f));
                case SOUTH -> matrices.mulPose(Axis.XP.rotationDegrees(67.5f));
                case EAST -> {
                    matrices.mulPose(Axis.YP.rotationDegrees(90f));
                    matrices.mulPose(Axis.XP.rotationDegrees(67.5f));
                }
                case WEST -> {
                    matrices.mulPose(Axis.YP.rotationDegrees(90f));
                    matrices.mulPose(Axis.XN.rotationDegrees(67.5f));
                }
            }
        }
        state.renderState.submit(matrices, queue, state.light*17, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();

    }

}
