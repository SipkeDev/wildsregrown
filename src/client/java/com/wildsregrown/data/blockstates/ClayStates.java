package com.wildsregrown.data.blockstates;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.data.blockstates.libraries.CastleLibrary;
import com.wildsregrown.registries.groups.ClayGroup;
import com.wildsregrown.registries.groups.StoneGroup;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.modelOf;

public class ClayStates {

    private final BlockModelGenerators generator;

    public ClayStates(BlockModelGenerators generator){
        this.generator = generator;
    }

    public void build(ClayGroup group) {

        Block block = group.soil;
        String texture, name = idFromBlock(block);

        texture = root+"soil/" + name + "_bricks";
        block = group.getBricks(ClayGroup.Bricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_bricks", root+"soil/" + name + "_bricks", block, false);

        block = group.getBricks(ClayGroup.Bricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.getBricks(ClayGroup.Bricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.getBricks(ClayGroup.Bricks.stairs);
        BlockStateLibrary.stairs(generator, idFromBlock(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.balustrade);
        BlockStateLibrary.balustrade(generator, idFromBlock(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.wall_support);
        BlockStateLibrary.wallSupport(generator, idFromBlock(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.half_arch);
        BlockStateLibrary.halfArch(generator, idFromBlock(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.arch);
        BlockStateLibrary.arch(generator, idFromBlock(block) + "_bricks", block, texture);

        block = group.getBricks(ClayGroup.Bricks.arrow_slit);
        CastleLibrary.arrowSlit(generator, idFromBlock(block) + "_bricks", block, texture,0);

        texture = root+"soil/" + name + "_old_bricks";
        block = group.getOldBricks(ClayGroup.Bricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_old_bricks", texture, block, false);
        block = group.getOldBricks(ClayGroup.Bricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.getOldBricks(ClayGroup.Bricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.getOldBricks(ClayGroup.Bricks.stairs);
        BlockStateLibrary.stairs(generator, idFromBlock(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.balustrade);
        BlockStateLibrary.balustrade(generator, idFromBlock(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.wall_support);
        BlockStateLibrary.wallSupport(generator, idFromBlock(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.half_arch);
        BlockStateLibrary.halfArch(generator, idFromBlock(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.arch);
        BlockStateLibrary.arch(generator, idFromBlock(block) + "_old_bricks", block, texture);

        block = group.getOldBricks(ClayGroup.Bricks.arrow_slit);
        CastleLibrary.arrowSlit(generator, idFromBlock(block) + "_old_bricks", block, texture, 0);

        texture = root+"soil/" + idFromBlock(group.soil) + "_roof_tiles";
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


        texture = root+"soil/" + idFromBlock(group.soil) + "_plaster";
        block = group.get(ClayGroup.Plaster.layered);
        BlockStateLibrary.layered(generator, name + "_plaster", texture, block, false);
        texture = root+"soil/" + idFromBlock(group.soil) + "_old_plaster";
        block = group.get(ClayGroup.Plaster.old_layered);
        BlockStateLibrary.layered(generator, name + "_old_plaster", texture, block, false);
        texture = root+"soil/" + idFromBlock(group.soil) + "_waved_plaster";
        block = group.get(ClayGroup.Plaster.waved_layered);
        BlockStateLibrary.layered(generator, name + "_waved_plaster", texture, block, false);

        /**
         * pottery
         */
        texture = root+"soil/" + idFromBlock(group.soil) + "_waved_plaster";
        block = group.get(ClayGroup.Pottery.urn);
        urn(generator, block, texture);
        block = group.get(ClayGroup.Pottery.amphora);
        amphora(generator, block, texture);
        texture = root+"soil/" + idFromBlock(group.soil) + "_old_plaster";
        block = group.get(ClayGroup.Pottery.urn_old);
        urn(generator, block, texture);
        block = group.get(ClayGroup.Pottery.amphora_old);
        amphora(generator, block, texture);

    }

    public static void urn(BlockModelGenerators generator, Block block, String texture) {
        String id = idFromBlock(block);
        applyTextureToModel(generator, id, root + "decoration/pottery/urn", texture);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id));
        CreateSingleton(generator, block, id);
    }

    public static void amphora(BlockModelGenerators generator, Block block, String texture) {
        String id = idFromBlock(block);
        applyTextureToModel(generator, id, root + "decoration/pottery/amphora", texture);
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id));
        PropertyDispatch.C1<MultiVariant, Direction.Axis> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS);
        map.select(Direction.Axis.X, modelOf(id, false, 90, 0));
        map.select(Direction.Axis.Z, modelOf(id, false, 0, 0));
        CreateVariants(generator, block, map);
    }

}
