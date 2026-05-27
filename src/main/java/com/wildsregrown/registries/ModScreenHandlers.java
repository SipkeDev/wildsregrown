package com.wildsregrown.registries;

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