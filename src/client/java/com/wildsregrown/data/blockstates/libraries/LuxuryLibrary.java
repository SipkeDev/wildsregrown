package com.wildsregrown.data.blockstates.libraries;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LuxuryLibrary {

    public static final String path = "luxury/";

    public static void tableChest(BlockModelGenerators generator, Block block, String id, String name) {

        String modelPath = path + "table_chest";

        applyTextureToModel(generator, modelPath + id + "_closed", root+modelPath + "_closed", log_path + name + "_wood");
        applyTextureToModel(generator, modelPath + id + "_open", root+modelPath + "_open", log_path + name + "_wood");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+modelPath + id + "_closed"));

        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN)
                .select(Direction.NORTH, false, modelOf(modelPath + id + "_closed", false, 0, 0))
                .select(Direction.SOUTH, false, modelOf(modelPath + id + "_closed", false, 180, 0))
                .select(Direction.EAST, false, modelOf(modelPath + id + "_closed", false, 90, 0))
                .select(Direction.WEST, false, modelOf(modelPath + id + "_closed", false, 270, 0))
                .select(Direction.NORTH, true, modelOf(modelPath + id + "_open", false, 0, 0))
                .select(Direction.SOUTH, true, modelOf(modelPath + id + "_open", false, 180, 0))
                .select(Direction.EAST, true, modelOf(modelPath + id + "_open", false, 90, 0))
                .select(Direction.WEST, true, modelOf(modelPath + id + "_open", false, 270, 0))

        );
    }

}
