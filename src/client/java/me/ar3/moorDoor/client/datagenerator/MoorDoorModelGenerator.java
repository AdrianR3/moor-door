package me.ar3.moorDoor.client.datagenerator;

import me.ar3.moorDoor.ModBlocks;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

import static net.minecraft.client.data.BlockStateModelGenerator.*;
import static net.minecraft.client.data.TextureMap.getSubId;

public class MoorDoorModelGenerator extends FabricModelProvider {

    public MoorDoorModelGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        registerTripleHeightDoor(blockStateModelGenerator, ModBlocks.TALL_DOOR);
    }

    private static Model block(String parent, String variant, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(Identifier.ofVanilla("block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    private void registerTripleHeightDoor(BlockStateModelGenerator blockStateModelGenerator, net.minecraft.block.Block doorBlock) {

        Model DOOR_MIDDLE_LEFT = block("door_middle_left", "_middle_left", TextureKey.TOP, TextureKey.BOTTOM);
        Model DOOR_MIDDLE_LEFT_OPEN = block("door_middle_left_open", "_middle_left_open", TextureKey.TOP, TextureKey.BOTTOM);
        Model DOOR_MIDDLE_RIGHT = block("door_middle_right", "_middle_right", TextureKey.TOP, TextureKey.BOTTOM);
        Model DOOR_MIDDLE_RIGHT_OPEN = block("door_middle_right_open", "_middle_right_open", TextureKey.TOP, TextureKey.BOTTOM);

        TextureMap textureMap = topBottomMiddle(doorBlock);
        WeightedVariant bottomLeft = createWeightedVariant(Models.DOOR_BOTTOM_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomLeftOpen = createWeightedVariant(Models.DOOR_BOTTOM_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomRight = createWeightedVariant(Models.DOOR_BOTTOM_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant bottomRightOpen = createWeightedVariant(Models.DOOR_BOTTOM_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));

        WeightedVariant middleLeft = createWeightedVariant(DOOR_MIDDLE_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleLeftOpen = createWeightedVariant(DOOR_MIDDLE_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleRight = createWeightedVariant(DOOR_MIDDLE_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant middleRightOpen = createWeightedVariant(DOOR_MIDDLE_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));

        WeightedVariant weightedVariant5 = createWeightedVariant(Models.DOOR_TOP_LEFT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant6 = createWeightedVariant(Models.DOOR_TOP_LEFT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant7 = createWeightedVariant(Models.DOOR_TOP_RIGHT.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
        WeightedVariant weightedVariant8 = createWeightedVariant(Models.DOOR_TOP_RIGHT_OPEN.upload(doorBlock, textureMap, blockStateModelGenerator.modelCollector));
//        blockStateModelGenerator.registerItemModel(doorBlock.asItem());
        blockStateModelGenerator.blockStateCollector.accept(createThreeDoorBlockState(doorBlock,
                bottomLeft, bottomLeftOpen, bottomRight, bottomRightOpen,
//                middleLeft, middleLeftOpen, middleRight, middleRightOpen,
                weightedVariant5, weightedVariant6, weightedVariant7, weightedVariant8
        ));

//        TextureMap bottomTexture = TextureMap.texture(doorBlock)
//            .put(TextureKey.TOP, new Identifier("moor-door:block/tall_door_bottom_top"))
//            .put(TextureKey.BOTTOM, new Identifier("moor-door:block/tall_door_bottom"));
//        TextureMap middleTexture = TextureMap.texture(doorBlock)
//            .put(TextureKey.TOP, new Identifier("moor-door:block/tall_door_middle_top"))
//            .put(TextureKey.BOTTOM, new Identifier("moor-door:block/tall_door_middle_bottom"));
//        TextureMap topTexture = TextureMap.texture(doorBlock)
//            .put(TextureKey.TOP, new Identifier("moor-door:block/tall_door_top"))
//            .put(TextureKey.BOTTOM, new Identifier("moor-door:block/tall_door_top_bottom"));
//
//        // Register models for each section and state
//
//        Identifier bottomLeftClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_bottom_left", Models.DOOR_BOTTOM_LEFT, bottomTexture);
//        Identifier bottomLeftOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_bottom_left_open", Models.DOOR_BOTTOM_LEFT_OPEN, bottomTexture);
//        Identifier bottomRightClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_bottom_right", Models.DOOR_BOTTOM_RIGHT, bottomTexture);
//        Identifier bottomRightOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_bottom_right_open", Models.DOOR_BOTTOM_RIGHT_OPEN, bottomTexture);
//
//        Identifier middleLeftClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_middle_left", Models.DOOR_BOTTOM_LEFT, middleTexture);
//        Identifier middleLeftOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_middle_left_open", Models.DOOR_BOTTOM_LEFT_OPEN, middleTexture);
//        Identifier middleRightClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_middle_right", Models.DOOR_BOTTOM_RIGHT, middleTexture);
//        Identifier middleRightOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_middle_right_open", Models.DOOR_BOTTOM_RIGHT_OPEN, middleTexture);
//
//        Identifier topLeftClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_top_left", Models.DOOR_TOP_LEFT, topTexture);
//        Identifier topLeftOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_top_left_open", Models.DOOR_TOP_LEFT_OPEN, topTexture);
//        Identifier topRightClosed = blockStateModelGenerator.createDoorModel(doorBlock, "_top_right", Models.DOOR_TOP_RIGHT, topTexture);
//        Identifier topRightOpen = blockStateModelGenerator.createDoorModel(doorBlock, "_top_right_open", Models.DOOR_TOP_RIGHT_OPEN, topTexture);

        // Register blockstate variants using VariantsBlockStateSupplier
//        blockStateModelGenerator.blockStateCollector.accept(
//                VariantsBlockModelDefinitionCreator.of(doorBlock, BlockStateModelGenerator.createModelVariant())
//                        .with(
//                                new WeightedVariant(new ModelVariant())
//                        )
//                        .coordinate(BlockStateModelGenerator.createModelVariant(
//                                bottomLeftClosed, bottomLeftOpen, bottomRightClosed, bottomRightOpen,
//                                middleLeftClosed, middleLeftOpen, middleRightClosed, middleRightOpen,
//                                topLeftClosed, topLeftOpen, topRightClosed, topRightOpen
//                        ))
//                        .put(VariantSettings.HINGE, DoorHinge.LEFT)
//                        .put(VariantSettings.FACING, Direction.NORTH)
//                        .put(VariantSettings.OPEN, false)
//                        .put(Properties.DOUBLE_BLOCK_HALF, Properties.DoubleBlockHalf.LOWER)
//            VariantsBlockStateSupplier.create(doorBlock)
//                .coordinate(BlockStateModelGenerator.createModelVariant(
//                    bottomLeftClosed, bottomLeftOpen, bottomRightClosed, bottomRightOpen,
//                    middleLeftClosed, middleLeftOpen, middleRightClosed, middleRightOpen,
//                    topLeftClosed, topLeftOpen, topRightClosed, topRightOpen
//                ))
//        );
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModBlocks.TALL_DOOR.asItem(), Models.GENERATED);
    }

    private static BlockModelDefinitionCreator createThreeDoorBlockState(Block doorBlock, WeightedVariant bottomLeftClosedModel, WeightedVariant bottomLeftOpenModel, WeightedVariant bottomRightClosedModel, WeightedVariant bottomRightOpenModel, WeightedVariant topLeftClosedModel, WeightedVariant topLeftOpenModel, WeightedVariant topRightClosedModel, WeightedVariant topRightOpenModel) {
        return VariantsBlockModelDefinitionCreator.of(doorBlock).with(
                BlockStateVariantMap.models(
                        Properties.HORIZONTAL_FACING,
                        Properties.DOUBLE_BLOCK_HALF,
                        Properties.DOOR_HINGE, Properties.OPEN
                ).register(
                        Direction.EAST,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.LEFT,
                        false,
                        bottomLeftClosedModel
                ).register(
                        Direction.SOUTH,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.LEFT,
                        false,
                        bottomLeftClosedModel.apply(ROTATE_Y_90)
                ).register(
                        Direction.WEST,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.LEFT,
                        false,
                        bottomLeftClosedModel.apply(ROTATE_Y_180)
                ).register(Direction.NORTH,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.LEFT,
                        false,
                        bottomLeftClosedModel.apply(ROTATE_Y_270)
                ).register(Direction.EAST,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.RIGHT,
                        false,
                        bottomRightClosedModel
                ).register(Direction.SOUTH,
                        DoubleBlockHalf.LOWER,
                        DoorHinge.RIGHT,
                        false,
                        bottomRightClosedModel.apply(ROTATE_Y_90)
                ).register(Direction.WEST, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel.apply(ROTATE_Y_180)).register(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, false, bottomRightClosedModel.apply(ROTATE_Y_270)).register(Direction.EAST, DoubleBlockHalf.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_90)).register(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_180)).register(Direction.WEST, DoubleBlockHalf.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel.apply(ROTATE_Y_270)).register(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHinge.LEFT, true, bottomLeftOpenModel).register(Direction.EAST, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_270)).register(Direction.SOUTH, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel).register(Direction.WEST, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_90)).register(Direction.NORTH, DoubleBlockHalf.LOWER, DoorHinge.RIGHT, true, bottomRightOpenModel.apply(ROTATE_Y_180)).register(Direction.EAST, DoubleBlockHalf.UPPER, DoorHinge.LEFT, false, topLeftClosedModel).register(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_90)).register(Direction.WEST, DoubleBlockHalf.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_180)).register(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHinge.LEFT, false, topLeftClosedModel.apply(ROTATE_Y_270)).register(Direction.EAST, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, false, topRightClosedModel).register(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_90)).register(Direction.WEST, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_180)).register(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, false, topRightClosedModel.apply(ROTATE_Y_270)).register(Direction.EAST, DoubleBlockHalf.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_90)).register(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_180)).register(Direction.WEST, DoubleBlockHalf.UPPER, DoorHinge.LEFT, true, topLeftOpenModel.apply(ROTATE_Y_270)).register(Direction.NORTH, DoubleBlockHalf.UPPER, DoorHinge.LEFT, true, topLeftOpenModel).register(Direction.EAST, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, true, topRightOpenModel.apply(ROTATE_Y_270)).register(Direction.SOUTH, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, true, topRightOpenModel).register(Direction.WEST, DoubleBlockHalf.UPPER, DoorHinge.RIGHT, true, topRightOpenModel.apply(ROTATE_Y_90)).register(Direction.NORTH,
                        DoubleBlockHalf.UPPER,
                        DoorHinge.RIGHT,
                        true,
                        topRightOpenModel.apply(ROTATE_Y_180)));
    }

    private static TextureMap topBottomMiddle(Block block) {
        return (new TextureMap())
                .put(TextureKey.TOP, getSubId(block, "_top"))
                .put(TextureKey.of("middle"), getSubId(block, "_middle"))
                .put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
    }
}
