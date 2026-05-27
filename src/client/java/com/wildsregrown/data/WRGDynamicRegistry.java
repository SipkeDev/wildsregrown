package com.wildsregrown.data;

import com.wildsregrown.WildsRegrown;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

public class WRGDynamicRegistry extends FabricDynamicRegistryProvider {

    protected WRGDynamicRegistry(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    public static void buildRegistry(RegistrySetBuilder registryBuilder) {
        //registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ::register);
        //registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ::register);
        //registryBuilder.add(Registries.BIOME, WRGBiomes::register);
    }

    @Override
    protected void configure(HolderLookup.Provider wrapperLookup, Entries entries) {
        //addAll(entries, wrapperLookup.getOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        //addAll(entries, wrapperLookup.getOrThrow(RegistryKeys.PLACED_FEATURE));
        addAll(entries, wrapperLookup.lookupOrThrow(Registries.BIOME));

        WildsRegrown.LOGGER.info("Configuring" + wrapperLookup.listRegistryKeys().count());
        wrapperLookup.lookupOrThrow(Registries.BIOME).listElements().forEach(
                o -> WildsRegrown.LOGGER.info(o.getRegisteredName())
        );

    }

    @Override
    public String getName() {
        return "WRG Dynamic registry";
    }

    @SuppressWarnings("UnusedReturnValue")
    public <T> List<Holder<T>> addAll(Entries entries, HolderLookup.RegistryLookup<T> registry) {
        return registry.listElementIds()
                .filter(tRegistryKey -> tRegistryKey.identifier().getNamespace().equals(modid))
                .map(tRegistryKey -> entries.add(registry, tRegistryKey))
                .toList();
    }

}