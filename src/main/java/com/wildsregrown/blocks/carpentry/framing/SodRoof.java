package com.wildsregrown.blocks.carpentry.framing;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.connecting.HorizontalConnected;
import wildsregrown.api.block.render.IRenderType;
import wildsregrown.api.block.render.ITintedBlock;

public class SodRoof extends Block implements SimpleWaterloggedBlock, ITintedBlock, IRenderType {

    private static final VoxelShape SOUTH;
    private static final VoxelShape EAST;
    private static final VoxelShape NORTH;
    private static final VoxelShape WEST;
    private static final VoxelShape X;
    private static final VoxelShape Z;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public SodRoof(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(WRGProperties.HORIZONTAL_CONNECTED, HorizontalConnected.SINGLE).setValue(FACING, Direction.NORTH));
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        Direction face = state.getValue(FACING);
        if (face == Direction.DOWN){
            return state.setValue(FACING, Direction.UP);
        }else if(face == Direction.UP){
            return state.setValue(FACING, Direction.DOWN);
        }else {
            return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
        }
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        return false;
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    public boolean isConnectingBlock(BlockState state) {
        return state.getBlock() instanceof SodRoof;
    }

    public boolean isValidFacing(BlockState currentState, BlockState validState) {

        if(isConnectingBlock(currentState)) {
            if (isConnectingBlock(validState) && currentState.getValue(BlockStateProperties.FACING) == validState.getValue(BlockStateProperties.FACING)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a shape property based on the surroundings from the given blockstate and position
     */
    public HorizontalConnected getPartProperty(LevelReader worldIn, BlockPos blockpos, Direction direction){
        switch (direction) {
            case DOWN -> direction = Direction.NORTH;
            case UP -> direction = Direction.WEST;
            default -> {}
        }


        BlockState state = worldIn.getBlockState(blockpos);
        BlockState stateLeft = worldIn.getBlockState(blockpos.relative(direction.getClockWise()));
        BlockState stateRight = worldIn.getBlockState(blockpos.relative(direction.getCounterClockWise()));

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
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockPos blockpos = context.getClickedPos();
        Direction direction = context.getHorizontalDirection();
        direction = context.getPlayer().isShiftKeyDown()? (direction.getAxis() == Direction.Axis.Z ? Direction.UP : Direction.DOWN) : direction;
        BlockState blockstate = defaultBlockState().setValue(BlockStateProperties.FACING, direction);
        return blockstate.setValue(WRGProperties.HORIZONTAL_CONNECTED, getPartProperty(context.getLevel(), blockpos, direction));
    }

    @Override
    public BlockState updateShape(BlockState state, LevelReader world, ScheduledTickAccess tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {
        return state.setValue(WRGProperties.HORIZONTAL_CONNECTED, getPartProperty(world, pos, state.getValue(BlockStateProperties.FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(PAINT, WRGProperties.HORIZONTAL_CONNECTED, BlockStateProperties.FACING);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.FACING)) {
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
            return state.getValue(PAINT).getRGB();
        }else
        if (tintIndex == 1){
            return Colors.fernGreen;
        }else {
            return -1;
        }
    }

    static {
        SOUTH = Shapes.or(
                Block.box(0, 0, 0, 16, 1, 9),
                Block.box(0, -1, 0, 16, 0, 8),
                Block.box(0, -2, 1, 16, -1, 7),
                Block.box(0, -3, 2, 16, -2, 6),
                Block.box(0, 15, 15, 16, 16, 17),
                Block.box(0, -4, 3, 16, -3, 5),
                Block.box(0, 13, 13, 16, 14, 19),
                Block.box(0, 9, 9, 16, 10, 18),
                Block.box(0, 11, 11, 16, 12, 20),
                Block.box(0, 7, 7, 16, 8, 16),
                Block.box(0, 5, 5, 16, 6, 14),
                Block.box(0, 3, 3, 16, 4, 12),
                Block.box(0, 1, 1, 16, 2, 10),
                Block.box(0, 2, 2, 16, 3, 11),
                Block.box(0, 4, 4, 16, 5, 13),
                Block.box(0, 6, 6, 16, 7, 15),
                Block.box(0, 10, 10, 16, 11, 19),
                Block.box(0, 8, 8, 16, 9, 17),
                Block.box(0, 12, 12, 16, 13, 20),
                Block.box(0, 14, 14, 16, 15, 18)
        );
        NORTH = VoxelTransform.rotate180(SOUTH);
        EAST  = VoxelTransform.rotate270(SOUTH);
        WEST  = VoxelTransform.rotate90(SOUTH);
        X = Shapes.or(
                Block.box(0, 0, 0, 16, 1, 16),
                Block.box(0, -1, 0, 16, 0, 16),
                Block.box(0, -2, 1, 16, -1, 15),
                Block.box(16, -3, 4, 20, 5, 12),
                Block.box(-4, -3, 4, 0, 5, 12),
                Block.box(0, -3, 2, 16, -2, 14),
                Block.box(0, -4, 3, 16, -3, 5),
                Block.box(0, -4, 11, 16, -3, 13),
                Block.box(0, 3, 3, 16, 4, 13),
                Block.box(0, 1, 1, 16, 2, 15),
                Block.box(0, 2, 2, 16, 3, 14),
                Block.box(0, 4, 4, 16, 5, 12)
        );
        Z = VoxelTransform.rotate270(X);
    }
}