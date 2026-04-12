package com.wildsregrown.data;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.data.tags.BiomeTagGenerator;
import com.wildsregrown.data.tags.BlockTagGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.registry.*;

public class DataGeneratorProvider implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {

		WildsRegrown.LOGGER.info("Generating files");
		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(Translations::new);
		pack.addProvider(BlockStateDataGenerator::new);
		pack.addProvider(BlockTagGenerator::new);
		pack.addProvider(ItemTagGenerator::new);
		pack.addProvider(BiomeTagGenerator::new);
		pack.addProvider(FluidTagProvider::new);
		pack.addProvider(BlockDrops::new);
		pack.addProvider(RecipeProvider::new);
		pack.addProvider(WRGDynamicRegistry::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		WRGDynamicRegistry.buildRegistry(registryBuilder);
	}

	public static String idFromBlock(Block block) {
		return Registries.BLOCK.getId(block).getPath().replaceFirst(".*\\.", "");
	}

}
