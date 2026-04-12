package com.wildsregrown.world.biomes;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ReadableContainer;

public interface SetableSection {

    void wrg$set(ReadableContainer<RegistryEntry<Biome>> biomeContainer);
    ReadableContainer<RegistryEntry<Biome>> wrg$get();

}
