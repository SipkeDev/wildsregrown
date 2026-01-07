package com.wildsregrown.data;

import com.wildsregrown.blocks.decoration.TentRoof;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.groups.ClayGroup;
import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

class BlockDrops extends FabricBlockLootTableProvider {

    public BlockDrops(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        //Gravel
        addDrop(ModBlocks.gravel_beige, layered(ModBlocks.gravel_beige));
        addDrop(ModBlocks.gravel_black, layered(ModBlocks.gravel_black));
        addDrop(ModBlocks.gravel_blue, layered(ModBlocks.gravel_blue));
        addDrop(ModBlocks.gravel_brown, layered(ModBlocks.gravel_brown));
        addDrop(ModBlocks.gravel_grey, layered(ModBlocks.gravel_grey));
        addDrop(ModBlocks.gravel_green, layered(ModBlocks.gravel_green));
        addDrop(ModBlocks.gravel_pink, layered(ModBlocks.gravel_pink));
        addDrop(ModBlocks.gravel_purple, layered(ModBlocks.gravel_purple));
        addDrop(ModBlocks.gravel_red, layered(ModBlocks.gravel_red));
        addDrop(ModBlocks.gravel_white, layered(ModBlocks.gravel_white));
        addDrop(ModBlocks.gravel_yellow, layered(ModBlocks.gravel_yellow));

        /**
         * Soils
         */
        //Sand
        addDrop(ModBlocks.sand_white, layered(ModBlocks.sand_white));
        addDrop(ModBlocks.sand_black, layered(ModBlocks.sand_black));
        addDrop(ModBlocks.sand_beige, layered(ModBlocks.sand_beige));
        addDrop(ModBlocks.sand_brown, layered(ModBlocks.sand_brown));
        addDrop(ModBlocks.sand_grey, layered(ModBlocks.sand_grey));
        addDrop(ModBlocks.sand_pink, layered(ModBlocks.sand_pink));
        addDrop(ModBlocks.sand_red, layered(ModBlocks.sand_red));
        addDrop(ModBlocks.sand_yellow, layered(ModBlocks.sand_yellow));
        //Loams
        addDrop(ModBlocks.loam_red, layered(ModBlocks.loam_red));
        addDrop(ModBlocks.loam_yellow, layered(ModBlocks.loam_yellow));
        addDrop(ModBlocks.loam_beige, layered(ModBlocks.loam_beige));
        addDrop(ModBlocks.loam_brown, layered(ModBlocks.loam_brown));
        addDrop(ModBlocks.loam_black, layered(ModBlocks.loam_black));
        //Peat
        addDrop(ModBlocks.peat_brown, layered(ModBlocks.peat_brown));
        addDrop(ModBlocks.peat_black, layered(ModBlocks.peat_black));
        //Clays
        buildClay(ModBlocks.clay_beige);
        buildClay(ModBlocks.clay_brown);
        buildClay(ModBlocks.clay_black);
        buildClay(ModBlocks.clay_red);
        buildClay(ModBlocks.clay_yellow);

        /**
         * Sedimentary Stones
         */
        buildStone(ModBlocks.limestone_white, ModBlocks.gravel_white);
        buildStone(ModBlocks.limestone_beige, ModBlocks.gravel_beige);
        buildStone(ModBlocks.limestone_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.limestone_dark_grey, ModBlocks.gravel_grey);

        buildStone(ModBlocks.sandstone_white, ModBlocks.gravel_white);
        buildStone(ModBlocks.sandstone_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.sandstone_black, ModBlocks.gravel_black);
        buildStone(ModBlocks.sandstone_beige, ModBlocks.gravel_beige);
        buildStone(ModBlocks.sandstone_brown, ModBlocks.gravel_brown);
        buildStone(ModBlocks.sandstone_pink, ModBlocks.gravel_pink);
        buildStone(ModBlocks.sandstone_red, ModBlocks.gravel_red);
        buildStone(ModBlocks.sandstone_yellow, ModBlocks.gravel_yellow);

        buildStone(ModBlocks.shale_black, ModBlocks.gravel_black);
        buildStone(ModBlocks.shale_dark_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.shale_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.shale_red, ModBlocks.gravel_red);

        buildStone(ModBlocks.travertine_white, ModBlocks.gravel_white);
        buildStone(ModBlocks.travertine_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.travertine_beige, ModBlocks.gravel_beige);

        /**
         * Metamorphic stones
         */

        buildStone(ModBlocks.slate_black, ModBlocks.gravel_black);
        buildStone(ModBlocks.slate_grey, ModBlocks.gravel_grey);
        buildStone(ModBlocks.slate_blue, ModBlocks.gravel_blue);
        buildStone(ModBlocks.slate_purple, ModBlocks.gravel_purple);
        buildStone(ModBlocks.slate_red, ModBlocks.gravel_red);
        buildStone(ModBlocks.slate_green, ModBlocks.gravel_green);

        buildStone(ModBlocks.marble_white, ModBlocks.gravel_white);
        buildStone(ModBlocks.marble_beige, ModBlocks.gravel_beige);
        buildStone(ModBlocks.marble_black, ModBlocks.gravel_black);
        buildStone(ModBlocks.marble_portoro, ModBlocks.gravel_black);
        buildStone(ModBlocks.marble_green, ModBlocks.gravel_green);
        buildStone(ModBlocks.marble_blue, ModBlocks.gravel_blue);

        /**
         * Igneous stones
         */
        buildStone(ModBlocks.basalt_black, ModBlocks.gravel_black);
        buildStone(ModBlocks.granite_white, ModBlocks.gravel_white);
        buildStone(ModBlocks.granite_pink, ModBlocks.gravel_pink);
        buildStone(ModBlocks.granite_red, ModBlocks.gravel_red);

        /**
         * Wood Registry
         */
        //Fruits
        buildWood(ModBlocks.apple);
        buildWood(ModBlocks.pear);
        buildWood(ModBlocks.plum);
        //magic
        buildWood(ModBlocks.ancient_oak);
        buildWood(ModBlocks.jacaranda);
        buildWood(ModBlocks.glowing_willow);
        //pines
        buildWood(ModBlocks.larch);
        buildWood(ModBlocks.spruce);
        //soft
        buildWood(ModBlocks.birch);
        buildWood(ModBlocks.willow);
        //hard
        buildWood(ModBlocks.oak);
        buildWood(ModBlocks.beech);
        buildWood(ModBlocks.ash);

        /**
         * Filter registries
         */
        Registries.BLOCK.forEach(block -> {
            if (block.getTranslationKey().startsWith("block." + modid)) {
                if (block instanceof Flora) {
                    dropsWithShears(block);
                }
                if (block instanceof TentRoof){
                    addDrop(block, drops(block.asItem()));
                }
            }});
    }


    private void buildStone(StoneGroup group, Block gravel) {

        Block cobble = group.get(StoneGroup.Common.cobble_layered);

        addDrop(group.get(StoneGroup.Common.layered), layered(group.get(StoneGroup.Common.layered), cobble));
        addDrop(group.get(StoneGroup.Common.stairs), drops(cobble));
        addDrop(group.get(StoneGroup.Common.cobble_layered), layered(group.get(StoneGroup.Common.cobble_layered), gravel));
        addDrop(group.get(StoneGroup.Common.cobble_stairs), drops(gravel));

        if (group.constructionExist()) {
            addDrop(group.get(StoneGroup.Construction.bricks), layered(group.get(StoneGroup.Construction.bricks), cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_stairs), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.pavement), layered(group.get(StoneGroup.Construction.pavement), cobble));
            addDrop(group.get(StoneGroup.Construction.pavement_stairs), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.cobble_bricks), layered(group.get(StoneGroup.Construction.cobble_bricks), gravel));
            addDrop(group.get(StoneGroup.Construction.cobble_bricks_stairs), drops(gravel));
            addDrop(group.get(StoneGroup.Construction.cobble_pavement), layered(group.get(StoneGroup.Construction.cobble_pavement), gravel));
            addDrop(group.get(StoneGroup.Construction.cobble_pavement_stairs), drops(gravel));
            addDrop(group.get(StoneGroup.Construction.pillar), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.thin_pillar), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_pillar), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_thin_pillar), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_wall_support), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_half_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_arrow_slit), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.bricks_machicolations), drops(cobble));
            addDrop(group.get(StoneGroup.Construction.battlements), drops(cobble));
        }

        if (group.luxuryExist()) {
            addDrop(group.get(StoneGroup.Luxury.smooth), layered(group.get(StoneGroup.Luxury.smooth), cobble));
            addDrop(group.get(StoneGroup.Luxury.smooth_stairs), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.polished), layered(group.get(StoneGroup.Luxury.polished), cobble));
            addDrop(group.get(StoneGroup.Luxury.polished_stairs), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.balustrade), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.smooth_balustrade), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.brazier), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.smooth_brazier), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.dungeonItemPedestal), drops(gravel));
            addDrop(group.get(StoneGroup.Luxury.smooth_half_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.smooth_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.smooth_arrow_slit), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.polished_half_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.polished_arch), drops(cobble));
            addDrop(group.get(StoneGroup.Luxury.polished_arrow_slit), drops(cobble));
        }

    }

    private void buildWood(WoodGroup group) {

        addDrop(group.get(WoodGroup.Common.leaves), dropsWithShears(group.get(WoodGroup.Common.leaves)));
        addDrop(group.get(WoodGroup.Common.branch), drops(group.get(WoodGroup.Common.beam)));
        addDrop(group.get(WoodGroup.Common.log));
        addDrop(group.get(WoodGroup.Common.stripped_log));
        addDrop(group.get(WoodGroup.Common.slab));
        addDrop(group.get(WoodGroup.Common.stripped_slab));
        addDrop(group.get(WoodGroup.Common.beam));
        addDrop(group.get(WoodGroup.Common.stripped_beam));
        addDrop(group.get(WoodGroup.Common.planks), quarter_layered(group.get(WoodGroup.Common.planks)));
        addDrop(group.get(WoodGroup.Common.planks_stairs));
        addDrop(group.get(WoodGroup.Common.portable_workbench));

        if (group.framingExist()){
            addDrop(group.get(WoodGroup.Framing.parquet), quarter_layered(group.get(WoodGroup.Framing.parquet)));
            addDrop(group.get(WoodGroup.Framing.parquet_stairs));
            addDrop(group.get(WoodGroup.Framing.siding), quarter_layered(group.get(WoodGroup.Framing.parquet)));
            addDrop(group.get(WoodGroup.Framing.siding_stairs));
            addDrop(group.get(WoodGroup.Framing.support), drops(group.get(WoodGroup.Common.beam)));
            addDrop(group.get(WoodGroup.Framing.half_arch));
            addDrop(group.get(WoodGroup.Framing.arch));
            addDrop(group.get(WoodGroup.Framing.roof));
            addDrop(group.get(WoodGroup.Framing.sod_roof));
            addDrop(group.get(WoodGroup.Framing.arrow_slit));
            addDrop(group.get(WoodGroup.Framing.ladder));
            addDrop(group.get(WoodGroup.Framing.window_cover));
            //addDrop(group.get(WoodGroup.Framing.door));
            addDrop(group.get(WoodGroup.Framing.crate_lid));
            addDrop(group.get(WoodGroup.Framing.crate));
        }

        if (group.furnitureExist()){
            addDrop(group.get(WoodGroup.Furniture.night_stand));
            addDrop(group.get(WoodGroup.Furniture.drawer));
            addDrop(group.get(WoodGroup.Furniture.storage_table));
            addDrop(group.get(WoodGroup.Furniture.stool));
            addDrop(group.get(WoodGroup.Furniture.chair));
            addDrop(group.get(WoodGroup.Furniture.table));
            addDrop(group.get(WoodGroup.Furniture.counter));
            addDrop(group.get(WoodGroup.Furniture.counter_shelves));
            addDrop(group.get(WoodGroup.Furniture.counter_chest));
            addDrop(group.get(WoodGroup.Furniture.cabinet));
            addDrop(group.get(WoodGroup.Furniture.cabinet_shelf));
            addDrop(group.get(WoodGroup.Furniture.shelves));
            addDrop(group.get(WoodGroup.Furniture.throne));
            addDrop(group.get(WoodGroup.Furniture.mirror));
            addDrop(group.get(WoodGroup.Furniture.table_chest));
        }

        if (group.utensilsExists()){
            addDrop(group.get(WoodGroup.Utensils.mug));
            addDrop(group.get(WoodGroup.Utensils.bowl));
        }

    }

    private void buildClay(ClayGroup group) {

        addDrop(group.soil, layered(group.soil));

    }


    public LootTable.Builder quarter_layered(Block block){
        LootPool.Builder lootTable = LootPool.builder()
                .with(ItemEntry.builder(block).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.QUARTER_LAYERS, 1))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f))))
                .with(ItemEntry.builder(block).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.QUARTER_LAYERS, 2))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2f))))
                .with(ItemEntry.builder(block).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.QUARTER_LAYERS, 3))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3f))))
                .with(ItemEntry.builder(block).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.QUARTER_LAYERS, 4))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4f))))
                ;
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(block, lootTable));
    }

    public LootTable.Builder layered(Block block){
        return layered(block, block);
    }
    public LootTable.Builder layered(Block block, Block drops){
        LootPool.Builder lootTable = LootPool.builder()
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 1))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 2))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 3))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 4))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(4f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 5))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(5f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 6))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(6f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 7))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(7f))))
                .with(ItemEntry.builder(drops).conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(ModProperties.LAYERS, 8))).apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(8f))))
                ;
        return LootTable.builder().pool(this.addSurvivesExplosionCondition(block, lootTable));
    }

    public LootTable.Builder withShovel(final Block withShovel, final ItemConvertible without) {
        return drops(
                withShovel,
                MatchToolLootCondition.builder(ItemPredicate.Builder.create().tag(this.registries.getOrThrow(RegistryKeys.ITEM), ItemTags.SHOVELS)),
                (LootPoolEntry.Builder)this.applyExplosionDecay(withShovel, ((LeafEntry.Builder)ItemEntry.builder(without))));
    }
}
