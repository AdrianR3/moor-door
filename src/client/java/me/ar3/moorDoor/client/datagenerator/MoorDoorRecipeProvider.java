package me.ar3.moorDoor.client.datagenerator;

import me.ar3.moorDoor.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class MoorDoorRecipeProvider extends FabricRecipeProvider {

    public MoorDoorRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup, RecipeExporter exporter) {
        return new RecipeGenerator(registryLookup, exporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = registries.getOrThrow(RegistryKeys.ITEM);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModItems.TALL_DOOR)
                        .input('#', Items.IRON_DOOR)
                        .input('X', Items.IRON_INGOT)
                        .pattern("X")
                        .pattern("X")
                        .pattern("#")
                        .criterion(hasItem(Items.IRON_INGOT), this.conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "MoorDoorRecipeProvider";
    }
}
