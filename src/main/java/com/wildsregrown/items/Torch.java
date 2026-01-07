package com.wildsregrown.items;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.registries.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import java.util.List;
import java.util.Objects;

public class Torch extends BlockItem {

    public Torch(Settings settings) {
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

    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = this.place(new ItemPlacementContext(context));
        return !actionResult.isAccepted() && context.getStack().contains(DataComponentTypes.CONSUMABLE) ? super.use(context.getWorld(), context.getPlayer(), context.getHand()) : actionResult;
    }

    public ActionResult place(ItemPlacementContext context) {

        ItemStack stack = context.getStack();
        BlockState blockState = ModBlocks.torch.getPlacementState(context);

        if (blockState == null) {
            return ActionResult.FAIL;
        } else if (canPlace(context, blockState)){
            blockState = blockState.with(ModProperties.TORCH_FUEL, stack.get(ModComponents.FUEL)).with(Properties.LIT, stack.get(ModComponents.LIT));
            if (!this.place(context, blockState)) {
                return ActionResult.FAIL;
            } else {
                Objects.requireNonNull(context.getPlayer()).setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        }else {
            return ActionResult.PASS;
        }
    }

    protected boolean place(ItemPlacementContext context, BlockState state) {
        WildsRegrown.LOGGER.info("placed");
        return context.getWorld().setBlockState(context.getBlockPos(), state, 11);
    }

    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        PlayerEntity playerEntity = context.getPlayer();
        ShapeContext shapeContext = playerEntity == null ? ShapeContext.absent() : ShapeContext.of(playerEntity);
        return state.canPlaceAt(context.getWorld(), context.getBlockPos()) && context.getWorld().canPlace(state, context.getBlockPos(), shapeContext);
    }


}
