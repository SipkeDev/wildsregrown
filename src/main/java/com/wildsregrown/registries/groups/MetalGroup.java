package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.shapes.Door;
import com.wildsregrown.blocks.forging.*;
import com.wildsregrown.blocks.forging.lights.*;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import wildsregrown.api.block.shapes.Layered;

import static com.wildsregrown.registries.ModItemGroups.METALS_GROUP_KEY;

public class MetalGroup {

    private final boolean oxidation;

    public MetalGroup(String id, boolean oxidation, boolean deco, boolean lights) {

        BlockBehaviour.Properties settings = BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK);

        this.oxidation = oxidation;
        this.deco   = deco   ? new Block[Deco.values().length] : null;
        this.lights = lights ? new Block[Lights.values().length] : null;

        this.block = ModBlocks.register(id, Layered::new, settings, METALS_GROUP_KEY);

        if (deco) {
            //framing
            this.deco[Deco.bars.ordinal()]        = ModBlocks.register(id + "_bars",            MetalBars::new, settings, METALS_GROUP_KEY);
            this.deco[Deco.wall_anchor.ordinal()] = ModBlocks.register(id + "_wall_anchor",     MetalWallAnchor::new, settings, METALS_GROUP_KEY);
            this.deco[Deco.bracket.ordinal()]     = ModBlocks.register(id + "_bracket",         MetalBracket::new, settings, METALS_GROUP_KEY);
            this.deco[Deco.door.ordinal()]     = ModBlocks.register(id + "_door",               Door::new, settings, METALS_GROUP_KEY);
            this.deco[Deco.door_window.ordinal()]     = ModBlocks.register(id + "_door_window", Door::new, settings, METALS_GROUP_KEY);
            //Decoration
            this.deco[Deco.fireplace_tool_stand.ordinal()]     = ModBlocks.register(id + "_fireplace_tool_stand", MetalFirePlaceStand::new, settings, METALS_GROUP_KEY);
            //Utensils
            this.deco[Deco.pan.ordinal()]     = ModBlocks.register(id + "_pan", MetalPan::new, settings, METALS_GROUP_KEY);
        }
        if (lights){
            this.lights[Lights.torch_holder.ordinal()]   = ModBlocks.register(id + "_torch_holder", MetalTorchHolder::new , settings, METALS_GROUP_KEY);
            this.lights[Lights.brazier.ordinal()]   = ModBlocks.register(id + "_brazier"  , MetalBrazier::new, settings, METALS_GROUP_KEY);
            //this.lights[Lights.and_irons.ordinal()] = ModBlocks.register(id + "_and_irons", MetalAndIrons::new, settings, METALS_GROUP_KEY);
            this.lights[Lights.lantern.ordinal()]   = ModBlocks.register(id + "_lantern"  , MetalLantern::new, settings, METALS_GROUP_KEY);
            this.lights[Lights.candle_lantern.ordinal()]   = ModBlocks.register(id + "_candle_lantern", MetalCandleLantern::new , settings, METALS_GROUP_KEY);
            this.lights[Lights.oil_lantern.ordinal()]   = ModBlocks.register(id + "_oil_lantern", MetalOilLantern::new , settings, METALS_GROUP_KEY);
        }
    }

    public boolean isOxidation() {
        return oxidation;
    }

    public enum Deco {
        bars,
        wall_anchor,
        bracket,
        door,
        door_window,
        fireplace_tool_stand,
        pan
    }

    public enum Lights {
        torch_holder,
        brazier,
        and_irons,
        lantern,
        candle_lantern,
        oil_lantern
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
