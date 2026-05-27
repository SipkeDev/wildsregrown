package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.entities.block.*;
import com.wildsregrown.entities.mob.bandit.Bandit;
import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static com.wildsregrown.WildsRegrown.modid;

public class ModEntities {

    /**
     * Entities
     */

    //Animals
    public static final EntityType<Bandit> bandit = register("bandit", Bandit::new, MobCategory.CREATURE, 1f, 1f);
    //public static final EntityType<SoldierEntity> soldierEntity = register("soldier", SoldierEntity::new, SpawnGroup.CREATURE, 1f, 1f);
    //public static final EntityType<Bovine> bovine = register("bovine", Bovine::new, SpawnGroup.CREATURE, 1f, 1f);

    //Misc
    public static final EntityType<SitEntity> sitAbleEntity = register("sit_entity", SitEntity::new, MobCategory.MISC, 0f, 0f);

    public static <T extends Entity> EntityType<T> register(String name, EntityType.EntityFactory<T> factory, MobCategory spawngroup, float width, float height) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(modid, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, EntityType.Builder.<T>of(factory, spawngroup).sized(width, height).build(key));
    }

    /**
     * BlockEntities
     */
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
            ModBlocks.ancient_oak.get(WoodGroup.Interior.crate),
            ModBlocks.jacaranda.get(WoodGroup.Interior.crate),
            ModBlocks.larch.get(WoodGroup.Interior.crate),
            ModBlocks.spruce.get(WoodGroup.Interior.crate),
            ModBlocks.oak.get(WoodGroup.Interior.crate),
            ModBlocks.ash.get(WoodGroup.Interior.crate),
            ModBlocks.apple.get(WoodGroup.Interior.crate),
            ModBlocks.pear.get(WoodGroup.Interior.crate),
            ModBlocks.plum.get(WoodGroup.Interior.crate)
            );

    public static final BlockEntityType<GenericSingleStorageEntity> genericSingleStorageEntity = register("single_storage_entity", GenericSingleStorageEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.pear.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.plum.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.larch.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.spruce.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.birch.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.willow.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.oak.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.beech.get(WoodGroup.Furniture.basic_storage_table),
            ModBlocks.ash.get(WoodGroup.Furniture.basic_storage_table)
    );

    public static final BlockEntityType<TableChestEntity> tableChestEntity = register("table_chest_entity", TableChestEntity::new,
            ModBlocks.apple.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.pear.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.plum.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.larch.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.spruce.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.birch.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.willow.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.oak.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.beech.get(WoodGroup.Furniture.basic_table_chest),
            ModBlocks.ash.get(WoodGroup.Furniture.basic_table_chest)
    );

    public static final BlockEntityType<CounterShelvesEntity> counterShelvesEntity = register("chounter_shelves_entity", CounterShelvesEntity::new,
            ModBlocks.apple.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.pear.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.plum.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.ancient_oak.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.jacaranda.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.larch.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.spruce.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.oak.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.beech.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.ash.get(WoodGroup.Interior.counter_shelves),
            ModBlocks.apple.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.pear.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.plum.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.ancient_oak.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.jacaranda.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.larch.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.spruce.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.oak.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.beech.get(WoodGroup.Interior.cabinet_shelf),
            ModBlocks.ash.get(WoodGroup.Interior.cabinet_shelf)
    );

    public static final BlockEntityType<GenericSmallStorageEntity> genericSmallStorage = register("small_storage", GenericSmallStorageEntity::new,
            ModBlocks.apple.get(WoodGroup.Interior.counter_chest),
            ModBlocks.pear.get(WoodGroup.Interior.counter_chest),
            ModBlocks.plum.get(WoodGroup.Interior.counter_chest),
            ModBlocks.ancient_oak.get(WoodGroup.Interior.counter_chest),
            ModBlocks.jacaranda.get(WoodGroup.Interior.counter_chest),
            ModBlocks.larch.get(WoodGroup.Interior.counter_chest),
            ModBlocks.spruce.get(WoodGroup.Interior.counter_chest),
            ModBlocks.oak.get(WoodGroup.Interior.counter_chest),
            ModBlocks.beech.get(WoodGroup.Interior.counter_chest),
            ModBlocks.ash.get(WoodGroup.Interior.counter_chest),

            ModBlocks.apple.get(WoodGroup.Interior.cabinet),
            ModBlocks.pear.get(WoodGroup.Interior.cabinet),
            ModBlocks.plum.get(WoodGroup.Interior.cabinet),
            ModBlocks.ancient_oak.get(WoodGroup.Interior.cabinet),
            ModBlocks.jacaranda.get(WoodGroup.Interior.cabinet),
            ModBlocks.larch.get(WoodGroup.Interior.cabinet),
            ModBlocks.spruce.get(WoodGroup.Interior.cabinet),
            ModBlocks.oak.get(WoodGroup.Interior.cabinet),
            ModBlocks.beech.get(WoodGroup.Interior.cabinet),
            ModBlocks.ash.get(WoodGroup.Interior.cabinet),

            ModBlocks.apple.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.pear.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.plum.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.ancient_oak.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.jacaranda.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.glowing_willow.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.larch.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.spruce.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.birch.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.willow.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.oak.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.beech.get(WoodGroup.Furniture.basic_night_stand),
            ModBlocks.ash.get(WoodGroup.Furniture.basic_night_stand),

            ModBlocks.ancient_oak.get(WoodGroup.Interior.barrel),
            ModBlocks.jacaranda.get(WoodGroup.Interior.barrel),
            ModBlocks.larch.get(WoodGroup.Interior.barrel),
            ModBlocks.spruce.get(WoodGroup.Interior.barrel),
            ModBlocks.oak.get(WoodGroup.Interior.barrel),
            ModBlocks.ash.get(WoodGroup.Interior.barrel)
    );
    
    public static final BlockEntityType<ShelvesEntity> shelvesEntity = register("shelves_entity", ShelvesEntity::new,
            ModBlocks.apple.get(WoodGroup.Interior.shelves),
            ModBlocks.pear.get(WoodGroup.Interior.shelves),
            ModBlocks.plum.get(WoodGroup.Interior.shelves),
            ModBlocks.ancient_oak.get(WoodGroup.Interior.shelves),
            ModBlocks.jacaranda.get(WoodGroup.Interior.shelves),
            ModBlocks.larch.get(WoodGroup.Interior.shelves),
            ModBlocks.spruce.get(WoodGroup.Interior.shelves),
            ModBlocks.oak.get(WoodGroup.Interior.shelves),
            ModBlocks.beech.get(WoodGroup.Interior.shelves),
            ModBlocks.ash.get(WoodGroup.Interior.shelves)
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
        Identifier id = Identifier.fromNamespaceAndPath(modid, name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize(){
        WildsRegrown.LOGGER.info("Registering Entities");
    }

}
