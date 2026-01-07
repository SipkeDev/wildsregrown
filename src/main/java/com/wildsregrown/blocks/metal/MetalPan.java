package com.wildsregrown.blocks.metal;

import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.ModProperties;
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

public class MetalPan extends Block {

    private static final VoxelShape[] SHAPE;
    private static final IntProperty OXIDATION = ModProperties.OXIDATION;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;

    public MetalPan(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(OXIDATION, 0).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0){
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
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
        return getDefaultState().with(FACING, direction).with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        int key = state.get(FACING).getHorizontalQuarterTurns();
        return SHAPE[key];
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
        SHAPE = new VoxelShape[4];
        SHAPE[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.25, 0, 0.25, 0.75, 0.0625, 0.75),
                VoxelShapes.cuboid(0.25, 0.03125, 0.75, 0.75, 0.15625, 0.8125),
                VoxelShapes.cuboid(0.25, 0.03125, 0.1875, 0.75, 0.15625, 0.25),
                VoxelShapes.cuboid(0.75, 0.03125, 0.1875, 0.8125, 0.15625, 0.8125),
                VoxelShapes.cuboid(0.1875, 0.03125, 0.1875, 0.25, 0.15625, 0.8125),
                VoxelShapes.cuboid(0.4375, 0.03125, 0.0625, 0.5625, 0.15625, 0.1875),
                VoxelShapes.cuboid(0.45625, 0.05, -0.4375, 0.54375, 0.1375, 0.0625)
        );
        SHAPE[1]  = VoxelTransform.rotate90(SHAPE[0]);
        SHAPE[2]  = VoxelTransform.rotate180(SHAPE[0]);
        SHAPE[3]  = VoxelTransform.rotate270(SHAPE[0]);

    }

}
