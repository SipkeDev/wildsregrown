package com.wildsregrown.data;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.world.biomes.WRGBiomes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

public class WRGDynamicRegistry extends FabricDynamicRegistryProvider {

    protected WRGDynamicRegistry(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void buildRegistry(RegistryBuilder registryBuilder) {
        //registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ::register);
        //registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ::register);
        registryBuilder.addRegistry(RegistryKeys.BIOME, WRGBiomes::register);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
        //addAll(entries, wrapperLookup.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        //addAll(entries, wrapperLookup.getOrThrow(RegistryKeys.PLACED_FEATURE));
        addAll(entries, wrapperLookup.getOrThrow(RegistryKeys.BIOME));

        WildsRegrown.LOGGER.info("Configuring" + wrapperLookup.streamAllRegistryKeys().count());
        wrapperLookup.getOrThrow(RegistryKeys.BIOME).streamEntries().forEach(
                o -> WildsRegrown.LOGGER.info(o.getIdAsString())
        );

    }

    @Override
    public String getName() {
        return "WRG Dynamic registry";
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T> List<RegistryEntry<T>> addAll(Entries entries, RegistryWrapper.Impl<T> registry) {
        return registry.streamKeys()
                .filter(tRegistryKey -> tRegistryKey.getValue().getNamespace().equals(modid))
                .map(tRegistryKey -> entries.add(registry, tRegistryKey))
                .toList();
    }

}