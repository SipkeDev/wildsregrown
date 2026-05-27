package com.wildsregrown.data.blockstates.libraries;

import com.google.gson.JsonObject;
import com.mojang.math.Quadrant;
import com.wildsregrown.blocks.decoration.Candles;
import com.wildsregrown.blocks.properties.*;
import com.wildsregrown.blocks.properties.fuel.FueledLight;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.DelegatedModel;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;
import org.apache.commons.lang3.tuple.Triple;
import wildsregrown.api.block.properties.Orientation;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.CornerConnecting;
import wildsregrown.api.block.properties.connecting.VerticalConnected;
import wildsregrown.api.block.properties.shapes.HalfStair;

import java.util.stream.Stream;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class BlockStateLibrary {

    public static String root = "block/";

    public static void singleton(BlockModelGenerators generator, Block block, String id, String model) {
        applyTextureToModel(generator, id, model);
        generator.blockStateOutput.accept(createSimpleBlock(block, plainVariant(Identifier.fromNamespaceAndPath(modid, root+id))));
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id));
    }

    public static void stairs(BlockModelGenerators generator, String name, Block block, String texture) {
        BlockStateLibrary.applyTextureToModel(generator, "inner_" + name, "block/inner_stairs", texture);
        BlockStateLibrary.applyTextureToModel(generator, "outer_" + name, "block/outer_stairs", texture);
        BlockStateLibrary.applyTextureToModel(generator, "vanilla_" + name, "block/vanilla_stairs", texture);
        generator.blockStateOutput.accept(createStairs(block, plainVariant(Identifier.fromNamespaceAndPath(modid, root+"inner_" + name)), plainVariant(Identifier.fromNamespaceAndPath(modid, root+"vanilla_" + name)), plainVariant(Identifier.fromNamespaceAndPath(modid, root+"outer_" + name))));
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+"vanilla_" + name));
    }

    public static void halfStairs(BlockModelGenerators generator, String name, Block block, String texture) {

        String loc0 = "half_stairs/" + name;
        String inner = "_inner";
        String outer = "_outer";
        String straight = "_straight";
        String top = "_top";
        String bottom = "_bottom";
        
        applyTextureToModel(generator, loc0 + inner + top, "block/half_stairs_top_inner", texture);
        applyTextureToModel(generator, loc0 + outer + top, "block/half_stairs_top_outer", texture);
        applyTextureToModel(generator, loc0 + straight + top, "block/half_stairs_top", texture);
        applyTextureToModel(generator, loc0 + inner + bottom, "block/half_stairs_bottom_inner", texture);
        applyTextureToModel(generator, loc0 + outer + bottom, "block/half_stairs_bottom_outer", texture);
        applyTextureToModel(generator, loc0 + straight + bottom, "block/half_stairs_bottom", texture);
        
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0+straight+bottom));

        PropertyDispatch.C3<MultiVariant, Direction, HalfStair, StairsShape> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, WRGProperties.HALF_STAIR, BlockStateProperties.STAIRS_SHAPE);

        for(Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int dir = 90 + (direction.get2DDataValue() * 90);
            if (dir > 360){dir -= 360;}
            map.select(direction, HalfStair.TOP_INVERT, StairsShape.INNER_LEFT, modelOf(loc0+inner+top, true, dir, 180));
            map.select(direction, HalfStair.TOP_INVERT, StairsShape.INNER_RIGHT, modelOf(loc0+inner+top, true, dir + 90, 180));
            map.select(direction, HalfStair.TOP_INVERT, StairsShape.OUTER_LEFT, modelOf(loc0+outer+top, true, dir, 180));
            map.select(direction, HalfStair.TOP_INVERT, StairsShape.OUTER_RIGHT, modelOf(loc0+outer+top, true, dir + 90, 180));
            map.select(direction, HalfStair.TOP_INVERT, StairsShape.STRAIGHT, modelOf(loc0+straight+top, true, dir, 180));
            map.select(direction, HalfStair.BOTTOM_INVERT, StairsShape.INNER_LEFT, modelOf(loc0+inner+bottom, true, dir, 180));
            map.select(direction, HalfStair.BOTTOM_INVERT, StairsShape.INNER_RIGHT, modelOf(loc0+inner+bottom, true, dir + 90, 180));
            map.select(direction, HalfStair.BOTTOM_INVERT, StairsShape.OUTER_LEFT, modelOf(loc0+outer+bottom, true, dir, 180));
            map.select(direction, HalfStair.BOTTOM_INVERT, StairsShape.OUTER_RIGHT, modelOf(loc0+outer+bottom, true, dir + 90, 180));
            map.select(direction, HalfStair.BOTTOM_INVERT, StairsShape.STRAIGHT, modelOf(loc0+straight+bottom, true, dir, 180));

            map.select(direction, HalfStair.TOP, StairsShape.INNER_LEFT, modelOf(loc0+inner+top, true, dir - 90, 0));
            map.select(direction, HalfStair.TOP, StairsShape.INNER_RIGHT, modelOf(loc0+inner+top, true, dir, 0));
            map.select(direction, HalfStair.TOP, StairsShape.OUTER_LEFT, modelOf(loc0+outer+top, true, dir - 90, 0));
            map.select(direction, HalfStair.TOP, StairsShape.OUTER_RIGHT, modelOf(loc0+outer+top, true, dir, 0));
            map.select(direction, HalfStair.TOP, StairsShape.STRAIGHT, modelOf(loc0+straight+top, true, dir, 0));
            map.select(direction, HalfStair.BOTTOM, StairsShape.INNER_LEFT, modelOf(loc0+inner+bottom, true, dir - 90, 0));
            map.select(direction, HalfStair.BOTTOM, StairsShape.INNER_RIGHT, modelOf(loc0+inner+bottom, true, dir, 0));
            map.select(direction, HalfStair.BOTTOM, StairsShape.OUTER_LEFT, modelOf(loc0+outer+bottom, true, dir - 90, 0));
            map.select(direction, HalfStair.BOTTOM, StairsShape.OUTER_RIGHT, modelOf(loc0+outer+bottom, true, dir, 0));
            map.select(direction, HalfStair.BOTTOM, StairsShape.STRAIGHT, modelOf(loc0+straight+bottom, true, dir, 0));
        }
        CreateVariants(generator, block, map);
    }

    public static void quarterStairs(BlockModelGenerators generator, String name, Block block, String texture) {

        String loc = "quarter_stairs/" + name;
        String side = "_side";

        applyTextureToModel(generator, loc + "_0", "block/quarter_stairs_0", texture);
        applyTextureToModel(generator, loc + "_1", "block/quarter_stairs_1", texture);
        applyTextureToModel(generator, loc + "_2", "block/quarter_stairs_2", texture);
        applyTextureToModel(generator, loc + "_3", "block/quarter_stairs_3", texture);
        applyTextureToModel(generator, loc + "_0" + side, "block/quarter_stairs_0" + side, texture);
        applyTextureToModel(generator, loc + "_1" + side, "block/quarter_stairs_1" + side, texture);
        applyTextureToModel(generator, loc + "_2" + side, "block/quarter_stairs_2" + side, texture);
        applyTextureToModel(generator, loc + "_3" + side, "block/quarter_stairs_3" + side, texture);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc+"_0"));

        PropertyDispatch.C3<MultiVariant, Integer, Direction, Orientation> map = PropertyDispatch.initial(WRGProperties.QUARTER_LAYERS, BlockStateProperties.FACING, WRGProperties.ORIENTATION);

        for(Direction direction : BlockStateProperties.FACING.getPossibleValues()) {
            for (int k : WRGProperties.QUARTER_LAYERS.getPossibleValues()) {
                switch (direction){
                    case UP -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 0, 180));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1), true, 90, 180));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 180, 180));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1), true, 270, 180));
                    }
                    case DOWN -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 0, 0));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1), true, 90, 0));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 180, 0));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1), true, 270, 0));
                    }
                    case NORTH -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 0, 270));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 180, 90));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1) + side, true, 0, 270));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1) + side, true, 180, 90));
                    }
                    case SOUTH -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 0, 90));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 180, 270));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1) + side, true, 0, 90));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1) + side, true, 180, 270));
                    }
                    case EAST -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 90, 270));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 270, 90));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1) + side, true, 270, 90));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1) + side, true, 90, 270));
                    }
                    case WEST -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 270, 270));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 90, 90));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1) + side, true, 90,90));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1) + side, true, 270, 270));
                    }
                    default -> {
                        map.select(k, direction, Orientation.UP, modelOf(loc + "_" + (k - 1), true, 0, 90));
                        map.select(k, direction, Orientation.RIGHT, modelOf(loc + "_" + (k - 1), true, 0, 90));
                        map.select(k, direction, Orientation.DOWN, modelOf(loc + "_" + (k - 1), true, 0, 90));
                        map.select(k, direction, Orientation.LEFT, modelOf(loc + "_" + (k - 1), true, 0, 90));
                    }
                }
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void halfArch(BlockModelGenerators generator, String id, Block block, String texture) {
        BlockStateLibrary.applyTextureToModel(generator, "inner_" + id, "block/castle/half_arch_inner_corner", texture);
        BlockStateLibrary.applyTextureToModel(generator, "outer_" + id, "block/castle/half_arch_outer_corner", texture);
        BlockStateLibrary.applyTextureToModel(generator, "basic_" + id, "block/castle/half_arch", texture);
        generator.blockStateOutput.accept(createStairs(block, plainVariant(Identifier.fromNamespaceAndPath(modid, root+"inner_" + id)), plainVariant(Identifier.fromNamespaceAndPath(modid, root+"basic_" + id)), plainVariant(Identifier.fromNamespaceAndPath(modid, root+"outer_" + id))));
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+"basic_" + id));
    }

    public static void arch(BlockModelGenerators generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id, root+"castle/arch", texture);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id));
        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.HALF)
                        .select(Direction.Axis.X, Half.TOP   , modelOf(id, true , 90, 0))
                        .select(Direction.Axis.Z, Half.TOP   , modelOf(id, false, 0 , 0))
                        .select(Direction.Axis.X, Half.BOTTOM, modelOf(id, true , 90, 180))
                        .select(Direction.Axis.Z, Half.BOTTOM, modelOf(id, true , 0 , 180))
                        );

    }

    public static void pillarFacing(BlockModelGenerators generator, String id, Block block, String texture) {
            applyTextureToModel(generator, id + "_top","block/decoration/pillar_facing_top", texture);
            applyTextureToModel(generator, id + "_single","block/decoration/pillar_facing_single", texture);
            applyTextureToModel(generator, id + "_middle","block/decoration/pillar_facing_middle", texture);
            applyTextureToModel(generator, id + "_bottom","block/decoration/pillar_facing_bottom", texture);

            applyTextureToModel(generator, id + "_c_top","block/decoration/pillar_top", texture);
            applyTextureToModel(generator, id + "_c_single","block/decoration/pillar_single", texture);
            applyTextureToModel(generator, id + "_c_middle","block/decoration/pillar_middle", texture);
            applyTextureToModel(generator, id + "_c_bottom","block/decoration/pillar_bottom", texture);

            applyTextureToModel(generator, id + "_smooth_top","block/decoration/pillar_facing_smooth_top", texture);
            applyTextureToModel(generator, id + "_smooth_single","block/decoration/pillar_facing_smooth_single", texture);
            applyTextureToModel(generator, id + "_smooth_middle","block/decoration/pillar_facing_smooth_middle", texture);
            applyTextureToModel(generator, id + "_smooth_bottom","block/decoration/pillar_facing_smooth_bottom", texture);

            applyTextureToModel(generator, id + "_smooth_c_top","block/decoration/pillar_smooth_top", texture);
            applyTextureToModel(generator, id + "_smooth_c_single","block/decoration/pillar_smooth_single", texture);
            applyTextureToModel(generator, id + "_smooth_c_middle","block/decoration/pillar_smooth_middle", texture);
            applyTextureToModel(generator, id + "_smooth_c_bottom","block/decoration/pillar_smooth_bottom", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_c_single"));

        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.FACING_HOPPER, WRGProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_2)
                .select(Direction.DOWN , VerticalConnected.SINGLE, 1, modelOf(id + "_c_single"       , true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.MIDDLE, 1, modelOf(id + "_c_middle"       , true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.TOP   , 1, modelOf(id + "_c_top"          , true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.BOTTOM, 1, modelOf(id + "_c_bottom"       , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 0  , 0))
                .select(Direction.WEST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 90 , 0))
                .select(Direction.NORTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 180, 0))
                .select(Direction.EAST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 270, 0))

                .select(Direction.DOWN , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_c_single", true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_c_middle", true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_c_top"   , true, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_c_bottom", true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 0  , 0))
                .select(Direction.WEST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 90 , 0))
                .select(Direction.WEST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 90 , 0))
                .select(Direction.NORTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 180, 0))
                .select(Direction.NORTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 180, 0))
                .select(Direction.EAST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 270, 0))
                .select(Direction.EAST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 270, 0)))
        ;
    }

    public static void thinPillarFacing(BlockModelGenerators generator, String id, Block block, String texture) {
            applyTextureToModel(generator, id + "_top","block/decoration/thin_pillar_facing_top", texture);
            applyTextureToModel(generator, id + "_single","block/decoration/thin_pillar_facing_single", texture);
            applyTextureToModel(generator, id + "_middle","block/decoration/thin_pillar_facing_middle", texture);
            applyTextureToModel(generator, id + "_bottom","block/decoration/thin_pillar_facing_bottom", texture);

            applyTextureToModel(generator, id + "_c_top","block/decoration/thin_pillar_top", texture);
            applyTextureToModel(generator, id + "_c_single","block/decoration/thin_pillar_single", texture);
            applyTextureToModel(generator, id + "_c_middle","block/decoration/thin_pillar_middle", texture);
            applyTextureToModel(generator, id + "_c_bottom","block/decoration/thin_pillar_bottom", texture);

            applyTextureToModel(generator, id + "_smooth_top","block/decoration/thin_pillar_facing_smooth_top", texture);
            applyTextureToModel(generator, id + "_smooth_single","block/decoration/thin_pillar_facing_smooth_single", texture);
            applyTextureToModel(generator, id + "_smooth_middle","block/decoration/thin_pillar_facing_smooth_middle", texture);
            applyTextureToModel(generator, id + "_smooth_bottom","block/decoration/thin_pillar_facing_smooth_bottom", texture);

            applyTextureToModel(generator, id + "_smooth_c_top","block/decoration/thin_pillar_smooth_top", texture);
            applyTextureToModel(generator, id + "_smooth_c_single","block/decoration/thin_pillar_smooth_single", texture);
            applyTextureToModel(generator, id + "_smooth_c_middle","block/decoration/thin_pillar_smooth_middle", texture);
            applyTextureToModel(generator, id + "_smooth_c_bottom","block/decoration/thin_pillar_smooth_bottom", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_c_single"));

        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.FACING_HOPPER, WRGProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_2)
                .select(Direction.DOWN , VerticalConnected.SINGLE, 1, modelOf(id + "_c_single"       , false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.MIDDLE, 1, modelOf(id + "_c_middle"       , false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.TOP   , 1, modelOf(id + "_c_top"          , false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.BOTTOM, 1, modelOf(id + "_c_bottom"       , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 0  , 0))
                .select(Direction.WEST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 90 , 0))
                .select(Direction.NORTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 180, 0))
                .select(Direction.EAST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 270, 0))

                .select(Direction.DOWN , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_c_single", false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_c_middle", false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_c_top"   , false, 0  , 0))
                .select(Direction.DOWN , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_c_bottom", false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 0  , 0))
                .select(Direction.SOUTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 0  , 0))
                .select(Direction.WEST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 90 , 0))
                .select(Direction.WEST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 90 , 0))
                .select(Direction.NORTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.MIDDLE, 2, modelOf(id + "_smooth_middle"  , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 180, 0))
                .select(Direction.NORTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 180, 0))
                .select(Direction.EAST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 270, 0))
                .select(Direction.EAST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 270, 0))
        );
    }

    public static void machicolations(BlockModelGenerators G, String id, Block block, String texture) {
        applyTextureToModel(G, id + "_top","block/castle/machicolations_top", texture);
        applyTextureToModel(G, id + "_middle","block/castle/machicolations_middle", texture);
        applyTextureToModel(G, id + "_bottom","block/castle/machicolations_bottom", texture);
        applyTextureToModel(G, id + "_single","block/castle/machicolations_single", texture);

        G.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_single"));

        CreateVariants(G, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, WRGProperties.VERTICAL_CONNECTED)
                        .select(Direction.SOUTH, VerticalConnected.SINGLE, modelOf(id + "_single", false, 0  , 0))
                        .select(Direction.SOUTH, VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 0  , 0))
                        .select(Direction.SOUTH, VerticalConnected.TOP   , modelOf(id + "_top"   , false, 0  , 0))
                        .select(Direction.SOUTH, VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 0  , 0))
                        .select(Direction.WEST , VerticalConnected.SINGLE, modelOf(id + "_single", false, 90 , 0))
                        .select(Direction.WEST , VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 90 , 0))
                        .select(Direction.WEST , VerticalConnected.TOP   , modelOf(id + "_top"   , false, 90 , 0))
                        .select(Direction.WEST , VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 90 , 0))
                        .select(Direction.NORTH, VerticalConnected.SINGLE, modelOf(id + "_single", false, 180, 0))
                        .select(Direction.NORTH, VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 180, 0))
                        .select(Direction.NORTH, VerticalConnected.TOP   , modelOf(id + "_top"   , false, 180, 0))
                        .select(Direction.NORTH, VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 180, 0))
                        .select(Direction.EAST , VerticalConnected.SINGLE, modelOf(id + "_single", false, 270, 0))
                        .select(Direction.EAST , VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 270, 0))
                        .select(Direction.EAST , VerticalConnected.TOP   , modelOf(id + "_top"   , false, 270, 0))
                        .select(Direction.EAST , VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 270, 0))
        );
    }

    public static void wallSupport(BlockModelGenerators generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id,root+"castle/wall_support", texture);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id));
        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING)
                        .select(Direction.SOUTH, modelOf(id, false, 0  , 0))
                        .select(Direction.WEST , modelOf(id, false, 90 , 0))
                        .select(Direction.NORTH, modelOf(id, false, 180, 0))
                        .select(Direction.EAST , modelOf(id, false, 270, 0))
        );
    }

    public static void balustrade(BlockModelGenerators G, String id, Block block, String texture) {
        applyTextureToModel(G, id + "_straight","block/decoration/balustrade", texture);
        applyTextureToModel(G, id + "_inner","block/decoration/balustrade_inner_corner", texture);
        applyTextureToModel(G, id + "_outer","block/decoration/balustrade_outer_corner", texture);

        G.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_straight"));

        CreateVariants(G, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, WRGProperties.CORNER_CONNECTING)
                        .select(Direction.EAST , CornerConnecting.NONE        , modelOf(id + "_straight", true, 270, 0))
                        .select(Direction.EAST , CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 270, 0))
                        .select(Direction.EAST , CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 270, 0))
                        .select(Direction.NORTH, CornerConnecting.NONE        , modelOf(id + "_straight", true, 180, 0))
                        .select(Direction.NORTH, CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 180, 0))
                        .select(Direction.NORTH, CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 180, 0))
                        .select(Direction.SOUTH, CornerConnecting.NONE        , modelOf(id + "_straight", true, 0  , 0))
                        .select(Direction.SOUTH, CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 0  , 0))
                        .select(Direction.SOUTH, CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 0  , 0))
                        .select(Direction.WEST , CornerConnecting.NONE        , modelOf(id + "_straight", true, 90 , 0))
                        .select(Direction.WEST , CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 90 , 0))
                        .select(Direction.WEST , CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 90 , 0))
        );
    }

    public static void soil(BlockModelGenerators generator, String id, Block block){

        //no overgrown
        String texture = root + "soil/" + id;
        applyTextureToModel(generator, id + "_layer_1_0", "block/layered_2", texture);
        applyTextureToModel(generator, id + "_layer_2_0", "block/layered_4", texture);
        applyTextureToModel(generator, id + "_layer_3_0", "block/layered_6", texture);
        applyTextureToModel(generator, id + "_layer_4_0", "block/layered_8", texture);
        applyTextureToModel(generator, id + "_layer_5_0", "block/layered_10", texture);
        applyTextureToModel(generator, id + "_layer_6_0", "block/layered_12", texture);
        applyTextureToModel(generator, id + "_layer_7_0", "block/layered_14", texture);
        applyTextureToModel(generator, id + "_layer_8_0", "block/layered_full", texture);

        for (int k = 1; k <= WRGProperties.OVERGROWN.getPossibleValues().getLast(); k++) {
            String grass = root + "flora/overgrown_" + k;
            applyTextureToModel(generator, id + "_layer_1_" + k, "block/layered_2_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_2_" + k, "block/layered_4_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_3_" + k, "block/layered_6_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_4_" + k, "block/layered_8_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_5_" + k, "block/layered_10_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_6_" + k, "block/layered_12_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_7_" + k, "block/layered_14_overgrown", texture, grass);
            applyTextureToModel(generator, id + "_layer_8_" + k, "block/layered_full_overgrown", texture, grass);
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid,root + id + "_layer_8_0"));

        PropertyDispatch.C2<MultiVariant, Integer, Integer> map = PropertyDispatch.initial(WRGProperties.OVERGROWN, WRGProperties.LAYERS);

        for (int n : WRGProperties.LAYERS.getPossibleValues()) {
            map.select(0, n, modelOf(id + "_layer_" + n + "_0", true, 0, 0));
            for (int k = 1; k <= WRGProperties.OVERGROWN.getPossibleValues().getLast(); k++) {
                map.select(k, n, modelOf(id + "_layer_" + n + "_" + k, true, 0, 0));
            }
        }

        CreateVariants(generator, block, map);

    }

    public static void layered(BlockModelGenerators generator, String id, String path, Block block, boolean oxidation){
        if (oxidation){
            path = path + "_0";
        }
        applyTextureToModel(generator, id + "_1", "block/layered_2", path);
        applyTextureToModel(generator, id + "_2", "block/layered_4", path);
        applyTextureToModel(generator, id + "_3", "block/layered_6", path);
        applyTextureToModel(generator, id + "_4", "block/layered_8", path);
        applyTextureToModel(generator, id + "_5", "block/layered_10", path);
        applyTextureToModel(generator, id + "_6", "block/layered_12", path);
        applyTextureToModel(generator, id + "_7", "block/layered_14", path);
        applyTextureToModel(generator, id + "_8", "block/layered_full", path);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid,root + id + "_8"));

        PropertyDispatch.C2<MultiVariant, Direction, Integer> map = PropertyDispatch.initial(BlockStateProperties.FACING, WRGProperties.LAYERS);

        for (int n : WRGProperties.LAYERS.getPossibleValues()) {map
                .select(Direction.UP,n, modelOf(id + "_" + n, true, 0, 0))
                .select(Direction.DOWN,n, modelOf(id + "_" + n, true, 0, 180))
                .select(Direction.NORTH,n, modelOf(id + "_" + n, true, 0, 90))
                .select(Direction.SOUTH,n, modelOf(id + "_" + n, true, 180, 90))
                .select(Direction.EAST,n, modelOf(id + "_" + n, true, 90, 90))
                .select(Direction.WEST,n, modelOf(id + "_" + n, true, 270, 90));
        }

        CreateVariants(generator, block, map);
    }

    public static void gravel(BlockModelGenerators generator, Block block){

        String id = idFromBlock(block);

        String texture = "block/gravel/" + id;
        applyTextureToModel(generator, id + "_1", "block/layered_2", texture);
        applyTextureToModel(generator, id + "_2", "block/layered_4", texture);
        applyTextureToModel(generator, id + "_3", "block/layered_6", texture);
        applyTextureToModel(generator, id + "_4", "block/layered_8", texture);
        applyTextureToModel(generator, id + "_5", "block/layered_10", texture);
        applyTextureToModel(generator, id + "_6", "block/layered_12", texture);
        applyTextureToModel(generator, id + "_7", "block/layered_14", texture);
        applyTextureToModel(generator, id + "_8", "block/layered_full", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid,root + id + "_8"));

        PropertyDispatch.C1<MultiVariant, Integer> map = PropertyDispatch.initial(WRGProperties.LAYERS);

        for (int n : WRGProperties.LAYERS.getPossibleValues()) {map
                .select(n, modelOf(id + "_" + n, false, 0, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void battlements(BlockModelGenerators generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id,"block/castle/battlements", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root +  id));

        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.SOUTH, modelOf(id, false, 0  , 0))
                .select(Direction.WEST , modelOf(id, false, 90 , 0))
                .select(Direction.NORTH, modelOf(id, false, 180, 0))
                .select(Direction.EAST , modelOf(id, false, 270, 0))
        );
    }

    public static void peekingHole(BlockModelGenerators generator, String id, Block block, String texture) {

        for (int i = 0; i < 2; i++) {
            applyTextureToModel(generator, id + "_" + i, root + "castle/peeking_hole_" + i, texture);
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0"));

        PropertyDispatch.C2<MultiVariant, Direction.Axis, Integer> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS, ModProperties.VARIATIONS_2);

        for (int i = 0; i < 2; i++) {
            map
                    .select(Direction.Axis.X, i+1, modelOf(id + "_" + i, false, 90, 0))
                    .select(Direction.Axis.Z, i+1, modelOf(id + "_" + i, false, 0, 0));
        }
        CreateVariants(generator, block, map);
    }

    public static void roof(BlockModelGenerators generator, Block block, String id, String texture) {

        applyTextureToModel(generator, "misc/" + id, "block/framing/roof", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+"misc/" + id));

        PropertyDispatch.C1<MultiVariant, Direction> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING);

        Triple<Direction, Integer, Integer>[] directions = new Triple[]{
                Triple.of(Direction.SOUTH, 0, 0),
                Triple.of(Direction.WEST, 90, 0),
                Triple.of(Direction.NORTH, 180, 0),
                Triple.of(Direction.EAST, 270, 0)
        };

        Stream.of(directions).forEach(ctx -> {
                    map
                            .select(ctx.getLeft(), modelOf("misc/" + id, true, ctx.getMiddle(), ctx.getRight()));
                }
        );

        CreateVariants(generator, block, map);
    }

    public static void torch(BlockModelGenerators generator, Block block) {

        String id = idFromBlock(block);
        Identifier identifier = Identifier.fromNamespaceAndPath(modid, root + id);
        generator.registerSimpleItemModel(block, identifier);

        ModelInstance supplier = new DelegatedModel(Identifier.fromNamespaceAndPath(modid, root + "lights/torch"));

        generator.modelOutput.accept(identifier, supplier);
        generator.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, modelOf(id)));

    }

    public static void light(BlockModelGenerators generator, String id, Block block, String texture, String parent, String filled, String lit) {

        applyTextureToModel(generator, id + "_empty","block/" + parent, texture);
        applyTextureToModel(generator, id + "_filled","block/" + filled, texture);
        applyTextureToModel(generator, id + "_lit","block/" + lit, texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_empty"));
        CreateVariants(generator, block, PropertyDispatch.initial(ModProperties.FUELED_LIGHT)
                .select(FueledLight.EMPTY, modelOf(id + "_empty"))
                .select(FueledLight.FILLED, modelOf(id + "_filled"))
                .select(FueledLight.LIT, modelOf(id + "_lit"))
        );
    }

    public static void candles(BlockModelGenerators generator, String id, Block block) {
        for (int i : WRGProperties.LAYERS.getPossibleValues()){
            applyTextureToModel(generator, id + "_" + i,"block/decoration/misc/candle_" + i);
        }
        if (block instanceof Candles candles) {
            generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_1"), new Constant(candles.getRgb()));
        }
        PropertyDispatch.C1<MultiVariant, Integer> map = PropertyDispatch.initial(WRGProperties.LAYERS);
        for (int i : WRGProperties.LAYERS.getPossibleValues()){
            map.select(i, modelOf(id + "_" + i, false, 0, 0));
        }
        CreateVariants(generator, block, map);
    }

    public static void itemLootPedestal(BlockModelGenerators generator, String id, Block block, String texture) {

        applyTextureToModel(generator, id + "_1","block/dungeon/item_loot_pedestal_1", texture);
        applyTextureToModel(generator, id + "_2","block/dungeon/item_loot_pedestal_2", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_1"));

        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, ModProperties.VARIATIONS_2)
                .select(Direction.NORTH, 1, modelOf(id + "_1", false, 0, 0))
                .select(Direction.EAST, 1, modelOf(id + "_1", false, 90, 0))
                .select(Direction.SOUTH, 1, modelOf(id + "_1", false, 180, 0))
                .select(Direction.WEST, 1, modelOf(id + "_1", false, 270, 0))
                .select(Direction.NORTH, 2, modelOf(id + "_2", false, 0, 0))
                .select(Direction.EAST, 2, modelOf(id + "_2", false, 90, 0))
                .select(Direction.SOUTH, 2, modelOf(id + "_2", false, 180, 0))
                .select(Direction.WEST, 2, modelOf(id + "_2", false, 270, 0))
        );
    }

    public static void glassPane(BlockModelGenerators generator, String id, Block block, String texture) {

        for (int i = 0; i < 3; i++) {
            applyTextureToModel(generator, id + "_" + i, root + "decoration/glass_pane_" + i, texture);
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0"));

        PropertyDispatch.C2<MultiVariant, Direction.Axis, Integer> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS, ModProperties.VARIATIONS_3);

        for (int i = 0; i < 3; i++) {
            map.select(Direction.Axis.X, i+1, modelOf(id + "_" + i, false, 90,0));
            map.select(Direction.Axis.Z, i+1, modelOf(id + "_" + i, false, 0,0));
        }

        CreateVariants(generator, block, map);
    }

    public static void axis(BlockModelGenerators generator, String id, Block block, String texture, String parent) {
            applyTextureToModel(generator, id, root + parent, texture);
            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id));
            CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS)
                    .select(Direction.Axis.X, modelOf(id, false, 90,0))
                    .select(Direction.Axis.Z, modelOf(id, false, 0,0))
            );
    }

    public static void applyTextureToModel(BlockModelGenerators generator, String newJsonLoc, String parentLoc, String textureLoc) {
        generator.modelOutput.accept(Identifier.fromNamespaceAndPath(modid, root + newJsonLoc), () -> {
            JsonObject jsonObject = new JsonObject();
            JsonObject texture = new JsonObject();
            texture.addProperty("0",modid + ":" + textureLoc);
            texture.addProperty("particle",modid + ":" + textureLoc);
            jsonObject.addProperty("parent", modid + ":" + parentLoc);
            jsonObject.add("textures", texture);
            return jsonObject;
        });
    }

    public static void applyTextureToModel(BlockModelGenerators generator, String newJsonLoc, String parentLoc, String... textures) {
        generator.modelOutput.accept(Identifier.fromNamespaceAndPath(modid, root+newJsonLoc), () -> {
            JsonObject jsonObject = new JsonObject();
            JsonObject texture = new JsonObject();
            if (textures.length != 0) {
                texture.addProperty("particle", modid + ":" + textures[0]);
                for (int i = 0; i < textures.length; i++) {
                    if (textures[i] == null) {
                        continue;
                    }
                    texture.addProperty(String.valueOf(i), modid + ":" + textures[i]);
                }
                jsonObject.add("textures", texture);
            }
            jsonObject.addProperty("parent", modid + ":" + parentLoc);
            return jsonObject;
        });
    }

    public static void CreateSingleton(BlockModelGenerators generator, Block block, String variant) {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, modelOf(variant)));
    }

    public static void CreateVariants(BlockModelGenerators generator, Block block, PropertyDispatch map) {
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(map));
    }

    public static MultiVariant modelOf(String key) {
        return plainVariant(Identifier.fromNamespaceAndPath(modid, root + key));
    }

    public static MultiVariant modelOf(String key, int vars) {
        Variant[] list = new Variant[vars];
        for (int i = 0; i < vars; i++) {
            list[i] = plainModel(Identifier.fromNamespaceAndPath(modid, root+key+"_"+i));
        }
        return variants(list);
    }

    public static MultiVariant modelOf(String key, boolean UVlock, int rotateY, int rotateX) {
        MultiVariant variant = modelOf(key);
        if (UVlock) {variant = variant.with(UV_LOCK);}
        variant = switch (rotateY) {
            case 90 -> variant.with(Y_ROT_90);
            case 180-> variant.with(Y_ROT_180);
            case 270-> variant.with(Y_ROT_270);
            default -> variant;
        };
        variant = switch (rotateX) {
            case 90 -> variant.with(X_ROT_90);
            case 180-> variant.with(X_ROT_180);
            case 270-> variant.with(X_ROT_270);
            default -> variant;
        };
        return variant;
    }

    public static MultiVariant modelOf(String key, boolean UVlock, int rotateX, int rotateY, int rotateZ) {
        MultiVariant variant = modelOf(key);
        if (UVlock) {variant = variant.with(UV_LOCK);}
        variant = switch (rotateX) {
            case 90 -> variant.with(X_ROT_90);
            case 180-> variant.with(X_ROT_180);
            case 270-> variant.with(X_ROT_270);
            default -> variant;
        };
        variant = switch (rotateY) {
            case 90 -> variant.with(Y_ROT_90);
            case 180-> variant.with(Y_ROT_180);
            case 270-> variant.with(Y_ROT_270);
            default -> variant;
        };
        variant = switch (rotateZ) {
            case 90 -> variant.with(VariantMutator.Z_ROT.withValue(Quadrant.R90));
            case 180-> variant.with(VariantMutator.Z_ROT.withValue(Quadrant.R180));
            case 270-> variant.with(VariantMutator.Z_ROT.withValue(Quadrant.R270));
            default -> variant.with(VariantMutator.Z_ROT.withValue(Quadrant.R0));
        };
        return variant;
    }
}

