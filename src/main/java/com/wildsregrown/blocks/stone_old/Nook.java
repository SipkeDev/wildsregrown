package com.wildsregrown.blocks.stone_old;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.VoxelTransform;

public class Nook extends Corner {
    private static final VoxelShape[][][] SHAPES;
    public Nook(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES[state.getValue(X) ? 1 : 0][state.getValue(Z) ? 1 : 0][state.getValue(Y) ? 1 : 0];
    }

    static {
        VoxelShape top = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(8 , 8, 0, 16, 16, 16),
                Block.box(0 , 8, 8, 16, 16, 16));
        VoxelShape bot = Shapes.or(
                Block.box(8 , 0, 8, 16, 16, 16),
                Block.box(8 , 0, 0, 16,  8, 16),
                Block.box(0 , 0, 8, 16,  8, 16));

        VoxelShape[] SHAPE_PP = new VoxelShape[]{bot, top};
        VoxelShape[] SHAPE_NP = new VoxelShape[]{VoxelTransform.rotate90 (bot), VoxelTransform.rotate90 (top)};
        VoxelShape[] SHAPE_NN = new VoxelShape[]{VoxelTransform.rotate180(bot), VoxelTransform.rotate180(top)};
        VoxelShape[] SHAPE_PN = new VoxelShape[]{VoxelTransform.rotate270(bot), VoxelTransform.rotate270(top)};

        SHAPES = new VoxelShape[/*X*/][/*Z*/][/*Y*/]{
                {
                        SHAPE_NN,
                        SHAPE_NP
                },
                {
                        SHAPE_PN,
                        SHAPE_PP
                }
        };
    }
}
