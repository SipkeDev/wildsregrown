package com.wildsregrown.blocks.wood.tree;

import com.sipke.api.features.trees.type.ITreeType;
import com.sipke.api.features.trees.type.TreeType;
import com.sipke.math.MathUtil;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.render.ITintedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.awt.*;

import static com.wildsregrown.blocks.render.TintUtil.buildBlendMap;

public class Leaves extends Block implements ITintedBlock, ITreeType {

    private static final VoxelShape shape;
    public static final IntProperty MOISTURE = ModProperties.MOISTURE;
    protected static int[] rgb;

    public Leaves(Settings settings) {
        super(settings);
        this.setDefaultState(getDefaultState().with(MOISTURE, 8));
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
        if (random.nextInt(20) == 1){

            int moisture = state.get(MOISTURE);
            if (world.isRaining()){
                moisture = moisture + 5;
            }else if (world.isThundering()){
                moisture = moisture + 10;
            }else {
                moisture = moisture - 1;
            }
            //WildsRegrown.LOGGER.info("test: " + state.with(MOISTURE, MathUtil.clamp(moisture,0, 16)));
            world.setBlockState(pos, state.with(MOISTURE, MathUtil.clamp(moisture,0, 16)));
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(MOISTURE);
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
            entity.playSound(soundEvent, 1.0F, 1.0F);
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return shape;
    }

    @Override
    public int getOpacity(BlockState state) {
        return 0;
    }

    static {
        shape = VoxelShapes.union(
                VoxelShapes.cuboid(0.1875, 0.1875, 0.125, 0.8125, 0.8125, 0.875)
        );
        //Set colorfull life
        int dry = Color.decode("#c8b478").getRGB();
        int temperate = Color.decode("#748937").getRGB();
        int wet = Color.decode("#93ad46").getRGB();
        rgb = buildBlendMap(temperate, dry, wet, MOISTURE.getValues().size());
    }

    @Override
    public int getTint(BlockState state, int tintIndex) {
        return rgb[MathUtil.clamp(state.get(MOISTURE)-1, 0, rgb.length)];
    }

    @Override
    public TreeType getType() {
        return TreeType.thin_branch;
    }
}
