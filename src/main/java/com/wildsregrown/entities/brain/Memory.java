package com.wildsregrown.entities.brain;

import com.mojang.serialization.Codec;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

import static com.wildsregrown.WildsRegrown.modid;

public class Memory extends MemoryModuleType<Object> {

    public Memory() {
        super(Optional.empty());
    }

    private static <U> MemoryModuleType<U> register(String id, Codec<U> codec) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, Identifier.fromNamespaceAndPath(modid,id), new MemoryModuleType(Optional.of(codec)));
    }

    private static <U> MemoryModuleType<U> register(String id) {
        return Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, Identifier.fromNamespaceAndPath(modid,id), new MemoryModuleType(Optional.empty()));
    }

}
