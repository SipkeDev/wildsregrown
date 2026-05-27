package com.wildsregrown.blocks.carpentry.tree;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.IRenderType;
import wildsregrown.api.block.render.ITintedBlock;

public class HalfLog extends Block implements ITintedBlock, IRenderType {

    protected static final VoxelShape DOWN;
    protected static final VoxelShape UP;
    protected static final VoxelShape NORTH;
    protected static final VoxelShape EAST;
    protected static final VoxelShape SOUTH;
    protected static final VoxelShape WEST;

    public static final BooleanProperty rotated = BooleanProperty.create("rotated");
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public HalfLog(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(rotated, false).setValue(PAINT, LinSeedPaintable.NONE).setValue(FACING, Direction.DOWN));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(rotated, FACING, PAINT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {

        Direction place = ctx.getNearestLookingDirection().getOpposite();
        boolean isVertical = place.getAxis().isVertical();
        Vec3 centerPos = ctx.getClickedPos().getCenter();
        Vec3 offset = centerPos.subtract(ctx.getClickLocation());

        if (isVertical){
            boolean rotated = Math.abs(offset.x) > 0.2 || Math.abs(offset.z) > 0.2;
            return defaultBlockState().setValue(HalfLog.rotated, !rotated).setValue(FACING, place);
        }else {
            return defaultBlockState().setValue(HalfLog.rotated, offset.y > 0).setValue(FACING, place);
        }

    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.FACING, rotation.rotate(state.getValue(BlockStateProperties.FACING)));
    }
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.FACING)));
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return tintIndex == 1 ? state.getValue(PAINT).getRGB() : -1;
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(FACING)) {
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
        UP = Shapes.or(
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0)
        );
        DOWN = Shapes.or(
                Block.box(0, 8, 0, 16, 16, 16)
        );
        NORTH = Shapes.or(
                Shapes.box(0, 0, 0.5, 1, 1, 1)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);
    }

}
