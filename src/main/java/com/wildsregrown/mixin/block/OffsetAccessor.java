package com.wildsregrown.mixin.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.Properties.class)
public interface OffsetAccessor {
    @Accessor("offsetFunction")
    void offsetter(BlockBehaviour.OffsetFunction offset);
}