package com.wildsregrown.blocks.carpentry.framing.beam;

import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.item.MultiSelectableBlock;

import static com.wildsregrown.WildsRegrown.modid;

public abstract class AbstractSupport extends Block implements ITintedBlock, SimpleWaterloggedBlock, MultiSelectableBlock {

    private final Identifier id;

    public AbstractSupport(String id, Properties settings) {
        super(settings);
        this.id = generateIdentifier(modid, id);
    }

    public static boolean isOfSupport(Block block){return block instanceof AbstractSupport;}
    public static boolean isOfSupport(BlockState state){
        return isOfSupport(state.getBlock());
    }

    public static boolean isOfBeam(Block block){return block instanceof BeamSupport;}
    public static boolean isOfBeam(BlockState state){return isOfBeam(state.getBlock());}

    public static boolean isOfPost(Block block){
        return block instanceof PostSupport;
    }
    public static boolean isOfPost(BlockState state){
        return isOfPost(state.getBlock());
    }

    public static boolean isOfCeiling(Block block){return block instanceof CeilingSupport;}
    public static boolean isOfCeiling(BlockState state){return isOfCeiling(state.getBlock());}

    public static boolean isOfDiagonal(Block block){return block instanceof DiagonalSupport;}
    public static boolean isOfDiagonal(BlockState state){return isOfDiagonal(state.getBlock());}

    @Override
    public Item asItem() {
        return asItem(id);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(ModProperties.LINSEED_PAINT).getRGB();
    }

}
