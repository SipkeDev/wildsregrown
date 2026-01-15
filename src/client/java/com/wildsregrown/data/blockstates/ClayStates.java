package com.wildsregrown.data.blockstates;

import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.ClayGroup;
import com.wildsregrown.registries.groups.StoneGroup;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;

import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.root;
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
        block = group.get(ClayGroup.Bricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_bricks", root+"soil/" + name + "_bricks", block, false);

        block = group.get(ClayGroup.Bricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.Bricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.Bricks.stairs);
        BlockStateLibrary.stairs(generator, id(block) + "_bricks", block, texture);

        block = group.get(ClayGroup.Bricks.balustrade);
        BlockStateLibrary.balustrade(generator, id(block) + "_bricks", block, texture);

        block = group.get(ClayGroup.Bricks.wall_support);
        BlockStateLibrary.wallSupport(generator, id(block) + "_bricks", block, texture);

        block = group.get(ClayGroup.Bricks.half_arch);
        BlockStateLibrary.halfArch(generator, id(block) + "_bricks", block, texture);

        block = group.get(ClayGroup.Bricks.arch);
        BlockStateLibrary.arch(generator, id(block) + "_bricks", block, texture);

        block = group.get(ClayGroup.Bricks.arrow_slit);
        BlockStateLibrary.arrowSlit(generator, id(block) + "_bricks", block, texture);

        texture = root+"soil/" + name + "_old_bricks";
        block = group.get(ClayGroup.OldBricks.block);
        BlockStateLibrary.layered(generator, "layered/" + name + "_old_bricks", texture, block, false);
        block = group.get(ClayGroup.OldBricks.quarter_stairs);
        BlockStateLibrary.quarterStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.OldBricks.half_stairs);
        BlockStateLibrary.halfStairs(generator, idFromBlock(block), block, texture);
        block = group.get(ClayGroup.OldBricks.stairs);
        BlockStateLibrary.stairs(generator, id(block) + "_old_bricks", block, texture);

        block = group.get(ClayGroup.OldBricks.balustrade);
        BlockStateLibrary.balustrade(generator, id(block) + "_old_bricks", block, texture);

        block = group.get(ClayGroup.OldBricks.wall_support);
        BlockStateLibrary.wallSupport(generator, id(block) + "_old_bricks", block, texture);

        block = group.get(ClayGroup.OldBricks.half_arch);
        BlockStateLibrary.halfArch(generator, id(block) + "_old_bricks", block, texture);

        block = group.get(ClayGroup.OldBricks.arch);
        BlockStateLibrary.arch(generator, id(block) + "_old_bricks", block, texture);

        block = group.get(ClayGroup.OldBricks.arrow_slit);
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

    }
}
