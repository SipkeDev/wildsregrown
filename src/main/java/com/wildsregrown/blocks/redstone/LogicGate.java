//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;

public class LogicGate extends DiodeBlock {
    public static final EnumProperty<GateMode> MODE = EnumProperty.create("mode", GateMode.class);;

    public LogicGate(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(POWERED, false).setValue(MODE, GateMode.AND));
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

    protected boolean shouldTurnOn(Level world, BlockPos pos, BlockState state) {
        boolean side1 = getInputSignal(world, pos, state.setValue(FACING, state.getValue(FACING).getClockWise())) > 0;
        boolean side2 = getInputSignal(world, pos, state.setValue(FACING, state.getValue(FACING).getCounterClockWise())) > 0;

        if ((state.getValue(MODE) == GateMode.AND
            && side1
            && side2)
            ||
            state.getValue(MODE)== GateMode.XOR
            &&(side1
            != side2))
        {
            return true;
        }
        return false;
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            state = state.cycle(MODE);
            world.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, state.getValue(MODE) == GateMode.XOR ? 0.55F : 0.5F);
            world.setBlock(pos, state, 2);
            this.update(world, pos, state);
            return InteractionResult.PASS;
        }
    }

    protected void checkTickOnNeighbor(Level world, BlockPos pos, BlockState state) {
        if (!world.getBlockTicks().willTickThisTick(pos, this)) {
                world.scheduleTick(pos, this, 2, TickPriority.NORMAL);
        }
    }

    private void update(Level world, BlockPos pos, BlockState state) {
            boolean bl = this.shouldTurnOn(world, pos, state);
            boolean bl2 = state.getValue(POWERED);
            if (bl2 && !bl) {
                world.setBlock(pos, state.setValue(POWERED, false), 2);
            } else if (!bl2 && bl) {
                world.setBlock(pos, state.setValue(POWERED, true), 2);
            }
            this.updateNeighborsInFront(world, pos, state);

    }

    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.update(world, pos, state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, POWERED);
    }

    public enum GateMode implements StringRepresentable {
        AND("and"),
        XOR("xor");

        private final String name;

        private GateMode(String name) {
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
