package com.wildsregrown.blocks.carpentry;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.render.ITintedBlock;

public class Planks extends Block implements ITintedBlock, SimpleWaterloggedBlock {

    public final static IntegerProperty LAYERS = WRGProperties.QUARTER_LAYERS;
    public final static EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    public final static EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    public final static BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape[] DOWN;
    protected static final VoxelShape[] UP;
    protected static final VoxelShape[] NORTH;
    protected static final VoxelShape[] EAST;
    protected static final VoxelShape[] SOUTH;
    protected static final VoxelShape[] WEST;

    public Planks(Properties settings) {
        super(settings);
        registerDefaultState(this.defaultBlockState().setValue(LAYERS, 4).setValue(PAINT, LinSeedPaintable.NONE).setValue(FACING, Direction.UP).setValue(WATERLOGGED, false));
    }

    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)) {
            case Direction.DOWN -> {
                return DOWN[state.getValue(LAYERS)];
            }
            case Direction.NORTH -> {
                return NORTH[state.getValue(LAYERS)];
            }
            case Direction.SOUTH -> {
                return SOUTH[state.getValue(LAYERS)];
            }
            case Direction.EAST -> {
                return EAST[state.getValue(LAYERS)];
            }
            case Direction.WEST -> {
                return WEST[state.getValue(LAYERS)];
            }
            default -> {
                return UP[state.getValue(LAYERS)];
            }
        }
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {

        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1F;
    }

    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        int i = state.getValue(LAYERS);
        if (context.getItemInHand().is(this.asItem()) && i < 4) {
            if (context.replacingClickedOnBlock()) {
                return context.getClickedFace() == state.getValue(FACING);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        if (player.isShiftKeyDown() && state.getValue(LAYERS) != 1) {
            world.setBlockAndUpdate(pos, state.setValue(LAYERS, state.getValue(LAYERS) - 1));
        } else {
            super.playerDestroy(world, player, pos, state, blockEntity, tool);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        boolean sneak = ctx.getPlayer() != null && ctx.getPlayer().isShiftKeyDown();

        if (blockState.is(this)) {
            ctx.getItemInHand().grow(1);
            int i = blockState.getValue(LAYERS);
            if (!sneak) {
                return defaultBlockState();
            }
            return blockState.setValue(LAYERS, i + 1);
        }

        if (sneak) {
            boolean waterlogged = blockState.is(Blocks.WATER);
            blockState = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(ctx.getClickedFace().getOpposite()));

            if (blockState.is(this) && blockState.getValue(LAYERS) !=4) {
                return blockState.setValue(WATERLOGGED, waterlogged);
            }
            return defaultBlockState().setValue(FACING, ctx.getClickedFace()).setValue(LAYERS, 1).setValue(WATERLOGGED, waterlogged);
        }
        return defaultBlockState().setValue(FACING, ctx.getClickedFace());
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PAINT, LAYERS, WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        UP = new VoxelShape[]{Shapes.empty(),
                Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
                Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
        };
        DOWN = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 12, 0, 16, 16, 16),
                Block.box(0, 8 , 0, 16, 16, 16),
                Block.box(0, 4 , 0, 16, 16, 16),
                Block.box(0, 0 , 0, 16, 16, 16)
        };
        NORTH = new VoxelShape[]{Shapes.empty(),
                Block.box(0, 0, 12, 16, 16, 16),
                Block.box(0, 0, 8, 16, 16, 16),
                Block.box(0, 0, 4, 16, 16, 16),
                Block.box(0, 0, 0, 16, 16, 16)
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
