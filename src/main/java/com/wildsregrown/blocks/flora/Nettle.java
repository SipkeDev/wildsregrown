package com.wildsregrown.blocks.flora;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.flora.type.RootedFlora;
import wildsregrown.api.block.render.TintUtil;

/*
Stinging nettle
 */
public class Nettle extends RootedFlora {

    private final int[] rgb;

    public Nettle(Properties settings) {
        super(settings, 0.8f, 5, 12);
        this.rgb = TintUtil.buildBlendMap(Colors.fern, Colors.fernGreen, Colors.pastelYellow, moisture.getPossibleValues().size());
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier insideBlockEffectApplier, boolean bl) {
        entity.hurt(level.damageSources().cactus(), 1.0F);
        if (entity instanceof Player player){
            player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 500), entity);
        }else {
            level.explode(entity, pos.getX(),pos.getY(),pos.getZ(),20, Level.ExplosionInteraction.BLOCK);
        }
    }

    @Override
    protected VoxelShape getEntityInsideCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, Entity entity) {
        return super.getEntityInsideCollisionShape(blockState, blockGetter, blockPos, entity);
    }

    @Override
    public int getTint(final BlockState state, final int tintIndex) {
        return tintIndex == 0 ?
        rgb[MathUtil.clamp(state.getValue(moisture)-1, 0, rgb.length)]
        : -1;
    }

}
