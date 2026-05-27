package com.wildsregrown.data.blockstates.libraries;

import com.sipke.math.MathUtil;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.StairsShape;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;

public class WoodInterior {

    private final static String modelPath = "interior/";

    public static void counter(BlockModelGenerators generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/interior/counter", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_inner", "block/interior/counter_inner", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_outer", "block/interior/counter_outer", log_path + name + "_wood", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner", "block/interior/counter_inner", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer", "block/interior/counter_outer", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner", "block/interior/counter_inner", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer", "block/interior/counter_outer", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner", "block/interior/counter_inner", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer", "block/interior/counter_outer", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
        }

        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C3<MultiVariant,LinSeedPaintable, Direction, StairsShape> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.STAIRS_SHAPE);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                for (StairsShape shape : StairsShape.values()) {
                    String part = "";
                    int rotateY = dir.get2DDataValue()*90;
                    switch (shape) {
                        case STRAIGHT -> part = "";
                        case INNER_RIGHT -> part = "_inner";
                        case INNER_LEFT -> {part = "_inner"; rotateY-=90;}
                        case OUTER_RIGHT -> part = "_outer";
                        case OUTER_LEFT -> {part = "_outer"; rotateY-=90;}
                    }
                    rotateY = (int) MathUtil.clampAngle(rotateY);
                    map.select(paint, dir, shape, modelOf(finalLoc + part, false, rotateY, 0));
                }
            }
        }
        CreateVariants(generator, block, map);

    }

    public static void counterShelves(BlockModelGenerators generator, Block block, String id, String name, String parent) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/interior/" + parent, plank_path + name + "_planks", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/interior/" + parent, plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/interior/" + parent, plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/interior/" + parent, plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
        }

        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C2<MultiVariant,LinSeedPaintable, Direction> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()){
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                map.select(paint, dir, modelOf(finalLoc, false, dir.get2DDataValue()*90, 0));
            }
        }

        CreateVariants(generator, block, map);

    }

    public static void counterChest(BlockModelGenerators generator, Block block, String id, String name, boolean item, String... parents) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_closed", "block/interior/" + parents[0], plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0+ "_open", "block/interior/" + parents[1], plank_path + name + "_planks", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_closed", "block/interior/" + parents[0], plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open", "block/interior/" + parents[1], plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_closed", "block/interior/" + parents[0], plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open", "block/interior/" + parents[1], plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_closed", "block/interior/" + parents[0], plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open", "block/interior/" + parents[1], plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
        }

        if (item) {
            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + loc0 + "_closed"));
        }

        PropertyDispatch.C3<MultiVariant,LinSeedPaintable, Direction, Boolean> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                map.select(paint, dir, true, modelOf(finalLoc + "_open", false, dir.get2DDataValue()*90, 0))
                        .select(paint, dir, false, modelOf(finalLoc + "_closed", false, dir.get2DDataValue()*90, 0));
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void storageTable(BlockModelGenerators generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_single", "block/interior/storage_table_single",log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_middle", "block/interior/storage_table_middle",log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_left", "block/interior/storage_table_left",   log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_right", "block/interior/storage_table_right", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_single", "block/interior/storage_table_single",log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle", "block/interior/storage_table_middle",log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_left", "block/interior/storage_table_left",   log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_right", "block/interior/storage_table_right", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_single", "block/interior/storage_table_single",log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle", "block/interior/storage_table_middle",log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_left", "block/interior/storage_table_left",   log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_right", "block/interior/storage_table_right", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_single", "block/interior/storage_table_single",log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle", "block/interior/storage_table_middle",log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_left", "block/interior/storage_table_left",   log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_right", "block/interior/storage_table_right", log_path + name + "_paintable_wood");
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0 + "_single"));

        PropertyDispatch.C3<MultiVariant,LinSeedPaintable, Direction, HorizontalConnected> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, WRGProperties.HORIZONTAL_CONNECTED);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            int[] rotate = {0, 0, 0, 180, 270, 90};
            for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                for (HorizontalConnected shape : HorizontalConnected.values()) {
                    String part = "";
                    switch (shape) {
                        case SINGLE -> part = "_single";
                        case MIDDLE -> part = "_middle";
                        case LEFT -> part = "_left";
                        case RIGHT -> part = "_right";
                    }

                    map.select(paint, dir, shape, modelOf(finalLoc + part, false, rotate[dir.get3DDataValue()], 0));
                }
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void shelves(BlockModelGenerators generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/interior/shelves", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/interior/shelves", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/interior/shelves", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/interior/shelves", log_path + name + "_paintable_wood");
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C2<MultiVariant,LinSeedPaintable, Direction>  map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map     .select(paint,Direction.NORTH, modelOf(finalLoc, false, 0, 0))
                    .select(paint,Direction.SOUTH, modelOf(finalLoc, false, 180, 0))
                    .select(paint,Direction.EAST,  modelOf(finalLoc, false, 90, 0))
                    .select(paint,Direction.WEST,  modelOf(finalLoc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

}
