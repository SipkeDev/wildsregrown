package com.wildsregrown;

import com.wildsregrown.commands.SkunkWorks;
import com.wildsregrown.commands.TogglePreGen;
import com.wildsregrown.recipe.ModRecipes;
import com.wildsregrown.registries.*;
import com.wildsregrown.commands.Locate;
import com.wildsregrown.network.Networking;
import com.wildsregrown.registries.ModScreenHandlers;
import com.wildsregrown.world.InitiationHandler;
import com.wildsregrown.world.WRGChunkGenerator;
import com.wildsregrown.world.biomes.WRGBiomeProvider;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
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
		Networking.initialize();
		//Register voxels
		//ModScreenHandlers.initialize();
		ModBlocks.initialize();
		ModItems.initialize();
		ModComponents.initialize();
		ModItemGroups.initialize();
		ModRecipes.initialize();
		ModEntities.initialize();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			Locate.register(dispatcher);
			TogglePreGen.register(dispatcher);
			SkunkWorks.register(dispatcher);
		});

		//Register custom world classes
		Registry.register(Registries.BIOME_SOURCE, Identifier.of(modid, "wrg_biome"), WRGBiomeProvider.CODEC);
		Registry.register(Registries.CHUNK_GENERATOR, Identifier.of(modid, "wrg_chunk"), WRGChunkGenerator.CODEC);
		//Server sync
		ServerLifecycleEvents.SERVER_STARTING.register(InitiationHandler::initServer);

		disableIcon();

	}

	private void disableIcon(){
		if (PreWildsRegrown.frame != null) {
			PreWildsRegrown.frame.setVisible(false);
			PreWildsRegrown.frame.dispose();
			PreWildsRegrown.frame = null;
		}
	}

}