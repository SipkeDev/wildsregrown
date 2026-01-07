package com.wildsregrown.world.biomes;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sipke.World;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.List;
import java.util.stream.Stream;

public class WRGBiomeProvider extends BiomeSource {

    public static final MapCodec<WRGBiomeProvider> CODEC = RecordCodecBuilder.mapCodec(
            (instance) ->
                    instance.group(
                            Biome.REGISTRY_CODEC.fieldOf("biome").forGetter((biomeSource) -> biomeSource.biome)
                    ).apply(instance, WRGBiomeProvider::new)
    );
    private final RegistryEntry<Biome> biome;
    private World world;

    public WRGBiomeProvider(RegistryEntry<Biome> biome) {
        this.biome = biome;
    }

    public void setWorld(World world){
        this.world = world;
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return Stream.empty();
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        return biome;
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos, MultiNoiseUtil.MultiNoiseSampler noiseSampler) {
        info.add("WRG Biome builder");
    }

}
