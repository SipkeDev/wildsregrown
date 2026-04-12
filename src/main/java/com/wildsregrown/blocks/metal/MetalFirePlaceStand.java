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

public class MetalFirePlaceStand extends Block {

    private static final VoxelShape SHAPE;
    private static final IntProperty OXIDATION;

    public MetalFirePlaceStand(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(OXIDATION, 0).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(10) == 0){
            WildsRegrown.LOGGER.info("OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (random.nextInt(100) == 0){
            WildsRegrown.LOGGER.info("UNLUCKY OXIDATION HAPPENED");
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(OXIDATION, Properties.WATERLOGGED);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        return getDefaultState().with(Properties.WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return SHAPE;
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0.4375, 0, 0, 0.5625, 1, 0.0625),
                VoxelShapes.cuboid(0.375, 0.46875, -0.0625, 0.625, 0.53125, 0.125)
        );
    }

}
