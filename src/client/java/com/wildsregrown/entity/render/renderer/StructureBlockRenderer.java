package com.wildsregrown.entity.render.renderer;

import com.wildsregrown.blocks.dungeon.StructureEntity;
import com.wildsregrown.entity.render.renderstates.StructureBlockRenderState;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DrawStyle;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexRendering;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.debug.gizmo.GizmoDrawing;
import org.jspecify.annotations.Nullable;

public class StructureBlockRenderer implements BlockEntityRenderer<StructureEntity, StructureBlockRenderState> {

    private final BlockEntityRendererFactory.Context ctx;

    public StructureBlockRenderer(BlockEntityRendererFactory.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public StructureBlockRenderState createRenderState() {
        return new StructureBlockRenderState();
    }

    @Override
    public void updateRenderState(StructureEntity blockEntity, StructureBlockRenderState renderState, float tickProgress, Vec3d cameraPos, ModelCommandRenderer.@Nullable CrumblingOverlayCommand crumblingOverlay) {
        World world = blockEntity.getWorld();
        BlockEntityRenderer.super.updateRenderState(blockEntity, renderState, tickProgress, cameraPos, crumblingOverlay);

        BlockState state = world.getBlockState(blockEntity.getPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.light = world.getLightLevel(blockEntity.getPos());
            renderState.showOutline = blockEntity.doesShow();
            renderState.x0 = blockEntity.getX0();
            renderState.y0 = blockEntity.getY0();
            renderState.z0 = blockEntity.getZ0();
            renderState.x1 = blockEntity.getX1();
            renderState.y1 = blockEntity.getY1();
            renderState.z1 = blockEntity.getZ1();
        }
    }

    @Override
    public void render(StructureBlockRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (MinecraftClient.getInstance().player.isCreativeLevelTwoOp() || MinecraftClient.getInstance().player.isSpectator()) {
            if (state.showOutline) {

                int x0 = state.x0, x1 = state.x1;
                int y0 = state.y0, y1 = state.y1;
                int z0 = state.z0, z1 = state.z1;

                GizmoDrawing.box((new Box(x0, y0, z0, x1, y1, z1)).offset(state.pos), DrawStyle.stroked(ColorHelper.fromFloats(1.0F, 0.9F, 0.9F, 0.9F)), true);

            }
        }
    }

}
