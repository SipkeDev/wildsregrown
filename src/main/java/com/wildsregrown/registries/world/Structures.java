package com.wildsregrown.registries.world;

import com.sipke.api.features.structures.Structure;
import com.sipke.registeries.RegistryContainer;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.structures.abandoned.*;
import com.wildsregrown.registries.world.structures.ruins.*;

/**
 * List of all spawn-able objects
 */
public class Structures {

    //Abandoned
    public static RegistryContainer<Structure> spruceCabin = WorldRegistries.STRUCTURES.register(new SpruceCabin());
    public static RegistryContainer<Structure> spruceHut = WorldRegistries.STRUCTURES.register(new SpruceHut());

    //Ruins
    public static RegistryContainer<Structure> ruinedHouse = WorldRegistries.STRUCTURES.register(new RuinedHouse());
    public static RegistryContainer<Structure> small_ruins = WorldRegistries.STRUCTURES.register(new SmallRuins());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Structures");
    }

}
