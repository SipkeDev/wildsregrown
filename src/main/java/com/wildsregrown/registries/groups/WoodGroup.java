package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.wood.PortableWorkBench;
import com.wildsregrown.blocks.wood.framing.WoodenArchBlock;
import com.wildsregrown.blocks.wood.framing.WoodenArrowSlit;
import com.wildsregrown.blocks.wood.framing.WoodenHalfArchBlock;
import com.wildsregrown.blocks.wood.framing.SodRoof;
import com.wildsregrown.blocks.wood.tree.HalfLog;
import com.wildsregrown.blocks.wood.tree.Leaves;
import com.wildsregrown.blocks.wood.tree.Log;
import com.wildsregrown.blocks.wood.tree.TreeBranch;
import com.wildsregrown.blocks.wood.*;
import com.wildsregrown.blocks.wood.framing.*;
import com.wildsregrown.blocks.wood.utensils.*;
import com.wildsregrown.blocks.wood.framing.LadderBlock;
import com.wildsregrown.blocks.wood.furniture.*;
import com.wildsregrown.blocks.wood.framing.Crate;
import com.wildsregrown.blocks.wood.framing.CrateLid;
import com.wildsregrown.blocks.wood.furniture.TableChest;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;

import static com.wildsregrown.registries.ModItemGroups.*;

public class WoodGroup {

    private final Block[] common;
    private final Block[] framing;
    private final Block[] furniture;
    private final Block[] utensils;

    public WoodGroup(String id, float strength, boolean framing, boolean furniture, boolean utensils) {

        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .instrument(NoteBlockInstrument.BASS)
                .strength(strength)
                .sounds(BlockSoundGroup.WOOD)
                .burnable();

        this.common    = new Block[Common.values().length];
        this.framing   = framing ? new Block[Framing.values().length]    : null;
        this.furniture = furniture ? new Block[Furniture.values().length]: null;
        this.utensils  = utensils ? new Block[Utensils.values().length]   : null;

        this.common[Common.leaves.ordinal()]              = ModBlocks.register(id + "_leaves",                    Leaves::new, settings, WOOD_GROUP_KEY);
        this.common[Common.branch.ordinal()]              = ModBlocks.register(id + "_branch",                    TreeBranch::new, settings, WOOD_GROUP_KEY);
        this.common[Common.log.ordinal()]                 = ModBlocks.register(id + "_log",                       Log::new, settings, WOOD_GROUP_KEY);      ModBlocks.CUTOUTS.add(this.common[Common.log.ordinal()]);
        this.common[Common.stripped_log.ordinal()]        = ModBlocks.register("stripped_"+ id + "_log",          Log::new, settings, WOOD_GROUP_KEY);      ModBlocks.CUTOUTS.add(this.common[Common.stripped_log.ordinal()]);
        this.common[Common.slab.ordinal()]                = ModBlocks.register(id + "_slab",                  HalfLog::new, settings, WOOD_GROUP_KEY);  ModBlocks.CUTOUTS.add(this.common[Common.slab.ordinal()]);
        this.common[Common.stripped_slab.ordinal()]       = ModBlocks.register("stripped_"+ id + "_slab",     HalfLog::new, settings, WOOD_GROUP_KEY);  ModBlocks.CUTOUTS.add(this.common[Common.stripped_slab.ordinal()]);
        this.common[Common.beam.ordinal()]                = ModBlocks.register(id + "_beam",                      Beam::new, settings, WOOD_GROUP_KEY);     ModBlocks.CUTOUTS.add(this.common[Common.beam.ordinal()]);
        this.common[Common.stripped_beam.ordinal()]       = ModBlocks.register("stripped_"+ id + "_beam",         Beam::new, settings, WOOD_GROUP_KEY);     ModBlocks.CUTOUTS.add(this.common[Common.stripped_beam.ordinal()]);
        this.common[Common.planks.ordinal()]              = ModBlocks.register(id + "_planks",                    Planks::new, settings, WOOD_GROUP_KEY);
        this.common[Common.planks_stairs.ordinal()]       = ModBlocks.register(id + "_planks_stairs", s -> new PaintedStairs(common[Common.planks.ordinal()].getDefaultState(), s), settings,  WOOD_GROUP_KEY);
        this.common[Common.portable_workbench.ordinal()]  = ModBlocks.register(id + "_portable_workbench", PortableWorkBench::new, settings, WOOD_GROUP_KEY);

        if (framing) {
            this.framing[Framing.parquet.ordinal()]           = ModBlocks.register(id + "_parquet", Planks::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.parquet_stairs.ordinal()]    = ModBlocks.register(id + "_parquet_stairs", (ctx -> new PaintedStairs(this.framing[0].getDefaultState(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.siding.ordinal()]           = ModBlocks.register(id + "_siding", Planks::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.siding_stairs.ordinal()]    = ModBlocks.register(id + "_siding_stairs", (ctx -> new PaintedStairs(this.framing[0].getDefaultState(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.support.ordinal()]           = ModBlocks.register(id + "_support",       WoodenSupport::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.arch.ordinal()]              = ModBlocks.register(id + "_arch",          WoodenArchBlock::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.half_arch.ordinal()]         = ModBlocks.register(id + "_half_arch",     (s) -> new WoodenHalfArchBlock(common[Common.log.ordinal()].getDefaultState(),s), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.roof.ordinal()]              = ModBlocks.register(id + "_roof",          ColoredRoof::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.sod_roof.ordinal()]          = ModBlocks.register(id + "_sod_roof",      SodRoof::new, settings, WOOD_FRAMING_GROUP_KEY); ModBlocks.CUTOUTS.add(this.framing[Framing.sod_roof.ordinal()]);
            this.framing[Framing.arrow_slit.ordinal()]        = ModBlocks.register(id + "_arrow_slit",    WoodenArrowSlit::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.ladder.ordinal()]            = ModBlocks.register(id + "_ladder",        LadderBlock::new, settings, WOOD_FRAMING_GROUP_KEY); ModBlocks.CLIMBABLE.add(this.framing[Framing.ladder.ordinal()]);
            this.framing[Framing.window_cover.ordinal()]      = ModBlocks.register(id + "_window_cover",  WindowCover::new, settings, WOOD_GROUP_KEY);
            //this.framing[Framing.door.ordinal()]              = ModBlocks.register(id + "_door",          WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            //this.framing[Framing.trapdoor.ordinal()]        = ModBlocks.register(id + "_door",               WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.crate_lid.ordinal()]         = ModBlocks.register(id + "_crate_lid",     CrateLid::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.crate.ordinal()]             = ModBlocks.register(id + "_crate",         (ctx -> new Crate(get(Framing.crate_lid).asItem(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
        }

        if (furniture){
            this.furniture[Furniture.night_stand.ordinal()]     = ModBlocks.register(id + "_night_stand", NightStandBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.drawer.ordinal()]          = ModBlocks.register(id + "_drawer", DrawerBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.storage_table.ordinal()]   = ModBlocks.register(id + "_storage_table", StorageTableBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.stool.ordinal()]           = ModBlocks.register(id + "_stool", (ctx) -> new Stool(ctx, 0.5f), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.chair.ordinal()]           = ModBlocks.register(id + "_chair", (ctx) -> new Chair(ctx, 0.5f), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.table.ordinal()]           = ModBlocks.register(id + "_table",    Table::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.throne.ordinal()]          = ModBlocks.register(id + "_throne", (ctx -> new Throne(ctx, 0.5f)), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.mirror.ordinal()]          = ModBlocks.register(id + "_mirror", MirrorBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.table_chest.ordinal()]     = ModBlocks.register(id + "_table_chest", TableChest::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.counter.ordinal()]           = ModBlocks.register(id + "_counter",             CounterBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.counter_shelves.ordinal()]   = ModBlocks.register(id + "_counter_shelves",     CounterShelvesBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.counter_chest.ordinal()]     = ModBlocks.register(id + "_counter_chest",       CounterChestBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.cabinet.ordinal()]           = ModBlocks.register(id + "_cabinet",             Cabinetblock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.cabinet_shelf.ordinal()]     = ModBlocks.register(id + "_cabinet_shelves",     CabinetShelvesBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.shelves.ordinal()]           = ModBlocks.register(id + "_shelves",             ShelvesBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
        }

        if (utensils) {
            this.utensils[Utensils.bowl.ordinal()]      = ModBlocks.register(id + "_bowl", Bowl::new, settings, WOOD_UTENSILS_GROUP_KEY);
            this.utensils[Utensils.mug.ordinal()]       = ModBlocks.register(id + "_mug",  Mug::new, settings, WOOD_UTENSILS_GROUP_KEY);
            this.utensils[Utensils.cutting_board.ordinal()]       = ModBlocks.register(id + "_cutting_board",  CuttingBoard::new, settings, WOOD_UTENSILS_GROUP_KEY);
        }

    }

    public enum Common {
        leaves,
        branch,
        log,
        stripped_log,
        slab,
        stripped_slab,
        beam,
        stripped_beam,
        planks,
        planks_stairs,
        portable_workbench
    }

    public enum Framing {
        //Textures
        parquet,
        parquet_stairs,
        siding,
        siding_stairs,
        //Blocks
        support,
        arch,
        half_arch,
        roof,
        sod_roof,
        arrow_slit,
        ladder,
        window_cover,
        door,
        crate_lid,
        crate,
    }

    public enum Furniture {
        night_stand,
        drawer,
        storage_table,
        stool,
        chair,
        table,
        throne,
        mirror,
        table_chest,
        counter,
        counter_shelves,
        counter_chest,
        cabinet,
        cabinet_shelf,
        shelves
    }

    public enum Utensils {
        bowl,
        mug,
        cutting_board
    }

    public boolean framingExist() {return framing != null;}
    public boolean furnitureExist() {return furniture != null;}
    public boolean utensilsExists() {return utensils != null;}

    public Block get(Common basic) {
        return this.common[basic.ordinal()];
    }
    public Block get(Framing framing) {
        return this.framing[framing.ordinal()];
    }
    public Block get(Furniture furniture) {
        return this.furniture[furniture.ordinal()];
    }
    public Block get(Utensils utensils) {
        return this.utensils[utensils.ordinal()];
    }

}
