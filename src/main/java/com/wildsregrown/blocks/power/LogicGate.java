//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

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
import net.minecraft.util.ActionResult;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

public class LogicGate extends AbstractRedstoneGateBlock {
    public static final EnumProperty<GateMode> MODE = EnumProperty.of("mode", GateMode.class);;

    public LogicGate(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(POWERED, false).with(MODE, GateMode.AND));
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

    protected boolean hasPower(World world, BlockPos pos, BlockState state) {
        boolean side1 = getPower(world, pos, state.with(FACING, state.get(FACING).rotateYClockwise())) > 0;
        boolean side2 = getPower(world, pos, state.with(FACING, state.get(FACING).rotateYCounterclockwise())) > 0;

        if ((state.get(MODE) == GateMode.AND
            && side1
            && side2)
            ||
            state.get(MODE)== GateMode.XOR
            &&(side1
            != side2))
        {
            return true;
        }
        return false;
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        } else {
            state = state.cycle(MODE);
            world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3F, state.get(MODE) == GateMode.XOR ? 0.55F : 0.5F);
            world.setBlockState(pos, state, 2);
            this.update(world, pos, state);
            return ActionResult.PASS;
        }
    }

    protected void updatePowered(World world, BlockPos pos, BlockState state) {
        if (!world.getBlockTickScheduler().isTicking(pos, this)) {
                world.scheduleBlockTick(pos, this, 2, TickPriority.NORMAL);
        }
    }

    private void update(World world, BlockPos pos, BlockState state) {
            boolean bl = this.hasPower(world, pos, state);
            boolean bl2 = state.get(POWERED);
            if (bl2 && !bl) {
                world.setBlockState(pos, state.with(POWERED, false), 2);
            } else if (!bl2 && bl) {
                world.setBlockState(pos, state.with(POWERED, true), 2);
            }
            this.updateTarget(world, pos, state);

    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.update(world, pos, state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, POWERED);
    }

    public enum GateMode implements StringIdentifiable {
        AND("and"),
        XOR("xor");

        private final String name;

        private GateMode(String name) {
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
