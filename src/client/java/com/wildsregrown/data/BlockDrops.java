package com.wildsregrown.data;

import com.wildsregrown.blocks.decoration.TentRoof;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.groups.ClayGroup;
import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import wildsregrown.api.block.flora.Flora;
import wildsregrown.api.block.properties.WRGProperties;

import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

class BlockDrops extends FabricBlockLootTableProvider {

    public BlockDrops(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {

        //Gravel
        add(ModBlocks.gravel_beige, layered(ModBlocks.gravel_beige));
        add(ModBlocks.gravel_black, layered(ModBlocks.gravel_black));
        add(ModBlocks.gravel_blue, layered(ModBlocks.gravel_blue));
        add(ModBlocks.gravel_brown, layered(ModBlocks.gravel_brown));
        add(ModBlocks.gravel_grey, layered(ModBlocks.gravel_grey));
        add(ModBlocks.gravel_green, layered(ModBlocks.gravel_green));
        add(ModBlocks.gravel_pink, layered(ModBlocks.gravel_pink));
        add(ModBlocks.gravel_purple, layered(ModBlocks.gravel_purple));
        add(ModBlocks.gravel_red, layered(ModBlocks.gravel_red));
        add(ModBlocks.gravel_white, layered(ModBlocks.gravel_white));
        add(ModBlocks.gravel_yellow, layered(ModBlocks.gravel_yellow));

        /**
         * Soils
         */
        //Sand
        add(ModBlocks.sand_white, layered(ModBlocks.sand_white));
        add(ModBlocks.sand_black, layered(ModBlocks.sand_black));
        add(ModBlocks.sand_beige, layered(ModBlocks.sand_beige));
        add(ModBlocks.sand_brown, layered(ModBlocks.sand_brown));
        add(ModBlocks.sand_grey, layered(ModBlocks.sand_grey));
        add(ModBlocks.sand_pink, layered(ModBlocks.sand_pink));
        add(ModBlocks.sand_red, layered(ModBlocks.sand_red));
        add(ModBlocks.sand_yellow, layered(ModBlocks.sand_yellow));
        //Loams
        add(ModBlocks.loam_red, layered(ModBlocks.loam_red));
        add(ModBlocks.loam_yellow, layered(ModBlocks.loam_yellow));
        add(ModBlocks.loam_beige, layered(ModBlocks.loam_beige));
        add(ModBlocks.loam_brown, layered(ModBlocks.loam_brown));
        add(ModBlocks.loam_black, layered(ModBlocks.loam_black));
        //Peat
        add(ModBlocks.peat_brown, layered(ModBlocks.peat_brown));
        add(ModBlocks.peat_black, layered(ModBlocks.peat_black));
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
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block.getDescriptionId().startsWith("block." + modid)) {
                if (block instanceof Flora) {
                    createShearsOnlyDrop(block);
                }
                if (block instanceof TentRoof){
                    add(block, createSingleItemTable(block.asItem()));
                }
            }});
    }


    private void buildStone(StoneGroup group, Block gravel) {

        Block cobble = group.get(StoneGroup.Common.cobble_layered);

        add(group.get(StoneGroup.Common.layered), layered(group.get(StoneGroup.Common.layered), cobble));
        add(group.get(StoneGroup.Common.stairs), createSingleItemTable(cobble));
        add(group.get(StoneGroup.Common.cobble_layered), layered(group.get(StoneGroup.Common.cobble_layered), gravel));
        add(group.get(StoneGroup.Common.cobble_stairs), createSingleItemTable(gravel));

        if (group.constructionExist()) {
            add(group.get(StoneGroup.Construction.bricks), layered(group.get(StoneGroup.Construction.bricks), cobble));
            add(group.get(StoneGroup.Construction.bricks_stairs), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.pavement), layered(group.get(StoneGroup.Construction.pavement), cobble));
            add(group.get(StoneGroup.Construction.pavement_stairs), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.cobble_bricks), layered(group.get(StoneGroup.Construction.cobble_bricks), gravel));
            add(group.get(StoneGroup.Construction.cobble_bricks_stairs), createSingleItemTable(gravel));
            add(group.get(StoneGroup.Construction.cobble_pavement), layered(group.get(StoneGroup.Construction.cobble_pavement), gravel));
            add(group.get(StoneGroup.Construction.cobble_pavement_stairs), createSingleItemTable(gravel));
            add(group.get(StoneGroup.Construction.pillar), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.thin_pillar), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_pillar), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_thin_pillar), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_wall_support), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_half_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_basic_arrow_slit), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.bricks_machicolations), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Construction.battlements), createSingleItemTable(cobble));
        }

        if (group.luxuryExist()) {
            add(group.get(StoneGroup.Luxury.smooth), layered(group.get(StoneGroup.Luxury.smooth), cobble));
            add(group.get(StoneGroup.Luxury.smooth_stairs), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.polished), layered(group.get(StoneGroup.Luxury.polished), cobble));
            add(group.get(StoneGroup.Luxury.polished_stairs), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.balustrade), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.smooth_balustrade), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.brazier), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.smooth_brazier), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.dungeonItemPedestal), createSingleItemTable(gravel));
            add(group.get(StoneGroup.Luxury.smooth_half_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.smooth_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.smooth_arrow_slit), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.polished_half_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.polished_arch), createSingleItemTable(cobble));
            add(group.get(StoneGroup.Luxury.polished_arrow_slit), createSingleItemTable(cobble));
        }

    }

    private void buildWood(WoodGroup group) {

        add(group.get(WoodGroup.Common.leaves), createShearsOnlyDrop(group.get(WoodGroup.Common.leaves)));
        //add(group.get(WoodGroup.Common.branch), dropSelf(group.get(WoodGroup.Common.beam)));
        dropSelf(group.get(WoodGroup.Common.log));
        dropSelf(group.get(WoodGroup.Common.stripped_log));
        dropSelf(group.get(WoodGroup.Common.slab));
        dropSelf(group.get(WoodGroup.Common.stripped_slab));
        //dropSelf(group.get(WoodGroup.Common.beam));
        //dropSelf(group.get(WoodGroup.Common.stripped_beam));
        add(group.get(WoodGroup.Common.planks), quarter_layered(group.get(WoodGroup.Common.planks)));
        dropSelf(group.get(WoodGroup.Common.planks_stairs));
        dropSelf(group.get(WoodGroup.Common.portable_workbench));

        if (group.framingExist()){
            add(group.get(WoodGroup.Framing.parquet), quarter_layered(group.get(WoodGroup.Framing.parquet)));
            dropSelf(group.get(WoodGroup.Framing.parquet_stairs));
            add(group.get(WoodGroup.Framing.siding), quarter_layered(group.get(WoodGroup.Framing.parquet)));
            dropSelf(group.get(WoodGroup.Framing.siding_stairs));
            //add(group.get(WoodGroup.Framing.beam_support), createSingleItemTable(group.get(WoodGroup.Common.beam)));
            dropSelf(group.get(WoodGroup.Framing.basic_half_arch));
            dropSelf(group.get(WoodGroup.Framing.basic_arch));
            dropSelf(group.get(WoodGroup.Framing.roof));
            dropSelf(group.get(WoodGroup.Framing.sod_roof));
            dropSelf(group.get(WoodGroup.Framing.basic_arrow_slit));
            dropSelf(group.get(WoodGroup.Framing.basic_ladder));
            dropSelf(group.get(WoodGroup.Framing.basic_window_cover));
            dropSelf(group.get(WoodGroup.Framing.basic_door));
        }

        if (group.furnitureExist()){
            dropSelf(group.get(WoodGroup.Furniture.stool));
            dropSelf(group.get(WoodGroup.Furniture.basic_chair));
            dropSelf(group.get(WoodGroup.Furniture.throne));
            dropSelf(group.get(WoodGroup.Furniture.basic_night_stand));
            dropSelf(group.get(WoodGroup.Furniture.basic_storage_table));
            dropSelf(group.get(WoodGroup.Furniture.basic_table));
            dropSelf(group.get(WoodGroup.Furniture.refined_table));
            dropSelf(group.get(WoodGroup.Furniture.basic_mirror));
            dropSelf(group.get(WoodGroup.Furniture.refined_mirror));
            dropSelf(group.get(WoodGroup.Furniture.basic_table_chest));
        }

        if (group.interiorExists()){
            dropSelf(group.get(WoodGroup.Interior.counter));
            dropSelf(group.get(WoodGroup.Interior.counter_shelves));
            dropSelf(group.get(WoodGroup.Interior.counter_chest));
            dropSelf(group.get(WoodGroup.Interior.cabinet));
            dropSelf(group.get(WoodGroup.Interior.cabinet_shelf));
            dropSelf(group.get(WoodGroup.Interior.shelves));
            dropSelf(group.get(WoodGroup.Interior.crate_lid));
            dropSelf(group.get(WoodGroup.Interior.crate));
            dropSelf(group.get(WoodGroup.Interior.barrel));
        }

    }

    private void buildClay(ClayGroup group) {
        add(group.soil, layered(group.soil));
    }


    public LootTable.Builder quarter_layered(Block block){
        LootPool.Builder lootTable = LootPool.lootPool()
                .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.QUARTER_LAYERS, 1))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))))
                .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.QUARTER_LAYERS, 2))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2f))))
                .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.QUARTER_LAYERS, 3))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3f))))
                .add(LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.QUARTER_LAYERS, 4))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4f))))
                ;
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, lootTable));
    }

    public LootTable.Builder layered(Block block){
        return layered(block, block);
    }
    public LootTable.Builder layered(Block block, Block drops){
        LootPool.Builder lootTable = LootPool.lootPool()
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 1))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 2))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 3))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(3f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 4))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(4f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 5))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(5f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 6))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(6f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 7))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(7f))))
                .add(LootItem.lootTableItem(drops).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WRGProperties.LAYERS, 8))).apply(SetItemCountFunction.setCount(ConstantValue.exactly(8f))))
                ;
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block, lootTable));
    }

    public LootTable.Builder withShovel(final Block withShovel, final ItemLike without) {
        return createSelfDropDispatchTable(
                withShovel,
                MatchTool.toolMatches(ItemPredicate.Builder.item().of(this.registries.lookupOrThrow(Registries.ITEM), ItemTags.SHOVELS)),
                (LootPoolEntryContainer.Builder)this.applyExplosionDecay(withShovel, ((LootPoolSingletonContainer.Builder)LootItem.lootTableItem(without))));
    }
}
