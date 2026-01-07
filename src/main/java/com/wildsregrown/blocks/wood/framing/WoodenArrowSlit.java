package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.VerticalConnectingFacingBlock;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class WoodenArrowSlit extends VerticalConnectingFacingBlock implements ITintedBlock {

    private static final VoxelShape[] SINGLE;
    private static final VoxelShape[] TOP;
    private static final VoxelShape[] MIDDLE;
    private static final VoxelShape[] BOTTOM;

    public static final IntProperty VARIATIONS = ModProperties.VARIATIONS_3;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    //Defining Default BlockState
    public WoodenArrowSlit(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(ModProperties.VERTICAL_CONNECTED, VerticalConnected.SINGLE).with(Properties.WATERLOGGED, Boolean.FALSE).with(VARIATIONS, 1));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(PAINT, Properties.HORIZONTAL_FACING, ModProperties.VERTICAL_CONNECTED, Properties.WATERLOGGED, VARIATIONS);
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() == this;
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(Properties.HORIZONTAL_FACING, context.getHorizontalPlayerFacing()).with(ModProperties.VERTICAL_CONNECTED, getPartProperty(context.getWorld(), context.getBlockPos()));
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {

        int i;
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            default -> i = 0;
            case EAST -> i = 1;
            case NORTH -> i = 2;
            case WEST -> i = 3;
        }
        if (state.get(VARIATIONS) == 2) {
            i += 4;
        }

        switch (state.get(ModProperties.VERTICAL_CONNECTED)) {
            default -> {
                return SINGLE[i];
            }
            case TOP -> {
                return TOP[i];
            }
            case MIDDLE -> {
                return MIDDLE[i];
            }
            case BOTTOM -> {
                return BOTTOM[i];
            }
        }
    }


    static {
        VoxelShape shape = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 11,4.6, 16, 16),
                Block.createCuboidShape(11.4, 0, 11,16, 16, 16),
                Block.createCuboidShape(13, 0, 7,16, 16, 14.3),
                Block.createCuboidShape(10, 0, 14.3,16, 16, 16),
                Block.createCuboidShape(0, 0, 14.3,6, 16, 16),
                Block.createCuboidShape(0, 0, 7,3, 16, 14.3));

        VoxelShape shape_ = VoxelShapes.union(
                Block.createCuboidShape(9, 0, 12,12, 8, 16),
                Block.createCuboidShape(12, 0, 7,16, 16, 16),
                Block.createCuboidShape(9, 10, 12,12, 16, 16),
                Block.createCuboidShape(0, 0, 7,4, 16, 16),
                Block.createCuboidShape(4, 0, 12,7, 8, 16),
                Block.createCuboidShape(4, 10, 12,7, 16, 16),
                Block.createCuboidShape(7, 13, 12,9, 16, 16));
        SINGLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape),
                shape_,
                VoxelTransform.rotate270(shape_),
                VoxelTransform.rotate180(shape_),
                VoxelTransform.rotate90(shape_)
        };

        shape = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 11,4.6, 16, 16),
                Block.createCuboidShape(11.4, 0, 11,16, 16, 16),
                Block.createCuboidShape(3, 14, 7,13, 16, 14.3),
                Block.createCuboidShape(6, 10, 14.3,10, 16, 16),
                Block.createCuboidShape(0, 0, 7,3, 16, 14.3),
                Block.createCuboidShape(13, 0, 7,16, 16, 14.3),
                Block.createCuboidShape(10, 0, 14.3,16, 16, 16),
                Block.createCuboidShape(0, 0, 14.3,6, 16, 16));
        shape_ = VoxelShapes.union(
                Block.createCuboidShape(12, 4, 7,16, 14, 16),
                Block.createCuboidShape(14, 0, 7,16, 4, 16),
                Block.createCuboidShape(0, 4, 7,4, 14, 16),
                Block.createCuboidShape(0, 0, 7,2, 4, 16),
                Block.createCuboidShape(0, 14, 7,16, 16, 16),
                Block.createCuboidShape(4, 10.5, 12,12, 14, 16),
                Block.createCuboidShape(10, 4, 12,12, 10.5, 16),
                Block.createCuboidShape(4, 4, 12,6, 10.5, 16));
        TOP = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape),
                shape_,
                VoxelTransform.rotate270(shape_),
                VoxelTransform.rotate180(shape_),
                VoxelTransform.rotate90(shape_)
        };

        shape = SINGLE[0];
        shape_ = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 7,4, 16, 16),
                Block.createCuboidShape(10, 0, 12,12, 16, 16),
                Block.createCuboidShape(12, 0, 7,16, 16, 16),
                Block.createCuboidShape(4, 0, 12,6, 16, 16));
        MIDDLE = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape),
                shape_,
                VoxelTransform.rotate270(shape_),
                VoxelTransform.rotate180(shape_),
                VoxelTransform.rotate90(shape_)
        };

        shape = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 11,4.6, 16, 16),
                Block.createCuboidShape(11.4, 0, 11,16, 16, 16),
                Block.createCuboidShape(3, 0, 7,13, 2, 14.3),
                Block.createCuboidShape(6, 0, 14.3,10, 6, 16),
                Block.createCuboidShape(0, 0, 14.3,6, 16, 16),
                Block.createCuboidShape(13, 0, 7,16, 16, 14.3),
                Block.createCuboidShape(10, 0, 14.3,16, 16, 16),
                Block.createCuboidShape(0, 0, 7,3, 16, 14.3));
        shape_ = VoxelShapes.union(
                Block.createCuboidShape(4, 0, 7,12, 2, 16),
                Block.createCuboidShape(6, 0, 14,10, 6, 16),
                Block.createCuboidShape(0, 0, 7,4, 16, 16),
                Block.createCuboidShape(10, 0, 12,12, 16, 16),
                Block.createCuboidShape(12, 0, 7,16, 16, 16),
                Block.createCuboidShape(4, 0, 12,6, 16, 16));
        BOTTOM = new VoxelShape[]{
                shape,
                VoxelTransform.rotate270(shape),
                VoxelTransform.rotate180(shape),
                VoxelTransform.rotate90(shape),
                shape_,
                VoxelTransform.rotate270(shape_),
                VoxelTransform.rotate180(shape_),
                VoxelTransform.rotate90(shape_)
        };
    }

}