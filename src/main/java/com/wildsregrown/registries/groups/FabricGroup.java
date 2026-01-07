package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.ColoredLayered;
import com.wildsregrown.blocks.ColoredStairs;
import com.wildsregrown.blocks.decoration.TentRoof;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.*;

import static com.wildsregrown.registries.ModItemGroups.FABRIC_GROUP_KEY;

public class FabricGroup {

    public FabricGroup(String id, int rgb) {

        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.WHITE_WOOL);

        this.common = new Block[Common.values().length];
        this.common[Common.layered.ordinal()]   = ModBlocks.register(id, (ctx) -> new ColoredLayered(ctx, rgb), settings, FABRIC_GROUP_KEY);
        this.common[Common.stairs.ordinal()]    = ModBlocks.register(id + "_stairs", (ctx) -> new ColoredStairs(get(Common.layered).getDefaultState(), ctx, rgb), settings, FABRIC_GROUP_KEY);
        this.common[Common.tent.ordinal()]      = ModBlocks.register(id + "_tent", (ctx) -> new TentRoof(ctx, rgb), settings, FABRIC_GROUP_KEY);

        ModBlocks.CUTOUTS.add(this.get(Common.layered));
        ModBlocks.CUTOUTS.add(this.get(Common.stairs));
        ModBlocks.CUTOUTS.add(this.get(Common.tent));

    }

    private final Block[] common;

    public Block get(Common common) {
        return this.common[common.ordinal()];
    }

    public enum Common {
        layered,
        stairs,
        tent,
    }

}
