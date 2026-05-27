package com.wildsregrown.blocks.carpentry.interior.cabinet;

import com.wildsregrown.blocks.carpentry.interior.counter.CounterShelvesBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class CabinetShelvesBlock extends CounterShelvesBlock {

    private static final VoxelShape[] shapes;

    public CabinetShelvesBlock(Identifier id, Properties settings) {
        super(id, settings);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)){
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
                return Shapes.block();
            }
        }
    }

    static {
        shapes = new VoxelShape[4];
        shapes[0] = VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0.875, 0.3125, 1, 1, 1),
                Shapes.box(0.875, 0.125, 0.5, 1, 0.875, 1),
                Shapes.box(0, 0.125, 0.5, 0.125, 0.875, 1),
                Shapes.box(0, 0, 0.4375, 1, 0.125, 1),
                Shapes.box(0.125, 0.125, 0.875, 0.875, 0.875, 1),
                Shapes.box(0.125, 0.4375, 0.5625, 0.875, 0.5625, 0.875)
        ));
        shapes[1] = VoxelTransform.rotate90(shapes[0]);
        shapes[2] = VoxelTransform.rotate180(shapes[0]);
        shapes[3] = VoxelTransform.rotate270(shapes[0]);
    }

}
