package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import net.minecraft.block.Block;
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

public class FurnitureLibrary {

    private final static String modelPath = "decoration/furniture/";
    private final static String modelPathOld = "furniture/";

    public static void stool(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        String source = root + modelPathOld + "wood_stool";
        if (pines){
            source = root + modelPathOld + "wood_stool_pine";
        }

        applyTextureToModel(generator, loc0, source,log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, source, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, source,log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, source,log_path + name + "_paintable_wood");
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));
        BlockStateVariantMap.SingleProperty<WeightedVariant, LinSeedPaintable> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint, modelOf(loc));
        }
        CreateVariants(generator, block, map);
    }

    public static void chair(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/wood_chair",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_chair", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_chair",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/wood_chair",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint,Direction.NORTH, modelOf(loc, false, 0 , 0))
                    .register(paint,Direction.SOUTH, modelOf(loc, false, 180, 0))
                    .register(paint,Direction.EAST , modelOf(loc, false, 90, 0))
                    .register(paint,Direction.WEST , modelOf(loc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void throne(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/wood_throne",log_path + name + "_wood", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_throne", log_path + "pine_paintable_wood", plank_path + "pine_paintable_parquet");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_throne",log_path + "fruit_paintable_wood",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/wood_throne",log_path + name + "_paintable_wood",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint,Direction.NORTH, modelOf(loc, false, 0 , 0))
                    .register(paint,Direction.SOUTH, modelOf(loc, false, 180, 0))
                    .register(paint,Direction.EAST , modelOf(loc, false, 90, 0))
                    .register(paint,Direction.WEST , modelOf(loc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void table(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/wood_table",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_table", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/wood_table",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/wood_table",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.SingleProperty<WeightedVariant,LinSeedPaintable> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint, modelOf(loc));
        }

        CreateVariants(generator, block, map);

    }

    public static void mirror(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/mirror",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/mirror", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/mirror",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/mirror",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint,Direction.NORTH, modelOf(loc, false, 0 , 0))
                    .register(paint,Direction.SOUTH, modelOf(loc, false, 180, 0))
                    .register(paint,Direction.EAST , modelOf(loc, false, 90, 0))
                    .register(paint,Direction.WEST , modelOf(loc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void woodenBench(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        for (int i = 0; i < 2; i++) {
            applyTextureToModel(generator, loc0 + "_single" + "_" + i, root + modelPathOld + "wooden_bench_single" + "_" + i, log_path + name + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_middle" + "_" + i, root + modelPathOld + "wooden_bench_middle" + "_" + i, log_path + name + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_left" + "_" + i, root + modelPathOld + "wooden_bench_left" + "_" + i, log_path + name + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_right" + "_" + i, root + modelPathOld + "wooden_bench_right" + "_" + i, log_path + name + "_wood", plank_path + name + "_planks");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_single" + "_" + i, root + modelPathOld + "wooden_bench_single" + "_" + i, log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
                applyTextureToModel(generator, loc1 + "_middle" + "_" + i, root + modelPathOld + "wooden_bench_middle" + "_" + i, log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
                applyTextureToModel(generator, loc1 + "_left" + "_" + i, root + modelPathOld + "wooden_bench_left" + "_" + i, log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
                applyTextureToModel(generator, loc1 + "_right" + "_" + i, root + modelPathOld + "wooden_bench_right" + "_" + i, log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_single" + "_" + i, root + modelPathOld + "wooden_bench_single" + "_" + i, log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
                applyTextureToModel(generator, loc1 + "_middle" + "_" + i, root + modelPathOld + "wooden_bench_middle" + "_" + i, log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
                applyTextureToModel(generator, loc1 + "_left" + "_" + i, root + modelPathOld + "wooden_bench_left" + "_" + i, log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
                applyTextureToModel(generator, loc1 + "_right" + "_" + i, root + modelPathOld + "wooden_bench_right" + "_" + i, log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
            } else {
                applyTextureToModel(generator, loc1 + "_single" + "_" + i, root + modelPathOld + "wooden_bench_single" + "_" + i, log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
                applyTextureToModel(generator, loc1 + "_middle" + "_" + i, root + modelPathOld + "wooden_bench_middle" + "_" + i, log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
                applyTextureToModel(generator, loc1 + "_left" + "_" + i, root + modelPathOld + "wooden_bench_left" + "_" + i, log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
                applyTextureToModel(generator, loc1 + "_right" + "_" + i, root + modelPathOld + "wooden_bench_right" + "_" + i, log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
            }
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_single"));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant,LinSeedPaintable, Direction, HorizontalConnected, Integer> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, ModProperties.HORIZONTAL_CONNECTED, ModProperties.VARIATIONS_2);

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

                    map.register(paint, dir, shape, 1, modelOf(finalLoc + part + "_0", false, rotate[dir.getIndex()], 0));
                    map.register(paint, dir, shape, 2, modelOf(finalLoc + part + "_1", false, rotate[dir.getIndex()], 0));
                }
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void woodenDiningTable(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_single", root + modelPathOld + "wooden_dining_table_single",log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_middle", root + modelPathOld + "wooden_dining_table_middle",log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_left", root + modelPathOld + "wooden_dining_table_left",   log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_right", root + modelPathOld + "wooden_dining_table_right", log_path + name + "_wood",plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + "wooden_dining_table_single",log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + "wooden_dining_table_middle",log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + "wooden_dining_table_left",   log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + "wooden_dining_table_right", log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + "wooden_dining_table_single",log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + "wooden_dining_table_middle",log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + "wooden_dining_table_left",   log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + "wooden_dining_table_right", log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
        }else {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + "wooden_dining_table_single",log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + "wooden_dining_table_middle",log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + "wooden_dining_table_left",   log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + "wooden_dining_table_right", log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_single"));

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

}
