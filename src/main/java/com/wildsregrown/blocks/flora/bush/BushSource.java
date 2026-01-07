package com.wildsregrown.blocks.flora.bush;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.blocks.Dice;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class BushSource extends BlockWithEntity {

    public BushSource(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(Dice.d4(random) == 1) {
            if (world.getBlockEntity(pos) instanceof BushEntity bush) {
                bush.update(state, world, pos, random);
            }
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(BushSource::new);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BushEntity(pos, state);
    }

}
