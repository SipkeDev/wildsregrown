package com.wildsregrown.blocks.flora.rooted;


import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

/**
 * FLowers have a 4 age cycle, on age 4 they flower.
 * They spread by leaving wind trails.
 * Usually finds natural death by fungus late in the year.
 */
public class RootedFlora extends Flora {

    public RootedFlora(Settings settings, float toughness, int consumptionRate, int floweringRange) {
        super(settings.velocityMultiplier(0.8f), ModProperties.AGE_6, toughness, consumptionRate, floweringRange);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(stage, moisture, ModProperties.AGE_6, layers);
    }

    /**
     * Grows to age 4, flowers after
     */
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (Dice.d4(random) == 1) {
            if (retrieveMoisture(world, state, pos)) {
                FloraStage stage = state.get(Flora.stage);
                if (FloraStage.isSick(stage)) {
                    handleSickness(world, state, pos, random);
                } else if (attemptGrowth(world, state, pos, random)) {
                    cycleFlowering(stage, world, state, pos, random);
                } else {
                    //During grow stages, perhaps it gets sick.
                    if (Dice.d20(random) == 1) {
                        switch (Dice.d4(random)) {
                            case 1:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.BACTERIA));
                            case 2:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.INSECTS));
                            default:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.FUNGUS));
                        }
                    }
                }
            }
        }

    }

    /**
     * cycles flowering
     */
    private void cycleFlowering(FloraStage stage, ServerWorld world, BlockState state, BlockPos pos, Random random){
        switch (stage){
            case HEALTHY -> {
                if (Dice.d8(random) >= 6){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.BUDS));
                }
            }
            case BUDS -> {
                if (Dice.d4(random) >= 3){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FLOWERING));
                }
            }
            case FLOWERING -> {
                if (Dice.d12(random) >= 10){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FRUITS));
                }
            }
            case FRUITS -> {
                if (Dice.d4(random) >= 3){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FUNGUS));
                }else {
                    spreadSeed(world, pos, random, this.floweringRange);
                }
            }
        }
    }

    @Override
    public void handleSickness(ServerWorld world, BlockState state, BlockPos pos, Random random) {
        spreadDisease(world, state, pos, random, state.get(stage), toughness);
    }

}
