package com.wildsregrown.blocks.flora.bush;

import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.registries.world.Trees;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

public class BushEntity extends BlockEntity {

    private int species;

    public BushEntity(BlockPos pos, BlockState state) {
        super(null, pos, state);
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.species = view.getInt("species", 0);
    }
    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putInt("species", species);
    }

    public TreeConfig config(){
        return WorldRegistries.TREES.get(species);
    }

    public void update(BlockState state, ServerWorld world, BlockPos pos, Random random) {

    }
}
