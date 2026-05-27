package com.wildsregrown.blocks.carpentry.interior.cabinet;

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

public class CabinetItem extends MultiBlockItem {

    public CabinetItem(Properties properties, Block... blocks) {
        super(properties.component(ITEM_STANCE, 0), blocks);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltip, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, consumer, flag);
        consumer.accept(
                blocks[stack.getComponents().get(ITEM_STANCE)].getName().setStyle(Style.EMPTY.withBold(true).withColor(Colors.floralWhite))
        );
    }

    @Override
    public RadialStack getStack() {
        RadialEntry[] entries = new RadialEntry[2];
        entries[0] = new RadialEntry("cabinet", Colors.light_brown, blocks[0].asItem());
        entries[1] = new RadialEntry("shelves", Colors.brown, blocks[1].asItem());
        RadialStack stack = new ItemScreenStack(idFromItem(this), entries);
        addBlockTooStack(stack, blocks[0]);
        addBlockTooStack(stack, blocks[1]);
        return stack;
    }

}
