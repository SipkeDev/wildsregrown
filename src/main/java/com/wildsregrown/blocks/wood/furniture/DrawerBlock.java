package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.HorizontalConnectingBlock;
import com.wildsregrown.blocks.properties.DrawerState;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.DrawerEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DrawerBlock extends HorizontalConnectingBlock implements BlockEntityProvider, ITintedBlock {

    private static final VoxelShape[] SHAPE_CLOSED;
    private static final VoxelShape[] SHAPE_TOP_OPEN;
    private static final VoxelShape[] SHAPE_BOTTOM_OPEN;
    private static final VoxelShape[] SHAPE_OPEN;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<HorizontalConnected> SHAPE = ModProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<DrawerState> DRAWERS = ModProperties.DRAWER_STATE;

    public DrawerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(PAINT, LinSeedPaintable.NONE).with(SHAPE, HorizontalConnected.SINGLE).with(DRAWERS, DrawerState.CLOSED));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        Vec3d offset = pos.toCenterPos().subtract(hit.getPos());

        if (player.isSneaking()) {
            //top drawer
            if (offset.y < -0.1f) {
                return toggleTop(state, world, pos);
            }
            //bottom drawer
            else {
                return toggleBottom(state, world, pos);
            }
        } else {
            if (offset.y < -0.1f) {
                if (state.get(DRAWERS) == DrawerState.OPEN || state.get(DRAWERS) == DrawerState.DRAWER_TOP) {
                    return swapItemStackInInventory(1, state, world, pos, player, hit);
                }
            }else {
                if (state.get(DRAWERS) == DrawerState.DRAWER_BOTTOM) {
                    return swapItemStackInInventory(0, state, world, pos, player, hit);
                }
            }
        }
        return ActionResult.CONSUME;
    }

    private ActionResult swapItemStackInInventory(int drawerFlag, BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        double power = drawerFlag == 0 ? 0.25 : 0.125;
        Vec3d vec = pos.toCenterPos().add(new Vec3d(
                    state.get(FACING).getVector().getX()*power,
                    0,
                    state.get(FACING).getVector().getZ()*power
            ));

        Vec3d offset = vec.subtract(hit.getPos());
        //four quadrants
        boolean q0 = offset.x < 0.0 && offset.z < 0.0;
        boolean q1 = offset.x > 0.0 && offset.z < 0.0;
        boolean q2 = offset.x < 0.0 && offset.z > 0.0;
        boolean q3 = offset.x > 0.0 && offset.z > 0.0;

        if (world.getBlockEntity(pos) instanceof DrawerEntity entity) {
            if (q0) {entity.switchStack(drawerFlag, 0, player);}
            if (q1) {entity.switchStack(drawerFlag, 1, player);}
            if (q2) {entity.switchStack(drawerFlag, 2, player);}
            if (q3) {entity.switchStack(drawerFlag, 3, player);}
        }

        return ActionResult.SUCCESS;
    }

    private ActionResult toggleTop(BlockState state, World world, BlockPos pos){

        if (state.get(DRAWERS) == DrawerState.CLOSED) {
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.DRAWER_TOP));
        }else if (state.get(DRAWERS) == DrawerState.DRAWER_TOP) {
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.CLOSED));
        }else if (state.get(DRAWERS) == DrawerState.DRAWER_BOTTOM){
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.OPEN));
        }else if (state.get(DRAWERS) == DrawerState.OPEN){
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.DRAWER_BOTTOM));
        }
        return ActionResult.SUCCESS;

    }

    private ActionResult toggleBottom(BlockState state, World world, BlockPos pos){

        if (state.get(DRAWERS) == DrawerState.CLOSED) {
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.DRAWER_BOTTOM));
        }else if (state.get(DRAWERS) == DrawerState.DRAWER_BOTTOM){
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.CLOSED));
        }else if (state.get(DRAWERS) == DrawerState.DRAWER_TOP) {
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.OPEN));
        }else if (state.get(DRAWERS) == DrawerState.OPEN){
            world.setBlockState(pos, state.with(DRAWERS, DrawerState.DRAWER_TOP));
        }
        return ActionResult.FAIL;

    }

    /**
     * Vanilla constructors
     * @param builder
     */

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT, SHAPE, DRAWERS, Properties.WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(FACING, context.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DrawerEntity(pos, state);
    }

    /**
     * Get shape
     * @param state
     * @param world
     * @param pos
     * @param context
     * @return
     */

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {return getShape(state, 0);}
            case EAST -> {return getShape(state, 1);}
            case SOUTH -> {return getShape(state, 2);}
            case WEST -> {return getShape(state, 3);}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    private VoxelShape getShape(BlockState state, int part){
        switch (state.get(DRAWERS)){
            case CLOSED ->{return SHAPE_CLOSED[part];}
            case DRAWER_TOP -> {return SHAPE_TOP_OPEN[part];}
            case DRAWER_BOTTOM -> {return SHAPE_BOTTOM_OPEN[part];}
            case OPEN -> {return SHAPE_OPEN[part];}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        SHAPE_CLOSED = new VoxelShape[4];
        SHAPE_CLOSED[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.0625, 0.375, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0.875, 0, 0.875, 1, 0.0625, 1),
                VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.0625, 1),
                VoxelShapes.cuboid(0, 0, 0.375, 0.125, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.375, 1, 0.0625, 0.5),
                VoxelShapes.cuboid(0.375, 0.375, 0.3125, 0.625, 0.4375, 0.375),
                VoxelShapes.cuboid(0.375, 0.6875, 0.3125, 0.625, 0.75, 0.375)
        );
        SHAPE_CLOSED[1] = VoxelTransform.rotate90(SHAPE_CLOSED[0]);
        SHAPE_CLOSED[2] = VoxelTransform.rotate180(SHAPE_CLOSED[0]);
        SHAPE_CLOSED[3] = VoxelTransform.rotate270(SHAPE_CLOSED[0]);

        SHAPE_TOP_OPEN = new VoxelShape[4];
        SHAPE_TOP_OPEN[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.1875, 0.375, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.1875, 0.4375, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.875, 0.1875, 0.375, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0, 0.0625, 0.375, 1, 0.1875, 1),
                VoxelShapes.cuboid(0, 0, 0.375, 0.125, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.375, 1, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.875, 1, 0.0625, 1),
                VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.0625, 1),
                VoxelShapes.cuboid(0.125, 0.25, 0.375, 0.875, 0.5, 0.4375),
                VoxelShapes.cuboid(0.375, 0.375, 0.3125, 0.625, 0.4375, 0.375),
                VoxelShapes.cuboid(0.125, 0.5625, -0.125, 0.875, 0.8125, -0.0625),
                VoxelShapes.cuboid(0.375, 0.6875, -0.1875, 0.625, 0.75, -0.125),
                VoxelShapes.cuboid(0.125, 0.5625, -0.0625, 0.1875, 0.8125, 0.4375),
                VoxelShapes.cuboid(0.8125, 0.5625, -0.0625, 0.875, 0.8125, 0.4375),
                VoxelShapes.cuboid(0.1875, 0.5625, -0.0625, 0.8125, 0.625, 0.4375)
        );
        SHAPE_TOP_OPEN[1] = VoxelTransform.rotate90(SHAPE_TOP_OPEN[0]);
        SHAPE_TOP_OPEN[2] = VoxelTransform.rotate180(SHAPE_TOP_OPEN[0]);
        SHAPE_TOP_OPEN[3] = VoxelTransform.rotate270(SHAPE_TOP_OPEN[0]);

        SHAPE_BOTTOM_OPEN = new VoxelShape[4];
        SHAPE_BOTTOM_OPEN[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.1875, 0.375, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.1875, 0.4375, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.875, 0.1875, 0.375, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0, 0.0625, 0.375, 1, 0.1875, 1),
                VoxelShapes.cuboid(0, 0, 0.375, 0.125, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.375, 1, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.875, 1, 0.0625, 1),
                VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.0625, 1),
                VoxelShapes.cuboid(0.125, 0.5625, 0.375, 0.875, 0.8125, 0.4375),
                VoxelShapes.cuboid(0.375, 0.6875, 0.3125, 0.625, 0.75, 0.375),
                VoxelShapes.cuboid(0.125, 0.25, -0.125, 0.875, 0.5, -0.0625),
                VoxelShapes.cuboid(0.375, 0.375, -0.1875, 0.625, 0.4375, -0.125),
                VoxelShapes.cuboid(0.125, 0.25, -0.0625, 0.1875, 0.5, 0.4375),
                VoxelShapes.cuboid(0.8125, 0.25, -0.0625, 0.875, 0.5, 0.4375),
                VoxelShapes.cuboid(0.1875, 0.25, -0.0625, 0.8125, 0.3125, 0.4375)
        );
        SHAPE_BOTTOM_OPEN[1] = VoxelTransform.rotate90(SHAPE_BOTTOM_OPEN[0]);
        SHAPE_BOTTOM_OPEN[2] = VoxelTransform.rotate180(SHAPE_BOTTOM_OPEN[0]);
        SHAPE_BOTTOM_OPEN[3] = VoxelTransform.rotate270(SHAPE_BOTTOM_OPEN[0]);

        SHAPE_OPEN = new VoxelShape[4];
        SHAPE_OPEN[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.1875, 0.375, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.1875, 0.4375, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.875, 0.1875, 0.375, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0, 0.0625, 0.375, 1, 0.1875, 1),
                VoxelShapes.cuboid(0, 0, 0.375, 0.125, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.375, 1, 0.0625, 0.5),
                VoxelShapes.cuboid(0.875, 0, 0.875, 1, 0.0625, 1),
                VoxelShapes.cuboid(0, 0, 0.875, 0.125, 0.0625, 1),
                VoxelShapes.cuboid(0.125, 0.5625, -0.125, 0.875, 0.8125, -0.0625),
                VoxelShapes.cuboid(0.375, 0.6875, -0.1875, 0.625, 0.75, -0.125),
                VoxelShapes.cuboid(0.125, 0.5625, -0.0625, 0.1875, 0.8125, 0.4375),
                VoxelShapes.cuboid(0.8125, 0.5625, -0.0625, 0.875, 0.8125, 0.4375),
                VoxelShapes.cuboid(0.1875, 0.5625, -0.0625, 0.8125, 0.625, 0.4375),
                VoxelShapes.cuboid(0.125, 0.25, -0.125, 0.875, 0.5, -0.0625),
                VoxelShapes.cuboid(0.375, 0.375, -0.1875, 0.625, 0.4375, -0.125),
                VoxelShapes.cuboid(0.125, 0.25, -0.0625, 0.1875, 0.5, 0.4375),
                VoxelShapes.cuboid(0.8125, 0.25, -0.0625, 0.875, 0.5, 0.4375),
                VoxelShapes.cuboid(0.1875, 0.25, -0.0625, 0.8125, 0.3125, 0.4375)
        );
        SHAPE_OPEN[1] = VoxelTransform.rotate90(SHAPE_OPEN[0]);
        SHAPE_OPEN[2] = VoxelTransform.rotate180(SHAPE_OPEN[0]);
        SHAPE_OPEN[3] = VoxelTransform.rotate270(SHAPE_OPEN[0]);
    }

}
