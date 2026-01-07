package com.wildsregrown.registries;

import com.wildsregrown.registries.groups.StoneGroup;
import com.wildsregrown.registries.groups.WoodGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;

public final class ModItemGroups {

    public static final RegistryKey<ItemGroup> TOOL_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "tool_group"));
    public static final ItemGroup TOOL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Items.IRON_SWORD))
            .displayName(Text.translatable("itemGroup.tool_group"))
            .build();

    public static final RegistryKey<ItemGroup> STONE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "stone_group"));
    public static final ItemGroup STONE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sandstone_grey.get(StoneGroup.Common.layered)))
            .displayName(Text.translatable("itemGroup.stone_group"))
            .build();

    public static final RegistryKey<ItemGroup> STONE_DECO_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "stone_deco_group"));
    public static final ItemGroup STONE_DECO_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sandstone_grey.get(StoneGroup.Construction.bricks_arch)))
            .displayName(Text.translatable("itemGroup.stone_deco_group"))
            .build();

    public static final RegistryKey<ItemGroup> WOOD_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "wood_group"));
    public static final ItemGroup WOOD_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.oak.get(WoodGroup.Common.log)))
            .displayName(Text.translatable("itemGroup.wood_group"))
            .build();

    public static final RegistryKey<ItemGroup> WOOD_FRAMING_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "wood_framing_group"));
    public static final ItemGroup WOOD_FRAMING_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.oak.get(WoodGroup.Framing.parquet)))
            .displayName(Text.translatable("itemGroup.wood_framing_group"))
            .build();

    public static final RegistryKey<ItemGroup> WOOD_FURNITURE_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "wood_furniture_group"));
    public static final ItemGroup WOOD_FURNITURE_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.apple.get(WoodGroup.Furniture.drawer)))
            .displayName(Text.translatable("itemGroup.wood_furniture_group"))
            .build();

    public static final RegistryKey<ItemGroup> WOOD_UTENSILS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "wood_utensils_group"));
    public static final ItemGroup WOOD_UTENSILS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.apple.get(WoodGroup.Utensils.mug)))
            .displayName(Text.translatable("itemGroup.wood_utensils_group"))
            .build();

    public static final RegistryKey<ItemGroup> METALS_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "metals_group"));
    public static final ItemGroup METALS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.banded_iron))
            .displayName(Text.translatable("itemGroup.metals_group"))
            .build();

    public static final RegistryKey<ItemGroup> SOIL_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "soil_group"));
    public static final ItemGroup SOIL_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.sand_beige))
            .displayName(Text.translatable("itemGroup.soil_group"))
            .build();

    public static final RegistryKey<ItemGroup> FLORA_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "flora_group"));
    public static final ItemGroup FLORA_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.SHORT_GRASS))
            .displayName(Text.translatable("itemGroup.flora_group"))
            .build();

    public static final RegistryKey<ItemGroup> DECORATION_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "decoration_group"));
    public static final ItemGroup DECORATION_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.glass_window))
            .displayName(Text.translatable("itemGroup.decoration_group"))
            .build();

    public static final RegistryKey<ItemGroup> FABRIC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(modid, "fabric_group"));
    public static final ItemGroup FABRIC_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(Blocks.GRAY_WOOL))
            .displayName(Text.translatable("itemGroup.fabric_group"))
            .build();

    public static String id(Block block) {
        return Registries.BLOCK.getId(block).getPath().replaceFirst(".*\\.", "");
    }

    public static void initialize(){
        LOGGER.info("Registering item Groups");
        Registry.register(Registries.ITEM_GROUP, TOOL_GROUP_KEY, TOOL_GROUP);
        Registry.register(Registries.ITEM_GROUP, STONE_GROUP_KEY, STONE_GROUP);
        Registry.register(Registries.ITEM_GROUP, STONE_DECO_GROUP_KEY, STONE_DECO_GROUP);
        Registry.register(Registries.ITEM_GROUP, WOOD_GROUP_KEY, WOOD_GROUP);
        Registry.register(Registries.ITEM_GROUP, WOOD_FRAMING_GROUP_KEY, WOOD_FRAMING_GROUP);
        Registry.register(Registries.ITEM_GROUP, WOOD_FURNITURE_GROUP_KEY, WOOD_FURNITURE_GROUP);
        Registry.register(Registries.ITEM_GROUP, WOOD_UTENSILS_GROUP_KEY, WOOD_UTENSILS_GROUP);
        Registry.register(Registries.ITEM_GROUP, METALS_GROUP_KEY, METALS_GROUP);
        Registry.register(Registries.ITEM_GROUP, SOIL_GROUP_KEY, SOIL_GROUP);
        Registry.register(Registries.ITEM_GROUP, FLORA_GROUP_KEY, FLORA_GROUP);
        Registry.register(Registries.ITEM_GROUP, DECORATION_GROUP_KEY, DECORATION_GROUP);
        Registry.register(Registries.ITEM_GROUP, FABRIC_GROUP_KEY, FABRIC_GROUP);
    }

}