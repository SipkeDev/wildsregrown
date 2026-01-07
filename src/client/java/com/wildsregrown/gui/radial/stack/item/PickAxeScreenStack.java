package com.wildsregrown.gui.radial.stack.item;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;

public class PickAxeScreenStack extends ScreenStack {

    public PickAxeScreenStack() {
        super(new ScreenEntry[3]);
        entries[0] = new ScreenEntry("Mining", Colors.lightBlue);
        entries[1] = new ScreenEntry("Splitting", Colors.pastelBlue);
        entries[2] = new ScreenEntry("Shaping", Colors.darkPastelBlue);
    }

}
