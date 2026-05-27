package com.wildsregrown.blocks.crafting;

import com.mojang.serialization.MapCodec;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.entities.block.PortableWorkbenchEntity;
import com.wildsregrown.items.tools.Hatchet;
import com.wildsregrown.items.tools.Pickaxe;
import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.recipe.ToolEventInput;
import com.wildsregrown.recipe.ToolEventRecipe;
import com.wildsregrown.registries.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.registry.defaults.ApiComponents;

import java.util.Optional;

public class PortableWorkBench extends BaseEntityBlock implements ITintedBlock {

    private static final VoxelShape shape;
    private static final EnumProperty<LinSeedPaintable> PAINT = ModProperties.LINSEED_PAINT;

    public PortableWorkBench(BlockBehaviour.Properties settings){
        super(settings);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        PortableWorkbenchEntity entity = (PortableWorkbenchEntity) world.getBlockEntity(pos);
        ItemStack playerStack = player.getMainHandItem();

        world.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());

        //Both empty, Pick up the workbench
        if (entity.isEmpty() && playerStack.isEmpty()){
            player.setItemInHand(InteractionHand.MAIN_HAND, this.asItem().getDefaultInstance());
            entity.setRemoved();
            world.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            handleInteraction(world, pos, state, entity);
            return InteractionResult.SUCCESS;
        }
        //Workbench empty, place material... If craftable...
        else if (entity.isEmpty()){
            if (playerStack.is(ModTags.stone_crafting_materials) || playerStack.is(ModTags.wood_crafting_materials)) {
                entity.setStack(playerStack);
                player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                handleInteraction(world, pos, state, entity);
                return InteractionResult.CONSUME;
            }else {
                return InteractionResult.FAIL;
            }
        }
        //Workbench is full, hand free. Retrieve material
        else if (playerStack.isEmpty() && !entity.isEmpty()){
            player.setItemInHand(InteractionHand.MAIN_HAND, entity.getStack());
            entity.setStack(ItemStack.EMPTY);
            handleInteraction(world, pos, state, entity);
            return InteractionResult.SUCCESS;
        }
        //Tool event handler
        else if (world instanceof ServerLevel serverWorld){
            if (player.getMainHandItem().getComponents().has(ApiComponents.ITEM_STANCE)) {
                RecipeManager recipeManager = serverWorld.recipeAccess();
                ItemStack tool = player.getMainHandItem();
                ItemStack material = entity.getStack();

                if (tool.getItem() instanceof Pickaxe item){
                    WildsRegrown.LOGGER.info(String.valueOf(Pickaxe.Stances.values()[tool.get(ApiComponents.ITEM_STANCE)]));
                }
                if (tool.getItem() instanceof Hatchet item){
                    WildsRegrown.LOGGER.info(String.valueOf(Hatchet.Stances.values()[tool.get(ApiComponents.ITEM_STANCE)]));
                }

                Optional<RecipeHolder<ToolEventRecipe>> match = recipeManager.getRecipeFor(ModRecipes.toolEventType, new ToolEventInput(tool.get(ApiComponents.ITEM_STANCE), tool, material), world);
                if (match.isPresent()) {
                    ItemStack stack = match.get().value().output();
                    stack.setCount(entity.getStack().getCount());
                    entity.setStack(stack);

                    handleInteraction(world, pos, state, entity);
                    return InteractionResult.CONSUME;
                }
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.PASS;
    }

    /**
     * Interactions
     */
    private void handleInteraction(Level world, BlockPos pos, BlockState state, BlockEntity entity){
        entity.setChanged();
        world.sendBlockUpdated(pos, state, state, 2);
        displayInteraction(world, pos);
    }

    private void displayInteraction(Level world, BlockPos pos){
        if (world instanceof ServerLevel serverWorld) {
            serverWorld.sendParticles(ParticleTypes.POOF, pos.getX() + 0.5F, pos.getY() + 1.0, pos.getZ() + 0.5F, 7,0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    /**
      * Vanilla constructors
      */
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(PortableWorkBench::new);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PAINT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PortableWorkbenchEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    static {
        shape = Shapes.or(
                Shapes.box(0, 0.75, 0, 1, 1, 1),
                Shapes.box(0.0625, 0, 0.0625, 0.25, 0.75, 0.25),
                Shapes.box(0.75, 0, 0.0625, 0.9375, 0.75, 0.25),
                Shapes.box(0.0625, 0, 0.75, 0.25, 0.75, 0.9375),
                Shapes.box(0.75, 0, 0.75, 0.9375, 0.75, 0.9375),
                Shapes.box(0.25, 0.125, 0.125, 0.75, 0.25, 0.1875),
                Shapes.box(0.25, 0.125, 0.8125, 0.75, 0.25, 0.875),
                Shapes.box(0.8125, 0.125, 0.25, 0.875, 0.25, 0.75),
                Shapes.box(0.125, 0.125, 0.25, 0.1875, 0.25, 0.75)
        );
    }

    @Override
    public int getTint(BlockState blockState, int i) {
        return blockState.getValue(PAINT).getRGB();
    }
}
