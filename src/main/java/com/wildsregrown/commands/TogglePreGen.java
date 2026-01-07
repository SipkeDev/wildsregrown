package com.wildsregrown.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sipke.api.PosTranslator;
import com.sipke.api.grid.WorldGrid;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.chunk.Chunk;

public class TogglePreGen {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((CommandManager.literal("wrg_save_structure").requires((source) -> source.isExecutedByPlayer()))
                        .executes((context) -> handlePreGen(context.getSource()))
        );
    }

    public static int handlePreGen(ServerCommandSource source) {

        ServerWorld serverWorld = source.getWorld();
        WorldGrid grid = null;
        WRGChunkGenerator generator = null;
        if (serverWorld.getChunkManager().getChunkGenerator() instanceof WRGChunkGenerator chunkGenerator){
            grid = chunkGenerator.getWorld().getGrid();
            generator = chunkGenerator;
        }
        if (grid == null){
            return 0;
        }


        int startX = PosTranslator.gridToGlobal(0, grid.getSize());
        int startZ = startX;

        Chunk chunk = serverWorld.getChunk(startX, startZ);

        if (chunk.getStatus() == null){
            WildsRegrown.LOGGER.info("HAALOOOOO");
        }

        return 1;
    }

}
