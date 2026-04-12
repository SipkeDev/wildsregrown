package com.wildsregrown.gui.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.Positioner;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class Crafting extends GridScreenTab {

    public Crafting(TextRenderer textRender) {
        super(Text.of("Help"));
        this.grid.setSpacing(8);
        Positioner positioner = this.grid.copyPositioner();
        GridWidget.Adder adder = this.grid.createAdder(3);
        adder.add(new TextWidget(Text.of("Test"), textRender), positioner);
    }

}