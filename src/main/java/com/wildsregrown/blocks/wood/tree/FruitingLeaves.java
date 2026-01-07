package com.wildsregrown.blocks.wood.tree;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import static com.wildsregrown.blocks.properties.ModProperties.FRUITING;

public class FruitingLeaves extends Leaves {
    private final Item fruit;

    public FruitingLeaves(Settings settings, Item fruit) {
        super(settings);
        this.fruit = fruit;
        this.setDefaultState(this.getDefaultState().with(FRUITING, 0));
    }

    @Override
    protected ActionResult onUse(final BlockState state, final World world, final BlockPos pos, final PlayerEntity player, final BlockHitResult hit) {
        if (state.get(FRUITING) == 3) {
            player.giveOrDropStack(new ItemStack(fruit, 1));
            player.playSound(SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES,1f,1f);
            world.setBlockState(pos, state.with(FRUITING,0));
        }
        return super.onUse(state, world, pos, player, hit);
    }

    @Override
    public BlockState onBreak(final World world, final BlockPos pos, final BlockState state, final PlayerEntity player) {
        if (state.get(FRUITING) == 3) {
            world.spawnEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(fruit)));
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected boolean hasRandomTicks(final BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(final BlockState state, final ServerWorld world, final BlockPos pos, final Random random) {
        final int fruiting = state.get(FRUITING);
        if (fruiting >= 3) {
            return;
        }
        world.setBlockState(pos, state.with(FRUITING, fruiting+1));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FRUITING);
    }
}
