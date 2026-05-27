package com.wildsregrown.registries.world.ecosystem.steppe;

import com.sipke.api.categorization.Climate;
import com.sipke.api.categorization.Placement;
import com.sipke.api.terrain.Ecosystem;
import com.wildsregrown.registries.world.Biomes;

import static com.wildsregrown.WildsRegrown.modid;

public class Plains extends Ecosystem {

    public Plains(){
        super(modid, "plains", Climate.steppe, Biomes.grassfield);
        register(Placement.Biome.normal, Biomes.curly_grassfield);
        register(Placement.Biome.normal, Biomes.thin_grassfield);
        register(Placement.Biome.normal, Biomes.tall_grassfield);
        register(Placement.Biome.normal, Biomes.tall_curly_grassfield);
        register(Placement.Biome.normal, Biomes.tall_thin_grassfield);
        register(Placement.Biome.dry, Biomes.sparse_grassfield);
        register(Placement.Biome.dry, Biomes.dry_grassfield);
        register(Placement.Biome.dry, Biomes.dry_thin_grassfield);
        register(Placement.Biome.dry, Biomes.dry_tall_thin_grassfield);
        register(Placement.Biome.wet, Biomes.wet_grassfield);
        register(Placement.Biome.wet, Biomes.wet_tall_curly_grassfield);
        register(Placement.Biome.wet, Biomes.wet_tall_grassfield);
        register(Placement.Biome.wet, Biomes.dense_grassfield);
        register(Placement.Biome.swamp, Biomes.dense_grassfield);
        register(Placement.Biome.swamp, Biomes.dense_tall_grassfield);
        register(Placement.Biome.lake, Biomes.temperateLake);
        register(Placement.Biome.river, Biomes.temperateRiver);
    }

}
