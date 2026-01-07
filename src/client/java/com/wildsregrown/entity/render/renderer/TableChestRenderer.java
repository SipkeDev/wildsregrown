package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.entities.block.CounterShelvesEntity;
import com.wildsregrown.entities.block.TableChestEntity;
import com.wildsregrown.entity.render.renderstates.RenderState;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TableChestRenderer implements BlockEntityRenderer<TableChestEntity, RenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public TableChestRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void updateRenderState(TableChestEntity blockEntity, RenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(0), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        renderState.facing = state.get(Properties.HORIZONTAL_FACING);
        renderState.light = world.getLightLevel(blockEntity.getPos());
    }

    @Override
    public void render(RenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        //Render contents
        if (state.facing == Direction.DOWN) {
            for (int i = 0; i < 3; i++) {
                renderSlot(i, state, matrices, queue, cameraState);
            }
        }
    }

    private void renderSlot(int slot, RenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState){

        matrices.push();
        if (state.facing.getAxis() == Direction.Axis.Z) {
            matrices.translate(0.3f + slot * 0.2f, 0.2f, 0.5f);
        }else {
            matrices.translate(0.5f, 0.2f, 0.3f + slot * 0.2f);
        }
        matrices.scale(0.125f, 0.125f, 0.125f);

        /**
        ctx.getItemRenderer().renderItem(
                entity.getStack(slot),
                ModelTransformationMode.NONE,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
        );

        matrices.pop();
         */
    }

}