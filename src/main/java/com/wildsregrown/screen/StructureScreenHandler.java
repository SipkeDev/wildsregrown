package com.wildsregrown.screen;

import com.wildsregrown.blocks.dungeon.StructureEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class StructureScreenHandler extends ScreenHandler {

    public final StructureEntity entity;

    public StructureScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory.player.getEntityWorld().getBlockEntity(pos));
    }

    private StructureScreenHandler(int syncId, BlockEntity blockEntity) {
        super(ModScreenHandlers.STRUCTURE_SCREEN_HANDLER, syncId);
        this.entity = (StructureEntity) blockEntity;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

}