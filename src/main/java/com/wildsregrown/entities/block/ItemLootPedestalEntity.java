package com.wildsregrown.entities.block;

import com.wildsregrown.registries.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class ItemLootPedestalEntity extends BlockEntity {

    private ItemStack stack;

    public ItemLootPedestalEntity(BlockPos pos, BlockState state) {
        super(ModEntities.itemLootPedestal, pos, state);
        this.stack = ItemStack.EMPTY;
    }

    public ItemStack getStack() {
        return stack;
    }

    public void takeItem(Player player){
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        clear();
        setChanged();
        playSound(player.level(), player.blockPosition(), SoundEvents.PLAYER_LEVELUP);
    }

    public void placeItem(Player player){
        this.stack = player.getMainHandItem();
        player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        setChanged();
    }

    @Override
    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!stack.isEmpty()) {
            view.store("stack", ItemStack.CODEC, stack);
        }
    }

    @Override
    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        Optional<ItemStack> s0 = view.read("stack", ItemStack.CODEC);
        s0.ifPresent(stack -> this.stack = stack);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
        return saveWithoutMetadata(registryLookup);
    }

    public void clear(){
        this.stack = ItemStack.EMPTY;
    }

    public boolean isEmpty(){
        return stack.isEmpty();
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ItemLootPedestalEntity entity) {

        if (!world.isClientSide() && state.getValue(BlockStateProperties.ENABLED)) {

            double effectRadius = 32;
            AABB box = (new AABB(pos)).inflate(effectRadius).expandTowards(0.0F, world.getHeight(),0.0F);

            List<Player> list = world.getEntitiesOfClass(Player.class, box);
            for(Player playerEntity : list) {
                playerEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, 12, 0, true, true));
            }

        }

    }

    public static void playSound(Level world, BlockPos pos, SoundEvent sound) {
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

}