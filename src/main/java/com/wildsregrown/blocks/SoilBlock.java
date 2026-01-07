package com.wildsregrown.blocks;

import com.mojang.serialization.MapCodec;
import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.sipke.math.MathUtil;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

import static com.wildsregrown.blocks.render.TintUtil.buildBlendMap;

/**
 * Overgrown represents the density of the overgrown growth with 0 being empty and 5 being filled
 * MOISTURE represents the remaining moisture left in the soil block. It's defined with a 0-16 integer, or inaccurate 0-1 float.
 */
public class SoilBlock extends FallingBlock implements Waterloggable, ITintedBlock {

    public static final IntProperty LAYERS = ModProperties.LAYERS;
    public static final IntProperty OVERGROWN = ModProperties.OVERGROWN;
    public static final IntProperty MOISTURE = ModProperties.MOISTURE;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    protected static final VoxelShape[] LAYERS_TO_SHAPE;
    protected static int[] rgb;

    public SoilBlock(AbstractBlock.Settings settings) {
        super(settings.ticksRandomly());
        this.setDefaultState((this.stateManager.getDefaultState()).with(LAYERS, 8).with(OVERGROWN, 0).with(MOISTURE, 4).with(WATERLOGGED, false));
    }

    public boolean hasRandomTicks(BlockState state) {
        return !state.get(WATERLOGGED);
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Dice.d6(random) == 1) {
            grow(state, world, pos, random);
        }else {
            handleMoisture(state, world, pos, random);
        }
    }

    private void grow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int overgrown = state.get(OVERGROWN);
        if (overgrown != 0 && overgrown < 5){
            world.setBlockState(pos, state.with(OVERGROWN, MathUtil.min(5, overgrown+1)));
        }
    }

    private void handleMoisture(BlockState state, ServerWorld world, BlockPos pos, Random random){
        if (world.isRaining() || world.isThundering()){
            int moisture = MathUtil.min(state.get(MOISTURE)+3, 16);
            world.setBlockState(pos, state.with(MOISTURE, moisture));
        }else if (Dice.d20(random) == 1){
            int moisture = MathUtil.max(state.get(MOISTURE)-1, 0);
            world.setBlockState(pos, state.with(MOISTURE, moisture));
        }
    }


    //Fun gimick
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (world instanceof ServerWorld) {
            int over;
            if (fallDistance < 0.5F && entity instanceof LivingEntity) {
                over = state.get(OVERGROWN)-1;
            }else {
                over = state.get(OVERGROWN)-2;
            }
            world.setBlockState(pos, state.with(OVERGROWN, MathUtil.max(over, 0)));
        }

        super.onLandedUpon(world, state, pos, entity, fallDistance);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return null;
    }

    @Override
    public void onBlockBreakStart(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        BlockPos loc = pos.up();
        if (world.getBlockState(loc).getBlock() instanceof Flora){
            world.breakBlock(loc,true, player);
        }
        super.onBlockBreakStart(state, world, pos, player);
    }


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public boolean canReplace(BlockState state, ItemPlacementContext context) {

        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 8) {
            if (context.canReplaceExisting()) {
                return context.getSide() == Direction.UP;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (player.isSneaking() && state.get(LAYERS) != 1) {
            world.setBlockState(pos, state.with(LAYERS, state.get(LAYERS) - 1));
        } else {
            super.afterBreak(world, player, pos, state, blockEntity, tool);
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        boolean sneak = ctx.getPlayer() != null && ctx.getPlayer().isSneaking();

        if (blockState.isOf(this)) {
            ctx.getStack().increment(1);
            int i = blockState.get(LAYERS);
            if (!sneak) {
                return getDefaultState();
            }
            return blockState.with(LAYERS, i + 1);
        }

        if (sneak) {
            blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()));
            if (blockState.isOf(this) && blockState.get(LAYERS) !=8) {
                return blockState;
            }
            return getDefaultState().with(LAYERS, 1);
        }
        return getDefaultState();
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, OVERGROWN, MOISTURE, WATERLOGGED);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (direction == Direction.UP && !neighborState.getCollisionShape(world, neighborPos).isEmpty()) {
                return state.with(ModProperties.LAYERS, 8);
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction,  neighborPos, neighborState, random);
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return Colors.darkLavender;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    static {
        LAYERS_TO_SHAPE = new VoxelShape[]{VoxelShapes.empty(), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0), 
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)};

        //Set colorfull life
        int dry = Color.decode("#c8b478").getRGB();
        int temperate = Color.decode("#748937").getRGB();
        int wet = Color.decode("#93ad46").getRGB();

        rgb = buildBlendMap(temperate, dry, wet, MOISTURE.getValues().size());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 0){
            return rgb[MathUtil.clamp(state.get(MOISTURE)-1, 0, rgb.length)];
        }
        return -1;
    }
}
