package com.wildsregrown.registries;

import com.sipke.registeries.Trees;
import com.wildsregrown.WildsRegrown;
import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.*;
import com.wildsregrown.blocks.decoration.GlassWindows;
import com.wildsregrown.blocks.dungeon.StructureBlock;
import com.wildsregrown.blocks.flora.flowers.ColoredFlowers;
import com.wildsregrown.blocks.flora.flowers.FlowerFlora;
import com.wildsregrown.blocks.flora.rooted.Chives;
import com.wildsregrown.blocks.flora.grass.Grass;
import com.wildsregrown.blocks.flora.grass.TallGrass;
import com.wildsregrown.blocks.flora.rooted.Nettle;
import com.wildsregrown.blocks.flora.shrubs.Shrub;
import com.wildsregrown.blocks.flora.shrubs.TallShrub;
import com.wildsregrown.blocks.TorchBlock;
import com.wildsregrown.blocks.wood.framing.Roof;
import com.wildsregrown.blocks.wood.tree.TreeSource;
import com.wildsregrown.registries.groups.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.registries.ModItemGroups.*;

public class ModBlocks {

    public static ArrayList<Block> CLIMBABLE = new ArrayList<>();
    public static ArrayList<Block> CUTOUTS = new ArrayList<>();

    public static final Block structureBlock = register("structure_block", StructureBlock::new, AbstractBlock.Settings.create(), TOOL_GROUP_KEY);

    /**
     * Sedimentary Stone groups
     */

    //Limestone -> White
    public static final StoneGroup limestone_white = new StoneGroup("limestone_white", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_beige = new StoneGroup("limestone_beige", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_grey = new StoneGroup("limestone_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_dark_grey = new StoneGroup("limestone_dark_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, true);

    public static final StoneGroup sandstone_white = new StoneGroup("sandstone_white", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_grey = new StoneGroup("sandstone_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_black = new StoneGroup("sandstone_black", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_beige = new StoneGroup("sandstone_beige", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_brown = new StoneGroup("sandstone_brown", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_pink = new StoneGroup("sandstone_pink", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_red = new StoneGroup("sandstone_red", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_yellow = new StoneGroup("sandstone_yellow", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);

    public static final StoneGroup shale_black = new StoneGroup("shale_black", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_dark_grey = new StoneGroup("shale_dark_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_grey = new StoneGroup("shale_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_red = new StoneGroup("shale_red", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, false);

    public static final StoneGroup travertine_white = new StoneGroup("travertine_white", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup travertine_grey = new StoneGroup("travertine_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup travertine_beige = new StoneGroup("travertine_beige", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);

    /**
     * Metamorphic Stone groups
     */

    //Slate -> black
    public static final StoneGroup slate_black = new StoneGroup("slate_black", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_grey = new StoneGroup("slate_grey", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_blue = new StoneGroup("slate_blue", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_purple = new StoneGroup("slate_purple", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_red = new StoneGroup("slate_red", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_green = new StoneGroup("slate_green", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), true, false);

    //Marble -> White
    public static final StoneGroup marble_white = new StoneGroup("marble_white", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_beige = new StoneGroup("marble_beige", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_black = new StoneGroup("marble_black", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_portoro = new StoneGroup("marble_portoro", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_green = new StoneGroup("marble_green", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_blue = new StoneGroup("marble_blue", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);

    /**
     * Igneous Stone groups
     */
    //Basalt -> Black
    public static final StoneGroup basalt_black = new StoneGroup("basalt_black", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);

    //Granite -> White
    public static final StoneGroup granite_white = new StoneGroup("granite_white", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup granite_pink = new StoneGroup("granite_pink", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup granite_red = new StoneGroup("granite_red", AbstractBlock.Settings.create().requiresTool().strength(1.5F, 6.0F), false, true);

    /**
     * Wood groups
     */
    //Fruit trees
    public static final WoodGroup apple = new WoodGroup("apple", 2f, false, true, true);
    public static final Block apple_source = register("apple_source", ctx -> new TreeSource(ctx, Trees.apple.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup pear  = new WoodGroup("pear", 2f, false, true, true);
    public static final Block pear_source = register("pear_source", ctx -> new TreeSource(ctx, Trees.pear.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup plum  = new WoodGroup("plum", 2f, false, true, true);
    public static final Block plum_source = register("plum_source", ctx -> new TreeSource(ctx, Trees.plum.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Magic trees
    public static final WoodGroup ancient_oak     = new WoodGroup("ancient_oak", 12f, true, true, false);
    public static final Block ancient_oak_source = register("ancient_oak_source", ctx -> new TreeSource(ctx, Trees.ancient_oak.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup jacaranda       = new WoodGroup("jacaranda", 8f, true, true, false);
    public static final Block jacaranda_source = register("jacaranda_source", ctx -> new TreeSource(ctx, Trees.jacaranda.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup glowing_willow  = new WoodGroup("glowing_willow", 6f, false, true, true);
    public static final Block glowing_willow_source = register("glowing_willow_source", ctx -> new TreeSource(ctx, Trees.glowing_willow.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Pines
    public static final WoodGroup larch = new WoodGroup("larch", 2f, true, true, false);
    public static final Block larch_source = register("larch_source", ctx -> new TreeSource(ctx, Trees.spruce.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup spruce = new WoodGroup("spruce", 2f, true, true, false);
    public static final Block spruce_source = register("spruce_source", ctx -> new TreeSource(ctx, Trees.larch.getKey()), AbstractBlock.Settings.copy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Soft woods
    public static final WoodGroup birch = new WoodGroup("birch", 2f, false, true, true);
    public static final Block dwarf_birch_source = register("dwarf_birch_source", ctx -> new TreeSource(ctx, Trees.dwarf_birch.getKey()), AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block silver_birch_source = register("silver_birch_source", ctx -> new TreeSource(ctx, Trees.silver_birch.getKey()), AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block tall_birch_source = register("tall_birch_source", ctx -> new TreeSource(ctx, Trees.tall_birch.getKey()), AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup willow = new WoodGroup("willow", 2f, false, true, true);
    public static final Block weeping_willow_source = register("weeping_willow_source", ctx -> new TreeSource(ctx, Trees.weeping_willow.getKey()), AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block bebb_willow_source = register("bebb_willow_source", ctx -> new TreeSource(ctx, Trees.bebb_willow.getKey()), AbstractBlock.Settings.copy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);

    //Hard woods
    public static final WoodGroup oak = new WoodGroup("oak", 6f, true, true, false);
    public static final Block oak_source = register("oak_source", ctx -> new TreeSource(ctx, Trees.oak.getKey()), AbstractBlock.Settings.copy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final Block dense_oak_source = register("dense_oak_source", ctx -> new TreeSource(ctx, Trees.dense_oak.getKey()), AbstractBlock.Settings.copy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final Block large_oak_source = register("large_oak_source", ctx -> new TreeSource(ctx, Trees.large_oak.getKey()), AbstractBlock.Settings.copy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup beech = new WoodGroup("beech", 6f, false, true, true);
    public static final Block beech_source = register("beech_source", ctx -> new TreeSource(ctx, Trees.beech.getKey()), AbstractBlock.Settings.copy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup ash = new WoodGroup("ash", 6f, true, true, true);
    public static final Block ash_source = register("ash_source", ctx -> new TreeSource(ctx, Trees.ash.getKey()), AbstractBlock.Settings.copy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);

    //MISC
    public static final Block glass_window = register("glass_window", GlassWindows::new, AbstractBlock.Settings.copy(Blocks.GLASS)      , DECORATION_GROUP_KEY);    static {CUTOUTS.add(glass_window);}
    public static final Block thatch_roof = register("thatch_roof", Roof::new         , AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)   , DECORATION_GROUP_KEY);
    public static final Block torch = registerWithoutItem("torch", TorchBlock::new, AbstractBlock.Settings.copy(Blocks.TORCH));

    //FLORA
    private static Block registerFlora(String id, Function<AbstractBlock.Settings, Block> factory) {
        Block block = register(id, factory, AbstractBlock.Settings.copy(Blocks.SHORT_GRASS).ticksRandomly().sounds(BlockSoundGroup.CHERRY_SAPLING), FLORA_GROUP_KEY);
        CUTOUTS.add(block);
        return block;
    }

    //Shrubs
    public static final Block heather_yellow = registerFlora("heather_yellow", ctx -> new Shrub(ctx, Color.decode("#ffdf00").getRGB(), 0.5f, 2, 8));
    public static final Block heather_purple = registerFlora("heather_purple", ctx -> new Shrub(ctx, Color.decode("#800080").getRGB(), 0.5f, 2, 8));
    public static final Block heather_lilac  = registerFlora("heather_lilac" , ctx -> new Shrub(ctx, Color.decode("#c8a2c8").getRGB(), 0.5f, 2, 8));
    public static final Block heather_mauve  = registerFlora("heather_mauve" , ctx -> new Shrub(ctx, Color.decode("#e0b0ff").getRGB(), 0.5f, 2, 8));
    public static final Block heather_pink   = registerFlora("heather_pink"  , ctx -> new Shrub(ctx, Color.decode("#ee82ee").getRGB(), 0.5f, 2, 8));
    public static final Block heather_red    = registerFlora("heather_red"   , ctx -> new Shrub(ctx, Color.decode("#e62020").getRGB(), 0.5f, 2, 8));
    public static final Block heather_silver = registerFlora("heather_silver", ctx -> new Shrub(ctx, Color.decode("#e6e6fa").getRGB(), 0.5f, 2, 8));
    public static final Block heather_white  = registerFlora("heather_white" , ctx -> new Shrub(ctx, Color.decode("#faf0e6").getRGB(), 0.5f, 2, 8));

    /**
     * Tall shrubs
     */
    //sage bush
    public static final Block sagebush_lilac  = registerFlora("sagebush_lilac", ctx -> new TallShrub(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.lilac, 0.7f, 1, 3));
    public static final Block sagebush_purple = registerFlora("sagebush_purple", ctx -> new TallShrub(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.richLilac, 0.7f, 1, 3));
    public static final Block sagebush_pink   = registerFlora("sagebush_pink", ctx -> new TallShrub(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.pinkLavender, 0.7f, 1, 3));
    public static final Block sagebush_white   = registerFlora("sagebush_white", ctx -> new TallShrub(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.lavender, 0.7f, 1, 3));

    //Hydrangea
    public static final Block hydrangea_white      = registerFlora("hydrangea_white"     , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#fffaf0").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_white_mist = registerFlora("hydrangea_white_mist", ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#e6e6fa").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_lime       = registerFlora("hydrangea_lime"      , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#d0f0c0").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_red        = registerFlora("hydrangea_red"       , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#c23b22").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_orange     = registerFlora("hydrangea_orange"    , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#ffb347").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_pink       = registerFlora("hydrangea_pink"      , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#fbaed2").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_violet     = registerFlora("hydrangea_violet"    , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#cb99c9").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_purple     = registerFlora("hydrangea_purple"    , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#bf94e4").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_blue       = registerFlora("hydrangea_blue"      , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#324ab2").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_blue_mist  = registerFlora("hydrangea_blue_mist" , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#ccccff").getRGB(), 0.3f, 8, 16));

    //Spirea
    public static final Block spirea_green_white  = registerFlora("spirea_green_white" , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_red    = registerFlora("spirea_green_red"   , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_mauve  = registerFlora("spirea_green_mauve" , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_pink   = registerFlora("spirea_green_pink"  , ctx -> new TallShrub(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_white = registerFlora("spirea_golden_white", ctx -> new TallShrub(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_red   = registerFlora("spirea_golden_red"  , ctx -> new TallShrub(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_mauve = registerFlora("spirea_golden_mauve", ctx -> new TallShrub(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_pink  = registerFlora("spirea_golden_pink" , ctx -> new TallShrub(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_white   = registerFlora("spirea_blue_white"  , ctx -> new TallShrub(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_red     = registerFlora("spirea_blue_red"    , ctx -> new TallShrub(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_mauve   = registerFlora("spirea_blue_mauve"  , ctx -> new TallShrub(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_pink    = registerFlora("spirea_blue_pink"   , ctx -> new TallShrub(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));

    //flowers
    public static final Block daisy            = registerFlora("daisy",     ctx -> new FlowerFlora(ctx, Colors.fernGreen, 0.5f, 1, 8));
    public static final Block poppy            = registerFlora("poppy",     ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    public static final Block dandelion        = registerFlora("dandelion", ctx -> new FlowerFlora(ctx, Colors.pastelGreen, 0.5f, 1, 8));
    public static final Block cornflower       = registerFlora("cornflower",ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    public static final Block marigold         = registerFlora("marigold",  ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    //artiplexes
    public static final Block artiplex_red      = registerFlora("artiplex_red"    , (ctx) -> new ColoredFlowers(ctx, Colors.pastelRed, Colors.darkPastelRed,0.7f, 5, 16));
    public static final Block artiplex_green    = registerFlora("artiplex_green"  , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.pastelGreen,0.7f, 5, 16));
    public static final Block artiplex_silver   = registerFlora("artiplex_silver", (ctx) -> new ColoredFlowers(ctx, Colors.fern,  Colors.ivory,0.7f, 5, 16));
    //orchids
    public static final Block orchid_white       = registerFlora("orchid_white"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.floralWhite,0.35f, 7, 5));
    public static final Block orchid_yellow      = registerFlora("orchid_yellow"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.pastelYellow,0.35f, 7, 5));
    public static final Block orchid_red         = registerFlora("orchid_red"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.pastelRed,0.35f, 7, 5));
    public static final Block orchid_orange      = registerFlora("orchid_orange"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.pastelOrange,0.35f, 7, 5));
    public static final Block orchid_blue        = registerFlora("orchid_blue"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.darkPastelBlue,0.35f, 7, 5));
    public static final Block orchid_purple      = registerFlora("orchid_purple"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelGreen, Colors.pastelPurple,0.35f, 7, 5));
    //Campanulas
    public static final Block campanula_white    = registerFlora("campanula_white"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.floralWhite,0.2f, 4, 5));
    public static final Block campanula_purple   = registerFlora("campanula_purple"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.richLilac,0.2f, 4, 5));
    public static final Block campanula_pink     = registerFlora("campanula_pink"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.vibrantPink,0.2f, 4, 5));
    public static final Block campanula_lilac    = registerFlora("campanula_lilac"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.lilac,0.2f, 4, 5));
    public static final Block campanula_blue     = registerFlora("campanula_blue"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.vibrantBlue,0.2f, 4, 5));
    //foxglove
    public static final Block foxglove_white    = registerFlora("foxglove_white"      , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.floralWhite,0.2f, 4, 5));
    public static final Block foxglove_pink     = registerFlora("foxglove_pink"      , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.lilac,0.2f, 4, 5));
    public static final Block foxglove_purple   = registerFlora("foxglove_purple"      , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.deepLavender,0.2f, 4, 5));
    public static final Block foxglove_yellow   = registerFlora("foxglove_yellow"      , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.vibrantYellow,0.2f, 4, 5));
    public static final Block foxglove_red      = registerFlora("foxglove_red"      , (ctx) -> new ColoredFlowers(ctx, Colors.fernGreen, Colors.vibrantRed,0.2f, 4, 5));
    //Tulips
    public static final Block tulip_white        = registerFlora("tulip_white"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.floralWhite,0.2f, 4, 5));
    public static final Block tulip_red          = registerFlora("tulip_red"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantRed,0.2f, 4, 5));
    public static final Block tulip_orange       = registerFlora("tulip_orange"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantOrange,0.2f, 4, 5));
    public static final Block tulip_yellow       = registerFlora("tulip_yellow"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantYellow,0.2f, 4, 5));
    public static final Block tulip_pink         = registerFlora("tulip_pink"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantPink,0.2f, 4, 5));
    public static final Block tulip_purple       = registerFlora("tulip_purple"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantPurple,0.2f, 4, 5));
    //lily's
    public static final Block lily_white        = registerFlora("lily_white"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.floralWhite,0.5f, 3, 12));
    public static final Block lily_yellow       = registerFlora("lily_yellow"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.pastelYellow,0.5f, 3, 12));
    public static final Block lily_orange       = registerFlora("lily_orange"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.pastelOrange,0.5f, 3, 12));
    public static final Block lily_red          = registerFlora("lily_red"          , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.pastelRed,0.5f, 3, 12));
    public static final Block lily_purple       = registerFlora("lily_purple"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.pinkLavender,0.5f, 3, 12));
    public static final Block lily_pink         = registerFlora("lily_pink"      , (ctx) -> new ColoredFlowers(ctx, Colors.fern, Colors.vibrantPink,0.5f, 3, 12));
    //sea holly
    public static final Block sea_holly_light = registerFlora("sea_holly_light"          , (ctx) -> new ColoredFlowers(ctx, Colors.lightBlue, Colors.darkPastelPurple,0.8f, 2, 3));
    public static final Block sea_holly_silver = registerFlora("sea_holly_silver"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelBlue, Colors.pinkLavender,0.8f, 2, 3));
    public static final Block sea_holly_blue = registerFlora("sea_holly_blue"      , (ctx) -> new ColoredFlowers(ctx, Colors.darkPastelBlue, Colors.blueLavender,0.8f, 2, 3));
    //bluebottle
    public static final Block blue_bottle_light  = registerFlora("blue_bottle_light"          , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.lightBlue,0.5f, 3, 12));
    public static final Block blue_bottle        = registerFlora("blue_bottle"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.darkPastelPurple,0.5f, 3, 12));
    public static final Block blue_bottle_dark   = registerFlora("blue_bottle_dark"      , (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.5f, 3, 12));

    //leek
    public static final Block leek = registerFlora("leek", (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.8f, 5, 5));
    //cabbage
    public static final Block oak_leaf_cabbage = registerFlora("oak_leaf_cabbage", (ctx) -> new ColoredFlowers(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.1f, 1, 18));

    //grass
    public static final Block tall_curly_grass = registerFlora("tall_curly_grass", ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block tall_grass       = registerFlora("tall_grass"      , ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block tall_thin_grass  = registerFlora("tall_thin_grass" , ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block curly_grass      = registerFlora("curly_grass"     , ctx -> new Grass(ctx, Colors.fernGreen, 0.7f, 1, 5));
    public static final Block grass            = registerFlora("grass"           , ctx -> new Grass(ctx, Colors.fernGreen, 0.7f, 1, 5));
    public static final Block thin_grass       = registerFlora("thin_grass"      , ctx -> new Grass(ctx, Colors.fernGreen, 0.7f, 1, 5));

    //rooted large
    public static final Block nettle            = registerFlora("nettle", Nettle::new);
    public static final Block chives_pink = registerFlora("chives_pink", ctx -> new Chives(ctx, Colors.pinkLavender));
    public static final Block chives_lilac = registerFlora("chives_lilac", ctx -> new Chives(ctx, Colors.lilac));
    public static final Block chives_purple = registerFlora("chives_purple", ctx -> new Chives(ctx, Colors.pastelPurple));

    public static final Block snow = register("snow", GravelBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);

    public static final Block gravel_beige = register("gravel_beige", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_black = register("gravel_black", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_blue = register("gravel_blue", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_brown = register("gravel_brown", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_green = register("gravel_green", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_grey = register("gravel_grey", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_pink = register("gravel_pink", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_purple = register("gravel_purple", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_red = register("gravel_red", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_white = register("gravel_white", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_yellow = register("gravel_yellow", GravelBlock::new, AbstractBlock.Settings.copy(Blocks.GRAVEL), SOIL_GROUP_KEY);

    public static final Block sand_white  = register("sand_white", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_black  = register("sand_black", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_beige  = register("sand_beige", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_brown  = register("sand_brown", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_grey   = register("sand_grey", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_pink   = register("sand_pink", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_red    = register("sand_red", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_yellow = register("sand_yellow", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);

    //Loam
    public static final Block loam_red    = register("loam_red", SoilBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_yellow = register("loam_yellow", SoilBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_beige  = register("loam_beige", SoilBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_brown  = register("loam_brown", SoilBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_black  = register("loam_black", SoilBlock::new, AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);

    //Peat
    public static final Block peat_brown = register("peat_brown", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block peat_black = register("peat_black", SoilBlock::new ,AbstractBlock.Settings.copy(Blocks.DIRT), SOIL_GROUP_KEY);

    //Clay
    public static final ClayGroup clay_yellow = new ClayGroup("clay_yellow");
    public static final ClayGroup clay_red = new ClayGroup("clay_red");
    public static final ClayGroup clay_beige = new ClayGroup("clay_beige");
    public static final ClayGroup clay_brown = new ClayGroup("clay_brown");
    public static final ClayGroup clay_black = new ClayGroup("clay_black");

    //Metals
    public static final MetalGroup tin = new MetalGroup("tin", false, false, false);
    public static final MetalGroup tin_polished = new MetalGroup("tin_polished", false, false, false);
    public static final MetalGroup copper = new MetalGroup("copper", true, false, false);
    //public static final MetalGroup copper_polished = new MetalGroup("copper_polished", true, false, false);

    public static final MetalGroup iron = new MetalGroup("iron", true, true, true);
    //public static final MetalGroup iron_polished = new MetalGroup("iron_polished", true, true, true);
    //public static final MetalGroup cast_iron = new MetalGroup("cast_iron", true, true, true);
    public static final MetalGroup steel = new MetalGroup("steel", true, true, true);
    //public static final MetalGroup steel_polished = new MetalGroup("steel_polished", true, true, true);
    public static final MetalGroup silver = new MetalGroup("silver", false, false, false);
    public static final MetalGroup silver_polished = new MetalGroup("silver_polished",false, false, false);
    public static final MetalGroup gold = new MetalGroup("gold", false, false, false);
    public static final MetalGroup gold_polished = new MetalGroup("gold_polished", false, false, false);

    //Fabrics
    public static final FabricGroup linen_natural = new FabricGroup("linen_natural", Colors.linen);
    public static final FabricGroup lace_natural = new FabricGroup("lace_natural", Colors.linen);
    public static final FabricGroup jute_natural = new FabricGroup("jute_natural", Colors.linen);
    public static final FabricGroup burlap_natural = new FabricGroup("burlap_natural", Colors.linen);
    public static final FabricGroup leather_natural = new FabricGroup("leather_natural", Colors.linen);

    //Crystals
    public static final Block amethyst      = register("amethyst", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block diamond       = register("diamond", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block quartz        = register("quartz", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block emerald       = register("emerald", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block ruby          = register("ruby", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));
    public static final Block sapphire      = register("sapphire", Layered::new, AbstractBlock.Settings.copy(Blocks.QUARTZ_BLOCK));

    //Ores
    public static final Block coal          = register("coal", Layered::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block lignite       = register("lignite", Layered::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block anthracite    = register("anthracite", Layered::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block banded_iron   = register("banded_iron", Layered::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);

    protected static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid, path));
        final Block block = Blocks.register(registryKey, factory, settings);
        Items.register(block);
        return block;
    }

    public static Block register(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, RegistryKey<ItemGroup> key) {
        Block block = register(path, factory, settings);
        ItemGroupEvents.modifyEntriesEvent(key).register(itemGroup -> {
            itemGroup.add(block);
        });
        return block;
    }

    protected static Block registerWithoutItem(String path, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        final RegistryKey<Block> registryKey = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modid, path));
        return Blocks.register(registryKey, factory, settings);
    }

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering blocks");
    }

}


