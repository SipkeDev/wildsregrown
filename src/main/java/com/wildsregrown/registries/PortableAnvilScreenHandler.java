package com.wildsregrown.registries;

import com.wildsregrown.blocks.crafting.PortableAnvil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class PortableAnvilScreenHandler extends AnvilScreenHandler {

    public PortableAnvil customAnvil;

    public PortableAnvilScreenHandler(PortableAnvil customAnvil, int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(syncId, inventory, context);
        this.customAnvil = customAnvil;
        this.context.run(((world, blockPos) -> {
            //if (world.getBlockState(blockPos).isIn()) {
            //PortableAnvilScreenHandler.xpLimit = customAnvil.getXPLimit();
            //}
        }));
    }

    @Override
    public void updateResult() {
        super.updateResult();
    }

    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        super.onTakeOutput(player, stack);
        this.context.run((world, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            if (!player.getAbilities().creativeMode && player.getRandom().nextFloat() < this.customAnvil.getBlastResistance()) {
                world.setBlockState(pos, blockState, 2);
                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 10F, 1F);
            } else {
                world.playSound(null, pos, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 10F, 1F);
            }
        });
    }

}
