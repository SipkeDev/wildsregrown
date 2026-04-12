package com.wildsregrown.gui.menu.builder.camera;

import com.sipke.math.MathUtil;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.Widget;

import java.util.function.Consumer;

public abstract class Camera<T> implements Widget, Drawable {

    public int size;
    private int x;
    private int y;
    private final int[] image;
    public final int res;
    public CameraRender cameraRender;

    public Camera(int res, int size){
        this.res = res;
        this.size = size;
        this.image = new int[res*res];
        this.cameraRender = CameraRender.climate;
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

    //Widget
    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getWidth() {
        return size;
    }

    @Override
    public int getHeight() {
        return size;
    }

    @Override
    public void forEachChild(Consumer<ClickableWidget> consumer) {}

}
