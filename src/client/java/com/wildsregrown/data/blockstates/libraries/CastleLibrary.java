package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.ArrowSlitConnected;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;

public class CastleLibrary {

    private final static String modelPath = "castle/";

    public static void arrowSlit(BlockModelGenerators generator, String id, Block block, String texture, int tier) {

        String type = switch (tier){
            case 1 -> "refined";
            case 2 -> "cross";
            default -> "basic";
        };

        applyTextureToModel(generator, modelPath+id + "_single","block/castle/"+type+"_arrowslit_single", texture);
        applyTextureToModel(generator, modelPath+id + "_top","block/castle/"+type+"_arrowslit_top", texture);
        applyTextureToModel(generator, modelPath+id + "_middle","block/castle/"+type+"_arrowslit_middle", texture);
        applyTextureToModel(generator, modelPath+id + "_bottom","block/castle/"+type+"_arrowslit_bottom", texture);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+modelPath+id+ "_single"));

        PropertyDispatch.C2<MultiVariant, Direction, ArrowSlitConnected> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, ModProperties.ARROW_SLIT_CONNECTED);
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int dir = direction.get2DDataValue() * 90;
            int invDir = dir + 180;
            if (invDir > 270){invDir -= 360;}
            map.select(direction, ArrowSlitConnected.SINGLE, modelOf(modelPath+id + "_single", true, dir, 0));
            map.select(direction, ArrowSlitConnected.TOP,    modelOf(modelPath+id + "_top", true, dir, 0));
            map.select(direction, ArrowSlitConnected.MIDDLE, modelOf(modelPath+id + "_middle", true, dir, 0));
            map.select(direction, ArrowSlitConnected.BOTTOM, modelOf(modelPath+id + "_top", true, invDir, 180));
            map.select(direction, ArrowSlitConnected.BOTTOM_FLOOR, modelOf(modelPath+id + "_bottom", true, dir, 0));
        }
        CreateVariants(generator, block, map);
    }

    public static void woodenArrowSlit(BlockModelGenerators generator, Block block, String id, String name, int tier) {

        String loc0 = modelPath + id;
        String loc1 = loc0 + "_paintable";
        String type = switch (tier){
            case 1 -> "refined_wood_arrow_slit";
            case 2 -> "cross_wood_arrow_slit";
            default -> "basic_wood_arrow_slit";
        };

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_single",root+modelPath+type+"_single", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_top",root+modelPath+type+"_top", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_middle",root+modelPath+type+"_middle", plank_path + name + "_planks", log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_bottom",root+modelPath+type+"_bottom", plank_path + name + "_planks", log_path + name + "_wood");
        //applyTextureToModel(generator, loc0 + "_bottom_floor",root+modelPath+"wood_arrow_slit_bottom_floor", plank_path + name + "_" + type, log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+type+"_single", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_top",root+modelPath+type+"_top", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",root+modelPath+type+"_middle", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",root+modelPath+type+"_bottom", plank_path + "pine_paintable_planks", log_path + "pine_paintable_wood");
            //applyTextureToModel(generator, loc1 + "_bottom_floor",root+modelPath+"wood_arrow_slit_bottom_floor", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+type+"_single", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_top",root+modelPath+type+"_top", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",root+modelPath+type+"_middle", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",root+modelPath+type+"_bottom", plank_path + "fruit_paintable_planks", log_path + "fruit_paintable_wood");
            //applyTextureToModel(generator, loc1 + "_bottom_floor",root+modelPath+"wood_arrow_slit_bottom_floor", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+type+"_single", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_top",root+modelPath+type+"_top", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_middle",root+modelPath+type+"_middle", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_bottom",root+modelPath+type+"_bottom", plank_path + name + "_paintable_planks", log_path + name + "_paintable_wood");
            //applyTextureToModel(generator, loc1 + "_bottom_floor",root+modelPath+"wood_arrow_slit_bottom_floor", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + loc0 + "_single"));

        PropertyDispatch.C3<MultiVariant, LinSeedPaintable, Direction, ArrowSlitConnected> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, ModProperties.ARROW_SLIT_CONNECTED);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                int dir = direction.get2DDataValue() * 90;
                int invDir = dir + 180;
                if (invDir > 270){invDir -= 360;}
                map.select(paint, direction, ArrowSlitConnected.SINGLE, modelOf(finalLoc + "_single", true, dir, 0));
                map.select(paint, direction, ArrowSlitConnected.TOP,    modelOf(finalLoc + "_top", true, dir, 0));
                map.select(paint, direction, ArrowSlitConnected.MIDDLE, modelOf(finalLoc + "_middle", true, dir, 0));
                map.select(paint, direction, ArrowSlitConnected.BOTTOM, modelOf(finalLoc + "_top", true, invDir, 180));
                map.select(paint, direction, ArrowSlitConnected.BOTTOM_FLOOR, modelOf(finalLoc + "_bottom", true, dir, 0));
            }
        }

        CreateVariants(generator, block, map);
    }

    public static void woodenArch(BlockModelGenerators generator, Block block, String id, String name, String type) {

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

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C3<MultiVariant, LinSeedPaintable, Direction.Axis, Half> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_AXIS, BlockStateProperties.HALF);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getPossibleValues()) {
            String l = loc1;
            if (paint == LinSeedPaintable.NONE) {
                l = loc0;
            }
            map.select(paint, Direction.Axis.X, Half.TOP, modelOf(l, true, 90, 0));
            map.select(paint, Direction.Axis.Z, Half.TOP, modelOf(l, true, 0, 0));
            map.select(paint, Direction.Axis.X, Half.BOTTOM, modelOf(l, true, 90, 180));
            map.select(paint, Direction.Axis.Z, Half.BOTTOM, modelOf(l, true, 0, 180));
        }

        CreateVariants(generator, block, map);

    }

    public static void woodenHalfArch(BlockModelGenerators generator, String name, Block block, String type) {

        String loc0 = modelPath + name + "_" + type;
        String loc1 = modelPath + name + "_paintable_" + type;
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
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+"straight_" + loc0));

        PropertyDispatch.C4<MultiVariant, LinSeedPaintable, Direction, Half, StairsShape> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc = loc1;
            if (paint == LinSeedPaintable.NONE) {
                finalLoc = loc0;
            }
            for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                int dir = 90 + (direction.get2DDataValue() * 90);
                map.select(paint, direction, Half.TOP, StairsShape.INNER_LEFT, modelOf("inner_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.TOP, StairsShape.INNER_RIGHT, modelOf("inner_" + finalLoc, true, dir + 90, 180));
                map.select(paint, direction, Half.TOP, StairsShape.OUTER_LEFT, modelOf("outer_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.TOP, StairsShape.OUTER_RIGHT, modelOf("outer_" + finalLoc, true, dir + 90, 180));
                map.select(paint, direction, Half.TOP, StairsShape.STRAIGHT, modelOf("straight_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.BOTTOM, StairsShape.INNER_LEFT, modelOf("inner_" + finalLoc, true, dir - 90, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.INNER_RIGHT, modelOf("inner_" + finalLoc, true, dir, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.OUTER_LEFT, modelOf("outer_" + finalLoc, true, dir - 90, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.OUTER_RIGHT, modelOf("outer_" + finalLoc, true, dir, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.STRAIGHT, modelOf("straight_" + finalLoc, true, dir, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

}
