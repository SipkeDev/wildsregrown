package com.wildsregrown.data.blockstates;

import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.FabricGroup;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;
import static com.wildsregrown.registries.ModItemGroups.id;

public class FabricStates {

    public final static String texture_path = "block/fabric/";

    private final BlockStateModelGenerator generator;

    public FabricStates(BlockStateModelGenerator generator){
        this.generator = generator;
    }

    public void build(FabricGroup group) {

        Block block;
        String name, texture;

        block = group.get(FabricGroup.Common.layered);
        name = id(block);
        texture = texture_path + getName(name);
        BlockStateLibrary.layered(generator, "block/layered/" + name, texture, block, false);

        block = group.get(FabricGroup.Common.stairs);
        BlockStateLibrary.stairs(generator, name, block, texture);

        block = group.get(FabricGroup.Common.tent);
        tent(generator, block, id(block), texture);

    }

    private static void tent(BlockStateModelGenerator generator, Block block, String id, String texture) {

        applyTextureToModel(generator, texture_path + id, texture_path + "tent", texture);
        generator.registerParentedItemModel(block, Identifier.of(modid, texture_path+id));

        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                .register(Direction.NORTH, modelOf(texture_path+id, false, 180, 0))
                .register(Direction.SOUTH, modelOf(texture_path+id, false, 0  , 0))
                .register(Direction.EAST , modelOf(texture_path+id, false, 270, 0))
                .register(Direction.WEST , modelOf(texture_path+id, false, 90 , 0))
        );
    }

    private static String getName(String name){

        if (name.contains("linen")){
            return "linen";
        }
        if (name.contains("lace")){
            return "lace";
        }
        if (name.contains("jute")){
            return "jute";
        }
        if (name.contains("burlap")){
            return "burlap";
        }
        if (name.contains("leather")){
            return "leather";
        }
        return name;

    }

}
