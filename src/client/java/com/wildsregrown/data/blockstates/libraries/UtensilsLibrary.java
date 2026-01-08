package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;

public class UtensilsLibrary {

    private final static String modelPath = "decoration/wood/";

    public static void mug(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/decoration/wood_mug",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/decoration/wood_mug", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/decoration/wood_mug",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/decoration/wood_mug",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+ loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint,Direction.NORTH, modelOf(loc, false, 90 , 0))
                    .register(paint,Direction.SOUTH, modelOf(loc, false, 270, 0))
                    .register(paint,Direction.EAST , modelOf(loc, false, 180, 0))
                    .register(paint,Direction.WEST , modelOf(loc, false, 0  , 0));
        }

        CreateVariants(generator, block, map);
    }
    public static void bowl(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/decoration/wood_bowl",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/decoration/wood_bowl", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/decoration/wood_bowl",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/decoration/wood_bowl",log_path + name + "_paintable_wood");
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));
        BlockStateVariantMap.SingleProperty<WeightedVariant,LinSeedPaintable> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint, modelOf(loc));
        }
        CreateVariants(generator, block, map);
    }
    public static void cuttingBoard(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/utensils/cuttingboard_floor",log_path + name + "_wood");
        applyTextureToModel(generator, loc0+"_side", "block/utensils/cuttingboard_side",log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, "block/utensils/cuttingboard_floor", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1+"_side", "block/utensils/cuttingboard_side", log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/utensils/cuttingboard_floor",log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1+"_side", "block/utensils/cuttingboard_side",log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, "block/utensils/cuttingboard_floor",log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1+"_side", "block/utensils/cuttingboard_side",log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant,Direction, LinSeedPaintable> map = BlockStateVariantMap.models(Properties.FACING, ModProperties.LINSEED_PAINT);
        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(Direction.UP, paint, modelOf(loc));
            map.register(Direction.DOWN, paint, modelOf(loc, false, 90,0));

            map.register(Direction.NORTH, paint, modelOf(loc+"_side", false, 0,0));
            map.register(Direction.EAST, paint, modelOf(loc+"_side", false, 90,0));
            map.register(Direction.SOUTH, paint, modelOf(loc+"_side", false, 180,0));
            map.register(Direction.WEST, paint, modelOf(loc+"_side", false, 270,0));
        }
        CreateVariants(generator, block, map);
    }

}
