package com.wildsregrown.blocks.carpentry.framing.tudor;

import com.sipke.api.features.Colors;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;
import wildsregrown.api.item.MultiBlockItem;
import wildsregrown.api.radial.RadialEntry;
import wildsregrown.api.radial.stack.ItemScreenStack;
import wildsregrown.api.radial.stack.RadialStack;

import java.util.function.Consumer;

import static wildsregrown.api.radial.RadialBuilder.idFromItem;
import static wildsregrown.api.registry.defaults.ApiComponents.ITEM_STANCE;

public class TudorItem extends MultiBlockItem {

    public TudorItem(Properties settings, Block square, Block horizontal, Block vertical) {
        super(settings.component(ITEM_STANCE, 0), new Block[]{square, horizontal, vertical});
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltip, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, consumer, flag);
        consumer.accept(
                Component.literal("Try the radial menu for different shapes.")
        );
        consumer.accept(
                blocks[stack.getComponents().get(ITEM_STANCE)].getName().setStyle(Style.EMPTY.withBold(true).withColor(Colors.floralWhite))
        );
    }

    @Override
    public RadialStack getStack() {
        RadialEntry[] entries = new RadialEntry[3];
        entries[0] = new RadialEntry("square", Colors.grey, blocks[0].asItem());
        entries[1] = new RadialEntry("horizontal", Colors.darkGrey, blocks[1].asItem());
        entries[2] = new RadialEntry("vertical", Colors.light_grey, blocks[2].asItem());
        RadialStack stack = new ItemScreenStack(idFromItem(this), entries);
        addBlockTooStack(stack, blocks[0]);
        addBlockTooStack(stack, blocks[1]);
        addBlockTooStack(stack, blocks[2]);
        return stack;
    }

}
