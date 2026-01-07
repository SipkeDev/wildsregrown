package com.wildsregrown.blocks.wood.furniture;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class CabinetShelvesBlock extends CounterShelvesBlock {

    private static final VoxelShape[] shapes;

    public CabinetShelvesBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(Properties.HORIZONTAL_FACING)){
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
                VoxelShapes.cuboid(0, 0.875, 0.3125, 1, 1, 1),
                VoxelShapes.cuboid(0.875, 0.125, 0.5, 1, 0.875, 1),
                VoxelShapes.cuboid(0, 0.125, 0.5, 0.125, 0.875, 1),
                VoxelShapes.cuboid(0, 0, 0.4375, 1, 0.125, 1),
                VoxelShapes.cuboid(0.125, 0.125, 0.875, 0.875, 0.875, 1),
                VoxelShapes.cuboid(0.125, 0.4375, 0.5625, 0.875, 0.5625, 0.875)
        );
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }

}
