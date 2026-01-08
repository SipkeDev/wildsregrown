package com.wildsregrown.data.blockstates.libraries;

import com.google.gson.JsonObject;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.*;
import com.wildsregrown.blocks.properties.connecting.CornerConnecting;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.ModelVariantOperator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.tuple.Triple;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class BlockStateLibrary {

    public static String root = "block/";

    public static void singleton(BlockStateModelGenerator generator, Block block, String id, String model) {
        applyTextureToModel(generator, id, model);
        generator.blockStateCollector.accept(createSingletonBlockState(block, createWeightedVariant(Identifier.of(modid, root+id))));
        generator.registerParentedItemModel(block, Identifier.of(modid, root+id));
    }

    public static void stairs(BlockStateModelGenerator generator, String name, Block block, String texture) {
        BlockStateLibrary.applyTextureToModel(generator, "inner_" + name, "block/inner_stairs", texture);
        BlockStateLibrary.applyTextureToModel(generator, "outer_" + name, "block/outer_stairs", texture);
        BlockStateLibrary.applyTextureToModel(generator, "vanilla_" + name, "block/vanilla_stairs", texture);
        generator.blockStateCollector.accept(createStairsBlockState(block, createWeightedVariant(Identifier.of(modid, root+"inner_" + name)), createWeightedVariant(Identifier.of(modid, root+"vanilla_" + name)), createWeightedVariant(Identifier.of(modid, root+"outer_" + name))));
        generator.registerParentedItemModel(block, Identifier.of(modid, root+"vanilla_" + name));
    }

    public static void halfArch(BlockStateModelGenerator generator, String id, Block block, String texture) {
        BlockStateLibrary.applyTextureToModel(generator, "inner_" + id, "block/castle/half_arch_inner_corner", texture);
        BlockStateLibrary.applyTextureToModel(generator, "outer_" + id, "block/castle/half_arch_outer_corner", texture);
        BlockStateLibrary.applyTextureToModel(generator, "basic_" + id, "block/castle/half_arch", texture);
        generator.blockStateCollector.accept(createStairsBlockState(block, createWeightedVariant(Identifier.of(modid, root+"inner_" + id)), createWeightedVariant(Identifier.of(modid, root+"basic_" + id)), createWeightedVariant(Identifier.of(modid, root+"outer_" + id))));
        generator.registerParentedItemModel(block, Identifier.of(modid, root+"basic_" + id));
    }
    
    public static void arch(BlockStateModelGenerator generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id, root+"castle/arch", texture);
        generator.registerParentedItemModel(block, Identifier.of(modid, root+id));
        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_AXIS, Properties.BLOCK_HALF)
                        .register(Direction.Axis.X, BlockHalf.TOP   , modelOf(id, true , 90, 0))
                        .register(Direction.Axis.Z, BlockHalf.TOP   , modelOf(id, false, 0 , 0))
                        .register(Direction.Axis.X, BlockHalf.BOTTOM, modelOf(id, true , 90, 180))
                        .register(Direction.Axis.Z, BlockHalf.BOTTOM, modelOf(id, true , 0 , 180))
                        );

    }

    public static void pillarFacing(BlockStateModelGenerator G, String id, Block block, String texture) {
            applyTextureToModel(G, id + "_top","block/decoration/pillar_facing_top", texture);
            applyTextureToModel(G, id + "_single","block/decoration/pillar_facing_single", texture);
            applyTextureToModel(G, id + "_middle","block/decoration/pillar_facing_middle", texture);
            applyTextureToModel(G, id + "_bottom","block/decoration/pillar_facing_bottom", texture);

            applyTextureToModel(G, id + "_c_top","block/decoration/pillar_top", texture);
            applyTextureToModel(G, id + "_c_single","block/decoration/pillar_single", texture);
            applyTextureToModel(G, id + "_c_middle","block/decoration/pillar_middle", texture);
            applyTextureToModel(G, id + "_c_bottom","block/decoration/pillar_bottom", texture);

            applyTextureToModel(G, id + "_smooth_top","block/decoration/pillar_facing_smooth_top", texture);
            applyTextureToModel(G, id + "_smooth_single","block/decoration/pillar_facing_smooth_single", texture);
            applyTextureToModel(G, id + "_smooth_middle","block/decoration/pillar_facing_smooth_middle", texture);
            applyTextureToModel(G, id + "_smooth_bottom","block/decoration/pillar_facing_smooth_bottom", texture);

            applyTextureToModel(G, id + "_smooth_c_top","block/decoration/pillar_smooth_top", texture);
            applyTextureToModel(G, id + "_smooth_c_single","block/decoration/pillar_smooth_single", texture);
            applyTextureToModel(G, id + "_smooth_c_middle","block/decoration/pillar_smooth_middle", texture);
            applyTextureToModel(G, id + "_smooth_c_bottom","block/decoration/pillar_smooth_bottom", texture);

        G.registerParentedItemModel(block, Identifier.of(modid, root+id + "_c_single"));

        CreateVariants(G, block, BlockStateVariantMap.models(Properties.HOPPER_FACING, ModProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_2)
                .register(Direction.DOWN , VerticalConnected.SINGLE, 1, modelOf(id + "_c_single"       , true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.MIDDLE, 1, modelOf(id + "_c_middle"       , true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.TOP   , 1, modelOf(id + "_c_top"          , true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.BOTTOM, 1, modelOf(id + "_c_bottom"       , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , true, 270, 0))

                .register(Direction.DOWN , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_c_single", true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_c_middle", true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_c_top"   , true, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_c_bottom", true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , true, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , true, 270, 0)))
        ;
    }

    public static void thinPillarFacing(BlockStateModelGenerator generator, String id, Block block, String texture) {
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

        generator.registerParentedItemModel(block, Identifier.of(modid, root+id + "_c_single"));

        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HOPPER_FACING, ModProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_2)
                .register(Direction.DOWN , VerticalConnected.SINGLE, 1, modelOf(id + "_c_single"       , false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.MIDDLE, 1, modelOf(id + "_c_middle"       , false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.TOP   , 1, modelOf(id + "_c_top"          , false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.BOTTOM, 1, modelOf(id + "_c_bottom"       , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 1, modelOf(id + "_single"         , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"         , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 1, modelOf(id + "_top"            , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 1, modelOf(id + "_bottom"         , false, 270, 0))

                .register(Direction.DOWN , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_c_single", false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_c_middle", false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_c_top"   , false, 0  , 0))
                .register(Direction.DOWN , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_c_bottom", false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 2, modelOf(id + "_smooth_middle"  , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 2 , modelOf(id + "_smooth_single"  , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 2 , modelOf(id + "_smooth_middle"  , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 2 , modelOf(id + "_smooth_top"     , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 2 , modelOf(id + "_smooth_bottom"  , false, 270, 0))
        );
    }

    public static void arrowSlit(BlockStateModelGenerator G, String id, Block block, String texture) {
        applyTextureToModel(G, id + "_top","block/castle/arrowslit_top", texture);
        applyTextureToModel(G, id + "_middle","block/castle/arrowslit_middle", texture);
        applyTextureToModel(G, id + "_bottom","block/castle/arrowslit_bottom", texture);
        applyTextureToModel(G, id + "_cross_top","block/castle/arrowslit_cross_top", texture);
        applyTextureToModel(G, id + "_cross_middle","block/castle/arrowslit_cross_middle", texture);
        applyTextureToModel(G, id + "_cross_bottom","block/castle/arrowslit_cross_bottom", texture);
        applyTextureToModel(G, id + "_cross_single","block/castle/arrowslit_cross_single", texture);

        G.registerParentedItemModel(block, Identifier.of(modid, root+id + "_middle"));

        CreateVariants(G, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_3)
            .register(Direction.SOUTH, VerticalConnected.SINGLE, 1, modelOf(id + "_middle"      , false, 0  , 0))
            .register(Direction.SOUTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"      , false, 0  , 0))
            .register(Direction.SOUTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"         , false, 0  , 0))
            .register(Direction.SOUTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_top"      , false, 180  , 180))
            .register(Direction.WEST , VerticalConnected.SINGLE, 1, modelOf(id + "_middle"      , false, 90 , 0))
            .register(Direction.WEST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"      , false, 90 , 0))
            .register(Direction.WEST , VerticalConnected.TOP   , 1, modelOf(id + "_top"         , false, 90 , 0))
            .register(Direction.WEST , VerticalConnected.BOTTOM, 1, modelOf(id + "_top"      , false, 270 , 180))
            .register(Direction.NORTH, VerticalConnected.SINGLE, 1, modelOf(id + "_middle"      , false, 180, 0))
            .register(Direction.NORTH, VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"      , false, 180, 0))
            .register(Direction.NORTH, VerticalConnected.TOP   , 1, modelOf(id + "_top"         , false, 180, 0))
            .register(Direction.NORTH, VerticalConnected.BOTTOM, 1, modelOf(id + "_top"      , false, 0, 180))
            .register(Direction.EAST , VerticalConnected.SINGLE, 1, modelOf(id + "_middle"      , false, 270, 0))
            .register(Direction.EAST , VerticalConnected.MIDDLE, 1, modelOf(id + "_middle"      , false, 270, 0))
            .register(Direction.EAST , VerticalConnected.TOP   , 1, modelOf(id + "_top"         , false, 270, 0))
            .register(Direction.EAST , VerticalConnected.BOTTOM, 1, modelOf(id + "_top"      , false, 90, 180))

                .register(Direction.SOUTH, VerticalConnected.SINGLE, 2, modelOf(id + "_middle"      , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 2, modelOf(id + "_middle"      , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 2, modelOf(id + "_top"         , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 2, modelOf(id + "_bottom"      , false, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 2, modelOf(id + "_middle"      , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 2, modelOf(id + "_middle"      , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 2, modelOf(id + "_top"         , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 2, modelOf(id + "_bottom"      , false, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 2, modelOf(id + "_middle"      , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 2, modelOf(id + "_middle"      , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 2, modelOf(id + "_top"         , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 2, modelOf(id + "_bottom"      , false, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 2, modelOf(id + "_middle"      , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 2, modelOf(id + "_middle"      , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 2, modelOf(id + "_top"         , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 2, modelOf(id + "_bottom"      , false, 270, 0))

                .register(Direction.SOUTH, VerticalConnected.SINGLE, 3, modelOf(id + "_cross_single"      , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.MIDDLE, 3, modelOf(id + "_cross_middle"      , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.TOP   , 3, modelOf(id + "_cross_top"         , false, 0  , 0))
                .register(Direction.SOUTH, VerticalConnected.BOTTOM, 3, modelOf(id + "_cross_bottom"      , false, 0  , 0))
                .register(Direction.WEST , VerticalConnected.SINGLE, 3, modelOf(id + "_cross_single"      , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.MIDDLE, 3, modelOf(id + "_cross_middle"      , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.TOP   , 3, modelOf(id + "_cross_top"         , false, 90 , 0))
                .register(Direction.WEST , VerticalConnected.BOTTOM, 3, modelOf(id + "_cross_bottom"      , false, 90 , 0))
                .register(Direction.NORTH, VerticalConnected.SINGLE, 3, modelOf(id + "_cross_single"      , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.MIDDLE, 3, modelOf(id + "_cross_middle"      , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.TOP   , 3, modelOf(id + "_cross_top"         , false, 180, 0))
                .register(Direction.NORTH, VerticalConnected.BOTTOM, 3, modelOf(id + "_cross_bottom"      , false, 180, 0))
                .register(Direction.EAST , VerticalConnected.SINGLE, 3, modelOf(id + "_cross_single"      , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.MIDDLE, 3, modelOf(id + "_cross_middle"      , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.TOP   , 3, modelOf(id + "_cross_top"         , false, 270, 0))
                .register(Direction.EAST , VerticalConnected.BOTTOM, 3, modelOf(id + "_cross_bottom"      , false, 270, 0))

        );
    }

    public static void machicolations(BlockStateModelGenerator G, String id, Block block, String texture) {
        applyTextureToModel(G, id + "_top","block/castle/machicolations_top", texture);
        applyTextureToModel(G, id + "_middle","block/castle/machicolations_middle", texture);
        applyTextureToModel(G, id + "_bottom","block/castle/machicolations_bottom", texture);
        applyTextureToModel(G, id + "_single","block/castle/machicolations_single", texture);

        G.registerParentedItemModel(block, Identifier.of(modid, root+id + "_single"));

        CreateVariants(G, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.VERTICAL_CONNECTED)
                        .register(Direction.SOUTH, VerticalConnected.SINGLE, modelOf(id + "_single", false, 0  , 0))
                        .register(Direction.SOUTH, VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 0  , 0))
                        .register(Direction.SOUTH, VerticalConnected.TOP   , modelOf(id + "_top"   , false, 0  , 0))
                        .register(Direction.SOUTH, VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 0  , 0))
                        .register(Direction.WEST , VerticalConnected.SINGLE, modelOf(id + "_single", false, 90 , 0))
                        .register(Direction.WEST , VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 90 , 0))
                        .register(Direction.WEST , VerticalConnected.TOP   , modelOf(id + "_top"   , false, 90 , 0))
                        .register(Direction.WEST , VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 90 , 0))
                        .register(Direction.NORTH, VerticalConnected.SINGLE, modelOf(id + "_single", false, 180, 0))
                        .register(Direction.NORTH, VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 180, 0))
                        .register(Direction.NORTH, VerticalConnected.TOP   , modelOf(id + "_top"   , false, 180, 0))
                        .register(Direction.NORTH, VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 180, 0))
                        .register(Direction.EAST , VerticalConnected.SINGLE, modelOf(id + "_single", false, 270, 0))
                        .register(Direction.EAST , VerticalConnected.MIDDLE, modelOf(id + "_middle", false, 270, 0))
                        .register(Direction.EAST , VerticalConnected.TOP   , modelOf(id + "_top"   , false, 270, 0))
                        .register(Direction.EAST , VerticalConnected.BOTTOM, modelOf(id + "_bottom", false, 270, 0))
        );
    }

    public static void wallSupport(BlockStateModelGenerator generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id,root+"castle/wall_support", texture);
        generator.registerParentedItemModel(block, Identifier.of(modid, root+id));
        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                        .register(Direction.SOUTH, modelOf(id, false, 0  , 0))
                        .register(Direction.WEST , modelOf(id, false, 90 , 0))
                        .register(Direction.NORTH, modelOf(id, false, 180, 0))
                        .register(Direction.EAST , modelOf(id, false, 270, 0))
        );
    }

    public static void balustrade(BlockStateModelGenerator G, String id, Block block, String texture) {
        applyTextureToModel(G, id + "_straight","block/decoration/balustrade", texture);
        applyTextureToModel(G, id + "_inner","block/decoration/balustrade_inner_corner", texture);
        applyTextureToModel(G, id + "_outer","block/decoration/balustrade_outer_corner", texture);

        G.registerParentedItemModel(block, Identifier.of(modid, root+id + "_straight"));

        CreateVariants(G, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.CORNER_CONNECTING)
                        .register(Direction.EAST , CornerConnecting.NONE        , modelOf(id + "_straight", true, 270, 0))
                        .register(Direction.EAST , CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 270, 0))
                        .register(Direction.EAST , CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 270, 0))
                        .register(Direction.NORTH, CornerConnecting.NONE        , modelOf(id + "_straight", true, 180, 0))
                        .register(Direction.NORTH, CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 180, 0))
                        .register(Direction.NORTH, CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 180, 0))
                        .register(Direction.SOUTH, CornerConnecting.NONE        , modelOf(id + "_straight", true, 0  , 0))
                        .register(Direction.SOUTH, CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 0  , 0))
                        .register(Direction.SOUTH, CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 0  , 0))
                        .register(Direction.WEST , CornerConnecting.NONE        , modelOf(id + "_straight", true, 90 , 0))
                        .register(Direction.WEST , CornerConnecting.RIGHT_CORNER, modelOf(id + "_outer"   , true, 90 , 0))
                        .register(Direction.WEST , CornerConnecting.RIGHT_NOOK  , modelOf(id + "_inner"   , true, 90 , 0))
        );
    }

    public static void soil(BlockStateModelGenerator generator, String id, Block block){

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

        for (int k = 1; k <= ModProperties.OVERGROWN.getValues().getLast(); k++) {
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

        generator.registerParentedItemModel(block, Identifier.of(modid,root + id + "_layer_8_0"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Integer, Integer> map = BlockStateVariantMap.models(ModProperties.OVERGROWN, ModProperties.LAYERS);

        for (int n : ModProperties.LAYERS.getValues()) {
            map.register(0, n, modelOf(id + "_layer_" + n + "_0", true, 0, 0));
            for (int k = 1; k <= ModProperties.OVERGROWN.getValues().getLast(); k++) {
                map.register(k, n, modelOf(id + "_layer_" + n + "_" + k, true, 0, 0));
            }
        }

        CreateVariants(generator, block, map);

    }

    public static void layered(BlockStateModelGenerator generator, String id, String path, Block block, boolean oxidation){
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

        generator.registerParentedItemModel(block, Identifier.of(modid,root + id + "_8"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, Integer> map = BlockStateVariantMap.models(Properties.FACING, ModProperties.LAYERS);

        for (int n : ModProperties.LAYERS.getValues()) {map
                .register(Direction.UP,n, modelOf(id + "_" + n, true, 0, 0))
                .register(Direction.DOWN,n, modelOf(id + "_" + n, true, 0, 180))
                .register(Direction.NORTH,n, modelOf(id + "_" + n, true, 0, 90))
                .register(Direction.SOUTH,n, modelOf(id + "_" + n, true, 180, 90))
                .register(Direction.EAST,n, modelOf(id + "_" + n, true, 90, 90))
                .register(Direction.WEST,n, modelOf(id + "_" + n, true, 270, 90));
        }

        CreateVariants(generator, block, map);
    }

    public static void gravel(BlockStateModelGenerator generator, Block block){

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

        generator.registerParentedItemModel(block, Identifier.of(modid,root + id + "_8"));

        BlockStateVariantMap.SingleProperty<WeightedVariant, Integer> map = BlockStateVariantMap.models(ModProperties.LAYERS);

        for (int n : ModProperties.LAYERS.getValues()) {map
                .register(n, modelOf(id + "_" + n, false, 0, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void battlements(BlockStateModelGenerator generator, String id, Block block, String texture) {
        applyTextureToModel(generator, id,"block/castle/battlements", texture);

        generator.registerParentedItemModel(block, Identifier.of(modid, root +  id));

        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                .register(Direction.SOUTH, modelOf(id, false, 0  , 0))
                .register(Direction.WEST , modelOf(id, false, 90 , 0))
                .register(Direction.NORTH, modelOf(id, false, 180, 0))
                .register(Direction.EAST , modelOf(id, false, 270, 0))
        );
    }

    public static void roof(BlockStateModelGenerator generator, Block block, String id, String texture) {

        applyTextureToModel(generator, "misc/" + id, "block/framing/roof", texture);

        generator.registerParentedItemModel(block, Identifier.of(modid, root+"misc/" + id));

        BlockStateVariantMap.SingleProperty<WeightedVariant, Direction> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING);

        Triple<Direction, Integer, Integer>[] directions = new Triple[]{
                Triple.of(Direction.SOUTH, 0, 0),
                Triple.of(Direction.WEST, 90, 0),
                Triple.of(Direction.NORTH, 180, 0),
                Triple.of(Direction.EAST, 270, 0)
        };

        Stream.of(directions).forEach(ctx -> {
                    map
                            .register(ctx.getLeft(), modelOf("misc/" + id, true, ctx.getMiddle(), ctx.getRight()));
                }
        );

        CreateVariants(generator, block, map);
    }

    public static void torch(BlockStateModelGenerator generator, Block block) {

        String id = idFromBlock(block);
        Identifier identifier = Identifier.of(modid, root + id);
        generator.registerParentedItemModel(block, identifier);

        ModelSupplier supplier = new SimpleModelSupplier(Identifier.of(modid, root + "lights/torch"));

        generator.modelCollector.accept(identifier, supplier);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block, modelOf(id)));

    }

    public static void light(BlockStateModelGenerator generator, String id, Block block, String texture, String parent, String filled, String lit) {

        applyTextureToModel(generator, id + "_empty","block/" + parent, texture);
        applyTextureToModel(generator, id + "_filled","block/" + filled, texture);
        applyTextureToModel(generator, id + "_lit","block/" + lit, texture);

        generator.registerParentedItemModel(block, Identifier.of(modid, root+id+"_empty"));
        CreateVariants(generator, block, BlockStateVariantMap.models(ModProperties.FUELED_LIGHT)
                .register(FueledLight.EMPTY, modelOf(id + "_empty"))
                .register(FueledLight.FILLED, modelOf(id + "_filled"))
                .register(FueledLight.LIT, modelOf(id + "_lit"))
        );
    }

    public static void itemLootPedestal(BlockStateModelGenerator generator, String id, Block block, String texture) {

        applyTextureToModel(generator, id + "_1","block/dungeon/item_loot_pedestal_1", texture);
        applyTextureToModel(generator, id + "_2","block/dungeon/item_loot_pedestal_2", texture);

        generator.registerParentedItemModel(block, Identifier.of(modid, root + id + "_1"));

        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.VARIATIONS_2)
                .register(Direction.NORTH, 1, modelOf(id + "_1", false, 0, 0))
                .register(Direction.EAST, 1, modelOf(id + "_1", false, 90, 0))
                .register(Direction.SOUTH, 1, modelOf(id + "_1", false, 180, 0))
                .register(Direction.WEST, 1, modelOf(id + "_1", false, 270, 0))
                .register(Direction.NORTH, 2, modelOf(id + "_2", false, 0, 0))
                .register(Direction.EAST, 2, modelOf(id + "_2", false, 90, 0))
                .register(Direction.SOUTH, 2, modelOf(id + "_2", false, 180, 0))
                .register(Direction.WEST, 2, modelOf(id + "_2", false, 270, 0))
        );
    }

    public static void axis(BlockStateModelGenerator generator, String id, Block block, String texture, String parent) {
            applyTextureToModel(generator, id, "block/" + parent, texture);
            generator.registerParentedItemModel(block, Identifier.of(modid, root+id));
            CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_AXIS)
                    .register(Direction.Axis.X, modelOf(id, false, 90,0))
                    .register(Direction.Axis.Z, modelOf(id, false, 0,0))
            );
    }

    public static void applyTextureToModel(BlockStateModelGenerator generator, String newJsonLoc, String parentLoc, String textureLoc) {
        generator.modelCollector.accept(Identifier.of(modid, root + newJsonLoc), () -> {
            JsonObject jsonObject = new JsonObject();
            JsonObject texture = new JsonObject();
            texture.addProperty("0",modid + ":" + textureLoc);
            texture.addProperty("particle",modid + ":" + textureLoc);
            jsonObject.addProperty("parent", modid + ":" + parentLoc);
            jsonObject.add("textures", texture);
            return jsonObject;
        });
    }

    public static void applyTextureToModel(BlockStateModelGenerator generator, String newJsonLoc, String parentLoc, String... textures) {
        generator.modelCollector.accept(Identifier.of(modid, root+newJsonLoc), () -> {
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

    public static void CreateVariants(BlockStateModelGenerator generator, Block block, BlockStateVariantMap map) {
        generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block).with(map));
    }

    public static WeightedVariant modelOf(String key) {
        return createWeightedVariant(Identifier.of(modid, root + key));
    }
    public static WeightedVariant modelOf(String key, int vars) {
        ModelVariant[] list = new ModelVariant[vars];
        for (int i = 0; i < vars; i++) {
            list[i] = createModelVariant(Identifier.of(modid, root+key+"_"+i));
        }
        return createWeightedVariant(list);
    }

    public static WeightedVariant modelOf(String key, boolean UVlock, int rotateY, int rotateX) {
        WeightedVariant variant = modelOf(key);
        if (UVlock) {variant = variant.apply(UV_LOCK);}
        variant = switch (rotateY) {
            case 90 -> variant.apply(ROTATE_Y_90);
            case 180-> variant.apply(ROTATE_Y_180);
            case 270-> variant.apply(ROTATE_Y_270);
            default -> variant;
        };
        variant = switch (rotateX) {
            case 90 -> variant.apply(ROTATE_X_90);
            case 180-> variant.apply(ROTATE_X_180);
            case 270-> variant.apply(ROTATE_X_270);
            default -> variant;
        };
        return variant;
    }

}

