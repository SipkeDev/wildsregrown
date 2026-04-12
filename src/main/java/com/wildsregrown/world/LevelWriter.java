package com.wildsregrown.world;

import com.wildsregrown.WildsRegrown;
import com.sipke.api.PosTranslator;
import com.sipke.builder.GridCtx;
import com.wildsregrown.world.biomes.WRGBiomes;
import net.minecraft.nbt.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.io.*;
import java.util.Objects;

public abstract class LevelWriter {

    //We assume here that the context is still a 1/1 copy of the just saved grid.
    public static void writeLevel(GridCtx ctx) {

        File folder = new File("saves");
        if (!folder.exists()) {
            return;
        }
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            File level = new File(file, "level.dat");
            if (new File(file, "grid.data").exists() && !(level.exists())) {
                try {
                    NbtCompound dim = new NbtCompound();
                    NbtCompound overworld = new NbtCompound();
                    NbtCompound gen = new NbtCompound();
                    NbtCompound biome = new NbtCompound();
                    overworld.putString("type", "wildsregrown:wrg_dimension");
                    gen.putString("type", "wildsregrown:wrg_chunk");
                    biome.putString("type", "wildsregrown:wrg_biome");

                    NbtList biomes = new NbtList();
                    for (RegistryKey<Biome> b : WRGBiomes.BIOMES){
                        WildsRegrown.LOGGER.info(b.toString());
                        biomes.add(NbtString.of(b.getValue().toString()));
                    }
                    biome.put("biomes", biomes);
                    gen.put("biome_source", biome);
                    gen.putString("settings", "minecraft:overworld");
                    overworld.put("generator", gen);
                    dim.put("minecraft:overworld", overworld);

                    NbtCompound world = new NbtCompound();
                    world.putLong("seed", ctx.seed);
                    world.putByte("generate_features", (byte) 0);
                    world.put("dimensions", dim);

                    NbtCompound data = new NbtCompound();
                    NbtCompound dragon = new NbtCompound();
                    NbtCompound levelNBT = new NbtCompound();
                    data.putString("LevelName", file.getName());
                    data.putInt("version", 19133);
                    data.putInt("DataVersion", 4189);
                    data.putByte("allowCommands", (byte) 1);
                    data.putInt("GameType", ctx.gamemode);
                    data.put("WorldGenSettings", world);
                    data.putInt("SpawnX", PosTranslator.gridToGlobal(ctx.playerSpawn.getX(), ctx.size, ctx.config.getScaleMultiplier()));
                    data.putInt("SpawnZ", PosTranslator.gridToGlobal(ctx.playerSpawn.getZ(), ctx.size, ctx.config.getScaleMultiplier()));
                    data.put("DragonFight", dragon);

                    levelNBT.put("Data", data);

                    FileOutputStream fileOutputStream = new FileOutputStream(level);
                    NbtIo.writeCompressed(levelNBT, fileOutputStream);
                    fileOutputStream.close();

                } catch (Exception var10) {
                    WildsRegrown.LOGGER.error("World translation error");
                    var10.printStackTrace();
                }
            }
        }
    }

}
