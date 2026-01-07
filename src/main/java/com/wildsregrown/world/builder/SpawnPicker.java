package com.wildsregrown.world.builder;

import com.sipke.api.categorization.Climate;
import net.minecraft.text.Text;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.jetbrains.annotations.Nullable;

import java.util.function.IntFunction;

public enum SpawnPicker implements StringIdentifiable {

    //Polar
    ice             (0, "ice", Climate.ice),
    polarDesert     (1, "polar desert", Climate.polarDesert),
    tundra          (2, "tundra", Climate.tundra),

    //Cold
    coniferousForest(3, "coniferious forest", Climate.coniferousForest),
    coolDesert      (4, "cool desert", Climate.coolDesert),
    coolScrubland   (5, "cool shrubland", Climate.coolScrubland),
    mixedForest     (6, "mixed forest", Climate.mixedForest),
    steppe          (7, "steppe", Climate.steppe),

    //hot
    hotDesert       (8, "hot desert", Climate.hotDesert),
    hotScrubland    (9, "hot shrubland", Climate.hotScrubland),
    chaparral       (10, "chaparral", Climate.chaparral),
    deciduousForest (11, "decidious forest", Climate.deciduousForest),

    //Tropical
    ancientForest   (12, "ancient forest", Climate.ancientForest),
    spiritForest    (13, "spirit forest", Climate.spiritForest),
    savanna         (14, "savanna", Climate.savanna);

    public static final EnumCodec<SpawnPicker> CODEC = StringIdentifiable.createCodec(SpawnPicker::values);
    private static final IntFunction<SpawnPicker> BY_ID = ValueLists.createIndexToValueFunction(SpawnPicker::getId, values(), ValueLists.OutOfBoundsHandling.WRAP);

    private final int id;
    private final String name;
    private final Climate climate;

    SpawnPicker(int id, String name, Climate climate){
        this.id = id;
        this.name = name;
        this.climate = climate;
    }

    public int getId() {
        return this.id;
    }

    public Climate getClimate(){
        return this.climate;
    }

    @Override
    public String asString() {
        return name;
    }

    public Text getDisplayText() {
        return Text.literal(this.name);
    }

    public static SpawnPicker byId(int id) {
        return BY_ID.apply(id);
    }

    @Nullable
    public static SpawnPicker byName(String name) {
        return CODEC.byId(name);
    }

}
