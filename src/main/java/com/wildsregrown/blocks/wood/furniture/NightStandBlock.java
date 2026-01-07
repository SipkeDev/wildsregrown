package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class NightStandBlock extends CounterChestBlock {

    private static final VoxelShape[] shapes;
    private static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;
    private static final BooleanProperty OPEN = Properties.OPEN;

    public NightStandBlock(Settings settings){
        super(settings.ticksRandomly());
        this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(OPEN, false).with(PAINT, LinSeedPaintable.NONE));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Night Stand");
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
            case NORTH -> {
                return shapes[0];
            }
            case EAST -> {
                return shapes[1];
            }
            case SOUTH -> {
                return shapes[2];
            }
            case WEST -> {
                return shapes[3];
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    static {
        shapes = new VoxelShape[4];
        shapes[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0.125, 0.125, 0.125, 0.875, 1, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.75, 0.25, 0.125, 0.875),
                VoxelShapes.cuboid(0.125, 0, 0.125, 0.25, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.125, 0.875, 0.125, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.875, 0.125, 0.875)
        );
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }

}
