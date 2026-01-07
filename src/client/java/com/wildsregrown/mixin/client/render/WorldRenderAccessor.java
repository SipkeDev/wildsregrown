package com.wildsregrown.mixin.client.render;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface WorldRenderAccessor {
    @Accessor("world")
    ClientWorld getWorld();
}