package com.wildsregrown.blocks.flora;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.items.weapons.Sword;
import com.wildsregrown.registries.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import wildsregrown.api.block.flora.type.RootedFlora;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.render.TintUtil;

/*
Chives
 */
public class Chives extends RootedFlora {

    private final int flower;
    private final int[] rgb;

    public Chives(Properties settings, int flower) {
        super(settings, 0.5f, 3, 8);
        this.flower = flower;
        this.rgb = TintUtil.buildBlendMap(Colors.fern, Colors.fernGreen, Colors.pastelYellow, moisture.getPossibleValues().size());
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {

        int age = state.getValue(WRGProperties.AGE_6);
        if (age > 1) {
            if (stack.getItem() instanceof Sword) {
                int transfer = age - 1;
                world.setBlockAndUpdate(pos, state.setValue(WRGProperties.AGE_6, 1));
                player.getInventory().add(ModItems.chives_bundle.getDefaultInstance().copyWithCount(transfer));
                return InteractionResult.SUCCESS;
            } else if (stack.isEmpty()) {
                world.setBlockAndUpdate(pos, state.setValue(WRGProperties.AGE_6, age-1));
                player.getInventory().add(ModItems.chives_bundle.getDefaultInstance());
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getTint(final BlockState state, final int tintIndex) {
        if (tintIndex == 0){
            return rgb[MathUtil.clamp(state.getValue(moisture)-1, 0, rgb.length)];
        }
        if (tintIndex == 1){
            return flower;
        }
        return -1;
    }
}
