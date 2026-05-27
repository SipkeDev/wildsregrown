package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.beam.SupportConnected;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class WoodenBeamSupport extends Block implements ITintedBlock {

    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] WEST;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final EnumProperty<SupportConnected> STATE = ModProperties.SUPPORT_STATE;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WoodenBeamSupport(Properties setting){
        super(setting);
        registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(STATE, SupportConnected.WALL).setValue(PAINT, LinSeedPaintable.NONE).setValue(WATERLOGGED, false));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {

        if (state.getValue(WATERLOGGED)) {
            tickView.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return getRelevantState(world, pos, state);
    }

    private BlockState getRelevantState(LevelReader world, BlockPos pos, BlockState state){

        BlockState up = world.getBlockState(pos.above());
        if (!up.isAir() && !(up.getBlock() instanceof WoodenBeamSupport)) {

            BlockState down = world.getBlockState(pos.below());
            if (down.getBlock() instanceof WoodenBeamSupport) {
                return state.setValue(STATE, SupportConnected.BRACKET);
            }else {
                return state.setValue(STATE, SupportConnected.CEIL);
            }
        }
        return state;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, STATE, PAINT, WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection().getOpposite();
        return getRelevantState(context.getLevel(), blockpos, defaultBlockState()).setValue(FACING, direction).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int i = state.getValue(STATE).ordinal();
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH -> {return NORTH[i];}
            case SOUTH  -> {return SOUTH[i];}
            case EAST  -> {return EAST[i];}
            case WEST  -> {return WEST[i];}
            default -> {return NORTH[0];}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        NORTH = new VoxelShape[3];
        NORTH[0] = VoxelTransform.rotate180(Shapes.box(0.25, 0, 0, 0.75, 1, 0.5));
        NORTH[1] = VoxelTransform.rotate180(Shapes.or(Shapes.box(0.25, 0, 0, 0.75, 1, 0.5), Shapes.box(0.25, 0.5, 0.5, 0.75, 1, 1)));
        NORTH[2] = VoxelTransform.rotate90(Shapes.box(0.25, 0.5, 0, 0.75, 1, 1));
        SOUTH = new VoxelShape[3];
        SOUTH[0] = VoxelTransform.rotate180(NORTH[0]);
        SOUTH[1] = VoxelTransform.rotate180(NORTH[1]);
        SOUTH[2] = VoxelTransform.rotate180(NORTH[2]);
        EAST = new VoxelShape[3];
        EAST[0] = VoxelTransform.rotate90(NORTH[0]);
        EAST[1] = VoxelTransform.rotate90(NORTH[1]);
        EAST[2] = VoxelTransform.rotate90(NORTH[2]);
        WEST = new VoxelShape[3];
        WEST[0] = VoxelTransform.rotate270(NORTH[0]);
        WEST[1] = VoxelTransform.rotate270(NORTH[1]);
        WEST[2] = VoxelTransform.rotate270(NORTH[2]);
    }

}
