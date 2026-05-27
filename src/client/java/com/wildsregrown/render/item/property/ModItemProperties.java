package com.wildsregrown.render.item.property;

import static com.wildsregrown.WildsRegrown.modid;
import static net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperties.ID_MAPPER;

import net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperties;
import net.minecraft.resources.Identifier;

public class ModItemProperties {

    public static void init(){
        RangeSelectItemModelProperties.ID_MAPPER.put(Identifier.fromNamespaceAndPath(modid,"oxidation"), ItemOxidationProperty.CODEC);
    }

}
