package com.wildsregrown.data.blockstates;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.properties.*;
import com.wildsregrown.blocks.wood.tree.FruitingLeaves;
import com.wildsregrown.blocks.wood.tree.HalfLog;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.data.blockstates.libraries.FramingLibrary;
import com.wildsregrown.registries.groups.WoodGroup;
import net.minecraft.block.Block;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.block.enums.StairShape;
import net.minecraft.client.data.*;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.client.render.model.json.ModelVariant;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.CastleLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.FramingLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.LuxuryLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.WoodDeco.*;
import static com.wildsregrown.data.blockstates.libraries.WoodInterior.*;
import static com.wildsregrown.registries.ModItemGroups.id;
import static net.minecraft.client.data.BlockStateModelGenerator.*;

public class WoodGroupBlockStates {

    public final static String log_path = "block/logs/";
    public final static String plank_path = "block/planks/";

    private final BlockStateModelGenerator generator;

    public WoodGroupBlockStates(BlockStateModelGenerator generator) {
        this.generator = generator;
    }

    public static void source(BlockStateModelGenerator generator, String id, Block block, String family) {
        final String modelPath = "block/tree/";
        applyTextureToModel(generator, modelPath + id, "block/trees/tree_source", log_path + family + "_bark", log_path + family + "_wood_end");
        generator.blockStateCollector.accept(createSingletonBlockState(block, createWeightedVariant(Identifier.of(modid, modelPath + id))));
        generator.registerParentedItemModel(block, Identifier.of(modid, modelPath + id));
    }

    public void build(WoodGroup group) {
        Block block;
        String texture;
        String name = id(group.get(WoodGroup.Common.log)).replace("_log", "");

        block = group.get(WoodGroup.Common.leaves);
        texture = "block/leaves/" + id(block);

        if (block instanceof FruitingLeaves) {
            fruitingLeaves(generator, block, texture);
        } else {
            leaves(generator, block, texture);
        }

        block = group.get(WoodGroup.Common.branch);
        branch(generator, id(block), block, name);

        log(generator, group.get(WoodGroup.Common.log), name, "_bark");
        log(generator, group.get(WoodGroup.Common.stripped_log), name, "_wood");

        slab(generator, group.get(WoodGroup.Common.slab), name, "_bark");
        slab(generator, group.get(WoodGroup.Common.stripped_slab), name, "_wood");

        beam(generator, group.get(WoodGroup.Common.beam), name, "_bark");
        beam(generator, group.get(WoodGroup.Common.stripped_beam), name, "_wood");

        planks(generator, group.get(WoodGroup.Common.planks), name, "planks");
        stairs(generator, name, group.get(WoodGroup.Common.planks_stairs), "planks");

        block = group.get(WoodGroup.Common.portable_workbench);
        portableWorkbench(generator, block, id(block), name);

        if (group.framingExist()) {

            planks(generator, group.get(WoodGroup.Framing.parquet), name, "parquet");
            stairs(generator, name, group.get(WoodGroup.Framing.parquet_stairs), "parquet");

            planks(generator, group.get(WoodGroup.Framing.siding), name, "siding");
            stairs(generator, name, group.get(WoodGroup.Framing.siding_stairs), "siding");

            block = group.get(WoodGroup.Framing.support);
            woodenSupport(generator, id(block), block, name);

            block = group.get(WoodGroup.Framing.arch);
            woodenArch(generator, block, id(block), name, "planks");

            block = group.get(WoodGroup.Framing.half_arch);
            woodenHalfArch(generator, name, block, "planks");

            block = group.get(WoodGroup.Framing.roof);
            FramingLibrary.roof(generator, block, id(block), name);

            block = group.get(WoodGroup.Framing.sod_roof);
            sodRoof(generator, block, id(block), name);

            block = group.get(WoodGroup.Framing.arrow_slit);
            woodenArrowSlit(generator, block, id(block), name, "planks");

            block = group.get(WoodGroup.Framing.ladder);
            ladder(generator, block, id(block), name);

            block = group.get(WoodGroup.Framing.window_cover);
            windowCover(generator, block, id(block), name, "planks");

            block = group.get(WoodGroup.Framing.crate);
            crate(generator, block, id(block), name);

            block = group.get(WoodGroup.Framing.crate_lid);
            crateLid(generator, block, id(block), name);

        }

        if (group.furnitureExist()) {
            block = group.get(WoodGroup.Furniture.night_stand);
            counterChest(generator, block, id(block), name, "night_stand_closed", "night_stand_open");

            block = group.get(WoodGroup.Furniture.drawer);
            drawer(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.storage_table);
            storageTable(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.stool);
            stool(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.chair);
            chair(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.table);
            table(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.counter);
            counter(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.counter_shelves);
            counterShelves(generator, block, id(block), name, "counter_shelves");

            block = group.get(WoodGroup.Furniture.counter_chest);
            counterChest(generator, block, id(block), name, "counter_chest_closed", "counter_chest_open");

            block = group.get(WoodGroup.Furniture.cabinet);
            counterChest(generator, block, id(block), name, "cabinets_closed", "cabinets_open");

            block = group.get(WoodGroup.Furniture.cabinet_shelf);
            counterShelves(generator, block, id(block), name, "cabinets_shelves");

            block = group.get(WoodGroup.Furniture.shelves);
            shelves(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.throne);
            throne(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.mirror);
            mirror(generator, block, id(block), name);

            block = group.get(WoodGroup.Furniture.table_chest);
            tableChest(generator, block, id(block), name);
        }

        if (group.utensilsExists()) {
            block = group.get(WoodGroup.Utensils.mug);
            mug(generator, block, id(block), name);

            block = group.get(WoodGroup.Utensils.bowl);
            bowl(generator, block, id(block), name);

            block = group.get(WoodGroup.Utensils.cutting_board);
            cuttingBoard(generator, block, id(block), name);
        }

    }

    public static void stairs(BlockStateModelGenerator generator, String name, Block block, String type) {

        String loc0 = plank_path + name + "_" + type;
        String loc1 = plank_path + name + "_paintable_" + type;
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, "inner_stairs_" + loc0, "block/inner_stairs", plank_path + name + "_" + type);
        applyTextureToModel(generator, "outer_stairs_" + loc0, "block/outer_stairs", plank_path + name + "_" + type);
        applyTextureToModel(generator, "straight_stairs_" + loc0, "block/vanilla_stairs", plank_path + name + "_" + type);
        if (pines) {
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/inner_stairs", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/outer_stairs", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/vanilla_stairs", plank_path + "pine_paintable_" + type);
        }else if(fruit){
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/inner_stairs", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/outer_stairs", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/vanilla_stairs", plank_path + "fruit_paintable_" + type);
        }else {
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/inner_stairs", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/outer_stairs", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/vanilla_stairs", plank_path + name + "_paintable_" + type);
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, "straight_stairs_" + loc0));

        BlockStateVariantMap.QuadrupleProperty<WeightedVariant, LinSeedPaintable, Direction, BlockHalf, StairShape> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, Properties.BLOCK_HALF, Properties.STAIR_SHAPE);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc = loc1;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }
            for(Direction direction : Properties.HORIZONTAL_FACING.getValues()) {
                int dir = 90 + (direction.getHorizontalQuarterTurns()*90);
                map.register(paint, direction, BlockHalf.TOP, StairShape.INNER_LEFT, modelOf("inner_stairs_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.INNER_RIGHT, modelOf("inner_stairs_" + finalLoc, true, dir+90, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.OUTER_LEFT, modelOf("outer_stairs_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.OUTER_RIGHT, modelOf("outer_stairs_" + finalLoc, true, dir+90, 180));
                map.register(paint, direction, BlockHalf.TOP, StairShape.STRAIGHT, modelOf("straight_stairs_" + finalLoc, true, dir, 180));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.INNER_LEFT, modelOf("inner_stairs_" + finalLoc, true, dir-90, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.INNER_RIGHT, modelOf("inner_stairs_" + finalLoc, true, dir, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.OUTER_LEFT, modelOf("outer_stairs_" + finalLoc, true, dir-90, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.OUTER_RIGHT, modelOf("outer_stairs_" + finalLoc, true, dir, 0));
                map.register(paint, direction, BlockHalf.BOTTOM, StairShape.STRAIGHT, modelOf("straight_stairs_" + finalLoc, true, dir, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

    private static void windowCover(BlockStateModelGenerator generator, Block block, String id, String name, String type) {

        String modelPath = "block/framing/";

        String loc0 = modelPath + id + "_" + type;
        String loc1 = modelPath + id + "_paintable_" + type;

        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0+ "_1","block/framing/window_cover_0", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_2","block/framing/window_cover_1", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_3","block/framing/window_cover_2", plank_path + name + "_" + type, log_path + name + "_wood");
        applyTextureToModel(generator, loc0 + "_4","block/framing/window_cover_3", plank_path + name + "_" + type, log_path + name + "_wood");
        if (pines) {
            applyTextureToModel(generator, loc1  + "_1","block/framing/window_cover_0", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_2","block/framing/window_cover_1", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_3","block/framing/window_cover_2", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
            applyTextureToModel(generator, loc1 + "_4","block/framing/window_cover_3", plank_path + "pine_paintable_" + type, log_path + "pine_paintable_wood");
        }else if (fruit) {
            applyTextureToModel(generator, loc1 + "_1","block/framing/window_cover_0", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_2","block/framing/window_cover_1", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_3","block/framing/window_cover_2", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
            applyTextureToModel(generator, loc1 + "_4","block/framing/window_cover_3", plank_path + "fruit_paintable_" + type, log_path + "fruit_paintable_wood");
        }else {
            applyTextureToModel(generator, loc1 + "_1","block/framing/window_cover_0", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_2","block/framing/window_cover_1", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_3","block/framing/window_cover_2", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
            applyTextureToModel(generator, loc1 + "_4","block/framing/window_cover_3", plank_path + name + "_paintable_" + type, log_path + name + "_paintable_wood");
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, loc0 + "_1"));

        BlockStateVariantMap.TripleProperty<WeightedVariant, LinSeedPaintable, Direction, Integer> map = BlockStateVariantMap.models(ModProperties.LINSEED_PAINT, Properties.HORIZONTAL_FACING, ModProperties.VARIATIONS_4);

        for(LinSeedPaintable paintable : ModProperties.LINSEED_PAINT.getValues()){
            String finalLoc = paintable == LinSeedPaintable.NONE ? loc0 : loc1;

            for (int i : ModProperties.VARIATIONS_4.getValues()) {
                for (Direction direction : Properties.HORIZONTAL_FACING.getValues()){
                    int dir = direction.getHorizontalQuarterTurns()*90;
                    map.register(paintable, direction, i, modelOf(finalLoc + "_" + i, false, dir, 0));
                }
            }

        }

        CreateVariants(generator, block, map);

    }


    /**
     * Common wood states
     */
    public static void leaves(BlockStateModelGenerator generator, Block block, String id) {
        int vars = 4;
        String suffix = "";
        if (id.contains("larch") || id.contains("spruce")){
            suffix = "_pine";
        }
        for (int i = 0; i < vars; i++) {
            applyTextures(generator, id + "_" + i, Identifier.of(modid, "block/leaves" + suffix + "_" + i), new Pair<>("0", Identifier.of(modid, id)));
        }
        WeightedVariant[] map = new WeightedVariant[vars * 4];
        for (int i = 0; i < vars; i++) {
            int k = i * 4;
            map[k] = modelOf(id + "_" + i, false, 0, 0);
            map[k + 1] = modelOf(id + "_" + i, false, 90, 0);
            map[k + 2] = modelOf(id + "_" + i, false, 180, 0);
            map[k + 3] = modelOf(id + "_" + i, false, 270, 0);
        }
        //generator.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(block, map));

        if (id.contains("jacaranda")) {
            generator.registerTintedItemModel(block, Identifier.of(modid, id + "_0"), new ConstantTintSource(Colors.richLilac));
        } else {
            generator.registerTintedItemModel(block, Identifier.of(modid, id + "_0"), new ConstantTintSource(Colors.pastelGreen));
        }
    }

    public static void fruitingLeaves(BlockStateModelGenerator generator, Block block, String id) {
        final String modelPath = "block/leaves";

        applyTextureToModel(generator, modelPath+id + "_0", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_1", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_2", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_3", modelPath, id);

        generator.registerTintedItemModel(block, Identifier.of(modid, modelPath+id + "_1"), new GrassTintSource());

        CreateVariants(generator, block, BlockStateVariantMap.models(ModProperties.FRUITING)
                .register(0, modelOf(modelPath+id + "_0", false, 0, 0))
                .register(1, modelOf(modelPath+id + "_1", false, 0, 0))
                .register(2, modelOf(modelPath+id + "_2", false, 0, 0))
                .register(3, modelOf(modelPath+id + "_3", false, 0, 0))
        );
    }

    public static void branch(BlockStateModelGenerator generator, String id, Block block, String name) {

        applyTextureToModel(generator, id + "_diagonal","block/branch_diagonal", log_path + name +"_bark");
        applyTextureToModel(generator, id + "_face","block/branch_face", log_path + name +"_bark");
        applyTextureToModel(generator, id + "_corner","block/branch_corner", log_path + name +"_bark");

        generator.registerParentedItemModel(block, Identifier.of(modid, id + "_diagonal"));

        CreateVariants(generator, block, BlockStateVariantMap.models(OrdinalDirection.DIRECTIONS, Verticality.VERTICALITY)
                .register(OrdinalDirection.N , Verticality.LEVEL, modelOf(id + "_face"    , false, 0  , 0))
                .register(OrdinalDirection.E , Verticality.LEVEL, modelOf(id + "_face"    , false, 90 , 0))
                .register(OrdinalDirection.S , Verticality.LEVEL, modelOf(id + "_face"    , false, 180, 0))
                .register(OrdinalDirection.W , Verticality.LEVEL, modelOf(id + "_face"    , false, 270, 0))
                .register(OrdinalDirection.N , Verticality.UP   , modelOf(id + "_diagonal", false, 270, 0))
                .register(OrdinalDirection.E , Verticality.UP   , modelOf(id + "_diagonal", false, 0  , 0))
                .register(OrdinalDirection.S , Verticality.UP   , modelOf(id + "_diagonal", false, 90 , 0))
                .register(OrdinalDirection.W , Verticality.UP   , modelOf(id + "_diagonal", false, 180, 0))
                .register(OrdinalDirection.N , Verticality.DOWN , modelOf(id + "_diagonal", false, 270, 180))
                .register(OrdinalDirection.W , Verticality.DOWN , modelOf(id + "_diagonal", false, 180, 180))
                .register(OrdinalDirection.S , Verticality.DOWN , modelOf(id + "_diagonal", false, 90 , 180))
                .register(OrdinalDirection.E , Verticality.DOWN , modelOf(id + "_diagonal", false, 0  , 180))
                .register(OrdinalDirection.NW, Verticality.LEVEL, modelOf(id + "_diagonal", false, 180, 270))
                .register(OrdinalDirection.NE, Verticality.LEVEL, modelOf(id + "_diagonal", false, 0  , 90))
                .register(OrdinalDirection.SE, Verticality.LEVEL, modelOf(id + "_diagonal", false, 0  , 270))
                .register(OrdinalDirection.SW, Verticality.LEVEL, modelOf(id + "_diagonal", false, 180, 90))
                .register(OrdinalDirection.NE, Verticality.UP   , modelOf(id + "_corner"  , false, 0  , 0))
                .register(OrdinalDirection.NW, Verticality.UP   , modelOf(id + "_corner"  , false, 270, 0))
                .register(OrdinalDirection.SE, Verticality.UP   , modelOf(id + "_corner"  , false, 90 , 0))
                .register(OrdinalDirection.SW, Verticality.UP   , modelOf(id + "_corner"  , false, 180, 0))
                .register(OrdinalDirection.NE, Verticality.DOWN , modelOf(id + "_corner"  , false, 270, 180))
                .register(OrdinalDirection.NW, Verticality.DOWN , modelOf(id + "_corner"  , false, 180, 180))
                .register(OrdinalDirection.SE, Verticality.DOWN , modelOf(id + "_corner"  , false, 0  , 180))
                .register(OrdinalDirection.SW, Verticality.DOWN , modelOf(id + "_corner"  , false, 90 , 180))
        );
    }

    public static void log(BlockStateModelGenerator generator, Block block, String name, String type) {

        String loc0 = log_path + name + type;
        String loc1 = log_path + name + "_paintable" + type;
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        if (type.contains("bark")) {
            applyTextureToModel(generator, loc0, "block/log", log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc1, "block/log", log_path + name + type, log_path + "pine_paintable_wood_end");
            }else if(fruit){
                applyTextureToModel(generator, loc1, "block/log", log_path + name + type, log_path + "fruit_paintable_wood_end");
            }else {
                applyTextureToModel(generator, loc1, "block/log", log_path + name + type, log_path + name + "_paintable_wood_end");
            }
        }else {
            applyTextureToModel(generator, loc0, "block/stripped_log", log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc1, "block/stripped_log", log_path + "pine_paintable" + type, log_path + "pine_paintable_wood_end");
            }else if(fruit){
                applyTextureToModel(generator, loc1, "block/stripped_log", log_path + "fruit_paintable" + type, log_path + "fruit_paintable_wood_end");
            }else {
                applyTextureToModel(generator, loc1, "block/stripped_log", log_path + name + "_paintable" + type, log_path + name + "_paintable_wood_end");
            }
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Direction.Axis, LinSeedPaintable> map = BlockStateVariantMap.models(Properties.AXIS, ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint.asString().equals("none")){
                finalLoc = loc0;
            }else {
                finalLoc = loc1;
            }
            map
                .register(Direction.Axis.Y, paint, modelOf(finalLoc, false, 0, 0))
                .register(Direction.Axis.Z, paint, modelOf(finalLoc, false, 0, 90))
                .register(Direction.Axis.X, paint, modelOf(finalLoc, false, 90, 90));
        }
        CreateVariants(generator, block, map);
    }

    public static void slab(BlockStateModelGenerator generator, Block block, String name, String type) {

        final String loc0 = log_path + name + "_half" + type;
        final String loc1 = log_path + name + "_paintable" + "_half" + type;
        final String r = "_rotated";

        final boolean pines = name.contains("larch") || name.contains("spruce");
        final boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        if (type.contains("bark")) {
            applyTextureToModel(generator, loc0, "block/half_log", log_path + name + type, log_path + name + "_wood_end", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1, "block/half_log", log_path + name + type, log_path + "pine_paintable_wood_end", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1, "block/half_log", log_path + name + type, log_path + "fruit_paintable_wood_end", log_path + "fruit_paintable_wood");
            }else{
                applyTextureToModel(generator, loc1, "block/half_log", log_path + name + type, log_path + name + "_paintable_wood_end", log_path + name + "_paintable_wood");
            }
            applyTextureToModel(generator, loc0+r, "block/half_log"+r, log_path + name + type, log_path + name + "_wood_end", log_path + name + "_wood");
            if (pines) {
                applyTextureToModel(generator, loc1 +r, "block/half_log"+r, log_path + name + type, log_path + "pine_paintable_wood_end", log_path + "pine_paintable_wood");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 +r, "block/half_log"+r, log_path + name + type, log_path + "fruit_paintable_wood_end", log_path + "fruit_paintable_wood");
            }else {
                applyTextureToModel(generator, loc1 +r, "block/half_log"+r, log_path + name + type, log_path + name + "_paintable_wood_end", log_path + name + "_paintable_wood");
            }
        }else {
            applyTextureToModel(generator, loc0, "block/stripped_half_log", log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc1, "block/stripped_half_log", log_path + "pine_paintable" + type, log_path + "pine_paintable_wood_end");
            } else if (fruit) {
                applyTextureToModel(generator, loc1, "block/stripped_half_log", log_path + "fruit_paintable" + type, log_path + "fruit_paintable_wood_end");
            }else {
                applyTextureToModel(generator, loc1, "block/stripped_half_log", log_path + name + "_paintable" + type, log_path + name + "_paintable_wood_end");
            }
            applyTextureToModel(generator, loc0+r, "block/stripped_half_log"+r, log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc1 +r, "block/stripped_half_log"+r, log_path + "pine_paintable" + type, log_path + "pine_paintable_wood_end");
            } else if (fruit) {
                applyTextureToModel(generator, loc1 +r, "block/stripped_half_log"+r, log_path + "fruit_paintable" + type, log_path + "fruit_paintable_wood_end");
            }else {
                applyTextureToModel(generator, loc1 +r, "block/stripped_half_log"+r, log_path + name + "_paintable" + type, log_path + name + "_paintable_wood_end");
            }
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.TripleProperty<WeightedVariant, Boolean, Direction, LinSeedPaintable> map = BlockStateVariantMap.models(HalfLog.rotated, Properties.FACING, ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }else {
                finalLoc = loc1;
            }
            map
            .register(false, Direction.UP   , paint, modelOf(finalLoc, false, 0, 0))
            .register(false, Direction.DOWN , paint, modelOf(finalLoc, false, 0, 180))
            .register(false, Direction.NORTH, paint, modelOf(finalLoc, false, 0, 90))
            .register(false, Direction.SOUTH, paint, modelOf(finalLoc, false, 180, 90))
            .register(false, Direction.EAST , paint, modelOf(finalLoc, false, 90, 90))
            .register(false, Direction.WEST , paint, modelOf(finalLoc, false, 270, 90))
            .register(true , Direction.UP   , paint, modelOf(finalLoc, false, 0, 0))
            .register(true , Direction.DOWN , paint, modelOf(finalLoc, false, 0, 180))
            .register(true , Direction.NORTH, paint, modelOf(finalLoc, false, 0, 90))
            .register(true , Direction.SOUTH, paint, modelOf(finalLoc, false, 180, 90))
            .register(true , Direction.EAST , paint, modelOf(finalLoc, false, 90, 90))
            .register(true , Direction.WEST , paint, modelOf(finalLoc, false, 270, 90));

        }
        CreateVariants(generator, block, map);
    }

    public static void beam(BlockStateModelGenerator generator, Block block, String name, String type) {
        String loc0 = log_path + name + "_beam" + type;
        String loc2 = log_path + name + "_beam" + "_paintable" + type;
        boolean pines = name.contains("larch") || name.contains("spruce") || name.contains("sequoia");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("citrus") || name.contains("plum") || name.contains("walnut");

        if (type.contains("bark")) {
            applyTextureToModel(generator, loc0, "block/beam", log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc2, "block/beam", log_path + type, log_path + "pine_paintable_wood_end");
            } else if (fruit) {
                applyTextureToModel(generator, loc2, "block/beam", log_path + type, log_path + "fruit_paintable_wood_end");
            }else{
                applyTextureToModel(generator, loc2, "block/beam", log_path + name + type, log_path + name + "_paintable_wood_end");
            }
        }else {
            applyTextureToModel(generator, loc0, "block/stripped_beam", log_path + name + type, log_path + name + "_wood_end");
            if (pines) {
                applyTextureToModel(generator, loc2, "block/stripped_beam", log_path + "pine_paintable" + type, log_path + "pine_paintable_wood_end");
            } else if (fruit) {
                applyTextureToModel(generator, loc2, "block/stripped_beam", log_path + "fruit_paintable" + type, log_path + "fruit_paintable_wood_end");
            }else {
                applyTextureToModel(generator, loc2, "block/stripped_beam", log_path + name + "_paintable" + type, log_path + name + "_paintable_wood_end");
            }
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, loc0));

        BlockStateVariantMap.TripleProperty<WeightedVariant, Direction.Axis, LinSeedPaintable, Quadrant> map = BlockStateVariantMap.models(Properties.AXIS, ModProperties.LINSEED_PAINT, Quadrant.QUADRANT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }else {
                finalLoc = loc2;
            }
            map
                    .register(Direction.Axis.X, paint, Quadrant.PP , modelOf(finalLoc, true, 90  , 180))
                    .register(Direction.Axis.X, paint, Quadrant.NP , modelOf(finalLoc, true, 270 , 180))
                    .register(Direction.Axis.X, paint, Quadrant.NN , modelOf(finalLoc, true, 270 , 0))
                    .register(Direction.Axis.X, paint, Quadrant.PN , modelOf(finalLoc, true, 90  , 0))
                    .register(Direction.Axis.Z, paint, Quadrant.PP , modelOf(finalLoc, true, 0  , 180))
                    .register(Direction.Axis.Z, paint, Quadrant.NP , modelOf(finalLoc, true, 180 , 180))
                    .register(Direction.Axis.Z, paint, Quadrant.NN , modelOf(finalLoc, true, 180 , 0))
                    .register(Direction.Axis.Z, paint, Quadrant.PN , modelOf(finalLoc, true, 0  , 0))
                    .register(Direction.Axis.Y, paint, Quadrant.PP , modelOf(finalLoc, true, 0  , 90))
                    .register(Direction.Axis.Y, paint, Quadrant.NP , modelOf(finalLoc, true, 270  , 90))
                    .register(Direction.Axis.Y, paint, Quadrant.NN , modelOf(finalLoc, true, 180  , 90))
                    .register(Direction.Axis.Y, paint, Quadrant.PN , modelOf(finalLoc, true, 90  , 90));
        }
        CreateVariants(generator, block, map);
    }
}
