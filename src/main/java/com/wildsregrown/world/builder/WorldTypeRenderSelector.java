package com.wildsregrown.world.builder;

import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum WorldTypeRenderSelector implements StringIdentifiable {

    climate(0, "climate"),
    height(1, "height"),
    temperature(2, "temperature"),
    rainfall(3, "rainfall");

    public static final EnumCodec<WorldTypeRenderSelector> CODEC = StringIdentifiable.createCodec(WorldTypeRenderSelector::values);
    private static final IntFunction<WorldTypeRenderSelector> BY_ID = ValueLists.createIndexToValueFunction(WorldTypeRenderSelector::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;

    WorldTypeRenderSelector(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public String asString() {
        return name;
    }

    public Text getDisplayText() {
        return Text.literal(this.name);
    }

    public static WorldTypeRenderSelector byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static WorldTypeRenderSelector byName(String name) {
        return CODEC.byId(name);
    }

}
