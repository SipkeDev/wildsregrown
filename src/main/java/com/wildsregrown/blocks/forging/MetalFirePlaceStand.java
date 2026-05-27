package com.wildsregrown.blocks.forging;

import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MetalFirePlaceStand extends Block {

    private static final VoxelShape SHAPE;
    private static final IntegerProperty OXIDATION;

    public MetalFirePlaceStand(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(OXIDATION, 0).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
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
        builder.add(OXIDATION, BlockStateProperties.WATERLOGGED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        return defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    static {
        OXIDATION = ModProperties.OXIDATION;
        SHAPE = Shapes.or(
                Shapes.box(0.4375, 0, 0, 0.5625, 1, 0.0625),
                Shapes.box(0.375, 0.46875, -0.0625, 0.625, 0.53125, 0.125)
        );
    }

}
