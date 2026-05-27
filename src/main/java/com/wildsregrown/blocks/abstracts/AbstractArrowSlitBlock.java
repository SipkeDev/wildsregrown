package com.wildsregrown.blocks.abstracts;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.ArrowSlitConnected;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.ticks.ScheduledTick;

public abstract class AbstractArrowSlitBlock extends Block {

    private static final EnumProperty<ArrowSlitConnected> SHAPE = ModProperties.ARROW_SLIT_CONNECTED;

    //Defining Default BlockState
    public AbstractArrowSlitBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setValue(SHAPE, ArrowSlitConnected.SINGLE).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {
        if(isConnectingBlock(currentState)) {
            if (isConnectingBlock(validState) && currentState.getValue(BlockStateProperties.HORIZONTAL_FACING) == validState.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a shape property based from the given blockstate and position
     */
    public ArrowSlitConnected getPartProperty(LevelReader worldIn, BlockPos blockpos) {

        BlockState state = worldIn.getBlockState(blockpos);
        BlockState stateUp = worldIn.getBlockState(blockpos.above());
        BlockState stateDown = worldIn.getBlockState(blockpos.below());

        boolean top = isConnectingBlock(stateDown) && isValidFacing(state, stateDown);
        boolean bot = isConnectingBlock(stateUp) && isValidFacing(state, stateUp);

        if(isValidFacing(state, state)) {
            if (top && bot) {
                return ArrowSlitConnected.MIDDLE;
            } else if (top) {
                return ArrowSlitConnected.TOP;
            } else if (bot) {
                return state.getValue(SHAPE) == ArrowSlitConnected.BOTTOM_FLOOR ? ArrowSlitConnected.BOTTOM_FLOOR : ArrowSlitConnected.BOTTOM;
            }
        }
        return ArrowSlitConnected.SINGLE;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);

        ArrowSlitConnected connected = getPartProperty(context.getLevel(), blockpos);
        if (context.getPlayer().isShiftKeyDown() && isConnectingBlock(context.getLevel().getBlockState(blockpos.above()))){
            connected = ArrowSlitConnected.BOTTOM_FLOOR;
        }

        return defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, context.getHorizontalDirection())
                .setValue(SHAPE, connected)
                .setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER,pos));
        }
        return direction.getAxis().isVertical() ? state.setValue(SHAPE, getPartProperty(world, pos)) : super.updateShape(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING, SHAPE, BlockStateProperties.WATERLOGGED);
    }

}