package com.wildsregrown.gui.menu.builder.camera;

import com.sipke.World;
import com.sipke.api.PosTranslator;
import com.sipke.api.cell.BiomeCell;
import com.sipke.api.cell.Cell;
import com.sipke.api.cell.EcoSystemCell;
import com.sipke.api.features.Colors;
import com.sipke.api.features.structures.StructureSpawn;
import com.sipke.api.grid.WorldGrid;
import com.sipke.api.rivers.River;
import com.sipke.api.rivers.RiverBasin;
import com.sipke.api.terrain.Ecosystem;
import com.sipke.api.terrain.Landform;
import com.sipke.generator.heightmap.task.height.TileCell;
import com.sipke.math.Distance;
import com.sipke.math.MathUtil;
import com.sipke.registeries.Ecosystems;
import com.sipke.registeries.Landforms;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.render.state.ColoredQuadGuiElementRenderState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.TextureSetup;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2f;

import java.awt.*;
import java.util.ArrayList;

public class GridCamera extends Camera<WorldGrid> implements Drawable, IRenderType {

    private final Screen parent;
    private final int size;

    public GridCamera(Screen parent, int size){
        super(256);
        this.parent = parent;
        this.size = size;
    }

    public void takeShot(WorldGrid grid) {

        renderSelectedLayer(grid);
        renderRivers(grid);
        renderStructures(grid);

    }

    private void renderSelectedLayer(WorldGrid grid) {
        for (int x = 0; x < res; x++) {
            for (int y = 0; y < res; y++) {

                int dx = translateCoord(x);
                int dy = translateCoord(y);

                switch (cameraRender){

                    case region -> {
                        TileCell tileCell = getPos(grid.getEcosystems(), dx, dy);
                        Ecosystem ecosystem = Ecosystems.get(tileCell.getCell().getConfig());
                        setPixel(x, y, ecosystem.getClimate().getRgb());
                    }
                    case height -> {
                        setPixel(x, y, Color.HSBtoRGB(0f, 0f, grid.getPos(dx, dy).getHeight()));
                    }
                    case elevation -> {
                        setPixel(x, y, getElevationRGB(grid.getPos(dx, dy).getHeight()));
                    }
                    case temperature -> {
                        setPixel(x, y, Color.HSBtoRGB(0f, 0f, grid.getPos(dx, dy).getTemperature()));
                    }
                    case moisture -> {
                        float m = grid.getPos(dx, dy).getMoisture();
                        if (m >= 0.95f){
                            setPixel(x, y, Colors.pastelRed);
                        }else if (m > 0.7){//wet
                            setPixel(x, y, Colors.darkPastelGreen);
                        }else if (m > 0.3){//temperate
                            setPixel(x, y, Colors.pastelGreen);
                        }else{//dry
                            setPixel(x, y, Colors.pastelYellow);
                        }
                    }
                    case landform -> {
                        TileCell tileCell = getPos(grid.getLandforms(), dx, dy);
                        Landform landform = Landforms.get(tileCell.getCell().getConfig());
                        setPixel(x, y, landform.elevation.rgb);
                    }
                    case landform_edge -> {
                        TileCell tileCell = getPos(grid.getLandforms(), dx, dy);
                        setPixel(x, y, Color.HSBtoRGB(0f, 0f, tileCell.getEdge()));
                    }
                    case ecosystem_edge -> {
                        TileCell tileCell = getPos(grid.getEcosystems(), dx, dy);
                        setPixel(x, y, Color.HSBtoRGB(0f, 0f, tileCell.getEdge()));
                    }

                }

            }
        }
    }


    private void renderRivers(WorldGrid grid) {

        //Let's dive in, and wait
        for (RiverBasin basin : grid.getRiverBasins()){
            World.LOGGER.info(basin.toString());
            for (River river : basin.getRivers()){
                for (int i = 0; i < river.path.size()-1; i++) {

                    int dx = translateOverlay(river.path.get(i).x);
                    int dy = translateOverlay(river.path.get(i).z);
                    int dx0 = translateOverlay(river.path.get(i+1).x);
                    int dy0 = translateOverlay(river.path.get(i+1).z);

                    findLine(dx, dy, dx0, dy0, river.path.get(i).lake ? Color.cyan.getRGB() : Color.blue.getRGB());

                }
            }
        }

    }

    public void findLine(int x0, int z0, int x1, int z1, int color) {
       
        int dx = MathUtil.abs(x1 - x0), dy = MathUtil.abs(z1 - z0);
        int sx = x0 < x1 ? 1 : -1, sy = z0 < z1 ? 1 : -1, err = dx-dy, e2;

        while (true) {
            setPixel(x0, z0, color);
            if (x0 == x1 && z0 == z1)
                break;
            e2 = 2 * err;
            if (e2 > -dy)
            {
                err = err - dy;
                x0 = x0 + sx;
            }
            if (e2 < dx)
            {
                err = err + dx;
                z0 = z0 + sy;
            }
        }
    }

    private void renderStructures(WorldGrid grid) {

        for (EcoSystemCell ecoSystemCell : grid.getEcosystems()){
            for (BiomeCell biomeCell : ecoSystemCell.getBiomes()){
                for (StructureSpawn spawn : biomeCell.getStructures()){

                    float x = PosTranslator.globalToGrid(spawn.getX(), grid.getSize());
                    float z = PosTranslator.globalToGrid(spawn.getZ(), grid.getSize());
                    int dx = translateOverlay(x);
                    int dy = translateOverlay(z);
                    setPixel(dx, dy, Colors.darkPastelRed);
                }
            }
        }

    }


    private int translateCoord(int v){
        return (int) MathUtil.range(v, 0, this.res, 0, size);
    }
    private int translateOverlay(float v){
        return (int) MathUtil.range(v, 0, size, 0, this.res);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        int size = MathUtil.min(parent.width, parent.height);
        context.state.addSimpleElement(new RenderWorldMap(RenderPipelines.GUI, TextureSetup.empty(), context.getMatrices(), this.getImage(), size, res, null));

    }

    private <T extends Cell> TileCell getPos(ArrayList<T> cells, float x, float z){

        float dist0 = Float.MAX_VALUE;
        float dist1 = Float.MAX_VALUE;
        Cell closestCell = null;

        for (T cell : cells){

            float dx = cell.getX() - x;
            float dz = cell.getZ() - z;
            float newDist = Distance.chebyShev.apply(dx, dz);

            dist1 = MathUtil.max(MathUtil.min(dist1, newDist), dist0);
            if (newDist < dist0)
            {
                dist0 = newDist;
                closestCell = cell;
            }

        }

        float edge = MathUtil.range(MathUtil.abs(dist1 - dist0), 0, MathUtil.abs(dist0 + dist1), 0f, 1f);
        return new TileCell(closestCell, edge);

    }

}