package com.wildsregrown.registries.world;

import com.sipke.api.features.structures.Structure;
import com.sipke.registeries.WorldRegistries;
import com.sipke.registeries.core.RegistryObject;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.structures.abandoned.*;
import com.wildsregrown.registries.world.structures.castle.RuinedCliffCastle;
import com.wildsregrown.registries.world.structures.ruins.*;

/**
 * List of all spawn-able objects
 */
public class Structures {

    //Abandoned
    public static RegistryObject<Structure> larchHouse = WorldRegistries.STRUCTURES.register(new LarchHouse());
    public static RegistryObject<Structure> larchHut = WorldRegistries.STRUCTURES.register(new LarchHut());
    public static RegistryObject<Structure> red_sandstone_house = WorldRegistries.STRUCTURES.register(new RedSandstoneHouse());
    public static RegistryObject<Structure> spruceCabin = WorldRegistries.STRUCTURES.register(new SpruceCabin());
    public static RegistryObject<Structure> smallSpruceCabin = WorldRegistries.STRUCTURES.register(new SmallSpruceCabin());
    public static RegistryObject<Structure> spruceHut = WorldRegistries.STRUCTURES.register(new SpruceHut());
    public static RegistryObject<Structure> abandonedOutpost = WorldRegistries.STRUCTURES.register(new AbandonedOutPost());
    public static RegistryObject<Structure> cliffOutpost = WorldRegistries.STRUCTURES.register(new CliffOutpost());

    //Ruins
    public static RegistryObject<Structure> altar = WorldRegistries.STRUCTURES.register(new Altar());
    public static RegistryObject<Structure> ruinedHouse = WorldRegistries.STRUCTURES.register(new RuinedHouse());
    public static RegistryObject<Structure> small_ruins = WorldRegistries.STRUCTURES.register(new SmallRuins());
    public static RegistryObject<Structure> ruinedCastle = WorldRegistries.STRUCTURES.register(new RuinedCliffCastle());
    public static RegistryObject<Structure> savannaTemple = WorldRegistries.STRUCTURES.register(new SavannaTemple());
    public static RegistryObject<Structure> whiteTempleRuins = WorldRegistries.STRUCTURES.register(new WhiteTempleRuins());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Structures");
        WorldRegistries.STRUCTURES.bootstrap();
    }

}
