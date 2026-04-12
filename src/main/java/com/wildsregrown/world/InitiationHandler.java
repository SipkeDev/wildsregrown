package com.wildsregrown.world;

import com.google.common.collect.ImmutableList;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.world.biomes.WRGBiomeProvider;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;

import java.util.Map;

public class InitiationHandler {

    public static void initServer(MinecraftServer server) {
        DynamicRegistryManager.Immutable registryAccess = server.getRegistryManager();

        Registry<DimensionOptions> dim = registryAccess.getOrThrow(RegistryKeys.DIMENSION);
        for (Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions> entry : dim.getEntrySet()) {
            WildsRegrown.LOGGER.info("MARK: " + entry.getKey());
            DimensionOptions dimensionOptions = entry.getValue();
            initBiomes(registryAccess, dimensionOptions, entry.getKey());
        }
    }

    private static void initBiomes(DynamicRegistryManager.Immutable registryAccess, DimensionOptions options, RegistryKey<DimensionOptions> key) {

        if (options.chunkGenerator() instanceof WRGChunkGenerator chunk) {
            ImmutableList.Builder<RegistryEntry<Biome>> builder = ImmutableList.builder();
            Registry<Biome> biomeRegistry = registryAccess.getOrThrow(RegistryKeys.BIOME);
            /**
            biomeRegistry.getIndexedEntries().forEach(ctx -> {
                WildsRegrown.LOGGER.info("Key: " + ctx);
                builder.add(ctx);
            });
             */
            biomeRegistry.getIndexedEntries().forEach(ctx -> {
                WildsRegrown.LOGGER.info("Key: " + ctx);
                builder.add(ctx);
            });
            if (chunk.getBiomeSource() instanceof WRGBiomeProvider provider){
                provider.addBiomes(builder.build());
            }
            WildsRegrown.LOGGER.info("Loaded biomes");
        }

    }

}