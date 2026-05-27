package com.wildsregrown.blocks.crafting;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.registries.PortableAnvilScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PortableAnvil extends FallingBlock {

    public static final MapCodec<PortableAnvil> CODEC = simpleCodec(PortableAnvil::new);

    private static final VoxelShape shape;

    public PortableAnvil(Properties settings){
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new PortableAnvilScreenHandler(this, syncId, inventory, ContainerLevelAccess.create(world, pos)), Component.nullToEmpty("Portable Anvil"));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (!world.isClientSide()) {
            player.openMenu(state.getMenuProvider(world, pos));
            player.awardStat(Stats.INTERACT_WITH_ANVIL);
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter world, BlockPos pos) {
        return state.getMapColor(world, pos).col;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    protected void falling(FallingBlockEntity entity) {
        entity.setHurtsEntities(2.0F, 40);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    static {
        shape = Shapes.or(
                Shapes.box(0.375, 0.125, 0.375, 0.625, 0.5625, 0.625),
                Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875),
                Shapes.box(0.375, 0.6875, 0.375, 0.625, 0.8125, 0.625),
                Shapes.box(0.40625, 0.5625, 0.40625, 0.59375, 0.6875, 0.59375)
        );
    }

}
