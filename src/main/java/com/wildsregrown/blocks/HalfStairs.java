package com.wildsregrown.blocks;

import com.wildsregrown.blocks.abstracts.CornerConnectingBlock;
import com.wildsregrown.blocks.properties.HalfStair;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.StairShape;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.Map;

public class HalfStairs extends CornerConnectingBlock {

    private static final Map<Direction, VoxelShape> OUTER_TOP_SHAPES;
    private static final Map<Direction, VoxelShape> OUTER_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> STRAIGHT_TOP_SHAPES;
    private static final Map<Direction, VoxelShape> STRAIGHT_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> INNER_TOP_SHAPES;
    private static final Map<Direction, VoxelShape> INNER_BOTTOM_SHAPES;
    private static final Map<Direction, VoxelShape> OUTER_TOP_SHAPES_INVERTED;
    private static final Map<Direction, VoxelShape> OUTER_BOTTOM_SHAPES_INVERTED;
    private static final Map<Direction, VoxelShape> STRAIGHT_TOP_SHAPES_INVERTED;
    private static final Map<Direction, VoxelShape> STRAIGHT_BOTTOM_SHAPES_INVERTED;
    private static final Map<Direction, VoxelShape> INNER_TOP_SHAPES_INVERTED;
    private static final Map<Direction, VoxelShape> INNER_BOTTOM_SHAPES_INVERTED;
    private static final EnumProperty<HalfStair> HALF = ModProperties.HALF;
    private static final EnumProperty<StairShape> SHAPE = Properties.STAIR_SHAPE;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public HalfStairs(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(HALF, HalfStair.BOTTOM).with(SHAPE, StairShape.STRAIGHT).with(FACING, Direction.NORTH));
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        HalfStair half = state.get(HALF);
        boolean bottom = half == HalfStair.BOTTOM || half == HalfStair.BOTTOM_INVERT;
        boolean inverted = half == HalfStair.BOTTOM_INVERT || half == HalfStair.TOP_INVERT;
        Direction direction = state.get(FACING);
        Map map;
        switch (state.get(SHAPE)) {
            case STRAIGHT:
                if (inverted){
                    map = bottom ? STRAIGHT_BOTTOM_SHAPES_INVERTED : STRAIGHT_TOP_SHAPES_INVERTED;
                }else {
                    map = bottom ? STRAIGHT_BOTTOM_SHAPES : STRAIGHT_TOP_SHAPES;
                }
                break;
            case OUTER_LEFT:
            case OUTER_RIGHT:
                if(inverted){
                    map = bottom ? OUTER_BOTTOM_SHAPES_INVERTED : OUTER_TOP_SHAPES_INVERTED;
                }else {
                    map = bottom ? OUTER_BOTTOM_SHAPES : OUTER_TOP_SHAPES;
                }
                break;
            case INNER_RIGHT:
            case INNER_LEFT:
                if (inverted){
                    map = bottom ? INNER_BOTTOM_SHAPES_INVERTED : INNER_TOP_SHAPES_INVERTED;
                }else {
                    map = bottom ? INNER_BOTTOM_SHAPES : INNER_TOP_SHAPES;
                }
                break;
            default:
                throw new MatchException(null, null);
        }

        Direction dir;
        switch (state.get(SHAPE)) {
            case STRAIGHT:
            case OUTER_LEFT:
            case INNER_RIGHT:
                dir = direction;
                break;
            case INNER_LEFT:
                dir = direction.rotateYCounterclockwise();
                break;
            case OUTER_RIGHT:
                dir = direction.rotateYClockwise();
                break;
            default:
                throw new MatchException(null, null);
        }

        return (VoxelShape)map.get(dir);
    }

    @Override
    protected boolean canConnectToBlock(BlockState blockState) {
        return blockState.getBlock() instanceof HalfStairs;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        if (context.getStack().isOf(this.asItem())) {
            HalfStair i = state.get(HALF);
            if (i == HalfStair.BOTTOM || i == HalfStair.BOTTOM_INVERT) {
                return context.canReplaceExisting();
            }
        }
        return false;
    }

    @Override
    public StairShape getPartProperty(BlockState state, WorldView world, BlockPos pos){

        Direction direction = state.get(FACING);

        //Check outer
        BlockState newState0 = world.getBlockState(pos.offset(direction));
        if (canConnectToBlock(newState0)) {
            if (newState0.get(HALF) == state.get(HALF)) {
                Direction newDir0 = newState0.get(FACING);
                if (newDir0.getAxis() != state.get(FACING).getAxis() && isDifferentOrientation(state, world, pos, newDir0.getOpposite())) {
                    if (newDir0 == direction.rotateYCounterclockwise()) {
                        return StairShape.OUTER_LEFT;
                    }

                    return StairShape.OUTER_RIGHT;
                }
            }
        }
        //check inner
        BlockState newState1 = world.getBlockState(pos.offset(direction.getOpposite()));
        if (canConnectToBlock(newState1)) {
            if (newState1.get(HALF) == state.get(HALF)) {
                Direction newDir1 = newState1.get(FACING);
                if (newDir1.getAxis() != state.get(FACING).getAxis() && isDifferentOrientation(state, world, pos, newDir1)) {
                    if (newDir1 == direction.rotateYCounterclockwise()) {
                        return StairShape.INNER_LEFT;
                    }

                    return StairShape.INNER_RIGHT;
                }
            }
        }

        return StairShape.STRAIGHT;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)){
            if (blockState.get(HALF) == HalfStair.BOTTOM){
                return blockState.with(HALF, HalfStair.TOP);
            } else if (blockState.get(HALF) == HalfStair.BOTTOM_INVERT) {
                return blockState.with(HALF, HalfStair.TOP_INVERT);
            }
        }

        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);
        BlockState predicate = this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()).with(Properties.WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return predicate.with(HALF, isInverted(ctx)).with(SHAPE, getPartProperty(predicate, ctx.getWorld(), blockPos));
    }

    private HalfStair isInverted(ItemPlacementContext context){
        if (context.getVerticalPlayerLookDirection() == Direction.DOWN){
            return HalfStair.BOTTOM;
        }else {
            return HalfStair.BOTTOM_INVERT;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(HALF, SHAPE, FACING, Properties.WATERLOGGED);
    }

    public static EnumProperty<StairShape> getSHAPE() {
        return SHAPE;
    }

    static {
        VoxelShape OUTER_SHAPE_TOP = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.75, 1),
                VoxelShapes.cuboid(0, 0.75, 0, 0.5, 1, 0.5)
        );
        VoxelShape OUTER_SHAPE_BOTTOM = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1),
                VoxelShapes.cuboid(0, 0.25, 0, 0.5, 0.5, 0.5)
        );
        VoxelShape STRAIGHT_SHAPE_TOP = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.75, 1),
                VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 0.5)
        );
        VoxelShape STRAIGHT_SHAPE_BOTTOM = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1),
                VoxelShapes.cuboid(0, 0.25, 0, 1, 0.5, 0.5)
        );
        VoxelShape INNER_SHAPE_TOP = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.75, 1),
                VoxelShapes.cuboid(0.5, 0.75, 0, 1, 1, 1),
                VoxelShapes.cuboid(0, 0.75, 0, 0.5, 1, 0.5)
        );
        VoxelShape INNER_SHAPE_BOTTOM = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.25, 1),
                VoxelShapes.cuboid(0.5, 0.25, 0, 1, 0.5, 1),
                VoxelShapes.cuboid(0, 0.25, 0, 0.5, 0.5, 0.5)
        );
        OUTER_TOP_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(OUTER_SHAPE_TOP);
        OUTER_BOTTOM_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(OUTER_SHAPE_BOTTOM);
        STRAIGHT_TOP_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(STRAIGHT_SHAPE_TOP);
        STRAIGHT_BOTTOM_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(STRAIGHT_SHAPE_BOTTOM);
        INNER_TOP_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(INNER_SHAPE_TOP);
        INNER_BOTTOM_SHAPES = VoxelShapes.createHorizontalFacingShapeMap(INNER_SHAPE_BOTTOM);
        OUTER_TOP_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(OUTER_SHAPE_BOTTOM, DirectionTransformation.INVERT_Y));
        OUTER_BOTTOM_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(OUTER_SHAPE_TOP, DirectionTransformation.INVERT_Y));
        STRAIGHT_TOP_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(STRAIGHT_SHAPE_TOP, DirectionTransformation.INVERT_Y));
        STRAIGHT_BOTTOM_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(STRAIGHT_SHAPE_BOTTOM, DirectionTransformation.INVERT_Y));
        INNER_TOP_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(INNER_SHAPE_TOP, DirectionTransformation.INVERT_Y));
        INNER_BOTTOM_SHAPES_INVERTED = VoxelShapes.createHorizontalFacingShapeMap(VoxelShapes.transform(INNER_SHAPE_BOTTOM, DirectionTransformation.INVERT_Y));
    }

}
