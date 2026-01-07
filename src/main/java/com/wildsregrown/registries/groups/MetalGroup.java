package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.metal.*;
import com.wildsregrown.blocks.metal.lights.MetalAndIrons;
import com.wildsregrown.blocks.metal.lights.MetalBrazier;
import com.wildsregrown.blocks.metal.lights.MetalLantern;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static com.wildsregrown.registries.ModBlocks.CUTOUTS;
import static com.wildsregrown.registries.ModItemGroups.METALS_GROUP_KEY;

public class MetalGroup {

    private final boolean oxidation;

    public MetalGroup(String id, boolean oxidation, boolean deco, boolean lights) {

        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.IRON_BLOCK);

        this.oxidation = oxidation;
        this.deco   = deco   ? new Block[Deco.values().length] : null;
        this.lights = lights ? new Block[Lights.values().length] : null;

        this.block = ModBlocks.register(id, Layered::new, settings, METALS_GROUP_KEY);

        if (deco) {
            this.deco[Deco.bars.ordinal()]        = ModBlocks.register(id + "_bars"       , MetalBars::new      , settings, METALS_GROUP_KEY);
            this.deco[Deco.wall_anchor.ordinal()] = ModBlocks.register(id + "_wall_anchor", MetalWallAnchor::new, settings, METALS_GROUP_KEY);
            this.deco[Deco.bracket.ordinal()]     = ModBlocks.register(id + "_bracket"    , MetalBracket::new   , settings, METALS_GROUP_KEY);
            this.deco[Deco.pan.ordinal()]     = ModBlocks.register(id + "_pan"    , MetalPan::new   , settings, METALS_GROUP_KEY);
        }
        if (lights){
            this.lights[Lights.torch_holder.ordinal()]   = ModBlocks.register(id + "_torch_holder"  , MetalTorchHolder::new , settings, METALS_GROUP_KEY);
            this.lights[Lights.brazier.ordinal()]   = ModBlocks.register(id + "_brazier"  , MetalBrazier::new , settings, METALS_GROUP_KEY);
            this.lights[Lights.and_irons.ordinal()] = ModBlocks.register(id + "_and_irons", MetalAndIrons::new, settings, METALS_GROUP_KEY);
            this.lights[Lights.lantern.ordinal()]   = ModBlocks.register(id + "_lantern"  , MetalLantern::new , settings, METALS_GROUP_KEY);
            CUTOUTS.add(this.lights[Lights.brazier.ordinal()]);
            CUTOUTS.add(this.lights[Lights.and_irons.ordinal()]);
            CUTOUTS.add(this.lights[Lights.lantern.ordinal()]);
        }
    }

    public boolean isOxidation() {
        return oxidation;
    }

    public enum Deco {
        bars,
        wall_anchor,
        bracket,
        pan
    }

    public enum Lights {
        torch_holder,
        brazier,
        and_irons,
        lantern
    }

    public  final Block block;
    private final Block[] deco;
    private final Block[] lights;

    public boolean decoExist() {return deco != null;}
    public boolean lightsExist() {return lights != null;}

    public Block get(Deco deco) {
        return this.deco[deco.ordinal()];
    }

    public Block get(Lights lights){
        return this.lights[lights.ordinal()];
    }

}
