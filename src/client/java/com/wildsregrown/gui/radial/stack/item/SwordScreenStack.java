package com.wildsregrown.gui.radial.stack.item;

import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;

import java.awt.*;

public class SwordScreenStack extends ScreenStack {

    public SwordScreenStack() {
        super(new ScreenEntry[5]);
        entries[0] = new ScreenEntry("Quick", Color.decode("#96a4a5").getRGB());
        entries[1] = new ScreenEntry("Sweeping", Color.decode("#889899").getRGB());
        entries[2] = new ScreenEntry("Stun", Color.decode("#7a8c8d").getRGB());
        entries[3] = new ScreenEntry("Push", Color.decode("#6e7f80").getRGB());
        entries[4] = new ScreenEntry("Skull Crush", Color.decode("#627172").getRGB());
    }

    @Override
    public void mouseClicked(int index) {
    }

}
