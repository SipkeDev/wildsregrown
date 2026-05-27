package com.wildsregrown.items.tools;

import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.recipe.ToolEventInput;
import com.wildsregrown.recipe.ToolEventRecipe;
import com.wildsregrown.registries.ModComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import wildsregrown.api.block.Dice;
import wildsregrown.api.registry.defaults.ApiComponents;

import java.util.Locale;
import java.util.Optional;
import java.util.Random;

public class Hatchet extends Item {

    public Hatchet(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        super(setting(material, attackDamage, attackSpeed, settings));
    }

    private static Properties setting(ToolMaterial material, float attackDamage, float attackSpeed, Properties settings) {
        return material.applyToolProperties(settings, BlockTags.MINEABLE_WITH_AXE, attackDamage, attackSpeed, 0.25f)
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
    public void inventoryTick(ItemStack stack, ServerLevel world, Entity entity, @Nullable EquipmentSlot slot) {
        //if (slot.getEntitySlotId()) {
            if (Dice.d100(world.getRandom()) == 1) {
                int i = stack.get(ModComponents.ITEM_OXIDATION);
                if (i > 0)
                    stack.set(ModComponents.ITEM_OXIDATION, i-1);
            }
        //}
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level world = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        if (world instanceof ServerLevel server){

            BlockState state = world.getBlockState(pos);
            RecipeManager recipeManager = server.recipeAccess();
            ItemStack tool = player.getMainHandItem();
            ItemStack material = state.getBlock().asItem().getDefaultInstance();

            Optional<RecipeHolder<ToolEventRecipe>> match = recipeManager.getRecipeFor(ModRecipes.toolEventType, new ToolEventInput(tool.get(ApiComponents.ITEM_STANCE), tool, material), world);
            if (match.isPresent()) {
                ItemStack stack = match.get().value().output();
                final Block place = BuiltInRegistries.BLOCK.getValue(Identifier.parse(stack.getItemHolder().getRegisteredName()));
                world.setBlock(pos, place.defaultBlockState(), 2);
                return InteractionResult.CONSUME;
            }

        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        return super.use(world, user, hand);
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        playSound(target.level(), target.blockPosition());
        if (attacker instanceof Player entity) {
            if (entity.isShiftKeyDown()) {
                spawnParticles(target.level(), target.blockPosition(), 20, target.getBbWidth() * 2.5, target.getBbHeight());
                target.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 60, 50), attacker);
                attacker.addEffect(new MobEffectInstance(MobEffects.SPEED, 60, 5));
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

    private static void playSound(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverWorld){
            serverWorld.playSound(null, pos, SoundEvents.FIRE_AMBIENT, SoundSource.PLAYERS);
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state);
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

        public String getTooltip() {
            return tooltip;
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