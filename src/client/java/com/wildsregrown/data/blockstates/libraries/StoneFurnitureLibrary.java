package com.wildsregrown.data.blockstates.libraries;

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
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.CreateVariants;

public class StoneFurnitureLibrary {

    public static final String modelPath = "furniture/";

    public static void stoneDiningTable(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc = modelPath + id;

        applyTextureToModel(generator, loc + "_single", root + modelPath + "stone_dining_table_single", name);
        applyTextureToModel(generator, loc + "_middle", root + modelPath + "stone_dining_table_middle",name);
        applyTextureToModel(generator, loc + "_left", root + modelPath + "stone_dining_table_left",name);
        applyTextureToModel(generator, loc + "_right", root + modelPath + "stone_dining_table_right",name);


        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc + "_single"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, HorizontalConnected> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.HORIZONTAL_CONNECTED);

        for (Direction dir : Properties.HORIZONTAL_FACING.getValues()) {
            for (HorizontalConnected shape : HorizontalConnected.values()) {
                String part = "";
                switch (shape) {
                    case SINGLE -> part = "_single";
                    case MIDDLE -> part = "_middle";
                    case LEFT -> part = "_left";
                    case RIGHT -> part = "_right";
                }
                map.register(dir, shape, modelOf(loc + part, false, dir.getHorizontalQuarterTurns()*90, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void stoneStool(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc = modelPath + id + "_stool";

        applyTextureToModel(generator, loc, root + modelPath + "stone_stool", name);
        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc));
        CreateSingleton(generator, block, loc);
    }

    public static void stoneTable(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc = modelPath + id + "_table";

        applyTextureToModel(generator, loc, root + modelPath + "stone_table", name);
        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc));
        CreateSingleton(generator, block, loc);
    }

    public static void stoneBench(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc = modelPath + id;

        applyTextureToModel(generator, loc + "_single", root + modelPath + "stone_bench_single", name);
        applyTextureToModel(generator, loc + "_middle", root + modelPath + "stone_bench_middle",name);
        applyTextureToModel(generator, loc + "_left", root + modelPath + "stone_bench_left",name);
        applyTextureToModel(generator, loc + "_right", root + modelPath + "stone_bench_right",name);


        generator.registerParentedItemModel(block, Identifier.of(modid, root + loc + "_single"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, HorizontalConnected> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.HORIZONTAL_CONNECTED);

        for (Direction dir : Properties.HORIZONTAL_FACING.getValues()) {
            for (HorizontalConnected shape : HorizontalConnected.values()) {
                String part = "";
                switch (shape) {
                    case SINGLE -> part = "_single";
                    case MIDDLE -> part = "_middle";
                    case LEFT -> part = "_left";
                    case RIGHT -> part = "_right";
                }
                map.register(dir, shape, modelOf(loc + part, false, dir.getHorizontalQuarterTurns()*90, 0));
            }
        }
        CreateVariants(generator, block, map);
    }


}
