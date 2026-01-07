package com.wildsregrown.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

public class ToolMaterials {

    public static final ToolMaterial IRON =  new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 384, 2.0F, 0.0F, 1, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial C45 =   new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 768, 3.2F, 1.0F, 1, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial C60 =   new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 1024, 3.8F, 2.0F, 1, ItemTags.IRON_TOOL_MATERIALS);
    public static final ToolMaterial WOOTZ = new ToolMaterial(BlockTags.INCORRECT_FOR_IRON_TOOL, 8000, 6.0F, 5.0F, 1, ItemTags.IRON_TOOL_MATERIALS);

}