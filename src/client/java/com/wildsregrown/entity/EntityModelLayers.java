package com.wildsregrown.entity;

import com.google.common.collect.Sets;
import java.util.Set;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public class EntityModelLayers {

    private static final Set<ModelLayerLocation> LAYERS = Sets.newHashSet();

    //public static final EntityModelLayer bandit = register("mob/bandit");

    private static ModelLayerLocation register(String id) {
        return register(id, "main");
    }

    private static ModelLayerLocation register(String id, String layer) {
        ModelLayerLocation entityModelLayer = create(id, layer);
        if (!LAYERS.add(entityModelLayer)) {
            throw new IllegalStateException("Duplicate registration for " + String.valueOf(entityModelLayer));
        } else {
            return entityModelLayer;
        }
    }

    private static ModelLayerLocation create(String id, String layer) {
        return new ModelLayerLocation(Identifier.fromNamespaceAndPath(modid, id), layer);
    }

}
