package com.wildsregrown.gui.menu.builder;

import com.sipke.builder.WorldBuilder;
import com.wildsregrown.gui.menu.BackgroundMessageScreen;
import com.wildsregrown.gui.menu.main.MainMenu;
import com.wildsregrown.gui.menu.world.SelectWorld;
import com.wildsregrown.world.LevelWriter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class IntegratedBuilder {

    private final MinecraftClient client;
    private final WorldBuilder builder;

    public IntegratedBuilder(MinecraftClient client, WorldBuilder builder){
        this.client = client;
        this.builder = builder;
    }

    public void start() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating the world"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyWorldType();
        this.landformGrid();
    }

    public void landformGrid() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating landform grid"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyLandformGrid();
        this.erode();
    }

    public void erode() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Eroding the world"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyErosion();
        this.mainRivers();
    }

    public void mainRivers() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating River Basins"),
                Identifier.of(modid, "gen_world_rivers")
        ));
        this.builder.applyMainRivers();
        this.moisture();
    }

    public void moisture() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Simulating rain"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyMoisture();
        this.ecosystemGrid();
    }

    public void ecosystemGrid() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating ecosystem grid"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyEcosystemGrid();
        this.biomeGrid();
    }

    public void biomeGrid() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating biome grid"),
                Identifier.of(modid, "gen_world_type")
        ));
        this.builder.applyBiomeGrid();
        this.rivers();
    }

    public void rivers() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Creating rivers"),
                Identifier.of(modid, "gen_world_rivers")
        ));
        this.builder.applyRivers();
        this.updateHeightMap();
    }

    public void updateHeightMap() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Updating Heightmap"),
                Identifier.of(modid, "gen_world_rivers")
        ));
        this.builder.updateHeightMap();
        this.caves();
    }

    public void caves() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Generating Caves"),
                Identifier.of(modid, "gen_world_structures")
        ));
        this.builder.buildStrata();
        this.builder.buildCaves();
        this.structures();
    }

    public void structures() {
        this.client.setScreenAndRender(new BackgroundMessageScreen(
                Text.of("Placing structures"),
                Identifier.of(modid, "gen_world_structures")
        ));
        this.builder.applyStructures();
        this.save();
    }

    public void save() {
        this.client.setScreenAndRender(new MessageScreen(Text.of("Saving Grid")));
        this.builder.save();
        LevelWriter.writeLevel(this.builder.ctx);
        this.client.setScreen(new SelectWorld(new MainMenu()));
    }

}
