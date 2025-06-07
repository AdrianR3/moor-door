package me.ar3.moorDoor.client;

import me.ar3.moorDoor.client.datagenerator.MoorDoorModelGenerator;
import me.ar3.moorDoor.client.datagenerator.MoorDoorRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MoorDoorDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(MoorDoorModelGenerator::new);
        pack.addProvider(MoorDoorRecipeProvider::new);
    }
}
