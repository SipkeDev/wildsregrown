package com.wildsregrown.blocks.carpentry.furniture.sitable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Stool extends AbstractStool {

    private static final VoxelShape[] shapes;
    private final int tier;

    public Stool(BlockBehaviour.Properties settings, float height, int tier){
        super(settings, height);
        this.tier = tier;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shapes[tier];
    }

    static {
        shapes = new VoxelShape[3];
        shapes[0] = Shapes.box(0.125, 0.3125, 0.125, 0.875, 0.5, 0.875);
        shapes[1] = Shapes.or(
                Shapes.box(0.625, 0, 0.1875, 0.8125, 0.3125, 0.375),
                Shapes.box(0.1875, 0, 0.1875, 0.375, 0.3125, 0.375),
                Shapes.box(0.625, 0, 0.625, 0.8125, 0.3125, 0.8125),
                Shapes.box(0.1875, 0, 0.625, 0.375, 0.3125, 0.8125),
                Shapes.box(0.125, 0.3125, 0.125, 0.875, 0.5, 0.875)
        );
        shapes[2] = Shapes.empty();
    }

}
