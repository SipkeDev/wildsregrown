//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wildsregrown.items.tools;

import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.Layered;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.MathUtil;
import com.wildsregrown.items.IRadialItem;
import com.wildsregrown.registries.ModComponents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Pickaxe extends Item implements IRadialItem {

    public Pickaxe(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(setting(material, attackDamage, attackSpeed, settings));
    }

    private static Settings setting(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        return material.applyToolSettings(settings.maxCount(1), BlockTags.PICKAXE_MINEABLE, attackDamage, attackSpeed, 0.125f)
                .component(ModComponents.ITEM_OXIDATION, 0)
                .component(ModComponents.ITEM_SHARPNESS, 0)
                .component(ModComponents.ITEM_STANCE, 0);
    }

    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(2, attacker, EquipmentSlot.MAINHAND);
    }

    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return super.postMine(stack, world, state, pos, miner);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        BlockState state = world.getBlockState(context.getBlockPos());
        Block block = state.getBlock();
        if (block instanceof Layered){
            int layer = state.get(ModProperties.LAYERS);
            if (layer == 1){
                world.breakBlock(context.getBlockPos(), true);
            }else if (layer == 8){
                world.setBlockState(context.getBlockPos(), state.with(Properties.FACING, context.getSide()).with(ModProperties.LAYERS, layer-1),2);
            }else {
                world.setBlockState(context.getBlockPos(), state.with(ModProperties.LAYERS, layer-1),2);
            }

            world.playSound(context.getPlayer(), context.getBlockPos(), SoundEvents.BLOCK_STONE_BREAK, SoundCategory.BLOCKS);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
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
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity entity) {
            if (entity.isSneaking()) {
                playSound(target.getEntityWorld(), target.getBlockPos(), SoundEvents.ENTITY_PAINTING_BREAK);
                spawnParticles(target.getEntityWorld(), target.getBlockPos(), 20, target.getWidth() * 2.5, target.getHeight());
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 8), attacker);
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 4));
            }
        }
    }

    /**
     * World interactions
     */

    private static void spawnParticles(World world, BlockPos pos, int particleCount, double width, double height) {
        Random rand = new Random();

        for (int i = 0; i < particleCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * width;
            double offsetY = rand.nextDouble() * height;
            double offsetZ = (rand.nextDouble() - 0.5) * width;

            double x = pos.getX() + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + offsetZ;

            if (world instanceof ServerWorld server) {
                server.spawnParticles(ParticleTypes.POOF, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            } else {
                world.addParticleClient(ParticleTypes.POOF, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private static void playSound(World world, BlockPos pos, SoundEvent event) {
        if (world instanceof ServerWorld serverWorld){
            serverWorld.playSound(null, pos, event, SoundCategory.PLAYERS);
        }
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        float t = super.getMiningSpeed(stack, state);
        if (stack.contains(ModComponents.ITEM_STANCE)) {
            switch (stack.get(ModComponents.ITEM_STANCE)) {
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