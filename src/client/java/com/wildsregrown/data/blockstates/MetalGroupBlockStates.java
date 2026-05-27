package com.wildsregrown.data.blockstates;

import com.wildsregrown.blocks.properties.*;
import com.wildsregrown.blocks.properties.fuel.FueledLight;
import com.wildsregrown.blocks.properties.metal.TorchHolderState;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.MetalGroup;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import wildsregrown.api.block.properties.WRGProperties;
import wildsregrown.api.block.properties.shapes.DoorState;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class MetalGroupBlockStates {

    private final BlockModelGenerators generator;

    public MetalGroupBlockStates(BlockModelGenerators generator){
        this.generator = generator;
    }

    public void build(MetalGroup group) {
        Block block = group.block;
        String texture = "block/metals/" + idFromBlock(block);
        BlockStateLibrary.layered(generator, "layered/" + idFromBlock(block), "block/metals/" + idFromBlock(block), block, group.isOxidation());

        if (group.decoExist()) {
            //Framing
            block = group.get(MetalGroup.Deco.bars);
            MetalGroupBlockStates.metalBars(generator, idFromBlock(block), block, texture);
            block = group.get(MetalGroup.Deco.wall_anchor);
            MetalGroupBlockStates.horizontalFacing(generator, idFromBlock(block), block, texture, "decoration/metal_wall_anchor", group.isOxidation());
            block = group.get(MetalGroup.Deco.bracket);
            MetalGroupBlockStates.horizontalFacing(generator, idFromBlock(block), block, texture, "decoration/metal_bracket", group.isOxidation());
            block = group.get(MetalGroup.Deco.door);
            door(generator, block, texture, false);
            block = group.get(MetalGroup.Deco.door_window);
            door(generator, block, texture, true);

            //Decoration
            block = group.get(MetalGroup.Deco.fireplace_tool_stand);
            MetalGroupBlockStates.fireplaceToolStand(generator, idFromBlock(block), block, texture);

            //Utensils
            block = group.get(MetalGroup.Deco.pan);
            MetalGroupBlockStates.horizontalFacing(generator, idFromBlock(block), block, texture, "utensils/iron_pan", group.isOxidation());

        }
        if (group.lightsExist()) {
            block = group.get(MetalGroup.Lights.torch_holder);
            MetalGroupBlockStates.torchHolder(generator, idFromBlock(block), block, texture);

            block = group.get(MetalGroup.Lights.brazier);
            MetalGroupBlockStates.brazier(generator, idFromBlock(block), block, texture);

            //block = group.get(MetalGroup.Lights.and_irons);
            //MetalGroupBlockStates.lightFacing(generator, idFromBlock(block), block, texture, "lights/andirons", "lights/andirons_filled", "lights/andirons_lit");

            block = group.get(MetalGroup.Lights.lantern);
            MetalGroupBlockStates.lantern(generator, idFromBlock(block), block, texture, "lights/iron_lantern_off", "lights/iron_lantern_on");

            block = group.get(MetalGroup.Lights.candle_lantern);
            MetalGroupBlockStates.candleLantern(generator, idFromBlock(block), block, texture);

            block = group.get(MetalGroup.Lights.oil_lantern);
            MetalGroupBlockStates.oilLantern(generator, idFromBlock(block), block, texture);

        }
    }

    public static void horizontalFacing(BlockModelGenerators generator, String id, Block block, String texture, String parent, boolean oxidation) {

        if (oxidation) {
            applyTextureToModel(generator, id + "_0", "block/" + parent, texture + "_0");
            applyTextureToModel(generator, id + "_1", "block/" + parent, texture + "_1");
            applyTextureToModel(generator, id + "_2", "block/" + parent, texture + "_2");
            applyTextureToModel(generator, id + "_3", "block/" + parent, texture + "_3");

            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_0"));
            CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, ModProperties.OXIDATION)
                    .select(Direction.NORTH, 0, modelOf(id + "_0", false, 0, 0))
                    .select(Direction.WEST, 0, modelOf(id + "_0", false, 270, 0))
                    .select(Direction.SOUTH, 0, modelOf(id + "_0", false, 180, 0))
                    .select(Direction.EAST, 0, modelOf(id + "_0", false, 90, 0))
                    .select(Direction.NORTH, 1, modelOf(id + "_1", false, 0, 0))
                    .select(Direction.WEST, 1, modelOf(id + "_1", false, 270, 0))
                    .select(Direction.SOUTH, 1, modelOf(id + "_1", false, 180, 0))
                    .select(Direction.EAST, 1, modelOf(id + "_1", false, 90, 0))
                    .select(Direction.NORTH, 2, modelOf(id + "_2", false, 0, 0))
                    .select(Direction.WEST, 2, modelOf(id + "_2", false, 270, 0))
                    .select(Direction.SOUTH, 2, modelOf(id + "_2", false, 180, 0))
                    .select(Direction.EAST, 2, modelOf(id + "_2", false, 90, 0))
                    .select(Direction.NORTH, 3, modelOf(id + "_3", false, 0, 0))
                    .select(Direction.WEST, 3, modelOf(id + "_3", false, 270, 0))
                    .select(Direction.SOUTH, 3, modelOf(id + "_3", false, 180, 0))
                    .select(Direction.EAST, 3, modelOf(id + "_3", false, 90, 0))
            );
        }else {
            applyTextureToModel(generator, id, parent, texture);
            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id));
            CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING)
                    .select(Direction.NORTH, modelOf(id, false, 0, 0))
                    .select(Direction.WEST, modelOf(id, false, 270, 0))
                    .select(Direction.SOUTH, modelOf(id, false, 180, 0))
                    .select(Direction.EAST, modelOf(id, false, 90, 0))
            );
        }

    }

    public static void axis(BlockModelGenerators generator, String id, Block block, String texture, String parent, boolean oxidation) {

        if (oxidation) {
            applyTextureToModel(generator, id + "_0", "block/" + parent, texture + "_0");
            applyTextureToModel(generator, id + "_1", "block/" + parent, texture + "_1");
            applyTextureToModel(generator, id + "_2", "block/" + parent, texture + "_2");
            applyTextureToModel(generator, id + "_3", "block/" + parent, texture + "_3");

            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_0"));
            CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS, ModProperties.OXIDATION)
                    .select(Direction.Axis.X, 0, modelOf(id + "_0", false, 90, 0))
                    .select(Direction.Axis.Z, 0, modelOf(id + "_0", false, 0, 0))
                    .select(Direction.Axis.X, 1, modelOf(id + "_1", false, 90, 0))
                    .select(Direction.Axis.Z, 1, modelOf(id + "_1", false, 0, 0))
                    .select(Direction.Axis.X, 2, modelOf(id + "_2", false, 90, 0))
                    .select(Direction.Axis.Z, 2, modelOf(id + "_2", false, 0, 0))
                    .select(Direction.Axis.X, 3, modelOf(id + "_3", false, 90, 0))
                    .select(Direction.Axis.Z, 3, modelOf(id + "_3", false, 0, 0))
            );
        }else {
            applyTextureToModel(generator, id, "block/" + parent, texture);
            generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, id));
            CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS)
                    .select(Direction.Axis.X, modelOf(id, false, 90,0))
                    .select(Direction.Axis.Z, modelOf(id, false, 0,0))
            );
        }

    }

    public static void light(BlockModelGenerators generator, String id, Block block, String texture, String parent, String filled, String lit) {

        applyTextureToModel(generator, id + "_0_empty","block/" + parent, texture + "_0");
        applyTextureToModel(generator, id + "_1_empty","block/" + parent, texture + "_1");
        applyTextureToModel(generator, id + "_2_empty","block/" + parent, texture + "_2");
        applyTextureToModel(generator, id + "_3_empty","block/" + parent, texture + "_3");

        applyTextureToModel(generator, id + "_0_filled","block/" + filled, texture + "_0");
        applyTextureToModel(generator, id + "_1_filled","block/" + filled, texture + "_1");
        applyTextureToModel(generator, id + "_2_filled","block/" + filled, texture + "_2");
        applyTextureToModel(generator, id + "_3_filled","block/" + filled, texture + "_3");

        applyTextureToModel(generator, id + "_0_lit","block/" + lit, texture + "_0");
        applyTextureToModel(generator, id + "_1_lit","block/" + lit, texture + "_1");
        applyTextureToModel(generator, id + "_2_lit","block/" + lit, texture + "_2");
        applyTextureToModel(generator, id + "_3_lit","block/" + lit, texture + "_3");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0_empty"));
        CreateVariants(generator, block, PropertyDispatch.initial(ModProperties.FUELED_LIGHT, ModProperties.OXIDATION)
                .select(FueledLight.EMPTY,0, modelOf(id + "_0_empty"))
                .select(FueledLight.EMPTY,1, modelOf(id + "_1_empty"))
                .select(FueledLight.EMPTY,2, modelOf(id + "_2_empty"))
                .select(FueledLight.EMPTY,3, modelOf(id + "_3_empty"))

                .select(FueledLight.FILLED,0, modelOf(id + "_0_filled"))
                .select(FueledLight.FILLED,1, modelOf(id + "_1_filled"))
                .select(FueledLight.FILLED,2, modelOf(id + "_2_filled"))
                .select(FueledLight.FILLED,3, modelOf(id + "_3_filled"))

                .select(FueledLight.LIT,0, modelOf(id + "_0_lit"))
                .select(FueledLight.LIT,1, modelOf(id + "_1_lit"))
                .select(FueledLight.LIT,2, modelOf(id + "_2_lit"))
                .select(FueledLight.LIT,3, modelOf(id + "_3_lit"))
        );
    }

    //always from oxidated material
    public static void lightFacing(BlockModelGenerators generator, String id, Block block, String texture, String parent, String filled, String lit) {

        applyTextureToModel(generator, id + "_0_empty", "block/" + parent, texture + "_0");
        applyTextureToModel(generator, id + "_1_empty", "block/" + parent, texture + "_1");
        applyTextureToModel(generator, id + "_2_empty", "block/" + parent, texture + "_2");
        applyTextureToModel(generator, id + "_3_empty", "block/" + parent, texture + "_3");

        applyTextureToModel(generator, id + "_0_filled", "block/" + filled, texture + "_0");
        applyTextureToModel(generator, id + "_1_filled", "block/" + filled, texture + "_1");
        applyTextureToModel(generator, id + "_2_filled", "block/" + filled, texture + "_2");
        applyTextureToModel(generator, id + "_3_filled", "block/" + filled, texture + "_3");

        applyTextureToModel(generator, id + "_0_lit", "block/" + lit, texture + "_0");
        applyTextureToModel(generator, id + "_1_lit", "block/" + lit, texture + "_1");
        applyTextureToModel(generator, id + "_2_lit", "block/" + lit, texture + "_2");
        applyTextureToModel(generator, id + "_3_lit", "block/" + lit, texture + "_3");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0_empty"));
        CreateVariants(generator, block, PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, ModProperties.FUELED_LIGHT, ModProperties.OXIDATION)
                .select(Direction.NORTH, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 0,0))
                .select(Direction.WEST, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 270,0))
                .select(Direction.SOUTH, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 180,0))
                .select(Direction.EAST, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 90,0))
                .select(Direction.NORTH, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 0,0))
                .select(Direction.WEST, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 270,0))
                .select(Direction.SOUTH, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 180,0))
                .select(Direction.EAST, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 90,0))
                .select(Direction.NORTH, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 0,0))
                .select(Direction.WEST, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 270,0))
                .select(Direction.SOUTH, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 180,0))
                .select(Direction.EAST, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 90,0))
                .select(Direction.NORTH, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 0,0))
                .select(Direction.WEST, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 270,0))
                .select(Direction.SOUTH, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 180,0))
                .select(Direction.EAST, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 90,0))

                .select(Direction.NORTH, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 0,0))
                .select(Direction.WEST, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 90,0))
                .select(Direction.SOUTH, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 180,0))
                .select(Direction.EAST, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 270,0))
                .select(Direction.NORTH, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 0,0))
                .select(Direction.WEST, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 90,0))
                .select(Direction.SOUTH, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 180,0))
                .select(Direction.EAST, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 270,0))
                .select(Direction.NORTH, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 0,0))
                .select(Direction.EAST, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 90,0))
                .select(Direction.SOUTH, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 180,0))
                .select(Direction.WEST, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 270,0))
                .select(Direction.NORTH, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 0,0))
                .select(Direction.EAST, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 90,0))
                .select(Direction.SOUTH, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 180,0))
                .select(Direction.WEST, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 270,0))

                .select(Direction.NORTH, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 0,0))
                .select(Direction.EAST, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 90,0))
                .select(Direction.SOUTH, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 180,0))
                .select(Direction.WEST, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 270,0))
                .select(Direction.NORTH, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 0,0))
                .select(Direction.EAST, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 90,0))
                .select(Direction.SOUTH, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 180,0))
                .select(Direction.WEST, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 270,0))
                .select(Direction.NORTH, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 0,0))
                .select(Direction.EAST, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 90,0))
                .select(Direction.SOUTH, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 180,0))
                .select(Direction.WEST, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 270,0))
                .select(Direction.NORTH, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 0,0))
                .select(Direction.EAST, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 90,0))
                .select(Direction.SOUTH, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 180,0))
                .select(Direction.WEST, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 270,0))
        );
    }

    private static void torchHolder(BlockModelGenerators generator, String id, Block block, String texture) {

        for (TorchHolderState state : ModProperties.TORCH_HOLDER_STATE.getPossibleValues()){
            for(int oxidated : ModProperties.OXIDATION.getPossibleValues()) {
                String loc = id + "_" + oxidated + "_" + state.getSerializedName();
                String parent = "block/" + (state == TorchHolderState.EMPTY ? "lights/torch_holder_empty" : "lights/torch_holder_filled");
                applyTextureToModel(generator, loc, parent, texture + "_" + oxidated);
            }
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0_empty"));

        PropertyDispatch.C3<MultiVariant, TorchHolderState, Direction, Integer> map = PropertyDispatch.initial(ModProperties.TORCH_HOLDER_STATE, BlockStateProperties.HORIZONTAL_FACING, ModProperties.OXIDATION);

        for (TorchHolderState state : ModProperties.TORCH_HOLDER_STATE.getPossibleValues()){
            for (int oxidated : ModProperties.OXIDATION.getPossibleValues()) {
                String loc = id + "_" + oxidated + "_" + state.getSerializedName();
                map
                        .select(state, Direction.NORTH, oxidated, modelOf(loc, true, 0, 0))
                        .select(state, Direction.SOUTH, oxidated, modelOf(loc, true, 180, 0))
                        .select(state, Direction.EAST, oxidated, modelOf(loc, true, 90, 0))
                        .select(state, Direction.WEST, oxidated, modelOf(loc, true, 270, 0));
            }
        }

        CreateVariants(generator, block, map);

    }

    private static void brazier(BlockModelGenerators generator, String id, Block block, String texture) {

        String parent = "block/lights/brazier";
        for(int oxidated : ModProperties.OXIDATION.getPossibleValues()) {
            String loc = id + "_" + oxidated;
            applyTextureToModel(generator, loc + "_0", parent + "_0", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_1", parent + "_1", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_5", parent + "_5", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_10", parent + "_10", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_15", parent + "_15", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_20", parent + "_20", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_25", parent + "_25", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_27", parent + "_27", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_29", parent + "_29", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_31", parent + "_31", texture + "_" + oxidated);
            applyTextureToModel(generator, loc + "_32", parent + "_32", texture + "_" + oxidated);
        }

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_0_0"));

        PropertyDispatch.C2<MultiVariant, Integer, Integer> map = PropertyDispatch.initial(ModProperties.OXIDATION, ModProperties.FUEL_32);


        for (int oxidated : ModProperties.OXIDATION.getPossibleValues()) {
            String loc = id + "_" + oxidated;
            for(int fuel : ModProperties.FUEL_32.getPossibleValues()){
                if (fuel == 32){
                    map.select(oxidated, fuel, modelOf(loc + "_32", true, 0, 0));
                }else if (fuel == 31){
                    map.select(oxidated, fuel, modelOf(loc + "_31", true, 0, 0));
                }else if (fuel >= 29){
                    map.select(oxidated, fuel, modelOf(loc + "_29", true, 0, 0));
                }else if (fuel >= 27){
                    map.select(oxidated, fuel, modelOf(loc + "_27", true, 0, 0));
                }else if (fuel >= 25){
                    map.select(oxidated, fuel, modelOf(loc + "_25", true, 0, 0));
                }else if (fuel >= 20){
                    map.select(oxidated, fuel, modelOf(loc + "_20", true, 0, 0));
                }else if (fuel >= 15){
                    map.select(oxidated, fuel, modelOf(loc + "_15", true, 0, 0));
                }else if (fuel >= 10){
                    map.select(oxidated, fuel, modelOf(loc + "_10", true, 0, 0));
                }else if (fuel >= 5){
                    map.select(oxidated, fuel, modelOf(loc + "_5", true, 0, 0));
                }else if (fuel >= 1){
                    map.select(oxidated, fuel, modelOf(loc + "_1", true, 0, 0));
                }else if (fuel == 0){
                    map.select(oxidated, fuel, modelOf(loc + "_0", true, 0, 0));
                }
            }
        }

        CreateVariants(generator, block, map);

    }

    private static void lantern(BlockModelGenerators generator, String id, Block block, String texture, String parent0, String parent1) {

        for (int i : ModProperties.OXIDATION.getPossibleValues()) {
            applyTextureToModel(generator, id + "_unlit_" + i, "block/" + parent0, texture + "_" + i);
            applyTextureToModel(generator, id + "_lit_" + i, "block/" + parent1, texture + "_" + i);
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id+"_unlit_0"));

        PropertyDispatch.C2<MultiVariant, Integer, Boolean> map = PropertyDispatch.initial(ModProperties.OXIDATION, BlockStateProperties.LIT);

        for (int oxidation : ModProperties.OXIDATION.getPossibleValues()){
            map.select(oxidation, false, modelOf(id+"_unlit_"+oxidation));
            map.select(oxidation, true, modelOf(id+"_lit_"+oxidation));
        }

        CreateVariants(generator, block, map);

    }

    public static void metalBars(BlockModelGenerators generator, String id, Block block, String texture) {

        for (int i = 0; i < 3; i++) {
            applyTextureToModel(generator, id + "_" + i + "_0", root + "decoration/metals/metal_bars_" + i, texture + "_0");
            applyTextureToModel(generator, id + "_" + i +  "_1", root + "decoration/metals/metal_bars_" + i, texture + "_1");
            applyTextureToModel(generator, id + "_" + i +  "_2", root + "decoration/metals/metal_bars_" + i, texture + "_2");
            applyTextureToModel(generator, id + "_" + i +  "_3", root + "decoration/metals/metal_bars_" + i, texture + "_3");
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_0_0"));

        PropertyDispatch.C3<MultiVariant, Direction.Axis, Integer, Integer> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_AXIS, ModProperties.OXIDATION, ModProperties.VARIATIONS_3);

        for (int i = 0; i < 3; i++) {
            map.select(Direction.Axis.X, 0, i+1,  modelOf(id + "_" + i + "_0", false, 90, 0))
                    .select(Direction.Axis.Z, 0,i+1, modelOf(id + "_" + i + "_0", false, 0, 0))
                    .select(Direction.Axis.X, 1,i+1, modelOf(id + "_" + i + "_1", false, 90, 0))
                    .select(Direction.Axis.Z, 1,i+1, modelOf(id + "_" + i + "_1", false, 0, 0))
                    .select(Direction.Axis.X, 2,i+1, modelOf(id + "_" + i + "_2", false, 90, 0))
                    .select(Direction.Axis.Z, 2,i+1, modelOf(id + "_" + i + "_2", false, 0, 0))
                    .select(Direction.Axis.X, 3,i+1, modelOf(id + "_" + i + "_3", false, 90, 0))
                    .select(Direction.Axis.Z, 3,i+1, modelOf(id + "_" + i + "_3", false, 0, 0));
        }

        CreateVariants(generator, block, map);

    }

    public static void candleLantern(BlockModelGenerators generator, String id, Block block, String texture) {

        applyTextureToModel(generator, id + "_0", root + "decoration/metals/metal_candle_lantern", texture + "_0");
        applyTextureToModel(generator, id + "_1", root + "decoration/metals/metal_candle_lantern", texture + "_1");
        applyTextureToModel(generator, id + "_2", root + "decoration/metals/metal_candle_lantern", texture + "_2");
        applyTextureToModel(generator, id + "_3", root + "decoration/metals/metal_candle_lantern", texture + "_3");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_0"));

        PropertyDispatch.C1<MultiVariant, Integer> map = PropertyDispatch.initial(ModProperties.OXIDATION);

        map.select(0, modelOf(id + "_0", false, 0, 0))
                    .select(1, modelOf(id + "_1", false, 0, 0))
                    .select(2, modelOf(id + "_2", false, 0, 0))
                    .select(3, modelOf(id + "_3", false, 0, 0));
        CreateVariants(generator, block, map);

    }

    public static void fireplaceToolStand(BlockModelGenerators generator, String id, Block block, String texture) {

        for (int i = 0; i < 4; i++) {
            applyTextureToModel(generator, id + "_" + i, root + "decoration/metals/metal_fireplace_tool_stand", texture + "_" + i);
        }
        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_0"));
        CreateVariants(generator, block, PropertyDispatch.initial(ModProperties.OXIDATION).select(0, modelOf(id + "_0", false, 0, 0))
                .select(1, modelOf(id + "_1", false, 0, 0))
                .select(2, modelOf(id + "_2", false, 0, 0))
                .select(3, modelOf(id + "_3", false, 0, 0)));

    }

    public static void oilLantern(BlockModelGenerators generator, String id, Block block, String texture) {

        applyTextureToModel(generator, id + "_0", root + "decoration/metals/metal_oil_lantern", texture + "_0");
        applyTextureToModel(generator, id + "_1", root + "decoration/metals/metal_oil_lantern", texture + "_1");
        applyTextureToModel(generator, id + "_2", root + "decoration/metals/metal_oil_lantern", texture + "_2");
        applyTextureToModel(generator, id + "_3", root + "decoration/metals/metal_oil_lantern", texture + "_3");

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root + id + "_0"));

        PropertyDispatch.C1<MultiVariant, Integer> map = PropertyDispatch.initial(ModProperties.OXIDATION);

        map.select(0, modelOf(id + "_0", false, 0, 0))
                .select(1, modelOf(id + "_1", false, 0, 0))
                .select(2, modelOf(id + "_2", false, 0, 0))
                .select(3, modelOf(id + "_3", false, 0, 0));
        CreateVariants(generator, block, map);

    }

    public static void door(BlockModelGenerators generator, Block block, String texture, boolean window) {

        String modelPath = "framing/";
        String id = idFromBlock(block);
        String loc = modelPath + id;

        String open = "_open";
        String closed = "_closed";
        String top = "_top";
        String bottom = "_bottom";
        String left = "_left";
        String right = "_right";
        String w = window ? "_window" : "";

        int i = 0;
        applyTextureToModel(generator, loc + bottom + left + closed, root+modelPath + "door/metal_door" + bottom + left + closed, texture + "_" + i);
        applyTextureToModel(generator, loc + bottom + left + open, root+modelPath + "door/metal_door" + bottom + left + open, texture + "_" + i);
        applyTextureToModel(generator, loc + top + left + closed, root+modelPath + "door/metal_door" + w + top + left + closed, texture + "_" + i);
        applyTextureToModel(generator, loc + top + left + open, root+modelPath + "door/metal_door" + w + top + left + open, texture + "_" + i);
        applyTextureToModel(generator, loc + bottom + right + closed, root+modelPath + "door/metal_door" + bottom + right + closed, texture + "_" + i);
        applyTextureToModel(generator, loc + bottom + right + open, root+modelPath + "door/metal_door" + bottom + right + open, texture + "_" + i);
        applyTextureToModel(generator, loc + top + right + closed, root+modelPath + "door/metal_door" + w + top + right + closed, texture + "_" + i);
        applyTextureToModel(generator, loc + top + right + open, root+modelPath + "door/metal_door" + w + top + right + open, texture + "_" + i);

        generator.registerSimpleItemModel(block, Identifier.fromNamespaceAndPath(modid, root+loc + top + left + closed));

        PropertyDispatch.C3<MultiVariant, Direction, Boolean, DoorState> map = PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN, WRGProperties.DOOR);

        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {
            int dir = direction.get2DDataValue() * 90;
            map.select(direction, true, DoorState.left_bottom, modelOf(loc + bottom + left + open, false, dir, 0));
            map.select(direction, false, DoorState.left_bottom, modelOf(loc + bottom + left + closed, false, dir, 0));
            map.select(direction, true, DoorState.left_top, modelOf(loc + top + left + open, false, dir, 0));
            map.select(direction, false, DoorState.left_top, modelOf(loc + top + left + closed, false, dir, 0));
            map.select(direction, true, DoorState.right_bottom, modelOf(loc + bottom + right + open, false, dir, 0));
            map.select(direction, false, DoorState.right_bottom, modelOf(loc + bottom + right + closed, false, dir, 0));
            map.select(direction, true, DoorState.right_top, modelOf(loc + top + right + open, false, dir, 0));
            map.select(direction, false, DoorState.right_top, modelOf(loc + top + right + closed, false, dir, 0));
        }

        CreateVariants(generator, block, map);

    }


}
