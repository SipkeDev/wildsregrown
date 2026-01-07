package com.wildsregrown.mixin.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.wildsregrown.WildsRegrown.modid;

@Environment(EnvType.CLIENT)
@Mixin(Screen.class)
public abstract class BackgroundMixin {

	@Unique private final Identifier background = Identifier.of(modid, "background");

	@Shadow protected MinecraftClient client;

	@Shadow protected abstract void renderDarkening(DrawContext context);

	@Shadow public int height;

	@Shadow public int width;

	@Inject(cancellable = true, method = "Lnet/minecraft/client/gui/screen/Screen;renderBackground(Lnet/minecraft/client/gui/DrawContext;IIF)V", at = @At("HEAD"))
	public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (this.client.world == null) {
			context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, background, 0, 0, this.width, this.height);
		}

		context.applyBlur();
		this.renderDarkening(context);
		ci.cancel();
	}
}