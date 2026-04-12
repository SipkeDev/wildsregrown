package com.wildsregrown.world.biomes;

import com.sipke.api.categorization.Climate;
import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

import static com.wildsregrown.WildsRegrown.modid;

public class WRGBiomes {

    //todo custom spawn implementation
    private static final SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();
    static {DefaultBiomeFeatures.addFarmAnimals(spawnBuilder);}

    /**
     * Climate Keys
     */
    public static final RegistryKey<Biome> ice              = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "ice"));
    public static final RegistryKey<Biome> polar_desert = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "polar_desert"));
    public static final RegistryKey<Biome> tundra           = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "tundra"));
    public static final RegistryKey<Biome> coniferous_forest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "coniferous_forest"));
    public static final RegistryKey<Biome> cool_desert = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cool_desert"));
    public static final RegistryKey<Biome> cool_shrubland    = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cool_shrubland"));
    public static final RegistryKey<Biome> mixed_forest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "mixed_forest"));
    public static final RegistryKey<Biome> steppe           = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "steppe"));
    public static final RegistryKey<Biome> hot_desert = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "hot_desert"));
    public static final RegistryKey<Biome> hot_shrubland     = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "hot_shrubland"));
    public static final RegistryKey<Biome> chaparral        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "chaparral"));
    public static final RegistryKey<Biome> deciduous_forest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "deciduous_forest"));
    public static final RegistryKey<Biome> ancient_forest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "ancient_forest"));
    public static final RegistryKey<Biome> spirit_forest = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "spirit_forest"));
    public static final RegistryKey<Biome> savanna          = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "savanna"));
    public static final RegistryKey<Biome> coldSea          = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cold_sea"));
    public static final RegistryKey<Biome> temperate_sea = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "temperate_sea"));
    public static final RegistryKey<Biome> warm_sea = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_sea"));
    public static final RegistryKey<Biome> cold_ocean = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cold_ocean"));
    public static final RegistryKey<Biome> temperate_ocean = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "temperate_ocean"));
    public static final RegistryKey<Biome> warm_ocean = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_ocean"));

    /**
     * Custom Keys
     */
    public static final RegistryKey<Biome> frozen_river = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "frozen_river"));
    public static final RegistryKey<Biome> river        = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "river"));
    public static final RegistryKey<Biome> warm_river   = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_river"));

    public static final RegistryKey<Biome> cold_swamp   = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "cold_swamp"));
    public static final RegistryKey<Biome> warm_swamp   = RegistryKey.of(RegistryKeys.BIOME, Identifier.of(modid, "warm_swamp"));

    public static final List<RegistryKey<Biome>> BIOMES = List.of(
            /// climates
            ice,
            polar_desert,
            tundra,
            coniferous_forest,
            cool_desert,
            cool_shrubland,
            mixed_forest,
            steppe,
            hot_desert,
            hot_shrubland,
            chaparral,
            deciduous_forest,
            ancient_forest,
            spirit_forest,
            savanna,
            coldSea,
            temperate_sea,
            warm_sea,
            cold_ocean,
            temperate_ocean,
            warm_ocean,
            /// custom
            frozen_river,
            river,
            warm_river,
            cold_swamp,
            warm_swamp
    );

    /**
     * Biomes
     * @param context
     * @return
     */
    public static void register(Registerable<Biome> context) {
        context.register(ice,               buildBiome(context, false, temp(Climate.ice), rainfall(Climate.ice), Colors.pack("#80B497"), Colors.pack("#60A17B")));
        context.register(polar_desert,       buildBiome(context, false, temp(Climate.polarDesert), rainfall(Climate.polarDesert), Colors.pack("#80B497"), Colors.pack("#60A17B")));
        context.register(tundra,            buildBiome(context, true, temp(Climate.tundra), rainfall(Climate.tundra), Colors.pack("#80B497"), Colors.pack("#60A17B")));
        context.register(coniferous_forest,  buildBiome(context, true, temp(Climate.coniferousForest), rainfall(Climate.coniferousForest), Colors.pack("#86B783"), Colors.pack("#68A464")));
        context.register(cool_desert,        buildBiome(context, true, temp(Climate.coolDesert), rainfall(Climate.coolDesert), Colors.pack("#BFB755"), Colors.pack("#AEA42A")));
        context.register(cool_shrubland,    buildBiome(context, true, temp(Climate.coolShrubland), rainfall(Climate.coolShrubland), Colors.pack("#90814D"), Colors.pack("#9E814D")));
        context.register(mixed_forest,       buildBiome(context, true, temp(Climate.mixedForest), rainfall(Climate.mixedForest), Colors.pack("#88BB67"), Colors.pack("#59AE30")));
        context.register(steppe,            buildBiome(context, true, temp(Climate.steppe), rainfall(Climate.steppe), Colors.pack("#91BD59"), Colors.pack("#77AB2F")));
        context.register(hot_desert,         buildBiome(context, true, temp(Climate.hotDesert), rainfall(Climate.hotDesert), Colors.pack("#BFB755"), Colors.pack("#AEA42A")));
        context.register(hot_shrubland,     buildBiome(context, true, temp(Climate.hotScrubland), rainfall(Climate.hotScrubland), Colors.pack("#90814D"), Colors.pack("#9E814D")));
        context.register(chaparral,         buildBiome(context, true, temp(Climate.chaparral), rainfall(Climate.chaparral), Colors.pack("#90814D"), Colors.pack("#9E814D")));
        context.register(deciduous_forest,   buildBiome(context, true, temp(Climate.deciduousForest), rainfall(Climate.deciduousForest), Colors.pack("#507A32"), Colors.pack("#59AE30")));
        context.register(ancient_forest,     buildBiome(context, true, temp(Climate.ancientForest), rainfall(Climate.ancientForest), Colors.pack("#59C93C"), Colors.pack("#30BB0B"), BiomeEffects.GrassColorModifier.DARK_FOREST));
        context.register(spirit_forest,      buildBiome(context, true, temp(Climate.spiritForest), rainfall(Climate.spiritForest), Colors.pack("#59C93C"), Colors.pack("#30BB0B")));
        context.register(savanna,           buildBiome(context, true, temp(Climate.savanna), rainfall(Climate.savanna), Colors.pack("#bfb755"), Colors.pack("#AEA42A")));
        context.register(coldSea,           buildBiome(context, false, temp(Climate.coldSea), rainfall(Climate.coldSea), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));
        context.register(temperate_sea,      buildBiome(context, true, temp(Climate.temperateSea), rainfall(Climate.temperateSea), Colors.pack("#8EB971"), Colors.pack("#71A74D")));
        context.register(warm_sea,           buildBiome(context, true, temp(Climate.warmSea), rainfall(Climate.warmSea), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));
        context.register(cold_ocean,         buildBiome(context, false, temp(Climate.coldOcean), rainfall(Climate.coldOcean), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));
        context.register(temperate_ocean,    buildBiome(context, true, temp(Climate.temperateOcean), rainfall(Climate.temperateOcean), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));
        context.register(warm_ocean,         buildBiome(context, true, temp(Climate.warmOcean), rainfall(Climate.warmOcean), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));

        /// custom keys
        context.register(frozen_river,      buildBiome(context, true, temp(Climate.coldOcean), rainfall(Climate.spiritForest), Colors.pack("#8EB971"), Colors.pack("#AEA42A")));
        context.register(river,             buildBiome(context, true, temp(Climate.temperateSea), rainfall(Climate.spiritForest),Colors.darkPastelGreen, Colors.pastelGreen));
        context.register(warm_river,        buildBiome(context, true, temp(Climate.warmOcean), rainfall(Climate.spiritForest), Colors.darkPastelGreen, Colors.pastelGreen));
        context.register(cold_swamp,        buildBiome(context, true, temp(Climate.tundra), rainfall(Climate.tundra), Colors.pack("#6A7039"), Colors.pack("#6A7039"), BiomeEffects.GrassColorModifier.SWAMP));
        context.register(warm_swamp,        buildBiome(context, true, temp(Climate.savanna), rainfall(Climate.spiritForest), Colors.pack("#4C763C"), Colors.pack("#6A7039"), BiomeEffects.GrassColorModifier.SWAMP));

        for (RegistryKey<Biome> biome : BIOMES){
            context.getRegistryLookup(RegistryKeys.BIOME).getOptional(biome).ifPresent(o->WildsRegrown.LOGGER.info("WRGBIOMES: " + o.getIdAsString()));
        }
    }

    private static float temp(Climate climate) {
        return calcVal(climate.getTemperature());
    }
    private static float rainfall(Climate climate) {
        return calcVal(climate.getRainfall());
    }
    private static float calcVal(float val) {
        return MathUtil.range(val, 0f, 1f, -1f, 1f);
    }

    public static Biome buildBiome(Registerable<Biome> registerable, boolean precipitation, float temperature, float downfall, int grassColor, int foilageColor){
        return new Biome.Builder()
                .generationSettings(createGenerationSettings(registerable))
                .spawnSettings(createSpawnSettings())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .effects(createBiomeEffects(grassColor, foilageColor, BiomeEffects.GrassColorModifier.NONE))
                .addEnvironmentAttributes(createEnvironmentAttributes(temperature))
                .build();
    }

    public static Biome buildBiome(Registerable<Biome> registerable, boolean precipitation, float temperature, float downfall, int grasscolor, int foilagecolor, BiomeEffects.GrassColorModifier modifier){
        return new Biome.Builder()
                .generationSettings(createGenerationSettings(registerable))
                .spawnSettings(createSpawnSettings())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(downfall)
                .effects(createBiomeEffects(grasscolor, foilagecolor, modifier))
                .addEnvironmentAttributes(createEnvironmentAttributes(temperature))
                .build();
    }

    private static GenerationSettings createGenerationSettings(Registerable<Biome> registerable) {
        RegistryEntryLookup<ConfiguredCarver<?>> configuredCarvers = registerable.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        RegistryEntryLookup<PlacedFeature> placedFeatures = registerable.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        GenerationSettings.LookupBackedBuilder builder = new GenerationSettings.LookupBackedBuilder(placedFeatures, configuredCarvers);

        /*
        DefaultBiomeFeatures.addDefaultOres(builder);
        DefaultBiomeFeatures.addDefaultDisks(builder);
        DefaultBiomeFeatures.addDefaultFlowers(builder);
        DefaultBiomeFeatures.addDefaultGrass(builder);
        */
        return builder.build();
    }

    private static SpawnSettings createSpawnSettings() {
        SpawnSettings.Builder builder = new SpawnSettings.Builder();
        builder.spawn(SpawnGroup.AMBIENT, 10, new SpawnSettings.SpawnEntry(EntityType.BAT, 8, 8));

        builder.spawn(SpawnGroup.CREATURE, 12, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.PIG, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 4, 4));
        builder.spawn(SpawnGroup.CREATURE,  8, new SpawnSettings.SpawnEntry(EntityType.COW, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.FROG, 2, 5));

        builder.spawn(SpawnGroup.MONSTER, 100, new SpawnSettings.SpawnEntry(EntityType.SPIDER, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, 95, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, 5, new SpawnSettings.SpawnEntry(EntityType.ZOMBIE_VILLAGER, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, 70, new SpawnSettings.SpawnEntry(EntityType.SKELETON, 4, 4));
        builder.spawn(SpawnGroup.MONSTER, 1, new SpawnSettings.SpawnEntry(EntityType.SLIME, 1, 1));
        builder.spawn(SpawnGroup.MONSTER, 30, new SpawnSettings.SpawnEntry(EntityType.BOGGED, 4, 4));

        builder.spawn(SpawnGroup.UNDERGROUND_WATER_CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.GLOW_SQUID, 4, 6));
        return builder.build();
    }

    private static BiomeEffects createBiomeEffects(int grassColor, int foliageColor, BiomeEffects.GrassColorModifier grassColorModifier) {
        return new BiomeEffects.Builder()
                .waterColor(4159204)
                .grassColor(grassColor)
                .foliageColor(foliageColor)
                .grassColorModifier(grassColorModifier)
                .build();
    }

    private static EnvironmentAttributeMap createEnvironmentAttributes(float temperature) {
        return EnvironmentAttributeMap.builder()
                .with(EnvironmentAttributes.SKY_COLOR_VISUAL, OverworldBiomeCreator.getSkyColor(temperature))
                .with(EnvironmentAttributes.FOG_COLOR_VISUAL, 0xC0D8FF)
                .with(EnvironmentAttributes.WATER_FOG_COLOR_VISUAL, 0x316451)
                .with(EnvironmentAttributes.BACKGROUND_MUSIC_AUDIO, new BackgroundMusic(SoundEvents.MUSIC_OVERWORLD_MEADOW))
                .with(EnvironmentAttributes.WATER_FOG_END_DISTANCE_VISUAL, 0.85f)
                .build();
    }

}
