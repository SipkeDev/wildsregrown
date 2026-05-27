package com.wildsregrown.blocks.forging;

import com.wildsregrown.WildsRegrown;
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
import com.sipke.math.MathUtil;
import wildsregrown.api.block.VoxelTransform;

public class MetalWallAnchor extends Block {

    private static final VoxelShape NORTH;
    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape WEST;
    private static final IntegerProperty OXIDATION;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public MetalWallAnchor(Properties settings) {
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
            WildsRegrown.LOGGER.info("OXIDATION HAPPENED");
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
            WildsRegrown.LOGGER.info("UNLUCKY OXIDATION HAPPENED");
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
        switch (state.getValue(FACING)) {
            case NORTH -> {return NORTH;}
            case SOUTH  -> {return SOUTH;}
            case EAST  -> {return EAST;}
            case WEST  -> {return WEST;}
            default -> {return NORTH;}
        }
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
        OXIDATION = ModProperties.OXIDATION;
        NORTH = Shapes.or(
                Shapes.box(0.4375, 0, 0, 0.5625, 1, 0.0625),
                Shapes.box(0.375, 0.46875, -0.0625, 0.625, 0.53125, 0.125)
        );
        SOUTH = VoxelTransform.rotate180(NORTH);
        EAST = VoxelTransform.rotate90(NORTH);
        WEST = VoxelTransform.rotate270(NORTH);

    }

}
