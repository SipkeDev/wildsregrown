package com.wildsregrown.blocks.wood;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Planks extends Block implements ITintedBlock, Waterloggable {

    public final static IntProperty LAYERS = ModProperties.QUARTER_LAYERS;
    public final static EnumProperty<Direction> FACING = Properties.FACING;
    public final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    public final static BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    protected static final VoxelShape[] DOWN;
    protected static final VoxelShape[] UP;
    protected static final VoxelShape[] NORTH;
    protected static final VoxelShape[] EAST;
    protected static final VoxelShape[] SOUTH;
    protected static final VoxelShape[] WEST;

    public Planks(Settings settings) {
        super(settings);
        setDefaultState(this.getDefaultState().with(LAYERS, 4).with(PAINT, LinSeedPaintable.NONE).with(FACING, Direction.UP).with(WATERLOGGED, false));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case Direction.DOWN -> {
                return DOWN[state.get(LAYERS)];
            }
            case Direction.NORTH -> {
                return NORTH[state.get(LAYERS)];
            }
            case Direction.SOUTH -> {
                return SOUTH[state.get(LAYERS)];
            }
            case Direction.EAST -> {
                return EAST[state.get(LAYERS)];
            }
            case Direction.WEST -> {
                return WEST[state.get(LAYERS)];
            }
            default -> {
                return UP[state.get(LAYERS)];
            }
        }
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {

        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1F;
    }

    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        int i = state.get(LAYERS);
        if (context.getStack().isOf(this.asItem()) && i < 4) {
            if (context.canReplaceExisting()) {
                return context.getSide() == state.get(FACING);
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
            boolean waterlogged = blockState.isOf(Blocks.WATER);
            blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()));

            if (blockState.isOf(this) && blockState.get(LAYERS) !=4) {
                return blockState.with(WATERLOGGED, waterlogged);
            }
            return getDefaultState().with(FACING, ctx.getSide()).with(LAYERS, 1).with(WATERLOGGED, waterlogged);
        }
        return getDefaultState().with(FACING, ctx.getSide());
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT, LAYERS, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    static {
        UP = new VoxelShape[]{VoxelShapes.empty(),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };
        DOWN = new VoxelShape[]{VoxelShapes.empty(),
                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                Block.createCuboidShape(0, 8 , 0, 16, 16, 16),
                Block.createCuboidShape(0, 4 , 0, 16, 16, 16),
                Block.createCuboidShape(0, 0 , 0, 16, 16, 16)
        };
        NORTH = new VoxelShape[]{VoxelShapes.empty(),
                Block.createCuboidShape(0, 0, 12, 16, 16, 16),
                Block.createCuboidShape(0, 0, 8, 16, 16, 16),
                Block.createCuboidShape(0, 0, 4, 16, 16, 16),
                Block.createCuboidShape(0, 0, 0, 16, 16, 16)
        };
        SOUTH = new VoxelShape[NORTH.length];
        for (int i = 0; i < SOUTH.length; i++) {
            SOUTH[i] = VoxelTransform.rotate180(NORTH[i]);
        }
        EAST = new VoxelShape[NORTH.length];
        for (int i = 0; i < EAST.length; i++) {
            EAST[i] = VoxelTransform.rotate90(NORTH[i]);
        }
        WEST = new VoxelShape[NORTH.length];
        for (int i = 0; i < WEST.length; i++) {
            WEST[i] = VoxelTransform.rotate270(NORTH[i]);
        }

    }
}
