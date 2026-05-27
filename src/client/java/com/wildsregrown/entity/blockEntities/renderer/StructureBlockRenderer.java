package com.wildsregrown.entity.blockEntities.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.wildsregrown.entity.blockEntities.renderstates.StructureBlockRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.gizmos.GizmoStyle;
import net.minecraft.gizmos.Gizmos;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import wildsregrown.api.entities.StructureEntity;

public class StructureBlockRenderer implements BlockEntityRenderer<StructureEntity, StructureBlockRenderState> {

    private final BlockEntityRendererProvider.Context ctx;

    public StructureBlockRenderer(BlockEntityRendererProvider.Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public StructureBlockRenderState createRenderState() {
        return new StructureBlockRenderState();
    }

    @Override
    public void extractRenderState(StructureEntity blockEntity, StructureBlockRenderState renderState, float tickDelta, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        Level world = blockEntity.getLevel();
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, tickDelta, cameraPos, crumblingOverlay);

        BlockState state = world.getBlockState(blockEntity.getBlockPos());
        if (!(state.getBlock() instanceof AirBlock)) {
            renderState.light = world.getMaxLocalRawBrightness(blockEntity.getBlockPos());
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
    public void submit(StructureBlockRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (Minecraft.getInstance().player.canUseGameMasterBlocks() || Minecraft.getInstance().player.isSpectator()) {
            if (state.showOutline) {

                int x0 = state.x0, x1 = state.x1;
                int y0 = state.y0, y1 = state.y1;
                int z0 = state.z0, z1 = state.z1;

                Gizmos.cuboid((new AABB(x0, y0, z0, x1, y1, z1)).move(state.blockPos), GizmoStyle.stroke(ARGB.colorFromFloat(1.0F, 0.9F, 0.9F, 0.9F)), true);

            }
        }
    }


    public void updateRenderState(StructureEntity blockEntity, StructureBlockRenderState renderState, float tickProgress, Vec3 cameraPos, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {

    }

    public void render(StructureBlockRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {

    }

    @Override
    public boolean shouldRender(StructureEntity blockEntity, Vec3 vec3) {
        return BlockEntityRenderer.super.shouldRender(blockEntity, vec3);
    }

}
