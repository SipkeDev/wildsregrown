//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wildsregrown.items.tools;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Block;
import java.util.List;

public class Chisel extends Item {

    public Chisel(ToolMaterial material, TagKey<Block> effectiveBlocks, Properties settings) {
        super(material.applyToolProperties(settings, effectiveBlocks, 0,0, 0.125f));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return InteractionResult.PASS;
    }

}