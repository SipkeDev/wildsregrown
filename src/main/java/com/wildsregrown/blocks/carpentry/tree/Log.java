package com.wildsregrown.blocks.carpentry.tree;

import com.sipke.api.features.botanic.trees.ITreeType;
import com.sipke.api.features.botanic.trees.TreeType;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jspecify.annotations.Nullable;
import wildsregrown.api.block.render.IRenderType;
import wildsregrown.api.block.render.ITintedBlock;

public class Log extends RotatedPillarBlock implements ITreeType, ITintedBlock, IRenderType {

    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public Log(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(PAINT, LinSeedPaintable.NONE).setValue(AXIS, Direction.Axis.Y));
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        super.destroy(world, pos, state);

        MinecraftServer server = world.getServer();
        if (server!=null){
            // Retrieve the saved block data from the server.
        }

        /*
        Box box = Box.of(new Vec3d(pos.getX(),pos.getY(),pos.getZ()),
                32, 32, 32);
        BlockPos.Mutable loc;
        if (state.get(AXIS).isVertical()) {
            for (OrdinalDirection d : OrdinalDirection.values()) {
                loc = pos.mutableCopy().move(0, 1, 0).move(d.getVector());
                for (int i = 0; i < 3; i++) {
                    BlockState branch = world.getBlockState(loc);
                    if (branch.isIn(BlockTags.LEAVES) || (branch.getBlock() instanceof TreeBranch && TreeBranch.toBreak(branch, loc, pos))) {
                        world.breakBlock(loc, true);
                    }
                    loc = loc.move(0, -1, 0);
                }
            }
        }
         */
    }

    @Override
    public void playerDestroy(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(world, player, pos, state, blockEntity, tool);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, PAINT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return defaultBlockState().setValue(AXIS, ctx.getClickedFace().getAxis());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return tintIndex == 1 ? state.getValue(PAINT).getRGB() : -1;
    }

    @Override
    public TreeType getType() {
        return TreeType.trunk;
    }
}
