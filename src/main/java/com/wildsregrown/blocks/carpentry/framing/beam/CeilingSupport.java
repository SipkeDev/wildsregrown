package com.wildsregrown.blocks.carpentry.framing.beam;

import com.google.common.collect.Maps;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.beam.SupportCeiling;
import com.wildsregrown.blocks.properties.framing.beam.SupportPost;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;

public class CeilingSupport extends AbstractSupport {

    private static final Map<SupportCeiling, VoxelShape> shapes;
    public static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    public static final EnumProperty<SupportCeiling> SHAPE = EnumProperty.create("shape", SupportCeiling.class);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public CeilingSupport(String id, Properties settings) {
        super(id, settings);
        this.registerDefaultState(this.defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(SHAPE, SupportCeiling.STRAIGHT).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT, SHAPE, WATERLOGGED);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (context.getItemInHand().getItem() instanceof FramingBeamItem beamItem) {
            return isOfPost(beamItem.getBlock(context.getItemInHand()));
        }
        return super.canBeReplaced(state, context);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState currentState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (currentState.getBlock() instanceof PostSupport beamPostSupport){
            BlockState state = beamPostSupport.getStateForPlacement(ctx);
            WildsRegrown.LOGGER.info("Ceiling placement on post");
            return state.setValue(PostSupport.SHAPE, SupportPost.CEILING_BRACKET);
        }

        BlockState state = this.defaultBlockState()
                .setValue(SHAPE, ctx.getHorizontalDirection().getAxis() == Direction.Axis.Z
                        ? SupportCeiling.STRAIGHT : SupportCeiling.STRAIGHT_ROTATED);
        return getShape(state, ctx.getLevel(), ctx.getClickedPos());
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return getShape(state, world, pos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.above());
        if (upState.hasProperty(WRGProperties.LAYERS) && upState.hasProperty(BlockStateProperties.FACING)){
            if (upState.getValue(BlockStateProperties.FACING) == Direction.UP){
                return true;
            }else return upState.getValue(WRGProperties.LAYERS) == 8;
        }
        return super.canSurvive(state, world, pos);
    }

    private static BlockState getShape(BlockState state, LevelReader world, BlockPos pos) {
        //states
        BlockState north = world.getBlockState(pos.north());
        BlockState east = world.getBlockState(pos.east());
        BlockState south = world.getBlockState(pos.south());
        BlockState west = world.getBlockState(pos.west());
        //Is connecting?
        boolean isNorth = isOfSupport(north);
        boolean isEast = isOfSupport(east);
        boolean isSouth = isOfSupport(south);
        boolean isWest = isOfSupport(west);

        //How many are connecting types?
        int connected = 0;

        if (isNorth) {connected++;}
        if (isEast) {connected++;}
        if (isSouth) {connected++;}
        if (isWest) {connected++;}

        if (connected == 4) {
            return state.setValue(SHAPE, SupportCeiling.CROSS);
        } else if (connected == 3) {
            if (isNorth && isEast && isWest) {
                return state.setValue(SHAPE, SupportCeiling.T_JOINT_NORTH);
            }
            if (isEast && isNorth && isSouth) {
                return state.setValue(SHAPE, SupportCeiling.T_JOINT_EAST);
            }
            if (isSouth && isEast && isWest) {
                return state.setValue(SHAPE, SupportCeiling.T_JOINT_SOUTH);
            }
            if (isWest && isNorth && isSouth) {
                return state.setValue(SHAPE, SupportCeiling.T_JOINT_WEST);
            }
        } else if (connected != 0) {
            if (isNorth || isSouth) {
                return state.setValue(SHAPE, SupportCeiling.STRAIGHT);
            } else {
                return state.setValue(SHAPE, SupportCeiling.STRAIGHT_ROTATED);
            }
        }
        return state;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes.get(state.getValue(SHAPE));
    }

    static {
        VoxelShape straight = Shapes.or(
                Shapes.box(0.25, 0.5, 0, 0.75, 1, 1)
        );
        VoxelShape t_joint = VoxelTransform.rotate270(Shapes.or(
                Shapes.box(0.25, 0.5, 0, 0.75, 1, 1),
                Shapes.box(0.75, 0.5, 0.25, 1, 1, 0.75)
        ));
        VoxelShape cross = Shapes.or(
                Shapes.box(0.25, 0.5, 0, 0.75, 1, 1),
                Shapes.box(0, 0.5, 0.25, 1, 1, 0.75)
        );
        shapes = Maps.newEnumMap(Map.of(
                SupportCeiling.STRAIGHT, straight,
                SupportCeiling.STRAIGHT_ROTATED, VoxelTransform.rotate90(straight),
                SupportCeiling.T_JOINT_NORTH, t_joint,
                SupportCeiling.T_JOINT_EAST, VoxelTransform.rotate90(t_joint),
                SupportCeiling.T_JOINT_SOUTH, VoxelTransform.rotate180(t_joint),
                SupportCeiling.T_JOINT_WEST, VoxelTransform.rotate270(t_joint),
                SupportCeiling.CROSS, cross
        ));
    }

}
