package com.wildsregrown.data;

import com.wildsregrown.WildsRegrown;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

class Translations extends FabricLanguageProvider {

    protected Translations(FabricDataOutput dataGenerator, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
// Specifying en_us is optional, by default is en_us.
        super(dataGenerator, "en_us", registries);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        //Automatic EN translation from IDs
        Registries.ITEM.forEach(item -> { if (item.getTranslationKey().startsWith("item." + modid)) {
            translationBuilder.add(item, WordUtils.capitalize(item.getTranslationKey().split("\\.")[2].replace('_',' ')));
            //WildsRegrown.LOGGER.info(item.getTranslationKey());
        }});
        Registries.BLOCK.forEach(block -> { if (block.getTranslationKey().startsWith("block." + modid)) {
            translationBuilder.add(block, WordUtils.capitalize(block.getTranslationKey().split("\\.")[2].replace('_',' ')));
            //WildsRegrown.LOGGER.info(block.getTranslationKey());
        }});

    }
}
