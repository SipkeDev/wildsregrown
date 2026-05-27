package com.wildsregrown.registries;

import com.wildsregrown.blocks.crafting.PortableAnvil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class PortableAnvilScreenHandler extends AnvilMenu {

    public PortableAnvil customAnvil;

    public PortableAnvilScreenHandler(PortableAnvil customAnvil, int syncId, Inventory inventory, ContainerLevelAccess context) {
        super(syncId, inventory, context);
        this.customAnvil = customAnvil;
        this.access.execute(((world, blockPos) -> {
            //if (world.getBlockState(blockPos).isIn()) {
            //PortableAnvilScreenHandler.xpLimit = customAnvil.getXPLimit();
            //}
        }));
    }

    @Override
    public void createResult() {
        super.createResult();
    }

    protected void onTake(Player player, ItemStack stack) {
        super.onTake(player, stack);
        this.access.execute((world, pos) -> {
            BlockState blockState = world.getBlockState(pos);
            if (!player.getAbilities().instabuild && player.getRandom().nextFloat() < this.customAnvil.getExplosionResistance()) {
                world.setBlock(pos, blockState, 2);
                world.playSound(null, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 10F, 1F);
            } else {
                world.playSound(null, pos, SoundEvents.ANVIL_BREAK, SoundSource.BLOCKS, 10F, 1F);
            }
        });
    }

}
