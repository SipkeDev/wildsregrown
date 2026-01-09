package com.wildsregrown;

import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.wood.tree.Leaves;
import com.wildsregrown.entity.ModEntitiesRenderRegistery;
import com.wildsregrown.gui.KeyBindings;
import com.wildsregrown.gui.SkunkWorksGuiOverlay;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.network.payloads.StructureBlockPayload;
import com.wildsregrown.network.server.ConnectMessage;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.render.item.property.ModItemProperties;
import com.wildsregrown.screen.StructureBlockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.BlockRenderLayer;
import net.minecraft.registry.Registries;

import java.util.Objects;

public class WildsRegrownClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		//ClientPlayNetworking.registerGlobalReceiver(InWorldResults.PACKET_ID, ((payload, context) -> RadialScreen.show(payload.results().split(","), payload.placed())));

		ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sender.sendPacket(new ConnectMessage()));
		ClientPlayNetworking.registerGlobalReceiver(StructureBlockPayload.ID, ((payload, context) -> MinecraftClient.getInstance().setScreen(new StructureBlockScreen(payload))));

		//SkunkWorks
		//ModelLoadingPlugin.register(new ModelRegistry());
		SkunkWorksGuiOverlay.testingTheBlackBird();

		//todo add better compatibility
		boolean bl = true;
		for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
			String modId = mod.getMetadata().getId();
			WildsRegrown.LOGGER.info("Mod: " + modId);
			if (Objects.equals(modId, "replaymod")) {
				bl = false;
			}
		}
		boolean disableMenu = bl;
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof TitleScreen) {
				if (disableMenu) {
					client.setScreen(new MainMenu());
				}
			}
        });

		KeyBindings.register();
		KeyBindings.handleKeybinds();

		/**
		 * This entrypoint is suitable for setting up client-specific logic, such as rendering.
		 */
		ModEntitiesRenderRegistery.register();
		ModBlocks.CUTOUTS.forEach(block -> BlockRenderLayerMap.putBlock(block, BlockRenderLayer.CUTOUT));

		Registries.BLOCK.forEach(ctx -> {
			if (ctx instanceof SoilBlock) {
				BlockRenderLayerMap.putBlock(ctx, BlockRenderLayer.CUTOUT);
			}
			if (ctx instanceof Leaves) {
				BlockRenderLayerMap.putBlock(ctx, BlockRenderLayer.CUTOUT);
			}
		});

		ModItemProperties.init();
		//HandledScreens.register(ModScreenHandlers.STRUCTURE_SCREEN_HANDLER, StructureBlockScreen::new);

	}

}