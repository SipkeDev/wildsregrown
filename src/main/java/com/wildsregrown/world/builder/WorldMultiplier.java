package com.wildsregrown.world.builder;

import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum WorldMultiplier implements StringIdentifiable {

    Half(0, "0.5", 0.5f),
    Normal(1, "1", 1f),
    Double(2, "2", 2f),
    Triple(3, "3", 3f),
    Quadruple(4, "4", 4f);

    public static final EnumCodec<WorldMultiplier> CODEC = StringIdentifiable.createCodec(WorldMultiplier::values);
    private static final IntFunction<WorldMultiplier> BY_ID = ValueLists.createIndexToValueFunction(WorldMultiplier::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;
    private final float value;

    WorldMultiplier(int id, String name, float value){
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public int getId() {
        return this.id;
    }

    public float getValue() {
        return value;
    }

    @Override
    public String asString() {
        return name;
    }

    public Text getDisplayText() {
        return Text.literal(this.name);
    }

    public static WorldMultiplier byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static WorldMultiplier byName(String name) {
        return CODEC.byId(name);
    }

}
