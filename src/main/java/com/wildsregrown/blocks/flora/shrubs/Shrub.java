package com.wildsregrown.blocks.flora.shrubs;


import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
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

import static com.wildsregrown.blocks.render.TintUtil.buildBlendMap;

/**
 * Rooted flora has a 6 age cycle, repeats from the first step.
 */
public class Shrub extends Flora {

    private final int flowerColor;
    private final int[] rgb;

    public Shrub(Settings settings, int flowerColor, float toughness, int consumptionRate, int floweringRange) {
        super(settings.velocityMultiplier(0.3f), ModProperties.AGE_6, toughness, consumptionRate, floweringRange);
        this.flowerColor = flowerColor;
        this.rgb = buildBlendMap(Colors.fern, Colors.pastelYellow, Colors.fernGreen, moisture.getValues().size());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(stage, moisture, ModProperties.AGE_6, layers);
    }

    /**
     * Grows to age 6, flower cycles every age cycle.
     */
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (Dice.d4(random) == 1) {
            if (retrieveMoisture(world, state, pos)) {
                FloraStage stage = state.get(Flora.stage);
                if (FloraStage.isSick(stage)) {
                    handleSickness(world, state, pos, random);
                } else if (!cycleFlowering(stage, world, state, pos, random)) {
                    if (attemptGrowth(world, state, pos, random)) {
                        startCycle(world, state, pos, random, 8);
                    } else {
                        switch (Dice.d100(random)) {
                            case 1:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.INSECTS));
                            case 2:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.BACTERIA));
                            case 3:
                                world.setBlockState(pos, state.with(Flora.stage, FloraStage.FUNGUS));
                        }
                    }

                }
            }
        }

    }

    private void startCycle(ServerWorld world, BlockState state, BlockPos pos, Random random, int dc){
        if (state.get(Flora.stage) == FloraStage.HEALTHY){
            if (Dice.d8(random) >= dc){
                world.setBlockState(pos, state.with(Flora.stage, FloraStage.BUDS));
            }
        }
    }

    /**
     * cycles flowering
     * Returns true if in cycle
     */
    private boolean cycleFlowering(FloraStage stage, ServerWorld world, BlockState state, BlockPos pos, Random random){
        switch (stage){
            case BUDS -> {
                if (Dice.d4(random) == 4){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FLOWERING));
                }
                return true;
            }
            case FLOWERING -> {
                if (Dice.d12(random) >= 10){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FRUITS));
                }
                return true;
            }
            case FRUITS -> {
                if (Dice.d4(random) == 4){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.HEALTHY));
                }else {
                    spreadSeed(world, pos, random, this.floweringRange);
                }
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Override
    public void handleSickness(ServerWorld world, BlockState state, BlockPos pos, Random random) {
        spreadDisease(world, state, pos, random, state.get(stage), toughness);
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 1) {return flowerColor;}
        return rgb[MathUtil.clamp(state.get(moisture)-1, 0, rgb.length)];
    }

    public int getFlowerColor() {
        return flowerColor;
    }
}
