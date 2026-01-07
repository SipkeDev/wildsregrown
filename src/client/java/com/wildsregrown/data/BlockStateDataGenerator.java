package com.wildsregrown.data;

import com.wildsregrown.blocks.GravelBlock;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.decoration.GlassWindows;
import com.wildsregrown.blocks.flora.flowers.*;
import com.wildsregrown.blocks.flora.grass.*;
import com.wildsregrown.blocks.flora.rooted.*;
import com.wildsregrown.data.blockstates.*;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.data.blockstates.libraries.FloraLibrary;
import com.wildsregrown.registries.*;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;

public class BlockStateDataGenerator extends FabricModelProvider {

    protected BlockStateDataGenerator(FabricDataOutput generator) {
        super(generator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

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
        BlockStateLibrary.singleton(generator, ModBlocks.structureBlock, idFromBlock(ModBlocks.structureBlock), Identifier.of(modid, "block/dungeon/structure_block"));
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
        Registries.BLOCK.stream().filter((block) ->
                        block.getTranslationKey().startsWith("block." + modid))
                .forEach((block) -> {

                    String id = idFromBlock(block);

                    //Handle layered instances
                    if (block instanceof SoilBlock) {
                        BlockStateLibrary.soil(generator, "block/soil/" + id, block);
                    }
                    if (block instanceof GravelBlock) {
                        BlockStateLibrary.gravel(generator, block);
                    }
                    //Handle Flora instances
                    else if (block instanceof TallGrass){
                        FloraLibrary.tallGrass(generator, block, id);
                    }
                    else if (block instanceof Grass){
                        FloraLibrary.rootedFloraOneYear(generator, block, id);
                    }
                    else if (block instanceof Nettle || block instanceof Chives){
                        FloraLibrary.rootedFloraTwoYear(generator, block, id);
                    }
                    else if (block instanceof FlowerFlora || block instanceof ColoredFlowers){
                        FloraLibrary.flower(generator, block, id);
                    }
                    //handle misc
                    else if(block instanceof GlassWindows){
                        BlockStateLibrary.axis(generator, id, block, "block/misc/" + id, "decoration/glass_window");
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

        FabricStates fBuilder = new FabricStates(generator);
        fBuilder.build(ModBlocks.linen_natural);
        fBuilder.build(ModBlocks.lace_natural);
        fBuilder.build(ModBlocks.jute_natural);
        fBuilder.build(ModBlocks.burlap_natural);
        fBuilder.build(ModBlocks.leather_natural);

        //Sources
        Block tree_source;
        tree_source = ModBlocks.larch_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "larch");
        tree_source = ModBlocks.spruce_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "spruce");
        tree_source = ModBlocks.silver_birch_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "birch");
        tree_source = ModBlocks.dwarf_birch_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "birch");
        tree_source = ModBlocks.tall_birch_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "birch");
        tree_source = ModBlocks.oak_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "oak");
        tree_source = ModBlocks.dense_oak_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "oak");
        tree_source = ModBlocks.large_oak_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "oak");
        tree_source = ModBlocks.beech_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "beech");
        tree_source = ModBlocks.ash_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "ash");
        tree_source = ModBlocks.apple_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "apple");
        tree_source = ModBlocks.pear_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "pear");
        tree_source = ModBlocks.plum_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "plum");
        tree_source = ModBlocks.weeping_willow_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "willow");
        tree_source = ModBlocks.bebb_willow_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "willow");
        tree_source = ModBlocks.ancient_oak_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "ancient_oak");
        tree_source = ModBlocks.jacaranda_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "jacaranda");
        tree_source = ModBlocks.glowing_willow_source;
        WoodGroupBlockStates.source(generator, idFromBlock(tree_source), tree_source, "glowing_willow");

        /**
         * Flora
         */

        /*
         * Shrubs
         */
        FloraLibrary.tallShrub(generator, ModBlocks.sagebush_lilac);
        FloraLibrary.tallShrub(generator, ModBlocks.sagebush_purple);
        FloraLibrary.tallShrub(generator, ModBlocks.sagebush_pink);
        FloraLibrary.tallShrub(generator, ModBlocks.sagebush_white);

        FloraLibrary.tallShrub(generator, ModBlocks.spirea_green_white);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_green_red);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_green_mauve);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_green_pink);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_golden_white);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_golden_red);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_golden_mauve);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_golden_pink);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_blue_white);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_blue_red);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_blue_mauve);
        FloraLibrary.tallShrub(generator, ModBlocks.spirea_blue_pink);

        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_white);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_white_mist);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_lime);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_red);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_orange);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_pink);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_violet);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_purple);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_blue);
        FloraLibrary.tallShrub(generator, ModBlocks.hydrangea_blue_mist);

        /* Shrubs
         */
        FloraLibrary.shrub(generator, ModBlocks.heather_yellow);
        FloraLibrary.shrub(generator, ModBlocks.heather_purple);
        FloraLibrary.shrub(generator, ModBlocks.heather_lilac);
        FloraLibrary.shrub(generator, ModBlocks.heather_mauve);
        FloraLibrary.shrub(generator, ModBlocks.heather_pink);
        FloraLibrary.shrub(generator, ModBlocks.heather_red);
        FloraLibrary.shrub(generator, ModBlocks.heather_silver);
        FloraLibrary.shrub(generator, ModBlocks.heather_white);

    }

    @Override
    public void generateItemModels(final ItemModelGenerator itemModelGenerator) {

        ItemGenerator generator = new ItemGenerator(itemModelGenerator);

        Identifier steelTexture = Identifier.of(modid, "block/metals/steel_0");

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

        itemModelGenerator.output.accept(ModItems.knife_c45, ItemModels.basic(Identifier.of(modid, "items/tools/knife")));
        itemModelGenerator.output.accept(ModItems.knife_c60, ItemModels.basic(Identifier.of(modid, "items/tools/knife")));
        itemModelGenerator.output.accept(ModItems.knife_wootz, ItemModels.basic(Identifier.of(modid, "items/tools/knife")));

        //Hoes
        itemModelGenerator.output.accept(ModItems.hoe_c45, ItemModels.basic(Identifier.of(modid, "items/tools/hoe")));
        itemModelGenerator.output.accept(ModItems.hoe_c60, ItemModels.basic(Identifier.of(modid, "items/tools/hoe")));
        itemModelGenerator.output.accept(ModItems.hoe_wootz, ItemModels.basic(Identifier.of(modid, "items/tools/hoe")));

        itemModelGenerator.output.accept(ModItems.iron_shovel, ItemModels.basic(Identifier.of(modid, "items/tools/shovel")));

        itemModelGenerator.output.accept(ModItems.wood_chisel_c45, ItemModels.basic(Identifier.of(modid, "items/tools/wood_chisel")));
        itemModelGenerator.output.accept(ModItems.stone_chisel_c45, ItemModels.basic(Identifier.of(modid, "items/tools/stone_chisel")));

        itemModelGenerator.output.accept(ModItems.scorpion_sword, ItemModels.basic(Identifier.of(modid, "items/dungeon/scorpion_sword")));


        itemModelGenerator.output.accept(ModItems.whetstone_coticule, ItemModels.basic(Identifier.of(modid, "items/tools/whetstone")));

        //Consumeables
        item = Models.GENERATED.upload(ModItems.chives_bundle, TextureMap.layer0(Identifier.of(modid,"item/consumables/chives_bundle")), itemModelGenerator.modelCollector);
        itemModelGenerator.output.accept(ModItems.chives_bundle, ItemModels.basic(item));

        item = Models.GENERATED.upload(ModItems.apple, TextureMap.layer0(Identifier.of(modid,"item/consumables/apple")), itemModelGenerator.modelCollector);
        itemModelGenerator.output.accept(ModItems.apple, ItemModels.basic(item));


        item = Models.GENERATED.upload(ModItems.atriplex_leaves, TextureMap.layer0(Identifier.of(modid,"item/consumables/atriplex_leaves")), itemModelGenerator.modelCollector);
        itemModelGenerator.output.accept(ModItems.atriplex_leaves, ItemModels.basic(item));

    }

    private void item(ItemModelGenerator generator, Item item, String path){
        generator.output.accept(item, ItemModels.basic(Identifier.of(modid, path)));
    }

}
