package com.wildsregrown.blocks.metal;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class MetalBars extends Block {

    private static final VoxelShape X;
    private static final VoxelShape Z;
    private static final IntProperty OXIDATION;
    private static final EnumProperty<Direction.Axis> FACING = Properties.HORIZONTAL_AXIS;

    public MetalBars(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.Axis.X).with(OXIDATION, 0).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0){
            WildsRegrown.LOGGER.info("OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (random.nextInt(500) == 0){
            WildsRegrown.LOGGER.info("UNLUCKY OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(FACING, OXIDATION, Properties.WATERLOGGED);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();
        return getDefaultState().with(FACING, direction.getAxis()).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        switch (state.get(FACING)) {
            case X -> {return X;}
            case Z  -> {return Z;}
            default -> {return X;}
        }
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        Z = VoxelShapes.union(
                VoxelShapes.cuboid(0.4375, 0, 0.4375, 0.5625, 1, 0.5625),
                VoxelShapes.cuboid(0, 0.46875, 0.46875, 1, 0.53125, 0.5312500000000001),
                VoxelShapes.cuboid(0, 0.78125, 0.46875, 1, 0.84375, 0.5312500000000001),
                VoxelShapes.cuboid(0, 0.15625, 0.46875, 1, 0.21875, 0.5312500000000001)
        );
        X = VoxelTransform.rotate90(Z);

    }

}
