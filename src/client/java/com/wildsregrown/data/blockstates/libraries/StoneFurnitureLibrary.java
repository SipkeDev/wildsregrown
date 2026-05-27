package com.wildsregrown.data.blockstates.libraries;

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
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.CreateVariants;

public class StoneFurnitureLibrary {

    public static final String modelPath = "furniture/";

    public static void stoneDiningTable(BlockModelGenerators generator, Block block, String id, String name) {

        String loc = modelPath + id;

        applyTextureToModel(generator, loc + "_single", root + modelPath + "stone_dining_table_single", name);
        applyTextureToModel(generator, loc + "_middle", root + modelPath + "stone_dining_table_middle",name);
        applyTextureToModel(generator, loc + "_left", root + modelPath + "stone_dining_table_left",name);
        applyTextureToModel(generator, loc + "_right", root + modelPath + "stone_dining_table_right",name);


        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc + "_single"));

        PropertyDispatch.C2<MultiVariant, Direction, HorizontalConnected> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, WRGProperties.HORIZONTAL_CONNECTED);

        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnected shape : HorizontalConnected.values()) {
                String part = "";
                switch (shape) {
                    case SINGLE -> part = "_single";
                    case MIDDLE -> part = "_middle";
                    case LEFT -> part = "_left";
                    case RIGHT -> part = "_right";
                }
                map.select(dir, shape, modelOf(loc + part, false, dir.get2DDataValue()*90, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void stoneStool(BlockModelGenerators generator, Block block, String id, String name) {

        String loc = modelPath + id + "_stool";

        applyTextureToModel(generator, loc, root + modelPath + "stone_stool", name);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc));
        CreateSingleton(generator, block, loc);
    }

    public static void stoneTable(BlockModelGenerators generator, Block block, String id, String name) {

        String loc = modelPath + id + "_table";

        applyTextureToModel(generator, loc, root + modelPath + "stone_table", name);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc));
        CreateSingleton(generator, block, loc);
    }

    public static void stoneBench(BlockModelGenerators generator, Block block, String id, String name) {

        String loc = modelPath + id;

        applyTextureToModel(generator, loc + "_single", root + modelPath + "stone_bench_single", name);
        applyTextureToModel(generator, loc + "_middle", root + modelPath + "stone_bench_middle",name);
        applyTextureToModel(generator, loc + "_left", root + modelPath + "stone_bench_left",name);
        applyTextureToModel(generator, loc + "_right", root + modelPath + "stone_bench_right",name);


        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + loc + "_single"));

        PropertyDispatch.C2<MultiVariant, Direction, HorizontalConnected> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, WRGProperties.HORIZONTAL_CONNECTED);

        for (Direction dir : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            for (HorizontalConnected shape : HorizontalConnected.values()) {
                String part = "";
                switch (shape) {
                    case SINGLE -> part = "_single";
                    case MIDDLE -> part = "_middle";
                    case LEFT -> part = "_left";
                    case RIGHT -> part = "_right";
                }
                map.select(dir, shape, modelOf(loc + part, false, dir.get2DDataValue()*90, 0));
            }
        }
        CreateVariants(generator, block, map);
    }


}
