package com.wildsregrown.items.weapons;

import com.wildsregrown.items.ToolMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import java.util.List;
import java.util.Random;

public class ScorpionSword extends TwoHandedSword {

    private static final int COOLDOWN_TICKS = 80;

    public ScorpionSword(Properties settings) {
        super(ToolMaterials.WOOTZ, 12f, -4f, settings);
    }

    @Override
    public InteractionResult use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (player.getCooldowns().isOnCooldown(stack)) {
                player.displayClientMessage(Component.literal("Cooling down"), true);
                return InteractionResult.FAIL;
            }

            player.getCooldowns().addCooldown(stack, COOLDOWN_TICKS);

            BlockPos pos = player.blockPosition();
            Direction direction = player.getDirection();
            BlockPos.MutableBlockPos newPos = pos.mutable().move(direction);

            for (int i = 0; i < 8; i++) {
                if (world.isEmptyBlock(newPos)){
                    world.setBlockAndUpdate(newPos, Blocks.FIRE.defaultBlockState());
                }else if (world.isEmptyBlock(newPos.below())){
                    world.setBlockAndUpdate(newPos.below(), Blocks.FIRE.defaultBlockState());
                }else if (world.isEmptyBlock(newPos.above())){
                    world.setBlockAndUpdate(newPos.above(), Blocks.FIRE.defaultBlockState());
                }
                newPos.move(direction);
                spawnParticles(world, newPos, 5, 2, 1.5);
            }
            spawnParticles(world, newPos, 25, 3, 3);
            playSound(world, newPos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        playSound(target.level(), target.blockPosition());
        if (attacker instanceof Player entity) {
            if (entity.isShiftKeyDown() && !entity.getCooldowns().isOnCooldown(stack)) {
                spawnParticles(target.level(), target.blockPosition(), 20, target.getBbWidth() * 2.5, target.getBbHeight());
                target.igniteForTicks(7 * 20);
                target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 5));
            }
        }

    }

    /**
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("scorpion sword"));
        super.appendTooltip(stack, context, tooltip, type);
    }
    */

    /**
     * World interactions
     */

    private void spawnParticles(Level world, BlockPos pos, int particleCount, double width, double height) {
        Random rand = new Random();

        for (int i = 0; i < particleCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * width;
            double offsetY = rand.nextDouble() * height;
            double offsetZ = (rand.nextDouble() - 0.5) * width;

            double x = pos.getX() + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + offsetZ;

            if (world instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.FLAME, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            } else {
                world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void playSound(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverWorld){
            serverWorld.playSound(null, pos, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.PLAYERS);
        }
    }

}