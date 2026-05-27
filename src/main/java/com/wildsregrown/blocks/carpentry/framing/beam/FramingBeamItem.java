package com.wildsregrown.blocks.carpentry.framing.beam;

import com.sipke.api.features.Colors;

import java.util.function.Consumer;

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

import static wildsregrown.api.radial.RadialBuilder.idFromItem;
import static wildsregrown.api.registry.defaults.ApiComponents.ITEM_STANCE;

public class FramingBeamItem extends MultiBlockItem {

    public FramingBeamItem(Properties settings, Block beamBlock, Block postBlock, Block ceilingBlock, Block diagonalBlock) {
        super(settings.component(ITEM_STANCE, 0), new Block[]{beamBlock, postBlock, ceilingBlock, diagonalBlock});
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltip, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, consumer, flag);
        consumer.accept(
                blocks[stack.getComponents().get(ITEM_STANCE)].getName()          .setStyle(Style.EMPTY.withBold(true).withColor(Colors.floralWhite))
        );
    }

    @Override
    public RadialStack getStack() {
        RadialEntry[] entries = new RadialEntry[4];
        entries[0] = new RadialEntry("beam", Colors.light_brown, blocks[0].asItem());
        entries[1] = new RadialEntry("post", Colors.brown, blocks[1].asItem());
        entries[2] = new RadialEntry("ceiling", Colors.dark_brown, blocks[2].asItem());
        entries[3] = new RadialEntry("diagonal", Colors.deep_brown, blocks[3].asItem());
        RadialStack stack = new ItemScreenStack(idFromItem(this), entries);
        addBlockTooStack(stack, blocks[0]);
        addBlockTooStack(stack, blocks[1]);
        addBlockTooStack(stack, blocks[2]);
        addBlockTooStack(stack, blocks[3]);
        return stack;
    }

}
