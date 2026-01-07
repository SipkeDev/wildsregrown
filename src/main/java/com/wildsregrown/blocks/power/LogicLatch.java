package com.wildsregrown.blocks.power;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

public class LogicLatch extends AbstractRedstoneGateBlock {
    public static final EnumProperty<LatchMode> MODE = EnumProperty.of("mode", LatchMode.class);;

    public LogicLatch(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(MODE, LatchMode.T).with(Properties.LOCKED, false));
    }

    @Override
    protected MapCodec<? extends AbstractRedstoneGateBlock> getCodec() {
        return null;
    }

    protected int getUpdateDelayInternal(BlockState state) {
        return 2;
    }

    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    private boolean mainPower(World world, BlockPos pos, BlockState state) {
        return getPower(world, pos, state) > 0;
    }

    private boolean resetPower(World world, BlockPos pos, BlockState state) {
        boolean side1 = getPower(world, pos, state.with(FACING, state.get(FACING).rotateYClockwise())) > 0;
        boolean side2 = getPower(world, pos, state.with(FACING, state.get(FACING).rotateYCounterclockwise())) > 0;

        return side1 || side2;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else {
            state = state.cycle(MODE);
            world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, state.get(MODE) == LatchMode.RS ? 0.55F : 0.5F);
            world.setBlockState(pos, state, 2);
            this.update(world, pos, state);
            return ActionResult.PASS;//return ActionResult.success(world.isClient);
        }
    }

    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (!world.getBlockTickScheduler().isTicking(pos, this)) {
            world.scheduleBlockTick(pos, this, 2, TickPriority.NORMAL);
        }
    }

    private void update(World world, BlockPos pos, BlockState state) {
        boolean main = mainPower(world, pos, state);
        boolean powered = state.get(POWERED);
        boolean locked = state.get(Properties.LOCKED);

        if (state.get(MODE) == LatchMode.T) {
            if (main && !locked) {
                world.setBlockState(pos, state.with(POWERED, !powered).with(Properties.LOCKED, true), 2);
            } else if (!main && locked) {
                world.setBlockState(pos, state.with(Properties.LOCKED, false), 2);
            }
        } else {
            boolean reset = resetPower(world, pos, state);
            if (reset && powered) {
                world.setBlockState(pos, state.with(POWERED, false), 2);
            } else if (main && !reset && !powered) {
                world.setBlockState(pos, state.with(POWERED, true), 2);
            }

        }


        this.updateTarget(world, pos, state);
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.update(world, pos, state);
    }



    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, POWERED, Properties.LOCKED);
    }

    public enum LatchMode implements StringIdentifiable {
        RS("rs"),
        T("t");

        private final String name;

        private LatchMode(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String asString() {
            return this.name;
        }
    }
}
