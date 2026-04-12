package com.wildsregrown.gui.menu.builder;

import com.sipke.api.grid.WRGConfig;
import com.sipke.builder.WorldBuilder;
import com.sipke.math.MapType;
import com.sipke.math.MathUtil;
import com.wildsregrown.gui.menu.builder.camera.CameraRender;
import com.wildsregrown.gui.menu.builder.camera.WorldTypeCamera;
import com.wildsregrown.world.builder.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jspecify.annotations.Nullable;

import static com.sipke.WorldConstants.chunkSize;

public class ConfigScreen extends Screen {

    private WorldTypeCamera camera;
    private final WorldBuilder builder;
    private final Screen parent;

    public static final Identifier TAB_HEADER_BACKGROUND_TEXTURE = Identifier.ofVanilla("textures/gui/tab_header_background.png");

    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private @Nullable TabNavigationWidget tabNavigation;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);

    public ConfigScreen(WorldBuilder builder, Screen parent){
        super(Text.literal("config"));
        this.builder = builder;
        this.parent = parent;
    }

    public Screen getParent() {
        return parent;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
        super.render(context, mouseX, mouseY, deltaTicks);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, Screen.FOOTER_SEPARATOR_TEXTURE, 0, this.height - this.layout.getFooterHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
        if (camera != null){
            if (tabManager.getCurrentTab() == tabNavigation.getTabs().getFirst()) {
                camera.render(context, mouseX, mouseY, deltaTicks);
            }
        }
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        context.drawTexture(RenderPipelines.GUI_TEXTURED, TAB_HEADER_BACKGROUND_TEXTURE, 0, 0, 0.0F, 0.0F, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.renderDarkening(context, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    protected void init() {
        //Camera
        camera = new WorldTypeCamera(layout.getContentHeight()-10);
        camera.setRender(CameraRender.climate);
        camera.takeShot(builder.ctx);
        camera.setPosition(0, layout.getHeaderHeight());

        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width).tabs(
                new Tab[]{
                        new Climate(),
                        new WorldTab(),
                        new Filters(),
                        new Features(),
                        new Generator(),
                        new Disclaimer()
                }).build();
        this.addDrawableChild(this.tabNavigation);
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(Text.of("Save"), (button) -> {
            client.setScreen(new SaveConfigScreen(builder, this));
        }).dimensions(0,0, 80, 20).build());
        directionalLayoutWidget.add(ButtonWidget.builder(Text.of("Load"), (button) -> {
            client.setScreen(new ListConfigsScreen(builder, this));
        }).dimensions(0,0, 80, 20).build());
        directionalLayoutWidget.add(ButtonWidget.builder(Text.of("Accept"), (button) -> {
            if (parent instanceof WorldTypeScreen screen){
                screen.getCamera().takeShot(builder.ctx);
            }
            this.client.setScreen(parent);
        }).build());

        this.layout.forEachChild((child) -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);
        this.refreshWidgetPositions();

    }

    protected void setInitialFocus() {}

    public void refreshWidgetPositions() {
        if (this.tabNavigation != null) {
            this.tabNavigation.setWidth(this.width);
            this.tabNavigation.init();
            int i = this.tabNavigation.getNavigationFocus().getBottom();
            ScreenRect screenRect = new ScreenRect(0, i, this.width, this.height - this.layout.getFooterHeight() - i);
            this.tabManager.setTabArea(screenRect);
            this.layout.setHeaderHeight(i);
            this.layout.refreshPositions();
        }
    }

    @Environment(EnvType.CLIENT)
    class Climate extends GridScreenTab {

        Climate() {
            super(Text.of("Climate"));
            int w = 80;
            this.grid.setRowSpacing(8);
            this.grid.setColumnSpacing(5);
            Positioner positioner = this.grid.copyPositioner();

            this.grid.add(new EmptyWidget(width/4,10), 1, 1, positioner);
            this.grid.add(new EmptyWidget(width/4,10), 1, 2, positioner);

            this.grid.add(CyclingButtonWidget.builder(WorldTypeRenderSelector::getDisplayText, WorldTypeRenderSelector.climate).values(WorldTypeRenderSelector.values()).build(0,0, w, 20, Text.literal("Camera"),
                    (button, value) -> {
                camera.setRender(switch (value){
                    case climate -> CameraRender.climate;
                    case height -> CameraRender.height;
                    case temperature -> CameraRender.temperature;
                    case rainfall -> CameraRender.rainfall;
                });
                camera.takeShot(builder.ctx);
            }), 1,3, positioner).setTooltip(Tooltip.of(Text.of("Change camera render")));
            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Height"), textRenderer), 2,3, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getHeightMod())), builder.ctx.config.getHeightMod()+0.5) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value-0.5)));}
                @Override
                protected void applyValue() {
                    builder.ctx.config.setHeightMod((float)value-0.5f);
                    camera.takeShot(builder.ctx);
                }
            }, 3, 3, positioner).setTooltip(Tooltip.of(Text.of("Sets the height offset")));
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getHeightModClamp())), builder.ctx.config.getHeightModClamp()*2.0) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value/2.0)));}
                @Override
                protected void applyValue() {
                    builder.ctx.config.setHeightModClamp((float)(value/2.0));
                    camera.takeShot(builder.ctx);
                }
            }, 4,3, positioner).setTooltip(Tooltip.of(Text.of("Sets the height clamp")));
            this.grid.add(CyclingButtonWidget.builder(MapSelector::getDisplayText, getMappedValue(builder.ctx.config.getHeightType())).values(MapSelector.values()).build(0,0, w, 20, Text.literal("Type"),
                    (button, value) -> {builder.ctx.config.setHeightType(value.getType());camera.takeShot(builder.ctx);}
            ), 5,3, positioner).setTooltip(Tooltip.of(Text.of("Sets the height type")));


            this.grid.add(new EmptyWidget(w,10), 1, 4, positioner);
            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Temperature"), textRenderer), 2, 4, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getTemperature())), builder.ctx.config.getTemperature()+0.5) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value-0.5)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setTemperature((float)value-0.5f);
                    camera.takeShot(builder.ctx);
                }
            }, 3,4, positioner).setTooltip(Tooltip.of(Text.of("Sets the temperature offset")));
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getTemperatureClamp())), builder.ctx.config.getTemperatureClamp()*2.0) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value/2.0)));}
                @Override
                protected void applyValue() {
                    builder.ctx.config.setTemperatureClamp((float)(value/2.0));
                    camera.takeShot(builder.ctx);
                }
            }, 4,4, positioner).setTooltip(Tooltip.of(Text.of("Sets the temperature clamp")));
            this.grid.add(CyclingButtonWidget.builder(MapSelector::getDisplayText, getMappedValue(builder.ctx.config.getTemperatureType())).values(MapSelector.values()).build(0,0, w, 20, Text.literal("Type"),
                    (button, value) -> {builder.ctx.config.setTemperatureType(value.getType());camera.takeShot(builder.ctx);}
            ), 5,4, positioner).setTooltip(Tooltip.of(Text.of("Sets the temperature type")));


            this.grid.add(new EmptyWidget(width/4,10), 1, 5, positioner);
            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("RainFall"), textRenderer), 2, 5, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getRainfall())), builder.ctx.config.getHeightMod()+0.5) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value-0.5)));}
                @Override
                protected void applyValue() {
                    builder.ctx.config.setRainfall((float)value-0.5f);
                    //camera.setRender(CameraRender.temperature);
                    camera.takeShot(builder.ctx);
                }
            }, 3,5, positioner).setTooltip(Tooltip.of(Text.of("Sets the rainfall offset")));
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of(shortenSliderValue(builder.ctx.config.getRainfallClamp())), builder.ctx.config.getRainfallClamp()*2.0) {
                @Override
                protected void updateMessage() {
                    setMessage(Text.of(shortenSliderValue(value/2.0)));}
                @Override
                protected void applyValue() {
                    builder.ctx.config.setRainfallClamp((float)(value/2.0));
                    camera.takeShot(builder.ctx);
                }
            }, 4,5, positioner).setTooltip(Tooltip.of(Text.of("Sets the rainfall clamp")));
            this.grid.add(CyclingButtonWidget.builder(MapSelector::getDisplayText, getMappedValue(builder.ctx.config.getRainfallType())).values(MapSelector.values()).build(0,0, w, 20, Text.literal("Type"),
                    (button, value) -> {builder.ctx.config.setRainfallType(value.getType());camera.takeShot(builder.ctx);}
            ), 5,5, positioner).setTooltip(Tooltip.of(Text.of("Sets the rainfall type")));

        }

    }

    private MapSelector getMappedValue(MapType type) {
        return switch (type){
            case linear -> MapSelector.linear;
            case hermite -> MapSelector.hermite;
            case inverseHermite -> MapSelector.inverseHermite;
            case quintic -> MapSelector.quintic;
            case inverseQuintic -> MapSelector.inverseQuintic;
            case almostUnitIdentity -> MapSelector.almostUnitIdentity;
        };
    }

    @Environment(EnvType.CLIENT)
    class WorldTab extends GridScreenTab {

        private WorldMultiplier getMultiplier() {
            float v = builder.ctx.config.getScaleMultiplier();
            if (v < 1){
                return WorldMultiplier.Half;
            }else if (v == 1f){
                return WorldMultiplier.Normal;
            }else {
                return WorldMultiplier.Double;
            }
        }

        WorldTab() {
            super(Text.of("World"));
            this.grid.setSpacing(8);
            Positioner positioner = this.grid.copyPositioner();

            TextWidget totalSize = new TextWidget(0, 0, 100, 20, Text.literal("Total size: " + builder.ctx.config.getScaleMultiplier()*(builder.ctx.size*chunkSize)), textRenderer);
            totalSize.setTooltip(Tooltip.of(Text.of("Total world height/width in blocks")));
            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("World Scale"), textRenderer), 1, 1);
            this.grid.add(CyclingButtonWidget.builder(WorldSize::getDisplayText, WorldSize.medium).values(WorldSize.values()).build(0,0, 100, 20, Text.literal("World Size"),
                    (button, size) -> {
                        builder.ctx.setSize(size.getSize());
                totalSize.setMessage(Text.literal("Total size: " + builder.ctx.config.getScaleMultiplier()*(builder.ctx.size*chunkSize)));
            }), 2, 1, positioner).setTooltip(Tooltip.of(Text.of("change continent grid size")));
            this.grid.add(CyclingButtonWidget.builder(WorldMultiplier::getDisplayText, getMultiplier()).values(WorldMultiplier.values()).build(0,0, 100, 20, Text.literal("Scale multiplier"),
                    (button, v) -> {
                            builder.ctx.config.setScaleMultiplier(v.getValue());
                            totalSize.setMessage(Text.literal("Total size: " + builder.ctx.config.getScaleMultiplier()*(builder.ctx.size*chunkSize)));
            }), 3, 1, positioner);
            this.grid.add(totalSize, 4,1, positioner).setTooltip(Tooltip.of(Text.of("Sets the continent grid multiplier, 1x : 1 pixel = 1 chunk")));

            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("Cell scale"), textRenderer), 1, 2, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Landform " + shortenSliderValue(builder.ctx.config.getLandformScale())), builder.ctx.config.getLandformScale()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Landform " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setLandformScale((float)value);
                }
            }, 2,2, positioner).setTooltip(Tooltip.of(Text.of("Changes the landform cell width")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Ecosystem " + shortenSliderValue(builder.ctx.config.getEcoSystemScale())), builder.ctx.config.getEcoSystemScale()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Ecosystem " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setEcoSystemScale((float)value);
                }
            }, 3,2, positioner).setTooltip(Tooltip.of(Text.of("Changes the ecosystem cell width")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Biome " + shortenSliderValue(builder.ctx.config.getBiomeScale())), builder.ctx.config.getBiomeScale()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Biome " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setBiomeScale((float)value);
                }
            }, 4,2, positioner).setTooltip(Tooltip.of(Text.of("Changes the biome cell width")));

            //Height/factor
            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("Height Factors"), textRenderer), 1, 3, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Continent " + WRGConfig.calcContinentFactor(builder.ctx.config.getContinentFactor())), builder.ctx.config.getContinentFactor()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Continent " + shortenSliderValue(WRGConfig.calcContinentFactor((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setContinentFactor((float)value);
                }
            }, 2,3, positioner).setTooltip(Tooltip.of(Text.of("Changes the continent height")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Landform " + WRGConfig.calcLandformFactor(builder.ctx.config.getLandformFactor())), builder.ctx.config.getLandformFactor()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Landform: " + shortenSliderValue(WRGConfig.calcLandformFactor((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setLandformFactor((float)value);
                }
            }, 3,3, positioner).setTooltip(Tooltip.of(Text.of("Changes the landform height")));;
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Biome " + WRGConfig.calcBiomeFactor(builder.ctx.config.getBiomeFactor())), builder.ctx.config.getBiomeFactor()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Biome: " + shortenSliderValue(WRGConfig.calcBiomeFactor((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setBiomeFactor((float)value);
                }
            }, 4,3, positioner).setTooltip(Tooltip.of(Text.of("Changes the biome height")));;
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Waterlevel " + shortenSliderValue(MathUtil.range(builder.ctx.config.getWaterLevel(), 0, 1, 0, WRGConfig.calcContinentFactor(builder.ctx.config.getContinentFactor())))), builder.ctx.config.getWaterLevel()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Waterlevel " + shortenSliderValue(MathUtil.range((float)value, 0, 1, 0, WRGConfig.calcContinentFactor(builder.ctx.config.getContinentFactor()))));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setWaterLevel((float)value);
                }
            }, 5,3, positioner).setTooltip(Tooltip.of(Text.of("Changes the water level, relative to continent height")));

        }
    }

    @Environment(EnvType.CLIENT)
    class Filters extends GridScreenTab {

        Filters() {
            super(Text.of("Filters"));
            this.grid.setSpacing(8);

            Positioner positioner = this.grid.copyPositioner();

            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("Erosion"), textRenderer), 1, 1, positioner);
            this.grid.add(CyclingButtonWidget.builder(WorldAge::getDisplayText, switch (builder.ctx.config.getWorldAge()){
                case 0 -> WorldAge.young;
                case 1 -> WorldAge.normal;
                case 2 -> WorldAge.old;
                case 3 -> WorldAge.ancient;
                case 4 -> WorldAge.max;
                default -> throw new IllegalStateException("Unexpected value: " + builder.ctx.config.getWorldAge());
            }).values(WorldAge.values()).build(0,0, 100, 20, Text.literal("World Age"),
                    (button, v) -> {
                        builder.ctx.config.setWorldAge(v.getId());
                    }), 2, 1, positioner).setTooltip(Tooltip.of(Text.of("Changes the amount of cycles, older means more erosion but slower performance.")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Inertia " + shortenSliderValue(builder.ctx.config.getErosionInertia())), builder.ctx.config.getErosionInertia()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Inertia " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setErosionInertia((float)value);
                }
            }, 3,1, positioner).setTooltip(Tooltip.of(Text.of("Changes the tendency to move direction")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Friction " + shortenSliderValue(builder.ctx.config.getErosionFriction())), builder.ctx.config.getErosionFriction()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Friction " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setErosionFriction((float)value);
                }
            }, 4,1, positioner).setTooltip(Tooltip.of(Text.of("Changes how much velocity is kept for each time step")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Capacity " + shortenSliderValue(builder.ctx.config.getErosionCapacity())), builder.ctx.config.getErosionCapacity()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Capacity " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setErosionCapacity((float)value);
                }
            }, 5,1, positioner).setTooltip(Tooltip.of(Text.of("How much sediment the stream can carry")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Rate " + shortenSliderValue(builder.ctx.config.getErosionRate())), builder.ctx.config.getErosionRate()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Rate " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setErosionRate((float)value);
                }
            }, 6,1, positioner).setTooltip(Tooltip.of(Text.of("Rate of picking up and depositing sediment")));

            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("MainRivers"), textRenderer), 1, 2, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Inertia " + shortenSliderValue(builder.ctx.config.getMainRiverInertia())), builder.ctx.config.getMainRiverInertia()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Inertia " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setMainRiverInertia((float)value);
                }
            }, 2,2, positioner).setTooltip(Tooltip.of(Text.of("Changes the tendency of moving direction")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Friction " + shortenSliderValue(builder.ctx.config.getMainRiverFriction())), builder.ctx.config.getMainRiverFriction()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Friction " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setMainRiverFriction((float)value);
                }
            }, 3,2, positioner).setTooltip(Tooltip.of(Text.of("Changes how much velocity is kept each time step")));

            this.grid.add(new TextWidget(0, 0, 100, 20, Text.literal("Rivers"), textRenderer), 4, 2, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Inertia " + shortenSliderValue(builder.ctx.config.getRiverInertia())), builder.ctx.config.getRiverInertia()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Inertia " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setRiverInertia((float)value);
                }
            }, 5,2, positioner).setTooltip(Tooltip.of(Text.of("Changes the tendency of moving direction")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Friction " + shortenSliderValue(builder.ctx.config.getRiverFriction())), builder.ctx.config.getRiverFriction()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Friction " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setRiverFriction((float)value);
                }
            }, 6,2, positioner).setTooltip(Tooltip.of(Text.of("Changes how much velocity is kept each time step")));

        }
            
    }

    @Environment(EnvType.CLIENT)
    class Generator extends GridScreenTab {

        Generator() {
            super(Text.of("Generator"));
            this.grid.setColumnSpacing(8);
            this.grid.setRowSpacing(5);
            Positioner positioner = this.grid.copyPositioner();

            int h0 = 16;

            this.grid.add(CheckboxWidget.builder(Text.of("Flow erosion"), textRenderer).callback((widget, bool)-> builder.ctx.config.setFlowEnabled(bool)).checked(builder.ctx.config.getFlowEnabled()).maxWidth(100).build(), 1, 1, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Inertia " + shortenSliderValue(builder.ctx.config.getFlowInertia())), builder.ctx.config.getFlowInertia()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Inertia " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowInertia((float)value);
                }
            }, 2,1, positioner).setTooltip(Tooltip.of(Text.of("Changes the tendency of moving direction")));
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Friction " + shortenSliderValue(builder.ctx.config.getFlowFriction())), builder.ctx.config.getFlowFriction()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Friction " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowFriction((float)value);
                }
            }, 3,1, positioner).setTooltip(Tooltip.of(Text.of("Changes how much velocity is kept each time step")));
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Rate " + shortenSliderValue(builder.ctx.config.getFlowRate())), builder.ctx.config.getFlowRate()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Rate " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowRate((float)value);
                }
            }, 4,1, positioner).setTooltip(Tooltip.of(Text.of("Changes how much sediment can be transferred")));
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Capacity " + shortenSliderValue(WRGConfig.calcFlowCapacity(builder.ctx.config.getFlowCapacity()))), builder.ctx.config.getFlowCapacity()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Capacity " + shortenSliderValue(WRGConfig.calcFlowCapacity((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowCapacity((float)value);
                }
            }, 5,1, positioner).setTooltip(Tooltip.of(Text.of("Changes how much sediment can be transported")));
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Brush Size " + shortenSliderValue(WRGConfig.calcFlowBrush(builder.ctx.config.getFlowBrush()))), builder.ctx.config.getFlowBrush()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Brush Size " + (WRGConfig.calcFlowBrush((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowBrush((float)value);
                }
            }, 6,1, positioner).setTooltip(Tooltip.of(Text.of("Sets the stream radius, how wide is the flow erosion?")));
            this.grid.add(new SliderWidget(0, 0, 100, h0, Text.of("Power " + shortenSliderValue(WRGConfig.calcFlowPower(builder.ctx.config.getFlowPower()))), builder.ctx.config.getFlowPower()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Power " + shortenSliderValue(WRGConfig.calcFlowPower((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowPower((float)value);
                }
            }, 7,1, positioner).setTooltip(Tooltip.of(Text.of("How many times a spawn position is used")));

            this.grid.add(CheckboxWidget.builder(Text.of("Thermal erosion"), textRenderer).callback((widget, bool)-> builder.ctx.config.setThermalEnabled(bool)).checked(builder.ctx.config.getThermalEnabled()).maxWidth(100).build(), 1, 2, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("ThalusAngle " + shortenSliderValue(WRGConfig.calcThermalOffset(builder.ctx.config.getThermalThalusAngle()))), builder.ctx.config.getThermalThalusAngle()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("ThalusAngle " + shortenSliderValue(WRGConfig.calcThermalOffset((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setThermalThalusAngle((float)value);
                }
            }, 2,2, positioner).setTooltip(Tooltip.of(Text.of("Adds a offset to the material thalus angle")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Threshold " + shortenSliderValue(builder.ctx.config.getThermalThreshold())), builder.ctx.config.getThermalThreshold()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Threshold " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setThermalThreshold((float)value);
                }
            }, 3,2, positioner).setTooltip(Tooltip.of(Text.of("If the erosion mask is lower than the threshold, it erodes.")));

            this.grid.add(CheckboxWidget.builder(Text.of("Hydraulic erosion"), textRenderer).callback((widget, bool)-> builder.ctx.config.setHydroEnabled(bool)).checked(builder.ctx.config.getHydroEnabled()).maxWidth(100).build(), 4, 2, positioner);
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Inertia " + shortenSliderValue(WRGConfig.calcHydroOffset(builder.ctx.config.getHydrologyInertia()))), builder.ctx.config.getHydrologyInertia()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Inertia " + shortenSliderValue(WRGConfig.calcHydroOffset((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setHydrologyInertia((float)value);
                }
            }, 5,2, positioner).setTooltip(Tooltip.of(Text.of("Adds a offset to the material inertia")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Friction " + shortenSliderValue(WRGConfig.calcHydroOffset(builder.ctx.config.getHydrologyFriction()))), builder.ctx.config.getHydrologyFriction()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Friction " + shortenSliderValue(WRGConfig.calcHydroOffset((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setHydrologyFriction((float)value);
                }
            }, 6,2, positioner).setTooltip(Tooltip.of(Text.of("Adds a offset to the material friction")));
            this.grid.add(new SliderWidget(0, 0, 100, 20, Text.of("Capacity " + shortenSliderValue(WRGConfig.calcHydroCapacity(builder.ctx.config.getHydrologyCapacity()))), builder.ctx.config.getHydrologyCapacity()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Capacity " + shortenSliderValue(WRGConfig.calcHydroCapacity((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setHydrologyCapacity((float)value);
                }
            }, 7,2, positioner).setTooltip(Tooltip.of(Text.of("Changes how much sediment can be transported")));

            this.grid.add(new TextWidget(0, 0, 150, 20, Text.literal("Miscellaneous").setStyle(Style.EMPTY.withBold(true)), textRenderer), 1, 3, positioner);
            this.grid.add(new SliderWidget(0, 0, 150, 20, Text.of("Flow Blend " + shortenSliderValue(builder.ctx.config.getFlowBlend())), builder.ctx.config.getFlowBlend()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Flow Blend " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFlowBlend((float)value);
                }
            }, 2,3, positioner).setTooltip(Tooltip.of(Text.of("Does the continent hydro flow map blend in with the terrain?")));
            this.grid.add(new SliderWidget(0, 0, 150, 20, Text.of("Erosion mask " + shortenSliderValue(builder.ctx.config.getErosionMask())), builder.ctx.config.getErosionMask()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Erosion mask" + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setErosionMask((float)value);
                }
            }, 3,3, positioner).setTooltip(Tooltip.of(Text.of("Changes the multiplier how much erosion can interact with the terrain.\n 1 means no erosion, 0 means full erosion interaction \n is naturally populated by the tree spawner in wilds regrown.")));
            this.grid.add(new TextWidget(0, 0, 150, 20, Text.literal("Notes").setStyle(Style.EMPTY.withBold(true)), textRenderer), 3, 3, positioner);
            //this.grid.add(new TextWidget(0, 0, 150, 150, Text.literal("Lorem ipsum lange uitleg over deze shizzle "), textRenderer), 4, 3, positioner);

        }
    }

    @Environment(EnvType.CLIENT)
    class Features extends GridScreenTab {

        Features() {
            super(Text.of("Features"));
            this.grid.setSpacing(8);
            int w = 120;

            Positioner positioner = this.grid.copyPositioner();

            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Caves").setStyle(Style.EMPTY.withBold(true)), textRenderer), 1, 1, positioner);
            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Branchworks"), textRenderer), 2, 1, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Node Distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist(builder.ctx.config.getBranchworksDistance()))), builder.ctx.config.getBranchworksDistance()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Node Distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setBranchworksDistance((float)value);
                }
            }, 3,1, positioner).setTooltip(Tooltip.of(Text.of("Sets the distance of cave nodes, 1 = 16 blocks")));
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Size " + shortenSliderValue(builder.ctx.config.getBranchworksSize())), builder.ctx.config.getBranchworksSize()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Size " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setBranchworksSize((float)value);
                }
            }, 4,1, positioner).setTooltip(Tooltip.of(Text.of("Sets cave size")));;

            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Elongated"), textRenderer), 5, 1, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Node Distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist(builder.ctx.config.getElongatedDistance()))), builder.ctx.config.getElongatedDistance()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Node Distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setElongatedDistance((float)value);
                }
            }, 6,1, positioner).setTooltip(Tooltip.of(Text.of("Sets the distance of cave nodes, 1 = 16 blocks")));;
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Size " + shortenSliderValue(builder.ctx.config.getElongatedSize())), builder.ctx.config.getElongatedSize()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Size " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setElongatedSize((float)value);
                }
            }, 7,1, positioner).setTooltip(Tooltip.of(Text.of("Sets cave size")));;

            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Facture network"), textRenderer), 1, 3, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Node distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist(builder.ctx.config.getFractureDistance()))), builder.ctx.config.getFractureDistance()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Node distance " + shortenSliderValue(WRGConfig.calcCaveNodeDist((float)value)));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFractureDistance((float)value);
                }
            }, 2,3, positioner).setTooltip(Tooltip.of(Text.of("Sets the distance of cave nodes, 1 = 16 blocks")));;
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Size " + shortenSliderValue(builder.ctx.config.getFractureSize())), builder.ctx.config.getFractureSize()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Size " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setFractureSize((float)value);
                }
            }, 3,3, positioner).setTooltip(Tooltip.of(Text.of("Sets cave size")));

            this.grid.add(new TextWidget(0, 0, w, 20, Text.literal("Structures").setStyle(Style.EMPTY.withBold(true)), textRenderer), 1, 4, positioner);
            this.grid.add(new SliderWidget(0, 0, w, 20, Text.of("Distance " + builder.ctx.config.getStructureDistance()), builder.ctx.config.getStructureDistance()) {
                @Override
                protected void updateMessage() {
                    message = Text.of("Distance " + shortenSliderValue(value));
                }
                @Override
                protected void applyValue() {
                    builder.ctx.config.setStructureDistance((float)value);
                }
            }, 2,4, positioner).setTooltip(Tooltip.of(Text.of("Sets the distance multiplier for structure spawns")));

        }
    }

    @Environment(EnvType.CLIENT)
    class Disclaimer extends GridScreenTab {
        Disclaimer() {
            super(Text.of("Disclaimer"));
            GridWidget.Adder adder = this.grid.setRowSpacing(8).createAdder(1);
            Positioner positioner = adder.copyPositioner();
            adder.add(new TextWidget(Text.of("Disclaimer:"), textRenderer), positioner);
            adder.add(new TextWidget(Text.of("Settable values exceed safety bounds."), textRenderer), positioner);
            adder.add(new TextWidget(Text.of("Glitched terrain outside of the default settings are therefore a feature, not a bug."), textRenderer), positioner);
            adder.add(new TextWidget(Text.of("Have fun!"), textRenderer), positioner);
            adder.add(new TextWidget(Text.of("And don't forget to screenshot cool terrain."), textRenderer), positioner);
        }
    }


    private String shortenSliderValue(double value){
        String s = Double.toString(value);
        return s.substring(0, MathUtil.min(value < 0.0 ? 5 : 4, s.length()));
    }

}
