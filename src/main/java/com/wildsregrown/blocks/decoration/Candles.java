package com.wildsregrown.blocks.decoration;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.ToIntFunction;

public class Candles extends Block implements ITintedBlock {

    private static final VoxelShape shape;
    private static final IntProperty CANDLES = ModProperties.LAYERS;
    private static final BooleanProperty LIT = Properties.LIT;
    private static final Int2ObjectMap<List<Vec3d>> CANDLES_TO_PARTICLE_OFFSETS;
    private final int rgb;

    public Candles(Settings settings, int rgb) {
        super(settings.luminance(createLightLevelFromLitBlockState()));
        this.setDefaultState(getDefaultState().with(CANDLES, 1).with(LIT, false));
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
        return (state) -> state.get(LIT) ? 3 * state.get(CANDLES) : 0;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(CANDLES, LIT);
    }

    /**
     * Visuals
     */
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            CANDLES_TO_PARTICLE_OFFSETS.get(state.get(CANDLES)).forEach(
                    (offset) ->
                            spawnCandleParticles(world, offset.add(pos.getX(), pos.getY(), pos.getZ()), random)
            );
        }
    }

    public static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticleClient(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playSoundClient(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0f + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        world.addParticleClient(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    /**
     * World interactions
     * @param state
     * @param context
     * @return
     */

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(CANDLES);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            return context.canReplaceExisting();
        }
        return super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext context) {
        BlockState blockState = context.getWorld().getBlockState(context.getBlockPos());
        if (blockState.isOf(this)) {
            if (blockState.get(CANDLES) != 8){
                return blockState.with(CANDLES, blockState.get(CANDLES)+1);
            }
        }
        return getDefaultState();
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return shape;
    }

    static {
        shape = VoxelShapes.union(
                VoxelShapes.cuboid(0.34375, 0, 0.34375, 0.65625, 0.375, 0.65625)
        );
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(new Int2ObjectOpenHashMap<>(8), (int2ObjectOpenHashMap) -> {
            int2ObjectOpenHashMap.put(1, List.of((new Vec3d(8.0, 6.5, 8.0)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(2, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(3, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(4, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F), (new Vec3d(9.3, 4.5, 5.9)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(5, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F), (new Vec3d(9.3, 4.5, 5.9)).multiply(0.0625F), (new Vec3d(9.4, 5.5, 9.5)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(6, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F), (new Vec3d(9.3, 4.5, 5.9)).multiply(0.0625F), (new Vec3d(9.4, 5.5, 9.5)).multiply(0.0625F), (new Vec3d(10.7, 4.5, 7.5)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(7, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F), (new Vec3d(9.3, 4.5, 5.9)).multiply(0.0625F), (new Vec3d(9.4, 5.5, 9.5)).multiply(0.0625F), (new Vec3d(10.7, 4.5, 7.5)).multiply(0.0625F), (new Vec3d(7.3, 3.5, 10.9)).multiply(0.0625F)));
            int2ObjectOpenHashMap.put(8, List.of((new Vec3d(9, 6.5, 8)).multiply(0.0625F), (new Vec3d(7.4, 5.5, 8.5)).multiply(0.0625F), (new Vec3d(7.4, 4.5, 6.5)).multiply(0.0625F), (new Vec3d(9.3, 4.5, 5.9)).multiply(0.0625F), (new Vec3d(9.4, 5.5, 9.5)).multiply(0.0625F), (new Vec3d(10.7, 4.5, 7.5)).multiply(0.0625F), (new Vec3d(7.3, 3.5, 10.9)).multiply(0.0625F), (new Vec3d(7.9, 3.5, 4.2)).multiply(0.0625F)));
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
