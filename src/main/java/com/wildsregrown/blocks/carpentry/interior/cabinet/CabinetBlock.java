package com.wildsregrown.blocks.carpentry.interior.cabinet;

import com.wildsregrown.blocks.carpentry.interior.counter.CounterChestBlock;
import com.wildsregrown.entities.block.GenericSmallStorageEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;

public class CabinetBlock extends CounterChestBlock {

    private static final VoxelShape[] SHAPES;
    public CabinetBlock(Identifier id, Properties settings) {
        super(id, settings);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GenericSmallStorageEntity(pos, state, "Cabinet");
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)){
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
                return Shapes.block();
            }
        }
    }

    static {
        SHAPES = new VoxelShape[4];
        SHAPES[0] = VoxelTransform.mirrorZ(Shapes.or(
                Shapes.box(0, 0.875, 0.3125, 1, 1, 1),
                Shapes.box(0.875, 0.125, 0.5, 1, 0.875, 1),
                Shapes.box(0, 0.125, 0.5, 0.125, 0.875, 1),
                Shapes.box(0, 0, 0.4375, 1, 0.125, 1),
                Shapes.box(0.125, 0.125, 0.875, 0.875, 0.875, 1),
                Shapes.box(0.125, 0.4375, 0.5625, 0.875, 0.5625, 0.875)
        ));
        SHAPES[1] = VoxelTransform.rotate90(SHAPES[0]);
        SHAPES[2] = VoxelTransform.rotate180(SHAPES[0]);
        SHAPES[3] = VoxelTransform.rotate270(SHAPES[0]);
    }

}
