package com.wildsregrown.blocks.carpentry;

import com.wildsregrown.blocks.properties.fuel.FuelBurn;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.mixin.block.OffsetAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import wildsregrown.api.block.properties.WRGProperties;

public class Fireplace extends Block {

    private static final EnumProperty<FuelBurn> LIT = ModProperties.FUEL_BURN;
    private static final IntegerProperty FUEL = ModProperties.FUEL_6;

    public Fireplace(Properties settings) {
        super(offset(settings));
        this.registerDefaultState(this.defaultBlockState().setValue(FUEL, 0).setValue(LIT, FuelBurn.off));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, FUEL);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        FuelBurn burn = state.getValue(LIT);
        if (burn == FuelBurn.lit || burn == FuelBurn.starting){
            if (burn == FuelBurn.starting){

            }
        }
    }

    @Override
    protected boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return super.canSurvive(state, world, pos);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return super.getStateForPlacement(ctx);
    }

    private static Properties offset(Properties settings) {
        ((OffsetAccessor)settings).offsetter((state, pos) -> {
            final long l = Mth.getSeed(pos.getX(), 0, pos.getZ());
            final float f = 0.25f;
            final double x = Mth.clamp(((double)((float)(l & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final double z = Mth.clamp(((double)((float)(l >> 8 & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final int i = state.getValue(WRGProperties.LAYERS) ;
            return new Vec3(x,(i-8)*0.125F, z);
        });
        return settings;
    }

}
