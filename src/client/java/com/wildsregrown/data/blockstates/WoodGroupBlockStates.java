package com.wildsregrown.data.blockstates;

import com.sipke.api.features.Colors;
import com.wildsregrown.blocks.properties.*;
import com.wildsregrown.blocks.properties.old_branch.Verticality;
import com.wildsregrown.blocks.carpentry.tree.FruitingLeaves;
import com.wildsregrown.blocks.carpentry.tree.HalfLog;
import com.wildsregrown.blocks.properties.tree.LinSeedPaintable;
import com.wildsregrown.data.blockstates.libraries.FramingLibrary;
import com.wildsregrown.data.blockstates.libraries.TudorLibrary;
import com.wildsregrown.registries.groups.WoodGroup;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.StairsShape;

import static com.mojang.math.Quadrant.*;
import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.CreateVariants;
import static com.wildsregrown.data.blockstates.libraries.CastleLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.FramingLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.FurnitureLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.LuxuryLibrary.*;
import static com.wildsregrown.data.blockstates.libraries.WoodInterior.*;
import static net.minecraft.client.data.models.BlockModelGenerators.*;

public class WoodGroupBlockStates {

    public final static String log_path = "block/logs/";
    public final static String plank_path = "block/planks/";

    private final BlockModelGenerators generator;

    public WoodGroupBlockStates(BlockModelGenerators generator) {
        this.generator = generator;
    }

    public void build(WoodGroup group) {

        Block block;
        String texture;
        String name = idFromBlock(group.get(WoodGroup.Common.log)).replace("_log", "");

        block = group.get(WoodGroup.Common.leaves);
        texture = "block/leaves/" + idFromBlock(block);

        if (block instanceof FruitingLeaves) {
            fruitingLeaves(generator, block, texture);
        } else {
            leaves(generator, block, texture);
        }

        log(generator, group.get(WoodGroup.Common.log), name, "_bark");
        log(generator, group.get(WoodGroup.Common.stripped_log), name, "_wood");

        slab(generator, group.get(WoodGroup.Common.slab), name, "_bark");
        slab(generator, group.get(WoodGroup.Common.stripped_slab), name, "_wood");

        //beam(generator, group.get(WoodGroup.Common.beam), name, "_bark");
        //beam(generator, group.get(WoodGroup.Common.stripped_beam), name, "_wood");
        //beam(generator, group.get(WoodGroup.Common.rough_plank), name, "_wood", "plank");
        //beam(generator, group.get(WoodGroup.Common.stick), name, "_bark", "stick");
        //beam(generator, group.get(WoodGroup.Common.stripped_stick), name, "_wood", "stick");

        planks(generator, group.get(WoodGroup.Common.planks), name, "planks");
        stairs(generator, name, group.get(WoodGroup.Common.planks_stairs), "planks");

        block = group.get(WoodGroup.Common.portable_workbench);
        portableWorkbench(generator, block, idFromBlock(block), name);

        if (group.framingExist()) {
            framing(group, name);
        }

        if (group.furnitureExist()) {
            furniture(group, name);
        }

        if (group.interiorExists()) {
            interior(group, name);
        }

    }

    private void framing(WoodGroup group, String name) {
        Block block;

        planks(generator, group.get(WoodGroup.Framing.parquet), name, "parquet");
        stairs(generator, name, group.get(WoodGroup.Framing.parquet_stairs), "parquet");

        planks(generator, group.get(WoodGroup.Framing.siding), name, "siding");
        stairs(generator, name, group.get(WoodGroup.Framing.siding_stairs), "siding");

        openStairs(generator, name, group.get(WoodGroup.Framing.open_stairs));

        ///Beam stack
        block = group.get(WoodGroup.Framing.beam_support);
        beam(generator, block, name, "_wood");
        block = group.get(WoodGroup.Framing.beam_post);
        supportPost(generator, idFromBlock(block), block, name);
        block = group.get(WoodGroup.Framing.beam_ceiling);
        supportCeiling(generator, idFromBlock(block), block, name);
        block = group.get(WoodGroup.Framing.beam_diagonal);
        supportDiagonal(generator, idFromBlock(block), block, name);
        //Item
        generator.registerSimpleItemModel(group.get(WoodGroup.FramingItem.beam_framing), Identifier.fromNamespaceAndPath(modid, root + "log/" + name + "_beam_wood"));

        block = group.get(WoodGroup.Framing.basic_arch);
        woodenArch(generator, block, idFromBlock(block), name, "planks");

        block = group.get(WoodGroup.Framing.basic_half_arch);
        woodenHalfArch(generator, name, block, "planks");

        block = group.get(WoodGroup.Framing.roof);
        FramingLibrary.roof(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Framing.sod_roof);
        sodRoof(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Framing.basic_arrow_slit);
        woodenArrowSlit(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Framing.refined_arrow_slit);
        woodenArrowSlit(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Framing.cross_arrow_slit);
        woodenArrowSlit(generator, block, idFromBlock(block), name, 2);

        block = group.get(WoodGroup.Framing.basic_ladder);
        ladder(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Framing.basic_window_cover);
        windowCover(generator, block, idFromBlock(block), name, "planks");

        block = group.get(WoodGroup.Framing.basic_door);
        door(generator, block, name, false);
        block = group.get(WoodGroup.Framing.basic_door_window);
        door(generator, block, name, true);
        block = group.get(WoodGroup.Framing.refined_door);
        enforcedDoor(generator, block, name, false);
        block = group.get(WoodGroup.Framing.refined_door_window);
        enforcedDoor(generator, block, name, true);

        block = group.get(WoodGroup.Framing.basic_trapdoor);
        trapDoor(generator, block, name);

        //Tudors
        block = group.get(WoodGroup.Framing.tudor_square);
        TudorLibrary.square(generator, block, name);
        generator.registerSimpleItemModel(group.get(WoodGroup.FramingItem.tudor), Identifier.fromNamespaceAndPath(modid, root + "framing/tudor/" + idFromBlock(block) + "_cross"));
        block = group.get(WoodGroup.Framing.tudor_horizontal);
        TudorLibrary.horizontal(generator, block, name);
        block = group.get(WoodGroup.Framing.tudor_vertical);
        TudorLibrary.vertical(generator, block, name);
    }

    private void furniture(WoodGroup group, String name) {
        Block block;

        block = group.get(WoodGroup.Furniture.basic_night_stand);
        counterChest(generator, block, idFromBlock(block), name, true, "night_stand_closed", "night_stand_open");

        block = group.get(WoodGroup.Furniture.basic_storage_table);
        storageTable(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Furniture.stool);
        stool(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_stool);
        stool(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.basic_chair);
        chair(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_chair);
        chair(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.throne);
        chair(generator, block, idFromBlock(block), name, 2);

        block = group.get(WoodGroup.Furniture.basic_bench);
        woodenBench(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_bench);
        woodenBench(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.basic_bench_with_backrest);
        woodenBenchBackrest(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_bench_with_backrest);
        woodenBenchBackrest(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.luxury_bench_with_backrest);
        woodenBenchBackrest(generator, block, idFromBlock(block), name, 2);

        block = group.get(WoodGroup.Furniture.basic_table);
        woodenTable(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_table);
        woodenTable(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.basic_mirror);
        mirror(generator, block, idFromBlock(block), name, 0);

        block = group.get(WoodGroup.Furniture.refined_mirror);
        mirror(generator, block, idFromBlock(block), name, 1);

        block = group.get(WoodGroup.Furniture.basic_table_chest);
        tableChest(generator, block, idFromBlock(block), name);

    }

    private void interior(WoodGroup group, String name) {
        Block block;
        block = group.get(WoodGroup.Interior.counter);
        counter(generator, block, idFromBlock(block), name);
        generator.registerSimpleItemModel(group.get(WoodGroup.InteriorItem.counter), Identifier.fromNamespaceAndPath(modid, root+"interior/"+idFromBlock(block)));
        block = group.get(WoodGroup.Interior.counter_shelves);
        counterShelves(generator, block, idFromBlock(block), name, "counter_shelves");
        block = group.get(WoodGroup.Interior.counter_chest);
        counterChest(generator, block, idFromBlock(block), name,false,"counter_chest_closed", "counter_chest_open");

        block = group.get(WoodGroup.Interior.cabinet);
        counterChest(generator, block, idFromBlock(block), name,false,"cabinets_closed", "cabinets_open");
        generator.registerSimpleItemModel(group.get(WoodGroup.InteriorItem.cabinet), Identifier.fromNamespaceAndPath(modid, root+"interior/"+idFromBlock(block)+"_closed"));
        block = group.get(WoodGroup.Interior.cabinet_shelf);
        counterShelves(generator, block, idFromBlock(block), name, "cabinets_shelves");

        block = group.get(WoodGroup.Interior.shelves);
        shelves(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Interior.crate);
        crate(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Interior.crate_lid);
        crateLid(generator, block, idFromBlock(block), name);

        block = group.get(WoodGroup.Interior.barrel);
        barrel(generator, block, name);
    }

    public static void stairs(BlockModelGenerators generator, String name, Block block, String type) {

        String loc0 = "planks/" + name + "_" + type;
        String loc1 = "planks/" + name + "_paintable_" + type;
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, "inner_stairs_" + loc0, "block/framing/paintable_stairs_inner", plank_path + name + "_" + type);
        applyTextureToModel(generator, "outer_stairs_" + loc0, "block/framing/paintable_stairs_outer", plank_path + name + "_" + type);
        applyTextureToModel(generator, "straight_stairs_" + loc0, "block/framing/paintable_stairs", plank_path + name + "_" + type);
        if (pines) {
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/framing/paintable_stairs_inner", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/framing/paintable_stairs_outer", plank_path + "pine_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/framing/paintable_stairs", plank_path + "pine_paintable_" + type);
        }else if(fruit){
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/framing/paintable_stairs_inner", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/framing/paintable_stairs_outer", plank_path + "fruit_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/framing/paintable_stairs", plank_path + "fruit_paintable_" + type);
        }else {
            applyTextureToModel(generator, "inner_stairs_" + loc1, "block/framing/paintable_stairs_inner", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "outer_stairs_" + loc1, "block/framing/paintable_stairs_outer", plank_path + name + "_paintable_" + type);
            applyTextureToModel(generator, "straight_stairs_" + loc1, "block/framing/paintable_stairs", plank_path + name + "_paintable_" + type);
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+"straight_stairs_" + loc0));

        PropertyDispatch.C4<MultiVariant, LinSeedPaintable, Direction, Half, StairsShape> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.STAIRS_SHAPE);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc = loc1;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }
            for(Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
                int dir = 90 + (direction.get2DDataValue()*90);
                map.select(paint, direction, Half.TOP, StairsShape.INNER_LEFT, modelOf("inner_stairs_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.TOP, StairsShape.INNER_RIGHT, modelOf("inner_stairs_" + finalLoc, true, dir+90, 180));
                map.select(paint, direction, Half.TOP, StairsShape.OUTER_LEFT, modelOf("outer_stairs_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.TOP, StairsShape.OUTER_RIGHT, modelOf("outer_stairs_" + finalLoc, true, dir+90, 180));
                map.select(paint, direction, Half.TOP, StairsShape.STRAIGHT, modelOf("straight_stairs_" + finalLoc, true, dir, 180));
                map.select(paint, direction, Half.BOTTOM, StairsShape.INNER_LEFT, modelOf("inner_stairs_" + finalLoc, true, dir-90, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.INNER_RIGHT, modelOf("inner_stairs_" + finalLoc, true, dir, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.OUTER_LEFT, modelOf("outer_stairs_" + finalLoc, true, dir-90, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.OUTER_RIGHT, modelOf("outer_stairs_" + finalLoc, true, dir, 0));
                map.select(paint, direction, Half.BOTTOM, StairsShape.STRAIGHT, modelOf("straight_stairs_" + finalLoc, true, dir, 0));
            }
        }
        CreateVariants(generator, block, map);
    }

    private static void windowCover(BlockModelGenerators generator, Block block, String id, String name, String type) {

        String modelPath = "framing/";
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

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0 + "_1"));

        PropertyDispatch.C3<MultiVariant, LinSeedPaintable, Direction, Integer> map = PropertyDispatch.initial(ModProperties.LINSEED_PAINT, BlockStateProperties.HORIZONTAL_FACING, ModProperties.VARIATIONS_4);

        for(LinSeedPaintable paintable : ModProperties.LINSEED_PAINT.getPossibleValues()){
            String finalLoc = paintable == LinSeedPaintable.NONE ? loc0 : loc1;

            for (int i : ModProperties.VARIATIONS_4.getPossibleValues()) {
                for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()){
                    int dir = direction.get2DDataValue()*90;
                    map.select(paintable, direction, i, modelOf(finalLoc + "_" + i, false, dir, 0));
                }
            }

        }

        CreateVariants(generator, block, map);

    }


    /**
     * Common wood states
     */
    public static void leaves(BlockModelGenerators generator, Block block, String id) {
        int vars = 4;
        String suffix = "";
        if (id.contains("larch") || id.contains("spruce")){
            suffix = "_pine";
        }
        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_" + i, root+"leaves" + suffix + "_" + i, id);
        }
        Variant[] map = new Variant[vars * 4];
        for (int i = 0; i < vars; i++) {
            int k = i * 4;
            map[k] = plainModel(Identifier.fromNamespaceAndPath(modid,    root+id + "_" + i));
            map[k + 1] = plainModel(Identifier.fromNamespaceAndPath(modid,root+id + "_" + i)).withYRot(R90);
            map[k + 2] = plainModel(Identifier.fromNamespaceAndPath(modid,root+id + "_" + i)).withYRot(R180);
            map[k + 3] = plainModel(Identifier.fromNamespaceAndPath(modid,root+id + "_" + i)).withYRot(R270);
        }
        generator.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, variants(map)));

        if (id.contains("jacaranda")) {
            generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_0"), new Constant(Colors.richLilac));
        } else {
            generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_0"), new Constant(Colors.pastelGreen));
        }
    }

    public static void fruitingLeaves(BlockModelGenerators generator, Block block, String id) {
        final String modelPath = "block/leaves";

        applyTextureToModel(generator, modelPath+id + "_0", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_1", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_2", modelPath, id);
        applyTextureToModel(generator, modelPath+id + "_3", modelPath, id);

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, modelPath+id + "_1"), new GrassColorSource());

        CreateVariants(generator, block, PropertyDispatch.initial(ModProperties.FRUITING)
                .select(0, modelOf(modelPath+id + "_0", false, 0, 0))
                .select(1, modelOf(modelPath+id + "_1", false, 0, 0))
                .select(2, modelOf(modelPath+id + "_2", false, 0, 0))
                .select(3, modelOf(modelPath+id + "_3", false, 0, 0))
        );
    }

    public static void branch(BlockModelGenerators generator, String id, Block block, String name) {

        applyTextureToModel(generator, id + "_diagonal","block/branch_diagonal", log_path + name +"_bark");
        applyTextureToModel(generator, id + "_face","block/branch_face", log_path + name +"_bark");
        applyTextureToModel(generator, id + "_corner","block/branch_corner", log_path + name +"_bark");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_diagonal"));

        CreateVariants(generator, block, PropertyDispatch.initial(ModProperties.DIRECTIONS, Verticality.VERTICALITY)
                .select(OrdinalDirection.N , Verticality.LEVEL, modelOf(id + "_face"    , false, 0  , 0))
                .select(OrdinalDirection.E , Verticality.LEVEL, modelOf(id + "_face"    , false, 90 , 0))
                .select(OrdinalDirection.S , Verticality.LEVEL, modelOf(id + "_face"    , false, 180, 0))
                .select(OrdinalDirection.W , Verticality.LEVEL, modelOf(id + "_face"    , false, 270, 0))
                .select(OrdinalDirection.N , Verticality.UP   , modelOf(id + "_diagonal", false, 270, 0))
                .select(OrdinalDirection.E , Verticality.UP   , modelOf(id + "_diagonal", false, 0  , 0))
                .select(OrdinalDirection.S , Verticality.UP   , modelOf(id + "_diagonal", false, 90 , 0))
                .select(OrdinalDirection.W , Verticality.UP   , modelOf(id + "_diagonal", false, 180, 0))
                .select(OrdinalDirection.N , Verticality.DOWN , modelOf(id + "_diagonal", false, 270, 180))
                .select(OrdinalDirection.W , Verticality.DOWN , modelOf(id + "_diagonal", false, 180, 180))
                .select(OrdinalDirection.S , Verticality.DOWN , modelOf(id + "_diagonal", false, 90 , 180))
                .select(OrdinalDirection.E , Verticality.DOWN , modelOf(id + "_diagonal", false, 0  , 180))
                .select(OrdinalDirection.NW, Verticality.LEVEL, modelOf(id + "_diagonal", false, 180, 270))
                .select(OrdinalDirection.NE, Verticality.LEVEL, modelOf(id + "_diagonal", false, 0  , 90))
                .select(OrdinalDirection.SE, Verticality.LEVEL, modelOf(id + "_diagonal", false, 0  , 270))
                .select(OrdinalDirection.SW, Verticality.LEVEL, modelOf(id + "_diagonal", false, 180, 90))
                .select(OrdinalDirection.NE, Verticality.UP   , modelOf(id + "_corner"  , false, 0  , 0))
                .select(OrdinalDirection.NW, Verticality.UP   , modelOf(id + "_corner"  , false, 270, 0))
                .select(OrdinalDirection.SE, Verticality.UP   , modelOf(id + "_corner"  , false, 90 , 0))
                .select(OrdinalDirection.SW, Verticality.UP   , modelOf(id + "_corner"  , false, 180, 0))
                .select(OrdinalDirection.NE, Verticality.DOWN , modelOf(id + "_corner"  , false, 270, 180))
                .select(OrdinalDirection.NW, Verticality.DOWN , modelOf(id + "_corner"  , false, 180, 180))
                .select(OrdinalDirection.SE, Verticality.DOWN , modelOf(id + "_corner"  , false, 0  , 180))
                .select(OrdinalDirection.SW, Verticality.DOWN , modelOf(id + "_corner"  , false, 90 , 180))
        );
    }

    public static void log(BlockModelGenerators generator, Block block, String name, String type) {

        String loc0 = "log/" + name + type;
        String loc1 = "log/" + name + "_paintable" + type;
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
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C2<MultiVariant, Direction.Axis, LinSeedPaintable> map = PropertyDispatch.initial(BlockStateProperties.AXIS, ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint.getSerializedName().equals("none")){
                finalLoc = loc0;
            }else {
                finalLoc = loc1;
            }
            map
                .select(Direction.Axis.Y, paint, modelOf(finalLoc, false, 0, 0))
                .select(Direction.Axis.Z, paint, modelOf(finalLoc, false, 0, 90))
                .select(Direction.Axis.X, paint, modelOf(finalLoc, false, 90, 90));
        }
        CreateVariants(generator, block, map);
    }

    public static void slab(BlockModelGenerators generator, Block block, String name, String type) {

        final String loc0 = "log/" + name + "_half" + type;
        final String loc1 = "log/" + name + "_paintable" + "_half" + type;
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
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C3<MultiVariant, Boolean, Direction, LinSeedPaintable> map = PropertyDispatch.initial(HalfLog.rotated, BlockStateProperties.FACING, ModProperties.LINSEED_PAINT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }else {
                finalLoc = loc1;
            }
            map
            .select(false, Direction.UP   , paint, modelOf(finalLoc, false, 0, 0))
            .select(false, Direction.DOWN , paint, modelOf(finalLoc, false, 0, 180))
            .select(false, Direction.NORTH, paint, modelOf(finalLoc, false, 0, 90))
            .select(false, Direction.SOUTH, paint, modelOf(finalLoc, false, 180, 90))
            .select(false, Direction.EAST , paint, modelOf(finalLoc, false, 90, 90))
            .select(false, Direction.WEST , paint, modelOf(finalLoc, false, 270, 90))
            .select(true , Direction.UP   , paint, modelOf(finalLoc, false, 90, 0))
            .select(true , Direction.DOWN , paint, modelOf(finalLoc, false, 90, 180))
            .select(true , Direction.NORTH, paint, modelOf(finalLoc+r, false, 0, 90))
            .select(true , Direction.SOUTH, paint, modelOf(finalLoc+r, false, 180, 90))
            .select(true , Direction.EAST , paint, modelOf(finalLoc+r, false, 90, 90))
            .select(true , Direction.WEST , paint, modelOf(finalLoc+r, false, 270, 90));

        }
        CreateVariants(generator, block, map);
    }

    public static void beam(BlockModelGenerators generator, Block block, String name, String type) {
        String loc0 = "log/" + name + "_beam" + type;
        String loc2 = "log/" + name + "_beam" + "_paintable" + type;
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
        //generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C3<MultiVariant, Direction.Axis, LinSeedPaintable, Quadrant> map = PropertyDispatch.initial(BlockStateProperties.AXIS, ModProperties.LINSEED_PAINT, ModProperties.QUADRANT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }else {
                finalLoc = loc2;
            }
            map
                    .select(Direction.Axis.X, paint, Quadrant.I , modelOf(finalLoc, true, 90  , 180))
                    .select(Direction.Axis.X, paint, Quadrant.II , modelOf(finalLoc, true, 270 , 180))
                    .select(Direction.Axis.X, paint, Quadrant.III , modelOf(finalLoc, true, 270 , 0))
                    .select(Direction.Axis.X, paint, Quadrant.IV , modelOf(finalLoc, true, 90  , 0))
                    .select(Direction.Axis.Z, paint, Quadrant.I , modelOf(finalLoc, true, 0  , 180))
                    .select(Direction.Axis.Z, paint, Quadrant.II , modelOf(finalLoc, true, 180 , 180))
                    .select(Direction.Axis.Z, paint, Quadrant.III , modelOf(finalLoc, true, 180 , 0))
                    .select(Direction.Axis.Z, paint, Quadrant.IV , modelOf(finalLoc, true, 0  , 0))
                    .select(Direction.Axis.Y, paint, Quadrant.I , modelOf(finalLoc, true, 0  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.II , modelOf(finalLoc, true, 270  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.III , modelOf(finalLoc, true, 180  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.IV , modelOf(finalLoc, true, 90  , 90));
        }
        CreateVariants(generator, block, map);
    }

    public static void beam(BlockModelGenerators generator, Block block, String name, String type, String parent) {

        String id = idFromBlock(block);
        String loc0 = "log/" + id;
        String loc2 = "log/" + id + "_paintable";
        boolean pines = name.contains("larch") || name.contains("spruce");
        boolean fruit = name.contains("apple") || name.contains("pear") || name.contains("plum");

        applyTextureToModel(generator, loc0, "block/trees/" + parent, log_path + name + type, log_path + name + "_wood_end");
        if (pines) {
            applyTextureToModel(generator, loc2, "block/trees" + parent, log_path + type, log_path + "pine_paintable_wood_end");
        } else if (fruit) {
            applyTextureToModel(generator, loc2, "block/trees" + parent, log_path + type, log_path + "fruit_paintable_wood_end");
        }else{
            applyTextureToModel(generator, loc2, "block/trees" + parent, log_path + name + type, log_path + name + "_paintable_wood_end");
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc0));

        PropertyDispatch.C3<MultiVariant, Direction.Axis, LinSeedPaintable, Quadrant> map = PropertyDispatch.initial(BlockStateProperties.AXIS, ModProperties.LINSEED_PAINT, ModProperties.QUADRANT);

        for (LinSeedPaintable paint : LinSeedPaintable.values()) {
            String finalLoc;
            if (paint == LinSeedPaintable.NONE){
                finalLoc = loc0;
            }else {
                finalLoc = loc2;
            }
            map
                    .select(Direction.Axis.X, paint, Quadrant.I , modelOf(finalLoc, true, 90  , 180))
                    .select(Direction.Axis.X, paint, Quadrant.II , modelOf(finalLoc, true, 270 , 180))
                    .select(Direction.Axis.X, paint, Quadrant.III , modelOf(finalLoc, true, 270 , 0))
                    .select(Direction.Axis.X, paint, Quadrant.IV , modelOf(finalLoc, true, 90  , 0))
                    .select(Direction.Axis.Z, paint, Quadrant.I , modelOf(finalLoc, true, 0  , 180))
                    .select(Direction.Axis.Z, paint, Quadrant.II , modelOf(finalLoc, true, 180 , 180))
                    .select(Direction.Axis.Z, paint, Quadrant.III , modelOf(finalLoc, true, 180 , 0))
                    .select(Direction.Axis.Z, paint, Quadrant.IV , modelOf(finalLoc, true, 0  , 0))
                    .select(Direction.Axis.Y, paint, Quadrant.I , modelOf(finalLoc, true, 0  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.II , modelOf(finalLoc, true, 270  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.III , modelOf(finalLoc, true, 180  , 90))
                    .select(Direction.Axis.Y, paint, Quadrant.IV , modelOf(finalLoc, true, 90  , 90));
        }
        CreateVariants(generator, block, map);
    }
}
