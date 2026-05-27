package com.wildsregrown.blocks.properties.tree;


import java.awt.*;
import net.minecraft.util.StringRepresentable;

/**
Ref https://linoljeprodukter.se/wp-content/uploads/2023/06/webLilla-Handboken-Eng.pdf
*/
public enum LinSeedPaintable implements StringRepresentable {

    NONE("none", -1),

    OLD_WHITE("old_white", Color.decode("#f8f7ea").getRGB()),
    BENTHEIMER_YELLOW("bentheimer_yellow", Color.decode("#fed47f").getRGB()),
    ANTIQUE_GOLD("antique_gold", Color.decode("#d0942d").getRGB()),
    MONUMENT_GREEN("monument_green", Color.decode("#1e4229").getRGB()),
    SPRUCE_GREEN("spruce_green", Color.decode("#336834").getRGB()),
    DARK_BROWN("dark_brown", Color.decode("#23231a").getRGB()),
    VERONA_BROWN("verona_brown", Color.decode("#323124").getRGB()),
    RED("red", Color.decode("#873028").getRGB()),
    SWEDISH_RED("swedish_red", Color.decode("#823523").getRGB()),
    OLD_BLUE("old_blue", Color.decode("#003e52").getRGB()),
    LINSEED_BLUE("linseed_blue", Color.decode("#408d93").getRGB()),
    BLACK("black", Color.decode("#090c0c").getRGB())
    ;

    private final String name;
    private final int tint;

    LinSeedPaintable(String name, int tintIndex) {
        this.name = name;
        this.tint = tintIndex;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int getRGB() {
        return this.tint;
    }

}
