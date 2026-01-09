package com.wildsregrown.entities.brain;

import net.minecraft.entity.ai.brain.sensor.DummySensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

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
        return (SensorType) Registry.register(Registries.SENSOR_TYPE, Identifier.of(modid, id), new SensorType(factory));
    }

}
