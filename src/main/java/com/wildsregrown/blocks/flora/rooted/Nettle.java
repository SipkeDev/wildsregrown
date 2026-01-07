package com.wildsregrown.blocks.flora.rooted;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.render.TintUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
Stinging nettle
 */
public class Nettle extends RootedFlora {

    private final int[] rgb;

    public Nettle(Settings settings) {
        super(settings, 0.8f, 5, 12);
        this.rgb = TintUtil.buildBlendMap(Colors.fern, Colors.fernGreen, Colors.pastelYellow, moisture.getValues().size());
    }

    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        entity.serverDamage(world.getDamageSources().cactus(), 1.0F);
        if (entity instanceof PlayerEntity player){
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 500), entity);
        }else {
            world.createExplosion(entity, pos.getX(),pos.getY(),pos.getZ(),20, World.ExplosionSourceType.BLOCK);
        }
    }

    @Override
    public int getTint(final BlockState state, final int tintIndex) {
        return tintIndex == 0 ?
        rgb[MathUtil.clamp(state.get(moisture)-1, 0, rgb.length)]
        : -1;
    }
}
