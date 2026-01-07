package com.wildsregrown.gui.menu.builder.camera;

import com.sipke.math.MathUtil;
import net.minecraft.client.gui.Drawable;

public abstract class Camera<T> implements Drawable {

    private final int[] image;
    public final int res;
    public CameraRender cameraRender;

    public Camera(int res){
        this.res = res;
        this.image = new int[res*res];
        this.cameraRender = CameraRender.region;
    }

    public void setPixel(int i, int j, int rgb){
        if (MathUtil.inBounds(i,j, 0, res)) {
            this.image[i * res + j] = rgb;
        }
    }

    public int[] getImage() {
        return image;
    }

    public int getPixel(int i, int j){
        return this.image[i*res+j];
    }

    abstract void takeShot(T ctx);

    public void setRender(CameraRender selectedRender) {
        this.cameraRender = selectedRender;
    }
}
