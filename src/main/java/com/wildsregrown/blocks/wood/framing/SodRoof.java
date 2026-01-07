package com.wildsregrown.blocks.wood.framing;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;

public class SodRoof extends Block implements Waterloggable, ITintedBlock {

    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape NORTH;
    private static final VoxelShape WEST;
    private static final VoxelShape X;
    private static final VoxelShape Z;
    private static final EnumProperty<Direction> FACING = Properties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public SodRoof(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(PAINT, LinSeedPaintable.NONE).with(ModProperties.HORIZONTAL_CONNECTED, HorizontalConnected.SINGLE).with(FACING, Direction.NORTH));
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        Direction face = state.get(FACING);
        if (face == Direction.DOWN){
            return state.with(FACING, Direction.UP);
        }else if(face == Direction.UP){
            return state.with(FACING, Direction.DOWN);
        }else {
            return state.with(FACING, rotation.rotate(state.get(FACING)));
        }
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() instanceof SodRoof;
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {

        if(isConnectingBlock(currentState)) {
            if (isConnectingBlock(validState) && currentState.get(Properties.FACING) == validState.get(Properties.FACING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a shape property based on the surroundings from the given blockstate and position
     */
    public HorizontalConnected getPartProperty(WorldView worldIn, BlockPos blockpos, Direction direction){
        switch (direction) {
            case DOWN -> direction = Direction.NORTH;
            case UP -> direction = Direction.WEST;
            default -> {}
        }


        BlockState state = worldIn.getBlockState(blockpos);
        BlockState stateLeft = worldIn.getBlockState(blockpos.offset(direction.rotateYClockwise()));
        BlockState stateRight = worldIn.getBlockState(blockpos.offset(direction.rotateYCounterclockwise()));

        boolean left = isConnectingBlock(stateLeft) && isValidFacing(state, stateLeft);
        boolean right = isConnectingBlock(stateRight) && isValidFacing(state, stateRight);

        if(left && right)
        {
            return HorizontalConnected.MIDDLE;
        }
        else if(left)
        {
            return HorizontalConnected.LEFT;
        }
        else if(right)
        {
            return HorizontalConnected.RIGHT;
        }

        return HorizontalConnected.SINGLE;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        BlockPos blockpos = context.getBlockPos();
        Direction direction = context.getHorizontalPlayerFacing();
        direction = context.getPlayer().isSneaking()? (direction.getAxis() == Direction.Axis.Z ? Direction.UP : Direction.DOWN) : direction;
        BlockState blockstate = getDefaultState().with(Properties.FACING, direction);
        return blockstate.with(ModProperties.HORIZONTAL_CONNECTED, getPartProperty(context.getWorld(), blockpos, direction));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return state.with(ModProperties.HORIZONTAL_CONNECTED, getPartProperty(world, pos, state.get(Properties.FACING)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(PAINT, ModProperties.HORIZONTAL_CONNECTED, Properties.FACING);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.FACING)) {
            case SOUTH -> {return SOUTH;}
            case EAST  -> {return EAST ;}
            case NORTH -> {return NORTH;}
            case WEST  -> {return WEST ;}
            case UP -> {return Z;}
            default -> {return X;}
        }
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        if (tintIndex == 0){
            return state.get(PAINT).getRGB();
        }else
        if (tintIndex == 1){
            return Colors.fernGreen;
        }else {
            return -1;
        }
    }

    static {
        SOUTH = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 1, 9),
                Block.createCuboidShape(0, -1, 0, 16, 0, 8),
                Block.createCuboidShape(0, -2, 1, 16, -1, 7),
                Block.createCuboidShape(0, -3, 2, 16, -2, 6),
                Block.createCuboidShape(0, 15, 15, 16, 16, 17),
                Block.createCuboidShape(0, -4, 3, 16, -3, 5),
                Block.createCuboidShape(0, 13, 13, 16, 14, 19),
                Block.createCuboidShape(0, 9, 9, 16, 10, 18),
                Block.createCuboidShape(0, 11, 11, 16, 12, 20),
                Block.createCuboidShape(0, 7, 7, 16, 8, 16),
                Block.createCuboidShape(0, 5, 5, 16, 6, 14),
                Block.createCuboidShape(0, 3, 3, 16, 4, 12),
                Block.createCuboidShape(0, 1, 1, 16, 2, 10),
                Block.createCuboidShape(0, 2, 2, 16, 3, 11),
                Block.createCuboidShape(0, 4, 4, 16, 5, 13),
                Block.createCuboidShape(0, 6, 6, 16, 7, 15),
                Block.createCuboidShape(0, 10, 10, 16, 11, 19),
                Block.createCuboidShape(0, 8, 8, 16, 9, 17),
                Block.createCuboidShape(0, 12, 12, 16, 13, 20),
                Block.createCuboidShape(0, 14, 14, 16, 15, 18)
        );
        NORTH = VoxelTransform.rotate180(SOUTH);
        EAST  = VoxelTransform.rotate270(SOUTH);
        WEST  = VoxelTransform.rotate90(SOUTH);
        X = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 1, 16),
                Block.createCuboidShape(0, -1, 0, 16, 0, 16),
                Block.createCuboidShape(0, -2, 1, 16, -1, 15),
                Block.createCuboidShape(16, -3, 4, 20, 5, 12),
                Block.createCuboidShape(-4, -3, 4, 0, 5, 12),
                Block.createCuboidShape(0, -3, 2, 16, -2, 14),
                Block.createCuboidShape(0, -4, 3, 16, -3, 5),
                Block.createCuboidShape(0, -4, 11, 16, -3, 13),
                Block.createCuboidShape(0, 3, 3, 16, 4, 13),
                Block.createCuboidShape(0, 1, 1, 16, 2, 15),
                Block.createCuboidShape(0, 2, 2, 16, 3, 14),
                Block.createCuboidShape(0, 4, 4, 16, 5, 12)
        );
        Z = VoxelTransform.rotate270(X);
    }
}