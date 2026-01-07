package com.wildsregrown.mixin.client;

import net.minecraft.resource.ResourcePackManager;
import net.minecraft.server.SaveLoader;
import net.minecraft.server.integrated.IntegratedServerLoader;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServerLoader.class)
public abstract class BackupBypass {
	//Disables the annoying question prompt on modded world loading
	@Shadow protected abstract void start(LevelStorage.Session session, SaveLoader saveLoader, ResourcePackManager dataPackManager, Runnable onCancel);
	@Inject(cancellable = true, at = @At(shift = At.Shift.BEFORE,value = "INVOKE", target ="Lnet/minecraft/server/integrated/IntegratedServerLoader;showBackupPromptScreen(Lnet/minecraft/world/level/storage/LevelStorage$Session;ZLjava/lang/Runnable;Ljava/lang/Runnable;)V" ), method = "checkBackupAndStart")
	private void bypass(LevelStorage.Session session, SaveLoader saveLoader, ResourcePackManager dataPackManager, Runnable onCancel, CallbackInfo ci) {
		this.start(session, saveLoader, dataPackManager, onCancel);
		ci.cancel();
	}
}