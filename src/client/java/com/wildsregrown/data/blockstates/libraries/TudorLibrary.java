package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.Tudor;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.properties.connecting.VerticalConnected;

import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class TudorLibrary {

    private final static String modelPath = "framing/tudor/";

    public static void square(BlockModelGenerators generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName(), root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName(), root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName(), root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName(), root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
            }
        }

        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0 + "_" + "hollow"));

        PropertyDispatch.C3<MultiVariant, Tudor, LinSeedPaintable, Direction> map = PropertyDispatch.initial(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                map.select(tudor, paint, Direction.NORTH, modelOf(loc + "_" + tudor.getSerializedName(), false, 0, 0))
                        .select(tudor, paint, Direction.SOUTH, modelOf(loc + "_" + tudor.getSerializedName(), false, 180, 0))
                        .select(tudor, paint, Direction.EAST, modelOf(loc + "_" + tudor.getSerializedName(), false, 90, 0))
                        .select(tudor, paint, Direction.WEST, modelOf(loc + "_" + tudor.getSerializedName(), false, 270, 0));
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void horizontal(BlockModelGenerators generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle",log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "hori_" + tudor.ordinal() + "_middle", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_left", root+modelPath + "hori_" + tudor.ordinal() + "_left", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_right", root+modelPath + "hori_" + tudor.ordinal() + "_right", log_path + name + "_paintable_wood");
            }
        }

        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0 + "_hollow_single"));

        PropertyDispatch.C4<MultiVariant, Tudor, LinSeedPaintable, HorizontalConnected, Direction> map = PropertyDispatch.initial(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, WRGProperties.HORIZONTAL_CONNECTED, BlockStateProperties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                for (HorizontalConnected connected : WRGProperties.HORIZONTAL_CONNECTED.getPossibleValues()) {
                    String key = loc + "_" + tudor.getSerializedName() + "_" + connected.getSerializedName();
                    map
                            .select(tudor, paint, connected, Direction.NORTH, modelOf(key, false, 0, 0))
                            .select(tudor, paint, connected, Direction.SOUTH, modelOf(key, false, 180, 0))
                            .select(tudor, paint, connected, Direction.EAST, modelOf(key, false, 90, 0))
                            .select(tudor, paint, connected, Direction.WEST, modelOf(key, false, 270, 0));
                }
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void vertical(BlockModelGenerators generator, Block block, String name) {

        String id = idFromBlock(block);
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + name + "_wood");
            applyTextureToModel(generator, loc0 + "_" + tudor.getSerializedName() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle",log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + "pine_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + "fruit_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + "fruit_paintable_wood");
            } else {
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_single", root+modelPath + "square_" + tudor.ordinal(), log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_middle", root+modelPath + "vert_" + tudor.ordinal() + "_middle", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_top", root+modelPath + "vert_" + tudor.ordinal() + "_top", log_path + name + "_paintable_wood");
                applyTextureToModel(generator, loc1 + "_" + tudor.getSerializedName() + "_bottom", root+modelPath + "vert_" + tudor.ordinal() + "_bottom", log_path + name + "_paintable_wood");
            }
        }

        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0 + "_hollow_single"));

        PropertyDispatch.C4<MultiVariant, Tudor, LinSeedPaintable, VerticalConnected, Direction> map = PropertyDispatch.initial(ModProperties.TUDOR, ModProperties.LINSEED_PAINT, WRGProperties.VERTICAL_CONNECTED, BlockStateProperties.HORIZONTAL_FACING);

        for (Tudor tudor : ModProperties.TUDOR.getPossibleValues()) {
            for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
                String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                for (VerticalConnected connected : WRGProperties.VERTICAL_CONNECTED.getPossibleValues()) {
                    map
                            .select(tudor, paint, connected, Direction.NORTH, modelOf(loc + "_" + tudor.getSerializedName() + "_" + connected.getSerializedName(), false, 0, 0))
                            .select(tudor, paint, connected, Direction.SOUTH, modelOf(loc + "_" + tudor.getSerializedName() + "_" + connected.getSerializedName(), false, 180, 0))
                            .select(tudor, paint, connected, Direction.EAST, modelOf(loc + "_" + tudor.getSerializedName() + "_" + connected.getSerializedName(), false, 90, 0))
                            .select(tudor, paint, connected, Direction.WEST, modelOf(loc + "_" + tudor.getSerializedName() + "_" + connected.getSerializedName(), false, 270, 0));
                }
            }
        }

        CreateVariants(generator, block, map);
    }

}
