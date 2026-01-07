package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.dungeon.StructureEntity;
import com.wildsregrown.blocks.wood.tree.TreeEntity;
import com.wildsregrown.entities.block.*;
import com.wildsregrown.entities.mob.SoldierEntity;
import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import static com.wildsregrown.WildsRegrown.modid;

public class ModEntities {

    /**
     * Entities
     */

    //Animals
    public static final EntityType<SoldierEntity> soldierEntity = register("soldier", SoldierEntity::new, SpawnGroup.CREATURE, 1f, 1f);
    //public static final EntityType<Bovine> bovine = register("bovine", Bovine::new, SpawnGroup.CREATURE, 1f, 1f);

    //Misc
    public static final EntityType<SitEntity> sitAbleEntity = register("sit_entity", SitEntity::new, SpawnGroup.MISC, 0f, 0f);

    public static <T extends Entity> EntityType<T> register(String name, EntityType.EntityFactory<T> factory, SpawnGroup spawngroup, float width, float height) {
        RegistryKey<EntityType<?>> key = RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(modid, name));
        return Registry.register(Registries.ENTITY_TYPE, key, EntityType.Builder.<T>create(factory, spawngroup).dimensions(width, height).build(key));
    }

    /**
     * BlockEntities
     */
    public static final BlockEntityType<StructureEntity> structureBlock = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(modid, "structure_block"),
            FabricBlockEntityTypeBuilder.create(StructureEntity::new, ModBlocks.structureBlock).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "structure_block"))
    );
    public static final BlockEntityType<TreeEntity> tree = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(modid, "tree"),
            FabricBlockEntityTypeBuilder.create(TreeEntity::new,
                    ModBlocks.spruce_source,
                    ModBlocks.larch_source,
                    ModBlocks.dwarf_birch_source,
                    ModBlocks.silver_birch_source,
                    ModBlocks.tall_birch_source,
                    ModBlocks.oak_source,
                    ModBlocks.dense_oak_source,
                    ModBlocks.large_oak_source,
                    ModBlocks.beech_source,
                    ModBlocks.ash_source,
                    ModBlocks.weeping_willow_source,
                    ModBlocks.bebb_willow_source,
                    ModBlocks.apple_source,
                    ModBlocks.pear_source,
                    ModBlocks.plum_source,
                    ModBlocks.ancient_oak_source,
                    ModBlocks.jacaranda_source,
                    ModBlocks.glowing_willow_source
            ).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, "tree"))
    );

    public static final BlockEntityType<ItemLootPedestalEntity> itemLootPedestal = register("item_loot_pedestal", ItemLootPedestalEntity::new,
            ModBlocks.limestone_white.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.limestone_beige.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.limestone_grey.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.travertine_beige.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.travertine_grey.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.travertine_white.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_white.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_beige.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_black.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_portoro.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_green.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.marble_blue.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.basalt_black.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.granite_white.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.granite_pink.get(StoneGroup.Luxury.dungeonItemPedestal),
            ModBlocks.granite_red.get(StoneGroup.Luxury.dungeonItemPedestal)
    );

    public static final BlockEntityType<CrateEntity> crateEntity = register("crate_entity", CrateEntity::new,
            ModBlocks.ancient_oak.get(WoodGroup.Framing.crate),
            ModBlocks.jacaranda.get(WoodGroup.Framing.crate),
            ModBlocks.larch.get(WoodGroup.Framing.crate),
            ModBlocks.spruce.get(WoodGroup.Framing.crate),
            ModBlocks.oak.get(WoodGroup.Framing.crate),
            ModBlocks.ash.get(WoodGroup.Framing.crate)
            );

    public static final BlockEntityType<GenericSingleStorageEntity> genericSingleStorageEntity = register("single_storage_entity", GenericSingleStorageEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.storage_table),
            ModBlocks.pear.get(WoodGroup.Furniture.storage_table),
            ModBlocks.plum.get(WoodGroup.Furniture.storage_table),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.storage_table),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.storage_table),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.storage_table),
            ModBlocks.larch.get(WoodGroup.Furniture.storage_table),
            ModBlocks.spruce.get(WoodGroup.Furniture.storage_table),
            ModBlocks.birch.get(WoodGroup.Furniture.storage_table),
            ModBlocks.willow.get(WoodGroup.Furniture.storage_table),
            ModBlocks.oak.get(WoodGroup.Furniture.storage_table),
            ModBlocks.beech.get(WoodGroup.Furniture.storage_table),
            ModBlocks.ash.get(WoodGroup.Furniture.storage_table)
    );

    public static final BlockEntityType<TableChestEntity> tableChestEntity = register("table_chest_entity", TableChestEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.table_chest),
            ModBlocks.pear.get(WoodGroup.Furniture.table_chest),
            ModBlocks.plum.get(WoodGroup.Furniture.table_chest),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.table_chest),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.table_chest),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.table_chest),
            ModBlocks.larch.get(WoodGroup.Furniture.table_chest),
            ModBlocks.spruce.get(WoodGroup.Furniture.table_chest),
            ModBlocks.birch.get(WoodGroup.Furniture.table_chest),
            ModBlocks.willow.get(WoodGroup.Furniture.table_chest),
            ModBlocks.oak.get(WoodGroup.Furniture.table_chest),
            ModBlocks.beech.get(WoodGroup.Furniture.table_chest),
            ModBlocks.ash.get(WoodGroup.Furniture.table_chest)
    );

    public static final BlockEntityType<CounterShelvesEntity> counterShelvesEntity = register("chounter_shelves_entity", CounterShelvesEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.pear.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.plum.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.larch.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.spruce.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.birch.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.willow.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.oak.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.beech.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.ash.get(WoodGroup.Furniture.counter_shelves),
            ModBlocks.apple.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.pear.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.plum.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.larch.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.spruce.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.birch.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.willow.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.oak.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.beech.get(WoodGroup.Furniture.cabinet_shelf),
            ModBlocks.ash.get(WoodGroup.Furniture.cabinet_shelf)
    );

    public static final BlockEntityType<DrawerEntity> drawerEntity = register("drawer_entity", DrawerEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.drawer),
            ModBlocks.pear.get(WoodGroup.Furniture.drawer),
            ModBlocks.plum.get(WoodGroup.Furniture.drawer),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.drawer),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.drawer),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.drawer),
            ModBlocks.larch.get(WoodGroup.Furniture.drawer),
            ModBlocks.spruce.get(WoodGroup.Furniture.drawer),
            ModBlocks.birch.get(WoodGroup.Furniture.drawer),
            ModBlocks.willow.get(WoodGroup.Furniture.drawer),
            ModBlocks.oak.get(WoodGroup.Furniture.drawer),
            ModBlocks.beech.get(WoodGroup.Furniture.drawer),
            ModBlocks.ash.get(WoodGroup.Furniture.drawer)
    );

    public static final BlockEntityType<GenericSmallStorageEntity> genericSmallStorage = register("small_storage", GenericSmallStorageEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.pear.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.plum.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.larch.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.spruce.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.birch.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.willow.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.oak.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.beech.get(WoodGroup.Furniture.counter_chest),
            ModBlocks.ash.get(WoodGroup.Furniture.counter_chest),

            ModBlocks.apple.get(WoodGroup.Furniture.cabinet),
            ModBlocks.pear.get(WoodGroup.Furniture.cabinet),
            ModBlocks.plum.get(WoodGroup.Furniture.cabinet),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.cabinet),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.cabinet),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.cabinet),
            ModBlocks.larch.get(WoodGroup.Furniture.cabinet),
            ModBlocks.spruce.get(WoodGroup.Furniture.cabinet),
            ModBlocks.birch.get(WoodGroup.Furniture.cabinet),
            ModBlocks.willow.get(WoodGroup.Furniture.cabinet),
            ModBlocks.oak.get(WoodGroup.Furniture.cabinet),
            ModBlocks.beech.get(WoodGroup.Furniture.cabinet),
            ModBlocks.ash.get(WoodGroup.Furniture.cabinet),

            ModBlocks.apple.get(WoodGroup.Furniture.night_stand),
            ModBlocks.pear.get(WoodGroup.Furniture.night_stand),
            ModBlocks.plum.get(WoodGroup.Furniture.night_stand),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.night_stand),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.night_stand),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.night_stand),
            ModBlocks.larch.get(WoodGroup.Furniture.night_stand),
            ModBlocks.spruce.get(WoodGroup.Furniture.night_stand),
            ModBlocks.birch.get(WoodGroup.Furniture.night_stand),
            ModBlocks.willow.get(WoodGroup.Furniture.night_stand),
            ModBlocks.oak.get(WoodGroup.Furniture.night_stand),
            ModBlocks.beech.get(WoodGroup.Furniture.night_stand),
            ModBlocks.ash.get(WoodGroup.Furniture.night_stand)
    );
    
    public static final BlockEntityType<ShelvesEntity> shelvesEntity = register("shelves_entity", ShelvesEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.shelves),
            ModBlocks.pear.get(WoodGroup.Furniture.shelves),
            ModBlocks.plum.get(WoodGroup.Furniture.shelves),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.shelves),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.shelves),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.shelves),
            ModBlocks.larch.get(WoodGroup.Furniture.shelves),
            ModBlocks.spruce.get(WoodGroup.Furniture.shelves),
            ModBlocks.birch.get(WoodGroup.Furniture.shelves),
            ModBlocks.willow.get(WoodGroup.Furniture.shelves),
            ModBlocks.oak.get(WoodGroup.Furniture.shelves),
            ModBlocks.beech.get(WoodGroup.Furniture.shelves),
            ModBlocks.ash.get(WoodGroup.Furniture.shelves)
    );

    public static final BlockEntityType<PortableWorkbenchEntity> portableWorkbenchEntity = register("portable_workbench_entity", PortableWorkbenchEntity::new,
            ModBlocks.apple.get(WoodGroup.Common.portable_workbench),
            ModBlocks.pear.get(WoodGroup.Common.portable_workbench),
            ModBlocks.plum.get(WoodGroup.Common.portable_workbench),
            ModBlocks.ancient_oak.get(WoodGroup.Common.portable_workbench),
            ModBlocks.jacaranda.get(WoodGroup.Common.portable_workbench),
            ModBlocks.glowing_willow.get(WoodGroup.Common.portable_workbench),
            ModBlocks.larch.get(WoodGroup.Common.portable_workbench),
            ModBlocks.spruce.get(WoodGroup.Common.portable_workbench),
            ModBlocks.birch.get(WoodGroup.Common.portable_workbench),
            ModBlocks.willow.get(WoodGroup.Common.portable_workbench),
            ModBlocks.oak.get(WoodGroup.Common.portable_workbench),
            ModBlocks.beech.get(WoodGroup.Common.portable_workbench),
            ModBlocks.ash.get(WoodGroup.Common.portable_workbench)
    );

    //Fabric docs
    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        Identifier id = Identifier.of(modid, name);
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize(){
        WildsRegrown.LOGGER.info("Registering Entities");
    }

}
