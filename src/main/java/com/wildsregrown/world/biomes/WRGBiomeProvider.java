package com.wildsregrown.world.biomes;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sipke.World;
import com.sipke.api.categorization.Climate;
import com.sipke.registeries.core.Registry;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.identifiable.IdentifiableRegistery;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.CheckerboardBiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSourceParameterList;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.wildsregrown.WildsRegrown.modid;

public class WRGBiomeProvider extends BiomeSource {

    public static final MapCodec<WRGBiomeProvider> CODEC = RecordCodecBuilder.mapCodec((instance) ->
            instance.group(Biome.REGISTRY_ENTRY_LIST_CODEC.fieldOf("biomes").forGetter((biomeSource) -> biomeSource.biomeArray))
                    .apply(instance, WRGBiomeProvider::new));
    private final RegistryEntryList<Biome> biomeArray;
    private final HashMap<Identifier, RegistryEntry<Biome>> biomes;

    private WRGBiomeProvider(RegistryEntryList<Biome> biomes) {
        this.biomeArray = biomes;
        this.biomes = new HashMap<>();
    }

    public HashMap<Identifier, RegistryEntry<Biome>> getMap() {
        return biomes;
    }

    @Override
    protected MapCodec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    protected Stream<RegistryEntry<Biome>> biomeStream() {
        return biomes.values().stream();
    }

    @Override
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        //WRGVanilla.LOGGER.info("Hello?" + x + " " + y + " " + z);
        Climate climate = World.getInstance().generator.getClimate(x, z);
        return biomes.get(Identifier.of(modid, climate.name));
    }

    @Override
    public void addDebugInfo(List<String> info, BlockPos pos, MultiNoiseUtil.MultiNoiseSampler noiseSampler) {
        info.add("WRG Biome builder");
    }

    public void addBiomes(ImmutableList<RegistryEntry<Biome>> build) {
        for (RegistryEntry<Biome> entry : build) {
            Optional<RegistryKey<Biome>> key = entry.getKey();
            if (key.isPresent()) {
                this.biomes.put(key.get().getValue(), entry);
                WildsRegrown.LOGGER.info("value :" + key.get().getValue());
            }
        }
        /**
        for (RegistryEntry<Biome> entry : biomeArray){
            Optional<RegistryKey<Biome>> key = entry.getKey();
            if (key.isPresent()) {
                this.biomes.put(key.get().getValue(), entry);
                WildsRegrown.LOGGER.info("from array :" + key.get().getValue());
            }
        }
         */
    }

}
