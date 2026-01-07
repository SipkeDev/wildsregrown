package com.wildsregrown.blocks.properties;


import net.minecraft.util.StringIdentifiable;

import java.awt.*;

/**
Ref https://linoljeprodukter.se/wp-content/uploads/2023/06/webLilla-Handboken-Eng.pdf
*/
public enum LinSeedPaintable implements StringIdentifiable {

    NONE("none", -1),

    CLEAR_WHITE("clear_white", Color.decode("#fdfdf3").getRGB()),
    OLD_WHITE("old_white", Color.decode("#f8f7ea").getRGB()),
    BARLEY_WHITE("barley_white", Color.decode("#ffedbd").getRGB()),
    BENTHEIMER_YELLOW("bentheimer_yellow", Color.decode("#fed47f").getRGB()),
    ANTIQUE_GOLD("antique_gold", Color.decode("#d0942d").getRGB()),
    MONUMENT_GREEN("monument_green", Color.decode("#1e4229").getRGB()),
    SPRUCE_GREEN("spruce_green", Color.decode("#336834").getRGB()),
    WILD_SAGE("wild_sage", Color.decode("#8ca57c").getRGB()),
    DARK_BROWN("dark_brown", Color.decode("#23231a").getRGB()),
    VERONA_BROWN("verona_brown", Color.decode("#323124").getRGB()),
    HOUGHTON_BROWN("houghton_brown", Color.decode("#514e3c").getRGB()),
    CHOCOLATE("chocolate", Color.decode("#4b2824").getRGB()),
    RED("red", Color.decode("#873028").getRGB()),
    SWEDISH_RED("swedish_red", Color.decode("#823523").getRGB()),
    OLD_RED("old_red", Color.decode("#c23b22").getRGB()),
    MIDNIGHT_BLUE("midnight_blue", Color.decode("#073247").getRGB()),
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
    public String asString() {
        return this.name;
    }

    public int getRGB() {
        return this.tint;
    }

}
