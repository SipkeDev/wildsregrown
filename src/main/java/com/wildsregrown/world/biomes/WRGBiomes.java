package com.wildsregrown.world.biomes;

import com.sipke.api.categorization.Climate;
import com.sipke.api.features.Colors;
import com.wildsregrown.WildsRegrown;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;

import static com.wildsregrown.WildsRegrown.modid;

public class WRGBiomes {

    //todo custom spawn implementation
    private static final SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
    static {DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);}

    /**
     * Keys
     */
    public static final RegistryKey<Biome> ice              = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "ice"));
    public static final RegistryKey<Biome> polarDesert      = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "polar_desert"));
    public static final RegistryKey<Biome> tundra           = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "tundra"));
    public static final RegistryKey<Biome> coniferousForest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "coniferious_forest"));
    public static final RegistryKey<Biome> coolDesert       = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cool_desert"));
    public static final RegistryKey<Biome> coolScrubland    = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cool_shrubland"));
    public static final RegistryKey<Biome> mixedForest      = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "mixed_forest"));
    public static final RegistryKey<Biome> steppe           = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "steppe"));
    public static final RegistryKey<Biome> hotDesert        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "hot_desert"));
    public static final RegistryKey<Biome> hotScrubland     = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "hot_scrubland"));
    public static final RegistryKey<Biome> chaparral        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "chapparal"));
    public static final RegistryKey<Biome> deciduousForest  = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "decidious_forest"));
    public static final RegistryKey<Biome> ancientForest    = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "ancient_forest"));
    public static final RegistryKey<Biome> spiritForest     = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "spirit_forest"));
    public static final RegistryKey<Biome> savanna          = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "savanna"));
    public static final RegistryKey<Biome> coldSea          = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cold_sea"));
    public static final RegistryKey<Biome> temperateSea     = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "temperate_sea"));
    public static final RegistryKey<Biome> warmSea          = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_sea"));
    public static final RegistryKey<Biome> coldOcean        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cold_ocean"));
    public static final RegistryKey<Biome> temperateOcean   = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "temperate_ocean"));
    public static final RegistryKey<Biome> warmOcean        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_ocean"));

    /**
     * Biomes
     * @param context
     * @return
     */
    public static void register(Registerable<Biome> context) {
        context.register(ice,               buildBiome(false, 0.1f, 0f));
        context.register(polarDesert,       buildBiome(false, 0.1f, 0.1f));
        context.register(tundra,            buildBiome(true, 0.2f, 0.2f));
        context.register(coniferousForest,  buildBiome(true, 0.5f, 0.5f));
        context.register(coolDesert,        buildBiome(true, 0.2f, 0.2f));
        context.register(coolScrubland,     buildBiome(true, 0.3f, 0.2f));
        context.register(mixedForest,       buildBiome(true, 0.5f, 0.5f));
        context.register(steppe,            buildBiome(true, 0.5f, 0.5f));
        context.register(hotDesert,         buildBiome(true, 0.1f, 0.9f));
        context.register(hotScrubland,      buildBiome(true, 0.1f, 0.7f));
        context.register(chaparral,         buildBiome(true, 0.5f, 0.5f));
        context.register(deciduousForest,   buildBiome(true, 0.5f, 0.5f));
        context.register(ancientForest,     buildBiome(true, 0.8f, 0.9f));
        context.register(spiritForest,      buildBiome(true, 0.8f, 0.9f));
        context.register(savanna,           buildBiome(true, 0.8f, 0.9f));
        context.register(coldSea,           buildBiome(false, 0.8f, 0f));
        context.register(temperateSea,      buildBiome(true, 0.8f, 0.5f));
        context.register(warmSea,           buildBiome(true, 0.8f, 0.8f));
        context.register(coldOcean,         buildBiome(false, 0.8f, 0f));
        context.register(temperateOcean,    buildBiome(true, 0.8f, 0.5f));
        context.register(warmOcean,         buildBiome(true, 0.8f, 0.8f));
    }

    private static Biome buildBiome(boolean percipation, float downfall, float temperature) {
        Biome biome = new Biome.Builder()
                .precipitation(percipation)
                .downfall(downfall)
                .temperature(temperature)
                .spawnSettings(spawnBuilder.build())
                .generationSettings(GenerationSettings.INSTANCE)
                .effects(new BiomeEffects.Builder()
                        .waterColor(0xe82e3b)
                        .foliageColor(Colors.fern)
                        .dryFoliageColor(Colors.darkYellow)
                        .grassColor(Colors.fernGreen)
                        .build()
                )
                .build();
        WildsRegrown.LOGGER.info("TEST: " + biome.toString());
        return biome;
    }

}
