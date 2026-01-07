package com.wildsregrown.blocks.flora.grass;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.TintUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.awt.*;

/**
 * Has a typical european grassfield color palette
 */
public class Grass extends Flora {

    private final int[] rgb;

    public Grass(Settings settings, int rgb, float toughness, int consumptionRate, int floweringRange) {
        super(settings.velocityMultiplier(0.9f), ModProperties.AGE_4, toughness, consumptionRate, floweringRange);
        this.rgb = TintUtil.buildOverlayMap(rgb, Colors.pastelYellow, Colors.fernGreen, Flora.moisture.getValues().size(), 0.5f);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(stage, moisture, ModProperties.AGE_4, layers);
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
                    //WildsRegrown.LOGGER.info("Cycle:" + stage);
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
                if (Dice.d4(random) >= 3){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.BUDS));
                }
            }
            case BUDS -> {
                if (Dice.d4(random) == 4){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FLOWERING));
                }
            }
            case FLOWERING -> {
                if (Dice.d12(random) >= 10){
                    world.setBlockState(pos, state.with(Flora.stage, FloraStage.FRUITS));
                }
            }
            case FRUITS -> {
                if (Dice.d4(random) == 4){
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

    @Override
    public int getTint(final BlockState state, final int tintIndex) {
        return rgb[MathUtil.clamp(state.get(moisture)-1, 0, rgb.length)];
    }

}
