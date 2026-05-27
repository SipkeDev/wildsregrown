
package com.wildsregrown.blocks.shapes;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.ScheduledTick;
import org.jspecify.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.shapes.DoorState;

public class Door extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape[] closed;
    private static final VoxelShape[] open;
    private static final VoxelShape[] open_inv;

    private static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorState> PART = WRGProperties.DOOR;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;

    public Door(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(OPEN, false).setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, Boolean.FALSE));
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
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.isEmptyBlock(pos.above());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        boolean bl = !state.getValue(OPEN);
        world.setBlock(pos, state.setValue(OPEN, bl), 10);
        world.playSound(player, pos, bl ? SoundEvents.WOODEN_DOOR_OPEN : SoundEvents.WOODEN_DOOR_CLOSE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.gameEvent(player, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);

        BlockPos blockPos = DoorState.isUpper(state.getValue(PART)) ? pos.below() : pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(state.getBlock())) {
            world.setBlock(blockPos, blockState.setValue(OPEN, bl), 10);
        }

        updateMirror(state, world, pos, bl);

        return InteractionResult.SUCCESS;
    }

    private void updateMirror(BlockState state, Level world, BlockPos pos, boolean bl){
        Direction direction = state.getValue(FACING);
        direction = DoorState.isLeft(state.getValue(PART)) ? direction.getCounterClockWise() : direction.getClockWise();

        BlockPos blockPos = pos.relative(direction);
        WildsRegrown.LOGGER.info(pos + " / " + blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(state.getBlock())) {
            world.setBlock(blockPos, blockState.setValue(OPEN, bl), 10);
        }

        BlockPos blockPos0 = DoorState.isUpper(state.getValue(PART)) ? blockPos.below() : blockPos.above();
        BlockState blockState0 = world.getBlockState(blockPos0);
        if (blockState0.is(state.getBlock())) {
            world.setBlock(blockPos0, blockState0.setValue(OPEN, bl), 10);
        }

    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        FluidState fluidstate = context.getLevel().getFluidState(blockpos);
        Direction direction = context.getHorizontalDirection();

        return defaultBlockState().setValue(PART, isLeft(context) ? DoorState.left_bottom : DoorState.right_bottom).setValue(FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    /**
     * Placement
     */
    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            tickView.getFluidTicks().schedule(ScheduledTick.probe(Fluids.WATER,pos));
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    private boolean isLeft(BlockPlaceContext ctx){
        Vec3 centerPos = ctx.getClickedPos().getCenter();
        Vec3 vec = centerPos.subtract(ctx.getClickLocation());
        if (ctx.getHorizontalDirection().getAxis() == Direction.Axis.X){
            return vec.z < 0;
        }else {
            return vec.x < 0;
        }
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlock(pos.above(), state.setValue(PART, state.getValue(PART) == DoorState.right_bottom ? DoorState.right_top : DoorState.left_top), 3);
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        DoorState doorState = state.getValue(PART);
        boolean top = doorState == DoorState.left_top || doorState == DoorState.right_top;
        BlockPos blockPos = top ? pos.below() : pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(state.getBlock())) {
            BlockState airState = blockState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            world.setBlock(blockPos, airState, 35);
            world.levelEvent(player, 2001, blockPos, Block.getId(blockState));
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(OPEN, PART, FACING, BlockStateProperties.WATERLOGGED);
    }

    /**
     * Shape
     */
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        boolean isOpen = state.getValue(OPEN);
        boolean isLeft = DoorState.isLeft(state.getValue(PART));
        switch (state.getValue(FACING)) {
            case SOUTH -> {return isOpen ? (isLeft ? open[0] : open_inv[0]) : closed[0];}
            case EAST  -> {return isOpen ? (isLeft ? open[1] : open_inv[1]) : closed[1];}
            case NORTH -> {return isOpen ? (isLeft ? open[2] : open_inv[2]) : closed[2];}
            case WEST  -> {return isOpen ? (isLeft ? open[3] : open_inv[3]) : closed[3];}
            default -> {return Shapes.block();}
        }
    }

    static {
        closed = new VoxelShape[4];
        open = new VoxelShape[4];
        open_inv = new VoxelShape[4];

        closed[0] = Shapes.or(
                Shapes.box(0, 0, 0, 1, 1, 0.125)
        );
        closed[1] = VoxelTransform.rotate270(closed[0]);
        closed[2] = VoxelTransform.rotate180(closed[0]);
        closed[3] = VoxelTransform.rotate90(closed[0]);
        open[0] = Shapes.or(
                Shapes.box(0, 0, -0.125, 0.125, 1, 0.875)
        );
        open[1] = VoxelTransform.rotate270(open[0]);
        open[2] = VoxelTransform.rotate180(open[0]);
        open[3] = VoxelTransform.rotate90(open[0]);

        open_inv[0] = VoxelTransform.mirrorX(open[0]);
        open_inv[1] = VoxelTransform.mirrorZ(open[1]);
        open_inv[2] = VoxelTransform.mirrorX(open[2]);
        open_inv[3] = VoxelTransform.mirrorZ(open[3]);
    }

}