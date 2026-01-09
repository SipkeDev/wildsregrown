package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.items.*;
import com.wildsregrown.items.tools.*;
import com.wildsregrown.items.weapons.ScorpionSword;
import com.wildsregrown.items.weapons.Sword;
import com.wildsregrown.items.weapons.TwoHandedSword;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.registries.ModItemGroups.FLORA_GROUP_KEY;
import static com.wildsregrown.registries.ModItemGroups.TOOL_GROUP_KEY;

public class ModItems {

    public static final Torch torch               = (Torch)       registerItem("torch", Torch::new, new Item.Settings().component(ModComponents.FUEL, 15).component(ModComponents.LIT, true), TOOL_GROUP_KEY);

    /**
     * Tools
     */
    //Hatchet
    public static final Hatchet hatchet_c45     = (Hatchet)     registerItem("hatchet_c45", (settings) -> new Hatchet(ToolMaterials.C45, 3.0F, -2.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Hatchet hatchet_c60     = (Hatchet)     registerItem("hatchet_c60", (settings) -> new Hatchet(ToolMaterials.C60, 3.0F, -2.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Hatchet hatchet_wootz   = (Hatchet)     registerItem("hatchet_wootz", (settings) -> new Hatchet(ToolMaterials.WOOTZ, 3.0F, -2.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);

    //PickAxe
    public static final Pickaxe pickaxe_c45     = (Pickaxe)     registerItem("pickaxe_c45", (settings) -> new Pickaxe(ToolMaterials.C45, 1.0F, -1.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Pickaxe pickaxe_c60     = (Pickaxe)     registerItem("pickaxe_c60", (settings) -> new Pickaxe(ToolMaterials.C60, 1.0F, -1.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Pickaxe pickaxe_wootz   = (Pickaxe)     registerItem("pickaxe_wootz", (settings) -> new Pickaxe(ToolMaterials.WOOTZ, 1.0F, -1.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);

    //shovel
    public static final ShovelItem iron_shovel  = (ShovelItem)  registerItem("iron_shovel", (settings) -> new Shovel(ToolMaterials.IRON, 1.0F, -4.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);

    //hoe
    public static final Hoe hoe_c45             = (Hoe)         registerItem("hoe_c45", (settings) -> new Hoe(ToolMaterials.C45, 3.0F, -3.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Hoe hoe_c60             = (Hoe)         registerItem("hoe_c60", (settings) -> new Hoe(ToolMaterials.C60, 3.0F, -3.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Hoe hoe_wootz           = (Hoe)         registerItem("hoe_wootz", (settings) -> new Hoe(ToolMaterials.WOOTZ, 3.0F, -3.4F, settings), new Item.Settings(), TOOL_GROUP_KEY);

    public static final Sword knife_c45       = (Sword)       registerItem("knife_c45"  , (settings) -> new Sword(ToolMaterials.C45, 1F, -1.2F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Sword knife_c60       = (Sword)       registerItem("knife_c60"  , (settings) -> new Sword(ToolMaterials.C60, 1F, -1.2F, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Sword knife_wootz     = (Sword)       registerItem("knife_wootz"  , (settings) -> new Sword(ToolMaterials.WOOTZ, 1F, -1.2F, settings), new Item.Settings(), TOOL_GROUP_KEY);

    //Tool maintenance
    public static final Whetstone whetstone_coticule = (Whetstone)    registerItem("whetstone_coticule"  , (settings) -> new Whetstone(ToolMaterials.IRON, 0, 0, settings), new Item.Settings(), TOOL_GROUP_KEY);

    /**
     * Weapons
     */
    public static final Sword sword_c45         = (Sword)       registerItem("sword_c45"  , (settings) -> new Sword(ToolMaterials.C45, 6.0F, 3f, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Sword sword_c60         = (Sword)       registerItem("sword_c60"  , (settings) -> new Sword(ToolMaterials.C60, 6.0F, 3f, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Sword sword_wootz       = (Sword)       registerItem("sword_wootz"  , (settings) -> new Sword(ToolMaterials.WOOTZ, 6.0F, 3f, settings), new Item.Settings(), TOOL_GROUP_KEY);

    public static final TwoHandedSword two_handed_sword_c45      = (TwoHandedSword)registerItem("two_handed_sword_c45"  , (settings) -> new TwoHandedSword(ToolMaterials.C45, 12F, -3f, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final TwoHandedSword two_handed_sword_c60      = (TwoHandedSword)registerItem("two_handed_sword_c60"  , (settings) -> new TwoHandedSword(ToolMaterials.C60, 12F, -3f, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final TwoHandedSword two_handed_sword_wootz    = (TwoHandedSword)registerItem("two_handed_sword_wootz"  , (settings) -> new TwoHandedSword(ToolMaterials.WOOTZ, 12F, -3f, settings), new Item.Settings(), TOOL_GROUP_KEY);

    public static final ScorpionSword scorpion_sword = (ScorpionSword) registerItem("scorpion_sword", ScorpionSword::new, new Item.Settings(), TOOL_GROUP_KEY);

    //Chisels
    public static final Chisel stone_chisel_c45 = (Chisel) registerItem("stone_chisel_c45", (settings) -> new Chisel(ToolMaterials.C45, BlockTags.INCORRECT_FOR_STONE_TOOL, settings), new Item.Settings(), TOOL_GROUP_KEY);
    public static final Chisel wood_chisel_c45 = (Chisel) registerItem("wood_chisel_c45", (settings) -> new Chisel(ToolMaterials.C45, BlockTags.INCORRECT_FOR_WOODEN_TOOL, settings), new Item.Settings(), TOOL_GROUP_KEY);

    /**
     * Harvestables
     */
    public static final Item chives_bundle = registerItem("chives_bundle", Item::new, new Item.Settings().food(ModFoodComponents.WILD_FOOD), FLORA_GROUP_KEY);
    public static final Item atriplex_leaves = registerItem("atriplex_leaves", Item::new, new Item.Settings().food(ModFoodComponents.WILD_FOOD), FLORA_GROUP_KEY);
    public static final Item apple = registerItem("apple", Item::new, new Item.Settings().food(ModFoodComponents.APPLE), FLORA_GROUP_KEY);

    public static final Item bandit = registerItem("bandit", SpawnEggItem::new, new Item.Settings(), TOOL_GROUP_KEY);

    public static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings, RegistryKey<ItemGroup> key) {

        final RegistryKey<Item> registryKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modid, name));
        final Item item = Items.register(registryKey, factory, settings);
        ItemGroupEvents.modifyEntriesEvent(key).register(itemGroup -> {
            itemGroup.add(item);
        });
        return item;

    }

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering items");
    }
    
}
