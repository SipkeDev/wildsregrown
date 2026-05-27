package com.wildsregrown.mixin.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
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

	@Unique private final Identifier background = Identifier.fromNamespaceAndPath(modid, "background");

	@Shadow protected Minecraft minecraft;

	@Shadow protected abstract void renderMenuBackground(GuiGraphics context);

	@Shadow public int height;

	@Shadow public int width;

	@Inject(cancellable = true, method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"))
	public void renderBackground(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (this.minecraft.level == null) {
			context.blitSprite(RenderPipelines.GUI_TEXTURED, background, 0, 0, this.width, this.height);
		}

		context.blurBeforeThisStratum();
		this.renderMenuBackground(context);
		ci.cancel();
	}
}