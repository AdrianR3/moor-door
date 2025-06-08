package me.ar3.moorDoor.client.datagenerator;

import me.ar3.moorDoor.ModBlocks;
import me.ar3.moorDoor.MoorDoor;
import me.ar3.moorDoor.block.TripleDoorBlock;
import me.ar3.moorDoor.block.TripleDoorSection;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

import static net.minecraft.client.data.BlockStateModelGenerator.*;
import static net.minecraft.client.data.TextureMap.getSubId;

public class MoorDoorModelGenerator extends FabricModelProvider {

    private static final TextureKey MIDDLE = TextureKey.of("middle");

    public MoorDoorModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerTripleHeightDoorModel(blockStateModelGenerator, ModBlocks.STEEL_DOOR);
        registerTripleHeightDoorModel(blockStateModelGenerator, ModBlocks.PAPERBARK_DOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModBlocks.STEEL_DOOR.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.PAPERBARK_DOOR.asItem(), Models.GENERATED);
    }

    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.of(MoorDoor.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    private void registerTripleHeightDoorModel(BlockStateModelGenerator blockStateModelGenerator, net.minecraft.block.Block doorBlock) {

        Model DOOR_MIDDLE_LEFT = block("door_middle_left", "_middle_left", MIDDLE);
        Model DOOR_MIDDLE_LEFT_OPEN = block("door_middle_left_open", "_middle_left_open", MIDDLE);
        Model DOOR_MIDDLE_RIGHT = block("door_middle_right", "_middle_right", MIDDLE);
        Model DOOR_MIDDLE_RIGHT_OPEN = block("door_middle_right_open", "_middle_right_open", MIDDLE);

        TextureMap textureMap = topBottomMiddle(doorBlock);

        WeightedVariant bottomLeft = createWeightedVariant(Models.DOOR_BOTTOM_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomLeftOpen = createWeightedVariant(Models.DOOR_BOTTOM_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomRight = createWeightedVariant(Models.DOOR_BOTTOM_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomRightOpen = createWeightedVariant(Models.DOOR_BOTTOM_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));

        WeightedVariant middleLeft = createWeightedVariant(DOOR_MIDDLE_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleLeftOpen = createWeightedVariant(DOOR_MIDDLE_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleRight = createWeightedVariant(DOOR_MIDDLE_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleRightOpen = createWeightedVariant(DOOR_MIDDLE_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));

        WeightedVariant topLeft = createWeightedVariant(Models.DOOR_TOP_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant topLeftOpen = createWeightedVariant(Models.DOOR_TOP_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant topRight = createWeightedVariant(Models.DOOR_TOP_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant topRightOpen = createWeightedVariant(Models.DOOR_TOP_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));

        blockStateModelGenerator.blockStateCollector.accept(createThreeDoorBlockState(doorBlock,
                bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen,
                middleLeft, middleLeftOpen, middleRight, middleRightOpen,
                topLeft, topLeftOpen, topRight, topRightOpen
        ));
    }

    private static BlockModelDefinitionCreator createThreeDoorBlockState(
            Block doorBlock,
            WeightedVariant bottomLeftClosedModel, WeightedVariant bottomLeftOpenModel, WeightedVariant bottomRightClosedModel, WeightedVariant bottomRightOpenModel,
            WeightedVariant middleLeftClosedModel, WeightedVariant middleLeftOpenModel, WeightedVariant middleRightClosedModel, WeightedVariant middleRightOpenModel,
            WeightedVariant topLeftClosedModel, WeightedVariant topLeftOpenModel, WeightedVariant topRightClosedModel, WeightedVariant topRightOpenModel
    ) {
        return VariantsBlockModelDefinitionCreator.of(doorBlock).with(
                BlockStateVariantMap.models(
                        Properties.HORIZONTAL_FACING,
                        TripleDoorBlock.SECTION,
                        Properties.DOOR_HINGE, Properties.OPEN
                )
                        .register(Direction.EAST, TripleDoorSection.LOWER, DoorHinge.LEFT, false, bottomLeftClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.LOWER, DoorHinge.LEFT, false, bottomLeftClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.LOWER, DoorHinge.LEFT, false, bottomLeftClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.LOWER, DoorHinge.LEFT, false, bottomLeftClosedModel.apply(ROTATE_Y_270))
                        .register(Direction.EAST, TripleDoorSection.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel.apply(ROTATE_Y_270))

                        .register(Direction.EAST, TripleDoorSection.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, TripleDoorSection.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_180))
                        .register(Direction.WEST, TripleDoorSection.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.NORTH, TripleDoorSection.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel)
                        .register(Direction.EAST, TripleDoorSection.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.SOUTH, TripleDoorSection.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel)
                        .register(Direction.WEST, TripleDoorSection.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.NORTH, TripleDoorSection.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_180))

                        .register(Direction.EAST, TripleDoorSection.MIDDLE, DoorHinge.LEFT, false, middleLeftClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.MIDDLE, DoorHinge.LEFT, false, middleLeftClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.MIDDLE, DoorHinge.LEFT, false, middleLeftClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.MIDDLE, DoorHinge.LEFT, false, middleLeftClosedModel.apply(ROTATE_Y_270))
                        .register(Direction.EAST, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, false, middleRightClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, false, middleRightClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, false, middleRightClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, false, middleRightClosedModel.apply(ROTATE_Y_270))

                        .register(Direction.EAST, TripleDoorSection.MIDDLE, DoorHinge.LEFT, true, middleLeftOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, TripleDoorSection.MIDDLE, DoorHinge.LEFT, true, middleLeftOpenModel.apply(ROTATE_Y_180))
                        .register(Direction.WEST, TripleDoorSection.MIDDLE, DoorHinge.LEFT, true, middleLeftOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.NORTH, TripleDoorSection.MIDDLE, DoorHinge.LEFT, true, middleLeftOpenModel)
                        .register(Direction.EAST, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, true, middleRightOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.SOUTH, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, true, middleRightOpenModel)
                        .register(Direction.WEST, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, true, middleRightOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.NORTH, TripleDoorSection.MIDDLE, DoorHinge.RIGHT, true, middleRightOpenModel.apply(ROTATE_Y_180))

                        .register(Direction.EAST, TripleDoorSection.UPPER, DoorHinge.LEFT, false, topLeftClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_270))
                        .register(Direction.EAST, TripleDoorSection.UPPER, DoorHinge.RIGHT, false, topRightClosedModel)
                        .register(Direction.SOUTH, TripleDoorSection.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_90))
                        .register(Direction.WEST, TripleDoorSection.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_180))
                        .register(Direction.NORTH, TripleDoorSection.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_270))

                        .register(Direction.EAST, TripleDoorSection.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.SOUTH, TripleDoorSection.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_180))
                        .register(Direction.WEST, TripleDoorSection.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.NORTH, TripleDoorSection.UPPER, DoorHinge.LEFT, true, topLeftOpenModel)
                        .register(Direction.EAST, TripleDoorSection.UPPER, DoorHinge.RIGHT, true, topRightOpenModel.apply(ROTATE_Y_270))
                        .register(Direction.SOUTH, TripleDoorSection.UPPER, DoorHinge.RIGHT, true, topRightOpenModel)
                        .register(Direction.WEST, TripleDoorSection.UPPER, DoorHinge.RIGHT, true, topRightOpenModel.apply(ROTATE_Y_90))
                        .register(Direction.NORTH, TripleDoorSection.UPPER, DoorHinge.RIGHT, true, topRightOpenModel.apply(ROTATE_Y_180))
        );
    }

    private static TextureMap topBottomMiddle(Block block) {
        return (new TextureMap())
                .put(TextureKey.TOP, getSubId(block, "_top"))
                .put(MIDDLE, getSubId(block, "_middle"))
                .put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
    }
}
