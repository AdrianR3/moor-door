package me.ar3.moorDoor.block;

import net.minecraft.block.*;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

/**
 * Special door block that creates a 3-block tall door instead of the vanilla 2-block tall door
 */
public class TripleDoorBlock extends DoorBlock {

    private static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;
    private static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final EnumProperty<TripleDoorSection> SECTION = EnumProperty.of("section", TripleDoorSection.class);

    public TripleDoorBlock(AbstractBlock.Settings settings, BlockSetType blockSetType) {
        super(blockSetType, settings);
        this.setDefaultState(this.stateManager.getDefaultState()
                .with(FACING, Direction.NORTH)
                .with(OPEN, false)
                .with(HINGE, DoorHinge.LEFT)
                .with(POWERED, false)
                .with(SECTION, TripleDoorSection.LOWER));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(SECTION, HALF, FACING, OPEN, HINGE, POWERED);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
//        System.out.println("Checking placement for CustomDoorBlock at " + blockPos + " in world " + world.getRegistryKey());
        if (blockPos.getY() < world.getTopYInclusive() && world.getBlockState(blockPos.up()).canReplace(ctx) & world.getBlockState(blockPos.up().up()).canReplace(ctx)) {
            boolean bl = world.isReceivingRedstonePower(blockPos) || world.isReceivingRedstonePower(blockPos.up());
            return this.getDefaultState()
                    .with(FACING, ctx.getHorizontalPlayerFacing())
                    .with(HINGE, this.getHinge(ctx))
                    .with(POWERED, bl).with(OPEN, bl)
                    .with(HALF, DoubleBlockHalf.LOWER);
        } else return null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
//        System.out.println("CustomDoorBlock placed at " + pos + " in world " + world.getRegistryKey() + " with state " + state);

        BlockPos middlePos = pos.up();
        BlockPos topPos = pos.up(2);

        world.setBlockState(middlePos, state.with(SECTION, TripleDoorSection.MIDDLE), Block.NOTIFY_ALL);
        world.setBlockState(topPos, state.with(SECTION, TripleDoorSection.UPPER), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
//        if (!world.isClient && player.isCreative()) {
//            TripleBlockSection section = state.get(SECTION);
//            if (section == TripleBlockSection.MIDDLE || section == TripleBlockSection.UPPER) {
//                BlockPos lowerPos = section == TripleBlockSection.MIDDLE ? pos.down() : pos.down(2);
//                BlockState lowerState = world.getBlockState(lowerPos);
//                if (lowerState.isOf(this) && lowerState.get(SECTION) == TripleBlockSection.LOWER) {
//                    world.setBlockState(lowerPos, Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
//                }
//                if (section == TripleBlockSection.UPPER) {
//                    BlockPos middlePos = pos.down();
//                    BlockState middleState = world.getBlockState(middlePos);
//                    if (middleState.isOf(this) && middleState.get(SECTION) == TripleBlockSection.MIDDLE) {
//                        world.setBlockState(middlePos, Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
//                    }
//                }
//            } else {
//                BlockPos middlePos = pos.up();
//                BlockPos topPos = pos.up(2);
//                BlockState middleState = world.getBlockState(middlePos);
//                BlockState topState = world.getBlockState(topPos);
//
//                if (middleState.isOf(this) && middleState.get(SECTION) == TripleBlockSection.MIDDLE) {
//                    world.setBlockState(middlePos, Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
//                }
//
//                if (topState.isOf(this) && topState.get(SECTION) == TripleBlockSection.UPPER) {
//                    world.setBlockState(topPos, Blocks.AIR.getDefaultState(), NOTIFY_ALL | SKIP_DROPS);
//                }
//            }
//        }
//        System.out.println("CustomDoorBlock broken at " + pos + " in world " + world.getRegistryKey() + " with state " + state);
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        TripleDoorSection section = state.get(SECTION);

        if (direction == Direction.DOWN &&
            ((section == TripleDoorSection.MIDDLE && !neighborState.isOf(this)) ||
             (section == TripleDoorSection.UPPER && !neighborState.isOf(this)))
        ) {
//            System.out.println("CustomDoorBlock section " + section + " at " + pos + " breaking due to block below being destroyed.");
            return Blocks.AIR.getDefaultState();
        }

        if (direction == Direction.UP &&
            ((section == TripleDoorSection.LOWER && !neighborState.isOf(this)) ||
             (section == TripleDoorSection.MIDDLE && !neighborState.isOf(this)))
        ) {
//            System.out.println("CustomDoorBlock section " + section + " at " + pos + " breaking due to block below being destroyed.");
            return Blocks.AIR.getDefaultState();
        }

        // LOWER section should check if it can be placed at this position
        if (section == TripleDoorSection.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos)) {
//            System.out.println("CustomDoorBlock section " + section + " at " + pos + " breaking due to block below being destroyed.");
            return Blocks.AIR.getDefaultState();
        }

//        if (section == TripleDoorSection.LOWER) {
//            boolean isPowered = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
//            if (isPowered != state.get(POWERED)) {
//                return state.with(POWERED, isPowered);
//            }
//        }

        return state;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, WireOrientation wireOrientation, boolean notify) {
        TripleDoorSection section = state.get(SECTION);

        BlockPos lowerPos = getSectionPosition(state, pos, TripleDoorSection.LOWER);
        BlockPos middlePos = getSectionPosition(state, pos, TripleDoorSection.MIDDLE);
        BlockPos upperPos = getSectionPosition(state, pos, TripleDoorSection.UPPER);

        boolean lowerExists = world.getBlockState(lowerPos).isOf(this) && world.getBlockState(lowerPos).get(SECTION) == TripleDoorSection.LOWER;
        boolean middleExists = world.getBlockState(middlePos).isOf(this) && world.getBlockState(middlePos).get(SECTION) == TripleDoorSection.MIDDLE;
        boolean upperExists = world.getBlockState(upperPos).isOf(this) && world.getBlockState(upperPos).get(SECTION) == TripleDoorSection.UPPER;

        if ((section == TripleDoorSection.LOWER && (!middleExists || !upperExists)) ||
            (section == TripleDoorSection.MIDDLE && (!lowerExists || !upperExists)) ||
            (section == TripleDoorSection.UPPER && (!lowerExists || !middleExists))) {

//            System.out.println("Removing CustomDoorBlock at " + pos + " because one of its sections is missing." + "(Upper, Middle, Lower): (" + upperExists + ", " + middleExists + ", " + lowerExists + ")");
//            world.setBlockState(pos, Blocks.AIR.getDefaultState(), NOTIFY_ALL);
            return;
        }

        if (section == TripleDoorSection.LOWER) {
            boolean isPowered = world.isReceivingRedstonePower(lowerPos) || world.isReceivingRedstonePower(middlePos) || world.isReceivingRedstonePower(upperPos);

//            System.out.println("1CustomDoorBlock neighbor update at " + pos + " w/state " + state + " & power state (isPowered, state.get(POWERED)) (" + isPowered+ ", "+state.get(POWERED)+")");

            if (sourceBlock != this && (isPowered != state.get(POWERED))) {
                world.setBlockState(lowerPos, state.with(POWERED, isPowered), Block.NOTIFY_ALL);
                world.setBlockState(middlePos, world.getBlockState(middlePos).with(POWERED, isPowered), Block.NOTIFY_ALL);
                world.setBlockState(upperPos, world.getBlockState(upperPos).with(POWERED, isPowered), Block.NOTIFY_ALL);

//                System.out.println("2CustomDoorBlock at " + pos + " w/state " + state + " (isPowered:" + isPowered + ", state.get(OPEN): " + state.get(OPEN) + ")");
            }

            if (isPowered != state.get(OPEN)) {
//                System.out.println("3CustomDoorBlock at " + pos + " changing open state to " + isPowered);
                world.setBlockState(lowerPos, state.with(OPEN, isPowered), Block.NOTIFY_ALL);
                world.setBlockState(middlePos, world.getBlockState(middlePos).with(OPEN, isPowered), Block.NOTIFY_ALL);
                world.setBlockState(upperPos, world.getBlockState(upperPos).with(OPEN, isPowered), Block.NOTIFY_ALL);
                openCloseSound(world, pos, null, isPowered);
            }

        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!this.getBlockSetType().canOpenByHand()) {
            return ActionResult.PASS;
        }

        BlockPos lowerPos = getSectionPosition(state, pos, TripleDoorSection.LOWER);
        BlockPos middlePos = getSectionPosition(state, pos, TripleDoorSection.MIDDLE);
        BlockPos upperPos = getSectionPosition(state, pos, TripleDoorSection.UPPER);

        BlockState lowerState = world.getBlockState(lowerPos);
        BlockState middleState = world.getBlockState(middlePos);
        BlockState upperState = world.getBlockState(upperPos);

//        System.out.println("TripleDoorBlock onUse at " + pos + " with state " + state + " (Lower, Middle, Upper): (" + lowerState.get(OPEN) + ", " + middleState.get(OPEN) + ", " + upperState.get(OPEN) + ")");

        boolean isOpen = !state.get(OPEN);
        if (lowerState.isOf(this)) world.setBlockState(lowerPos, lowerState.with(OPEN, isOpen), Block.NOTIFY_LISTENERS);
        if (middleState.isOf(this)) world.setBlockState(middlePos, middleState.with(OPEN, isOpen), Block.NOTIFY_LISTENERS);
        if (upperState.isOf(this)) world.setBlockState(upperPos, upperState.with(OPEN, isOpen), Block.NOTIFY_LISTENERS);

        openCloseSound(world, pos, player, isOpen);
        world.emitGameEvent(player, isOpen ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);

        return ActionResult.SUCCESS;
    }

    private DoorHinge getHinge(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getHorizontalPlayerFacing();
        int offsetX = direction.getOffsetX();
        int offsetZ = direction.getOffsetZ();
        Vec3d vec3d = ctx.getHitPos();
        double d = vec3d.x - (double) blockPos.getX();
        double f = vec3d.z - (double) blockPos.getZ();
        return (offsetX >= 0 || !(f < 0.5)) && (offsetX <= 0 || !(f > 0.5)) && (offsetZ >= 0 || !(d > 0.5)) && (offsetZ <= 0 || !(d < 0.5)) ? DoorHinge.LEFT : DoorHinge.RIGHT;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (state.get(SECTION) == TripleDoorSection.LOWER) {
            return world.getBlockState(pos.down()).isSolid();
        } else if (state.get(SECTION) == TripleDoorSection.MIDDLE) {
            BlockState lowerState = world.getBlockState(pos.down());
            return lowerState.isOf(this) && lowerState.get(SECTION) == TripleDoorSection.LOWER;
        } else {
            BlockState middleState = world.getBlockState(pos.down());
            return middleState.isOf(this) && middleState.get(SECTION) == TripleDoorSection.MIDDLE;
        }
    }

    private void openCloseSound(World world, BlockPos pos, PlayerEntity player, boolean isOpen) {
        world.playSound(player, pos, isOpen ? getBlockSetType().doorOpen() : getBlockSetType().doorClose(), SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    private BlockPos getSectionPosition(BlockState state, BlockPos pos, TripleDoorSection requestedSection) {
        TripleDoorSection section = state.get(SECTION);

        BlockPos lowerPos = section == TripleDoorSection.MIDDLE ? pos.down() : (section == TripleDoorSection.UPPER ? pos.down(2) : pos);
        BlockPos middlePos = section == TripleDoorSection.LOWER ? pos.up() : (section == TripleDoorSection.UPPER ? pos.down() : pos);
        BlockPos upperPos = section == TripleDoorSection.LOWER ? pos.up(2) : (section == TripleDoorSection.MIDDLE ? pos.up() : pos);

        return switch (requestedSection) {
            case LOWER -> lowerPos;
            case MIDDLE -> middlePos;
            case UPPER -> upperPos;
        };

    }
}