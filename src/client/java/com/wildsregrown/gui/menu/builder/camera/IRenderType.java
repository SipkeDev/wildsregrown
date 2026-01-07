package com.wildsregrown.gui.menu.builder.camera;

import com.sipke.api.categorization.Placement;

import java.awt.*;

public interface IRenderType {

    default int getElevationRGB(float height){

        switch (Placement.Elevation.get(height)){
            default: return Color.white.getRGB();
            case mountain: return Color.white.getRGB();
            case highland: return Color.decode("#37A53B").getRGB();
            case lowland: return Color.decode("#355036").getRGB();
            case coast: return Color.decode("#CFC123").getRGB();
            case sea: return Color.decode("#00C4FA").getRGB();
            case ocean: return Color.decode("#378DA5").getRGB();
        }

    }

}
