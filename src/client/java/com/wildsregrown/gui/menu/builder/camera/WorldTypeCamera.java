package com.wildsregrown.gui.menu.builder.camera;

import com.sipke.api.categorization.Climate;
import com.sipke.builder.GridCtx;
import com.sipke.core.Seed;
import com.sipke.math.MathUtil;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.TextureSetup;
import net.minecraft.util.Identifier;

import java.awt.*;

public class WorldTypeCamera extends Camera<GridCtx> implements Drawable, IRenderType {

    private Seed seed;
    private Screen parent;

    public WorldTypeCamera(Screen parent){
        super(256);
        this.parent = parent;
    }

    public void takeShot(GridCtx ctx) {

        this.seed = new Seed(ctx.seed);

        for (int x = 0; x < res; x++) {
            for (int y = 0; y < res; y++) {

                switch (cameraRender) {
                    case region -> {
                        setPixel(x, y, Climate.getRegion(
                                ctx.type.temparature(seed, res).getNoise(x, y),
                                ctx.type.rainfall(seed, res).getNoise(x, y),
                                ctx.type.continent(seed, res).getNoise(x, y)
                        ).getRgb());
                    }
                    case elevation -> {
                        setPixel(x, y, getElevationRGB(ctx.type.continent(seed, res).getNoise(x, y)));
                    }
                    case height -> {
                        setPixel(x, y, Color.HSBtoRGB(0f, 0f, ctx.type.continent(seed, res).getNoise(x, y)));
                    }
                }

            }
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int size = MathUtil.min(parent.width, parent.height);
        context.state.addSimpleElement(new RenderWorldMap(RenderPipelines.GUI, TextureSetup.empty(), context.getMatrices(), this.getImage(), size, res, null));
    }

}