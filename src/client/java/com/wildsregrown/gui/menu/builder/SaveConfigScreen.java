package com.wildsregrown.gui.menu.builder;

import com.sipke.api.grid.GridLoader;
import com.sipke.builder.WorldBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.Text;

public class SaveConfigScreen extends Screen {

    private final WorldBuilder builder;
    private final Screen parent;
    private TextFieldWidget configName;

    public SaveConfigScreen(WorldBuilder builder, Screen parent){
        super(Text.literal("Save Config"));
        this.builder = builder;
        this.parent = parent;
    }

    @Override
    protected void init() {

        int w = 120, h = 22, m = 2;
        int x = (this.width/2)-(w/2);
        int y = (this.height/2)-(h+h);

        this.configName = new TextFieldWidget(textRenderer, x, y += (h+m), w , h, Text.literal(builder.ctx.name));
        this.configName.setText(builder.ctx.name + " config");
        this.configName.setTooltip(Tooltip.of(Text.literal("Set config name")));
        this.configName.setChangedListener((input)-> this.builder.ctx.name = input);
        this.addDrawableChild(configName);

        ButtonWidget save = ButtonWidget.builder(Text.of("Save"), button -> {
            GridLoader.saveConfig(builder.ctx.config, configName.getText());
            SystemToast toast = SystemToast.create(client, SystemToast.Type.PERIODIC_NOTIFICATION, Text.of("Saved config"), Text.of(configName.getText()));
            client.getToastManager().add(toast);
            this.client.setScreen(parent);
        }).dimensions(x, y += (h+m), w, h).build();
        ButtonWidget cancel = ButtonWidget.builder(Text.of("Cancel"), button -> {
            this.client.setScreen(parent);
        }).dimensions(x, y += (h+m), w, h).build();
        this.addDrawableChild(save);
        this.addDrawableChild(cancel);

    }

}
