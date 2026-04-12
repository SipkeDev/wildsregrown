package com.wildsregrown.world.builder;

import com.sipke.math.Distance;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum DistanceSelector implements StringIdentifiable {

    euclidean(0, "euclidean", Distance.euclidean),
    manhattan(1, "manhattan", Distance.manhattan),
    hybrid(2, "hybrid", Distance.hybrid),
    chebyshev(3, "chebyshev", Distance.chebyShev);

    public static final EnumCodec<DistanceSelector> CODEC = StringIdentifiable.createCodec(DistanceSelector::values);
    private static final IntFunction<DistanceSelector> BY_ID = ValueLists.createIndexToValueFunction(DistanceSelector::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;
    private final Distance distance;

    DistanceSelector(int id, String name, Distance distance){
        this.id = id;
        this.name = name;
        this.distance = distance;
    }

    public int getId() {
        return this.id;
    }

    public Distance getDistance() {
        return distance;
    }

    @Override
    public String asString() {
        return name;
    }

    public Text getDisplayText() {
        return Text.literal(this.name);
    }

    public static DistanceSelector byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static DistanceSelector byName(String name) {
        return CODEC.byId(name);
    }

}
