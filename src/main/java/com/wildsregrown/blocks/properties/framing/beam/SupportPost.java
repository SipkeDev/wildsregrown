package com.wildsregrown.blocks.properties.framing.beam;

import net.minecraft.util.StringRepresentable;

public enum SupportPost implements StringRepresentable {

    //Single indentity
    BEAM("beam"),
    CEILING_BRACKET("ceiling_bracket"),
    CEILING_DIAGONAL("ceiling_diagonal");

    private final String name;

    private SupportPost(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

}