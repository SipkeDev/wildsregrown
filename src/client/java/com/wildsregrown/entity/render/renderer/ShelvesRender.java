package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.ShelvesEntity;
import com.wildsregrown.entity.render.renderstates.ShelvesRenderState;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ShelvesRender implements BlockEntityRenderer<ShelvesEntity, ShelvesRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public ShelvesRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public ShelvesRenderState createRenderState() {
        return new ShelvesRenderState();
    }

    @Override
    public void updateRenderState(ShelvesEntity blockEntity, ShelvesRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.leftState, blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        ctx.itemModelManager().clearAndUpdate(renderState.rightState, blockEntity.getStack(1), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.facing = state.get(Properties.HORIZONTAL_FACING);
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(ShelvesRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            renderSlot(0, state, matrices, queue, cameraState);
            renderSlot(1, state, matrices, queue, cameraState);
        }
    }

    private void renderSlot(int slot, ShelvesRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){
        matrices.push();
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
            state.leftState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);
        }else {
            state.rightState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);
        }
        matrices.pop();

    }


}