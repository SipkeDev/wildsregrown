package com.wildsregrown.blocks.flora;

import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.properties.FloraStage;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.mixin.OffsetAccessor;
import net.minecraft.block.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public abstract class Flora extends Block implements IFlora, ITintedBlock {

    private static final VoxelShape[] shape;
    public static final EnumProperty<FloraStage> stage = ModProperties.FLORAL_STAGE;
    public static final IntProperty moisture = ModProperties.MOISTURE;
    public static final IntProperty layers = ModProperties.LAYERS;
    public final IntProperty age;       //Decides how many age steps there are
    public final float toughness;       //Decides change of recovery from sickness.
    public final int consumptionRate;   //Moisture needed for action.
    public final int floweringRange;

    public Flora(Settings settings, IntProperty age, float toughness, int consumptionRate, int floweringRange){
        super(offset(settings));
        this.toughness = toughness;
        this.consumptionRate = consumptionRate;
        this.floweringRange = floweringRange;
        this.age = age;
        this.setDefaultState(this.getDefaultState().with(moisture, 8).with(age, 0).with(stage, FloraStage.HEALTHY).with(layers, 8));
    }

    public boolean hasRandomTicks(BlockState state) {
        return true;
    }

    //Behaviors
    public abstract void handleSickness(ServerWorld world, BlockState state, BlockPos pos, Random random);

    //Returns true if max Age
    public boolean attemptGrowth(ServerWorld world, BlockState state, BlockPos pos, Random random) {
        int age = state.get(this.age);
        if (age < this.getMaxAge()){
            int moisture = state.get(Flora.moisture);
            if (moisture > this.consumptionRate){
                world.setBlockState(pos, state
                        .with(Flora.moisture, moisture-this.consumptionRate)
                        .with(this.age, age+1)
                );
            }
            return false;
        }else {
            return true;
        }
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1f;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.DOWN && neighborState.getBlock() instanceof SoilBlock) {
            return state.with(layers, neighborState.get(layers));
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isIn(BlockTags.DIRT);
    }

    @Override
    public int getTint(BlockState state, int tintIndex){
        return -1;
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos().down());
        return getDefaultState().with(layers, state.getBlock() instanceof SoilBlock ? state.get(layers) : 8);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape[7-(state.get(layers)-1)];
    }

    static {
        shape = new VoxelShape[layers.getValues().size()];
        shape[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.0625, 0, 1, 0.1875, 1),
                VoxelShapes.cuboid(0.1875, 0.1875, 0.1875, 0.8125, 0.75, 0.8125)
        );
        shape[1] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.125, 0.125, 0.875, 0, 0.875),
                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.3125, 0.6875)
        );
        shape[2] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.25, 0.125, 0.875, -0.125, 0.875),
                VoxelShapes.cuboid(0.3125, -0.125, 0.3125, 0.6875, 0.1875, 0.6875)
        );
        shape[3] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.375, 0.125, 0.875, -0.25, 0.875),
                VoxelShapes.cuboid(0.3125, -0.25, 0.3125, 0.6875, 0.0625, 0.6875)
        );
        shape[4] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.5, 0.125, 0.875, -0.375, 0.875),
                VoxelShapes.cuboid(0.3125, -0.375, 0.3125, 0.6875, -0.0625, 0.6875)
        );
        shape[5] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.625, 0.125, 0.875, -0.5, 0.875),
                VoxelShapes.cuboid(0.3125, -0.5, 0.3125, 0.6875, -0.1875, 0.6875)
        );
        shape[6] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.75, 0.125, 0.875, -0.625, 0.875),
                VoxelShapes.cuboid(0.3125, -0.625, 0.3125, 0.6875, -0.3125, 0.6875)
        );
        shape[7] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, -0.875, 0.125, 0.875, -0.75, 0.875),
                VoxelShapes.cuboid(0.3125, -0.75, 0.3125, 0.6875, -0.4375, 0.6875)
        );
    }

    private static Settings offset(Settings settings) {
        ((OffsetAccessor)settings).offsetter((state, pos) -> {
            final long l = MathHelper.hashCode(pos.getX(), 0, pos.getZ());
            final float f = 0.25f;
            final double x = MathHelper.clamp(((double)((float)(l & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final double z = MathHelper.clamp(((double)((float)(l >> 8 & 15L) / 15.0F) - 0.5) * 0.5, -f, f);
            final int i = state.get(layers) ;
            return new Vec3d(x,(i-8)*0.125F, z);
        });
        return settings;
    }

    public int getMaxAge() {
        return this.age.getValues().getLast();
    }
}

