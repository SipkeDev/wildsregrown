package com.wildsregrown.blocks;

import com.google.common.collect.Maps;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.Orientation;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.DirectionTransformation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class QuarterStairs extends Block implements Waterloggable {

    private static final Map<Orientation, VoxelShape[]> UP;
    private static final Map<Orientation, VoxelShape[]> DOWN;
    private static final Map<Orientation, VoxelShape[]> NORTH;
    private static final Map<Orientation, VoxelShape[]> EAST;
    private static final Map<Orientation, VoxelShape[]> SOUTH;
    private static final Map<Orientation, VoxelShape[]> WEST;
    private static final IntProperty LAYERS = ModProperties.QUARTER_LAYERS;
    private static final EnumProperty<Direction> FACING = Properties.FACING;
    private static final EnumProperty<Orientation> ORIENTATION = ModProperties.ORIENTATION;
    private static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    public QuarterStairs(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(ORIENTATION, Orientation.UP).with(FACING, Direction.UP).with(LAYERS, 1).with(WATERLOGGED, false));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 4) {
            return context.canReplaceExisting();
        }
        return false;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (player.isSneaking() && state.get(LAYERS) != 1) {
            world.setBlockState(pos, state.with(LAYERS, state.get(LAYERS) - 1));
        } else {
            super.afterBreak(world, player, pos, state, blockEntity, tool);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (state.isOf(this)){
            int i = state.get(LAYERS);
            if (i != 4){
                return state.with(LAYERS, i+1);
            }
        }
        return getDefaultState().with(ORIENTATION, getOrientation(ctx)).with(FACING, ctx.getSide().getOpposite());
    }

    private static Orientation getOrientation(ItemPlacementContext ctx) {

        float t = 0.25f;
        Vec3d centerPos = ctx.getBlockPos().toCenterPos();
        Vec3d vec = centerPos.subtract(ctx.getHitPos());
        Direction side = ctx.getSide();
        switch (side.getAxis()){
            case Y ->{
                if (vec.z > t) {
                    return Orientation.UP;
                }else if (vec.z < -t){
                    return Orientation.DOWN;
                }else {
                    if (vec.x > 0) {
                        return Orientation.LEFT;
                    } else {
                        return Orientation.RIGHT;
                    }
                }
            }
            case X ->{
                if (vec.y > t) {
                    return Orientation.DOWN;
                }else if (vec.y < -t){
                    return Orientation.UP;
                }else {
                    if (side.getDirection() == Direction.AxisDirection.POSITIVE) {
                        if (vec.z > 0) {
                            return Orientation.LEFT;
                        } else {
                            return Orientation.RIGHT;
                        }
                    } else {
                        if (vec.z > 0) {
                            return Orientation.RIGHT;
                        } else {
                            return Orientation.LEFT;
                        }
                    }
                }
            }
            case Z ->{
                if (vec.y > t) {
                    return Orientation.DOWN;
                }else if (vec.y < -t){
                    return Orientation.UP;
                }else {
                    if (side.getDirection() == Direction.AxisDirection.POSITIVE) {
                        if (vec.x > 0) {
                            return Orientation.LEFT;
                        } else {
                            return Orientation.RIGHT;
                        }
                    }else {
                        if (vec.x > 0) {
                            return Orientation.RIGHT;
                        } else {
                            return Orientation.LEFT;
                        }
                    }
                }
            }
        }
        return null;

    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Orientation or = state.get(ORIENTATION);
        int i = state.get(LAYERS)-1;
        switch (state.get(FACING)){
            case UP -> {return UP.get(or)[i];}
            case DOWN -> {return DOWN.get(or)[i];}
            case NORTH -> {return NORTH.get(or)[i];}
            case EAST -> {return EAST.get(or)[i];}
            case SOUTH -> {return SOUTH.get(or)[i];}
            case WEST -> {return WEST.get(or)[i];}
        }
        return VoxelShapes.fullCube();
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, ORIENTATION, LAYERS, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    static {
        VoxelShape downShape0 = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1),
                VoxelShapes.cuboid(0, 0.125, 0.5, 1, 0.25, 1)
        );
        VoxelShape downShape1 = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.375, 1),
                VoxelShapes.cuboid(0, 0.375, 0.5, 1, 0.5, 1)
        );
        VoxelShape downShape2 = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.625, 1),
                VoxelShapes.cuboid(0, 0.625, 0.5, 1, 0.75, 1)
        );
        VoxelShape downShape3 = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.875, 0.5, 1, 1, 1)
        );
        DOWN = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{downShape0, downShape1, downShape2, downShape3},
                Orientation.DOWN, new VoxelShape[]{VoxelTransform.rotate180(downShape0), VoxelTransform.rotate180(downShape1), VoxelTransform.rotate180(downShape2), VoxelTransform.rotate180(downShape3)},
                Orientation.LEFT, new VoxelShape[]{VoxelTransform.rotate270(downShape0), VoxelTransform.rotate270(downShape1), VoxelTransform.rotate270(downShape2), VoxelTransform.rotate270(downShape3)},
                Orientation.RIGHT, new VoxelShape[]{VoxelTransform.rotate90(downShape0), VoxelTransform.rotate90(downShape1), VoxelTransform.rotate90(downShape2), VoxelTransform.rotate90(downShape3)}
        ));
        VoxelShape upShape0 = VoxelTransform.mirrorY(downShape0);
        VoxelShape upShape1 = VoxelTransform.mirrorY(downShape1);
        VoxelShape upShape2 = VoxelTransform.mirrorY(downShape2);
        VoxelShape upShape3 = VoxelTransform.mirrorY(downShape3);
        UP = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{VoxelTransform.rotate180(upShape0), VoxelTransform.rotate180(upShape1), VoxelTransform.rotate180(upShape2), VoxelTransform.rotate180(upShape3)},
                Orientation.DOWN, new VoxelShape[]{upShape0, upShape1, upShape2, upShape3},
                Orientation.LEFT, new VoxelShape[]{VoxelTransform.rotate90(upShape0), VoxelTransform.rotate90(upShape1), VoxelTransform.rotate90(upShape2), VoxelTransform.rotate90(upShape3)},
                Orientation.RIGHT, new VoxelShape[]{VoxelTransform.rotate270(upShape0), VoxelTransform.rotate270(upShape1), VoxelTransform.rotate270(upShape2), VoxelTransform.rotate270(upShape3)}
        ));
        VoxelShape[] up = new VoxelShape[]{
                VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.125),
                        VoxelShapes.cuboid(0, 0, 0.125, 1, 0.5, 0.25)
                ),
                VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.375),
                        VoxelShapes.cuboid(0, 0, 0.375, 1, 0.5, 0.5)
                ),
                VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.625),
                        VoxelShapes.cuboid(0, 0, 0.625, 1, 0.5, 0.75)
                ),
                VoxelShapes.union(
                        VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.875),
                        VoxelShapes.cuboid(0, 0, 0.875, 1, 0.5, 1)
                )
        };
        VoxelShape[] down = new VoxelShape[]{
                VoxelTransform.mirrorY(up[0]),
                VoxelTransform.mirrorY(up[1]),
                VoxelTransform.mirrorY(up[2]),
                VoxelTransform.mirrorY(up[3])
        };
        VoxelShape[] left = new VoxelShape[]{
                VoxelShapes.transform(up[0], DirectionTransformation.ROT_90_Z_POS),
                VoxelShapes.transform(up[1], DirectionTransformation.ROT_90_Z_POS),
                VoxelShapes.transform(up[2], DirectionTransformation.ROT_90_Z_POS),
                VoxelShapes.transform(up[3], DirectionTransformation.ROT_90_Z_POS)
        };
        VoxelShape[] right = new VoxelShape[]{
                VoxelTransform.mirrorX(left[0]),
                VoxelTransform.mirrorX(left[1]),
                VoxelTransform.mirrorX(left[2]),
                VoxelTransform.mirrorX(left[3])
        };
        NORTH = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{up[0], up[1], up[2], up[3]},
                Orientation.DOWN, new VoxelShape[]{down[0], down[1], down[2], down[3]},
                Orientation.LEFT, new VoxelShape[]{left[0], left[1], left[2], left[3]},
                Orientation.RIGHT, new VoxelShape[]{right[0],right[1],right[2],right[3]}
        ));
        EAST = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{VoxelTransform.rotate90(up[0]), VoxelTransform.rotate90(up[1]), VoxelTransform.rotate90(up[2]), VoxelTransform.rotate90(up[3])},
                Orientation.DOWN, new VoxelShape[]{VoxelTransform.rotate90(down[0]), VoxelTransform.rotate90(down[1]), VoxelTransform.rotate90(down[2]), VoxelTransform.rotate90(down[3])},
                Orientation.LEFT, new VoxelShape[]{VoxelTransform.rotate90(right[0]), VoxelTransform.rotate90(right[1]), VoxelTransform.rotate90(right[2]), VoxelTransform.rotate90(right[3])},
                Orientation.RIGHT, new VoxelShape[]{VoxelTransform.rotate90(left[0]),VoxelTransform.rotate90(left[1]),VoxelTransform.rotate90(left[2]),VoxelTransform.rotate90(left[3])}
        ));
        SOUTH = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{VoxelTransform.rotate180(down[0]), VoxelTransform.rotate180(down[1]), VoxelTransform.rotate180(down[2]), VoxelTransform.rotate180(down[3])},
                Orientation.DOWN, new VoxelShape[]{VoxelTransform.rotate180(up[0]), VoxelTransform.rotate180(up[1]), VoxelTransform.rotate180(up[2]), VoxelTransform.rotate180(up[3])},
                Orientation.LEFT, new VoxelShape[]{VoxelTransform.rotate180(right[0]), VoxelTransform.rotate180(right[1]), VoxelTransform.rotate180(right[2]), VoxelTransform.rotate180(right[3])},
                Orientation.RIGHT, new VoxelShape[]{VoxelTransform.rotate180(left[0]),VoxelTransform.rotate180(left[1]),VoxelTransform.rotate180(left[2]),VoxelTransform.rotate180(left[3])}
        ));
        WEST = Maps.newEnumMap(Map.of(
                Orientation.UP, new VoxelShape[]{VoxelTransform.rotate270(up[0]), VoxelTransform.rotate270(up[1]), VoxelTransform.rotate270(up[2]), VoxelTransform.rotate270(up[3])},
                Orientation.DOWN, new VoxelShape[]{VoxelTransform.rotate270(down[0]), VoxelTransform.rotate270(down[1]), VoxelTransform.rotate270(down[2]), VoxelTransform.rotate270(down[3])},
                Orientation.LEFT, new VoxelShape[]{VoxelTransform.rotate270(right[0]), VoxelTransform.rotate270(right[1]), VoxelTransform.rotate270(right[2]), VoxelTransform.rotate270(right[3])},
                Orientation.RIGHT, new VoxelShape[]{VoxelTransform.rotate270(left[0]),VoxelTransform.rotate270(left[1]),VoxelTransform.rotate270(left[2]),VoxelTransform.rotate270(left[3])}
        ));
    }

}
