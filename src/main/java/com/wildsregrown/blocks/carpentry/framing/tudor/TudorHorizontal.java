package com.wildsregrown.blocks.carpentry.framing.tudor;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.Tudor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.abstracts.HorizontalConnectingBlock;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.item.MultiSelectableBlock;

public class TudorHorizontal extends HorizontalConnectingBlock implements ITintedBlock, MultiSelectableBlock {

    private static final VoxelShape[] shape;
    private static final EnumProperty<HorizontalConnected> SHAPE = WRGProperties.HORIZONTAL_CONNECTED;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private final static EnumProperty<Tudor> TUDOR = ModProperties.TUDOR;
    private Identifier id;

    public TudorHorizontal(String id, Properties setting){
        super(setting);
        this.id = generateIdentifier(WildsRegrown.modid, id);
        //registerDefaultState(this.defaultBlockState().setValue(TUDOR, Tudor.hollow).setValue(FACING, Direction.NORTH).setValue(SHAPE, HorizontalConnected.SINGLE).setValue(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public Item asItem() {
        return asItem(id);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, SHAPE, TUDOR, PAINT, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected MapCodec<? extends HorizontalConnectingBlock> codec() {
        return simpleCodec(ctx->new TudorHorizontal(id.getPath(), ctx));
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH -> {return shape[0];}
            case EAST  -> {return shape[1];}
            case SOUTH  -> {return shape[2];}
            case WEST  -> {return shape[3];}
            default -> {return shape[0];}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        shape = new VoxelShape[4];
        shape[0] = Shapes.or(
                Shapes.box(0, 0, 0, 1, 0.125, 0.125),
                Shapes.box(0, 0.875, 0, 1, 1, 0.125),
                Shapes.box(0, 0.125, 0, 0.125, 0.875, 0.125),
                Shapes.box(0.875, 0.125, 0, 1, 0.875, 0.125)
        );
        shape[1] = VoxelTransform.rotate90(shape[0]);
        shape[2] = VoxelTransform.rotate180(shape[0]);
        shape[3] = VoxelTransform.rotate270(shape[0]);
    }

}
