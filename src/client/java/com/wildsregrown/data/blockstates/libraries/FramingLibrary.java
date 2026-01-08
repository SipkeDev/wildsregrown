package com.wildsregrown.data.blockstates.libraries;

import com.wildsregrown.blocks.properties.LinSeedPaintable;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.connecting.SupportConnected;
import com.wildsregrown.blocks.properties.connecting.HorizontalConnected;
import net.minecraft.block.Block;
import net.minecraft.block.enums.DoorHinge;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.data.*;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;
import org.apache.commons.lang3.tuple.Triple;

import java.util.stream.Stream;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.log_path;
import static com.wildsregrown.data.blockstates.WoodGroupBlockStates.plank_path;

public class FramingLibrary {

    private final static String modelPath = "framing/";
    
    public static void planks(BlockStateModelGenerator generator, Block block, String id, String type) {

        String texture0 = plank_path + id + "_" + type;
        String texture2 = plank_path + id + "_paintable_" + type;

        //compress pines
        if (id.contains("larch") || id.contains("spruce") || id.contains("sequoia")) {
            texture2 = plank_path + "pine_paintable_" + type;
        }
        //compress fruit
        if (id.contains("apple") || id.contains("pear") || id.contains("plum")) {
            texture2 = plank_path + "fruit_paintable_" + type;
        }

        type = "_" + type;
        applyTextureToModel(generator, modelPath + id + type + "_1", "block/layered_4", texture0);
        applyTextureToModel(generator, modelPath + id + type + "_2", "block/layered_8", texture0);
        applyTextureToModel(generator, modelPath + id + type + "_3", "block/layered_12", texture0);
        applyTextureToModel(generator, modelPath + id + type + "_4", "block/layered_full", texture0);
        applyTextureToModel(generator, modelPath + id + type + "_paintable_1", "block/layered_4", texture2);
        applyTextureToModel(generator, modelPath + id + type + "_paintable_2", "block/layered_8", texture2);
        applyTextureToModel(generator, modelPath + id + type + "_paintable_3", "block/layered_12", texture2);
        applyTextureToModel(generator, modelPath + id + type + "_paintable_4", "block/layered_full", texture2);

        generator.registerParentedItemModel(block, Identifier.of(modid, root+modelPath + id + type + "_2"));

        BlockStateVariantMap.TripleProperty<WeightedVariant, Direction, Integer, LinSeedPaintable> map = BlockStateVariantMap.models(Properties.FACING, ModProperties.QUARTER_LAYERS, ModProperties.LINSEED_PAINT);

        Triple<Direction, Integer, Integer>[] directions = new Triple[]{
                Triple.of(Direction.UP   , 0  , 0),
                Triple.of(Direction.DOWN , 0  , 180),
                Triple.of(Direction.NORTH, 0  , 90),
                Triple.of(Direction.SOUTH, 180, 90),
                Triple.of(Direction.EAST , 90 , 90),
                Triple.of(Direction.WEST , 270, 90)

        };

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalType;
            if (paint.asString().equals("none")){
                finalType = type;
            }else {
                finalType = type + "_paintable";
            }
            Stream.of(directions).forEach(ctx -> {
                        map
                                .register(ctx.getLeft(), 1, paint, modelOf(modelPath + id + finalType + "_1", true, ctx.getMiddle(), ctx.getRight()))
                                .register(ctx.getLeft(), 2, paint, modelOf(modelPath + id + finalType + "_2", true, ctx.getMiddle(), ctx.getRight()))
                                .register(ctx.getLeft(), 3, paint, modelOf(modelPath + id + finalType + "_3", true, ctx.getMiddle(), ctx.getRight()))
                                .register(ctx.getLeft(), 4, paint, modelOf(modelPath + id + finalType + "_4", true, ctx.getMiddle(), ctx.getRight()));
                    }
            );
        }

        CreateVariants(generator, block, map);
    }

    public static void ladder(BlockStateModelGenerator generator, Block block, String id, String name) {

        String texture = "wood";
        String texture0 = log_path + name + "_" + texture;
        String texture1 = log_path + name + "_paintable_" + texture;

        //compress pines
        if (name.contains("larch") || name.contains("spruce")) {
            texture1 = log_path + "pine_paintable_" + texture;
        }
        //compress fruit
        if (name.contains("apple") || name.contains("pear") || name.contains("plum")) {
            texture1 = log_path + "fruit_paintable_" + texture;
        }
        applyTextureToModel(generator, modelPath + id, root+"framing/ladder", texture0);
        applyTextureToModel(generator, modelPath + id + "_paintable", root+"framing/ladder", texture1);

        generator.registerParentedItemModel(block, Identifier.of(modid, root+modelPath + id));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, LinSeedPaintable> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.LINSEED_PAINT);

        Triple<Direction, Integer, Integer>[] directions = new Triple[]{
                Triple.of(Direction.SOUTH, 0, 0),
                Triple.of(Direction.WEST, 90, 0),
                Triple.of(Direction.NORTH, 180, 0),
                Triple.of(Direction.EAST, 270, 0)
        };

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalType;
            if (paint.asString().equals("none")) {
                finalType = modelPath + id;
            } else {
                finalType = modelPath + id + "_paintable";
            }
            Stream.of(directions).forEach(ctx -> {
                        map
                                .register(ctx.getLeft(), paint, modelOf(finalType, true, ctx.getMiddle(), ctx.getRight()));
            }
            );
        }

        CreateVariants(generator, block, map);
    }

    public static void sodRoof(BlockStateModelGenerator generator, Block block, String id, String name) {

        String loc0 = modelPath+"roof/" + name + "_planks";
        String loc1 = modelPath+"roof/" + name + "_paintable_planks";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_straight",root+modelPath+"roof_moss", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_left",root+modelPath+"roof_moss_left", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_right",root+modelPath+"roof_moss_right", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_single",root+modelPath+"roof_moss_single", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_top", root+modelPath+"roof_moss_top", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_top_side", root+modelPath+"roof_moss_top_side", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_top_single", root+modelPath+"roof_moss_top_single", plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_straight",root+modelPath+"roof_moss", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left",root+modelPath+"roof_moss_left", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right",root+modelPath+"roof_moss_right", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+"roof_moss_single", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top", root+modelPath+"roof_moss_top", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top_side", root+modelPath+"roof_moss_top_side", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top_single", root+modelPath+"roof_moss_top_single", plank_path + "pine_paintable_planks");
        } else if (fruit) {
            applyTextureToModel(generator, loc1 + "_straight",root+modelPath+"roof_moss", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_left",root+modelPath+"roof_moss_left", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_right",root+modelPath+"roof_moss_right", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+"roof_moss_single", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top", root+modelPath+"roof_moss_top", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top_side", root+modelPath+"roof_moss_top_side", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_top_single", root+modelPath+"roof_moss_top_single", plank_path + "fruit_paintable_planks");
        } else {
            applyTextureToModel(generator, loc1 + "_straight",root+modelPath+"roof_moss", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_left",root+modelPath+"roof_moss_left", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_right",root+modelPath+"roof_moss_right", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_single",root+modelPath+"roof_moss_single", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_top", root+modelPath+"roof_moss_top", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_top_side", root+modelPath+"roof_moss_top_side", plank_path + name + "_paintable_" + "_planks");
            applyTextureToModel(generator, loc1 + "_top_single", root+modelPath+"roof_moss_top_single", plank_path + name + "_paintable_" + "_planks");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+ loc0 + "_single"));

        BlockStateVariantMap.TripleProperty<WeightedVariant, LinSeedPaintable, HorizontalConnected, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, ModProperties.HORIZONTAL_CONNECTED, Properties.FACING);

        for (LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String loc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
                 map.register(paint, HorizontalConnected.LEFT  , Direction.NORTH, modelOf(loc + "_left", false, 180, 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.NORTH, modelOf(loc + "_right", false, 180, 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.NORTH, modelOf(loc + "_straight", false, 180  , 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.NORTH, modelOf(loc + "_single", false, 180  , 0))
                    .register(paint, HorizontalConnected.LEFT,   Direction.SOUTH, modelOf(loc + "_left", false, 0  , 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.SOUTH, modelOf(loc + "_right", false, 0  , 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.SOUTH, modelOf(loc + "_straight", false, 0  , 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.SOUTH, modelOf(loc + "_single", false, 0  , 0))
                    .register(paint, HorizontalConnected.LEFT  , Direction.EAST , modelOf(loc + "_left", false, 270, 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.EAST , modelOf(loc + "_right", false, 270, 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.EAST , modelOf(loc + "_straight", false, 270, 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.EAST , modelOf(loc + "_single", false, 270, 0))
                    .register(paint, HorizontalConnected.LEFT  , Direction.WEST , modelOf(loc + "_left", false, 90 , 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.WEST , modelOf(loc + "_right", false, 90 , 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.WEST , modelOf(loc + "_straight", false, 90 , 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.WEST , modelOf(loc + "_single", false, 90 , 0))
                    .register(paint, HorizontalConnected.LEFT  , Direction.DOWN , modelOf(loc + "_top_side", false, 0  , 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.DOWN , modelOf(loc + "_top_side", false, 180, 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.DOWN , modelOf(loc + "_top", false, 0  , 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.DOWN , modelOf(loc + "_top_single", false, 0  , 0))
                    .register(paint, HorizontalConnected.LEFT  , Direction.UP   , modelOf(loc + "_top_side", false, 270, 0))
                    .register(paint, HorizontalConnected.RIGHT , Direction.UP   , modelOf(loc + "_top_side", false, 90 , 0))
                    .register(paint, HorizontalConnected.MIDDLE, Direction.UP   , modelOf(loc + "_top", false, 90 , 0))
                    .register(paint, HorizontalConnected.SINGLE, Direction.UP   , modelOf(loc + "_top_single", false, 90 , 0));
        }
        CreateVariants(generator, block, map);
    }

    public static void roof(BlockStateModelGenerator generator, Block block, String id, String name) {

        String texture = "planks";
        String texture0 = plank_path + name + "_" + texture;
        String texture1 = plank_path + name + "_paintable_" + texture;

        //compress pines
        if (id.contains("larch") || id.contains("spruce")) {texture1 = plank_path + "pine_paintable_" + texture;}
        //compress fruit
        if (id.contains("apple") || id.contains("pear") || id.contains("plum")) {texture1 = plank_path + "fruit_paintable_" + texture;}

        applyTextureToModel(generator, modelPath + id, root+modelPath + "roof", texture0);
        applyTextureToModel(generator, modelPath + id + "_paintable", root+modelPath + "roof", texture1);

        generator.registerParentedItemModel(block, Identifier.of(modid, root+modelPath + id));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction, LinSeedPaintable> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.LINSEED_PAINT);

        Triple<Direction, Integer, Integer>[] directions = new Triple[]{
                Triple.of(Direction.SOUTH, 0, 0),
                Triple.of(Direction.WEST, 90, 0),
                Triple.of(Direction.NORTH, 180, 0),
                Triple.of(Direction.EAST, 270, 0)
        };

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalType;
            if (paint.asString().equals("none")) {
                finalType = modelPath + id;
            } else {
                finalType = modelPath + id + "_paintable";
            }
            Stream.of(directions).forEach(ctx -> {
                        map
                                .register(ctx.getLeft(), paint, modelOf(finalType, true, ctx.getMiddle(), ctx.getRight()));
                    }
            );
        }

        CreateVariants(generator, block, map);
    }

    public static void woodenSupport(BlockStateModelGenerator generator, String id, Block block, String name) {

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        boolean pines = id.contains("larch") || id.contains("spruce");
        boolean fruit = id.contains("apple") || id.contains("pear") || id.contains("walnut");

        String tex0 = log_path + name + "_wood";
        String tex1 = log_path + name + "_paintable_wood";
        if (pines){
            tex1 = log_path + "pine_paintable_wood";}
        if (fruit){
            tex1 = log_path + "fruit_paintable_wood";}

        for (SupportConnected connected : SupportConnected.values()) {
            //var 1
            BlockStateLibrary.applyTextureToModel(generator, loc0 + "_" + connected.asString() + "_1", root+modelPath + "wood_support_" + connected.asString(), tex0);
            BlockStateLibrary.applyTextureToModel(generator, loc1 + "_" + connected.asString() + "_1", root+modelPath + "wood_support_" + connected.asString(), tex1);
            //var 2
            BlockStateLibrary.applyTextureToModel(generator, loc0 + "_" + connected.asString() + "_2", root+modelPath + "thin_wood_support_" + connected.asString(), tex0);
            BlockStateLibrary.applyTextureToModel(generator, loc1 + "_" + connected.asString() + "_2", root+modelPath + "thin_wood_support_" + connected.asString(), tex1);
            //var 3
            BlockStateLibrary.applyTextureToModel(generator, loc0 + "_" + connected.asString() + "_3", root+modelPath + "thin_r_wood_support_" + connected.asString(), tex0);
            BlockStateLibrary.applyTextureToModel(generator, loc1 + "_" + connected.asString() + "_3", root+modelPath + "thin_r_wood_support_" + connected.asString(), tex1);
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_wall_1"));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, Direction, SupportConnected, LinSeedPaintable, Integer> map = BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.SUPPORT_STATE, ModProperties.LINSEED_PAINT, ModProperties.VARIATIONS_3);

        for (Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
            for (LinSeedPaintable paintable : LinSeedPaintable.values()) {
                for (SupportConnected connected : SupportConnected.values()) {
                    for (int var : ModProperties.VARIATIONS_3.getValues()){

                        String ext = "_" + connected.asString() + "_" + var, path;
                        int dir = connected == SupportConnected.CEIL ? direction.getAxis() == Direction.Axis.X ? 0 : 90 : (int)  direction.getPositiveHorizontalDegrees();
                        if (paintable == LinSeedPaintable.NONE){
                            path = loc0 + ext;
                        }else {
                            path = loc1 + ext;
                        }
                        map.register(direction, connected, paintable, var, modelOf(path, false, dir, 0));

                    }
                }
            }
        }

        CreateVariants(generator, block, map);

    }

    public static void crate(BlockStateModelGenerator generator, Block block, String id, String name) {

        String modelPath = "crafting/crate";
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_closed", root+modelPath + "_closed", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_open",root+modelPath + "_open", plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_closed", root+modelPath + "_closed", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_open",root+modelPath + "_open", plank_path + "pine_paintable_planks");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_closed", root+modelPath + "_closed", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_open",root+modelPath + "_open", plank_path + "fruit_paintable_planks");
        }else {
            applyTextureToModel(generator, loc1 + "_closed", root+modelPath + "_closed", plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_open",root+modelPath + "_open", plank_path + name + "_paintable_planks");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid,root+loc0 + "_open"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, LinSeedPaintable, Boolean> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT,Properties.OPEN);

        for(LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint, false, modelOf(finalLoc + "_closed"));
            map.register(paint,true, modelOf(finalLoc + "_open"));
        }

        CreateVariants(generator, block, map);

    }

    public static void crateLid(BlockStateModelGenerator generator, Block block, String id, String name) {

        String modelPath = "crafting/crate_lid";
        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0 + "_floor", root+modelPath + "_floor", plank_path + name + "_planks");
        applyTextureToModel(generator, loc0 + "_side",root+modelPath + "_side", plank_path + name + "_planks");
        if (pines) {
            applyTextureToModel(generator, loc1 + "_floor", root+modelPath + "_floor", plank_path + "pine_paintable_planks");
            applyTextureToModel(generator, loc1 + "_side",root+modelPath + "_side", plank_path + "pine_paintable_planks");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_floor", root+modelPath + "_floor", plank_path + "fruit_paintable_planks");
            applyTextureToModel(generator, loc1 + "_side",root+modelPath + "_side", plank_path + "fruit_paintable_planks");
        }else {
            applyTextureToModel(generator, loc1 + "_floor", root+modelPath + "_floor", plank_path + name + "_paintable_planks");
            applyTextureToModel(generator, loc1 + "_side",root+modelPath + "_side", plank_path + name + "_paintable_planks");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0 + "_floor"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, LinSeedPaintable, Direction> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HOPPER_FACING);
        for(LinSeedPaintable paint : ModProperties.LINSEED_PAINT.getValues()){
            String finalLoc = paint == LinSeedPaintable.NONE ? loc0 : loc1;
            map.register(paint, Direction.NORTH, modelOf(finalLoc + "_side", false, 0, 0));
            map.register(paint, Direction.SOUTH, modelOf(finalLoc + "_side", false, 180, 0));
            map.register(paint, Direction.EAST, modelOf(finalLoc + "_side", false, 90, 0));
            map.register(paint, Direction.WEST, modelOf(finalLoc + "_side", false, 270, 0));
            map.register(paint, Direction.DOWN, modelOf(finalLoc + "_floor"));
        }

        CreateVariants(generator, block, map);

    }

    public static void portableWorkbench(BlockStateModelGenerator generator, Block block, String id, String name) {
        String modelPath = "crafting/";

        String loc0 = modelPath + id;
        String loc1 = modelPath + id + "_paintable";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, root+modelPath + "portable_workbench", log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1, root+modelPath + "portable_workbench", log_path + "pine_paintable_wood");
        }else if(fruit){
            applyTextureToModel(generator, loc1, root+modelPath + "portable_workbench", log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1, root+modelPath + "portable_workbench", log_path + name + "_paintable_wood");
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, root+loc0));

        BlockStateVariantMap.SingleProperty<WeightedVariant, LinSeedPaintable> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc = loc1;
            if (paint == LinSeedPaintable.NONE) {
                finalLoc = loc0;
            }
            map.register(paint, modelOf(finalLoc));
        }

        CreateVariants(generator, block, map);
    }

}
