package com.wildsregrown.blocks.carpentry.framing;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.VoxelTransform;
import wildsregrown.api.block.render.ITintedBlock;

public class CrateLid extends Block implements ITintedBlock {

    private static final VoxelShape[] SHAPES;
    private static final EnumProperty<Direction> FACING = BlockStateProperties.FACING_HOPPER;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public CrateLid(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.DOWN).setValue(PAINT, LinSeedPaintable.NONE));
    }

    /**
     * Interactions
     */
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (stack.getItem() == Items.AIR){
            player.setItemInHand(InteractionHand.MAIN_HAND, this.asItem().getDefaultInstance());
            world.destroyBlock(pos, false);
            return InteractionResult.SUCCESS;
        }else
        if (stack.getItem() == this.asItem()){
            stack.grow(1);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
            world.destroyBlock(pos, false);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    /**
     * Vanilla constructors
     * @param ctx
     * @return
     */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getClickedFace();
        if (direction == Direction.UP){direction = Direction.DOWN;}
        return defaultBlockState().setValue(FACING, direction);
    }

    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        switch (state.getValue(FACING)){
            case DOWN -> {
                return SHAPES[0];
            }
            case NORTH -> {
                return SHAPES[1];
            }
            case EAST -> {
                return SHAPES[2];
            }
            case SOUTH -> {
                return SHAPES[3];
            }
            case WEST -> {
                return SHAPES[4];
            }
            default -> {
                return Shapes.block();
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING,PAINT);
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }

    static {
        SHAPES = new VoxelShape[5];
        SHAPES[0] = Shapes.box(0, 0, 0, 1, 0.125, 1);
        SHAPES[1] = Shapes.or(
                Shapes.box(0, 0, 0.625, 1, 0.1875, 0.75),
                Shapes.box(0, 0.1875, 0.6875, 1, 0.375, 0.8125),
                Shapes.box(0, 0.5625, 0.8125, 1, 0.75, 0.9375),
                Shapes.box(0, 0.375, 0.75, 1, 0.5625, 0.875),
                Shapes.box(0, 0.75, 0.875, 1, 0.9375, 1),
                Shapes.box(0, 0, 0.5625, 1, 0.125, 0.625)
        );
        SHAPES[2] = VoxelTransform.rotate90(SHAPES[1]);
        SHAPES[3] = VoxelTransform.rotate180(SHAPES[1]);
        SHAPES[4] = VoxelTransform.rotate270(SHAPES[1]);
    }

}
