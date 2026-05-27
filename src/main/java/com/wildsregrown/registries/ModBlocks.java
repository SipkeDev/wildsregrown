package com.wildsregrown.registries;

import com.wildsregrown.blocks.crafting.PortableAnvil;
import com.wildsregrown.blocks.decoration.TorchBlock;
import com.wildsregrown.registries.world.Trees;
import com.wildsregrown.WildsRegrown;
import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.decoration.Candles;
import com.wildsregrown.blocks.decoration.ColoredGlassPane;
import com.wildsregrown.blocks.decoration.GlassPane;
import com.wildsregrown.blocks.flora.Chives;
import com.wildsregrown.blocks.flora.TallGrass;
import com.wildsregrown.blocks.flora.Nettle;
import com.wildsregrown.blocks.flora.TallShrubFlora;
import com.wildsregrown.blocks.carpentry.framing.Roof;
import com.wildsregrown.registries.groups.*;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import wildsregrown.api.block.flora.type.FlowerFlora;
import wildsregrown.api.block.flora.type.GrassFlora;
import wildsregrown.api.block.flora.type.ShrubFlora;
import wildsregrown.api.block.materials.*;
import wildsregrown.api.block.shapes.Layered;
import wildsregrown.api.block.tree.dynamic.TreeSource;
import wildsregrown.api.registry.defaults.ApiEntities;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Function;

import static com.sipke.api.features.Colors.pack;
import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.registries.ModItemGroups.*;

public class ModBlocks {

    public static ArrayList<Block> CLIMBABLE = new ArrayList<>();
    /**
     * Crafting
     */
    public static final Block portable_anvil = register("portable_anvil", PortableAnvil::new, BlockBehaviour.Properties.of(), TOOL_GROUP_KEY);

    /**
     * Fluids
     */
    public static final Block pitch = registerWithoutItem("pitch", ctx -> new LiquidBlock(ModFluids.PITCH, ctx), BlockBehaviour.Properties.of().mapColor(MapColor.WATER).replaceable().noCollision().strength(100.0F).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY));

    /**
     * Sedimentary Stone groups
     */

    //Limestone -> White
    public static final StoneGroup limestone_white = new StoneGroup("limestone_white", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_beige = new StoneGroup("limestone_beige", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_grey = new StoneGroup("limestone_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, true);
    public static final StoneGroup limestone_dark_grey = new StoneGroup("limestone_dark_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, true);

    public static final StoneGroup sandstone_white = new StoneGroup("sandstone_white", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_grey = new StoneGroup("sandstone_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_black = new StoneGroup("sandstone_black", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_beige = new StoneGroup("sandstone_beige", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_brown = new StoneGroup("sandstone_brown", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_pink = new StoneGroup("sandstone_pink", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_red = new StoneGroup("sandstone_red", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup sandstone_yellow = new StoneGroup("sandstone_yellow", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);

    public static final StoneGroup shale_black = new StoneGroup("shale_black", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_dark_grey = new StoneGroup("shale_dark_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_grey = new StoneGroup("shale_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, false);
    public static final StoneGroup shale_red = new StoneGroup("shale_red", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, false);

    public static final StoneGroup travertine_white = new StoneGroup("travertine_white", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup travertine_grey = new StoneGroup("travertine_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup travertine_beige = new StoneGroup("travertine_beige", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);

    /**
     * Metamorphic Stone groups
     */

    //Slate -> black
    public static final StoneGroup slate_black = new StoneGroup("slate_black", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_grey = new StoneGroup("slate_grey", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_blue = new StoneGroup("slate_blue", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_purple = new StoneGroup("slate_purple", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_red = new StoneGroup("slate_red", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);
    public static final StoneGroup slate_green = new StoneGroup("slate_green", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), true, false);

    //Marble -> White
    public static final StoneGroup marble_white = new StoneGroup("marble_white", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_beige = new StoneGroup("marble_beige", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_black = new StoneGroup("marble_black", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_portoro = new StoneGroup("marble_portoro", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_green = new StoneGroup("marble_green", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup marble_blue = new StoneGroup("marble_blue", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);

    /**
     * Igneous Stone groups
     */
    //Basalt -> Black
    public static final StoneGroup basalt_black = new StoneGroup("basalt_black", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);

    //Granite -> White
    public static final StoneGroup granite_white = new StoneGroup("granite_white", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup granite_pink = new StoneGroup("granite_pink", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);
    public static final StoneGroup granite_red = new StoneGroup("granite_red", BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1.5F, 6.0F), false, true);

    /**
     * Wood groups
     */
    //Fruit trees
    public static final WoodGroup apple = new WoodGroup("apple", 2f, false, true, true,true);
    public static final Block apple_source = register("apple_source", ctx -> new TreeSource(ctx, Trees.apple.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup pear  = new WoodGroup("pear", 2f, false, true, true, true);
    public static final Block pear_source = register("pear_source", ctx -> new TreeSource(ctx, Trees.pear.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup plum  = new WoodGroup("plum", 2f, false, true, true, true);
    public static final Block plum_source = register("plum_source", ctx -> new TreeSource(ctx, Trees.plum.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Magic trees
    public static final WoodGroup ancient_oak     = new WoodGroup("ancient_oak", 12f, true, true, true, false);
    public static final Block ancient_oak_source = register("ancient_oak_source", ctx -> new TreeSource(ctx, Trees.ancient_oak.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup jacaranda       = new WoodGroup("jacaranda", 8f, false, true, true, false);
    public static final Block jacaranda_source = register("jacaranda_source", ctx -> new TreeSource(ctx, Trees.jacaranda.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup glowing_willow  = new WoodGroup("glowing_willow", 6f, false, true, false, true);
    public static final Block glowing_willow_source = register("glowing_willow_source", ctx -> new TreeSource(ctx, Trees.glowing_willow.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Pines
    public static final WoodGroup larch = new WoodGroup("larch", 2f, true, true, true, false);
    public static final Block larch_source = register("larch_source", ctx -> new TreeSource(ctx, Trees.spruce.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup spruce = new WoodGroup("spruce", 2f, true, true, true, false);
    public static final Block spruce_source = register("spruce_source", ctx -> new TreeSource(ctx, Trees.larch.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_WOOD), WOOD_GROUP_KEY);

    //Soft woods
    public static final WoodGroup birch = new WoodGroup("birch", 2f, false, true, false, true);
    public static final Block dwarf_birch_source = register("dwarf_birch_source", ctx -> new TreeSource(ctx, Trees.dwarf_birch.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block silver_birch_source = register("silver_birch_source", ctx -> new TreeSource(ctx, Trees.silver_birch.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block tall_birch_source = register("tall_birch_source", ctx -> new TreeSource(ctx, Trees.tall_birch.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup willow = new WoodGroup("willow", 2f, false, true, false, true);
    public static final Block weeping_willow_source = register("weeping_willow_source", ctx -> new TreeSource(ctx, Trees.weeping_willow.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);
    public static final Block bebb_willow_source = register("bebb_willow_source", ctx -> new TreeSource(ctx, Trees.bebb_willow.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_WOOD), WOOD_GROUP_KEY);

    //Hard woods
    public static final WoodGroup oak = new WoodGroup("oak", 6f, true, true, true, false);
    public static final Block oak_source = register("oak_source", ctx -> new TreeSource(ctx, Trees.oak.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final Block dense_oak_source = register("dense_oak_source", ctx -> new TreeSource(ctx, Trees.dense_oak.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final Block large_oak_source = register("large_oak_source", ctx -> new TreeSource(ctx, Trees.large_oak.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup beech = new WoodGroup("beech", 6f, false, true, true, true);
    public static final Block beech_source = register("beech_source", ctx -> new TreeSource(ctx, Trees.beech.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);
    public static final WoodGroup ash = new WoodGroup("ash", 6f, true, true, true, true);
    public static final Block ash_source = register("ash_source", ctx -> new TreeSource(ctx, Trees.ash.getKey()), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD), WOOD_GROUP_KEY);

    /// Register Tree sources
    static {
        ApiEntities.treeEntity.addSupportedBlock(apple_source);
        ApiEntities.treeEntity.addSupportedBlock(pear_source);
        ApiEntities.treeEntity.addSupportedBlock(plum_source);
        ApiEntities.treeEntity.addSupportedBlock(ancient_oak_source);
        ApiEntities.treeEntity.addSupportedBlock(jacaranda_source);
        ApiEntities.treeEntity.addSupportedBlock(glowing_willow_source);
        ApiEntities.treeEntity.addSupportedBlock(larch_source);
        ApiEntities.treeEntity.addSupportedBlock(spruce_source);
        ApiEntities.treeEntity.addSupportedBlock(dwarf_birch_source);
        ApiEntities.treeEntity.addSupportedBlock(silver_birch_source);
        ApiEntities.treeEntity.addSupportedBlock(tall_birch_source);
        ApiEntities.treeEntity.addSupportedBlock(weeping_willow_source);
        ApiEntities.treeEntity.addSupportedBlock(bebb_willow_source);
        ApiEntities.treeEntity.addSupportedBlock(oak_source);
        ApiEntities.treeEntity.addSupportedBlock(dense_oak_source);
        ApiEntities.treeEntity.addSupportedBlock(large_oak_source);
        ApiEntities.treeEntity.addSupportedBlock(beech_source);
        ApiEntities.treeEntity.addSupportedBlock(ash_source);
    }

    //MISC
    public static final Block thatch_roof = register("thatch_roof", Roof::new         , BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)   , DECORATION_GROUP_KEY);
    public static final Block torch = registerWithoutItem("torch", TorchBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH));

    //FLORA
    private static Block registerFlora(String id, Function<BlockBehaviour.Properties, Block> factory) {
        return register(id, factory, BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS).randomTicks().sound(SoundType.CHERRY_SAPLING), FLORA_GROUP_KEY);
    }

    //Shrubs
    public static final Block heather_yellow = registerFlora("heather_yellow", ctx -> new ShrubFlora(ctx, Color.decode("#ffdf00").getRGB(), 0.5f, 2, 8));
    public static final Block heather_purple = registerFlora("heather_purple", ctx -> new ShrubFlora(ctx, Color.decode("#800080").getRGB(), 0.5f, 2, 8));
    public static final Block heather_lilac  = registerFlora("heather_lilac" , ctx -> new ShrubFlora(ctx, Color.decode("#c8a2c8").getRGB(), 0.5f, 2, 8));
    public static final Block heather_mauve  = registerFlora("heather_mauve" , ctx -> new ShrubFlora(ctx, Color.decode("#e0b0ff").getRGB(), 0.5f, 2, 8));
    public static final Block heather_pink   = registerFlora("heather_pink"  , ctx -> new ShrubFlora(ctx, Color.decode("#ee82ee").getRGB(), 0.5f, 2, 8));
    public static final Block heather_red    = registerFlora("heather_red"   , ctx -> new ShrubFlora(ctx, Color.decode("#e62020").getRGB(), 0.5f, 2, 8));
    public static final Block heather_silver = registerFlora("heather_silver", ctx -> new ShrubFlora(ctx, Color.decode("#e6e6fa").getRGB(), 0.5f, 2, 8));
    public static final Block heather_white  = registerFlora("heather_white" , ctx -> new ShrubFlora(ctx, Color.decode("#faf0e6").getRGB(), 0.5f, 2, 8));

    /**
     * Tall shrubs
     */
    //sage bush
    public static final Block sagebush_lilac  = registerFlora("sagebush_lilac", ctx -> new TallShrubFlora(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.lilac, 0.7f, 1, 3));
    public static final Block sagebush_purple = registerFlora("sagebush_purple", ctx -> new TallShrubFlora(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.richLilac, 0.7f, 1, 3));
    public static final Block sagebush_pink   = registerFlora("sagebush_pink", ctx -> new TallShrubFlora(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.pinkLavender, 0.7f, 1, 3));
    public static final Block sagebush_white   = registerFlora("sagebush_white", ctx -> new TallShrubFlora(ctx, Colors.fern, Colors.fernGreen, Colors.sapGreen, Colors.lavender, 0.7f, 1, 3));

    //Hydrangea
    public static final Block hydrangea_white      = registerFlora("hydrangea_white"     , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#fffaf0").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_white_mist = registerFlora("hydrangea_white_mist", ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#e6e6fa").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_lime       = registerFlora("hydrangea_lime"      , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#d0f0c0").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_red        = registerFlora("hydrangea_red"       , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#c23b22").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_orange     = registerFlora("hydrangea_orange"    , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#ffb347").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_pink       = registerFlora("hydrangea_pink"      , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#fbaed2").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_violet     = registerFlora("hydrangea_violet"    , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#cb99c9").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_purple     = registerFlora("hydrangea_purple"    , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#bf94e4").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_blue       = registerFlora("hydrangea_blue"      , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#324ab2").getRGB(), 0.3f, 8, 16));
    public static final Block hydrangea_blue_mist  = registerFlora("hydrangea_blue_mist" , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.darkPastelGreen, Color.decode("#ccccff").getRGB(), 0.3f, 8, 16));

    //Spirea
    public static final Block spirea_green_white  = registerFlora("spirea_green_white" , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_red    = registerFlora("spirea_green_red"   , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_mauve  = registerFlora("spirea_green_mauve" , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_green_pink   = registerFlora("spirea_green_pink"  , ctx -> new TallShrubFlora(ctx, Colors.fernGreen, Colors.fern, Colors.linen, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_white = registerFlora("spirea_golden_white", ctx -> new TallShrubFlora(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_red   = registerFlora("spirea_golden_red"  , ctx -> new TallShrubFlora(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_mauve = registerFlora("spirea_golden_mauve", ctx -> new TallShrubFlora(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_golden_pink  = registerFlora("spirea_golden_pink" , ctx -> new TallShrubFlora(ctx, Colors.pastelYellow, Colors.darkYellow, Colors.lightYellow, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_white   = registerFlora("spirea_blue_white"  , ctx -> new TallShrubFlora(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#faf0e6").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_red     = registerFlora("spirea_blue_red"    , ctx -> new TallShrubFlora(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#e62020").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_mauve   = registerFlora("spirea_blue_mauve"  , ctx -> new TallShrubFlora(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#e0b0ff").getRGB(), 0.3f, 5, 8));
    public static final Block spirea_blue_pink    = registerFlora("spirea_blue_pink"   , ctx -> new TallShrubFlora(ctx, Colors.pastelBlue, Colors.darkPastelBlue, Colors.lightBlue, Color.decode("#ee82ee").getRGB(), 0.3f, 5, 8));

    //flowers
    public static final Block daisy            = registerFlora("daisy",     ctx -> new FlowerFlora(ctx, Colors.fernGreen, 0.5f, 1, 8));
    public static final Block poppy            = registerFlora("poppy",     ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    public static final Block dandelion        = registerFlora("dandelion", ctx -> new FlowerFlora(ctx, Colors.pastelGreen, 0.5f, 1, 8));
    public static final Block cornflower       = registerFlora("cornflower",ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    public static final Block marigold         = registerFlora("marigold",  ctx -> new FlowerFlora(ctx, Colors.darkPastelGreen, 0.5f, 1, 8));
    
    //todo TINTED INSTANCES BELOW!!!
    
    //artiplexes
    public static final Block artiplex_red      = registerFlora("artiplex_red"    , (ctx) -> new FlowerFlora(ctx, Colors.pastelRed, Colors.darkPastelRed,0.7f, 5, 16));
    public static final Block artiplex_green    = registerFlora("artiplex_green"  , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.pastelGreen,0.7f, 5, 16));
    public static final Block artiplex_silver   = registerFlora("artiplex_silver", (ctx) -> new FlowerFlora(ctx, Colors.fern,  Colors.ivory,0.7f, 5, 16));
    //orchids
    public static final Block orchid_white       = registerFlora("orchid_white"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.floralWhite,0.35f, 7, 5));
    public static final Block orchid_yellow      = registerFlora("orchid_yellow"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.pastelYellow,0.35f, 7, 5));
    public static final Block orchid_red         = registerFlora("orchid_red"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.pastelRed,0.35f, 7, 5));
    public static final Block orchid_orange      = registerFlora("orchid_orange"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.pastelOrange,0.35f, 7, 5));
    public static final Block orchid_blue        = registerFlora("orchid_blue"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.darkPastelBlue,0.35f, 7, 5));
    public static final Block orchid_purple      = registerFlora("orchid_purple"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelGreen, Colors.pastelPurple,0.35f, 7, 5));
    //Campanulas
    public static final Block campanula_white    = registerFlora("campanula_white"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.floralWhite,0.2f, 4, 5));
    public static final Block campanula_purple   = registerFlora("campanula_purple"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.richLilac,0.2f, 4, 5));
    public static final Block campanula_pink     = registerFlora("campanula_pink"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.vibrantPink,0.2f, 4, 5));
    public static final Block campanula_lilac    = registerFlora("campanula_lilac"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.lilac,0.2f, 4, 5));
    public static final Block campanula_blue     = registerFlora("campanula_blue"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.vibrantBlue,0.2f, 4, 5));
    //foxglove
    public static final Block foxglove_white    = registerFlora("foxglove_white"      , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.floralWhite,0.2f, 4, 5));
    public static final Block foxglove_pink     = registerFlora("foxglove_pink"      , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.lilac,0.2f, 4, 5));
    public static final Block foxglove_purple   = registerFlora("foxglove_purple"      , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.deepLavender,0.2f, 4, 5));
    public static final Block foxglove_yellow   = registerFlora("foxglove_yellow"      , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.vibrantYellow,0.2f, 4, 5));
    public static final Block foxglove_red      = registerFlora("foxglove_red"      , (ctx) -> new FlowerFlora(ctx, Colors.fernGreen, Colors.vibrantRed,0.2f, 4, 5));
    //Tulips
    public static final Block tulip_white        = registerFlora("tulip_white"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.floralWhite,0.2f, 4, 5));
    public static final Block tulip_red          = registerFlora("tulip_red"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantRed,0.2f, 4, 5));
    public static final Block tulip_orange       = registerFlora("tulip_orange"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantOrange,0.2f, 4, 5));
    public static final Block tulip_yellow       = registerFlora("tulip_yellow"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantYellow,0.2f, 4, 5));
    public static final Block tulip_pink         = registerFlora("tulip_pink"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantPink,0.2f, 4, 5));
    public static final Block tulip_purple       = registerFlora("tulip_purple"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantPurple,0.2f, 4, 5));
    //lily's
    public static final Block lily_white        = registerFlora("lily_white"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.floralWhite,0.5f, 3, 12));
    public static final Block lily_yellow       = registerFlora("lily_yellow"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.pastelYellow,0.5f, 3, 12));
    public static final Block lily_orange       = registerFlora("lily_orange"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.pastelOrange,0.5f, 3, 12));
    public static final Block lily_red          = registerFlora("lily_red"          , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.pastelRed,0.5f, 3, 12));
    public static final Block lily_purple       = registerFlora("lily_purple"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.pinkLavender,0.5f, 3, 12));
    public static final Block lily_pink         = registerFlora("lily_pink"      , (ctx) -> new FlowerFlora(ctx, Colors.fern, Colors.vibrantPink,0.5f, 3, 12));
    //sea holly
    public static final Block sea_holly_light = registerFlora("sea_holly_light"          , (ctx) -> new FlowerFlora(ctx, Colors.lightBlue, Colors.darkPastelPurple,0.8f, 2, 3));
    public static final Block sea_holly_silver = registerFlora("sea_holly_silver"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelBlue, Colors.pinkLavender,0.8f, 2, 3));
    public static final Block sea_holly_blue = registerFlora("sea_holly_blue"      , (ctx) -> new FlowerFlora(ctx, Colors.darkPastelBlue, Colors.blueLavender,0.8f, 2, 3));
    //bluebottle
    public static final Block blue_bottle_light  = registerFlora("blue_bottle_light"          , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.lightBlue,0.5f, 3, 12));
    public static final Block blue_bottle        = registerFlora("blue_bottle"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.darkPastelPurple,0.5f, 3, 12));
    public static final Block blue_bottle_dark   = registerFlora("blue_bottle_dark"      , (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.5f, 3, 12));

    //leek
    public static final Block leek = registerFlora("leek", (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.8f, 5, 5));
    //cabbage
    public static final Block oak_leaf_cabbage = registerFlora("oak_leaf_cabbage", (ctx) -> new FlowerFlora(ctx, Colors.pastelGreen, Colors.darkPastelBlue,0.1f, 1, 18));

    //todo END OF TINTED INSTANCES
    
    //grass
    public static final Block tall_curly_grass = registerFlora("tall_curly_grass", ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block tall_grass       = registerFlora("tall_grass"      , ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block tall_thin_grass  = registerFlora("tall_thin_grass" , ctx -> new TallGrass(ctx, Colors.fernGreen));
    public static final Block curly_grass      = registerFlora("curly_grass"     , ctx -> new GrassFlora(ctx, Colors.fernGreen, 0.7f, 1, 5));
    public static final Block grass            = registerFlora("grass"           , ctx -> new GrassFlora(ctx, Colors.fernGreen, 0.7f, 1, 5));
    public static final Block thin_grass       = registerFlora("thin_grass"      , ctx -> new GrassFlora(ctx, Colors.fernGreen, 0.7f, 1, 5));

    //rooted large
    public static final Block nettle            = registerFlora("nettle", Nettle::new);
    public static final Block chives_pink = registerFlora("chives_pink", ctx -> new Chives(ctx, Colors.pinkLavender));
    public static final Block chives_lilac = registerFlora("chives_lilac", ctx -> new Chives(ctx, Colors.lilac));
    public static final Block chives_purple = registerFlora("chives_purple", ctx -> new Chives(ctx, Colors.pastelPurple));

    public static final Block snow = register("snow", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);

    public static final Block gravel_beige = register("gravel_beige", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_black = register("gravel_black", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_blue = register("gravel_blue", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_brown = register("gravel_brown", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_green = register("gravel_green", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_grey = register("gravel_grey", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_pink = register("gravel_pink", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_purple = register("gravel_purple", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_red = register("gravel_red", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_white = register("gravel_white", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);
    public static final Block gravel_yellow = register("gravel_yellow", GravelBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL), SOIL_GROUP_KEY);

    public static final Block sand_white  = register("sand_white", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_black  = register("sand_black", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_beige  = register("sand_beige", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_brown  = register("sand_brown", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_grey   = register("sand_grey", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_pink   = register("sand_pink", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_red    = register("sand_red", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block sand_yellow = register("sand_yellow", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);

    //Loam
    public static final Block loam_red    = register("loam_red", SoilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_yellow = register("loam_yellow", SoilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_beige  = register("loam_beige", SoilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_brown  = register("loam_brown", SoilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block loam_black  = register("loam_black", SoilBlock::new, BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);

    //Peat
    public static final Block peat_brown = register("peat_brown", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);
    public static final Block peat_black = register("peat_black", SoilBlock::new ,BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT), SOIL_GROUP_KEY);

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

    //Candles
    public static final Block candle_white = register("candle_white", ctx-> new Candles(ctx, Colors.ivory), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_grey = register("candle_grey", ctx-> new Candles(ctx, Colors.grey), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_dark_grey = register("candle_dark_grey", ctx-> new Candles(ctx, Colors.darkGrey), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_beige = register("candle_beige", ctx-> new Candles(ctx, Colors.linen), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_brown = register("candle_brown", ctx-> new Candles(ctx, Colors.darkOliveGreen), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_black = register("candle_black", ctx-> new Candles(ctx, Colors.black), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_yellow = register("candle_yellow", ctx-> new Candles(ctx, Colors.darkYellow), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_orange = register("candle_orange", ctx-> new Candles(ctx, Colors.darkPastelOrange), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_red = register("candle_red", ctx-> new Candles(ctx, Colors.darkPastelRed), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);
    public static final Block candle_blue = register("candle_blue", ctx-> new Candles(ctx, Colors.darkPastelBlue), BlockBehaviour.Properties.ofFullCopy(Blocks.CANDLE), DECORATION_GROUP_KEY);

    //Glass
    public static final Block frosted_glass_white = register("frosted_glass_white", ctx-> new ColoredGlassPane(ctx, Colors.ivory), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);
    public static final Block frosted_glass_grey = register("frosted_glass_grey", ctx-> new ColoredGlassPane(ctx, Colors.grey), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);
    public static final Block frosted_glass_beige = register("frosted_glass_beige", ctx-> new ColoredGlassPane(ctx, Colors.linen), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);
    public static final Block frosted_glass_clear = register("frosted_glass_clear", ctx-> new ColoredGlassPane(ctx, pack(125,125,125, 80)), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);

    public static final Block glass_pane = register("glass_pane", ctx -> new ColoredGlassPane(ctx, pack(125,125,125, 60)), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);
    public static final Block glass_pane_tinted = register("glass_pane_tinted", ctx -> new ColoredGlassPane(ctx, pack(0,0,0, 60)), BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS).noOcclusion(), DECORATION_GROUP_KEY);
    public static final Block glass_window = register("glass_window", GlassPane::new, BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS), DECORATION_GROUP_KEY);

    //Ores
    public static final Block coal          = register("coal", Layered::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block lignite       = register("lignite", Layered::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block anthracite    = register("anthracite", Layered::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);
    public static final Block banded_iron   = register("banded_iron", Layered::new, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK), METALS_GROUP_KEY);

    public static Block register(String path, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modid, path));
        final Block block = Blocks.register(registryKey, factory, settings);
        Items.registerBlock(block);
        return block;
    }

    public static Block register(String path, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, ResourceKey<CreativeModeTab> key) {
        Block block = register(path, factory, settings);
        ItemGroupEvents.modifyEntriesEvent(key).register(itemGroup -> {
            itemGroup.accept(block);
        });
        return block;
    }

    public static Block registerWithoutItem(String path, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        final ResourceKey<Block> registryKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(modid, path));
        return Blocks.register(registryKey, factory, settings);
    }

    public static void initialize() {
        WildsRegrown.LOGGER.info("Registering blocks");
    }

}


