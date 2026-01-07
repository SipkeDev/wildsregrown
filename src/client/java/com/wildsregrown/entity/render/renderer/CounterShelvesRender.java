package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.CounterShelvesEntity;
import com.wildsregrown.entity.render.renderstates.CounterShelvesRenderState;
import com.wildsregrown.entity.render.renderstates.SingleItemFacingRenderState;
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

public class CounterShelvesRender implements BlockEntityRenderer<CounterShelvesEntity, CounterShelvesRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public CounterShelvesRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    private float[] x = {0.3f, 0.7f, 0.3f, 0.7f};
    private float[] y = {0.25f, 0.25f, 0.7f, 0.7f};

    @Override
    public CounterShelvesRenderState createRenderState() {
        return new CounterShelvesRenderState().init();
    }

    @Override
    public void updateRenderState(CounterShelvesEntity blockEntity, CounterShelvesRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.state[0], blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelManager().clearAndUpdate(renderState.state[1], blockEntity.getStack(1), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelManager().clearAndUpdate(renderState.state[2], blockEntity.getStack(2), ItemDisplayContext.FIXED, world, null, 0);
        ctx.itemModelManager().clearAndUpdate(renderState.state[3], blockEntity.getStack(3), ItemDisplayContext.FIXED, world, null, 0);
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.facing = state.get(Properties.HORIZONTAL_FACING);
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(CounterShelvesRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.facing != null) {
            renderItem(0, state, matrices, queue, cameraState);
            renderItem(1, state, matrices, queue, cameraState);
            renderItem(2, state, matrices, queue, cameraState);
            renderItem(3, state, matrices, queue, cameraState);
        }
    }

    private void renderItem(int i, CounterShelvesRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){
        if (!state.state[i].isEmpty()) {
            matrices.push();
            if (state.facing.getAxis() == Direction.Axis.Z) {
                matrices.translate(x[i], y[i], 0.5f);
            } else {
                matrices.translate(0.5f, y[i], x[i]);
            }
            matrices.scale(0.25f, 0.25f, 0.25f);
            state.state[i].render(matrices, queue, state.light * 17, OverlayTexture.DEFAULT_UV, 0);
            matrices.pop();
        }

    }

}