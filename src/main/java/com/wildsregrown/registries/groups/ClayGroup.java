package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.HalfStairs;
import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.QuarterStairs;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.decoration.pottery.Amphora;
import com.wildsregrown.blocks.decoration.pottery.Urn;
import com.wildsregrown.blocks.stone.castle.*;
import com.wildsregrown.blocks.wood.framing.Roof;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.*;


import static com.wildsregrown.registries.ModItemGroups.*;

public class ClayGroup {

    public ClayGroup(String id) {

        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.DIRT);

        this.bricks     = new Block[Bricks.values().length];
        this.old_bricks = new Block[Bricks.values().length];
        this.tiles      = new Block[Tiles.values().length];
        this.plaster    = new Block[Plaster.values().length];
        this.pottery    = new Block[Pottery.values().length];

        //Soil
        this.soil = ModBlocks.register(id, SoilBlock::new, AbstractBlock.Settings.copy(Blocks.CLAY), SOIL_GROUP_KEY);

        //Bricks
        this.bricks[Bricks.block.ordinal()]        = ModBlocks.register(id + "_bricks"            , Layered::new      , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.quarter_stairs.ordinal()]= ModBlocks.register(id + "_brick_quarter_stairs", QuarterStairs::new, settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.half_stairs.ordinal()]   = ModBlocks.register(id + "_brick_half_stairs", HalfStairs::new, settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.stairs.ordinal()]       = ModBlocks.register(id + "_brick_stairs"      , (s) -> new StairsBlock(bricks[Bricks.block.ordinal()].getDefaultState()  , s)      , settings        , SOIL_GROUP_KEY);
        this.bricks[Bricks.balustrade.ordinal()]   = ModBlocks.register(id + "_brick_balustrade"  , Balustrade::new    , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.wall_support.ordinal()] = ModBlocks.register(id + "_brick_wall_support", WallSupport::new   , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.arch.ordinal()]         = ModBlocks.register(id + "_brick_arch"        , ArchBlock::new     , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.arrow_slit.ordinal()]   = ModBlocks.register(id + "_brick_arrow_slit"  , ArrowSlitBlock::new, settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.half_arch.ordinal()]    = ModBlocks.register(id + "_brick_half_arch"   , (s) -> new HalfArchBlock(bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);

        //Old Bricks
        this.old_bricks[Bricks.block.ordinal()]        = ModBlocks.register("old_" + id + "_bricks"            , Layered::new       , settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.quarter_stairs.ordinal()]= ModBlocks.register("old_" + id + "_brick_quarter_stairs", QuarterStairs::new, settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.half_stairs.ordinal()]   = ModBlocks.register("old_" + id + "_brick_half_stairs", HalfStairs::new, settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.stairs.ordinal()]       = ModBlocks.register("old_" + id + "_brick_stairs"      , (s) -> new StairsBlock(old_bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);
        this.old_bricks[Bricks.balustrade.ordinal()]   = ModBlocks.register("old_" + id + "_brick_balustrade"  , Balustrade::new    , settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.wall_support.ordinal()] = ModBlocks.register("old_" + id + "_brick_wall_support", WallSupport::new   , settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.arch.ordinal()]         = ModBlocks.register("old_" + id + "_brick_arch"        , ArchBlock::new     , settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.arrow_slit.ordinal()]   = ModBlocks.register("old_" + id + "_brick_arrow_slit"  , ArrowSlitBlock::new, settings, SOIL_GROUP_KEY);
        this.old_bricks[Bricks.half_arch.ordinal()]    = ModBlocks.register("old_" + id + "_brick_half_arch"   , (s) -> new HalfArchBlock(old_bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);

        //Roof Tiles
        this.tiles[Tiles.block.ordinal()] = ModBlocks.register(id + "_tiles", Layered::new, settings, SOIL_GROUP_KEY);
        this.tiles[Tiles.quarter_stairs.ordinal()]= ModBlocks.register(id + "_tile" + "_quarter_stairs", QuarterStairs::new, settings, SOIL_GROUP_KEY);
        this.tiles[Tiles.half_stairs.ordinal()]   = ModBlocks.register(id + "_tile" + "_half_stairs", HalfStairs::new, settings, SOIL_GROUP_KEY);
        this.tiles[Tiles.stairs.ordinal()]  = ModBlocks.register(id + "_tile_stairs" , (s) -> new StairsBlock(tiles[Tiles.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);
        this.tiles[Tiles.roof.ordinal()]  = ModBlocks.register(id + "_tile_roof" , Roof::new, settings, SOIL_GROUP_KEY);

        //Plaster
        this.plaster[Plaster.layered.ordinal()] = ModBlocks.register(id + "_plaster_layered", Layered::new, settings, SOIL_GROUP_KEY);
        this.plaster[Plaster.old_layered.ordinal()] = ModBlocks.register(id + "_old_plaster_layered", Layered::new, settings, SOIL_GROUP_KEY);
        this.plaster[Plaster.waved_layered.ordinal()] = ModBlocks.register(id + "_waved_plaster_layered", Layered::new, settings, SOIL_GROUP_KEY);

        //pottery
        this.pottery[Pottery.urn.ordinal()] = ModBlocks.register(id + "_urn", Urn::new, settings, SOIL_GROUP_KEY);
        this.pottery[Pottery.urn_old.ordinal()] = ModBlocks.register(id + "_urn_old", Urn::new, settings, SOIL_GROUP_KEY);
        this.pottery[Pottery.amphora.ordinal()] = ModBlocks.register(id + "_amphora", Amphora::new, settings, SOIL_GROUP_KEY);
        this.pottery[Pottery.amphora_old.ordinal()] = ModBlocks.register(id + "_amphora_old", Amphora::new, settings, SOIL_GROUP_KEY);

    }

    public  final Block   soil;
    private final Block[] bricks;
    private final Block[] old_bricks;
    private final Block[] tiles;
    private final Block[] plaster;
    private final Block[] pottery;

    public Block getBricks(Bricks bricks) {
        return this.bricks[bricks.ordinal()];
    }
    public Block getOldBricks(Bricks bricks) {
        return this.old_bricks[bricks.ordinal()];
    }
    public Block get(Tiles tiles) {
        return this.tiles[tiles.ordinal()];
    }
    public Block get(Plaster plaster) {
        return this.plaster[plaster.ordinal()];
    }
    public Block get(Pottery pottery) {return this.pottery[pottery.ordinal()];}

    public enum Bricks {
        //basic
        block,
        quarter_stairs,
        half_stairs,
        stairs,
        //deco
        balustrade,
        wall_support,
        half_arch,
        arch,
        //castle
        arrow_slit
    }

    public enum Tiles {
        //basic
        block,
        quarter_stairs,
        half_stairs,
        stairs,
        roof
    }

    public enum Plaster{
        layered,
        old_layered,
        waved_layered
    }

    public enum Pottery{
        urn,
        urn_old,
        amphora,
        amphora_old,
    }

}
