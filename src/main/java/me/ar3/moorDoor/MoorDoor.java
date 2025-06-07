package me.ar3.moorDoor;

import net.fabricmc.api.ModInitializer;

public class MoorDoor implements ModInitializer {

    public static final String MOD_ID = "moor-door";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
    }

}
