package com.wildsregrown.entity;

import com.google.common.collect.Sets;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

import java.util.Set;

import static com.wildsregrown.WildsRegrown.modid;

public class EntityModelLayers {

    private static final Set<EntityModelLayer> LAYERS = Sets.newHashSet();

    //public static final EntityModelLayer bandit = register("mob/bandit");

    private static EntityModelLayer register(String id) {
        return register(id, "main");
    }

    private static EntityModelLayer register(String id, String layer) {
        EntityModelLayer entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + String.valueOf(entityModelLayer));
        } else {
            return entityModelLayer;
        }
    }

    private static EntityModelLayer create(String id, String layer) {
        return new EntityModelLayer(Identifier.of(modid, id), layer);
    }

}
