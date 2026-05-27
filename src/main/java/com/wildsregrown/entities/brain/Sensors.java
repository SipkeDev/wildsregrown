package com.wildsregrown.entities.brain;

import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.sensing.DummySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;

import static com.wildsregrown.WildsRegrown.modid;

public class Sensors extends SensorType<DummySensor> {

    /**
     * General
     */

    /**
     * Behavior
     */
    //public static final SensorType<NearestEnemiesSensor> nearest_enemies = register("nearest_enemies", () -> new NearestEnemiesSensor());

    public Sensors() {
        super(new Supplier<>() {
            @Override
            public DummySensor get() {
                return new DummySensor();
            }
        });
    }

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return (SensorType) Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(modid, id), new SensorType(factory));
    }

}
