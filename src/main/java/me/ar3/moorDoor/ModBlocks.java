package me.ar3.moorDoor;

import me.ar3.moorDoor.block.TripleDoorBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

public class ModBlocks {

    public static final Block STEEL_DOOR = register(
            "steel_door",
            settings -> new TripleDoorBlock(settings, BlockSetType.IRON),
            AbstractBlock.Settings.copy(Blocks.IRON_DOOR).nonOpaque(),
            false
    );

    public static final Block PAPERBARK_DOOR = register(
            "paperbark_door",
            settings -> new TripleDoorBlock(settings, BlockSetType.BIRCH),
            AbstractBlock.Settings.copy(Blocks.BIRCH_DOOR).nonOpaque(),
            false
    );

    public static void initialize() {

//        Dummy method to load blocks

    }

    private static Block register(String name, Function<AbstractBlock.Settings, Block> blockFactory, AbstractBlock.Settings settings, boolean shouldRegisterItem) {
        RegistryKey<Block> blockKey = keyOfBlock(name);
        Block block = blockFactory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem) {
            RegistryKey<Item> itemKey = keyOfItem(name);

            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }

        return Registry.register(Registries.BLOCK, blockKey, block);
    }

    private static RegistryKey<Block> keyOfBlock(String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(MoorDoor.MOD_ID, name));
    }

    private static RegistryKey<Item> keyOfItem(String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MoorDoor.MOD_ID, name));
    }

}
