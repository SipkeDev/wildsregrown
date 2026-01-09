package com.wildsregrown.entities.brain;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.wildsregrown.WildsRegrown.modid;

public class Memory extends MemoryModuleType<Object> {

    public Memory() {
        super(Optional.empty());
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(modid,id), new MemoryModuleType(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(Registries.MEMORY_MODULE_TYPE, Identifier.of(modid,id), new MemoryModuleType(Optional.empty()));
    }

}
