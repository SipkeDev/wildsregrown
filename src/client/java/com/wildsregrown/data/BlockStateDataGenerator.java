package com.wildsregrown.data;

import com.wildsregrown.blocks.decoration.Candles;
import com.wildsregrown.blocks.decoration.GlassPane;
import com.wildsregrown.blocks.flora.*;
import com.wildsregrown.data.blockstates.*;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.data.blockstates.libraries.FloraLibraryOld;
import com.wildsregrown.registries.*;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import wildsregrown.api.block.flora.type.GrassFlora;
import wildsregrown.api.block.materials.GravelBlock;
import wildsregrown.api.block.materials.SoilBlock;
import wildsregrown.api.client.data.libraries.FloraLibrary;
import wildsregrown.api.client.data.libraries.TreeLibrary;
import wildsregrown.api.client.data.util.TextureRef;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;

public class BlockStateDataGenerator extends FabricModelProvider {

    protected BlockStateDataGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

        StoneGroupBlockStates sBuilder = new StoneGroupBlockStates(generator);
        /**
         * Sedimentary
         */
        //Limestone
        sBuilder.build(ModBlocks.limestone_white);
        sBuilder.build(ModBlocks.limestone_beige);
        sBuilder.build(ModBlocks.limestone_grey);
        sBuilder.build(ModBlocks.limestone_dark_grey);
        //Sandstone
        sBuilder.build(ModBlocks.sandstone_white);
        sBuilder.build(ModBlocks.sandstone_grey);
        sBuilder.build(ModBlocks.sandstone_black);
        sBuilder.build(ModBlocks.sandstone_beige);
        sBuilder.build(ModBlocks.sandstone_brown);
        sBuilder.build(ModBlocks.sandstone_pink);
        sBuilder.build(ModBlocks.sandstone_red);
        sBuilder.build(ModBlocks.sandstone_yellow);
        //Shale
        sBuilder.build(ModBlocks.shale_black);
        sBuilder.build(ModBlocks.shale_dark_grey);
        sBuilder.build(ModBlocks.shale_grey);
        sBuilder.build(ModBlocks.shale_red);
        //Travertine
        sBuilder.build(ModBlocks.travertine_white);
        sBuilder.build(ModBlocks.travertine_grey);
        sBuilder.build(ModBlocks.travertine_beige);
        
        /**
         * Metamorphic stones
         */
        //Slate
        sBuilder.build(ModBlocks.slate_black);
        sBuilder.build(ModBlocks.slate_grey);
        sBuilder.build(ModBlocks.slate_blue);
        sBuilder.build(ModBlocks.slate_purple);
        sBuilder.build(ModBlocks.slate_red);
        sBuilder.build(ModBlocks.slate_green);
        //Marble
        sBuilder.build(ModBlocks.marble_white);
        sBuilder.build(ModBlocks.marble_beige);
        sBuilder.build(ModBlocks.marble_black);
        sBuilder.build(ModBlocks.marble_portoro);
        sBuilder.build(ModBlocks.marble_green);
        sBuilder.build(ModBlocks.marble_blue);

        /**
         * Igneous
         */
        //Basalt
        sBuilder.build(ModBlocks.basalt_black);
        //Granite
        sBuilder.build(ModBlocks.granite_white);
        sBuilder.build(ModBlocks.granite_pink);
        sBuilder.build(ModBlocks.granite_red);

        //Misc
        BlockStateLibrary.roof(generator, ModBlocks.thatch_roof, "thatch_roof", "block/misc/thatch_roof");
        //BlockStateLibrary.singleton(generator, ModBlocks.structureBlock, idFromBlock(ModBlocks.structureBlock),"block/dungeon/structure_block");
        BlockStateLibrary.singleton(generator, ModBlocks.portable_anvil, idFromBlock(ModBlocks.portable_anvil),"block/crafting/portable_anvil");

        //Lights
        BlockStateLibrary.torch(generator, ModBlocks.torch);

        //Ores
        Block ore;
        ore = ModBlocks.coal;
        BlockStateLibrary.layered(generator, idFromBlock(ore), "block/ores/coal", ore, false);
        ore = ModBlocks.lignite;
        BlockStateLibrary.layered(generator, idFromBlock(ore), "block/ores/lignite", ore, false);
        ore = ModBlocks.banded_iron;
        BlockStateLibrary.layered(generator, idFromBlock(ore), "block/ores/banded_iron", ore, false);
        ore = ModBlocks.anthracite;
        BlockStateLibrary.layered(generator, idFromBlock(ore), "block/ores/anthracite", ore, false);

        //Additional block model definitions, skipped otherwise:
        BuiltInRegistries.BLOCK.stream().filter((block) ->
                        block.getDescriptionId().startsWith("block." + modid))
                .forEach((block) -> {

                    String id = idFromBlock(block);

                    //Handle layered instances
                    if (block instanceof SoilBlock) {
                        BlockStateLibrary.soil(generator, id, block);
                    }
                    if (block instanceof GravelBlock) {
                        BlockStateLibrary.gravel(generator, block);
                    }
                    //Handle Flora instances
                    else if (block instanceof TallGrass){
                        FloraLibraryOld.tallGrass(generator, block, id);
                    }
                    else if (block instanceof GrassFlora){
                        FloraLibraryOld.rootedFloraOneYear(generator, block, id);
                    }
                    else if (block instanceof Nettle || block instanceof Chives){
                        FloraLibraryOld.rootedFloraTwoYear(generator, block, id);
                    }
                    else if (block instanceof Candles) {
                        BlockStateLibrary.candles(generator, id, block);
                    }else if (block instanceof GlassPane) {
                        if (block.getDescriptionId().contains("window")){
                            BlockStateLibrary.glassPane(generator, id, block, "block/misc/glass_window");
                        }else {
                            BlockStateLibrary.glassPane(generator, id, block, "block/misc/glass_frosted");
                        }
                    }
                    //SKIP
                });

        WoodGroupBlockStates wBuilder = new WoodGroupBlockStates(generator);

        //Fruit trees
        wBuilder.build(ModBlocks.apple);
        wBuilder.build(ModBlocks.pear);
        wBuilder.build(ModBlocks.plum);

        //Magic trees
        wBuilder.build(ModBlocks.ancient_oak);
        wBuilder.build(ModBlocks.jacaranda);
        wBuilder.build(ModBlocks.glowing_willow);

        //Pines
        wBuilder.build(ModBlocks.larch);
        wBuilder.build(ModBlocks.spruce);

        //Soft Woods
        wBuilder.build(ModBlocks.birch);
        wBuilder.build(ModBlocks.willow);

        //Strong Woods
        wBuilder.build(ModBlocks.oak);
        wBuilder.build(ModBlocks.beech);
        wBuilder.build(ModBlocks.ash);

        //Clays
        ClayStates cBuilder = new ClayStates(generator);
        cBuilder.build(ModBlocks.clay_yellow);
        cBuilder.build(ModBlocks.clay_red);
        cBuilder.build(ModBlocks.clay_beige);
        cBuilder.build(ModBlocks.clay_brown);
        cBuilder.build(ModBlocks.clay_black);

        MetalGroupBlockStates mBuilder = new MetalGroupBlockStates(generator);
        mBuilder.build(ModBlocks.tin);
        mBuilder.build(ModBlocks.tin_polished);
        mBuilder.build(ModBlocks.copper);
        //mBuilder.build(ModBlocks.copper_polished);
        mBuilder.build(ModBlocks.iron);
        //mBuilder.build(ModBlocks.iron_polished);
        //mBuilder.build(ModBlocks.cast_iron);
        mBuilder.build(ModBlocks.steel);
        //mBuilder.build(ModBlocks.steel_polished);
        mBuilder.build(ModBlocks.silver);
        mBuilder.build(ModBlocks.silver_polished);
        mBuilder.build(ModBlocks.gold);
        mBuilder.build(ModBlocks.gold_polished);

        //Sources
        Block tree_source;
        tree_source = ModBlocks.larch_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "larch_bark"), TextureRef.of(1, modid, log_path + "larch_wood_end"));
        tree_source = ModBlocks.spruce_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "spruce_bark"), TextureRef.of(1, modid, log_path + "spruce_wood_end"));
        tree_source = ModBlocks.silver_birch_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "birch_bark"), TextureRef.of(1, modid, log_path + "birch_wood_end"));
        tree_source = ModBlocks.dwarf_birch_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "birch_bark"), TextureRef.of(1, modid, log_path + "birch_wood_end"));
        tree_source = ModBlocks.tall_birch_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "birch_bark"), TextureRef.of(1, modid, log_path + "birch_wood_end"));
        tree_source = ModBlocks.oak_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "oak_bark"), TextureRef.of(1, modid, log_path + "oak_wood_end"));
        tree_source = ModBlocks.dense_oak_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "oak_bark"), TextureRef.of(1, modid, log_path + "oak_wood_end"));
        tree_source = ModBlocks.large_oak_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "oak_bark"), TextureRef.of(1, modid, log_path + "oak_wood_end"));
        tree_source = ModBlocks.beech_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "beech_bark"), TextureRef.of(1, modid, log_path + "beech_wood_end"));
        tree_source = ModBlocks.ash_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "ash_bark"), TextureRef.of(1, modid, log_path + "ash_wood_end"));
        tree_source = ModBlocks.apple_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "apple_bark"), TextureRef.of(1, modid, log_path + "apple_wood_end"));
        tree_source = ModBlocks.pear_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "pear_bark"), TextureRef.of(1, modid, log_path + "pear_wood_end"));
        tree_source = ModBlocks.plum_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "plum_bark"), TextureRef.of(1, modid, log_path + "plum_wood_end"));
        tree_source = ModBlocks.weeping_willow_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "willow_bark"), TextureRef.of(1, modid, log_path + "willow_wood_end"));
        tree_source = ModBlocks.bebb_willow_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "willow_bark"), TextureRef.of(1, modid, log_path + "willow_wood_end"));
        tree_source = ModBlocks.ancient_oak_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "ancient_oak_bark"), TextureRef.of(1, modid, log_path + "ancient_oak_wood_end"));
        tree_source = ModBlocks.jacaranda_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "jacaranda_bark"), TextureRef.of(1, modid, log_path + "jacaranda_wood_end"));
        tree_source = ModBlocks.glowing_willow_source;
        TreeLibrary.source(generator, modid, tree_source, TextureRef.of(0, modid,log_path + "glowing_willow_bark"), TextureRef.of(1, modid, log_path + "glowing_willow_wood_end"));

        /**
         * Flora
         */
        FloraLibrary.stagedFlower(generator, ModBlocks.daisy, modid, "daisy", "daisy", false);
        FloraLibrary.stagedFlower(generator, ModBlocks.poppy, modid, "poppy", "poppy", false);
        FloraLibrary.stagedFlower(generator, ModBlocks.dandelion, modid, "dandelion", "dandelion", false);
        FloraLibrary.stagedFlower(generator, ModBlocks.cornflower, modid, "cornflower", "cornflower", false);
        FloraLibrary.stagedFlower(generator, ModBlocks.marigold, modid, "marigold", "marigold", false);

        FloraLibrary.stagedFlower(generator, ModBlocks.artiplex_red, modid, "artiplex_red", "artiplex", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.artiplex_green, modid, "artiplex_green", "artiplex", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.artiplex_silver, modid, "artiplex_silver", "artiplex", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_white, modid, "orchid_white", "orchid", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_yellow, modid, "orchid_yellow", "orchid", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_red, modid, "orchid_red", "orchid", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_orange, modid, "orchid_orange", "orchid", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_blue, modid, "orchid_blue", "orchid", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.orchid_purple, modid, "orchid_purple", "orchid", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.campanula_white, modid, "campanula_white", "campanula", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.campanula_purple, modid, "campanula_purple", "campanula", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.campanula_pink, modid, "campanula_pink", "campanula", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.campanula_lilac, modid, "campanula_lilac", "campanula", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.campanula_blue, modid, "campanula_blue", "campanula", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.foxglove_white, modid, "foxglove_white", "foxglove", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.foxglove_pink, modid, "foxglove_pink", "foxglove", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.foxglove_purple, modid, "foxglove_purple", "foxglove", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.foxglove_yellow, modid, "foxglove_yellow", "foxglove", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.foxglove_red, modid, "foxglove_red", "foxglove", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_white, modid, "tulip_white", "tulip", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_red, modid, "tulip_red", "tulip", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_orange, modid, "tulip_orange", "tulip", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_yellow, modid, "tulip_yellow", "tulip", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_pink, modid, "tulip_pink", "tulip", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.tulip_purple, modid, "tulip_purple", "tulip", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.lily_white, modid, "lily_white", "lily", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.lily_yellow, modid, "lily_yellow", "lily", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.lily_orange, modid, "lily_orange", "lily", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.lily_red, modid, "lily_red", "lily", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.lily_purple, modid, "lily_purple", "lily", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.lily_pink, modid, "lily_pink", "lily", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.sea_holly_light, modid, "sea_holly_light", "sea_holly", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.sea_holly_silver, modid, "sea_holly_silver", "sea_holly", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.sea_holly_blue, modid, "sea_holly_blue", "sea_holly", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.blue_bottle_light, modid, "blue_bottle_light", "blue_bottle", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.blue_bottle, modid, "blue_bottle", "blue_bottle", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.blue_bottle_dark, modid, "blue_bottle_dark", "blue_bottle", true);

        FloraLibrary.stagedFlower(generator, ModBlocks.leek, modid, "leek", "leek", true);
        FloraLibrary.stagedFlower(generator, ModBlocks.oak_leaf_cabbage, modid, "oak_leaf_cabbage", "oak_leaf_cabbage", true);

        /*
         * Shrubs
         */
        FloraLibraryOld.tallShrub(generator, ModBlocks.sagebush_lilac);
        FloraLibraryOld.tallShrub(generator, ModBlocks.sagebush_purple);
        FloraLibraryOld.tallShrub(generator, ModBlocks.sagebush_pink);
        FloraLibraryOld.tallShrub(generator, ModBlocks.sagebush_white);

        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_green_white);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_green_red);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_green_mauve);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_green_pink);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_golden_white);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_golden_red);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_golden_mauve);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_golden_pink);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_blue_white);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_blue_red);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_blue_mauve);
        FloraLibraryOld.tallShrub(generator, ModBlocks.spirea_blue_pink);

        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_white);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_white_mist);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_lime);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_red);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_orange);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_pink);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_violet);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_purple);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_blue);
        FloraLibraryOld.tallShrub(generator, ModBlocks.hydrangea_blue_mist);

        /* Shrubs
         */
        FloraLibraryOld.shrub(generator, ModBlocks.heather_yellow);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_purple);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_lilac);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_mauve);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_pink);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_red);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_silver);
        FloraLibraryOld.shrub(generator, ModBlocks.heather_white);

    }

    @Override
    public void generateItemModels(final ItemModelGenerators itemModelGenerator) {

        ItemGenerator generator = new ItemGenerator(itemModelGenerator);

        Identifier steelTexture = Identifier.fromNamespaceAndPath(modid, "block/metals/steel_0");

        Identifier item;

        //Tools
        generator.registerHatchet(ModItems.hatchet_c45);
        generator.registerHatchet(ModItems.hatchet_c60);
        generator.registerHatchet(ModItems.hatchet_wootz);

        generator.registerPickAxe(ModItems.pickaxe_c45);
        generator.registerPickAxe(ModItems.pickaxe_c60);
        generator.registerPickAxe(ModItems.pickaxe_wootz);

        //Weapons
        item(itemModelGenerator, ModItems.sword_c45,"items/weapons/sword");
        item(itemModelGenerator, ModItems.sword_c60, "items/weapons/sword");
        item(itemModelGenerator, ModItems.sword_wootz, "items/weapons/sword");
        item(itemModelGenerator, ModItems.two_handed_sword_c45, "items/weapons/two_handed_sword");
        item(itemModelGenerator, ModItems.two_handed_sword_c60, "items/weapons/two_handed_sword");
        item(itemModelGenerator, ModItems.two_handed_sword_wootz, "items/weapons/two_handed_sword");

        itemModelGenerator.itemModelOutput.accept(ModItems.knife_c45, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/knife")));
        itemModelGenerator.itemModelOutput.accept(ModItems.knife_c60, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/knife")));
        itemModelGenerator.itemModelOutput.accept(ModItems.knife_wootz, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/knife")));

        //Hoes
        itemModelGenerator.itemModelOutput.accept(ModItems.hoe_c45, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/hoe")));
        itemModelGenerator.itemModelOutput.accept(ModItems.hoe_c60, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/hoe")));
        itemModelGenerator.itemModelOutput.accept(ModItems.hoe_wootz, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/hoe")));

        itemModelGenerator.itemModelOutput.accept(ModItems.iron_shovel, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/shovel")));

        itemModelGenerator.itemModelOutput.accept(ModItems.wood_chisel_c45, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/wood_chisel")));
        itemModelGenerator.itemModelOutput.accept(ModItems.stone_chisel_c45, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/stone_chisel")));

        itemModelGenerator.itemModelOutput.accept(ModItems.scorpion_sword, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/dungeon/scorpion_sword")));


        itemModelGenerator.itemModelOutput.accept(ModItems.whetstone_coticule, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, "items/tools/whetstone")));

        //Consumeables
        item = ModelTemplates.FLAT_ITEM.create(ModItems.chives_bundle, TextureMapping.layer0(Identifier.fromNamespaceAndPath(modid,"item/consumables/chives_bundle")), itemModelGenerator.modelOutput);
        itemModelGenerator.itemModelOutput.accept(ModItems.chives_bundle, ItemModelUtils.plainModel(item));

        item = ModelTemplates.FLAT_ITEM.create(ModItems.apple, TextureMapping.layer0(Identifier.fromNamespaceAndPath(modid,"item/consumables/apple")), itemModelGenerator.modelOutput);
        itemModelGenerator.itemModelOutput.accept(ModItems.apple, ItemModelUtils.plainModel(item));

        item = ModelTemplates.FLAT_ITEM.create(ModItems.atriplex_leaves, TextureMapping.layer0(Identifier.fromNamespaceAndPath(modid,"item/consumables/atriplex_leaves")), itemModelGenerator.modelOutput);
        itemModelGenerator.itemModelOutput.accept(ModItems.atriplex_leaves, ItemModelUtils.plainModel(item));

    }

    private void item(ItemModelGenerators generator, Item item, String path){
        generator.itemModelOutput.accept(item, ItemModelUtils.plainModel(Identifier.fromNamespaceAndPath(modid, path)));
    }

}
