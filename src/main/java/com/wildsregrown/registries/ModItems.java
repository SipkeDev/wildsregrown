package com.wildsregrown.registries;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.carpentry.framing.beam.FramingBeamItem;
import com.wildsregrown.items.*;
import com.wildsregrown.items.tools.*;
import com.wildsregrown.items.weapons.ScorpionSword;
import com.wildsregrown.items.weapons.Sword;
import com.wildsregrown.items.weapons.TwoHandedSword;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;

import java.util.function.Function;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.registries.ModItemGroups.*;

public class ModItems {

    public static final Torch torch             = (Torch)   registerItem("torch", Torch::new, new Item.Properties().component(ModComponents.FUEL, 15).component(ModComponents.LIT, true), TOOL_GROUP_KEY);

    //public static final Item pitch_bucket =           registerItem("pitch_bucket", ctx -> new BucketItem(ModFluids.PITCH, ctx), new Item.Properties(), TOOL_GROUP_KEY);

    /**
     * Tools
     */
    //Hatchet
    public static final Hatchet hatchet_c45     = (Hatchet)     registerItem("hatchet_c45", (settings) -> new Hatchet(ToolMaterials.C45, 3.0F, -2.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Hatchet hatchet_c60     = (Hatchet)     registerItem("hatchet_c60", (settings) -> new Hatchet(ToolMaterials.C60, 3.0F, -2.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Hatchet hatchet_wootz   = (Hatchet)     registerItem("hatchet_wootz", (settings) -> new Hatchet(ToolMaterials.WOOTZ, 3.0F, -2.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);

    //PickAxe
    public static final Pickaxe pickaxe_c45     = (Pickaxe)     registerItem("pickaxe_c45", (settings) -> new Pickaxe(ToolMaterials.C45, 1.0F, -1.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Pickaxe pickaxe_c60     = (Pickaxe)     registerItem("pickaxe_c60", (settings) -> new Pickaxe(ToolMaterials.C60, 1.0F, -1.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Pickaxe pickaxe_wootz   = (Pickaxe)     registerItem("pickaxe_wootz", (settings) -> new Pickaxe(ToolMaterials.WOOTZ, 1.0F, -1.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);

    //shovel
    public static final ShovelItem iron_shovel  = (ShovelItem)  registerItem("iron_shovel", (settings) -> new Shovel(ToolMaterials.IRON, 1.0F, -4.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);

    //hoe
    public static final Hoe hoe_c45             = (Hoe)         registerItem("hoe_c45", (settings) -> new Hoe(ToolMaterials.C45, 3.0F, -3.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Hoe hoe_c60             = (Hoe)         registerItem("hoe_c60", (settings) -> new Hoe(ToolMaterials.C60, 3.0F, -3.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Hoe hoe_wootz           = (Hoe)         registerItem("hoe_wootz", (settings) -> new Hoe(ToolMaterials.WOOTZ, 3.0F, -3.4F, settings), new Item.Properties(), TOOL_GROUP_KEY);

    public static final Sword knife_c45       = (Sword)       registerItem("knife_c45"  , (settings) -> new Sword(ToolMaterials.C45, 1F, -1.2F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Sword knife_c60       = (Sword)       registerItem("knife_c60"  , (settings) -> new Sword(ToolMaterials.C60, 1F, -1.2F, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Sword knife_wootz     = (Sword)       registerItem("knife_wootz"  , (settings) -> new Sword(ToolMaterials.WOOTZ, 1F, -1.2F, settings), new Item.Properties(), TOOL_GROUP_KEY);

    //Tool maintenance
    public static final Whetstone whetstone_coticule = (Whetstone)    registerItem("whetstone_coticule"  , (settings) -> new Whetstone(ToolMaterials.IRON, 0, 0, settings), new Item.Properties(), TOOL_GROUP_KEY);

    /**
     * Weapons
     */
    public static final Sword sword_c45         = (Sword)       registerItem("sword_c45"  , (settings) -> new Sword(ToolMaterials.C45, 6.0F, 3f, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Sword sword_c60         = (Sword)       registerItem("sword_c60"  , (settings) -> new Sword(ToolMaterials.C60, 6.0F, 3f, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Sword sword_wootz       = (Sword)       registerItem("sword_wootz"  , (settings) -> new Sword(ToolMaterials.WOOTZ, 6.0F, 3f, settings), new Item.Properties(), TOOL_GROUP_KEY);

    public static final TwoHandedSword two_handed_sword_c45      = (TwoHandedSword)registerItem("two_handed_sword_c45"  , (settings) -> new TwoHandedSword(ToolMaterials.C45, 12F, -3f, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final TwoHandedSword two_handed_sword_c60      = (TwoHandedSword)registerItem("two_handed_sword_c60"  , (settings) -> new TwoHandedSword(ToolMaterials.C60, 12F, -3f, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final TwoHandedSword two_handed_sword_wootz    = (TwoHandedSword)registerItem("two_handed_sword_wootz"  , (settings) -> new TwoHandedSword(ToolMaterials.WOOTZ, 12F, -3f, settings), new Item.Properties(), TOOL_GROUP_KEY);

    public static final ScorpionSword scorpion_sword = (ScorpionSword) registerItem("scorpion_sword", ScorpionSword::new, new Item.Properties(), TOOL_GROUP_KEY);

    //Chisels
    public static final Chisel stone_chisel_c45 = (Chisel) registerItem("stone_chisel_c45", (settings) -> new Chisel(ToolMaterials.C45, BlockTags.INCORRECT_FOR_STONE_TOOL, settings), new Item.Properties(), TOOL_GROUP_KEY);
    public static final Chisel wood_chisel_c45 = (Chisel) registerItem("wood_chisel_c45", (settings) -> new Chisel(ToolMaterials.C45, BlockTags.INCORRECT_FOR_WOODEN_TOOL, settings), new Item.Properties(), TOOL_GROUP_KEY);

    /**
     * Harvestables
     */
    public static final Item chives_bundle = registerItem("chives_bundle", Item::new, new Item.Properties().food(ModComponents.WILD_FOOD), FLORA_GROUP_KEY);
    public static final Item atriplex_leaves = registerItem("atriplex_leaves", Item::new, new Item.Properties().food(ModComponents.WILD_FOOD), FLORA_GROUP_KEY);
    public static final Item apple = registerItem("apple", Item::new, new Item.Properties().food(ModComponents.APPLE), FLORA_GROUP_KEY);

    //public static final Item bandit = registerItem("bandit", SpawnEggItem::new, new Item.Properties(), TOOL_GROUP_KEY);


    /// beams
    public static final FramingBeamItem super_magic_beam = (FramingBeamItem) registerItem("super_magic_beam", ctx->new FramingBeamItem(ctx,
            ModBlocks.oak.get(WoodGroup.Framing.beam_support),
            ModBlocks.oak.get(WoodGroup.Framing.beam_post),
            ModBlocks.oak.get(WoodGroup.Framing.beam_ceiling),
            ModBlocks.oak.get(WoodGroup.Framing.beam_diagonal)
            ), new Item.Properties(), WOOD_FRAMING_GROUP_KEY);

    public static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings, ResourceKey<CreativeModeTab> key) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modid, name));
        final Item item = Items.registerItem(registryKey, factory, settings);
        ItemGroupEvents.modifyEntriesEvent(key).register(itemGroup -> {
            itemGroup.accept(item);
        });
        return item;
    }

    public static Item registerItem(Identifier identifier, Function<Item.Properties, Item> factory, Item.Properties settings, ResourceKey<CreativeModeTab> key) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, identifier);
        final Item item = Items.registerItem(registryKey, factory, settings);
        ItemGroupEvents.modifyEntriesEvent(key).register(itemGroup -> {
            itemGroup.accept(item);
        });
        return item;
    }

    public static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties settings) {
        final ResourceKey<Item> registryKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(modid, name));
        return Items.registerItem(registryKey, factory, settings);
    }

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering items");
    }
    
}
