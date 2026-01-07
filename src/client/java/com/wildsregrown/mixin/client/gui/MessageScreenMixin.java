package com.wildsregrown.mixin.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.wildsregrown.WildsRegrown.modid;

@Environment(EnvType.CLIENT)
@Mixin(MessageScreen.class)
public abstract class MessageScreenMixin {

	@Unique private final Identifier background = Identifier.of(modid, "background");

    @Inject(cancellable = true, method = "Lnet/minecraft/client/gui/screen/MessageScreen;renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V", at = @At("HEAD"))
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		MinecraftClient client = MinecraftClient.getInstance();

		context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());

		//Do blur manual, can't reach super methode.
		client.gameRenderer.renderBlur();
		client.getFramebuffer().blitToScreen();

		ci.cancel();
	}

}