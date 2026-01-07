package com.wildsregrown.blocks;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class StoneBlock extends Layered{

    public StoneBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.afterBreak(world, player, pos, state, blockEntity, tool);
        BlockState up = world.getBlockState(pos.up());
        if (up.getBlock() instanceof StoneBlock block){
            if (up.get(ModProperties.LAYERS) < 2){
                world.breakBlock(pos.up(), true, player, 1);
            }
        }
    }

}
