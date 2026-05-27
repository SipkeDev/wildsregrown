package com.wildsregrown.registries.world;

import com.sipke.api.terrain.Biome;
import com.sipke.registeries.RegistryContainer;
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
import com.wildsregrown.registries.world.biomes.river.ColdRiver;
import com.wildsregrown.registries.world.biomes.river.TemperateRiver;
import com.wildsregrown.registries.world.biomes.river.WarmRiver;
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

public class Biomes {
    
    public static final RegistryContainer<Biome> defaultBiome = WorldRegistries.BIOMES.register(new Swamp());

    public static final RegistryContainer<Biome> ice = WorldRegistries.BIOMES.register(new Ice());
    public static final RegistryContainer<Biome> swamp = WorldRegistries.BIOMES.register(new Swamp());
    public static final RegistryContainer<Biome> magic_swamp = WorldRegistries.BIOMES.register(new MagicSwamp());
    public static final RegistryContainer<Biome> kwelder = WorldRegistries.BIOMES.register(new Kwelder());

    //Maritime
    public static final RegistryContainer<Biome> coldSea = WorldRegistries.BIOMES.register(new ColdSea());
    public static final RegistryContainer<Biome> sea = WorldRegistries.BIOMES.register(new Sea());
    public static final RegistryContainer<Biome> warmSea = WorldRegistries.BIOMES.register(new WarmSea());

    //Desert
    public static final RegistryContainer<Biome> hotDesert = WorldRegistries.BIOMES.register(new HotDesert());
    public static final RegistryContainer<Biome> coolDesert = WorldRegistries.BIOMES.register(new CoolDesert());

    //Shrubland
    public static final RegistryContainer<Biome> hotShrubland = WorldRegistries.BIOMES.register(new HotShrubLand());
    public static final RegistryContainer<Biome> coolShrubland = WorldRegistries.BIOMES.register(new CoolShrubLand());

    //Decidious forest
    public static final RegistryContainer<Biome> sparse_beechForest = WorldRegistries.BIOMES.register(new SparseBeechForest());
    public static final RegistryContainer<Biome> dry_beechForest = WorldRegistries.BIOMES.register(new DryBeechForest());
    public static final RegistryContainer<Biome> beechForest = WorldRegistries.BIOMES.register(new BeechForest());
    public static final RegistryContainer<Biome> wet_beechForest = WorldRegistries.BIOMES.register(new WetBeechForest());
    public static final RegistryContainer<Biome> sparse_oakForest = WorldRegistries.BIOMES.register(new SparseOakForest());
    public static final RegistryContainer<Biome> dry_oakForest = WorldRegistries.BIOMES.register(new DryOakForest());
    public static final RegistryContainer<Biome> oakForest = WorldRegistries.BIOMES.register(new OakForest());
    public static final RegistryContainer<Biome> wet_oakForest = WorldRegistries.BIOMES.register(new WetOakForest());
    public static final RegistryContainer<Biome> sparse_ashForest = WorldRegistries.BIOMES.register(new SparseAshForest());
    public static final RegistryContainer<Biome> dry_ashForest = WorldRegistries.BIOMES.register(new DryAshForest());
    public static final RegistryContainer<Biome> ashForest = WorldRegistries.BIOMES.register(new AshForest());
    public static final RegistryContainer<Biome> wet_ashForest = WorldRegistries.BIOMES.register(new WetAshForest());


    //Meadows
    public static final RegistryContainer<Biome> oak_meadow = WorldRegistries.BIOMES.register(new OakMeadow());
    public static final RegistryContainer<Biome> mixed_flower_meadow = WorldRegistries.BIOMES.register(new MixedFlowerMeadow());
    public static final RegistryContainer<Biome> birch_meadow = WorldRegistries.BIOMES.register(new BirchMeadow());
    public static final RegistryContainer<Biome> campanula_meadow = WorldRegistries.BIOMES.register(new CampanulaMeadow());
    public static final RegistryContainer<Biome> dry_clover_meadow = WorldRegistries.BIOMES.register(new DryCloverMeadow());
    public static final RegistryContainer<Biome> clover_meadow = WorldRegistries.BIOMES.register(new CloverMeadow());
    public static final RegistryContainer<Biome> wet_clover_meadow = WorldRegistries.BIOMES.register(new WetCloverMeadow());
    public static final RegistryContainer<Biome> foxglove_meadow = WorldRegistries.BIOMES.register(new FoxGloveMeadow());
    public static final RegistryContainer<Biome> daisy_meadow = WorldRegistries.BIOMES.register(new DaisyMeadow());
    public static final RegistryContainer<Biome> lily_meadow = WorldRegistries.BIOMES.register(new LilyMeadow());
    public static final RegistryContainer<Biome> poppy_meadow = WorldRegistries.BIOMES.register(new PoppyMeadow());
    public static final RegistryContainer<Biome> hydrangea_meadow = WorldRegistries.BIOMES.register(new HydrangeaMeadow());
    public static final RegistryContainer<Biome> spirea_meadow = WorldRegistries.BIOMES.register(new SpireaMeadow());
    public static final RegistryContainer<Biome> dry_heather_meadow = WorldRegistries.BIOMES.register(new DryHeatherMeadow());
    public static final RegistryContainer<Biome> heather_meadow = WorldRegistries.BIOMES.register(new HeatherMeadow());
    public static final RegistryContainer<Biome> orchid_meadow = WorldRegistries.BIOMES.register(new OrchidMeadow());
    public static final RegistryContainer<Biome> tulip_fields = WorldRegistries.BIOMES.register(new TulipFields());

    //Heathers
    public static final RegistryContainer<Biome> heather_field = WorldRegistries.BIOMES.register(new HeatherFields());

    //grassfields - misc
    public static final RegistryContainer<Biome> sparse_grassfield = WorldRegistries.BIOMES.register(new SparseGrassfield());
    public static final RegistryContainer<Biome> dense_grassfield = WorldRegistries.BIOMES.register(new DenseGrassField());
    public static final RegistryContainer<Biome> dense_tall_grassfield = WorldRegistries.BIOMES.register(new DenseTallGrassField());
    public static final RegistryContainer<Biome> mixed_flower_grassfield = WorldRegistries.BIOMES.register(new MixedFlowerGrassfield());
    public static final RegistryContainer<Biome> red_flower_grassfield = WorldRegistries.BIOMES.register(new RedFlowerGrassfield());
    public static final RegistryContainer<Biome> white_flower_grassfield = WorldRegistries.BIOMES.register(new WhiteFlowerGrassfield());
    public static final RegistryContainer<Biome> savanna_grassfield = WorldRegistries.BIOMES.register(new SavannaGrassfield());
    //grassfields - temperate
    public static final RegistryContainer<Biome> curly_grassfield = WorldRegistries.BIOMES.register(new CurlyGrassfield());
    public static final RegistryContainer<Biome> grassfield = WorldRegistries.BIOMES.register(new Grassfield());
    public static final RegistryContainer<Biome> thin_grassfield = WorldRegistries.BIOMES.register(new ThinGrassfield());
    public static final RegistryContainer<Biome> tall_curly_grassfield = WorldRegistries.BIOMES.register(new TallCurlyGrassfield());
    public static final RegistryContainer<Biome> tall_grassfield = WorldRegistries.BIOMES.register(new TallGrassfield());
    public static final RegistryContainer<Biome> tall_thin_grassfield = WorldRegistries.BIOMES.register(new TallThinGrassfield());
    //grassfields - wet
    public static final RegistryContainer<Biome> wet_curly_grassfield = WorldRegistries.BIOMES.register(new WetCurlyGrassfield());
    public static final RegistryContainer<Biome> wet_grassfield = WorldRegistries.BIOMES.register(new WetGrassfield());
    public static final RegistryContainer<Biome> wet_thin_grassfield = WorldRegistries.BIOMES.register(new WetThinGrassfield());
    public static final RegistryContainer<Biome> wet_tall_curly_grassfield = WorldRegistries.BIOMES.register(new WetTallCurlyGrassfield());
    public static final RegistryContainer<Biome> wet_tall_grassfield = WorldRegistries.BIOMES.register(new WetTallGrassfield());
    public static final RegistryContainer<Biome> wet_tall_thin_grassfield = WorldRegistries.BIOMES.register(new WetTallThinGrassfield());
    //grassfields - dry
    public static final RegistryContainer<Biome> dry_curly_grassfield = WorldRegistries.BIOMES.register(new DryCurlyGrassfield());
    public static final RegistryContainer<Biome> dry_grassfield = WorldRegistries.BIOMES.register(new DryGrassfield());
    public static final RegistryContainer<Biome> dry_thin_grassfield = WorldRegistries.BIOMES.register(new DryThinGrassfield());
    public static final RegistryContainer<Biome> dry_tall_curly_grassfield = WorldRegistries.BIOMES.register(new DryTallCurlyGrassfield());
    public static final RegistryContainer<Biome> dry_tall_grassfield = WorldRegistries.BIOMES.register(new DryTallGrassfield());
    public static final RegistryContainer<Biome> dry_tall_thin_grassfield = WorldRegistries.BIOMES.register(new DryTallThinGrassfield());

    //Shrubs
    public static final RegistryContainer<Biome> dry_sageshrub_plains = WorldRegistries.BIOMES.register(new DrySageShrubPlains());
    public static final RegistryContainer<Biome> sageshrub_plains = WorldRegistries.BIOMES.register(new SageShrubPlains());
    public static final RegistryContainer<Biome> wet_sageshrub_plains = WorldRegistries.BIOMES.register(new WetSageShrubPlains());

    //Spruce forests
    public static final RegistryContainer<Biome> dry_spruceForest = WorldRegistries.BIOMES.register(new DrySpruceForest());
    public static final RegistryContainer<Biome> spruceForest = WorldRegistries.BIOMES.register(new SpruceForest());
    public static final RegistryContainer<Biome> wet_spruceForest = WorldRegistries.BIOMES.register(new WetSpruceForest());


    //Larch Forests
    public static final RegistryContainer<Biome> larchForest = WorldRegistries.BIOMES.register(new LarchForest());
    public static final RegistryContainer<Biome> larchForest_flowers = WorldRegistries.BIOMES.register(new LarchForestFlowers());
    public static final RegistryContainer<Biome> larchForest_artiplex = WorldRegistries.BIOMES.register(new LarchForestArtiPlex());
    public static final RegistryContainer<Biome> dense_larchForest = WorldRegistries.BIOMES.register(new DenseLarchForest());
    public static final RegistryContainer<Biome> sparse_larchForest = WorldRegistries.BIOMES.register(new SparseLarchForest());
    public static final RegistryContainer<Biome> dry_larchForest = WorldRegistries.BIOMES.register(new DryLarchForest());
    public static final RegistryContainer<Biome> wet_larchForest = WorldRegistries.BIOMES.register(new WetLarchForest());
    public static final RegistryContainer<Biome> swamp_larchForest = WorldRegistries.BIOMES.register(new SwampLarchForest());
    public static final RegistryContainer<Biome> river_larchForest = WorldRegistries.BIOMES.register(new RiverLarchForest());
    public static final RegistryContainer<Biome> lake_larchForest = WorldRegistries.BIOMES.register(new LakeLarchForest());

    public static final RegistryContainer<Biome> pineForest = WorldRegistries.BIOMES.register(new PineForest());

    public static final RegistryContainer<Biome> savannaShrubs = WorldRegistries.BIOMES.register(new SavannaShrubs());
    public static final RegistryContainer<Biome> savannaTrees = WorldRegistries.BIOMES.register(new SavannaTrees());
    public static final RegistryContainer<Biome> savannaWoodlands = WorldRegistries.BIOMES.register(new SavannaWoodlands());

    public static final RegistryContainer<Biome> ancientForest = WorldRegistries.BIOMES.register(new AncientForest());
    public static final RegistryContainer<Biome> jacarandaForest = WorldRegistries.BIOMES.register(new JacarandaForest());
    public static final RegistryContainer<Biome> spiritForest = WorldRegistries.BIOMES.register(new SpiritForest());
    public static final RegistryContainer<Biome> mixedForest = WorldRegistries.BIOMES.register(new MixedForest());

    public static final RegistryContainer<Biome> apple_orchard = WorldRegistries.BIOMES.register(new AppleOrchard());
    public static final RegistryContainer<Biome> pear_orchard = WorldRegistries.BIOMES.register(new PearOrchard());
    public static final RegistryContainer<Biome> plum_orchard = WorldRegistries.BIOMES.register(new PlumOrchard());

    //Latest Test
    public static final RegistryContainer<Biome> tall_birch_forest = WorldRegistries.BIOMES.register(new TallBirchForest());
    public static final RegistryContainer<Biome> tall_birch_swamp = WorldRegistries.BIOMES.register(new BirchSwamp());
    public static final RegistryContainer<Biome> abandonedFarmland = WorldRegistries.BIOMES.register(new AbandonedFarmLand());

    /**
     * Lakes
     */
    public static final RegistryContainer<Biome> temperateLake = WorldRegistries.BIOMES.register(new TemperateLake());

    /**
     * Rivers
     */
    public static final RegistryContainer<Biome> warmRiver = WorldRegistries.BIOMES.register(new WarmRiver());
    public static final RegistryContainer<Biome> temperateRiver = WorldRegistries.BIOMES.register(new TemperateRiver());
    public static final RegistryContainer<Biome> coldRiver = WorldRegistries.BIOMES.register(new ColdRiver());


    public static void init(){
        WildsRegrown.LOGGER.info("Registered Biomes");
    }

}
