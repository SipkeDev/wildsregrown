package com.wildsregrown.world.builder;

import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum WorldAge implements StringIdentifiable {

    young(0, "Young"),
    normal(1, "Normal"),
    old(2, "Old"),
    ancient(3, "Ancient");

    public static final EnumCodec<WorldAge> CODEC = StringIdentifiable.createCodec(WorldAge::values);
    private static final IntFunction<WorldAge> BY_ID = ValueLists.createIndexToValueFunction(WorldAge::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;

    WorldAge(int id, String name){
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

    public static WorldAge byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static WorldAge byName(String name) {
        return CODEC.byId(name);
    }

}
