package com.wildsregrown.registries;

import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;

public final class ModItemGroups {

    public static final ResourceKey<CreativeModeTab> TOOL_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "tool_group"));
    public static final CreativeModeTab TOOL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.IRON_SWORD))
            .title(Component.translatable("itemGroup.tool_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> STONE_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "stone_group"));
    public static final CreativeModeTab STONE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sandstone_grey.get(StoneGroup.Common.layered)))
            .title(Component.translatable("itemGroup.stone_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> STONE_DECO_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "stone_deco_group"));
    public static final CreativeModeTab STONE_DECO_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sandstone_grey.get(StoneGroup.Construction.bricks_arch)))
            .title(Component.translatable("itemGroup.stone_deco_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> WOOD_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "wood_group"));
    public static final CreativeModeTab WOOD_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.oak.get(WoodGroup.Common.log)))
            .title(Component.translatable("itemGroup.wood_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> WOOD_FRAMING_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "wood_framing_group"));
    public static final CreativeModeTab WOOD_FRAMING_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.oak.get(WoodGroup.Framing.parquet)))
            .title(Component.translatable("itemGroup.wood_framing_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> WOOD_FURNITURE_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "wood_furniture_group"));
    public static final CreativeModeTab WOOD_FURNITURE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.apple.get(WoodGroup.Furniture.basic_chair)))
            .title(Component.translatable("itemGroup.wood_furniture_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> METALS_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "metals_group"));
    public static final CreativeModeTab METALS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.banded_iron))
            .title(Component.translatable("itemGroup.metals_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> SOIL_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "soil_group"));
    public static final CreativeModeTab SOIL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sand_beige))
            .title(Component.translatable("itemGroup.soil_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> FLORA_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "flora_group"));
    public static final CreativeModeTab FLORA_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.SHORT_GRASS))
            .title(Component.translatable("itemGroup.flora_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> DECORATION_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "decoration_group"));
    public static final CreativeModeTab DECORATION_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.glass_window))
            .title(Component.translatable("itemGroup.decoration_group"))
            .build();

    public static final ResourceKey<CreativeModeTab> FABRIC_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(modid, "fabric_group"));
    public static final CreativeModeTab FABRIC_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.GRAY_WOOL))
            .title(Component.translatable("itemGroup.fabric_group"))
            .build();

    public static void initialize(){
        LOGGER.info("Registering item Groups");
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TOOL_GROUP_KEY, TOOL_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, STONE_GROUP_KEY, STONE_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, STONE_DECO_GROUP_KEY, STONE_DECO_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WOOD_GROUP_KEY, WOOD_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WOOD_FRAMING_GROUP_KEY, WOOD_FRAMING_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WOOD_FURNITURE_GROUP_KEY, WOOD_FURNITURE_GROUP);
        //Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, WOOD_UTENSILS_GROUP_KEY, WOOD_UTENSILS_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, METALS_GROUP_KEY, METALS_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SOIL_GROUP_KEY, SOIL_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FLORA_GROUP_KEY, FLORA_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, DECORATION_GROUP_KEY, DECORATION_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, FABRIC_GROUP_KEY, FABRIC_GROUP);
    }

}