package me.ar3.moorDoor.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;

@Environment(EnvType.CLIENT)
public class MoorDoorClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(
                me.ar3.moorDoor.ModBlocks.TALL_DOOR,
                net.minecraft.client.render.RenderLayer.getTranslucent()
        );
    }
}
