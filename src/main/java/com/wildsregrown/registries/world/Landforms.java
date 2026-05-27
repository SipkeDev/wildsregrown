package com.wildsregrown.registries.world;

import com.sipke.api.terrain.Landform;
import com.sipke.registeries.RegistryContainer;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.registries.world.landforms.Flat;
import com.wildsregrown.registries.world.landforms.coast.*;
import com.wildsregrown.registries.world.landforms.highlands.*;
import com.wildsregrown.registries.world.landforms.lowlands.*;
import com.wildsregrown.registries.world.landforms.mountains.*;
import com.wildsregrown.registries.world.landforms.ocean.Abyss;
import com.wildsregrown.registries.world.landforms.ocean.Basin;
import com.wildsregrown.registries.world.landforms.sea.Ridge;
import com.wildsregrown.registries.world.landforms.sea.Rift;

public class Landforms {

    public static final RegistryContainer<Landform> flat = WorldRegistries.LANDFORMS.register(new Flat());

    //Mountains
    public static final RegistryContainer<Landform> ancient_giants = WorldRegistries.LANDFORMS.register(new AncientGiants());
    public static final RegistryContainer<Landform> great_desert_mountains = WorldRegistries.LANDFORMS.register(new GreatDesertMountains());
    public static final RegistryContainer<Landform> desert_ridge = WorldRegistries.LANDFORMS.register(new DesertRidge());
    public static final RegistryContainer<Landform> mountain = WorldRegistries.LANDFORMS.register(new Mountain());
    public static final RegistryContainer<Landform> mountains = WorldRegistries.LANDFORMS.register(new Mountains());
    public static final RegistryContainer<Landform> old_mountains = WorldRegistries.LANDFORMS.register(new OldMountains());

    //Highlands
    public static final RegistryContainer<Landform> ardennes = WorldRegistries.LANDFORMS.register(new Ardennes());
    public static final RegistryContainer<Landform> badlands = WorldRegistries.LANDFORMS.register(new BadLands());
    public static final RegistryContainer<Landform> desert_ridges = WorldRegistries.LANDFORMS.register(new DesertRidges());
    public static final RegistryContainer<Landform> forest_hills = WorldRegistries.LANDFORMS.register(new ForestHills());
    public static final RegistryContainer<Landform> grandCanyon = WorldRegistries.LANDFORMS.register(new GrandCanyon());
    public static final RegistryContainer<Landform> great_desert_hills = WorldRegistries.LANDFORMS.register(new GreatDesertHills());
    public static final RegistryContainer<Landform> highlands = WorldRegistries.LANDFORMS.register(new HighLands());
    public static final RegistryContainer<Landform> hills = WorldRegistries.LANDFORMS.register(new Hills());
    public static final RegistryContainer<Landform> ice_sheets = WorldRegistries.LANDFORMS.register(new IceSheets());
    public static final RegistryContainer<Landform> mesa = WorldRegistries.LANDFORMS.register(new Mesa());
    public static final RegistryContainer<Landform> plateau = WorldRegistries.LANDFORMS.register(new Plateau());
    public static final RegistryContainer<Landform> rolling_hills = WorldRegistries.LANDFORMS.register(new RollingHills());

    //lowlands
    public static final RegistryContainer<Landform> desert_dunes = WorldRegistries.LANDFORMS.register(new DesertDunes());
    public static final RegistryContainer<Landform> flat_lands = WorldRegistries.LANDFORMS.register(new FlatLands());
    public static final RegistryContainer<Landform> great_plains = WorldRegistries.LANDFORMS.register(new GreatPlains());
    public static final RegistryContainer<Landform> mystic_plains = WorldRegistries.LANDFORMS.register(new MysticPlains());
    public static final RegistryContainer<Landform> plains = WorldRegistries.LANDFORMS.register(new Plains());
    public static final RegistryContainer<Landform> priaries = WorldRegistries.LANDFORMS.register(new Priaries());
    public static final RegistryContainer<Landform> tundra = WorldRegistries.LANDFORMS.register(new Tundra());
    public static final RegistryContainer<Landform> worseLands = WorldRegistries.LANDFORMS.register(new WorseLands());

    //coast
    public static final RegistryContainer<Landform> beach = WorldRegistries.LANDFORMS.register(new DesertBeach());
    public static final RegistryContainer<Landform> cliffs = WorldRegistries.LANDFORMS.register(new Cliffs());
    public static final RegistryContainer<Landform> dunes = WorldRegistries.LANDFORMS.register(new Dunes());
    public static final RegistryContainer<Landform> shallows = WorldRegistries.LANDFORMS.register(new Shallows());
    public static final RegistryContainer<Landform> tiny_cliffs = WorldRegistries.LANDFORMS.register(new TinyCliffs());

    //sea
    public static final RegistryContainer<Landform> midOceanRidge = WorldRegistries.LANDFORMS.register(new Ridge());
    public static final RegistryContainer<Landform> rift = WorldRegistries.LANDFORMS.register(new Rift());
    public static final RegistryContainer<Landform> sea = WorldRegistries.LANDFORMS.register(new Ridge());

    //ocean
    public static final RegistryContainer<Landform> abyss = WorldRegistries.LANDFORMS.register(new Abyss());
    public static final RegistryContainer<Landform> basin = WorldRegistries.LANDFORMS.register(new Basin());
    public static final RegistryContainer<Landform> ocean = WorldRegistries.LANDFORMS.register(new Basin());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Landforms");
    }

}
