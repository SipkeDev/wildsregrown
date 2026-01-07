package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.VerticalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;
import static net.minecraft.client.data.BlockStateModelGenerator.createStairsBlockState;

public class CastleLibrary {

    private final static String modelPath = "block/castle/";

    public static void woodenArrowSlit(BlockStateModelGenerator generator, Block block, String id, String name, String type) {

        String loc0 = modelPath + id + "_" + type;
        String loc1 = modelPath + id + "_paintable_" + type;

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_top",modelPath+"wood_arrow_slit_top", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_middle",modelPath+"wood_arrow_slit_middle", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_bottom",modelPath+"wood_arrow_slit_bottom", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_bottom_floor",modelPath+"wood_arrow_slit_bottom_floor", plank_path + name + "_" + type, log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_top",modelPath+"wood_arrow_slit_top", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",modelPath+"wood_arrow_slit_middle", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",modelPath+"wood_arrow_slit_bottom", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom_floor",modelPath+"wood_arrow_slit_bottom_floor", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_top",modelPath+"wood_arrow_slit_top", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",modelPath+"wood_arrow_slit_middle", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",modelPath+"wood_arrow_slit_bottom", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom_floor",modelPath+"wood_arrow_slit_bottom_floor", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_top",modelPath+"wood_arrow_slit_top", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",modelPath+"wood_arrow_slit_middle", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",modelPath+"wood_arrow_slit_bottom", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom_floor",modelPath+"wood_arrow_slit_bottom_floor", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0 + "_middle"));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, LinSeedPaintable, Direction, VerticalConnected, Integer> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, ModProperties.VERTICAL_CONNECTED, ModProperties.VARIATIONS_3);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
                int dir = direction.getHorizontalQuarterTurns() * 90;
                map.register(paint, direction, VerticalConnected.SINGLE, 1, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.TOP, 1, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.MIDDLE, 1, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.BOTTOM, 1, modelOf(finalLoc + "_middle", true, dir, 0));
            }
            for (Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
                int dir = direction.getHorizontalQuarterTurns() * 90;
                map.register(paint, direction, VerticalConnected.SINGLE, 2, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.TOP, 2, modelOf(finalLoc + "_top", true, dir, 0));
                map.register(paint, direction, VerticalConnected.MIDDLE, 2, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.BOTTOM, 2, modelOf(finalLoc + "_bottom", true, dir, 0));
            }
            for (Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
                int dir = direction.getHorizontalQuarterTurns() * 90;
                map.register(paint, direction, VerticalConnected.SINGLE, 3, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.TOP, 3, modelOf(finalLoc + "_top", true, dir, 0));
                map.register(paint, direction, VerticalConnected.MIDDLE, 3, modelOf(finalLoc + "_middle", true, dir, 0));
                map.register(paint, direction, VerticalConnected.BOTTOM, 3, modelOf(finalLoc + "_bottom_floor", true, dir, 0));
            }

        }

        CreateVariants(generator, block, map);
    }

    public static void woodenArch(BlockStateModelGenerator generator, Block block, String id, String name, String type) {

        String loc0 = modelPath + id + "_" + type;
        String loc1 = modelPath + id + "_paintable_" + type;

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/castle/wood_arch", plank_path + name + "_" + type);
        if (pines) {
            applyTextureToModel(generator, loc1, "block/castle/wood_arch", plank_path + "pine_paintable_" + type);
        }else if (fruit) {
            applyTextureToModel(generator, loc1, "block/castle/wood_arch", plank_path + "fruit_paintable_" + type);
        }else {
            applyTextureToModel(generator, loc1, "block/castle/wood_arch", plank_path + name + "_paintable_" + type);
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.TripleProperty<WeightedVariant, LinSeedPaintable, Direction.Axis, BlockHalf> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_AXIS, Properties.BLOCK_HALF);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()) {
            String l = loc1;
            if (paint == LinSeedPaintable.NONE) {
                l = loc0;
            }
            map.register(paint, Direction.Axis.X, BlockHalf.TOP, modelOf(l, true, 90, 0));
            map.register(paint, Direction.Axis.Z, BlockHalf.TOP, modelOf(l, true, 0, 0));
            map.register(paint, Direction.Axis.X, BlockHalf.BOTTOM, modelOf(l, true, 90, 180));
            map.register(paint, Direction.Axis.Z, BlockHalf.BOTTOM, modelOf(l, true, 0, 180));
        }

        CreateVariants(generator, block, map);

    }

    public static void woodenHalfArch(BlockStateModelGenerator generator, String name, Block block, String type) {

        String loc0 = plank_path + name + "_" + type;
        String loc1 = plank_path + name + "_paintable_" + type;
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, "inner_" + loc0, "block/castle/wood_half_arch_inner_corner", plank_path + name + "_" + type);
        applyTextureToModel(generator, "outer_" + loc0, "block/castle/wood_half_arch_outer_corner", plank_path + name + "_" + type);
        applyTextureToModel(generator, "straight_" + loc0, "block/castle/wood_half_arch", plank_path + name + "_" + type);
        if (pines) {
            applyTextureToModel(generator, "inner_" + loc1, "block/castle/wood_half_arch_inner_corner", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "outer_" + loc1, "block/castle/wood_half_arch_outer_corner", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "straight_" + loc1, "block/castle/wood_half_arch", plank_path + "pine_paintable_" + type);
        } else if (fruit) {
            applyTextureToModel(generator, "inner_" + loc1, "block/castle/wood_half_arch_inner_corner", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "outer_" + loc1, "block/castle/wood_half_arch_outer_corner", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "straight_" + loc1, "block/castle/wood_half_arch", plank_path + "fruit_paintable_" + type);
        } else {
            applyTextureToModel(generator, "inner_" + loc1, "block/castle/wood_half_arch_inner_corner", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "outer_" + loc1, "block/castle/wood_half_arch_outer_corner", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "straight_" + loc1, "block/castle/wood_half_arch", plank_path + name + "_paintable_" + type);
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, "straight_" + loc0));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, LinSeedPaintable, Direction, BlockHalf, StairShape> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, Properties.BLOCK_HALF, Properties.STAIR_SHAPE);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc = loc1;
            if (paint == LinSeedPaintable.NONE) {
                finalLoc = loc0;
            }
            for (Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
                int dir = 90 + (direction.getHorizontalQuarterTurns() * 90);
                map.register(paint, direction, BlockHalf.TOP, StairShape.INNER_LEFT, modelOf("inner_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.INNER_RIGHT, modelOf("inner_" + finalLoc, true, dir + 90, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.OUTER_LEFT, modelOf("outer_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.OUTER_RIGHT, modelOf("outer_" + finalLoc, true, dir + 90, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.STRAIGHT, modelOf("straight_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.INNER_LEFT, modelOf("inner_" + finalLoc, true, dir - 90, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.INNER_RIGHT, modelOf("inner_" + finalLoc, true, dir, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.OUTER_LEFT, modelOf("outer_" + finalLoc, true, dir - 90, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, modelOf("outer_" + finalLoc, true, dir, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.STRAIGHT, modelOf("straight_" + finalLoc, true, dir, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

}
