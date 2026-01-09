package com.wildsregrown.entity.blockEntities.renderer;

import com.wildsregrown.entities.block.CrateEntity;
import com.sipke.math.MathUtil;
import com.wildsregrown.entity.blockEntities.renderstates.CrateRenderState;
import net.minecraft.block.AirBlock;
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

public class CrateRenderer implements BlockEntityRenderer<CrateEntity, CrateRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public CrateRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public CrateRenderState createRenderState() {
        return new CrateRenderState();
    }

    @Override
    public void updateRenderState(CrateEntity blockEntity, CrateRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        ctx.itemModelManager().clearAndUpdate(renderState.renderState, blockEntity.getStack(), ItemDisplayContext.FIXED, world, null, (int)blockEntity.getPos().asLong());
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.hasLid = state.get(Properties.OPEN);
            renderState.count = blockEntity.count;
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(CrateRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.push();
        //Render contents
        if (state.hasLid) {
            float range = MathUtil.range(state.count, 0f, 1024, 0f, 0.3f);
            matrices.translate(0.5f, 0.2f+range, 0.5f);
            matrices.scale(0.75f, 0.75f, 0.75f);
            state.renderState.render(matrices, queue, state.light, 0, 0);
        }
        //render lid
        else {
            matrices.translate(0.5f, 1f, 0.5f);
            matrices.scale(1f, 1f, 1f);
            state.renderState.render(matrices, queue, state.light, 0, 0);
        }

        matrices.pop();
    }

}