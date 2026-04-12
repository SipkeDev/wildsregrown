package com.wildsregrown.world.biomes;

import com.sipke.World;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.registries.world.Ecosystems;
import com.wildsregrown.registries.world.identifiable.IdentifiableRegistery;
import com.wildsregrown.world.WRGChunkGenerator;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.PalettedContainer;

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.WildsRegrown.modid;

public class BiomePopulator {

    private final WRGChunkGenerator generator;
    private final World world;

    public BiomePopulator(WRGChunkGenerator generator){
        this.world = generator.getWorld();
        this.generator = generator;
    }

    public void apply(Chunk chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int x = chunkPos.getStartX();//BiomeCoords.fromBlock(chunkPos.getStartX());
        int z = chunkPos.getStartZ();//BiomeCoords.fromBlock(chunkPos.getStartZ());

        com.sipke.api.chunk.Chunk noiseChunk = world.generator.getNoiseChunk(x, z);

        if (generator.getBiomeSource() instanceof WRGBiomeProvider provider) {
            int center = 8*chunkSize+8;
            PalettedContainer<RegistryEntry<Biome>> container = chunk.getSection(0).getBiomeContainer().slice();
            PalettedContainer<RegistryEntry<Biome>> cave = chunk.getSection(0).getBiomeContainer().slice();
            for (int k = 0; k < 4; k++) {
                for (int m = 0; m < 4; m++) {
                    int idx = (k*4) * chunkSize + (m*4);
                    RegistryEntry<Biome> entry;
                    if (WorldRegistries.BIOMES.get(noiseChunk.getTile(idx).biome) instanceof IdentifiableRegistery id) {
                        entry = provider.getMap().get(id.getIdentifier());
                    }else {
                        String name = WorldRegistries.ECOSYSTEMS.get(noiseChunk.getTile(idx).ecosystem).getClimate().name;
                        entry = provider.getMap().get(Identifier.of(modid, name));
                    }

                    for (int l = 0; l < 4; l++) {
                        container.swapUnsafe(k, l, m, entry);
                    }
                }
            }
            //Identifier caveIdentifier = noiseChunk.getTile(center).flow > 0.5 ? ((VanillaBiome) Biomes.lush_caves.getInstance()).getIdentifier() : ((VanillaBiome) Biomes.dripstone_caves.getInstance()).getIdentifier();
            RegistryEntry<Biome> entry = provider.getMap().get(Identifier.ofVanilla("dripstone_caves"));
            for (int k = 0; k < 4; k++) {
                for (int m = 0; m < 4; m++) {
                    for (int l = 0; l < 4; l++) {
                        cave.swapUnsafe(k, l, m, entry);
                    }
                }
            }
            for (ChunkSection section0 : chunk.getSectionArray()) {
                ((SetableSection) section0).wrg$set(container);
            }
            int height = (int) noiseChunk.getTile(center).height;
            int index = chunk.getSectionIndex(height);
            if (index < 1 || index > 64) {
                index = 1;
            }
            if (index < chunk.getSectionArray().length) {
                ((SetableSection) chunk.getSection(index)).wrg$set(container);
            }
            if (index + 1 < chunk.getSectionArray().length) {
                ((SetableSection) chunk.getSection(index + 1)).wrg$set(container);
            }
            if (index - 1 > 0) {
                ((SetableSection) chunk.getSection(index - 1)).wrg$set(container);
            }
            if (index - 2 > 0) {
                ((SetableSection) chunk.getSection(index - 2)).wrg$set(container);
            }
        }
    }

}
