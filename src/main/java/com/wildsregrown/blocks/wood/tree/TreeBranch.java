package com.wildsregrown.blocks.wood.tree;

import com.sipke.api.features.trees.type.ITreeType;
import com.sipke.api.features.trees.type.TreeType;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import com.wildsregrown.blocks.properties.Verticality;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TreeBranch extends Block implements ITreeType {

    public TreeBranch(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(OrdinalDirection.DIRECTIONS, OrdinalDirection.N).with(Verticality.VERTICALITY, Verticality.LEVEL));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch (rotation) {
            default -> {return state;}
            case COUNTERCLOCKWISE_90 -> {return state.with(OrdinalDirection.DIRECTIONS, state.get(OrdinalDirection.DIRECTIONS).cClockwise().cClockwise());}
            case CLOCKWISE_90        -> {return state.with(OrdinalDirection.DIRECTIONS, state.get(OrdinalDirection.DIRECTIONS).clockwise().clockwise());}
            case CLOCKWISE_180       -> {return state.with(OrdinalDirection.DIRECTIONS, state.get(OrdinalDirection.DIRECTIONS).flip());}
        }
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        switch (mirror) {
        default -> {return state;}
        case FRONT_BACK -> {return state.with(OrdinalDirection.DIRECTIONS, state.get(OrdinalDirection.DIRECTIONS).mirrorZ());}
        case LEFT_RIGHT -> {return state.with(OrdinalDirection.DIRECTIONS, state.get(OrdinalDirection.DIRECTIONS).mirrorX());}
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OrdinalDirection.DIRECTIONS, Verticality.VERTICALITY);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return Block.createCuboidShape(5f, 5f, 5f, 11f, 11f, 11f);
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.getOutlineShape(state, world, pos, context);
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        super.onStateReplaced(state, world, pos, moved);
        OrdinalDirection d = state.get(OrdinalDirection.DIRECTIONS);
        BlockPos.Mutable loc;

        for (Vec3i v : new Vec3i[]{
                d.getVector(),
                d.clockwise().getVector(),
                d.clockwise().clockwise().getVector(),
                d.cClockwise().getVector(),
                d.cClockwise().cClockwise().getVector(),
        }) {
            loc = pos.mutableCopy().move(0, 1, 0).move(v);
            for (int i = 0; i < 3; i++) {
                BlockState branch = world.getBlockState(loc);
                if (branch.isIn(BlockTags.LEAVES) || (branch.getBlock() instanceof TreeBranch && toBreak(branch, loc, pos))) {
                    world.breakBlock(loc, true);
                }
                loc = loc.move(0, -1, 0);
            }
        }
    }

    public static boolean toBreak(BlockState state, BlockPos pos, BlockPos parent) {
        Vec3i vec = state.get(OrdinalDirection.DIRECTIONS).getVector().multiply(-1);

        switch (state.get(Verticality.VERTICALITY)) {
            case DOWN -> vec = vec.up();
            case UP   -> vec = vec.down();
            default   -> {}
        }
        return pos.add(vec).equals(parent);
    }


    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction d = ctx.getSide();
        BlockState state = this.getDefaultState();
        PlayerEntity player = ctx.getPlayer();
        boolean sneak = player.isSneaking();

        if (d.getAxis().isHorizontal() && !sneak) {
            return state.with(Verticality.VERTICALITY, Verticality.LEVEL).with(OrdinalDirection.DIRECTIONS, OrdinalDirection.getOrdinal(d));
        }

        switch(Math.round(player.getYaw()/45)) {
            default -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.S);
            case -3 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.SW);
            case -2 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.W);
            case -1 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.NW);
            case  0 -> {}
            case  1 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.NE);
            case  2 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.E);
            case  3 -> state = state.with(OrdinalDirection.DIRECTIONS, OrdinalDirection.SE);
        }

        return state.with(Verticality.VERTICALITY, sneak ? Verticality.LEVEL : d == Direction.UP ? Verticality.UP : Verticality.DOWN);
    }
    protected int getOpacity(BlockState state) {
        return 1;
    }

    @Override
    public TreeType getType() {
        return TreeType.branch;
    }
}