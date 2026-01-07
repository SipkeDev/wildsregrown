package com.wildsregrown.world.builder;

import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

import static com.sipke.WorldConstants.chunkSize;

public enum WorldSize implements StringIdentifiable {

    arena(0, "Arena", 512),
    tiny(1, "Tiny", 640),
    small(2, "Small", 768),
    medium(3, "Medium", 896),
    large(4, "Large", 1024),
    big(5, "Big", 1152),
    huge(6, "Huge", 1280),
    bigHuge(7, "BigHuge", 1408),
    yes(8, "Yes", 1536);

    public static final StringIdentifiable.EnumCodec<WorldSize> CODEC = StringIdentifiable.createCodec(WorldSize::values);
    private static final IntFunction<WorldSize> BY_ID = ValueLists.createIndexToValueFunction(WorldSize::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);
    private final int id;
    private final String name;
    private final int size;

    WorldSize(int id, String name, int size){
        this.id = id;
        this.name = name;
        this.size = size;
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

    public int getSize() {
        return size;
    }
    public int getWorldSize() {
        return size*chunkSize;
    }

    public static WorldSize byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static WorldSize byName(String name) {
        return CODEC.byId(name);
    }

}
