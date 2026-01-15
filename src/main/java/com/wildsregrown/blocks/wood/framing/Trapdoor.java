
package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class Trapdoor extends Block implements Waterloggable, ITintedBlock {

    private static final VoxelShape[] closed;
    private static final VoxelShape[] open;

    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final BooleanProperty OPEN = Properties.OPEN;

    public Trapdoor(Settings settings) {
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
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (player.getMainHandStack() == ItemStack.EMPTY){
            world.setBlockState(pos, state.with(OPEN, !state.get(OPEN)));
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        FluidState fluidstate = context.getWorld().getFluidState(blockpos);
        Direction direction = context.getHorizontalPlayerFacing();

        boolean IsOpen = false;
        if (context.getPlayer() != null){
            IsOpen = context.getPlayer().isSneaking();
        }
        return getDefaultState().with(OPEN, IsOpen).with(FACING, direction).with(Properties.WATERLOGGED, fluidstate.getProperties() == Fluids.WATER);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(Properties.WATERLOGGED)) {
            tickView.getFluidTickScheduler().scheduleTick(OrderedTick.create(Fluids.WATER,pos));
        }
        return state;
    }

    public FluidState getFluidState(BlockState state)
    {
        return state.get(Properties.WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, OPEN, FACING, Properties.WATERLOGGED);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        boolean isOpen = state.get(OPEN);
        switch (state.get(FACING)) {
            case SOUTH -> {return isOpen ? open[0] : closed[0];}
            case EAST  -> {return isOpen ? open[1] : closed[1];}
            case NORTH -> {return isOpen ? open[2] : closed[2];}
            case WEST  -> {return isOpen ? open[3] : closed[3];}
            default -> {return VoxelShapes.fullCube();}
        }
    }

    static {
        closed = new VoxelShape[4];
        open = new VoxelShape[4];

        closed[0] = VoxelTransform.mirrorZ(VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1),
                VoxelShapes.cuboid(0.6875, 0.125, 0.0625, 0.75, 0.1875, 0.125),
                VoxelShapes.cuboid(0.25, 0.125, 0.0625, 0.3125, 0.1875, 0.125),
                VoxelShapes.cuboid(0.25, 0.1875, 0.0625, 0.75, 0.25, 0.125)
                )
        );
        closed[1] = VoxelTransform.rotate90(closed[0]);
        closed[2] = VoxelTransform.rotate180(closed[0]);
        closed[3] = VoxelTransform.rotate270(closed[0]);
        open[0] = VoxelTransform.mirrorZ(VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.9375, 1, 0.4375, 1.0625),
                VoxelShapes.cuboid(0, 0.4375, 0.8125, 1, 0.75, 0.9375),
                VoxelShapes.cuboid(0, 0.75, 0.6875, 1, 0.9375, 0.8125)
                )
        );
        open[1] = VoxelTransform.rotate90(open[0]);
        open[2] = VoxelTransform.rotate180(open[0]);
        open[3] = VoxelTransform.rotate270(open[0]);
    }

}