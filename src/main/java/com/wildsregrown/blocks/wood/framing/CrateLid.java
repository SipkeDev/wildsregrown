package com.wildsregrown.blocks.wood.framing;

import com.wildsregrown.blocks.VoxelTransform;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CrateLid extends Block implements ITintedBlock {

    private static final VoxelShape[] SHAPES;
    private static final EnumProperty<Direction> FACING = Properties.HOPPER_FACING;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public CrateLid(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.DOWN).with(PAINT, LinSeedPaintable.NONE));
    }

    /**
     * Interactions
     */
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (stack.getItem() == Items.AIR){
            player.setStackInHand(Hand.MAIN_HAND, this.asItem().getDefaultStack());
            world.breakBlock(pos, false);
            return ActionResult.SUCCESS;
        }else
        if (stack.getItem() == this.asItem()){
            stack.increment(1);
            player.setStackInHand(Hand.MAIN_HAND, stack);
            world.breakBlock(pos, false);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    /**
     * Vanilla constructors
     * @param ctx
     * @return
     */

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getSide();
        if (direction == Direction.UP){direction = Direction.DOWN;}
        return getDefaultState().with(FACING, direction);
    }

    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)){
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
                return VoxelShapes.fullCube();
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING,PAINT);
    }

    static {
        SHAPES = new VoxelShape[5];
        SHAPES[0] = VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1);
        SHAPES[1] = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0, 0.625, 1, 0.1875, 0.75),
                VoxelShapes.cuboid(0, 0.1875, 0.6875, 1, 0.375, 0.8125),
                VoxelShapes.cuboid(0, 0.5625, 0.8125, 1, 0.75, 0.9375),
                VoxelShapes.cuboid(0, 0.375, 0.75, 1, 0.5625, 0.875),
                VoxelShapes.cuboid(0, 0.75, 0.875, 1, 0.9375, 1),
                VoxelShapes.cuboid(0, 0, 0.5625, 1, 0.125, 0.625)
        );
        SHAPES[2] = VoxelTransform.rotate90(SHAPES[1]);
        SHAPES[3] = VoxelTransform.rotate180(SHAPES[1]);
        SHAPES[4] = VoxelTransform.rotate270(SHAPES[1]);
    }

}
