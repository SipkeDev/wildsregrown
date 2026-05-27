package com.wildsregrown;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.carpentry.framing.beam.BeamSupport;
import com.wildsregrown.entity.ModEntitiesRenderRegistery;
import com.wildsregrown.gui.RadialProperties;
import com.wildsregrown.gui.SkunkWorksGuiOverlay;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.registries.ModFluids;
import com.wildsregrown.render.item.property.ModItemProperties;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import wildsregrown.api.block.flora.Flora;
import wildsregrown.api.block.materials.SoilBlock;
import wildsregrown.api.block.render.ITintedBlock;
import wildsregrown.api.block.render.TintUtil;
import wildsregrown.api.block.tree.Leaves;
import wildsregrown.api.block.tree.dynamic.TreeSource;

public class WildsRegrownClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		//SkunkWorks
		//ModelLoadingPlugin.register(new ModelRegistry());
		SkunkWorksGuiOverlay.testingTheBlackBird();
		RadialProperties.initialize();

		ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
			if (screen instanceof TitleScreen) {
				client.setScreen(new MainMenu());
			}
        });

		/**
		 * This entrypoint is suitable for setting up client-specific logic, such as rendering.
		 */
		ModEntitiesRenderRegistery.register();
		RegisterRenderSettings();
		ModItemProperties.init();

		//HandledScreens.register(ModScreenHandlers.ANVIL_SCREEN_HANDLER, AnvilScreen::new);

	}

	 private void RegisterRenderSettings(){

		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.PITCH, ModFluids.PITCH_FLOWING,
				new SimpleFluidRenderHandler(
						Identifier.withDefaultNamespace("block/water_still"),
						Identifier.withDefaultNamespace("block/water_flow"),
						Colors.darkGrey // Custom Color
				));

		float b = 0.32f;
		BuiltInRegistries.BLOCK.forEach(ctx -> {
			if (ctx instanceof TreeSource){
				BlockRenderLayerMap.putBlock(ctx, ChunkSectionLayer.CUTOUT);
			}
			if (ctx instanceof BeamSupport){
				BlockRenderLayerMap.putBlock(ctx, ChunkSectionLayer.CUTOUT);
			}

			if (ctx instanceof SoilBlock) {
				BlockRenderLayerMap.putBlock(ctx, ChunkSectionLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return TintUtil.blend(BiomeColors.getAverageGrassColor(view, pos), tint, b);
					}, ctx);
			}else if (ctx instanceof Flora) {
				BlockRenderLayerMap.putBlock(ctx, ChunkSectionLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return tintIndex == 0 ? TintUtil.blend(BiomeColors.getAverageFoliageColor(view, pos), tint, b) : tint;
				}, ctx);
			}else if (ctx instanceof Leaves) {
				BlockRenderLayerMap.putBlock(ctx, ChunkSectionLayer.CUTOUT);
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> {
					int tint = ((ITintedBlock)ctx).getTint(state, tintIndex);
					if (view == null){return tint;}
					return tintIndex == 0 ? TintUtil.blend(BiomeColors.getAverageFoliageColor(view, pos), tint, b) : tint;
			}, ctx);
			}else if (ctx instanceof ITintedBlock){
				ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> ((ITintedBlock)ctx).getTint(state, tintIndex), ctx);
			}

			/*
			if (ctx instanceof IRenderType renderType){
				BlockRenderLayerMap.putBlock(ctx, switch(renderType.getRenderType()) {
					case 1 -> ChunkSectionLayer.CUTOUT;
					case 2 -> ChunkSectionLayer.TRANSLUCENT;
					case 3 -> ChunkSectionLayer.TRIPWIRE;
					default -> ChunkSectionLayer.SOLID;
				});
			}

			 */
		});
	}

}