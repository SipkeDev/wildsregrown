package com.wildsregrown.gui.radial.stack.item;

import com.sipke.api.features.Colors;
import com.wildsregrown.gui.radial.stack.ScreenEntry;
import com.wildsregrown.gui.radial.stack.ScreenStack;

public class StoneChiselScreen extends ScreenStack {

    public StoneChiselScreen() {
        super(new ScreenEntry[2]);
        entries[0] = new ScreenEntry("Rough", Colors.ivory);
        entries[1] = new ScreenEntry("Careful", Colors.whiteLavender);
    }

}
