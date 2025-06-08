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
import net.minecraft.registry.tag.ItemTags;

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

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModItems.STEEL_DOOR_ITEM)
                        .input('#', Items.IRON_DOOR)
                        .input('X', Items.IRON_INGOT)
                        .pattern("X")
                        .pattern("X")
                        .pattern("#")
                        .criterion(hasItem(Items.IRON_INGOT), this.conditionsFromItem(Items.IRON_INGOT))
                        .offerTo(exporter);

                createShaped(RecipeCategory.BUILDING_BLOCKS, ModItems.PAPERBARK_DOOR_ITEM)
                        .input('#', ItemTags.WOODEN_DOORS)
                        .input('X', Items.BIRCH_PLANKS)
                        .pattern("X")
                        .pattern("X")
                        .pattern("#")
                        .criterion(hasItem(Items.BIRCH_PLANKS), this.conditionsFromItem(Items.BIRCH_PLANKS))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "MoorDoorRecipeProvider";
    }
}
