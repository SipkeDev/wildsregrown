package com.wildsregrown.mixin.world;

import com.wildsregrown.world.biomes.SetableSection;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ReadableContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkSection.class)
public class SectionMixin implements SetableSection {

    @Shadow
    private ReadableContainer<RegistryEntry<Biome>> biomeContainer;

    @Override
    public void wrg$set(ReadableContainer<RegistryEntry<Biome>> biomeContainer) {
        this.biomeContainer = biomeContainer;
    }

    @Override
    public ReadableContainer<RegistryEntry<Biome>> wrg$get() {
        return biomeContainer;
    }

}
