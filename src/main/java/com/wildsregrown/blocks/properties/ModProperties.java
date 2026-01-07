package com.wildsregrown.blocks.properties;

import com.wildsregrown.blocks.properties.connecting.*;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;

public class ModProperties {

    //Basic
    public static final IntProperty LAYERS = IntProperty.of("layers", 1, 8);
    public static final IntProperty QUARTER_LAYERS = IntProperty.of("layers", 1, 4);
    public static final IntProperty OXIDATION = IntProperty.of("oxidation", 0, 3);

    //Paintables
    public static final EnumProperty<LinSeedPaintable> LINSEED_PAINT = EnumProperty.of("paint", LinSeedPaintable.class);

    //Flora cycles
    public static final IntProperty FRUITING = IntProperty.of("fruiting", 0, 3);
    public static final IntProperty OVERGROWN = IntProperty.of("overgrown", 0, 5);
    public static final IntProperty MOISTURE = IntProperty.of("moisture", 0, 16);//Inaccurate 0-1 float
    public static final IntProperty AGE_4 = IntProperty.of("age", 0, 3);
    public static final IntProperty AGE_6 = IntProperty.of("age", 0, 5);
    public static final EnumProperty<FloraStage> FLORAL_STAGE = EnumProperty.of("floral_stage", FloraStage.class);

    //Part states
    public static final EnumProperty<VerticalConnected> VERTICAL_CONNECTED = EnumProperty.of("part", VerticalConnected.class);
    public static final EnumProperty<HorizontalConnected> HORIZONTAL_CONNECTED = EnumProperty.of("part", HorizontalConnected.class);
    public static final EnumProperty<HorizontalCornerConnected> HORIZONTAL_CORNER_CONNECTED = EnumProperty.of("part", HorizontalCornerConnected.class);
    public static final EnumProperty<CornerConnecting> CORNER_CONNECTING = EnumProperty.of("corner", CornerConnecting.class);
    public static final EnumProperty<SupportConnected> SUPPORT_STATE = EnumProperty.of("part", SupportConnected.class);

    //Decorative
    public static final IntProperty VARIATIONS_2 = IntProperty.of("variations", 1, 2);
    public static final IntProperty VARIATIONS_3 = IntProperty.of("variations", 1, 3);
    public static final IntProperty VARIATIONS_4 = IntProperty.of("variations", 1, 4);
    public static final IntProperty VARIATIONS_5 = IntProperty.of("variations", 1, 5);
    public static final IntProperty VARIATIONS_8 = IntProperty.of("variations", 1, 8);

    //Furniture states
    public static final EnumProperty<DrawerState> DRAWER_STATE = EnumProperty.of("drawers", DrawerState.class);

    //Castle States
    public static final EnumProperty<ArchConnected> ARCH = EnumProperty.of("arch", ArchConnected.class);

    //Light sources
    //todo remove FueledLight
    public static final EnumProperty<FueledLight> FUELED_LIGHT = EnumProperty.of("fueled_light", FueledLight.class);
    public static final IntProperty TORCH_FUEL = IntProperty.of("fuel", 0, 15);
    public static final IntProperty FUEL_16 = IntProperty.of("fuel", 0, 16);
    public static final IntProperty FUEL_32 = IntProperty.of("fuel", 0, 32);
    public static final IntProperty FUEL_64 = IntProperty.of("fuel", 0, 64);
    public static final EnumProperty<TorchHolderState> TORCH_HOLDER_STATE = EnumProperty.of("torch_holder", TorchHolderState.class);

}
