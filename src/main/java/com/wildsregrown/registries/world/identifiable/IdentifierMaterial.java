package com.wildsregrown.registries.world.identifiable;

import com.sipke.api.features.Colors;
import com.sipke.api.geology.GeoMaterial;
import net.minecraft.util.Identifier;

public class IdentifierMaterial extends GeoMaterial implements IdentifiableRegistery {

    private final String modid;

    public IdentifierMaterial(String modid, String name, float density, float friction, float thalusAngle, int weathers, int collapses, int transports) {
        super(name, Colors.grey, density, friction, thalusAngle, weathers, collapses, transports);
        this.modid = modid;
    }

    public String getModid() {
        return modid;
    }

    @Override
    public Identifier getIdentifier(){
        return Identifier.of(modid, name);
    }
}
