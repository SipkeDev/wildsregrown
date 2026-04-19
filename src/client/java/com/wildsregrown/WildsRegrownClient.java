package com.wildsregrown;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.SoilBlock;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.fluids.SweetWaterFluid;
import com.wildsregrown.blocks.fluids.SweetWaterFluidBlock;
import com.wildsregrown.blocks.render.IRenderType;
import com.wildsregrown.blocks.render.ITintedBlock;
import com.wildsregrown.blocks.render.TintUtil;
import com.wildsregrown.blocks.wood.tree.Leaves;
import com.wildsregrown.entity.ModEntitiesRenderRegistery;
import com.wildsregrown.gui.KeyBindings;
import com.wildsregrown.gui.SkunkWorksGuiOverlay;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.network.payloads.StructureBlockPayload;
import com.wildsregrown.network.server.ConnectMessage;
import com.wildsregrown.registries.ModBlocks;
import com.wildsregrown.registries.ModFluids;
import com.wildsregrown.render.item.property.ModItemProperties;
import com.wildsregrown.screen.StructureBlockScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRendering;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.impl.client.rendering.fluid.FluidRenderHandlerRegistryImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
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
		final boolean disableMenu = bl;
		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof TitleScreen) {
				if (disableMenu) {
					client.setScreen(new MainMenu());
				}
			}
        });

		KeyBindings.register();

		/**
		 * This entrypoint is suitable for setting up client-specific logic, such as rendering.
		 */
		ModEntitiesRenderRegistery.register();

		RegisterRenderSettings();

		ModItemProperties.init();

		//HandledScreens.register(ModScreenHandlers.ANVIL_SCREEN_HANDLER, AnvilScreen::new);

	}

	private void RegisterRenderSettings(){

		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.SWEET_WATER, ModFluids.SWEET_WATER_FLOWING, SimpleFluidRenderHandler.coloredWater(Colors.riverBlue));
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.PITCH, ModFluids.PITCH_FLOWING, SimpleFluidRenderHandler.coloredWater(Colors.black));
		BlockRenderLayerMap.putFluid(ModFluids.SWEET_WATER, BlockRenderLayer.TRANSLUCENT);
		BlockRenderLayerMap.putFluid(ModFluids.SWEET_WATER_FLOWING, BlockRenderLayer.TRANSLUCENT);
		ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> BiomeColors.getWaterColor(view, pos), ModBlocks.sweet_water);

		BlockRenderLayerMap.putBlock(ModBlocks.sweet_water, BlockRenderLayer.TRANSLUCENT);

		float b = 0.32f;
		Registries.BLOCK.forEach(ctx -> {
			if (ctx instanceof SoilBlock) {
				BlockRenderLayerMap.putBlock(ctx, BlockRenderLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return TintUtil.blend(BiomeColors.getGrassColor(view, pos), tint, b);
					}, ctx);
			}else if (ctx instanceof Flora) {
				BlockRenderLayerMap.putBlock(ctx, BlockRenderLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return tintIndex == 0 ? TintUtil.blend(BiomeColors.getFoliageColor(view, pos), tint, b) : tint;
				}, ctx);
			}else if (ctx instanceof Leaves) {
				BlockRenderLayerMap.putBlock(ctx, BlockRenderLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return tintIndex == 0 ? TintUtil.blend(BiomeColors.getFoliageColor(view, pos), tint, b) : tint;
			}, ctx);
			}else if (ctx instanceof ITintedBlock){
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ((ITintedBlock)ctx).getTint(state, tintIndex), ctx);
			}

			if (ctx instanceof IRenderType renderType){
				BlockRenderLayerMap.putBlock(ctx, switch(renderType.getRenderType()) {
					case 1 -> BlockRenderLayer.CUTOUT;
					case 2 -> BlockRenderLayer.TRANSLUCENT;
					case 3 -> BlockRenderLayer.TRIPWIRE;
					default -> BlockRenderLayer.SOLID;
				});
			}
		});
	}

}