package com.wildsregrown.items.weapons;

import com.wildsregrown.items.ToolMaterials;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ScorpionSword extends TwoHandedSword {

    private static final int COOLDOWN_TICKS = 80;

    public ScorpionSword(Settings settings) {
        super(ToolMaterials.WOOTZ, 12f, -4f, settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {

        ItemStack stack = player.getStackInHand(hand);

        if (player.isSneaking()) {
            if (player.getItemCooldownManager().isCoolingDown(stack)) {
                player.sendMessage(Text.literal("Cooling down"), true);
                return ActionResult.FAIL;
            }

            player.getItemCooldownManager().set(stack, COOLDOWN_TICKS);

            BlockPos pos = player.getBlockPos();
            Direction direction = player.getHorizontalFacing();
            BlockPos.Mutable newPos = pos.mutableCopy().move(direction);

            for (int i = 0; i < 8; i++) {
                if (world.isAir(newPos)){
                    world.setBlockState(newPos, Blocks.FIRE.getDefaultState());
                }else if (world.isAir(newPos.down())){
                    world.setBlockState(newPos.down(), Blocks.FIRE.getDefaultState());
                }else if (world.isAir(newPos.up())){
                    world.setBlockState(newPos.up(), Blocks.FIRE.getDefaultState());
                }
                newPos.move(direction);
                spawnParticles(world, newPos, 5, 2, 1.5);
            }
            spawnParticles(world, newPos, 25, 3, 3);
            playSound(world, newPos);
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        playSound(target.getEntityWorld(), target.getBlockPos());
        if (attacker instanceof PlayerEntity entity) {
            if (entity.isSneaking() && !entity.getItemCooldownManager().isCoolingDown(stack)) {
                spawnParticles(target.getEntityWorld(), target.getBlockPos(), 20, target.getWidth() * 2.5, target.getHeight());
                target.setOnFireForTicks(7 * 20);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, 60, 5));
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

    private void spawnParticles(World world, BlockPos pos, int particleCount, double width, double height) {
        Random rand = new Random();

        for (int i = 0; i < particleCount; i++) {
            double offsetX = (rand.nextDouble() - 0.5) * width;
            double offsetY = rand.nextDouble() * height;
            double offsetZ = (rand.nextDouble() - 0.5) * width;

            double x = pos.getX() + offsetX;
            double y = pos.getY() + offsetY;
            double z = pos.getZ() + offsetZ;

            if (world instanceof ServerWorld server) {
                server.spawnParticles(ParticleTypes.FLAME, x, y, z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
            } else {
                world.addParticleClient(ParticleTypes.FLAME, x, y, z, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private void playSound(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld){
            serverWorld.playSound(null, pos, SoundEvents.ENTITY_FIREWORK_ROCKET_BLAST, SoundCategory.PLAYERS);
        }
    }

}