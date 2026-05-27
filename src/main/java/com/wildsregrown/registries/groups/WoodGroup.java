package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.carpentry.framing.tudor.TudorHorizontal;
import com.wildsregrown.blocks.carpentry.framing.tudor.TudorItem;
import com.wildsregrown.blocks.carpentry.framing.tudor.TudorSquare;
import com.wildsregrown.blocks.carpentry.framing.tudor.TudorVertical;
import com.wildsregrown.blocks.carpentry.interior.cabinet.CabinetItem;
import com.wildsregrown.blocks.carpentry.interior.counter.CounterItem;
import com.wildsregrown.blocks.crafting.PortableWorkBench;
import com.wildsregrown.blocks.carpentry.framing.beam.*;
import com.wildsregrown.blocks.carpentry.furniture.surfaces.WoodenTable;
import com.wildsregrown.blocks.carpentry.interior.counter.CounterBlock;
import com.wildsregrown.blocks.carpentry.interior.counter.CounterChestBlock;
import com.wildsregrown.blocks.carpentry.interior.counter.CounterShelvesBlock;
import com.wildsregrown.blocks.carpentry.interior.cabinet.CabinetShelvesBlock;
import com.wildsregrown.blocks.carpentry.interior.cabinet.CabinetBlock;
import com.wildsregrown.blocks.carpentry.furniture.sitable.*;
import com.wildsregrown.blocks.carpentry.furniture.storage.*;
import com.wildsregrown.blocks.carpentry.tree.HalfLog;
import com.wildsregrown.blocks.carpentry.tree.Log;
import com.wildsregrown.blocks.carpentry.*;
import com.wildsregrown.blocks.carpentry.framing.*;
import com.wildsregrown.blocks.carpentry.furniture.*;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModItems;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import wildsregrown.api.block.tree.Leaves;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.registries.ModItemGroups.*;

public class WoodGroup {

    private final Block[] common;
    private final Block[] framing;
    private final Item[] framingItems;
    private final Block[] furniture;
    private final Block[] interior;
    private final Item[] interiorItems;
    //private final CounterItem counterItem;

    public WoodGroup(String id, float strength, boolean framing, boolean furniture, boolean interior, boolean utensils) {

        BlockBehaviour.Properties settings = BlockBehaviour.Properties.of()
                .instrument(NoteBlockInstrument.BASS)
                .strength(strength)
                .sound(SoundType.WOOD)
                .ignitedByLava();

        this.common    = new Block[Common.values().length];
        this.framing   = framing ? new Block[Framing.values().length]    : null;
        this.framingItems   = framing ? new Item[FramingItem.values().length]    : null;
        this.furniture = furniture ? new Block[Furniture.values().length]: null;
        this.interior  = interior ? new Block[Interior.values().length]  : null;
        this.interiorItems   = interior ? new Item[InteriorItem.values().length]    : null;

        this.common[Common.leaves.ordinal()]              = ModBlocks.register(id + "_leaves",                    Leaves::new, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), WOOD_GROUP_KEY);
        //this.common[Common.branch.ordinal()]              = ModBlocks.register(id + "_branch",                    TreeBranch::new, settings, WOOD_GROUP_KEY);
        this.common[Common.log.ordinal()]                 = ModBlocks.register(id + "_log",                       Log::new, settings, WOOD_GROUP_KEY);
        this.common[Common.stripped_log.ordinal()]        = ModBlocks.register("stripped_"+ id + "_log",          Log::new, settings, WOOD_GROUP_KEY);
        this.common[Common.slab.ordinal()]                = ModBlocks.register(id + "_slab",                  HalfLog::new, settings, WOOD_GROUP_KEY);
        this.common[Common.stripped_slab.ordinal()]       = ModBlocks.register("stripped_"+ id + "_slab",     HalfLog::new, settings, WOOD_GROUP_KEY);

        //this.common[Common.beam.ordinal()]                = ModBlocks.register(id + "_beam",                      BeamSupport::new, settings, WOOD_GROUP_KEY);
        //this.common[Common.stripped_beam.ordinal()]       = ModBlocks.register("stripped_"+ id + "_beam",         BeamSupport::new, settings, WOOD_GROUP_KEY);
        this.common[Common.planks.ordinal()]              = ModBlocks.register(id + "_planks",                    Planks::new, settings, WOOD_GROUP_KEY);
        this.common[Common.planks_stairs.ordinal()]       = ModBlocks.register(id + "_planks_stairs", s -> new PaintedStairs(common[Common.planks.ordinal()].defaultBlockState(), s), settings,  WOOD_GROUP_KEY);
        this.common[Common.portable_workbench.ordinal()]  = ModBlocks.register(id + "_portable_workbench", PortableWorkBench::new, settings, WOOD_GROUP_KEY);

        if (framing) {
            /// additional textures
            this.framing[Framing.parquet.ordinal()]          = ModBlocks.register(id + "_parquet", Planks::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.parquet_stairs.ordinal()]   = ModBlocks.register(id + "_parquet_stairs", (ctx -> new PaintedStairs(this.framing[0].defaultBlockState(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.siding.ordinal()]           = ModBlocks.register(id + "_siding", Planks::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.siding_stairs.ordinal()]    = ModBlocks.register(id + "_siding_stairs", (ctx -> new PaintedStairs(this.framing[0].defaultBlockState(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
            /// basic shapes
            this.framing[Framing.open_stairs.ordinal()]       = ModBlocks.register(id + "_basic_open_stairs", (ctx -> new OpenStairs(this.framing[0].defaultBlockState(), ctx)), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.beam_support.ordinal()]      = ModBlocks.registerWithoutItem(id + "_beam_support",       ctx->new BeamSupport(id+"_beam_framing", ctx), settings);
            this.framing[Framing.beam_post.ordinal()]         = ModBlocks.registerWithoutItem(id + "_beam_post",       ctx->new PostSupport(id+"_beam_framing", ctx), settings);
            this.framing[Framing.beam_ceiling.ordinal()]      = ModBlocks.registerWithoutItem(id + "_beam_ceiling",       ctx->new CeilingSupport(id+"_beam_framing", ctx), settings);
            this.framing[Framing.beam_diagonal.ordinal()]     = ModBlocks.registerWithoutItem(id + "_beam_diagonal",       ctx->new DiagonalSupport(id+"_beam_framing", ctx), settings);
            //this.framing[Framing.plank_support.ordinal()]   = ModBlocks.register(id + "_plank_support",       WoodenBeamSupport::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.basic_ladder.ordinal()]      = ModBlocks.register(id + "_basic_ladder",        LadderBlock::new, settings, WOOD_FRAMING_GROUP_KEY); ModBlocks.CLIMBABLE.add(this.framing[Framing.basic_ladder.ordinal()]);
            this.framing[Framing.basic_door.ordinal()]        = ModBlocks.register(id + "_basic_door",          WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.basic_door_window.ordinal()] = ModBlocks.register(id + "_basic_door_window",   WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.refined_door.ordinal()]      = ModBlocks.register(id + "_refined_door",       WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.refined_door_window.ordinal()]= ModBlocks.register(id + "_refined_door_window", WoodenDoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.basic_trapdoor.ordinal()]    = ModBlocks.register(id + "_basic_trapdoor",      Trapdoor::new, settings, WOOD_FRAMING_GROUP_KEY);
            /// castle
            //todo move to seperate category for exterior decoration
            this.framing[Framing.basic_arch.ordinal()]        = ModBlocks.register(id + "_basic_arch",          WoodenArchBlock::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.basic_half_arch.ordinal()]   = ModBlocks.register(id + "_basic_half_arch",     (s) -> new WoodenHalfArchBlock(common[Common.log.ordinal()].defaultBlockState(),s), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.basic_arrow_slit.ordinal()]  = ModBlocks.register(id + "_basic_arrow_slit",    ctx -> new WoodenArrowSlit(ctx, 0), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.refined_arrow_slit.ordinal()]= ModBlocks.register(id + "_refined_arrow_slit",  ctx -> new WoodenArrowSlit(ctx, 1), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.cross_arrow_slit.ordinal()]  = ModBlocks.register(id + "_cross_arrow_slit",    ctx -> new WoodenArrowSlit(ctx, 2), settings, WOOD_FRAMING_GROUP_KEY);
            /// exterior
            this.framing[Framing.basic_window_cover.ordinal()]= ModBlocks.register(id + "_basic_window_cover",  WindowCover::new, settings, WOOD_FRAMING_GROUP_KEY);
            /// roof
            this.framing[Framing.roof.ordinal()]              = ModBlocks.register(id + "_roof",          ColoredRoof::new, settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.sod_roof.ordinal()]          = ModBlocks.register(id + "_sod_roof",      SodRoof::new, settings, WOOD_FRAMING_GROUP_KEY);
            /// Tudor
            this.framing[Framing.tudor_square.ordinal()]      = ModBlocks.register(id + "_tudor_square",  ctx-> new TudorSquare(id+"_tudor", ctx), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.tudor_horizontal.ordinal()]  = ModBlocks.register(id + "_tudor_horizontal",  ctx-> new TudorHorizontal(id+"_tudor", ctx), settings, WOOD_FRAMING_GROUP_KEY);
            this.framing[Framing.tudor_vertical.ordinal()]    = ModBlocks.register(id + "_tudor_vertical",ctx-> new TudorVertical(id+"_tudor", ctx), settings, WOOD_FRAMING_GROUP_KEY);

            this.framingItems[FramingItem.tudor.ordinal()] = ModItems.registerItem(id+"_tudor", ctx->new TudorItem(ctx,
                    this.get(Framing.tudor_square),
                    this.get(Framing.tudor_horizontal),
                    this.get(Framing.tudor_vertical)
            ), new Item.Properties(), WOOD_FRAMING_GROUP_KEY);

            this.framingItems[FramingItem.beam_framing.ordinal()] =  ModItems.registerItem(id+"_beam_framing", ctx->new FramingBeamItem(ctx,
                    this.get(WoodGroup.Framing.beam_support),
                    this.get(WoodGroup.Framing.beam_post),
                    this.get(WoodGroup.Framing.beam_ceiling),
                    this.get(WoodGroup.Framing.beam_diagonal)
            ), new Item.Properties(), WOOD_FRAMING_GROUP_KEY);
        }

        if (furniture) {
            /// sitables
            this.furniture[Furniture.stool.ordinal()] = ModBlocks.register(id + "_basic_stool", (ctx) -> new Stool(ctx, 0.5f, 0), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_stool.ordinal()] = ModBlocks.register(id + "_refined_stool", (ctx) -> new Stool(ctx, 0.5f, 1), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.basic_chair.ordinal()] = ModBlocks.register(id + "_basic_chair", (ctx) -> new WoodenChair(ctx, 0,0.5), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_chair.ordinal()] = ModBlocks.register(id + "_refined_chair", (ctx) -> new WoodenChair(ctx, 1,0.5), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.throne.ordinal()] = ModBlocks.register(id + "_throne", (ctx) -> new WoodenChair(ctx, 2,0.5), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.basic_bench.ordinal()] = ModBlocks.register(id + "_basic_bench", ctx -> new WoodenBench(ctx, 0), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_bench.ordinal()] = ModBlocks.register(id + "_refined_bench", ctx -> new WoodenBench(ctx, 1), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.basic_bench_with_backrest.ordinal()] = ModBlocks.register(id + "_basic_bench_with_backrest", ctx -> new WoodenBenchBackrest(ctx, 0), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_bench_with_backrest.ordinal()] = ModBlocks.register(id + "_refined_bench_with_backrest", ctx -> new WoodenBenchBackrest(ctx, 1), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.luxury_bench_with_backrest.ordinal()] = ModBlocks.register(id + "_luxury_bench_with_backrest", ctx -> new WoodenBenchBackrest(ctx, 2), settings, WOOD_FURNITURE_GROUP_KEY);

            /// working surface
            this.furniture[Furniture.basic_storage_table.ordinal()] = ModBlocks.register(id + "_basic_storage_table", StorageTableBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.basic_table.ordinal()] = ModBlocks.register(id + "_basic_table", ctx -> new WoodenTable(ctx, 0), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_table.ordinal()] = ModBlocks.register(id + "_refined_table", ctx -> new WoodenTable(ctx, 1), settings, WOOD_FURNITURE_GROUP_KEY);

            /// Storage
            this.furniture[Furniture.basic_night_stand.ordinal()] = ModBlocks.register(id + "_basic_night_stand", NightStandBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.basic_table_chest.ordinal()] = ModBlocks.register(id + "_basic_table_chest", TableChest::new, settings, WOOD_FURNITURE_GROUP_KEY);

            /// decoration
            this.furniture[Furniture.basic_mirror.ordinal()] = ModBlocks.register(id + "_basic_mirror", ctx -> new MirrorBlock(ctx, 0), settings, WOOD_FURNITURE_GROUP_KEY);
            this.furniture[Furniture.refined_mirror.ordinal()] = ModBlocks.register(id + "_refined_mirror", ctx -> new MirrorBlock(ctx, 1), settings, WOOD_FURNITURE_GROUP_KEY);
        }

        if (interior){
            /// storage
            Identifier counter = Identifier.fromNamespaceAndPath(modid, id+"_counter");
            this.interior[Interior.counter.ordinal()]           = ModBlocks.registerWithoutItem(id + "_counter", ctx->new CounterBlock(counter, ctx), settings);
            this.interior[Interior.counter_shelves.ordinal()]   = ModBlocks.registerWithoutItem(id + "_counter_shelves", ctx->new CounterShelvesBlock(counter, ctx), settings);
            this.interior[Interior.counter_chest.ordinal()]     = ModBlocks.registerWithoutItem(id + "_counter_chest", ctx->new CounterChestBlock(counter, ctx), settings);
            this.interiorItems[InteriorItem.counter.ordinal()] = ModItems.registerItem(counter, ctx->new CounterItem(ctx,
                    this.get(Interior.counter),
                    this.get(Interior.counter_shelves),
                    this.get(Interior.counter_chest)
            ), new Item.Properties(), WOOD_FRAMING_GROUP_KEY);

            Identifier cabinet = Identifier.fromNamespaceAndPath(modid, id+"_cabinet");
            this.interior[Interior.cabinet.ordinal()]           = ModBlocks.registerWithoutItem(id + "_cabinet", ctx->new CabinetBlock(cabinet, ctx), settings);
            this.interior[Interior.cabinet_shelf.ordinal()]     = ModBlocks.registerWithoutItem(id + "_cabinet_shelves",ctx->new CabinetShelvesBlock(cabinet, ctx), settings);
            this.interiorItems[InteriorItem.cabinet.ordinal()] = ModItems.registerItem(cabinet, ctx->new CabinetItem(ctx,
                    this.get(Interior.cabinet),
                    this.get(Interior.cabinet_shelf)
            ), new Item.Properties(), WOOD_FRAMING_GROUP_KEY);

            this.interior[Interior.shelves.ordinal()]           = ModBlocks.register(id + "_shelves",             ShelvesBlock::new, settings, WOOD_FURNITURE_GROUP_KEY);

            this.interior[Interior.crate_lid.ordinal()]         = ModBlocks.register(id + "_crate_lid",     CrateLid::new, settings, WOOD_FURNITURE_GROUP_KEY);
            this.interior[Interior.crate.ordinal()]             = ModBlocks.register(id + "_crate",         (ctx -> new Crate(get(Interior.crate_lid).asItem(), ctx)), settings, WOOD_FURNITURE_GROUP_KEY);
            this.interior[Interior.barrel.ordinal()]            = ModBlocks.register(id + "_barrel",        Barrel::new, settings, WOOD_FURNITURE_GROUP_KEY);

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
        rough_plank,
        stick,
        stripped_stick,
        planks,
        planks_stairs,
        portable_workbench
    }

    public enum Framing {
        /// additional textures
        parquet,
        parquet_stairs,
        siding,
        siding_stairs,
        /// basic shapes
        open_stairs,
        beam_support,
        beam_post,
        beam_ceiling,
        beam_diagonal,
        plank_support,
        basic_ladder,
        /// entrance
        basic_door,
        basic_door_window,
        refined_door,
        refined_door_window,
        basic_trapdoor,
        /// castle
        basic_arch,
        basic_half_arch,
        basic_arrow_slit,
        refined_arrow_slit,
        cross_arrow_slit,
        /// exterior
        basic_window_cover,
        /// roof
        roof,
        sod_roof,
        /// Tudor
        tudor_square,
        tudor_horizontal,
        tudor_vertical,
    }
    public enum FramingItem {
        beam_framing,
        tudor
    }

    public enum Furniture {
        /// sitable furniture
        stool,
        refined_stool,
        basic_chair,
        refined_chair,
        throne,
        basic_bench,
        refined_bench,
        basic_bench_with_backrest,
        refined_bench_with_backrest,
        luxury_bench_with_backrest,
        /// working surface
        basic_storage_table,
        basic_dining_table,
        refined_dining_table,
        basic_table,
        refined_table,
        /// Storage
        basic_night_stand,
        basic_table_chest,
        /// decoration
        basic_mirror,
        refined_mirror,
    }

    public enum Interior{
        /// storage
        counter_chest,
        counter,
        counter_shelves,
        cabinet,
        cabinet_shelf,
        shelves,
        crate_lid,
        crate,
        barrel,
    }
    public enum InteriorItem {
        counter,
        cabinet
    }

    public boolean framingExist() {return framing != null;}
    public boolean furnitureExist() {return furniture != null;}
    public boolean interiorExists() {return interior != null;}

    public Block get(Common basic) {return this.common[basic.ordinal()];}
    public Block get(Framing framing) {
        return this.framing[framing.ordinal()];
    }
    public Item get(FramingItem framing) {
        return this.framingItems[framing.ordinal()];
    }
    public Block get(Furniture furniture) {
        return this.furniture[furniture.ordinal()];
    }
    public Block get(Interior interior) {
        return this.interior[interior.ordinal()];
    }
    public Item get(InteriorItem framing) {
        return this.interiorItems[framing.ordinal()];
    }
}
