package me.ar3.moorDoor.client;

import me.ar3.moorDoor.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

@Environment(EnvType.CLIENT)
public class MoorDoorClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(
                net.minecraft.client.render.RenderLayer.getTranslucent(),
                ModBlocks.STEEL_DOOR,
                ModBlocks.PAPERBARK_DOOR
        );
    }
}
