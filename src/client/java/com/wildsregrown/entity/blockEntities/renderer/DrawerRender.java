package com.wildsregrown.entity.blockEntities.renderer;

import com.wildsregrown.blocks.properties.DrawerState;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.DrawerEntity;
import com.wildsregrown.entity.blockEntities.renderstates.DrawerRenderState;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DrawerRender implements BlockEntityRenderer<DrawerEntity, DrawerRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public DrawerRender(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    private final float[] x = {0.6f, 0.4f, 0.6f, 0.4f};
    private final float[] z = {0.6f, 0.6f, 0.4f, 0.4f};
    private final float[] y = {0.35f, 0.75f};

    @Override
    public DrawerRenderState createRenderState() {
        return new DrawerRenderState().init();
    }

    @Override
    public void updateRenderState(DrawerEntity blockEntity, DrawerRenderState renderState, float tickProgress, Vec3d cameraPos, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);
        for (int i = 0; i < 8; i++) {
            ctx.itemModelManager().clearAndUpdate(renderState.state[i], blockEntity.getStack(i), ItemDisplayContext.FIXED, world, null, 0);
        }
        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.drawerState = state.get(ModProperties.DRAWER_STATE);
            renderState.facing = state.get(Properties.HORIZONTAL_FACING);
            renderState.light = world.getLightLevel(blockEntity.getPos());
        }
    }

    @Override
    public void render(DrawerRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (renderState.facing != null) {
            int drawer = -1;
            if (renderState.drawerState == DrawerState.OPEN || renderState.drawerState == DrawerState.DRAWER_TOP) {
                drawer = 1;
            }
            if (renderState.drawerState == DrawerState.DRAWER_BOTTOM) {
                drawer = 0;
            }

            if (drawer != -1) {

                Vec3i vector = renderState.facing.getVector();
                renderItem(drawer, 0, renderState, matrices, queue, vector, renderState.light);
                renderItem(drawer, 1, renderState, matrices, queue, vector, renderState.light);
                renderItem(drawer, 2, renderState, matrices, queue, vector, renderState.light);
                renderItem(drawer, 3, renderState, matrices, queue, vector, renderState.light);

            }
        }
    }

    private void renderItem(int drawerFlag, int i, DrawerRenderState renderState, MatrixStack matrices, OrderedRenderCommandQueue queue, Vec3i vector, int light){

        int idx = (drawerFlag*4)+i;
        matrices.push();
        matrices.translate(x[i] + vector.getX()*0.35, y[drawerFlag], z[i] + vector.getZ()*0.35);
        matrices.scale(0.125f, 0.125f, 0.125f);
        renderState.state[idx].render(matrices, queue, renderState.light*17, OverlayTexture.DEFAULT_UV, 0);
        matrices.pop();
    }

}