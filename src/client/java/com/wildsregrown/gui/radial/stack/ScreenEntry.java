package com.wildsregrown.gui.radial.stack;

import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.awt.*;

public class ScreenEntry {

    public final Text text;
    public final int rgb;
    public final ItemStack item;

    public ScreenEntry(String text){
        this.text = Text.of(text);
        this.rgb = Color.lightGray.getRGB();
        this.item = ItemStack.EMPTY;
    }

    public ScreenEntry(String text, int rgb){
        this.text = Text.of(text);
        this.rgb = rgb;
        this.item = ItemStack.EMPTY;
    }

    public ScreenEntry(String text, ItemStack item){
        this.text = Text.of(text);
        this.rgb = Color.lightGray.getRGB();
        this.item = item;
    }

}
