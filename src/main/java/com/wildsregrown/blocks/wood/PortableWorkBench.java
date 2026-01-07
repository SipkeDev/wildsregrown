package com.wildsregrown.blocks.wood;

import com.mojang.serialization.MapCodec;
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
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PortableWorkBench extends BlockWithEntity implements ITintedBlock {

    private static final VoxelShape shape;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public PortableWorkBench(AbstractBlock.Settings settings){
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        PortableWorkbenchEntity entity = (PortableWorkbenchEntity) world.getBlockEntity(pos);
        ItemStack playerStack = player.getMainHandStack();

        world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());

        //Both empty, Pick up the workbench
        if (entity.isEmpty() && playerStack.isEmpty()){
            player.setStackInHand(Hand.MAIN_HAND, this.asItem().getDefaultStack());
            entity.markRemoved();
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
            handleInteraction(world, pos, state, entity);
            return ActionResult.SUCCESS;
        }
        //Workbench empty, place material... If craftable...
        else if (entity.isEmpty()){
            if (playerStack.isIn(ModTags.stone_crafting_materials) || playerStack.isIn(ModTags.wood_crafting_materials)) {
                entity.setStack(playerStack);
                player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                handleInteraction(world, pos, state, entity);
                return ActionResult.CONSUME;
            }else {
                return ActionResult.FAIL;
            }
        }
        //Workbench is full, hand free. Retrieve material
        else if (playerStack.isEmpty() && !entity.isEmpty()){
            player.setStackInHand(Hand.MAIN_HAND, entity.getStack());
            entity.setStack(ItemStack.EMPTY);
            handleInteraction(world, pos, state, entity);
            return ActionResult.SUCCESS;
        }
        //Tool event handler
        else if (world instanceof ServerWorld serverWorld){
            if (player.getMainHandStack().getComponents().contains(ModComponents.ITEM_STANCE)) {
                ServerRecipeManager recipeManager = serverWorld.getRecipeManager();
                ItemStack tool = player.getMainHandStack();
                ItemStack material = entity.getStack();

                if (tool.getItem() instanceof Pickaxe item){
                    WildsRegrown.LOGGER.info(String.valueOf(Pickaxe.Stances.values()[tool.get(ModComponents.ITEM_STANCE)]));
                }
                if (tool.getItem() instanceof Hatchet item){
                    WildsRegrown.LOGGER.info(String.valueOf(Hatchet.Stances.values()[tool.get(ModComponents.ITEM_STANCE)]));
                }

                Optional<RecipeEntry<ToolEventRecipe>> match = recipeManager.getFirstMatch(ModRecipes.toolEventType, new ToolEventInput(tool.get(ModComponents.ITEM_STANCE), tool, material), world);
                if (match.isPresent()) {
                    ItemStack stack = match.get().value().output();
                    stack.setCount(entity.getStack().getCount());
                    entity.setStack(stack);

                    handleInteraction(world, pos, state, entity);
                    return ActionResult.CONSUME;
                }
            }
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }

    /**
     * Interactions
     */
    private void handleInteraction(World world, BlockPos pos, BlockState state, BlockEntity entity){
        entity.markDirty();
        world.updateListeners(pos, state, state, 2);
        displayInteraction(world, pos);
    }

    private void displayInteraction(World world, BlockPos pos){
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(ParticleTypes.POOF, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7,0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    /**
      * Vanilla constructors
      */
    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(PortableWorkBench::new);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PAINT);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
        return getDefaultState();
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PortableWorkbenchEntity(pos, state);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    static {
        shape = VoxelShapes.union(
                VoxelShapes.cuboid(0, 0.75, 0, 1, 1, 1),
                VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.25, 0.75, 0.25),
                VoxelShapes.cuboid(0.75, 0, 0.0625, 0.9375, 0.75, 0.25),
                VoxelShapes.cuboid(0.0625, 0, 0.75, 0.25, 0.75, 0.9375),
                VoxelShapes.cuboid(0.75, 0, 0.75, 0.9375, 0.75, 0.9375),
                VoxelShapes.cuboid(0.25, 0.125, 0.125, 0.75, 0.25, 0.1875),
                VoxelShapes.cuboid(0.25, 0.125, 0.8125, 0.75, 0.25, 0.875),
                VoxelShapes.cuboid(0.8125, 0.125, 0.25, 0.875, 0.25, 0.75),
                VoxelShapes.cuboid(0.125, 0.125, 0.25, 0.1875, 0.25, 0.75)
        );
    }

}
