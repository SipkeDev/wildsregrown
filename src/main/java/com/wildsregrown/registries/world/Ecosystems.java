package com.wildsregrown.registries.world;

import com.sipke.api.terrain.Ecosystem;
import com.sipke.registeries.RegistryContainer;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.ecosystem.chaparral.Grasslands;
import com.wildsregrown.registries.world.ecosystem.chaparral.Veluwe;
import com.wildsregrown.registries.world.ecosystem.desert.CoolDesert;
import com.wildsregrown.registries.world.ecosystem.desert.HotDesert;
import com.wildsregrown.registries.world.ecosystem.forest.ancient.AncientForest;
import com.wildsregrown.registries.world.ecosystem.forest.ancient.MagicOrchard;
import com.wildsregrown.registries.world.ecosystem.forest.coniferious.LarchForest;
import com.wildsregrown.registries.world.ecosystem.forest.coniferious.PineForest;
import com.wildsregrown.registries.world.ecosystem.forest.coniferious.SpruceForest;
import com.wildsregrown.registries.world.ecosystem.forest.decidious.*;
import com.wildsregrown.registries.world.ecosystem.forest.mixed.DrintskFryskWold;
import com.wildsregrown.registries.world.ecosystem.forest.mixed.KuunderForest;
import com.wildsregrown.registries.world.ecosystem.forest.mixed.MixedFlowerFields;
import com.wildsregrown.registries.world.ecosystem.forest.mixed.TallBirchForest;
import com.wildsregrown.registries.world.ecosystem.forest.spirit.SpiritForest;
import com.wildsregrown.registries.world.ecosystem.ice.Ice;
import com.wildsregrown.registries.world.ecosystem.ice.PolarDesert;
import com.wildsregrown.registries.world.ecosystem.ocean.ColdOcean;
import com.wildsregrown.registries.world.ecosystem.ocean.Ocean;
import com.wildsregrown.registries.world.ecosystem.ocean.WarmOcean;
import com.wildsregrown.registries.world.ecosystem.savanna.Savanna;
import com.wildsregrown.registries.world.ecosystem.sea.ColdSea;
import com.wildsregrown.registries.world.ecosystem.sea.Sea;
import com.wildsregrown.registries.world.ecosystem.sea.WarmSea;
import com.wildsregrown.registries.world.ecosystem.shrubland.CoolShrubLands;
import com.wildsregrown.registries.world.ecosystem.shrubland.HotShrubLands;
import com.wildsregrown.registries.world.ecosystem.steppe.Plains;
import com.wildsregrown.registries.world.ecosystem.steppe.SageBushSteppe;
import com.wildsregrown.registries.world.ecosystem.tundra.Tundra;

public class Ecosystems{
    
    //Steppe
    public static final RegistryContainer<Ecosystem> plains = WorldRegistries.ECOSYSTEMS.register(new Plains());
    public static final RegistryContainer<Ecosystem> sagebush_steppe = WorldRegistries.ECOSYSTEMS.register(new SageBushSteppe());

    //Savanna
    public static final RegistryContainer<Ecosystem> savanna = WorldRegistries.ECOSYSTEMS.register(new Savanna());

    //Chapparal
    public static final RegistryContainer<Ecosystem> grasslands = WorldRegistries.ECOSYSTEMS.register(new Grasslands());
    public static final RegistryContainer<Ecosystem> veluwe = WorldRegistries.ECOSYSTEMS.register(new Veluwe());

    //mixed forest
    public static final RegistryContainer<Ecosystem> drintsk_frysk_wold = WorldRegistries.ECOSYSTEMS.register(new DrintskFryskWold());
    public static final RegistryContainer<Ecosystem> kuunderForest = WorldRegistries.ECOSYSTEMS.register(new KuunderForest());
    public static final RegistryContainer<Ecosystem> tallBirchForest = WorldRegistries.ECOSYSTEMS.register(new TallBirchForest());
    public static final RegistryContainer<Ecosystem> flower_fields = WorldRegistries.ECOSYSTEMS.register(new MixedFlowerFields());

    //Decidious forest
    public static final RegistryContainer<Ecosystem> oakForest = WorldRegistries.ECOSYSTEMS.register(new OakForest());
    public static final RegistryContainer<Ecosystem> beechForest = WorldRegistries.ECOSYSTEMS.register(new BeechForest());
    public static final RegistryContainer<Ecosystem> decidious_forest = WorldRegistries.ECOSYSTEMS.register(new DecidiousForest());
    public static final RegistryContainer<Ecosystem> decidious_grassfield = WorldRegistries.ECOSYSTEMS.register(new DecidiousGrassfield());
    public static final RegistryContainer<Ecosystem> ash_forest = WorldRegistries.ECOSYSTEMS.register(new AshForest());

    //Coniferious forest
    public static final RegistryContainer<Ecosystem> pineForest = WorldRegistries.ECOSYSTEMS.register(new PineForest());
    public static final RegistryContainer<Ecosystem> larchForest = WorldRegistries.ECOSYSTEMS.register(new LarchForest());
    public static final RegistryContainer<Ecosystem> spruceForest = WorldRegistries.ECOSYSTEMS.register(new SpruceForest());

    //Ancient forest
    public static final RegistryContainer<Ecosystem> ancientForest = WorldRegistries.ECOSYSTEMS.register(new AncientForest());
    public static final RegistryContainer<Ecosystem> magicOrchard = WorldRegistries.ECOSYSTEMS.register(new MagicOrchard());

    //Spirit Forest
    public static final RegistryContainer<Ecosystem> spiritForest = WorldRegistries.ECOSYSTEMS.register(new SpiritForest());

    //Hot ShrubLands
    public static final RegistryContainer<Ecosystem> hotShrubLands = WorldRegistries.ECOSYSTEMS.register(new HotShrubLands());

    //Cool shrubland
    public static final RegistryContainer<Ecosystem> coolShrubLands = WorldRegistries.ECOSYSTEMS.register(new CoolShrubLands());

    //hot desert
    public static final RegistryContainer<Ecosystem> hotDesert = WorldRegistries.ECOSYSTEMS.register(new HotDesert());

    //cool desert
    public static final RegistryContainer<Ecosystem> coolDesert = WorldRegistries.ECOSYSTEMS.register(new CoolDesert());

    //polar desert
    public static final RegistryContainer<Ecosystem> polarDesert = WorldRegistries.ECOSYSTEMS.register(new PolarDesert());

    //tundra
    public static final RegistryContainer<Ecosystem> tundra = WorldRegistries.ECOSYSTEMS.register(new Tundra());

    //ice
    public static final RegistryContainer<Ecosystem> ice = WorldRegistries.ECOSYSTEMS.register(new Ice());

    //Maritime
    public static final RegistryContainer<Ecosystem> coldSea = WorldRegistries.ECOSYSTEMS.register(new ColdSea());
    public static final RegistryContainer<Ecosystem> sea = WorldRegistries.ECOSYSTEMS.register(new Sea());
    public static final RegistryContainer<Ecosystem> warmSea = WorldRegistries.ECOSYSTEMS.register(new WarmSea());
    public static final RegistryContainer<Ecosystem> coldOcean = WorldRegistries.ECOSYSTEMS.register(new ColdOcean());
    public static final RegistryContainer<Ecosystem> ocean = WorldRegistries.ECOSYSTEMS.register(new Ocean());
    public static final RegistryContainer<Ecosystem> warmOcean = WorldRegistries.ECOSYSTEMS.register(new WarmOcean());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Ecosystems");
    }

}
