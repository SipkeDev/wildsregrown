package com.wildsregrown.blocks.wood.tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sipke.api.features.trees.type.ITreeType;
import com.sipke.api.features.trees.type.TreeType;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.OrdinalDirection;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.OrderedTick;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

public class TreeSource extends BlockWithEntity implements ITreeType {

    private final int key;

    public TreeSource(Settings settings, int key) {
        super(settings);
        this.key = key;
    }

    @Override
    protected void onStateReplaced(BlockState state, ServerWorld world, BlockPos pos, boolean moved) {
        if (moved) {
            if (world.getBlockEntity(pos) instanceof TreeEntity entity){
                entity.fell();
                world.playSound(null, pos, SoundEvents.BLOCK_WOOD_FALL, SoundCategory.AMBIENT, 15F, 5F);
            }
            world.removeBlockEntity(pos);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof TreeEntity tree){
            if (player.isSneaking()) {
                tree.update(world, pos, world.random);
            }else {
                tree.reset(key);
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getBlockEntity(pos) instanceof TreeEntity tree){
            tree.update(world, pos, random);
        }
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return RecordCodecBuilder.mapCodec((instance) -> instance.group(createSettingsCodec()).apply(instance, ctx -> new TreeSource(ctx, key)));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TreeEntity(pos, state);
    }

    @Override
    public TreeType getType() {
        return TreeType.root;
    }

}
