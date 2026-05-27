package com.wildsregrown.items;

import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import java.util.Objects;

public class Torch extends BlockItem {

    public Torch(Properties settings) {
        super(ModBlocks.torch, settings);
    }

    /**
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if(stack.get(ModComponents.FUEL) != null) {
            tooltip.add(Text.literal("Fuel remaining: " + stack.get(ModComponents.FUEL)));
        }
        if(stack.get(ModComponents.LIT) != null) {
            tooltip.add(Text.literal("Lit: " + stack.get(ModComponents.LIT)));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
    */

    public InteractionResult useOn(UseOnContext context) {
        InteractionResult actionResult = this.place(new BlockPlaceContext(context));
        return !actionResult.consumesAction() && context.getItemInHand().has(DataComponents.CONSUMABLE) ? super.use(context.getLevel(), context.getPlayer(), context.getHand()) : actionResult;
    }

    public InteractionResult place(BlockPlaceContext context) {

        ItemStack stack = context.getItemInHand();
        BlockState blockState = ModBlocks.torch.getStateForPlacement(context);

        if (blockState == null) {
            return InteractionResult.FAIL;
        } else if (canPlace(context, blockState)){
            blockState = blockState.setValue(ModProperties.TORCH_FUEL, stack.get(ModComponents.FUEL)).setValue(BlockStateProperties.LIT, stack.get(ModComponents.LIT));
            if (!this.placeBlock(context, blockState)) {
                return InteractionResult.FAIL;
            } else {
                Objects.requireNonNull(context.getPlayer()).setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }else {
            return InteractionResult.PASS;
        }
    }

    protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
        return context.getLevel().setBlock(context.getClickedPos(), state, 11);
    }

    protected boolean canPlace(BlockPlaceContext context, BlockState state) {
        Player playerEntity = context.getPlayer();
        CollisionContext shapeContext = playerEntity == null ? CollisionContext.empty() : CollisionContext.of(playerEntity);
        return state.canSurvive(context.getLevel(), context.getClickedPos()) && context.getLevel().isUnobstructed(state, context.getClickedPos(), shapeContext);
    }

}
