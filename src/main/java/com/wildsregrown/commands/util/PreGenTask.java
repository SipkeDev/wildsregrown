package com.wildsregrown.commands.util;

import com.sipke.World;
import com.sipke.api.heightmap.HeightMap;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerChunkLoadingManager;
import net.minecraft.server.world.ServerChunkManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;
import java.util.function.BooleanSupplier;

public class PreGenTask {

    private final MinecraftServer server;
    private final ServerChunkManager chunkManager;
    private final ServerWorld serverLevel;
    private final World world;

    public PreGenTask(MinecraftServer server, World world){
        this.server = server;
        this.chunkManager = server.getOverworld().getChunkManager();
        this.serverLevel = server.getOverworld();
        this.world = world;
    }

    private void init (){
        //Queue
        chunkManager.tick(()->true, true);

        ServerChunkLoadingManager loadingManager = this.chunkManager.chunkLoadingManager;


    }

    //public static final ChunkTicketType<ChunkPos> pregen = ChunkTicketType.create("pregen", Comparator.comparingLong(ChunkPos::toLong));

    public void apply(int x, int z){



    }

    public void createChunk(int x, int z){

        //HeightMap heightMap = chunkManager.get;



    }

}
