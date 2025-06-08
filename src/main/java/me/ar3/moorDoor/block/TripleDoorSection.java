package me.ar3.moorDoor.block;

import net.minecraft.util.StringIdentifiable;

public enum TripleDoorSection implements StringIdentifiable {

    UPPER("upper"),
    MIDDLE("middle"),
    LOWER("lower");

    private final String name;

    TripleDoorSection(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}
