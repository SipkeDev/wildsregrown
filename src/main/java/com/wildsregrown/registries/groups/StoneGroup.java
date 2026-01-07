package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.StoneBlock;
import com.wildsregrown.blocks.dungeon.DungeonItemLootPedestal;
import com.wildsregrown.blocks.stone.*;
import com.wildsregrown.blocks.stone.castle.*;
import net.minecraft.block.*;

import static com.wildsregrown.registries.ModBlocks.*;
import static com.wildsregrown.registries.ModItemGroups.*;

public class StoneGroup {

    private final String id;

    public StoneGroup(String id, AbstractBlock.Settings settings, boolean construction, boolean luxury) {

        this.id = id;
        this.common = new Block[Common.values().length];
        this.construction = construction ? new Block[Construction.values().length] : null;
        this.luxury = luxury ? new Block[Luxury.values().length] : null;

        this.common[Common.layered.ordinal()]= register(id, StoneBlock::new, settings, STONE_GROUP_KEY);
        this.common[Common.stairs.ordinal()] = register(id + "_stairs"  , (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
        this.common[Common.cobble_layered.ordinal()]= register(id + "_cobble", StoneBlock::new, settings, STONE_GROUP_KEY);
        this.common[Common.cobble_stairs.ordinal()] = register(id + "_cobble" + "_stairs"  , (s) -> new StairsBlock(common[Common.cobble_layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);

        if (construction){
            this.construction[Construction.bricks.ordinal()]                = register(id + "_bricks", StoneBlock::new, settings, STONE_GROUP_KEY);
            this.construction[Construction.bricks_stairs.ordinal()]         = register(id + "_bricks" + "_stairs", (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.construction[Construction.pavement.ordinal()]              = register(id + "_pavement", StoneBlock::new, settings, STONE_GROUP_KEY);
            this.construction[Construction.pavement_stairs.ordinal()]       = register(id + "_pavement" + "_stairs", (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.construction[Construction.cobble_bricks.ordinal()]         = register(id + "_cobble_bricks", StoneBlock::new, settings, STONE_GROUP_KEY);
            this.construction[Construction.cobble_bricks_stairs.ordinal()]  = register(id + "_cobble_bricks" + "_stairs", (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.construction[Construction.cobble_pavement.ordinal()]       = register(id + "_cobble_pavement", StoneBlock::new, settings, STONE_GROUP_KEY);
            this.construction[Construction.cobble_pavement_stairs.ordinal()]= register(id + "_cobble_pavement" + "_stairs", (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.construction[Construction.pillar.ordinal()]                = register(id + "_pillar", Pillar::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.thin_pillar.ordinal()]           = register("thin_" + id + "_pillar", ThinPillar::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_pillar.ordinal()]         = register(id + "_bricks_pillar", Pillar::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_thin_pillar.ordinal()]    = register("thin_" + id + "_bricks_pillar", ThinPillar::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_wall_support.ordinal()]   = register(id + "_bricks_wall_support", WallSupport::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_half_arch.ordinal()]      = register(id + "_bricks_half_arch", (s) -> new HalfArchBlock(common[Common.layered.ordinal()].getDefaultState(),s), settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_arch.ordinal()]           = register(id + "_bricks_arch", ArchBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_arrow_slit.ordinal()]     = register(id + "_bricks_arrow_slit", ArrowSlitBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.bricks_machicolations.ordinal()] = register(id + "_bricks_machicolations", MachicolationsBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.construction[Construction.battlements.ordinal()]           = register(id + "_battlements", BattlementsBlock::new, settings, STONE_DECO_GROUP_KEY);
        }

        if (luxury){
            this.luxury[Luxury.smooth.ordinal()]                = register(id + "_smooth", Layered::new, settings, STONE_GROUP_KEY);
            this.luxury[Luxury.smooth_stairs.ordinal()]         = register(id + "_smooth" + "_stairs"  , (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.luxury[Luxury.polished.ordinal()]              = register(id + "_polished", Layered::new, settings, STONE_GROUP_KEY);
            this.luxury[Luxury.polished_stairs.ordinal()]       = register(id + "_polished" + "_stairs"  , (s) -> new StairsBlock(common[Common.layered.ordinal()].getDefaultState(), s), settings, STONE_GROUP_KEY);
            this.luxury[Luxury.balustrade.ordinal()]            = register(id + "_balustrade", Balustrade::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.smooth_balustrade.ordinal()]     = register(id + "_smooth_balustrade", Balustrade::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.brazier.ordinal()]               = register(id + "_brazier", StoneBrazier::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.smooth_brazier.ordinal()]        = register(id + "_smooth_brazier", StoneBrazier::new, settings, STONE_DECO_GROUP_KEY);
            CUTOUTS.add(this.luxury[Luxury.brazier.ordinal()]);CUTOUTS.add(this.luxury[Luxury.smooth_brazier.ordinal()]);
            this.luxury[Luxury.dungeonItemPedestal.ordinal()]   = register(id + "_dungeon_item_pedestal", DungeonItemLootPedestal::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.smooth_half_arch.ordinal()]      = register(id + "_smooth_half_arch", (s) -> new HalfArchBlock(common[Common.layered.ordinal()].getDefaultState(),s), settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.smooth_arch.ordinal()]           = register(id + "_smooth_arch", ArchBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.smooth_arrow_slit.ordinal()]     = register(id + "_smooth_arrow_slit", ArrowSlitBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.polished_half_arch.ordinal()]    = register(id + "_polished_half_arch", (s) -> new HalfArchBlock(common[Common.layered.ordinal()].getDefaultState(),s), settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.polished_arch.ordinal()]         = register(id + "_polished_arch", ArchBlock::new, settings, STONE_DECO_GROUP_KEY);
            this.luxury[Luxury.polished_arrow_slit.ordinal()]   = register(id + "_polished_arrow_slit", ArrowSlitBlock::new, settings, STONE_DECO_GROUP_KEY);
        }

    }

    public enum Common {
        layered,
        stairs,
        cobble_layered,
        cobble_stairs
    }

    public enum Construction {
        //Common
        bricks,
        bricks_stairs,
        pavement,
        pavement_stairs,
        cobble_bricks,
        cobble_bricks_stairs,
        cobble_pavement,
        cobble_pavement_stairs,
        //Pillar
        pillar,
        thin_pillar,
        bricks_pillar,
        bricks_thin_pillar,
        //Castle/Deco blocks
        bricks_wall_support,
        bricks_half_arch,
        bricks_arch,
        bricks_arrow_slit,
        bricks_machicolations,
        battlements
    }

    public enum Luxury {
        smooth,
        smooth_stairs,
        polished,
        polished_stairs,
        //Deco blocks
        balustrade,
        smooth_balustrade,
        brazier,
        smooth_brazier,
        dungeonItemPedestal,
        //Castle blocks
        smooth_half_arch,
        smooth_arch,
        polished_half_arch,
        polished_arch,
        smooth_arrow_slit,
        polished_arrow_slit
    }

    private final Block[] common;
    private final Block[] construction;
    private final Block[] luxury;

    public boolean constructionExist() {return construction != null;}
    public boolean luxuryExist() {return luxury != null;}

    public Block get(Common basic) {
        return this.common[basic.ordinal()];
    }
    public Block get(Construction construct) {
        return this.construction[construct.ordinal()];
    }
    public Block get(Luxury luxury) {
        return this.luxury[luxury.ordinal()];
    }

    public String getId() {
        return id;
    }
}
