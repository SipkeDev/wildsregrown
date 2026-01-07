package com.wildsregrown.items.tools;

import com.wildsregrown.blocks.Dice;
import com.wildsregrown.items.IRadialItem;
import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.recipe.ToolEventInput;
import com.wildsregrown.recipe.ToolEventRecipe;
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
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

public class Hatchet extends Item implements IRadialItem {

    public Hatchet(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(setting(material, attackDamage, attackSpeed, settings));
    }

    private static Settings setting(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        return material.applyToolSettings(settings, BlockTags.AXE_MINEABLE, attackDamage, attackSpeed, 0.25f)
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
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        //if (slot.getEntitySlotId()) {
            if (Dice.d100(world.getRandom()) == 1) {
                int i = stack.get(ModComponents.ITEM_OXIDATION);
                if (i > 0)
                    stack.set(ModComponents.ITEM_OXIDATION, i-1);
            }
        //}
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getBlockPos();
        if (world instanceof ServerWorld server){

            BlockState state = world.getBlockState(pos);
            ServerRecipeManager recipeManager = server.getRecipeManager();
            ItemStack tool = player.getMainHandStack();
            ItemStack material = state.getBlock().asItem().getDefaultStack();

            Optional<RecipeEntry<ToolEventRecipe>> match = recipeManager.getFirstMatch(ModRecipes.toolEventType, new ToolEventInput(tool.get(ModComponents.ITEM_STANCE), tool, material), world);
            if (match.isPresent()) {
                ItemStack stack = match.get().value().output();
                final Block place = Registries.BLOCK.get(Identifier.of(stack.getRegistryEntry().getIdAsString()));
                world.setBlockState(pos, place.getDefaultState(), 2);
                return ActionResult.CONSUME;
            }

        }

        return ActionResult.PASS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        playSound(target.getEntityWorld(), target.getBlockPos());
        if (attacker instanceof PlayerEntity entity) {
            if (entity.isSneaking()) {
                spawnParticles(target.getEntityWorld(), target.getBlockPos(), 20, target.getWidth() * 2.5, target.getHeight());
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 50), attacker);
                attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60, 5));
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

    private static void playSound(World world, BlockPos pos) {
        if (world instanceof ServerWorld serverWorld){
            serverWorld.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS);
        }
    }

    @Override
    public float getMiningSpeed(ItemStack stack, BlockState state) {
        return super.getMiningSpeed(stack, state);
    }

    public enum Stances{

        debark(0, "DeBarking"),
        split(1, "Split"),
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

        @Override
        public String toString() {
            return super.toString().toUpperCase(Locale.ROOT);
        }

    }

}