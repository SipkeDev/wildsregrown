package com.wildsregrown.blocks.flora.rooted;

import com.sipke.api.features.Colors;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.TintUtil;
import com.wildsregrown.items.weapons.Sword;
import com.wildsregrown.registries.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
Chives
 */
public class Chives extends RootedFlora {

    private final int flower;
    private final int[] rgb;

    public Chives(Settings settings, int flower) {
        super(settings, 0.5f, 3, 8);
        this.flower = flower;
        this.rgb = TintUtil.buildBlendMap(Colors.fern, Colors.fernGreen, Colors.pastelYellow, moisture.getValues().size());
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        int age = state.get(ModProperties.AGE_6);
        if (age > 1) {
            if (stack.getItem() instanceof Sword) {
                int transfer = age - 1;
                world.setBlockState(pos, state.with(ModProperties.AGE_6, 1));
                player.getInventory().insertStack(ModItems.chives_bundle.getDefaultStack().copyWithCount(transfer));
                return ActionResult.SUCCESS;
            } else if (stack.isEmpty()) {
                world.setBlockState(pos, state.with(ModProperties.AGE_6, age-1));
                player.getInventory().insertStack(ModItems.chives_bundle.getDefaultStack());
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public int getTint(final BlockState state, final int tintIndex) {
        if (tintIndex == 0){
            return rgb[MathUtil.clamp(state.get(moisture)-1, 0, rgb.length)];
        }
        if (tintIndex == 1){
            return flower;
        }
        return -1;
    }
}
