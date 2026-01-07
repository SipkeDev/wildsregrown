package com.wildsregrown.registries.groups;

import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.stone.castle.*;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.*;


import static com.wildsregrown.registries.ModItemGroups.*;

public class ClayGroup {

    public ClayGroup(String id) {

        AbstractBlock.Settings settings = AbstractBlock.Settings.copy(Blocks.DIRT);

        this.bricks     = new Block[Bricks.values().length];
        this.old_bricks = new Block[OldBricks.values().length];
        this.tiles      = new Block[Tiles.values().length];
        this.plaster    = new Block[Plaster.values().length];

        this.soil = ModBlocks.register(id, SoilBlock::new, AbstractBlock.Settings.copy(Blocks.CLAY), SOIL_GROUP_KEY);

        this.bricks[Bricks.block.ordinal()]        = ModBlocks.register(id + "_bricks"            , Layered::new      , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.balustrade.ordinal()]   = ModBlocks.register(id + "_brick_balustrade"  , Balustrade::new    , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.wall_support.ordinal()] = ModBlocks.register(id + "_brick_wall_support", WallSupport::new   , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.arch.ordinal()]         = ModBlocks.register(id + "_brick_arch"        , ArchBlock::new     , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.wall.ordinal()]         = ModBlocks.register(id + "_brick_wall"        , WallBlock::new     , settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.arrow_slit.ordinal()]   = ModBlocks.register(id + "_brick_arrow_slit"  , ArrowSlitBlock::new, settings, SOIL_GROUP_KEY);
        this.bricks[Bricks.stairs.ordinal()]       = ModBlocks.register(id + "_brick_stairs"      , (s) -> new StairsBlock(bricks[Bricks.block.ordinal()].getDefaultState()  , s)      , settings        , SOIL_GROUP_KEY);
        this.bricks[Bricks.half_arch.ordinal()]    = ModBlocks.register(id + "_brick_half_arch"   , (s) -> new HalfArchBlock(bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);

        this.old_bricks[OldBricks.block.ordinal()]        = ModBlocks.register("old_" + id + "_bricks"            , Layered::new       , settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.balustrade.ordinal()]   = ModBlocks.register("old_" + id + "_brick_balustrade"  , Balustrade::new    , settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.wall_support.ordinal()] = ModBlocks.register("old_" + id + "_brick_wall_support", WallSupport::new   , settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.arch.ordinal()]         = ModBlocks.register("old_" + id + "_brick_arch"        , ArchBlock::new     , settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.wall.ordinal()]         = ModBlocks.register("old_" + id + "_brick_wall"        , WallBlock::new     , settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.arrow_slit.ordinal()]   = ModBlocks.register("old_" + id + "_brick_arrow_slit"  , ArrowSlitBlock::new, settings, SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.stairs.ordinal()]       = ModBlocks.register("old_" + id + "_brick_stairs"      , (s) -> new StairsBlock(old_bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);
        this.old_bricks[OldBricks.half_arch.ordinal()]    = ModBlocks.register("old_" + id + "_brick_half_arch"   , (s) -> new HalfArchBlock(old_bricks[Bricks.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);

        this.tiles[Tiles.block.ordinal()] = ModBlocks.register(id + "_tiles", Layered::new, settings, SOIL_GROUP_KEY);
        this.tiles[Tiles.stairs.ordinal()]  = ModBlocks.register(id + "_tile_stairs" , (s) -> new StairsBlock(tiles[Tiles.block.ordinal()].getDefaultState(), s)      , settings        , SOIL_GROUP_KEY);

        //this.plaster[Plaster.layered.ordinal()] = ModBlocks.register(id + "_layered_plaster", Layered::new, settings, SOIL_GROUP_KEY);
        //this.plaster[Plaster.stairs.ordinal()]  = ModBlocks.register(id + "_plaster_stairs" , (s) -> new StairsBlock(tiles[Tiles.block.ordinal()].getDefaultState(),s), settings, SOIL_GROUP_KEY);

    }

    public  final Block   soil;
    private final Block[] bricks;
    private final Block[] old_bricks;
    private final Block[] tiles;
    private final Block[] plaster;

    public Block get(Bricks bricks) {
        return this.bricks[bricks.ordinal()];
    }

    public Block get(OldBricks bricks) {
        return this.old_bricks[bricks.ordinal()];
    }

    public Block get(Tiles tiles) {
        return this.tiles[tiles.ordinal()];
    }

    public Block get(Plaster plaster) {
        return this.plaster[plaster.ordinal()];
    }

    public enum Bricks {
        //basic
        block,
        stairs,
        //deco
        balustrade,
        wall_support,
        half_arch,
        arch,
        wall,
        //castle
        arrow_slit
    }

    public enum OldBricks {
        //basic
        block,
        cuttings,
        stairs,
        //deco
        balustrade,
        wall_support,
        half_arch,
        arch,
        wall,
        //castle
        arrow_slit
    }

    public enum Tiles {
        //basic
        block,
        stairs,
        roof
    }

    public enum Plaster{
        layered,
        stairs
    }

}
