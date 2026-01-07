package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.GenericSingleStorageEntity;
import com.wildsregrown.entity.render.renderstates.RenderState;
import net.minecraft.block.BlockState;
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

public class SingleStorageRender implements BlockEntityRenderer<GenericSingleStorageEntity, RenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public SingleStorageRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void updateRenderState(GenericSingleStorageEntity blockEntity, RenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        renderState.facing = state.get(Properties.HORIZONTAL_FACING);
        renderState.light = world.getLightLevel(blockEntity.getPos());
    }

    @Override
    public void render(RenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();
        matrices.translate(0.5f, 0.4f, 0.5f);
        matrices.scale(0.5f, 0.5f, 0.5f);
        /**
         ctx.getItemRenderer().renderItem(
         entity.getStack(),
         ModelTransformationMode.NONE,
         getLightLevel(entity.getWorld(), entity.getPos()),
         OverlayTexture.DEFAULT_UV,
         matrices,
         vertexConsumers,
         entity.getWorld(),
         1
         );
         */
    }

}