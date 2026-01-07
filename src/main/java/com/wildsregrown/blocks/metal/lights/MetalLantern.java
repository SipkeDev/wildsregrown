package com.wildsregrown.blocks.metal.lights;

import com.wildsregrown.blocks.Dice;
import com.wildsregrown.blocks.properties.ModProperties;
import com.sipke.math.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class MetalLantern extends Block {

    private static final VoxelShape SHAPE;
    private static final IntProperty OXIDATION = ModProperties.OXIDATION;
    private static final BooleanProperty LIT = Properties.LIT;

    public MetalLantern(Settings settings) {
        super(settings.luminance(state -> {
            return state.get(LIT) ? 12 : 0;
        }));
        this.setDefaultState(getDefaultState().with(LIT, false).with(OXIDATION, 0));
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(OXIDATION) < 3;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        setOxidation(state, world, pos, random);
    }

    private void setOxidation(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (Dice.d100(random, 3) <= 9){
            world.setBlockState(pos, state.with(OXIDATION, MathUtil.min(state.get(OXIDATION)+1, 3)));
        }else if (Dice.d100(random, 5) <= 9){
            world.setBlockState(pos, state.with(OXIDATION, 3));
        }
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {

        if (player.preferredHand != null) {
            ItemStack heldItem = player.getStackInHand(player.preferredHand);
            Item item = heldItem.getItem();

            if (item == Items.AIR){
                world.setBlockState(pos, state.with(LIT, !state.get(LIT)));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder){
        builder.add(LIT, OXIDATION);
    }

    //Defining shapes for every state
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext ctx) {
        return SHAPE;
    }

    static {
        SHAPE = VoxelShapes.union(
                VoxelShapes.cuboid(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875),
                VoxelShapes.cuboid(0.3125, 0.4375, 0.3125, 0.6875, 0.5, 0.6875),
                VoxelShapes.cuboid(0.4375, 0.5, 0.4375, 0.5625, 0.5625, 0.5625),
                VoxelShapes.cuboid(0.3125, 0.0625, 0.3125, 0.375, 0.4375, 0.375),
                VoxelShapes.cuboid(0.625, 0.0625, 0.3125, 0.6875, 0.4375, 0.375),
                VoxelShapes.cuboid(0.3125, 0.0625, 0.625, 0.375, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.625, 0.0625, 0.625, 0.6875, 0.4375, 0.6875),
                VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.4375, 0.625),
                VoxelShapes.cuboid(0.375, 0.0625, 0.375, 0.625, 0.4375, 0.625),
                VoxelShapes.cuboid(0.34375, 0.0625, 0.34375, 0.65625, 0.4375, 0.65625)
        );
    }
}
