package com.wildsregrown.blocks.properties;

import com.wildsregrown.blocks.properties.connecting.*;
import com.wildsregrown.blocks.properties.framing.Tudor;
import com.wildsregrown.blocks.properties.fuel.FuelBurn;
import com.wildsregrown.blocks.properties.fuel.FueledLight;
import com.wildsregrown.blocks.properties.framing.beam.SupportConnected;
import com.wildsregrown.blocks.properties.metal.TorchHolderState;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModProperties {

    //Basic
    public static final IntegerProperty OXIDATION = IntegerProperty.create("oxidation", 0, 3);

    //Paintables
    public static final EnumProperty<LinSeedPaintable> LINSEED_PAINT = EnumProperty.create("paint", LinSeedPaintable.class);

    //Flora cycles
    public static final IntegerProperty FRUITING = IntegerProperty.create("fruiting", 0, 3);

    //Part states
    public static final EnumProperty<SupportConnected> SUPPORT_STATE = EnumProperty.create("part", SupportConnected.class);
    public static final EnumProperty<ArrowSlitConnected> ARROW_SLIT_CONNECTED = EnumProperty.create("part", ArrowSlitConnected.class);

    //Decorative
    public static final IntegerProperty VARIATIONS_2 = IntegerProperty.create("variations", 1, 2);
    public static final IntegerProperty VARIATIONS_3 = IntegerProperty.create("variations", 1, 3);
    public static final IntegerProperty VARIATIONS_4 = IntegerProperty.create("variations", 1, 4);

    //Castle States
    public static final EnumProperty<ArchConnected> ARCH = EnumProperty.create("arch", ArchConnected.class);

    //Light sources
    //todo remove FueledLight
    public static final EnumProperty<FueledLight> FUELED_LIGHT = EnumProperty.create("fueled_light", FueledLight.class);
    public static final EnumProperty<FuelBurn> FUEL_BURN = EnumProperty.create("burning", FuelBurn.class);
    public static final IntegerProperty TORCH_FUEL = IntegerProperty.create("fuel", 0, 15);
    public static final IntegerProperty FUEL_6 = IntegerProperty.create("fuel", 0, 6);
    public static final IntegerProperty FUEL_32 = IntegerProperty.create("fuel", 0, 32);
    public static final EnumProperty<TorchHolderState> TORCH_HOLDER_STATE = EnumProperty.create("torch_holder", TorchHolderState.class);

    public static final EnumProperty<Tudor> TUDOR = EnumProperty.create("shape", Tudor.class);

    public static final EnumProperty<Quadrant> QUADRANT = EnumProperty.create("quadrant", Quadrant.class);
    public static final EnumProperty<OrdinalDirection> DIRECTIONS = EnumProperty.create("ordinal", OrdinalDirection.class);



}
