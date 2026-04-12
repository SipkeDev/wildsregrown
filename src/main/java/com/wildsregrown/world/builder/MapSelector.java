package com.wildsregrown.world.builder;

import com.sipke.math.MapType;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum MapSelector implements StringIdentifiable {

    linear(0, "linear", MapType.linear),
    hermite(1, "hermite", MapType.hermite),
    inverseHermite(2, "inverseHermite", MapType.inverseHermite),
    quintic(3, "quintic", MapType.quintic),
    inverseQuintic(4, "inverseQuintic", MapType.inverseQuintic),
    almostUnitIdentity(5, "almostUnitIdentity", MapType.almostUnitIdentity);

    public static final EnumCodec<MapSelector> CODEC = StringIdentifiable.createCodec(MapSelector::values);
    private static final IntFunction<MapSelector> BY_ID = ValueLists.createIndexToValueFunction(MapSelector::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;
    private final MapType type;

    MapSelector(int id, String name, MapType distance){
        this.id = id;
        this.name = name;
        this.type = distance;
    }

    public int getId() {
        return this.id;
    }

    public MapType getType() {
        return type;
    }

    @Override
    public String asString() {
        return name;
    }

    public Text getDisplayText() {
        return Text.literal(this.name);
    }

    public static MapSelector byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static MapSelector byName(String name) {
        return CODEC.byId(name);
    }

}
