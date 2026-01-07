package com.wildsregrown.data.blockstates;

import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.StoneGroup;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;

import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;

public class StoneGroupBlockStates {

    private final BlockStateModelGenerator generator;

    public StoneGroupBlockStates(BlockStateModelGenerator generator){
        this.generator = generator;
    }

    public void build(StoneGroup group) {

        Block block = group.get(StoneGroup.Common.layered);
        String texture = "block/rock/" + idFromBlock(block);

        block = group.get(StoneGroup.Common.layered);
        BlockStateLibrary.layered(generator, idFromBlock(block), texture, block, false);
        block = group.get(StoneGroup.Common.stairs);
        BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture);
        block = group.get(StoneGroup.Common.cobble_layered);
        BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_cobble", block, false);
        block = group.get(StoneGroup.Common.cobble_stairs);
        BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_cobble");

        if (group.constructionExist()) {
            block = group.get(StoneGroup.Construction.bricks);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_bricks", block, false);
            block = group.get(StoneGroup.Construction.bricks_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.pavement);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_pavement", block, false);
            block = group.get(StoneGroup.Construction.pavement_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_pavement");
            block = group.get(StoneGroup.Construction.cobble_bricks);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_cobble" + "_bricks", block, false);
            block = group.get(StoneGroup.Construction.cobble_bricks_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_cobble" + "_bricks");
            block = group.get(StoneGroup.Construction.cobble_pavement);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_cobble" + "_pavement", block, false);
            block = group.get(StoneGroup.Construction.cobble_pavement_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_cobble" + "_pavement");
            block = group.get(StoneGroup.Construction.pillar);
            BlockStateLibrary.pillarFacing(generator, idFromBlock(block), block, texture);
            block = group.get(StoneGroup.Construction.thin_pillar);
            BlockStateLibrary.thinPillarFacing(generator, idFromBlock(block), block, texture);
            block = group.get(StoneGroup.Construction.bricks_pillar);
            BlockStateLibrary.pillarFacing(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_thin_pillar);
            BlockStateLibrary.thinPillarFacing(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_wall_support);
            BlockStateLibrary.wallSupport(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_half_arch);
            BlockStateLibrary.halfArch(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_arch);
            BlockStateLibrary.arch(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_arrow_slit);
            BlockStateLibrary.arrowSlit(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.bricks_machicolations);
            BlockStateLibrary.machicolations(generator, idFromBlock(block), block, texture + "_bricks");
            block = group.get(StoneGroup.Construction.battlements);
            BlockStateLibrary.battlements(generator, idFromBlock(block), block, texture);
        }

        if (group.luxuryExist()) {
            block = group.get(StoneGroup.Luxury.smooth);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_smooth", block, false);
            block = group.get(StoneGroup.Luxury.smooth_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_smooth");
            block = group.get(StoneGroup.Luxury.polished);
            BlockStateLibrary.layered(generator, idFromBlock(block), texture + "_polished", block, false);
            block = group.get(StoneGroup.Luxury.polished_stairs);
            BlockStateLibrary.stairs(generator, idFromBlock(block), block, texture + "_polished");
            block = group.get(StoneGroup.Luxury.balustrade);
            BlockStateLibrary.balustrade(generator, idFromBlock(block), block, texture);
            block = group.get(StoneGroup.Luxury.smooth_balustrade);
            BlockStateLibrary.balustrade(generator, idFromBlock(block), block, texture + "_smooth");
            block = group.get(StoneGroup.Luxury.brazier);
            BlockStateLibrary.light(generator, idFromBlock(block), block, texture, "lights/stone_brazier", "lights/stone_brazier_filled", "lights/stone_brazier_lit");
            block = group.get(StoneGroup.Luxury.smooth_brazier);
            BlockStateLibrary.light(generator, idFromBlock(block), block, texture + "_smooth", "lights/stone_brazier", "lights/stone_brazier_filled", "lights/stone_brazier_lit");
            block = group.get(StoneGroup.Luxury.dungeonItemPedestal);
            BlockStateLibrary.itemLootPedestal(generator, idFromBlock(block), block, texture + "_polished");
            block = group.get(StoneGroup.Luxury.smooth_arrow_slit);
            BlockStateLibrary.arrowSlit(generator, idFromBlock(block), block, texture + "_smooth");
            block = group.get(StoneGroup.Luxury.smooth_half_arch);
            BlockStateLibrary.halfArch(generator, idFromBlock(block), block, texture + "_smooth");
            block = group.get(StoneGroup.Luxury.smooth_arch);
            BlockStateLibrary.arch(generator, idFromBlock(block), block, texture + "_smooth");
            block = group.get(StoneGroup.Luxury.polished_arrow_slit);
            BlockStateLibrary.arrowSlit(generator, idFromBlock(block), block, texture + "_polished");
            block = group.get(StoneGroup.Luxury.polished_half_arch);
            BlockStateLibrary.halfArch(generator, idFromBlock(block), block, texture + "_polished");
            block = group.get(StoneGroup.Luxury.polished_arch);
            BlockStateLibrary.arch(generator, idFromBlock(block), block, texture + "_polished");
        }

    }

}
