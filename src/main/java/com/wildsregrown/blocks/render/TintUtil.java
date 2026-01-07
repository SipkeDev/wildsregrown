package com.wildsregrown.blocks.render;

import com.sipke.math.MathUtil;

import static com.sipke.api.features.Colors.pack;

/**
 * Utility class to help blocks calculate their rgb tint
 */
public class TintUtil {

    public static int[] buildOverlayMap(int tint, int overlay0, int overlay1, int size, float filterDensity){
       int[] buffer = new int[size];
       for (int i = 0; i < size; i++) {
           if (i < size/2) {
               buffer[i] = overlay(tint, overlay0, filterDensity - MathUtil.range(i, 0, size/2f, 0f, filterDensity));
           } else {
               buffer[i] = overlay(tint, overlay1, MathUtil.range(i, size/2f, size, 0f, filterDensity));
           }
       }
       return buffer;
    }

    public static int[] buildBlend(int tint, int blend0, int size){
        int[] buffer = new int[size];
        for (int i = 0; i < size; i++) {
            float t = MathUtil.range(i, 0, size, 0f, 1f);
            buffer[i] = blend(tint, blend0, t);
        }
        return buffer;
    }

    public static int[] buildBlendMap(int tint, int blend0, int blend1, int size){
        int[] buffer = new int[size];
        for (int i = 0; i < size; i++) {
            if (i < size/2) {
                float t = MathUtil.range(i, 0, size/2f, 0f, 1f);
                buffer[i] = blend(tint, blend0, t);
            } else {
                float t = 1f-MathUtil.range(i, size/2f, size, 0f, 1f);
                buffer[i] = blend(tint, blend1, t);
            }
        }
        return buffer;
    }

    public static int blend(int tint, int tint2, float t0){

        int red0    = (tint >>16) &0x0ff;
        int green0  = (tint >>8)  &0x0ff;
        int blue0   = (tint)      &0x0ff;

        int red1    = (tint2 >>16) &0x0ff;
        int green1  = (tint2 >>8)  &0x0ff;
        int blue1   = (tint2)      &0x0ff;

        float t1 = 1f-t0;

        int r = (int) ((red0   * t0) + (red1   * t1));
        int g = (int) ((green0 * t0) + (green1 * t1));
        int b = (int) ((blue0  * t0) + (blue1  * t1));

        return pack(r,g,b);

    }

    public static int overlay(int tint, int overlay, float alpha) {

        int red = (tint >>16) &0x0ff;
        int green=(tint >>8)  &0x0ff;
        int blue= (tint)      &0x0ff;

        int overlayRed =   (overlay >> 16) &0xFF;
        int overlayGreen = (overlay >> 8)  &0xFF;
        int overlayBlue =  (overlay >> 0)  &0xFF;

        int r = (int) (red   +  (overlayRed   * alpha));
        int g = (int) (green +  (overlayGreen * alpha));
        int b = (int) (blue  +  (overlayBlue  * alpha));

        return pack(r,g,b);

    }

}
