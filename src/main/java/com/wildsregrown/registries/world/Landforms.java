package com.wildsregrown.registries.world;

import com.sipke.api.terrain.Landform;
import com.sipke.registeries.WorldRegistries;
import com.sipke.registeries.core.RegistryObject;
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

    public static final RegistryObject<Landform> flat = WorldRegistries.LANDFORMS.register(new Flat());

    //Mountains
    public static final RegistryObject<Landform> ancient_peaks = WorldRegistries.LANDFORMS.register(new AncientPeaks());
    public static final RegistryObject<Landform> ancient_giants = WorldRegistries.LANDFORMS.register(new AncientGiants());
    public static final RegistryObject<Landform> desert_ridge = WorldRegistries.LANDFORMS.register(new DesertRidge());
    public static final RegistryObject<Landform> mountain = WorldRegistries.LANDFORMS.register(new Mountain());
    public static final RegistryObject<Landform> mountains = WorldRegistries.LANDFORMS.register(new Mountains());
    public static final RegistryObject<Landform> old_mountains = WorldRegistries.LANDFORMS.register(new OldMountains());
    public static final RegistryObject<Landform> pinnacles = WorldRegistries.LANDFORMS.register(new Pinnacles());

    //Highlands
    public static final RegistryObject<Landform> ardennes = WorldRegistries.LANDFORMS.register(new Ardennes());
    public static final RegistryObject<Landform> badlands = WorldRegistries.LANDFORMS.register(new BadLands());
    public static final RegistryObject<Landform> desert_ridges = WorldRegistries.LANDFORMS.register(new DesertRidges());
    public static final RegistryObject<Landform> forest_hills = WorldRegistries.LANDFORMS.register(new ForestHills());
    public static final RegistryObject<Landform> grandCanyon = WorldRegistries.LANDFORMS.register(new GrandCanyon());
    public static final RegistryObject<Landform> highlands = WorldRegistries.LANDFORMS.register(new HighLands());
    public static final RegistryObject<Landform> hills = WorldRegistries.LANDFORMS.register(new Hills());
    public static final RegistryObject<Landform> ice_sheets = WorldRegistries.LANDFORMS.register(new IceSheets());
    public static final RegistryObject<Landform> mesa = WorldRegistries.LANDFORMS.register(new Mesa());
    public static final RegistryObject<Landform> plateau = WorldRegistries.LANDFORMS.register(new Plateau());
    public static final RegistryObject<Landform> rolling_hills = WorldRegistries.LANDFORMS.register(new RollingHills());

    //lowlands
    public static final RegistryObject<Landform> desert_dunes = WorldRegistries.LANDFORMS.register(new DesertDunes());
    public static final RegistryObject<Landform> flat_lands = WorldRegistries.LANDFORMS.register(new FlatLands());
    public static final RegistryObject<Landform> great_plains = WorldRegistries.LANDFORMS.register(new GreatPlains());
    public static final RegistryObject<Landform> mystic_plains = WorldRegistries.LANDFORMS.register(new MysticPlains());
    public static final RegistryObject<Landform> plains = WorldRegistries.LANDFORMS.register(new Plains());
    public static final RegistryObject<Landform> priaries = WorldRegistries.LANDFORMS.register(new Priaries());
    public static final RegistryObject<Landform> tundra = WorldRegistries.LANDFORMS.register(new Tundra());
    public static final RegistryObject<Landform> worseLands = WorldRegistries.LANDFORMS.register(new WorseLands());

    //coast
    public static final RegistryObject<Landform> beach = WorldRegistries.LANDFORMS.register(new DesertBeach());
    public static final RegistryObject<Landform> cliffs = WorldRegistries.LANDFORMS.register(new Cliffs());
    public static final RegistryObject<Landform> dunes = WorldRegistries.LANDFORMS.register(new Dunes());
    public static final RegistryObject<Landform> shallows = WorldRegistries.LANDFORMS.register(new Shallows());
    public static final RegistryObject<Landform> tiny_cliffs = WorldRegistries.LANDFORMS.register(new TinyCliffs());

    //sea
    public static final RegistryObject<Landform> midOceanRidge = WorldRegistries.LANDFORMS.register(new Ridge());
    public static final RegistryObject<Landform> rift = WorldRegistries.LANDFORMS.register(new Rift());
    public static final RegistryObject<Landform> sea = WorldRegistries.LANDFORMS.register(new Ridge());

    //ocean
    public static final RegistryObject<Landform> abyss = WorldRegistries.LANDFORMS.register(new Abyss());
    public static final RegistryObject<Landform> basin = WorldRegistries.LANDFORMS.register(new Basin());
    public static final RegistryObject<Landform> ocean = WorldRegistries.LANDFORMS.register(new Basin());

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Landforms");
        WorldRegistries.LANDFORMS.bootstrap();
    }

}
