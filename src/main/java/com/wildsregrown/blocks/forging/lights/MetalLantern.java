package com.wildsregrown.blocks.forging.lights;

import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import wildsregrown.api.block.Dice;
import wildsregrown.api.block.render.IRenderType;

public class MetalLantern extends Block implements IRenderType {

    private static final VoxelShape SHAPE;
    private static final IntegerProperty OXIDATION = ModProperties.OXIDATION;
    private static final BooleanProperty LIT = BlockStateProperties.LIT;

    public MetalLantern(Properties settings) {
        super(settings.lightLevel(state -> {
            return state.getValue(LIT) ? 12 : 0;
        }));
        this.registerDefaultState(defaultBlockState().setValue(LIT, false).setValue(OXIDATION, 0));
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        setOxidation(state, world, pos, random);
    }

    private void setOxidation(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (Dice.d100(random, 3) <= 9){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, MathUtil.min(state.getValue(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) <= 9){
            world.setBlockAndUpdate(pos, state.setValue(OXIDATION, 3));
        }
    }


    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {

        if (player.swingingArm != null) {
            ItemStack heldItem = player.getItemInHand(player.swingingArm);
            Item item = heldItem.getItem();

            if (item == Items.AIR){
                world.setBlockAndUpdate(pos, state.setValue(LIT, !state.getValue(LIT)));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(LIT, OXIDATION);
    }

    //Defining shapes for every state
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    static {
        SHAPE = Shapes.or(
                Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
                Shapes.box(0.3125, 0.4375, 0.3125, 0.6875, 0.5, 0.6875),
                Shapes.box(0.4375, 0.5, 0.4375, 0.5625, 0.5625, 0.5625),
                Shapes.box(0.3125, 0.0625, 0.3125, 0.375, 0.4375, 0.375),
                Shapes.box(0.625, 0.0625, 0.3125, 0.6875, 0.4375, 0.375),
                Shapes.box(0.3125, 0.0625, 0.625, 0.375, 0.4375, 0.6875),
                Shapes.box(0.625, 0.0625, 0.625, 0.6875, 0.4375, 0.6875),
                Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.4375, 0.625),
                Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.4375, 0.625),
                Shapes.box(0.34375, 0.0625, 0.34375, 0.65625, 0.4375, 0.65625)
        );
    }
}
