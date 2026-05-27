package com.wildsregrown.blocks.carpentry.tree;

import static com.wildsregrown.blocks.properties.ModProperties.FRUITING;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import wildsregrown.api.block.tree.Leaves;

public class FruitingLeaves extends Leaves {
    private final Item fruit;

    public FruitingLeaves(Properties settings, Item fruit) {
        super(settings);
        this.fruit = fruit;
        this.registerDefaultState(this.defaultBlockState().setValue(FRUITING, 0));
    }

    @Override
    protected InteractionResult useWithoutItem(final BlockState state, final Level world, final BlockPos pos, final Player player, final BlockHitResult hit) {
        if (state.getValue(FRUITING) == 3) {
            player.handleExtraItemsCreatedOnUse(new ItemStack(fruit, 1));
            player.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,1f,1f);
            world.setBlockAndUpdate(pos, state.setValue(FRUITING,0));
        }
        return super.useWithoutItem(state, world, pos, player, hit);
    }

    @Override
    public BlockState playerWillDestroy(final Level world, final BlockPos pos, final BlockState state, final Player player) {
        if (state.getValue(FRUITING) == 3) {
            world.addFreshEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(fruit)));
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected boolean isRandomlyTicking(final BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(final BlockState state, final ServerLevel world, final BlockPos pos, final RandomSource random) {
        final int fruiting = state.getValue(FRUITING);
        if (fruiting >= 3) {
            return;
        }
        world.setBlockAndUpdate(pos, state.setValue(FRUITING, fruiting+1));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FRUITING);
    }
}
