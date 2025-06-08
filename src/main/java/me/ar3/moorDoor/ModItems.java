package me.ar3.moorDoor;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.TallBlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModItems {

    public static final Item STEEL_DOOR_ITEM = register("steel_door", settings -> new TallBlockItem(ModBlocks.STEEL_DOOR, settings), new TallBlockItem.Settings());
    public static final Item PAPERBARK_DOOR_ITEM = register("paperbark_door", settings -> new TallBlockItem(ModBlocks.PAPERBARK_DOOR, settings), new TallBlockItem.Settings());

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register((itemGroup) -> itemGroup.add(ModItems.STEEL_DOOR_ITEM));

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                .register((itemGroup) -> itemGroup.add(ModItems.PAPERBARK_DOOR_ITEM));
    }

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoorDoor.MOD_ID, name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }
}
