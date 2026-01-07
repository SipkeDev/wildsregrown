package com.wildsregrown.data;

import com.wildsregrown.data.recipes.ToolEventBuilder;
import com.wildsregrown.items.tools.Hatchet;
import com.wildsregrown.items.tools.Pickaxe;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModTags;
import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Block;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;

import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.registries.ModItemGroups.id;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                /**
                 * Sedimentary Stones
                 */
                buildStone(itemLookup, exporter, ModBlocks.limestone_white);
                buildStone(itemLookup, exporter, ModBlocks.limestone_beige);
                buildStone(itemLookup, exporter, ModBlocks.limestone_grey);
                buildStone(itemLookup, exporter, ModBlocks.limestone_dark_grey);

                buildStone(itemLookup, exporter, ModBlocks.sandstone_white);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_grey);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_black);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_beige);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_brown);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_pink);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_red);
                buildStone(itemLookup, exporter, ModBlocks.sandstone_yellow);

                buildStone(itemLookup, exporter, ModBlocks.shale_black);
                buildStone(itemLookup, exporter, ModBlocks.shale_dark_grey);
                buildStone(itemLookup, exporter, ModBlocks.shale_grey);
                buildStone(itemLookup, exporter, ModBlocks.shale_red);

                buildStone(itemLookup, exporter, ModBlocks.travertine_white);
                buildStone(itemLookup, exporter, ModBlocks.travertine_grey);
                buildStone(itemLookup, exporter, ModBlocks.travertine_beige);

                /**
                 * Metamorphic Stones
                 */
                buildStone(itemLookup, exporter, ModBlocks.slate_black);
                buildStone(itemLookup, exporter, ModBlocks.slate_grey);
                buildStone(itemLookup, exporter, ModBlocks.slate_blue);
                buildStone(itemLookup, exporter, ModBlocks.slate_purple);
                buildStone(itemLookup, exporter, ModBlocks.slate_red);
                buildStone(itemLookup, exporter, ModBlocks.slate_green);

                buildStone(itemLookup, exporter, ModBlocks.marble_white);
                buildStone(itemLookup, exporter, ModBlocks.marble_beige);
                buildStone(itemLookup, exporter, ModBlocks.marble_black);
                buildStone(itemLookup, exporter, ModBlocks.marble_portoro);
                buildStone(itemLookup, exporter, ModBlocks.marble_green);
                buildStone(itemLookup, exporter, ModBlocks.marble_blue);

                /**
                 * Igneous Stones
                 */
                buildStone(itemLookup, exporter, ModBlocks.basalt_black);
                buildStone(itemLookup, exporter, ModBlocks.granite_white);
                buildStone(itemLookup, exporter, ModBlocks.granite_pink);
                buildStone(itemLookup, exporter, ModBlocks.granite_red);

                /**
                 * Trees
                 */
                buildWood(itemLookup, exporter, ModBlocks.apple);
                buildWood(itemLookup, exporter, ModBlocks.pear);
                buildWood(itemLookup, exporter, ModBlocks.plum);

                buildWood(itemLookup, exporter, ModBlocks.ancient_oak);
                buildWood(itemLookup, exporter, ModBlocks.jacaranda);
                buildWood(itemLookup, exporter, ModBlocks.glowing_willow);

                buildWood(itemLookup, exporter, ModBlocks.larch);
                buildWood(itemLookup, exporter, ModBlocks.spruce);
                buildWood(itemLookup, exporter, ModBlocks.birch);
                buildWood(itemLookup, exporter, ModBlocks.willow);

                buildWood(itemLookup, exporter, ModBlocks.oak);
                buildWood(itemLookup, exporter, ModBlocks.beech);
                buildWood(itemLookup, exporter, ModBlocks.ash);

                createShapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.larch.get(WoodGroup.Common.portable_workbench)) // You can also specify an int to produce more than one
                        .input(ModTags.wood_crafting_materials)
                        .input(ModTags.wood_crafting_materials)
                        .input(ModTags.wood_crafting_materials)
                        .input(ModTags.wood_crafting_materials)
                        .criterion("is_craftable", this.conditionsFromTag(ModTags.wood_crafting_materials))
                        .offerTo(exporter);

            }
        };
    }

    private void buildStone(RegistryWrapper.Impl<Item> itemLookup, RecipeExporter exporter, StoneGroup group){

        new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Common.stairs)).offerTo(exporter, id(group.get(StoneGroup.Common.stairs)));
        new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Common.cobble_stairs)).offerTo(exporter, id(group.get(StoneGroup.Common.cobble_stairs)));

        //Portable crafting, first index
        if (group.constructionExist()) {
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.split.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Construction.bricks)).offerTo(exporter, id(group.get(StoneGroup.Construction.bricks)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.bricks), group.get(StoneGroup.Construction.bricks_stairs)).offerTo(exporter, id(group.get(StoneGroup.Construction.bricks_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.mining.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Construction.pavement)).offerTo(exporter, id(group.get(StoneGroup.Construction.pavement)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.pavement), group.get(StoneGroup.Construction.pavement_stairs)).offerTo(exporter, id(group.get(StoneGroup.Construction.pavement_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.split.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Construction.cobble_bricks)).offerTo(exporter, id(group.get(StoneGroup.Construction.cobble_bricks)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.cobble_bricks), group.get(StoneGroup.Construction.cobble_bricks_stairs)).offerTo(exporter, id(group.get(StoneGroup.Construction.cobble_bricks_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.mining.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Construction.cobble_pavement)).offerTo(exporter, id(group.get(StoneGroup.Construction.cobble_pavement)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.cobble_pavement), group.get(StoneGroup.Construction.cobble_pavement_stairs)).offerTo(exporter, id(group.get(StoneGroup.Construction.cobble_pavement_stairs)));
        }

        if (group.luxuryExist()) {
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Common.layered), group.get(StoneGroup.Luxury.smooth)).offerTo(exporter, id(group.get(StoneGroup.Luxury.smooth)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Common.stairs), group.get(StoneGroup.Luxury.smooth_stairs)).offerTo(exporter, id(group.get(StoneGroup.Luxury.smooth_stairs)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Luxury.smooth), group.get(StoneGroup.Luxury.polished)).offerTo(exporter, id(group.get(StoneGroup.Luxury.polished)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Luxury.smooth_stairs), group.get(StoneGroup.Luxury.polished_stairs)).offerTo(exporter, id(group.get(StoneGroup.Luxury.polished_stairs)));
        }

        //Simple crafting
        if (group.constructionExist()){

        }

    }

    private void buildWood(RegistryWrapper.Impl<Item> itemLookup, RecipeExporter exporter, WoodGroup group) {

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.log), group.get(WoodGroup.Common.slab));
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.slab), group.get(WoodGroup.Common.beam));

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.log), group.get(WoodGroup.Common.stripped_log));
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.slab), group.get(WoodGroup.Common.stripped_slab), "_0");
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.beam), group.get(WoodGroup.Common.stripped_beam), "_0");

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_log), group.get(WoodGroup.Common.stripped_slab), "_1");
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_slab), group.get(WoodGroup.Common.stripped_beam), "_1");
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_beam), group.get(WoodGroup.Common.planks));
        //register(itemLookup, exporter, ItemTags.AXES, group.get(WoodGroup.Common.planks), Blocks.ACACIA_BUTTON);

        if (group.framingExist()){
            register(itemLookup, exporter, ModTags.wood_chisel, 0, group.get(WoodGroup.Common.planks), group.get(WoodGroup.Framing.parquet));
        }

    }

    private void register(RegistryWrapper.Impl<Item> itemLookup, RecipeExporter exporter, TagKey<Item> tool, int stance, Block material, Block result){
        register(itemLookup, exporter, tool, stance, material, result, "");
    }
    private void register(RegistryWrapper.Impl<Item> itemLookup, RecipeExporter exporter, TagKey<Item> tool, int stance, Block material, Block result, String key){
        new ToolEventBuilder(itemLookup, tool, stance, material, result).offerTo(exporter, id(result) + key);
    }

        @Override
    public String getName() {
        return "RecipeProvider";
    }

}