
package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.DoorState;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.*;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;
import org.jspecify.annotations.Nullable;

public class Door extends Block implements Waterloggable, ITintedBlock {

    private static final VoxelShape[] closed;
    private static final VoxelShape[] open;
    private static final VoxelShape[] open_inv;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final EnumProperty<DoorState> PART = ModProperties.DOOR;
    private static final BooleanProperty OPEN = Properties.OPEN;

    public Door(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(OPEN, false).with(PAINT, LinSeedPaintable.NONE).with(FACING, Direction.NORTH).with(Properties.WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror.apply(state.get(FACING)));
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.isAir(pos.up());
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        boolean bl = !state.get(OPEN);
        world.setBlockState(pos, state.with(OPEN, bl), 10);
        world.playSound(player, pos, bl ? SoundEvents.BLOCK_WOODEN_DOOR_OPEN : SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
        world.emitGameEvent(player, bl ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);

        BlockPos blockPos = DoorState.isUpper(state.get(PART)) ? pos.down() : pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(state.getBlock())) {
            world.setBlockState(blockPos, blockState.with(OPEN, bl), 10);
        }

        updateMirror(state, world, pos, bl);

        return ActionResult.SUCCESS;
    }

    private void updateMirror(BlockState state, World world, BlockPos pos, boolean bl){
        Direction direction = state.get(FACING);
        direction = DoorState.isLeft(state.get(PART)) ? direction.rotateYCounterclockwise() : direction.rotateYClockwise();

        BlockPos blockPos = pos.offset(direction);
        WildsRegrown.LOGGER.info(pos + " / " + blockPos);
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(state.getBlock())) {
            world.setBlockState(blockPos, blockState.with(OPEN, bl), 10);
        }

        BlockPos blockPos0 = DoorState.isUpper(state.get(PART)) ? blockPos.down() : blockPos.up();
        BlockState blockState0 = world.getBlockState(blockPos0);
        if (blockState0.isOf(state.getBlock())) {
            world.setBlockState(blockPos0, blockState0.with(OPEN, bl), 10);
        }

    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();

        return getDefaultState().with(PART, isLeft(context) ? DoorState.left_bottom : DoorState.right_bottom).with(FACING, direction).with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    /**
     * Placement
     */
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    private boolean isLeft(ItemPlacementContext ctx){
        Vec3d centerPos = ctx.getBlockPos().toCenterPos();
        Vec3d vec = centerPos.subtract(ctx.getHitPos());
        if (ctx.getHorizontalPlayerFacing().getAxis() == Direction.Axis.X){
            return vec.z < 0;
        }else {
            return vec.x < 0;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(PART, state.get(PART) == DoorState.right_bottom ? DoorState.right_top : DoorState.left_top), 3);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        DoorState doorState = state.get(PART);
        boolean top = doorState == DoorState.left_top || doorState == DoorState.right_top;
        BlockPos blockPos = top ? pos.down() : pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(state.getBlock())) {
            BlockState airState = blockState.getFluidState().isOf(Fluids.WATER) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(blockPos, airState, 35);
            world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, OPEN, PART, FACING, Properties.WATERLOGGED);
    }

    /**
     * Shape
     */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean isOpen = state.get(OPEN);
        boolean isLeft = DoorState.isLeft(state.get(PART));
        switch (state.get(FACING)) {
            case SOUTH -> {return isOpen ? (isLeft ? open[0] : open_inv[0]) : closed[0];}
            case EAST  -> {return isOpen ? (isLeft ? open[1] : open_inv[1]) : closed[1];}
            case NORTH -> {return isOpen ? (isLeft ? open[2] : open_inv[2]) : closed[2];}
            case WEST  -> {return isOpen ? (isLeft ? open[3] : open_inv[3]) : closed[3];}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        closed = new VoxelShape[4];
        open = new VoxelShape[4];
        open_inv = new VoxelShape[4];

        closed[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 1, 0.125)
        );
        closed[1] = VoxelTransform.rotate270(closed[0]);
        closed[2] = VoxelTransform.rotate180(closed[0]);
        closed[3] = VoxelTransform.rotate90(closed[0]);
        open[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, -0.125, 0.125, 1, 0.875)
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