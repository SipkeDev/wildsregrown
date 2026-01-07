package com.wildsregrown.world;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sipke.World;
import com.sipke.WorldConstants;
import com.wildsregrown.world.biomes.WRGBiomeProvider;
import com.wildsregrown.world.decorator.*;
import com.wildsregrown.world.decorator.FloraDecorator;
import com.wildsregrown.world.decorator.StructureDecorator;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.LOGGER;

public class WRGChunkGenerator extends ChunkGenerator {

    public static final MapCodec<WRGChunkGenerator> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter(
                            (generator) -> generator.biomeSource),
                    ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings")
                            .forGetter((generator) -> generator.settings)
            ).apply(instance, instance.stable(WRGChunkGenerator::new)));

    private final World world;
    private final RegistryEntry<ChunkGeneratorSettings> settings;
    private final BaseDecorator decorator;
    private final BedrockDecorator bedrockDecorator;
    private final TreeDecorator treeDecorator;
    private final FloraDecorator floraDecorator;
    private final StructureDecorator structureDecorator;
    private final FeatureDecorator featureDecorator;

    public WRGChunkGenerator(BiomeSource source, RegistryEntry<ChunkGeneratorSettings> settings) {
        super(source);
        this.settings = settings;
        this.world = new World();
        if (source instanceof WRGBiomeProvider provider){
            provider.setWorld(world);
        }
        this.decorator = new BaseDecorator(this);
        this.bedrockDecorator = new BedrockDecorator();
        this.floraDecorator = new FloraDecorator(this);
        this.treeDecorator = new TreeDecorator(this);
        this.structureDecorator = new StructureDecorator(this);
        this.featureDecorator = new FeatureDecorator(this);
        LOGGER.info("Chunk generator init");
    }

    @Override
    protected MapCodec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk) {}

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {}

    @Override
    public void populateEntities(ChunkRegion region) {}

    @Override
    public int getWorldHeight() {
        return WorldConstants.worldHeight;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {
        decorator.apply(chunk);
        bedrockDecorator.apply(chunk);
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        //super.generateFeatures(world,chunk,structureAccessor);
        floraDecorator.apply(world, chunk);
        treeDecorator.apply(world, chunk);
        structureDecorator.apply(world, chunk);
        featureDecorator.apply(world, chunk);
    }

    @Override
    public int getSeaLevel() {
        return WorldConstants.sea;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        return (int) getWorld().generator.getHeightMapPos(x, z).getHeight();
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return null;
    }

    @Override
    public void appendDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {}

    public World getWorld() {
        return this.world;
    }

}
