package com.wildsregrown.blocks.wood;

import com.wildsregrown.blocks.properties.fuel.FuelBurn;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.mixin.block.OffsetAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jspecify.annotations.Nullable;

public class Fireplace extends Block {

    private static final EnumProperty<FuelBurn> LIT = ModProperties.FUEL_BURN;
    private static final IntProperty FUEL = ModProperties.FUEL_6;

    public Fireplace(Settings settings) {
        super(offset(settings));
        this.setDefaultState(this.getDefaultState().with(FUEL, 0).with(LIT, FuelBurn.off));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT, FUEL);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        FuelBurn burn = state.get(LIT);
        if (burn == FuelBurn.lit || burn == FuelBurn.starting){
            if (burn == FuelBurn.starting){

            }
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);
    }

    private static Settings offset(Settings settings) {
        ((OffsetAccessor)settings).offsetter((state, pos) -> {
            final long l = MathHelper.hashCode(pos.getX(), 0, pos.getZ());
            final float f = 0.25f;
            final double x = MathHelper.clamp(((double)((float)(l & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final double z = MathHelper.clamp(((double)((float)(l >> 8 & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final int i = state.get(ModProperties.LAYERS) ;
            return new Vec3d(x,(i-8)*0.125F, z);
        });
        return settings;
    }

}
