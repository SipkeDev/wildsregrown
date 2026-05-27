package com.wildsregrown.mixin.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.wildsregrown.WildsRegrown.modid;

@Environment(EnvType.CLIENT)
@Mixin(GenericMessageScreen.class)
public abstract class MessageScreenMixin {

	@Unique private final Identifier background = Identifier.fromNamespaceAndPath(modid, "background");

    @Inject(cancellable = true, method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"))
	public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		Minecraft client = Minecraft.getInstance();

		context.blitSprite(RenderPipelines.GUI_TEXTURED, background, 0, 0, client.getWindow().getGuiScaledWidth(), client.getWindow().getGuiScaledHeight());

		//Do blur manual, can't reach super methode.
		client.gameRenderer.processBlurEffect();
		client.getMainRenderTarget().blitToScreen();

		ci.cancel();
	}

}