package com.wildsregrown.registries.world;

import com.sipke.api.terrain.Biome;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.biomes.field.HeatherFields;
import com.wildsregrown.registries.world.biomes.forest.*;
import com.wildsregrown.registries.world.biomes.forest.decidious.*;
import com.wildsregrown.registries.world.biomes.forest.pine.PineForest;
import com.wildsregrown.registries.world.biomes.forest.pine.larch.*;
import com.wildsregrown.registries.world.biomes.forest.pine.spruce.DrySpruceForest;
import com.wildsregrown.registries.world.biomes.forest.pine.spruce.SpruceForest;
import com.wildsregrown.registries.world.biomes.forest.pine.spruce.WetSpruceForest;
import com.wildsregrown.registries.world.biomes.grassfield.*;
import com.wildsregrown.registries.world.biomes.grassfield.dry.*;
import com.wildsregrown.registries.world.biomes.grassfield.temperate.*;
import com.wildsregrown.registries.world.biomes.grassfield.wet.*;
import com.wildsregrown.registries.world.biomes.meadows.*;
import com.wildsregrown.registries.world.biomes.meadows.dry.DryCloverMeadow;
import com.wildsregrown.registries.world.biomes.meadows.dry.DryHeatherMeadow;
import com.wildsregrown.registries.world.biomes.meadows.temperate.*;
import com.wildsregrown.registries.world.biomes.meadows.wet.WetCloverMeadow;
import com.wildsregrown.registries.world.biomes.lake.TemperateLake;
import com.wildsregrown.registries.world.biomes.orchard.AppleOrchard;
import com.wildsregrown.registries.world.biomes.orchard.PearOrchard;
import com.wildsregrown.registries.world.biomes.orchard.PlumOrchard;
import com.wildsregrown.registries.world.biomes.plains.*;
import com.wildsregrown.registries.world.biomes.river.TemperateRiver;
import com.wildsregrown.registries.world.biomes.sea.ColdSea;
import com.wildsregrown.registries.world.biomes.sea.Sea;
import com.wildsregrown.registries.world.biomes.sea.WarmSea;
import com.wildsregrown.registries.world.biomes.shrub.SavannaShrubs;
import com.wildsregrown.registries.world.biomes.steppe.DrySageShrubPlains;
import com.wildsregrown.registries.world.biomes.steppe.SageShrubPlains;
import com.wildsregrown.registries.world.biomes.steppe.WetSageShrubPlains;
import com.wildsregrown.registries.world.biomes.swamp.Kwelder;
import com.wildsregrown.registries.world.biomes.swamp.MagicSwamp;
import com.wildsregrown.registries.world.biomes.swamp.Swamp;
import com.wildsregrown.registries.world.biomes.swamp.BirchSwamp;
import com.sipke.registeries.core.RegistryObject;

public class Biomes {
    
    public static final RegistryObject<Biome> defaultBiome = WorldRegistries.BIOMES.register(new Swamp());

    public static final RegistryObject<Biome> ice = WorldRegistries.BIOMES.register(new Ice());
    public static final RegistryObject<Biome> swamp = WorldRegistries.BIOMES.register(new Swamp());
    public static final RegistryObject<Biome> magic_swamp = WorldRegistries.BIOMES.register(new MagicSwamp());
    public static final RegistryObject<Biome> kwelder = WorldRegistries.BIOMES.register(new Kwelder());

    //Maritime
    public static final RegistryObject<Biome> coldSea = WorldRegistries.BIOMES.register(new ColdSea());
    public static final RegistryObject<Biome> sea = WorldRegistries.BIOMES.register(new Sea());
    public static final RegistryObject<Biome> warmSea = WorldRegistries.BIOMES.register(new WarmSea());

    //Desert
    public static final RegistryObject<Biome> hotDesert = WorldRegistries.BIOMES.register(new HotDesert());
    public static final RegistryObject<Biome> coolDesert = WorldRegistries.BIOMES.register(new CoolDesert());

    //Shrubland
    public static final RegistryObject<Biome> hotShrubland = WorldRegistries.BIOMES.register(new HotShrubLand());
    public static final RegistryObject<Biome> coolShrubland = WorldRegistries.BIOMES.register(new CoolShrubLand());

    //Decidious forest
    public static final RegistryObject<Biome> sparse_beechForest = WorldRegistries.BIOMES.register(new SparseBeechForest());
    public static final RegistryObject<Biome> dry_beechForest = WorldRegistries.BIOMES.register(new DryBeechForest());
    public static final RegistryObject<Biome> beechForest = WorldRegistries.BIOMES.register(new BeechForest());
    public static final RegistryObject<Biome> wet_beechForest = WorldRegistries.BIOMES.register(new WetBeechForest());
    public static final RegistryObject<Biome> sparse_oakForest = WorldRegistries.BIOMES.register(new SparseOakForest());
    public static final RegistryObject<Biome> dry_oakForest = WorldRegistries.BIOMES.register(new DryOakForest());
    public static final RegistryObject<Biome> oakForest = WorldRegistries.BIOMES.register(new OakForest());
    public static final RegistryObject<Biome> wet_oakForest = WorldRegistries.BIOMES.register(new WetOakForest());
    public static final RegistryObject<Biome> sparse_ashForest = WorldRegistries.BIOMES.register(new SparseAshForest());
    public static final RegistryObject<Biome> dry_ashForest = WorldRegistries.BIOMES.register(new DryAshForest());
    public static final RegistryObject<Biome> ashForest = WorldRegistries.BIOMES.register(new AshForest());
    public static final RegistryObject<Biome> wet_ashForest = WorldRegistries.BIOMES.register(new WetAshForest());


    //Meadows
    public static final RegistryObject<Biome> oak_meadow = WorldRegistries.BIOMES.register(new OakMeadow());
    public static final RegistryObject<Biome> mixed_flower_meadow = WorldRegistries.BIOMES.register(new MixedFlowerMeadow());
    public static final RegistryObject<Biome> birch_meadow = WorldRegistries.BIOMES.register(new BirchMeadow());
    public static final RegistryObject<Biome> campanula_meadow = WorldRegistries.BIOMES.register(new CampanulaMeadow());
    public static final RegistryObject<Biome> dry_clover_meadow = WorldRegistries.BIOMES.register(new DryCloverMeadow());
    public static final RegistryObject<Biome> clover_meadow = WorldRegistries.BIOMES.register(new CloverMeadow());
    public static final RegistryObject<Biome> wet_clover_meadow = WorldRegistries.BIOMES.register(new WetCloverMeadow());
    public static final RegistryObject<Biome> foxglove_meadow = WorldRegistries.BIOMES.register(new FoxGloveMeadow());
    public static final RegistryObject<Biome> daisy_meadow = WorldRegistries.BIOMES.register(new DaisyMeadow());
    public static final RegistryObject<Biome> lily_meadow = WorldRegistries.BIOMES.register(new LilyMeadow());
    public static final RegistryObject<Biome> poppy_meadow = WorldRegistries.BIOMES.register(new PoppyMeadow());
    public static final RegistryObject<Biome> hydrangea_meadow = WorldRegistries.BIOMES.register(new HydrangeaMeadow());
    public static final RegistryObject<Biome> spirea_meadow = WorldRegistries.BIOMES.register(new SpireaMeadow());
    public static final RegistryObject<Biome> dry_heather_meadow = WorldRegistries.BIOMES.register(new DryHeatherMeadow());
    public static final RegistryObject<Biome> heather_meadow = WorldRegistries.BIOMES.register(new HeatherMeadow());
    public static final RegistryObject<Biome> orchid_meadow = WorldRegistries.BIOMES.register(new OrchidMeadow());
    public static final RegistryObject<Biome> tulip_fields = WorldRegistries.BIOMES.register(new TulipFields());

    //Heathers
    public static final RegistryObject<Biome> heather_field = WorldRegistries.BIOMES.register(new HeatherFields());

    //grassfields - misc
    public static final RegistryObject<Biome> sparse_grassfield = WorldRegistries.BIOMES.register(new SparseGrassfield());
    public static final RegistryObject<Biome> dense_grassfield = WorldRegistries.BIOMES.register(new DenseGrassField());
    public static final RegistryObject<Biome> dense_tall_grassfield = WorldRegistries.BIOMES.register(new DenseTallGrassField());
    public static final RegistryObject<Biome> mixed_flower_grassfield = WorldRegistries.BIOMES.register(new MixedFlowerGrassfield());
    public static final RegistryObject<Biome> red_flower_grassfield = WorldRegistries.BIOMES.register(new RedFlowerGrassfield());
    public static final RegistryObject<Biome> white_flower_grassfield = WorldRegistries.BIOMES.register(new WhiteFlowerGrassfield());
    public static final RegistryObject<Biome> savanna_grassfield = WorldRegistries.BIOMES.register(new SavannaGrassfield());
    //grassfields - temperate
    public static final RegistryObject<Biome> curly_grassfield = WorldRegistries.BIOMES.register(new CurlyGrassfield());
    public static final RegistryObject<Biome> grassfield = WorldRegistries.BIOMES.register(new Grassfield());
    public static final RegistryObject<Biome> thin_grassfield = WorldRegistries.BIOMES.register(new ThinGrassfield());
    public static final RegistryObject<Biome> tall_curly_grassfield = WorldRegistries.BIOMES.register(new TallCurlyGrassfield());
    public static final RegistryObject<Biome> tall_grassfield = WorldRegistries.BIOMES.register(new TallGrassfield());
    public static final RegistryObject<Biome> tall_thin_grassfield = WorldRegistries.BIOMES.register(new TallThinGrassfield());
    //grassfields - wet
    public static final RegistryObject<Biome> wet_curly_grassfield = WorldRegistries.BIOMES.register(new WetCurlyGrassfield());
    public static final RegistryObject<Biome> wet_grassfield = WorldRegistries.BIOMES.register(new WetGrassfield());
    public static final RegistryObject<Biome> wet_thin_grassfield = WorldRegistries.BIOMES.register(new WetThinGrassfield());
    public static final RegistryObject<Biome> wet_tall_curly_grassfield = WorldRegistries.BIOMES.register(new WetTallCurlyGrassfield());
    public static final RegistryObject<Biome> wet_tall_grassfield = WorldRegistries.BIOMES.register(new WetTallGrassfield());
    public static final RegistryObject<Biome> wet_tall_thin_grassfield = WorldRegistries.BIOMES.register(new WetTallThinGrassfield());
    //grassfields - dry
    public static final RegistryObject<Biome> dry_curly_grassfield = WorldRegistries.BIOMES.register(new DryCurlyGrassfield());
    public static final RegistryObject<Biome> dry_grassfield = WorldRegistries.BIOMES.register(new DryGrassfield());
    public static final RegistryObject<Biome> dry_thin_grassfield = WorldRegistries.BIOMES.register(new DryThinGrassfield());
    public static final RegistryObject<Biome> dry_tall_curly_grassfield = WorldRegistries.BIOMES.register(new DryTallCurlyGrassfield());
    public static final RegistryObject<Biome> dry_tall_grassfield = WorldRegistries.BIOMES.register(new DryTallGrassfield());
    public static final RegistryObject<Biome> dry_tall_thin_grassfield = WorldRegistries.BIOMES.register(new DryTallThinGrassfield());

    //Shrubs
    public static final RegistryObject<Biome> dry_sageshrub_plains = WorldRegistries.BIOMES.register(new DrySageShrubPlains());
    public static final RegistryObject<Biome> sageshrub_plains = WorldRegistries.BIOMES.register(new SageShrubPlains());
    public static final RegistryObject<Biome> wet_sageshrub_plains = WorldRegistries.BIOMES.register(new WetSageShrubPlains());

    //Spruce forests
    public static final RegistryObject<Biome> dry_spruceForest = WorldRegistries.BIOMES.register(new DrySpruceForest());
    public static final RegistryObject<Biome> spruceForest = WorldRegistries.BIOMES.register(new SpruceForest());
    public static final RegistryObject<Biome> wet_spruceForest = WorldRegistries.BIOMES.register(new WetSpruceForest());


    //Larch Forests
    public static final RegistryObject<Biome> larchForest = WorldRegistries.BIOMES.register(new LarchForest());
    public static final RegistryObject<Biome> larchForest_flowers = WorldRegistries.BIOMES.register(new LarchForestFlowers());
    public static final RegistryObject<Biome> larchForest_artiplex = WorldRegistries.BIOMES.register(new LarchForestArtiPlex());
    public static final RegistryObject<Biome> dense_larchForest = WorldRegistries.BIOMES.register(new DenseLarchForest());
    public static final RegistryObject<Biome> sparse_larchForest = WorldRegistries.BIOMES.register(new SparseLarchForest());
    public static final RegistryObject<Biome> dry_larchForest = WorldRegistries.BIOMES.register(new DryLarchForest());
    public static final RegistryObject<Biome> wet_larchForest = WorldRegistries.BIOMES.register(new WetLarchForest());
    public static final RegistryObject<Biome> swamp_larchForest = WorldRegistries.BIOMES.register(new SwampLarchForest());
    public static final RegistryObject<Biome> river_larchForest = WorldRegistries.BIOMES.register(new RiverLarchForest());
    public static final RegistryObject<Biome> lake_larchForest = WorldRegistries.BIOMES.register(new LakeLarchForest());

    public static final RegistryObject<Biome> pineForest = WorldRegistries.BIOMES.register(new PineForest());

    public static final RegistryObject<Biome> savannaShrubs = WorldRegistries.BIOMES.register(new SavannaShrubs());
    public static final RegistryObject<Biome> savannaTrees = WorldRegistries.BIOMES.register(new SavannaTrees());
    public static final RegistryObject<Biome> savannaWoodlands = WorldRegistries.BIOMES.register(new SavannaWoodlands());

    public static final RegistryObject<Biome> ancientForest = WorldRegistries.BIOMES.register(new AncientForest());
    public static final RegistryObject<Biome> jacarandaForest = WorldRegistries.BIOMES.register(new JacarandaForest());
    public static final RegistryObject<Biome> spiritForest = WorldRegistries.BIOMES.register(new SpiritForest());
    public static final RegistryObject<Biome> mixedForest = WorldRegistries.BIOMES.register(new MixedForest());

    public static final RegistryObject<Biome> apple_orchard = WorldRegistries.BIOMES.register(new AppleOrchard());
    public static final RegistryObject<Biome> pear_orchard = WorldRegistries.BIOMES.register(new PearOrchard());
    public static final RegistryObject<Biome> plum_orchard = WorldRegistries.BIOMES.register(new PlumOrchard());

    //Latest Test
    public static final RegistryObject<Biome> tall_birch_forest = WorldRegistries.BIOMES.register(new TallBirchForest());
    public static final RegistryObject<Biome> tall_birch_swamp = WorldRegistries.BIOMES.register(new BirchSwamp());
    public static final RegistryObject<Biome> abandonedFarmland = WorldRegistries.BIOMES.register(new AbandonedFarmLand());

    /**
     * Lakes
     */
    public static final RegistryObject<Biome> temperateLake = WorldRegistries.BIOMES.register(new TemperateLake());

    /**
     * Rivers
     */
    public static final RegistryObject<Biome> temperateRiver = WorldRegistries.BIOMES.register(new TemperateRiver());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Biomes");
        WorldRegistries.BIOMES.bootstrap();
    }

}
