//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wildsregrown.items.tools;

import com.wildsregrown.registries.ModComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.shapes.Layered;
import wildsregrown.api.registry.defaults.ApiComponents;

import java.util.Locale;
import java.util.Random;

public class Pickaxe extends Item {

    public Pickaxe(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(setting(material, attackDamage, attackSpeed, settings));
    }

    private static Properties setting(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        return material.applyToolProperties(settings.stacksTo(1), BlockTags.MINEABLE_WITH_PICKAXE, attackDamage, attackSpeed, 0.125f)
                .component(ModComponents.ITEM_OXIDATION, 0)
                .component(ModComponents.ITEM_SHARPNESS, 0)
                .component(ApiComponents.ITEM_STANCE, 0);
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(2, attacker, EquipmentSlot.MAINHAND);
    }

    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
        return super.mineBlock(stack, world, state, pos, miner);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level world = context.getLevel();
        BlockState state = world.getBlockState(context.getClickedPos());
        Block block = state.getBlock();
        if (block instanceof Layered){
            int layer = state.getValue(WRGProperties.LAYERS);
            if (layer == 1){
                world.destroyBlock(context.getClickedPos(), true);
            }else if (layer == 8){
                world.setBlock(context.getClickedPos(), state.setValue(BlockStateProperties.FACING, context.getClickedFace()).setValue(WRGProperties.LAYERS, layer-1),2);
            }else {
                world.setBlock(context.getClickedPos(), state.setValue(WRGProperties.LAYERS, layer-1),2);
            }

            world.playSound(context.getPlayer(), context.getClickedPos(), SoundEvents.STONE_BREAK, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    /**
    @Override

    public void inventoryTick(ItemStack stack, World world, Entity entity,) {
        if (selected) {
            if (Dice.d100(world.getRandom()) == 1) {
                int i = stack.get(ModComponents.ITEM_OXIDATION);
                if (i > 0)
                    stack.set(ModComponents.ITEM_OXIDATION, i-1);
            }
        }
    }
     */

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player entity) {
            if (entity.isShiftKeyDown()) {
                playSound(target.level(), target.blockPosition(), SoundEvents.PAINTING_BREAK);
                spawnParticles(target.level(), target.blockPosition(), 20, target.getBbWidth() * 2.5, target.getBbHeight());
                target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 8), attacker);
                attacker.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 4));
            }
        }
    }

    /**
     * World interactions
     */

    private static void spawnParticles(Level world, BlockPos pos, int particleCount, double width, double height) {
        Random rand = new Random();

        for (int i = 0; i < particleCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * width;
            double offsetY = rand.nextDouble() * height;
            double offsetZ = (rand.nextDouble() - 0.5) * width;

            double x = pos.getX() + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + offsetZ;

            if (world instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.POOF, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            } else {
                world.addParticle(ParticleTypes.POOF, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private static void playSound(Level world, BlockPos pos, SoundEvent event) {
        if (world instanceof ServerLevel serverWorld){
            serverWorld.playSound(null, pos, event, SoundSource.PLAYERS);
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float t = super.getDestroySpeed(stack, state);
        if (stack.has(ApiComponents.ITEM_STANCE)) {
            switch (stack.get(ApiComponents.ITEM_STANCE)) {
                case 0:
                    t *= 1.2f;
                case 1:
                    t *= 0.2f;
                case 2:
                    t *= 0.8f;
            }
        }
        return t;
    }

    public enum Stances{

        mining(0, "Mining"),
        split(1, "Splitting"),
        shape(2, "Shaping");

        private final int key;
        private final String tooltip;

        Stances(int key, String tooltip){
            this.key = key;
            this.tooltip = tooltip;
        }

        public int getKey() {
            return key;
        }

        public String getTooltip() {
            return tooltip;
        }

        @Override
        public String toString() {
            return super.toString().toUpperCase(Locale.ROOT);
        }

    }

}