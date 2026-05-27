package com.wildsregrown;

import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.registries.*;
import com.wildsregrown.registries.world.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Minecraft mod main
 */
public class WildsRegrown implements ModInitializer {

	public static final String modid = "wildsregrown";
    public static final Logger LOGGER = LoggerFactory.getLogger(modid);

	@Override
	public void onInitialize() {

		//Networking
		//Networking.initialize();
		//Events.initialize();
		//Register voxels
		ModScreenHandlers.initialize();
		ModItems.initialize();
		ModBlocks.initialize();
		ModComponents.initialize();
		ModItemGroups.initialize();
		ModRecipes.initialize();
		ModEntities.initialize();
		//Register world
		MaterialRegistery.init();
		Biomes.init();
		Ecosystems.init();
		Landforms.init();
		Trees.init();
		Floras.init();
		Structures.init();

	}

}