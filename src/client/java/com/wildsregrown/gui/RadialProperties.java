package com.wildsregrown.gui;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.framing.Tudor;
import net.minecraft.world.level.block.state.properties.Property;
import wildsregrown.api.WRGApi;
import wildsregrown.api.radial.RadialEntry;
import wildsregrown.api.radial.RadialRegistry;
import wildsregrown.api.radial.stack.PropertyScreenStack;

import java.awt.*;

public class RadialProperties {

    public static void initialize(){
        WRGApi.LOGGER.info("Registering radial stacks");
    }

    static {

        Property<?> p = ModProperties.LINSEED_PAINT;
        RadialEntry[] paints = new RadialEntry[p.getPossibleValues().size()];
        for (int i = 0; i < p.getPossibleValues().size(); i++) {
            LinSeedPaintable paintable = (LinSeedPaintable) p.getPossibleValues().get(i);
            paints[i] = new RadialEntry(paintable.name(), paintable.getRGB());
        }
        register(new PropertyScreenStack<>(p, paints));

        p = ModProperties.TUDOR;
        int k = p.getPossibleValues().size();
        float step = 1f/k;
        RadialEntry[] tudors = new RadialEntry[k];
        for (int i = 0; i < k; i++) {
            float t = 0.5f + 0.25f*(i*step);
            Tudor tudor = (Tudor) p.getPossibleValues().get(i);
            tudors[i] = new RadialEntry(tudor.name(), Color.HSBtoRGB(0.18f, 0.28f, t));
        }
        register(new PropertyScreenStack<>(p, tudors));

    }

    private static void register(PropertyScreenStack<?> stack){
        RadialRegistry.register(stack.getProperty().getName(), stack);
    }

}
