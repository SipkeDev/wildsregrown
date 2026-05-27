package com.wildsregrown.blocks.carpentry.tree;

import com.sipke.api.features.botanic.trees.ITreeType;
import com.sipke.api.features.botanic.trees.TreeType;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import com.wildsregrown.blocks.properties.old_branch.Verticality;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TreeBranch extends Block implements ITreeType {

    public TreeBranch(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(ModProperties.DIRECTIONS, OrdinalDirection.N).setValue(Verticality.VERTICALITY, Verticality.LEVEL));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
            default -> {return state;}
            case COUNTERCLOCKWISE_90 -> {return state.setValue(ModProperties.DIRECTIONS, state.getValue(ModProperties.DIRECTIONS).cClockwise().cClockwise());}
            case CLOCKWISE_90        -> {return state.setValue(ModProperties.DIRECTIONS, state.getValue(ModProperties.DIRECTIONS).clockwise().clockwise());}
            case CLOCKWISE_180       -> {return state.setValue(ModProperties.DIRECTIONS, state.getValue(ModProperties.DIRECTIONS).flip());}
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
        default -> {return state;}
        case FRONT_BACK -> {return state.setValue(ModProperties.DIRECTIONS, state.getValue(ModProperties.DIRECTIONS).mirrorZ());}
        case LEFT_RIGHT -> {return state.setValue(ModProperties.DIRECTIONS, state.getValue(ModProperties.DIRECTIONS).mirrorX());}
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ModProperties.DIRECTIONS, Verticality.VERTICALITY);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return Block.box(5f, 5f, 5f, 11f, 11f, 11f);
    }

    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.getShape(state, world, pos, context);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel world, BlockPos pos, boolean moved) {
        super.affectNeighborsAfterRemoval(state, world, pos, moved);
        OrdinalDirection d = state.getValue(ModProperties.DIRECTIONS);
        BlockPos.MutableBlockPos loc;

        for (Vec3i v : new Vec3i[]{
                d.getVector(),
                d.clockwise().getVector(),
                d.clockwise().clockwise().getVector(),
                d.cClockwise().getVector(),
                d.cClockwise().cClockwise().getVector(),
        }) {
            loc = pos.mutable().move(0, 1, 0).move(v);
            for (int i = 0; i < 3; i++) {
                BlockState branch = world.getBlockState(loc);
                if (branch.is(BlockTags.LEAVES) || (branch.getBlock() instanceof TreeBranch && toBreak(branch, loc, pos))) {
                    world.destroyBlock(loc, true);
                }
                loc = loc.move(0, -1, 0);
            }
        }
    }

    public static boolean toBreak(BlockState state, BlockPos pos, BlockPos parent) {
        Vec3i vec = state.getValue(ModProperties.DIRECTIONS).getVector().multiply(-1);

        switch (state.getValue(Verticality.VERTICALITY)) {
            case DOWN -> vec = vec.above();
            case UP   -> vec = vec.below();
            default   -> {}
        }
        return pos.offset(vec).equals(parent);
    }


    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction d = ctx.getClickedFace();
        BlockState state = this.defaultBlockState();
        Player player = ctx.getPlayer();
        boolean sneak = player.isShiftKeyDown();

        if (d.getAxis().isHorizontal() && !sneak) {
            return state.setValue(Verticality.VERTICALITY, Verticality.LEVEL).setValue(ModProperties.DIRECTIONS, OrdinalDirection.getOrdinal(d));
        }

        switch(Math.round(player.getYRot()/45)) {
            default -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.S);
            case -3 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.SW);
            case -2 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.W);
            case -1 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.NW);
            case  0 -> {}
            case  1 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.NE);
            case  2 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.E);
            case  3 -> state = state.setValue(ModProperties.DIRECTIONS, OrdinalDirection.SE);
        }

        return state.setValue(Verticality.VERTICALITY, sneak ? Verticality.LEVEL : d == Direction.UP ? Verticality.UP : Verticality.DOWN);
    }
    protected int getLightBlock(BlockState state) {
        return 1;
    }

    @Override
    public TreeType getType() {
        return TreeType.branch;
    }
}