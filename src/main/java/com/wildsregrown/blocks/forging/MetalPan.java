package com.wildsregrown.blocks.forging;

import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class MetalPan extends Block {

    private static final VoxelShape[] SHAPE;
    private static final IntegerProperty OXIDATION = ModProperties.OXIDATION;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MetalPan(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(OXIDATION, 0).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(10) == 0){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, 3));
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(FACING, OXIDATION, BlockStateProperties.WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();
        return defaultBlockState().setValue(FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        int key = state.getValue(FACING).get2DDataValue();
        return SHAPE[key];
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, rotation.rotate(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(BlockStateProperties.HORIZONTAL_FACING, mirror.mirror(state.getValue(BlockStateProperties.HORIZONTAL_FACING)));
    }

    static {
        SHAPE = new VoxelShape[4];
        SHAPE[0] = Shapes.or(
                Shapes.box(0.25, 0, 0.25, 0.75, 0.0625, 0.75),
                Shapes.box(0.25, 0.03125, 0.75, 0.75, 0.15625, 0.8125),
                Shapes.box(0.25, 0.03125, 0.1875, 0.75, 0.15625, 0.25),
                Shapes.box(0.75, 0.03125, 0.1875, 0.8125, 0.15625, 0.8125),
                Shapes.box(0.1875, 0.03125, 0.1875, 0.25, 0.15625, 0.8125),
                Shapes.box(0.4375, 0.03125, 0.0625, 0.5625, 0.15625, 0.1875),
                Shapes.box(0.45625, 0.05, -0.4375, 0.54375, 0.1375, 0.0625)
        );
        SHAPE[1]  = VoxelTransform.rotate90(SHAPE[0]);
        SHAPE[2]  = VoxelTransform.rotate180(SHAPE[0]);
        SHAPE[3]  = VoxelTransform.rotate270(SHAPE[0]);

    }

}
