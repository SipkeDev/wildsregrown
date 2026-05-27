package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class FurnitureLibrary {

    private final static String modelPath = "decoration/furniture/";
    private final static String modelPathOld = "furniture/";

    public static void stool(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = switch (tier){
            case 1 -> "refined_wood_stool";
            case 2 -> "empty";
            default -> "basic_wood_stool";
        };

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        String source = root + modelPathOld + type;

        applyTextureToModel(generator, loc0, source,log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, source, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, source,log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, source,log_path + name + "_paintable_wood");
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));
        PropertyDispatch.C1<MultiVariant, LinSeedPaintable> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.select(paint, modelOf(loc));
        }
        CreateVariants(generator, block, map);
    }

    public static void chair(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = switch (tier){
            case 1 -> "refined_wood_chair";
            case 2 -> "throne_wood_chair";
            default -> "basic_wood_chair";
        };
        String wood = tier == 0 ? "_wood" : "_smooth_wood";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/" + type,log_path + name + wood, plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/" + type, log_path + "pine_paintable" + wood, plank_path + "pine_paintable_planks");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/" + type,log_path + "fruit_paintable" + wood, plank_path + "fruit_paintable_planks");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/" + type,log_path + name + "_paintable" + wood, plank_path + name + "_paintable_planks");
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C2<MultiVariant,LinSeedPaintable, Direction> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.select(paint,Direction.NORTH, modelOf(loc, false, 0 , 0))
                    .select(paint,Direction.SOUTH, modelOf(loc, false, 180, 0))
                    .select(paint,Direction.EAST , modelOf(loc, false, 90, 0))
                    .select(paint,Direction.WEST , modelOf(loc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void mirror(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = tier == 0 ? "basic_mirror" : "refined_mirror";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/furniture/" + type,log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/furniture/" + type, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/furniture/" + type,log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/furniture/" + type,log_path + name + "_paintable_wood");
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C2<MultiVariant,LinSeedPaintable, Direction> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.select(paint,Direction.NORTH, modelOf(loc, false, 0 , 0))
                    .select(paint,Direction.SOUTH, modelOf(loc, false, 180, 0))
                    .select(paint,Direction.EAST , modelOf(loc, false, 90, 0))
                    .select(paint,Direction.WEST , modelOf(loc, false, 270, 0));
        }

        CreateVariants(generator, block, map);
    }

    public static void woodenBench(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = switch (tier){
            case 1 -> "refined";
            case 2 -> "empty";
            default -> "basic";
        };

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_single" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_single", log_path + name + "_wood", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_middle" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_middle", log_path + name + "_wood", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_left" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_left", log_path + name + "_wood", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_right" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_right", log_path + name + "_wood", plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_single" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_single", log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_middle", log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_left", log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_right", log_path + "pine_paintable_wood", plank_path + "pine_paintable_planks");
        } else if (fruit) {
            applyTextureToModel(generator, loc1 + "_single" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_single", log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_middle", log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_left", log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_right", log_path + "fruit_paintable_wood", plank_path + "fruit_paintable_planks");
        } else {
            applyTextureToModel(generator, loc1 + "_single" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_single", log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_middle", log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_left", log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right" + "_" + type, root + modelPathOld + type + "_" + "wooden_bench_right", log_path + name + "_paintable_wood", plank_path + name + "_paintable_planks");
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + loc0 + "_single_" + type));

        PropertyDispatch.C3<MultiVariant, LinSeedPaintable, Direction, HorizontalConnected> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, WRGProperties.HORIZONTAL_CONNECTED);

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
                    map.select(paint, dir, shape, modelOf(finalLoc + part + "_" + type, false, rotate[dir.get3DDataValue()], 0));
                }
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void woodenBenchBackrest(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = switch (tier){
            case 1 -> "refined";
            case 2 -> "luxury";
            default -> "basic";
        };
        String wood = tier != 0 ? "_smooth" : "";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

            applyTextureToModel(generator, loc0 + "_single", root + modelPathOld + type + "_wooden_bench_backrest_single", log_path + name + wood + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_middle", root + modelPathOld + type + "_wooden_bench_backrest_middle", log_path + name + wood + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_left", root + modelPathOld + type + "_wooden_bench_backrest_left", log_path + name + wood + "_wood", plank_path + name + "_planks");
            applyTextureToModel(generator, loc0 + "_right", root + modelPathOld + type + "_wooden_bench_backrest_right", log_path + name + wood + "_wood", plank_path + name + "_planks");
            if (pines) {
                applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "_wooden_bench_backrest_single", log_path + "pine_paintable" + wood + "_wood", plank_path + "pine_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "_wooden_bench_backrest_middle", log_path + "pine_paintable" + wood + "_wood", plank_path + "pine_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + type + "_wooden_bench_backrest_left", log_path + "pine_paintable" + wood + "_wood", plank_path + "pine_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type + "_wooden_bench_backrest_right", log_path + "pine_paintable" + wood + "_wood", plank_path + "pine_paintable" + "_planks");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "_wooden_bench_backrest_single", log_path + "fruit_paintable" + wood + "_wood", plank_path + "fruit_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "_wooden_bench_backrest_middle", log_path + "fruit_paintable" + wood + "_wood", plank_path + "fruit_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + type + "_wooden_bench_backrest_left", log_path + "fruit_paintable" + wood + "_wood", plank_path + "fruit_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type + "_wooden_bench_backrest_right", log_path + "fruit_paintable" + wood + "_wood", plank_path + "fruit_paintable" + "_planks");
            } else {
                applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "_wooden_bench_backrest_single", log_path + name + "_paintable" + wood + "_wood", plank_path + name + "_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "_wooden_bench_backrest_middle", log_path + name + "_paintable" + wood + "_wood", plank_path + name + "_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + type + "_wooden_bench_backrest_left", log_path + name + "_paintable" + wood + "_wood", plank_path + name + "_paintable" + "_planks");
                applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type +"_wooden_bench_backrest_right", log_path + name + "_paintable" + wood + "_wood", plank_path + name + "_paintable" + "_planks");
            }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + loc0 + "_single"));

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

    public static void woodenTable(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        String type = tier == 0 ? "basic_" : "refined_";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_single", root + modelPathOld + type + "wooden_table_single",log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_middle", root + modelPathOld + type + "wooden_table_middle",log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_left", root + modelPathOld + type + "wooden_table_left",   log_path + name + "_wood",plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_right", root + modelPathOld + type + "wooden_table_right", log_path + name + "_wood",plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "wooden_table_single",log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "wooden_table_middle",log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + type + "wooden_table_left",   log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type + "wooden_table_right", log_path + "pine_paintable_wood",plank_path + "pine_paintable_planks");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "wooden_table_single",log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "wooden_table_middle",log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld + type + "wooden_table_left",   log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type + "wooden_table_right", log_path + "fruit_paintable_wood",plank_path + "fruit_paintable_planks");
        }else {
            applyTextureToModel(generator, loc1 + "_single", root + modelPathOld + type + "wooden_table_single",log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_middle", root + modelPathOld + type + "wooden_table_middle",log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left", root + modelPathOld +  type + "wooden_table_left",   log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right", root + modelPathOld + type + "wooden_table_right", log_path + name + "_paintable_wood",plank_path + name + "_paintable_planks");
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

}
