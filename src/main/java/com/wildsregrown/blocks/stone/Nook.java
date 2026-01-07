package com.wildsregrown.blocks.stone;

import com.wildsregrown.blocks.VoxelTransform;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class Nook extends Corner {
    private static final VoxelShape[][][] SHAPES;
    public Nook(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES[state.get(X) ? 1 : 0][state.get(Z) ? 1 : 0][state.get(Y) ? 1 : 0];
    }

    static {
        VoxelShape top = VoxelShapes.union(
                Block.createCuboidShape(8 , 0, 8, 16, 16, 16),
                Block.createCuboidShape(8 , 8, 0, 16, 16, 16),
                Block.createCuboidShape(0 , 8, 8, 16, 16, 16));
        VoxelShape bot = VoxelShapes.union(
                Block.createCuboidShape(8 , 0, 8, 16, 16, 16),
                Block.createCuboidShape(8 , 0, 0, 16,  8, 16),
                Block.createCuboidShape(0 , 0, 8, 16,  8, 16));

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
