
package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class Trapdoor extends Block implements SimpleWaterloggedBlock, ITintedBlock {

    private static final VoxelShape[] closed;
    private static final VoxelShape[] open;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public Trapdoor(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(OPEN, false).setValue(PAINT, LinSeedPaintable.NONE).setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (player.getMainHandItem() == ItemStack.EMPTY){
            world.setBlockAndUpdate(pos, state.setValue(OPEN, !state.getValue(OPEN)));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();

        boolean IsOpen = false;
        if (context.getPlayer() != null){
            IsOpen = context.getPlayer().isShiftKeyDown();
        }
        return defaultBlockState().setValue(OPEN, IsOpen).setValue(FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER,pos));
        }
        return state;
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, OPEN, FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean isOpen = state.getValue(OPEN);
        switch (state.getValue(FACING)) {
            case SOUTH -> {return isOpen ? open[0] : closed[0];}
            case EAST  -> {return isOpen ? open[1] : closed[1];}
            case NORTH -> {return isOpen ? open[2] : closed[2];}
            case WEST  -> {return isOpen ? open[3] : closed[3];}
            default -> {return Shapes.block();}
        }
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        closed = new VoxelShape[4];
        open = new VoxelShape[4];

        closed[0] = VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0, 1, 0.125, 1),
                Shapes.box(0.6875, 0.125, 0.0625, 0.75, 0.1875, 0.125),
                Shapes.box(0.25, 0.125, 0.0625, 0.3125, 0.1875, 0.125),
                Shapes.box(0.25, 0.1875, 0.0625, 0.75, 0.25, 0.125)
                )
        );
        closed[1] = VoxelTransform.rotate90(closed[0]);
        closed[2] = VoxelTransform.rotate180(closed[0]);
        closed[3] = VoxelTransform.rotate270(closed[0]);
        open[0] = VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0, 0.9375, 1, 0.4375, 1.0625),
                Shapes.box(0, 0.4375, 0.8125, 1, 0.75, 0.9375),
                Shapes.box(0, 0.75, 0.6875, 1, 0.9375, 0.8125)
                )
        );
        open[1] = VoxelTransform.rotate90(open[0]);
        open[2] = VoxelTransform.rotate180(open[0]);
        open[3] = VoxelTransform.rotate270(open[0]);
    }

}