package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.SupportConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class WoodenSupport extends Block implements ITintedBlock {

    private static final VoxelShape[] NORTH;
    private static final VoxelShape[] SOUTH;
    private static final VoxelShape[] EAST;
    private static final VoxelShape[] WEST;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<SupportConnected> STATE = ModProperties.SUPPORT_STATE;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static IntProperty VAR = ModProperties.VARIATIONS_3;
    private final static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public WoodenSupport(Settings setting){
        super(setting);
        setDefaultState(this.getDefaultState().with(FACING, Direction.NORTH).with(STATE, SupportConnected.WALL).with(PAINT, LinSeedPaintable.NONE).with(VAR, 1).with(WATERLOGGED, false));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {

        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return getRelevantState(world, pos, state);
    }

    private BlockState getRelevantState(WorldView world, BlockPos pos, BlockState state){

        BlockState up = world.getBlockState(pos.up());
        if (!up.isAir() && !(up.getBlock() instanceof WoodenSupport)) {

            BlockState down = world.getBlockState(pos.down());
            if (down.getBlock() instanceof WoodenSupport) {
                return state.with(STATE, SupportConnected.BRACKET);
            }else {
                return state.with(STATE, SupportConnected.CEIL);
            }
        }
        return state;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, STATE, PAINT, VAR, WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing().getOpposite();
        return getRelevantState(context.getWorld(), blockpos, getDefaultState()).with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        int i = state.get(STATE).ordinal();
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            case NORTH -> {return NORTH[i];}
            case SOUTH  -> {return SOUTH[i];}
            case EAST  -> {return EAST[i];}
            case WEST  -> {return WEST[i];}
            default -> {return NORTH[0];}
        }
    }

    static {
        NORTH = new VoxelShape[3];
        NORTH[0] = VoxelTransform.rotate180(VoxelShapes.cuboid(0.25, 0, 0, 0.75, 1, 0.5));
        NORTH[1] = VoxelTransform.rotate180(VoxelShapes.union(VoxelShapes.cuboid(0.25, 0, 0, 0.75, 1, 0.5), VoxelShapes.cuboid(0.25, 0.5, 0.5, 0.75, 1, 1)));
        NORTH[2] = VoxelTransform.rotate90(VoxelShapes.cuboid(0.25, 0.5, 0, 0.75, 1, 1));
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
