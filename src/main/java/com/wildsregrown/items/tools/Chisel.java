//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wildsregrown.items.tools;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

import java.util.List;

public class Chisel extends Item {

    public Chisel(ToolMaterial material, TagKey<Block> effectiveBlocks, Settings settings) {
        super(material.applyToolSettings(settings, effectiveBlocks, 0,0, 0.125f));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return ActionResult.PASS;
    }

}