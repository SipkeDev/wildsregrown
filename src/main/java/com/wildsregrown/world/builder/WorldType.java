package com.wildsregrown.world.builder;

import com.sipke.registeries.worldtypes.*;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum WorldType implements StringIdentifiable {

    continent(0, "Continent", new Continent()),
    fractal_continent(1, "Fractal continent", new FractalContinent()),
    new_world(2, "New World", new NewWorld()),
    old_world(3, "Old World", new OldWorld()),
    random(4, "Random", new FullRandom()),
    great_lakes(5, "Great Lakes", new GreatLakes()),
    archipelago(6, "Archipelago", new Archipelago()),
    islands(7, "Islands", new Islands()),
    mediterranean(8, "Mediterranean", new Mediterranean()),
    watering_hole(9, "African watering hole", new AfricanWateringHole()),
    //heightmaps
    europa(10, "Europa", new Europa()),
    england(11, "England", new England()),
    antartica(12, "Antartica", new Antartica()),
    ;

    public static final EnumCodec<WorldType> CODEC = StringIdentifiable.createCodec(WorldType::values);
    private static final IntFunction<WorldType> BY_ID = ValueLists.createIndexToValueFunction(WorldType::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;
    private final com.sipke.registeries.worldtypes.WorldType type;

    WorldType(int id, String name, com.sipke.registeries.worldtypes.WorldType type){
        this.id = id;
        this.name = name;
        this.type = type;
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

    public com.sipke.registeries.worldtypes.WorldType getType() {
        return type;
    }

    public static WorldType byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static WorldType byName(String name) {
        return CODEC.byId(name);
    }

}
