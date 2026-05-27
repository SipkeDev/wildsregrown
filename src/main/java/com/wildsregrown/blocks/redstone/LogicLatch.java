package com.wildsregrown.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;

public class LogicLatch extends DiodeBlock {
    public static final EnumProperty<LatchMode> MODE = EnumProperty.create("mode", LatchMode.class);;

    public LogicLatch(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(MODE, LatchMode.T).setValue(BlockStateProperties.LOCKED, false));
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return null;
    }

    protected int getDelay(BlockState state) {
        return 2;
    }

    protected int getOutputSignal(BlockGetter world, BlockPos pos, BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    private boolean mainPower(Level world, BlockPos pos, BlockState state) {
        return getInputSignal(world, pos, state) > 0;
    }

    private boolean resetPower(Level world, BlockPos pos, BlockState state) {
        boolean side1 = getInputSignal(world, pos, state.setValue(FACING, state.getValue(FACING).getClockWise())) > 0;
        boolean side2 = getInputSignal(world, pos, state.setValue(FACING, state.getValue(FACING).getCounterClockWise())) > 0;

        return side1 || side2;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(MODE);
            world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, state.getValue(MODE) == LatchMode.RS ? 0.55F : 0.5F);
            world.setBlock(pos, state, 2);
            this.update(world, pos, state);
            return InteractionResult.PASS;//return ActionResult.success(world.isClient);
        }
    }

    protected void checkTickOnNeighbor(Level world, BlockPos pos, BlockState state) {
        if (!world.getBlockTicks().willTickThisTick(pos, this)) {
            world.scheduleTick(pos, this, 2, TickPriority.NORMAL);
        }
    }

    private void update(Level world, BlockPos pos, BlockState state) {
        boolean main = mainPower(world, pos, state);
        boolean powered = state.getValue(POWERED);
        boolean locked = state.getValue(BlockStateProperties.LOCKED);

        if (state.getValue(MODE) == LatchMode.T) {
            if (main && !locked) {
                world.setBlock(pos, state.setValue(POWERED, !powered).setValue(BlockStateProperties.LOCKED, true), 2);
            } else if (!main && locked) {
                world.setBlock(pos, state.setValue(BlockStateProperties.LOCKED, false), 2);
            }
        } else {
            boolean reset = resetPower(world, pos, state);
            if (reset && powered) {
                world.setBlock(pos, state.setValue(POWERED, false), 2);
            } else if (main && !reset && !powered) {
                world.setBlock(pos, state.setValue(POWERED, true), 2);
            }

        }


        this.updateNeighborsInFront(world, pos, state);
    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.update(world, pos, state);
    }



    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, POWERED, BlockStateProperties.LOCKED);
    }

    public enum LatchMode implements StringRepresentable {
        RS("rs"),
        T("t");

        private final String name;

        private LatchMode(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
