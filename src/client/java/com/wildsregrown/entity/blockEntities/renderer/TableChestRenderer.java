package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildsregrown.entities.block.TableChestEntity;
import com.wildsregrown.entity.blockEntities.renderstates.TableChestRenderState;
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
import org.jetbrains.annotations.Nullable;

public class TableChestRenderer implements BlockEntityRenderer<TableChestEntity, TableChestRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public TableChestRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public TableChestRenderState createRenderState() {
        return new TableChestRenderState().init();
    }

    @Override
    public void extractRenderState(TableChestEntity blockEntity, TableChestRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@org.jspecify.annotations.Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelResolver().updateForTopItem(renderState.state[0], blockEntity.getItem(0), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelResolver().updateForTopItem(renderState.state[1], blockEntity.getItem(1), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelResolver().updateForTopItem(renderState.state[2], blockEntity.getItem(2), ItemDisplayContext.FIXED, world, null, 0);
        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.open = state.getValue(BlockStateProperties.OPEN);
            renderState.facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
        }
    }


    @Override
    public void submit(TableChestRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            if (state.open) {
                //Render contents
                for (int i = 0; i < 3; i++) {
                    renderSlot(i, state, matrices, queue, cameraState);
                }
            }
        }
    }

    private void renderSlot(int slot, TableChestRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        matrices.pushPose();
        if (state.facing.getAxis() == Direction.Axis.Z) {
            matrices.translate(0.3f + slot * 0.2f, 0.2f, 0.5f);
        } else {
            matrices.translate(0.5f, 0.2f, 0.3f + slot * 0.2f);
        }
        matrices.scale(0.125f, 0.125f, 0.125f);
        state.state[slot].submit(matrices, queue, state.light * 17, OverlayTexture.NO_OVERLAY, 0);
        matrices.popPose();
    }

}