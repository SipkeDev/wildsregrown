package com.wildsregrown.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import static com.wildsregrown.WildsRegrown.LOGGER;
import static com.wildsregrown.WildsRegrown.modid;

public class ModScreenHandlers {

    public static final ScreenHandlerType<StructureScreenHandler> STRUCTURE_SCREEN_HANDLER =
            Registry.register(Registries.SCREEN_HANDLER, Identifier.of(modid, "structure_screen_handler"),
                    new ExtendedScreenHandlerType<>(StructureScreenHandler::new, BlockPos.PACKET_CODEC));

    public static void registerScreenHandlers() {
        LOGGER.info("Registering Screen Handlers for " + modid);
    }
}