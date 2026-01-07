package com.wildsregrown.blocks.wood.tree;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class HalfLog extends Block implements ITintedBlock {

    protected static final VoxelShape DOWN;
    protected static final VoxelShape UP;
    protected static final VoxelShape NORTH;
    protected static final VoxelShape EAST;
    protected static final VoxelShape SOUTH;
    protected static final VoxelShape WEST;

    public static final BooleanProperty rotated = BooleanProperty.of("rotated");
    private static final EnumProperty<Direction> FACING = Properties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public HalfLog(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(rotated, false).with(PAINT, LinSeedPaintable.NONE).with(FACING, Direction.DOWN));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(rotated, FACING, PAINT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {

        Direction place = ctx.getPlayerLookDirection().getOpposite();
        boolean isVertical = place.getAxis().isVertical();
        Vec3d centerPos = ctx.getBlockPos().toCenterPos();
        Vec3d offset = centerPos.subtract(ctx.getHitPos());

        if (isVertical){
            boolean rotated = Math.abs(offset.x) > 0.2 || Math.abs(offset.z) > 0.2;
            return getDefaultState().with(HalfLog.rotated, !rotated).with(FACING, place);
        }else {
            return getDefaultState().with(HalfLog.rotated, Math.abs(offset.y) > 0.2).with(FACING, place);
        }
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.FACING, rotation.rotate(state.get(Properties.FACING)));
    }
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.FACING)));
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return tintIndex == 1 ? state.get(PAINT).getRGB() : -1;
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
            case UP -> {return UP;}
            case DOWN -> {return DOWN;}
            case NORTH -> {return NORTH;}
            case SOUTH  -> {return SOUTH;}
            case EAST  -> {return EAST;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
    }

    static {
        UP = VoxelShapes.union(
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        );
        DOWN = VoxelShapes.union(
                Block.createCuboidShape(0, 8, 0, 16, 16, 16)
        );
        NORTH = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.5, 1, 1, 1)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }

}
