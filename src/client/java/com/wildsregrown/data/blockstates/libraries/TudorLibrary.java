package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.Tudor;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class TudorLibrary {

    private final static String modelPath = "framing/tudor/";

    public static void square(BlockStateModelGenerator generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.asString(), root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString(), root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString(), root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString(), root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
            }
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_" + "hollow"));

        BlockStateVariantMap.TripleProperty<WeightedVariant, Tudor, LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                map.register(tudor, paint, Direction.NORTH, modelOf(loc + "_" + tudor.asString(), false, 0, 0))
                        .register(tudor, paint, Direction.SOUTH, modelOf(loc + "_" + tudor.asString(), false, 180, 0))
                        .register(tudor, paint, Direction.EAST, modelOf(loc + "_" + tudor.asString(), false, 90, 0))
                        .register(tudor, paint, Direction.WEST, modelOf(loc + "_" + tudor.asString(), false, 270, 0));
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void horizontal(BlockStateModelGenerator generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle",log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + name + "_paintable_wood");
            }
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_hollow_single"));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, Tudor, LinSeedPaintable, HorizontalConnected, Direction> map = BlockStateVariantMap.models(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, ModProperties.HORIZONTAL_CONNECTED, Properties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                for (HorizontalConnected connected : ModProperties.HORIZONTAL_CONNECTED.getValues()) {
                    map
                            .register(tudor, paint, connected, Direction.NORTH, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 0, 0))
                            .register(tudor, paint, connected, Direction.SOUTH, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 180, 0))
                            .register(tudor, paint, connected, Direction.EAST, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 90, 0))
                            .register(tudor, paint, connected, Direction.WEST, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 270, 0));
                }
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void vertical(BlockStateModelGenerator generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.asString() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle",log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.asString() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + name + "_paintable_wood");
            }
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_hollow_single"));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, Tudor, LinSeedPaintable, VerticalConnected, Direction> map = BlockStateVariantMap.models(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, ModProperties.VERTICAL_CONNECTED, Properties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                for (VerticalConnected connected : ModProperties.VERTICAL_CONNECTED.getValues()) {
                    map
                            .register(tudor, paint, connected, Direction.NORTH, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 0, 0))
                            .register(tudor, paint, connected, Direction.SOUTH, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 180, 0))
                            .register(tudor, paint, connected, Direction.EAST, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 90, 0))
                            .register(tudor, paint, connected, Direction.WEST, modelOf(loc + "_" + tudor.asString() + "_" + connected.asString(), false, 270, 0));
                }
            }
        }

        CreateVariants(generator, block, map);
    }

}
