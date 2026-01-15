package com.wildsregrown.blocks.wood.framing;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.abstracts.HorizontalConnectingBlock;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.Tudor;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class TudorHorizontal extends HorizontalConnectingBlock implements ITintedBlock {

    private static final VoxelShape[] shape;
    private static final EnumProperty<HorizontalConnected> SHAPE = ModProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static EnumProperty<Tudor> TUDOR = ModProperties.TUDOR;

    public TudorHorizontal(Settings setting){
        super(setting);
        setDefaultState(this.getDefaultState().with(TUDOR, Tudor.hollow).with(FACING, Direction.NORTH).with(SHAPE, HorizontalConnected.SINGLE).with(PAINT, LinSeedPaintable.NONE));
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(Properties.HORIZONTAL_FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, SHAPE, TUDOR, PAINT, Properties.WATERLOGGED);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState().with(FACING, context.getHorizontalPlayerFacing());
    }

    @Override
    protected MapCodec<? extends HorizontalConnectingBlock> getCodec() {
        return createCodec(TudorHorizontal::new);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(Properties.HORIZONTAL_FACING)) {
            case NORTH -> {return shape[0];}
            case EAST  -> {return shape[1];}
            case SOUTH  -> {return shape[2];}
            case WEST  -> {return shape[3];}
            default -> {return shape[0];}
        }
    }

    static {
        shape = new VoxelShape[4];
        shape[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 0.125),
                VoxelShapes.cuboid(0, 0.875, 0, 1, 1, 0.125),
                VoxelShapes.cuboid(0, 0.125, 0, 0.125, 0.875, 0.125),
                VoxelShapes.cuboid(0.875, 0.125, 0, 1, 0.875, 0.125)
        );
        shape[1] = VoxelTransform.rotate90(shape[0]);
        shape[2] = VoxelTransform.rotate180(shape[0]);
        shape[3] = VoxelTransform.rotate270(shape[0]);
    }

}
