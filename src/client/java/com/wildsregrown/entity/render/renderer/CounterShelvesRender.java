package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.CounterShelvesEntity;
import com.wildsregrown.entity.render.renderstates.RenderState;
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

public class CounterShelvesRender implements BlockEntityRenderer<CounterShelvesEntity, RenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public CounterShelvesRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    private float[] x = {0.3f, 0.7f, 0.3f, 0.7f};
    private float[] y = {0.25f, 0.25f, 0.7f, 0.7f};

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void updateRenderState(CounterShelvesEntity blockEntity, RenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        renderState.facing = state.get(Properties.HORIZONTAL_FACING);
        renderState.light = world.getLightLevel(blockEntity.getPos());
    }

    @Override
    public void render(RenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        renderItem(0, state, matrices, queue, cameraState);
        renderItem(1, state, matrices, queue, cameraState);
        renderItem(2, state, matrices, queue, cameraState);
        renderItem(3, state, matrices, queue, cameraState);
    }

    private void renderItem(int i, RenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){

        if (state.facing.getAxis() == Direction.Axis.Z) {
            matrices.translate(x[i], y[i], 0.5f);
        }else {
            matrices.translate(0.5f, y[i], x[i]);
        }
        matrices.scale(0.25f, 0.25f, 0.25f);

        state.renderState.render(matrices, queue, state.light*17, OverlayTexture.DEFAULT_UV, 0);

    }

}