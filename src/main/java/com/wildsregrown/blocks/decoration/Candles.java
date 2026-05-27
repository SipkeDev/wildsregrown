package com.wildsregrown.blocks.decoration;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.render.ITintedBlock;

import java.util.List;
import java.util.function.ToIntFunction;

public class Candles extends Block implements ITintedBlock {

    private static final VoxelShape shape;
    private static final IntegerProperty CANDLES = WRGProperties.LAYERS;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final Int2ObjectMap<List<Vec3>> CANDLES_TO_PARTICLE_OFFSETS;
    private final int rgb;

    public Candles(Properties settings, int rgb) {
        super(settings.lightLevel(createLightLevelFromLitBlockState()));
        this.registerDefaultState(defaultBlockState().setValue(CANDLES, 1).setValue(LIT, false));
        this.rgb = rgb;
    }

    /**
     * Settings
     * @return
     */
    public int getRgb() {
        return rgb;
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState() {
        return (state) -> state.getValue(LIT) ? 3 * state.getValue(CANDLES) : 0;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(CANDLES, LIT);
    }

    /**
     * Visuals
     */
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            CANDLES_TO_PARTICLE_OFFSETS.get(state.getValue(CANDLES)).forEach(
                    (offset) ->
                            spawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random)
            );
        }
    }

    public static void spawnCandleParticles(Level world, Vec3 vec3d, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playLocalSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    /**
     * World interactions
     * @param state
     * @param context
     * @return
     */

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(CANDLES);
        if (context.getItemInHand().is(this.asItem()) && i < 8) {
            return context.replacingClickedOnBlock();
        }
        return super.canBeReplaced(state, context);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        if (blockState.is(this)) {
            if (blockState.getValue(CANDLES) != 8){
                return blockState.setValue(CANDLES, blockState.getValue(CANDLES)+1);
            }
        }
        return defaultBlockState();
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return shape;
    }

    static {
        shape = Shapes.or(
                Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.375, 0.65625)
        );
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(new Int2ObjectOpenHashMap<>(8), (int2ObjectOpenHashMap) -> {
            int2ObjectOpenHashMap.put(1, List.of((new Vec3(8.0, 6.5, 8.0)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(2, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(3, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(4, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F), (new Vec3(9.3, 4.5, 5.9)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(5, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F), (new Vec3(9.3, 4.5, 5.9)).scale(0.0625F), (new Vec3(9.4, 5.5, 9.5)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(6, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F), (new Vec3(9.3, 4.5, 5.9)).scale(0.0625F), (new Vec3(9.4, 5.5, 9.5)).scale(0.0625F), (new Vec3(10.7, 4.5, 7.5)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(7, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F), (new Vec3(9.3, 4.5, 5.9)).scale(0.0625F), (new Vec3(9.4, 5.5, 9.5)).scale(0.0625F), (new Vec3(10.7, 4.5, 7.5)).scale(0.0625F), (new Vec3(7.3, 3.5, 10.9)).scale(0.0625F)));
            int2ObjectOpenHashMap.put(8, List.of((new Vec3(9, 6.5, 8)).scale(0.0625F), (new Vec3(7.4, 5.5, 8.5)).scale(0.0625F), (new Vec3(7.4, 4.5, 6.5)).scale(0.0625F), (new Vec3(9.3, 4.5, 5.9)).scale(0.0625F), (new Vec3(9.4, 5.5, 9.5)).scale(0.0625F), (new Vec3(10.7, 4.5, 7.5)).scale(0.0625F), (new Vec3(7.3, 3.5, 10.9)).scale(0.0625F), (new Vec3(7.9, 3.5, 4.2)).scale(0.0625F)));
        });
    }

    @Override
    public int getTint(BlockState state, int tintIndex){
        if (tintIndex == 0){
            return rgb;
        }
        return -1;
    }

}
