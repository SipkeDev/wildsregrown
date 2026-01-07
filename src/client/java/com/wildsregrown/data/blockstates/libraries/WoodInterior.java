package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.DrawerState;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.enums.StairShape;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;

public class WoodInterior {

    private final static String modelPath = "block/interior/";

    public static void counter(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/interior/counter", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_inner_right", "block/interior/counter_inner_right", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_inner_left", "block/interior/counter_inner_left", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_outer_right", "block/interior/counter_outer_right", log_path + name + "_wood", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_outer_left", "block/interior/counter_outer_left", log_path + name + "_wood", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_right", "block/interior/counter_inner_right", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_left", "block/interior/counter_inner_left", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_right", "block/interior/counter_outer_right", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_left", "block/interior/counter_outer_left", log_path + "pine_paintable_wood", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_right", "block/interior/counter_inner_right", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_left", "block/interior/counter_inner_left", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_right", "block/interior/counter_outer_right", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_left", "block/interior/counter_outer_left", log_path + "fruit_paintable_wood", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/interior/counter", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_right", "block/interior/counter_inner_right", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_inner_left", "block/interior/counter_inner_left", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_right", "block/interior/counter_outer_right", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_outer_left", "block/interior/counter_outer_left", log_path + name + "_paintable_wood", log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.TripleProperty<WeightedVariant,LinSeedPaintable, Direction, StairShape> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, Properties.STAIR_SHAPE);

        int[] rotate = {0,0,0, 180, 270, 90};
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction dir : Properties.HORIZONTAL_FACING.getValues()) {
                for (StairShape shape : StairShape.values()) {
                    String part = "";
                    switch (shape) {
                        case STRAIGHT -> part = "";
                        case INNER_RIGHT -> part = "_inner_right";
                        case INNER_LEFT -> part = "_inner_left";
                        case OUTER_RIGHT -> part = "_outer_right";
                        case OUTER_LEFT -> part = "_outer_left";
                    }

                    map.register(paint, dir, shape, modelOf(finalLoc + part, false, rotate[dir.getHorizontalQuarterTurns()], 0));
                }
            }
        }
        CreateVariants(generator, block, map);

    }

    public static void counterShelves(BlockStateModelGenerator generator, Block block, String id, String name, String parent) {

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

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map     .register(paint, Direction.NORTH, modelOf(finalLoc, false, 0, 0))
                    .register(paint, Direction.SOUTH, modelOf(finalLoc, false, 180, 0))
                    .register(paint, Direction.EAST , modelOf(finalLoc, false, 90, 0))
                    .register(paint, Direction.WEST , modelOf(finalLoc, false, 270, 0));
        }

        CreateVariants(generator, block, map);

    }

    public static void counterChest(BlockStateModelGenerator generator, Block block, String id, String name, String... parents) {

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

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0 + "_closed"));

        BlockStateVariantMap.TripleProperty<WeightedVariant,LinSeedPaintable, Direction, Boolean> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, Properties.OPEN);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint,Direction.NORTH,true, modelOf(finalLoc + "_open", false, 0, 0))
                    .register(paint,Direction.SOUTH,true, modelOf(finalLoc + "_open", false, 180, 0))
                    .register(paint,Direction.EAST ,true, modelOf(finalLoc + "_open", false, 90, 0))
                    .register(paint,Direction.WEST ,true, modelOf(finalLoc + "_open", false, 270, 0))
                    .register(paint,Direction.NORTH,false, modelOf(finalLoc + "_closed", false, 0, 0))
                    .register(paint,Direction.SOUTH,false, modelOf(finalLoc + "_closed", false, 180, 0))
                    .register(paint,Direction.EAST ,false, modelOf(finalLoc + "_closed", false, 90, 0))
                    .register(paint,Direction.WEST ,false, modelOf(finalLoc + "_closed", false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void storageTable(BlockStateModelGenerator generator, Block block, String id, String name) {

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

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0 + "_single"));

        BlockStateVariantMap.TripleProperty<WeightedVariant,LinSeedPaintable, Direction, HorizontalConnected> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, ModProperties.HORIZONTAL_CONNECTED);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            int[] rotate = {0, 0, 0, 180, 270, 90};
            for (Direction dir : Properties.HORIZONTAL_FACING.getValues()) {
                for (HorizontalConnected shape : HorizontalConnected.values()) {
                    String part = "";
                    switch (shape) {
                        case SINGLE -> part = "_single";
                        case MIDDLE -> part = "_middle";
                        case LEFT -> part = "_left";
                        case RIGHT -> part = "_right";
                    }

                    map.register(paint, dir, shape, modelOf(finalLoc + part, false, rotate[dir.getIndex()], 0));
                }
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void drawer(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";


        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_closed",      "block/interior/" + "drawer_closed", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_open_top",    "block/interior/" + "drawer_open_top", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_open_bottom", "block/interior/" + "drawer_open_bottom", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_open",        "block/interior/" + "drawer_open", plank_path + name + "_planks", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_closed",      "block/interior/" + "drawer_closed", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_top",    "block/interior/" + "drawer_open_top", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_bottom", "block/interior/" + "drawer_open_bottom", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open",        "block/interior/" + "drawer_open", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_closed",      "block/interior/" + "drawer_closed", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_top",    "block/interior/" + "drawer_open_top", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_bottom", "block/interior/" + "drawer_open_bottom", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open",        "block/interior/" + "drawer_open", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_closed",      "block/interior/" + "drawer_closed", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_top",    "block/interior/" + "drawer_open_top", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open_bottom", "block/interior/" + "drawer_open_bottom", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_open",        "block/interior/" + "drawer_open", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, loc0 + "_closed"));

        BlockStateVariantMap.TripleProperty<WeightedVariant,LinSeedPaintable, Direction, DrawerState>  map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, ModProperties.DRAWER_STATE);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            int[] rotate = {0, 0, 0, 180, 270, 90};
            for (Direction dir : Properties.HORIZONTAL_FACING.getValues()) {
                for (DrawerState state : ModProperties.DRAWER_STATE.getValues()) {
                    String part = "";
                    switch (state) {
                        case CLOSED -> part = "_closed";
                        case DRAWER_TOP -> part = "_open_top";
                        case DRAWER_BOTTOM -> part = "_open_bottom";
                        case OPEN -> part = "_open";
                    }
                    map.register(paint, dir, state, modelOf(finalLoc + part, false, rotate[dir.getIndex()], 0));
                }
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void shelves(BlockStateModelGenerator generator, Block block, String id, String name) {

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
        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,LinSeedPaintable, Direction>  map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map     .register(paint,Direction.NORTH, modelOf(finalLoc, false, 0, 0))
                    .register(paint,Direction.SOUTH, modelOf(finalLoc, false, 180, 0))
                    .register(paint,Direction.EAST,  modelOf(finalLoc, false, 90, 0))
                    .register(paint,Direction.WEST,  modelOf(finalLoc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

}
