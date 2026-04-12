package com.wildsregrown.data.blockstates;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.ClayGroup;
import com.wildsregrown.registries.groups.StoneGroup;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;
import static com.wildsregrown.registries.ModItemGroups.id;

public class ClayStates {

    private final BlockStateModelGenerator generator;

    public ClayStates(BlockStateModelGenerator generator){
        this.generator = generator;
    }

    public void build(ClayGroup group) {

        Block block = group.soil;
        String texture, name = id(block);

        texture = root+"soil/" + name + "_bricks";
        block = group.getBricks(ClayGroup.Bricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_bricks", root+"soil/" + name + "_bricks", block, false);

        block = group.getBricks(ClayGroup.Bricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.getBricks(ClayGroup.Bricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.getBricks(ClayGroup.Bricks.stairs);
        BlockStateLibrary.stairs(generator, id(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.balustrade);
        BlockStateLibrary.balustrade(generator, id(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.wall_support);
        BlockStateLibrary.wallSupport(generator, id(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.half_arch);
        BlockStateLibrary.halfArch(generator, id(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.arch);
        BlockStateLibrary.arch(generator, id(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.arrow_slit);
        BlockStateLibrary.arrowSlit(generator, id(block) + "_bricks", block, texture);

        texture = root+"soil/" + name + "_old_bricks";
        block = group.getOldBricks(ClayGroup.Bricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_old_bricks", texture, block, false);
        block = group.getOldBricks(ClayGroup.Bricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.getOldBricks(ClayGroup.Bricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.getOldBricks(ClayGroup.Bricks.stairs);
        BlockStateLibrary.stairs(generator, id(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.balustrade);
        BlockStateLibrary.balustrade(generator, id(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.wall_support);
        BlockStateLibrary.wallSupport(generator, id(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.half_arch);
        BlockStateLibrary.halfArch(generator, id(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.arch);
        BlockStateLibrary.arch(generator, id(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.arrow_slit);
        BlockStateLibrary.arrowSlit(generator, id(block) + "_old_bricks", block, texture);

        texture = root+"soil/" + id(group.soil) + "_roof_tiles";
        block = group.get(ClayGroup.Tiles.block);
        BlockStateLibrary.layered(generator, name + "_roof_tiles", texture, block, false);
        block = group.get(ClayGroup.Tiles.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.Tiles.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.Tiles.stairs);
        BlockStateLibrary.stairs(generator, name + "_roof_tiles", block, texture);
        block = group.get(ClayGroup.Tiles.roof);
        BlockStateLibrary.roof(generator, block,name + "_roof_tiles", texture);


        texture = root+"soil/" + id(group.soil) + "_plaster";
        block = group.get(ClayGroup.Plaster.layered);
        BlockStateLibrary.layered(generator, name + "_plaster", texture, block, false);
        texture = root+"soil/" + id(group.soil) + "_old_plaster";
        block = group.get(ClayGroup.Plaster.old_layered);
        BlockStateLibrary.layered(generator, name + "_old_plaster", texture, block, false);
        texture = root+"soil/" + id(group.soil) + "_waved_plaster";
        block = group.get(ClayGroup.Plaster.waved_layered);
        BlockStateLibrary.layered(generator, name + "_waved_plaster", texture, block, false);

        /**
         * pottery
         */
        texture = root+"soil/" + id(group.soil) + "_waved_plaster";
        block = group.get(ClayGroup.Pottery.urn);
        urn(generator, block, texture);
        block = group.get(ClayGroup.Pottery.amphora);
        amphora(generator, block, texture);
        texture = root+"soil/" + id(group.soil) + "_old_plaster";
        block = group.get(ClayGroup.Pottery.urn_old);
        urn(generator, block, texture);
        block = group.get(ClayGroup.Pottery.amphora_old);
        amphora(generator, block, texture);

    }

    public static void urn(BlockStateModelGenerator generator, Block block, String texture) {
        String id = idFromBlock(block);
        applyTextureToModel(generator, id, root + "decoration/pottery/urn", texture);
        generator.registerParentedItemModel(block, Identifier.of(modid, root + id));
        CreateSingleton(generator, block, id);
    }

    public static void amphora(BlockStateModelGenerator generator, Block block, String texture) {
        String id = idFromBlock(block);
        applyTextureToModel(generator, id, root + "decoration/pottery/amphora", texture);
        generator.registerParentedItemModel(block, Identifier.of(modid, root + id));
        BlockStateVariantMap.SingleProperty<WeightedVariant, Direction.Axis> map = BlockStateVariantMap.models(Properties.HORIZONTAL_AXIS);
        map.register(Direction.Axis.X, modelOf(id, false, 90, 0));
        map.register(Direction.Axis.Z, modelOf(id, false, 0, 0));
        CreateVariants(generator, block, map);
    }

}
