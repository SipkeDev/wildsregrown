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
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import java.util.concurrent.CompletableFuture;

import static wildsregrown.api.client.WRGApiDataGenerator.idFromBlock;

public class RecipeProvider extends FabricRecipeProvider {

    public RecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected net.minecraft.data.recipes.RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new net.minecraft.data.recipes.RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                /**
                 * Sedimentary Stones
                 */
                buildStone(itemLookup, output, ModBlocks.limestone_white);
                buildStone(itemLookup, output, ModBlocks.limestone_beige);
                buildStone(itemLookup, output, ModBlocks.limestone_grey);
                buildStone(itemLookup, output, ModBlocks.limestone_dark_grey);

                buildStone(itemLookup, output, ModBlocks.sandstone_white);
                buildStone(itemLookup, output, ModBlocks.sandstone_grey);
                buildStone(itemLookup, output, ModBlocks.sandstone_black);
                buildStone(itemLookup, output, ModBlocks.sandstone_beige);
                buildStone(itemLookup, output, ModBlocks.sandstone_brown);
                buildStone(itemLookup, output, ModBlocks.sandstone_pink);
                buildStone(itemLookup, output, ModBlocks.sandstone_red);
                buildStone(itemLookup, output, ModBlocks.sandstone_yellow);

                buildStone(itemLookup, output, ModBlocks.shale_black);
                buildStone(itemLookup, output, ModBlocks.shale_dark_grey);
                buildStone(itemLookup, output, ModBlocks.shale_grey);
                buildStone(itemLookup, output, ModBlocks.shale_red);

                buildStone(itemLookup, output, ModBlocks.travertine_white);
                buildStone(itemLookup, output, ModBlocks.travertine_grey);
                buildStone(itemLookup, output, ModBlocks.travertine_beige);

                /**
                 * Metamorphic Stones
                 */
                buildStone(itemLookup, output, ModBlocks.slate_black);
                buildStone(itemLookup, output, ModBlocks.slate_grey);
                buildStone(itemLookup, output, ModBlocks.slate_blue);
                buildStone(itemLookup, output, ModBlocks.slate_purple);
                buildStone(itemLookup, output, ModBlocks.slate_red);
                buildStone(itemLookup, output, ModBlocks.slate_green);

                buildStone(itemLookup, output, ModBlocks.marble_white);
                buildStone(itemLookup, output, ModBlocks.marble_beige);
                buildStone(itemLookup, output, ModBlocks.marble_black);
                buildStone(itemLookup, output, ModBlocks.marble_portoro);
                buildStone(itemLookup, output, ModBlocks.marble_green);
                buildStone(itemLookup, output, ModBlocks.marble_blue);

                /**
                 * Igneous Stones
                 */
                buildStone(itemLookup, output, ModBlocks.basalt_black);
                buildStone(itemLookup, output, ModBlocks.granite_white);
                buildStone(itemLookup, output, ModBlocks.granite_pink);
                buildStone(itemLookup, output, ModBlocks.granite_red);

                /**
                 * Trees
                 */
                buildWood(itemLookup, output, ModBlocks.apple);
                buildWood(itemLookup, output, ModBlocks.pear);
                buildWood(itemLookup, output, ModBlocks.plum);

                buildWood(itemLookup, output, ModBlocks.ancient_oak);
                buildWood(itemLookup, output, ModBlocks.jacaranda);
                buildWood(itemLookup, output, ModBlocks.glowing_willow);

                buildWood(itemLookup, output, ModBlocks.larch);
                buildWood(itemLookup, output, ModBlocks.spruce);
                buildWood(itemLookup, output, ModBlocks.birch);
                buildWood(itemLookup, output, ModBlocks.willow);

                buildWood(itemLookup, output, ModBlocks.oak);
                buildWood(itemLookup, output, ModBlocks.beech);
                buildWood(itemLookup, output, ModBlocks.ash);

                shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.larch.get(WoodGroup.Common.portable_workbench)) // You can also specify an int to produce more than one
                        .requires(ModTags.wood_crafting_materials)
                        .requires(ModTags.wood_crafting_materials)
                        .requires(ModTags.wood_crafting_materials)
                        .requires(ModTags.wood_crafting_materials)
                        .unlockedBy("is_craftable", this.has(ModTags.wood_crafting_materials))
                        .save(output);

            }
        };
    }

    private void buildStone(HolderLookup.RegistryLookup<Item> itemLookup, RecipeOutput exporter, StoneGroup group){

        new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Common.stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Common.stairs)));
        new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Common.cobble_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Common.cobble_stairs)));

        //Portable crafting, first index
        if (group.constructionExist()) {
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.split.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Construction.bricks)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.bricks)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.bricks), group.get(StoneGroup.Construction.bricks_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.bricks_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.mining.ordinal(), group.get(StoneGroup.Common.layered), group.get(StoneGroup.Construction.pavement)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.pavement)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.pavement), group.get(StoneGroup.Construction.pavement_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.pavement_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.split.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Construction.cobble_bricks)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.cobble_bricks)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.cobble_bricks), group.get(StoneGroup.Construction.cobble_bricks_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.cobble_bricks_stairs)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.mining.ordinal(), group.get(StoneGroup.Common.cobble_layered), group.get(StoneGroup.Construction.cobble_pavement)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.cobble_pavement)));
            new ToolEventBuilder(itemLookup, ItemTags.PICKAXES, Pickaxe.Stances.shape.ordinal(), group.get(StoneGroup.Construction.cobble_pavement), group.get(StoneGroup.Construction.cobble_pavement_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Construction.cobble_pavement_stairs)));
        }

        if (group.luxuryExist()) {
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Common.layered), group.get(StoneGroup.Luxury.smooth)).save(exporter, idFromBlock(group.get(StoneGroup.Luxury.smooth)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Common.stairs), group.get(StoneGroup.Luxury.smooth_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Luxury.smooth_stairs)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Luxury.smooth), group.get(StoneGroup.Luxury.polished)).save(exporter, idFromBlock(group.get(StoneGroup.Luxury.polished)));
            new ToolEventBuilder(itemLookup, ModTags.whetstone, 0, group.get(StoneGroup.Luxury.smooth_stairs), group.get(StoneGroup.Luxury.polished_stairs)).save(exporter, idFromBlock(group.get(StoneGroup.Luxury.polished_stairs)));
        }

        //Simple crafting
        if (group.constructionExist()){

        }

    }

    private void buildWood(HolderLookup.RegistryLookup<Item> itemLookup, RecipeOutput exporter, WoodGroup group) {

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.log), group.get(WoodGroup.Common.slab));
        //register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.slab), group.get(WoodGroup.Common.beam));

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.log), group.get(WoodGroup.Common.stripped_log));
        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.slab), group.get(WoodGroup.Common.stripped_slab), "_0");
        //register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.debark.ordinal(), group.get(WoodGroup.Common.beam), group.get(WoodGroup.Common.stripped_beam), "_0");

        register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_log), group.get(WoodGroup.Common.stripped_slab), "_1");
        //register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_slab), group.get(WoodGroup.Common.stripped_beam), "_1");
        //register(itemLookup, exporter, ItemTags.AXES, Hatchet.Stances.split.ordinal(), group.get(WoodGroup.Common.stripped_beam), group.get(WoodGroup.Common.planks));
        //register(itemLookup, exporter, ItemTags.AXES, group.get(WoodGroup.Common.planks), Blocks.ACACIA_BUTTON);

        if (group.framingExist()){
            register(itemLookup, exporter, ModTags.wood_chisel, 0, group.get(WoodGroup.Common.planks), group.get(WoodGroup.Framing.parquet));
        }

    }

    private void register(HolderLookup.RegistryLookup<Item> itemLookup, RecipeOutput exporter, TagKey<Item> tool, int stance, Block material, Block result){
        register(itemLookup, exporter, tool, stance, material, result, "");
    }
    private void register(HolderLookup.RegistryLookup<Item> itemLookup, RecipeOutput exporter, TagKey<Item> tool, int stance, Block material, Block result, String key){
        new ToolEventBuilder(itemLookup, tool, stance, material, result).save(exporter, idFromBlock(result) + key);
    }

        @Override
    public String getName() {
        return "RecipeProvider";
    }

}