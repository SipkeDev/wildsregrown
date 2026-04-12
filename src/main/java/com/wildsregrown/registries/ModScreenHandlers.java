package com.wildsregrown.registries;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;

public class ModScreenHandlers {

    /*
    public static final ScreenHandlerType<PortableAnvilScreenHandler> ANVIL_SCREEN_HANDLER = Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "portable_anvil_screen_handler"),
                    new ScreenHandlerType<>(PortableAnvilScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
*/
    public static void initialize() {
        LOGGER.info("Registering Screen Handlers for " + modid);
    }
}