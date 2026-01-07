package com.wildsregrown.blocks.properties;

import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;

public enum OrdinalDirection implements StringIdentifiable  {
    N   ("n" , 0,0,-1),
    NE  ("ne", 1,0,-1),
    E   ("e" , 1,0, 0),
    SE  ("se", 1,0, 1),
    S   ("s" , 0,0, 1),
    SW  ("sw",-1,0, 1),
    W   ("w" ,-1,0, 0),
    NW  ("nw",-1,0,-1);

    private final String name;
    private final int x;
    private final int y;
    private final int z;
    public static final EnumProperty<OrdinalDirection> DIRECTIONS = EnumProperty.of("ordinal", OrdinalDirection.class);

    OrdinalDirection(String name, int x, int y, int z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public static OrdinalDirection getOrdinal(Direction direction) {
        switch (direction) {
            default     -> {return N;}
            case WEST   -> {return W;}
            case EAST   -> {return E;}
            case SOUTH  -> {return S;}
        }
    }
    public Direction getCardinal() {
        switch (this) {
            default -> {return Direction.NORTH;}
            case W  -> {return Direction.WEST;}
            case E  -> {return Direction.EAST;}
            case S  -> {return Direction.SOUTH;}
        }
    }
    public OrdinalDirection clockwise() {
        switch (this) {
            default -> {return N;}
            case N  -> {return NE;}
            case NE -> {return E;}
            case E  -> {return SE;}
            case SE -> {return S;}
            case S  -> {return SW;}
            case SW -> {return W;}
            case W  -> {return NW;}
        }
    }
    public OrdinalDirection cClockwise() {
        switch (this) {
            default -> {return W;}
            case N  -> {return NW;}
            case NE -> {return N ;}
            case E  -> {return NE;}
            case SE -> {return E ;}
            case S  -> {return SE;}
            case SW -> {return S ;}
            case W  -> {return SW;}
        }
    }

    public OrdinalDirection flip() {
        switch (this) {
            default -> {return SE;}
            case N  -> {return S;}
            case NE -> {return SW ;}
            case E  -> {return W;}
            case SE -> {return NW ;}
            case S  -> {return N;}
            case SW -> {return NE ;}
            case W  -> {return E;}
        }
    }
    public OrdinalDirection mirrorZ() {
        if (this == OrdinalDirection.N) {
            return this;
        }
        return OrdinalDirection.values()[8-this.ordinal()];
    }

    public OrdinalDirection mirrorX() {
        switch (this) {
            default -> {return this;}
            case N  -> {return S;}
            case S  -> {return N;}
            case SE -> {return NE ;}
            case SW -> {return NW ;}
            case NE -> {return SE ;}
            case NW -> {return SW ;}
        }
    }
    public Vec3i getVector() {
        return new Vec3i(this.x, this.y, this.z);
    }

    @Override
    public String asString() {
        return name;
    }
}