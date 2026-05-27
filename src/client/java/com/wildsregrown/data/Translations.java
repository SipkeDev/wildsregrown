package com.wildsregrown.data;

import com.wildsregrown.WildsRegrown;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

import static com.wildsregrown.WildsRegrown.modid;

class Translations extends FabricLanguageProvider {

    protected Translations(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> registries) {
        // Specifying en_us is optional, by default is en_us.
        super(dataGenerator, "en_us", registries);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider wrapperLookup, TranslationBuilder translationBuilder) {
        //Automatic EN translation from IDs
        BuiltInRegistries.ITEM.forEach(item -> { if (item.getDescriptionId().startsWith("item." + modid)) {
            translationBuilder.add(item, WordUtils.capitalize(item.getDescriptionId().split("\\.")[2].replace('_',' ')));
            //WildsRegrown.LOGGER.info(item.getTranslationKey());
        }});
        BuiltInRegistries.BLOCK.forEach(block -> { if (block.getDescriptionId().startsWith("block." + modid)) {
            translationBuilder.add(block, WordUtils.capitalize(block.getDescriptionId().split("\\.")[2].replace('_',' ')));
            //WildsRegrown.LOGGER.info(block.getTranslationKey());
        }});

    }
}
