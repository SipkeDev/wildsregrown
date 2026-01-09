package com.wildsregrown.entity.blockEntities.renderer;

import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.ItemLootPedestalEntity;
import com.wildsregrown.entity.blockEntities.renderstates.ItemLootPedestalRenderState;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.Util;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemLootPedestalRender implements BlockEntityRenderer<ItemLootPedestalEntity, ItemLootPedestalRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public ItemLootPedestalRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ItemLootPedestalRenderState createRenderState() {
        return new ItemLootPedestalRenderState();
    }

    @Override
    public void updateRenderState(ItemLootPedestalEntity blockEntity, ItemLootPedestalRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (state != Blocks.AIR.getDefaultState()) {
            renderState.var = state.get(ModProperties.VARIATIONS_2);
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(ItemLootPedestalRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.var == 1){
            renderVar1(state, matrices, queue, cameraState);
        }else {
            renderVar2(state, matrices, queue, cameraState);
        }
    }

    private void renderVar1(ItemLootPedestalRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){

        matrices.push();
        matrices.translate(0.5f, 0.75f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        float currentTime = Util.getMeasuringTimeMs() / 1000f;
        float lerpedAmount = MathUtil.clampAngle(currentTime);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(lerpedAmount));
        state.renderState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();

    }

    private void renderVar2(ItemLootPedestalRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){

        matrices.push();
        matrices.translate(0.5f, 0.8f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);

        if (state.facing != null) {
            switch (state.facing) {
                case NORTH -> matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(67.5f));
                case SOUTH -> matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(67.5f));
                case EAST -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(67.5f));
                }
                case WEST -> {
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
                    matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(67.5f));
                }
            }
        }
        state.renderState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();

    }

}
