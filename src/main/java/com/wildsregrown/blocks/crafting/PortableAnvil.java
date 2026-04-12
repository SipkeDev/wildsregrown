package com.wildsregrown.blocks.crafting;

import com.mojang.serialization.MapCodec;
import com.sipke.api.grid.mesh.cell.Cell;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.entities.block.PortableWorkbenchEntity;
import com.wildsregrown.items.tools.Hatchet;
import com.wildsregrown.items.tools.Pickaxe;
import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.recipe.ToolEventInput;
import com.wildsregrown.recipe.ToolEventRecipe;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModTags;
import com.wildsregrown.registries.PortableAnvilScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class PortableAnvil extends FallingBlock {

    public static final MapCodec<PortableAnvil> CODEC = createCodec(PortableAnvil::new);

    private static final VoxelShape shape;

    public PortableAnvil(Settings settings){
        super(settings);
    }

    @Override
    protected MapCodec<? extends FallingBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new PortableAnvilScreenHandler(this, syncId, inventory, ScreenHandlerContext.create(world, pos)), Text.of("Portable Anvil"));
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_ANVIL);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public int getColor(BlockState state, BlockView world, BlockPos pos) {
        return state.getMapColor(world, pos).color;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState();
    }

    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.setHurtEntities(2.0F, 40);
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    static {
        shape = VoxelShapes.union(
                VoxelShapes.cuboid(0.375, 0.125, 0.375, 0.625, 0.5625, 0.625),
                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.125, 0.6875),
                VoxelShapes.cuboid(0.375, 0.6875, 0.375, 0.625, 0.8125, 0.625),
                VoxelShapes.cuboid(0.40625, 0.5625, 0.40625, 0.59375, 0.6875, 0.59375)
        );
    }

}
