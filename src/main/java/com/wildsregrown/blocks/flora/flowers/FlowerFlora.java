package com.wildsregrown.blocks.flora.flowers;


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

/**
 * FLowers have a 4 age cycle, on age 4 they flower.
 * They spread by leaving wind trails.
 * Usually finds natural death by fungus late in the year.
 */
public class FlowerFlora extends Flora {

    public final int[] rgb;

    public FlowerFlora(Settings settings, int rgb, float toughness, int consumptionRate, int floweringRange) {
        super(settings, ModProperties.AGE_4, toughness, consumptionRate, floweringRange);
        this.rgb = TintUtil.buildOverlayMap(rgb, Colors.darkPastelGreen, Colors.linen, moisture.getValues().size(), 0.18f);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(stage, moisture, ModProperties.AGE_4, layers);
    }

    /**
     * Grows to age 4, flowers after
     */
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

        if (Dice.d4(random) <= 2) {
            if (retrieveMoisture(world, state, pos)) {
                FloraStage stage = state.get(Flora.stage);
                if (FloraStage.DEATH == stage){
                    world.setBlockState(pos, Blocks.AIR.getDefaultState());
                }else if (FloraStage.isSick(stage)) {
                    handleSickness(world, state, pos, random);
                } else if (attemptGrowth(world, state, pos, random)) {
                    cycleFlowering(stage, world, state, pos, random);
                } else {//During grow stages, perhaps it gets sick.
                    if (Dice.d12(random) == 1) {
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
                if (Dice.d8(random) == 8){
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
        if (random.nextFloat() > 1f-toughness){
            world.setBlockState(pos, state.with(ModProperties.FLORAL_STAGE, FloraStage.HEALTHY));
        }else {
            spreadDisease(world, state, pos, random, state.get(stage), toughness);
            int age = state.get(ModProperties.AGE_4);
            if (age <= 1){
                world.setBlockState(pos, state.with(ModProperties.AGE_4, 0).with(stage, FloraStage.DEATH));
            }else {
                world.setBlockState(pos, state.with(ModProperties.AGE_4, age-1));
            }
        }
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 0){
            int base = rgb[MathUtil.clamp(state.get(moisture) - 1, 0, rgb.length)];
            if (FloraStage.isSick(state.get(stage))){
                return TintUtil.blend(base, Colors.darkPastelOrange, 0.75f);
            }else{
                return base;
            }
        }else {
            return -1;
        }
    }

}
