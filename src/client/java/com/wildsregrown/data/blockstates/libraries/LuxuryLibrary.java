package com.wildsregrown.data.blockstates.libraries;

import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;

public class LuxuryLibrary {

    public static final String path = "block/luxury/";

    public static void tableChest(BlockStateModelGenerator generator, Block block, String id, String name) {

        String modelPath = path + "table_chest";

        applyTextureToModel(generator, modelPath + id + "_closed", modelPath + "_closed", log_path + name + "_wood");
        applyTextureToModel(generator, modelPath + id + "_open", modelPath + "_open", log_path + name + "_wood");

        generator.registerParentedItemModel(block, Identifier.of(modid, modelPath + id + "_closed"));

        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, Properties.OPEN)
                .register(Direction.NORTH, false, modelOf(modelPath + id + "_closed", false, 0, 0))
                .register(Direction.SOUTH, false, modelOf(modelPath + id + "_closed", false, 180, 0))
                .register(Direction.EAST, false, modelOf(modelPath + id + "_closed", false, 90, 0))
                .register(Direction.WEST, false, modelOf(modelPath + id + "_closed", false, 270, 0))
                .register(Direction.NORTH, true, modelOf(modelPath + id + "_open", false, 0, 0))
                .register(Direction.SOUTH, true, modelOf(modelPath + id + "_open", false, 180, 0))
                .register(Direction.EAST, true, modelOf(modelPath + id + "_open", false, 90, 0))
                .register(Direction.WEST, true, modelOf(modelPath + id + "_open", false, 270, 0))

        );
    }

}
