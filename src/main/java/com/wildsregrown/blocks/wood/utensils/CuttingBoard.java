
package com.wildsregrown.blocks.wood.utensils;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class CuttingBoard extends Block implements ITintedBlock {

    private static final VoxelShape[] floorShape = new VoxelShape[2];
    private static final VoxelShape[] SideShape = new VoxelShape[4];

    public CuttingBoard(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(ModProperties.LINSEED_PAINT, LinSeedPaintable.NONE).with(Properties.FACING, Direction.NORTH));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ModProperties.LINSEED_PAINT, Properties.FACING);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(Properties.HORIZONTAL_FACING, rotation.rotate(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(Properties.HORIZONTAL_FACING, mirror.apply(state.get(Properties.HORIZONTAL_FACING)));
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction dir = ctx.getPlayerLookDirection();
        Direction hori = ctx.getHorizontalPlayerFacing();
        if (dir == Direction.DOWN || dir == Direction.UP){
            return getDefaultState().with(Properties.FACING, hori.getAxis() == Direction.Axis.X ? Direction.DOWN : Direction.UP);
        }else {
            return getDefaultState().with(Properties.FACING, hori.getOpposite());
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction dir = state.get(Properties.FACING);
        if(dir == Direction.DOWN){
            return floorShape[1];
        }else if (dir == Direction.UP){
            return floorShape[0];
        }else {
            return SideShape[dir.getHorizontalQuarterTurns()];
        }
    }

    static {
        floorShape[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.1875, 1, 0.0625, 0.8125)
        );
        floorShape[1] = VoxelTransform.rotate90(floorShape[0]);
        SideShape[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0.5625, 0, 0.8125, 0.9375, 0.125),
                VoxelShapes.cuboid(0.1875, 0.25, 0.125, 0.8125, 0.625, 0.3125),
                VoxelShapes.cuboid(0.1875, 0, 0.3125, 0.8125, 0.3125, 0.4375)
        );
        SideShape[1] = VoxelTransform.rotate90 (SideShape[0]);
        SideShape[2] = VoxelTransform.rotate180(SideShape[0]);
        SideShape[3] = VoxelTransform.rotate270(SideShape[0]);
    }

}
