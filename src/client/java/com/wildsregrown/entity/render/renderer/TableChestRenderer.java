package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.TableChestEntity;
import com.wildsregrown.entity.render.renderstates.SingleItemFacingRenderState;
import com.wildsregrown.entity.render.renderstates.TableChestRenderState;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TableChestRenderer implements BlockEntityRenderer<TableChestEntity, TableChestRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public TableChestRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public TableChestRenderState createRenderState() {
        return new TableChestRenderState().init();
    }

    @Override
    public void updateRenderState(TableChestEntity blockEntity, TableChestRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.state[0], blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelManager().clearAndUpdate(renderState.state[1], blockEntity.getStack(1), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelManager().clearAndUpdate(renderState.state[2], blockEntity.getStack(2), ItemDisplayContext.FIXED, world, null, 0);
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.open = state.get(Properties.OPEN);
            renderState.facing = state.get(Properties.HORIZONTAL_FACING);
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(TableChestRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            if (state.open) {
                //Render contents
                for (int i = 0; i < 3; i++) {
                    renderSlot(i, state, matrices, queue, cameraState);
                }
            }
        }
    }

    private void renderSlot(int slot, TableChestRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();
        if (state.facing.getAxis() == Direction.Axis.Z) {
            matrices.translate(0.3f + slot * 0.2f, 0.2f, 0.5f);
        } else {
            matrices.translate(0.5f, 0.2f, 0.3f + slot * 0.2f);
        }
        matrices.scale(0.125f, 0.125f, 0.125f);
        state.state[slot].render(matrices, queue, state.light * 17, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }

}