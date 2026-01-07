package com.wildsregrown.blocks.dungeon;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StructureBlock extends BlockWithEntity implements OperatorBlock {

    public static final MapCodec<StructureBlock> CODEC = createCodec(StructureBlock::new);

    public MapCodec<StructureBlock> getCodec() {
        return CODEC;
    }

    public StructureBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState());
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StructureEntity(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof StructureEntity entity) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                entity.openScreen(serverPlayer);
            }
            return ActionResult.SUCCESS;
        } else {
            return ActionResult.PASS;
        }
    }

}