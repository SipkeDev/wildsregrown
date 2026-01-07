package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class Cabinetblock extends CounterChestBlock {

    private static final VoxelShape[] SHAPES;

    public Cabinetblock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Cabinet");
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.HORIZONTAL_FACING)){
            case NORTH -> {
                return SHAPES[0];
            }
            case EAST -> {
                return SHAPES[1];
            }
            case SOUTH -> {
                return SHAPES[2];
            }
            case WEST -> {
                return SHAPES[3];
            }
            default -> {
                return VoxelShapes.fullCube();
            }
        }
    }

    static {
        SHAPES = new VoxelShape[4];
        SHAPES[0] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0.875, 0.125, 0.5, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.125, 0.5, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0, 0, 0.4375, 1, 0.125, 1),
                VoxelShapes.cuboid(0.125, 0.125, 0.875, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.4375, 0.5625, 0.875, 0.5625, 0.875)
        );
        SHAPES[1] = VoxelTransform.rotate90(SHAPES[0]);
        SHAPES[2] = VoxelTransform.rotate180(SHAPES[0]);
        SHAPES[3] = VoxelTransform.rotate270(SHAPES[0]);
    }

}
