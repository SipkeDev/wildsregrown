package com.wildsregrown.blocks.properties.framing.beam;

import net.minecraft.util.StringRepresentable;

public enum SupportDiagonal implements StringRepresentable {

    //Single indentity
    DIAGONAL("diagonal"),
    DIAGONAL_BRACKET("diagonal_bracket"),
    DIAGONAL_CEILING("diagonal_ceiling"),
    DIAGONAL_POST("diagonal_post"),
    DIAGONAL_ON_POST("diagonal_on_post")
    ;

    private final String name;

    private SupportDiagonal(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }

}