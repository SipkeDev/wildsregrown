package com.wildsregrown.render.item.property;

import net.minecraft.client.render.item.property.numeric.NumericProperties;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;
import static net.minecraft.client.render.item.property.bool.BooleanProperties.ID_MAPPER;

public class ModItemProperties {

    public static void init(){
        NumericProperties.ID_MAPPER.put(Identifier.of(modid,"oxidation"), ItemOxidationProperty.CODEC);
    }

}
