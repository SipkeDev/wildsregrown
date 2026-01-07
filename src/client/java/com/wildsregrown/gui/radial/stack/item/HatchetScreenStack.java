package com.wildsregrown.gui.radial.stack.item;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;

public class HatchetScreenStack extends ScreenStack {

    public HatchetScreenStack() {
        super(new ScreenEntry[3]);
        entries[0] = new ScreenEntry("Debark", Colors.fern);
        entries[1] = new ScreenEntry("Split", Colors.fernGreen);
        entries[2] = new ScreenEntry("Shaping", Colors.darkPastelGreen);
    }

}
