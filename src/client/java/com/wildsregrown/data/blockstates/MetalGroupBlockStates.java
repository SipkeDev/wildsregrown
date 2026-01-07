package com.wildsregrown.data.blockstates;

import com.wildsregrown.blocks.properties.FueledLight;
import com.wildsregrown.blocks.properties.ModProperties;
import com.wildsregrown.blocks.properties.TorchHolderState;
import com.wildsregrown.data.blockstates.libraries.BlockStateLibrary;
import com.wildsregrown.registries.groups.MetalGroup;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;
import static com.wildsregrown.registries.ModItemGroups.id;

public class MetalGroupBlockStates {

    private final BlockStateModelGenerator generator;

    public MetalGroupBlockStates(BlockStateModelGenerator generator){
        this.generator = generator;
    }

    public void build(MetalGroup group) {
        Block block = group.block;
        String texture = "block/metals/" + id(block);
        BlockStateLibrary.layered(generator, "block/layered/" + id(block), "block/metals/" + id(block), block, group.isOxidation());

        if (group.decoExist()) {

            block = group.get(MetalGroup.Deco.bars);
            MetalGroupBlockStates.axis(generator, id(block), block, texture, "decoration/metal_bars", group.isOxidation());

            block = group.get(MetalGroup.Deco.wall_anchor);
            MetalGroupBlockStates.horizontalFacing(generator, id(block), block, texture, "decoration/metal_wall_anchor", group.isOxidation());

            block = group.get(MetalGroup.Deco.bracket);
            MetalGroupBlockStates.horizontalFacing(generator, id(block), block, texture, "decoration/metal_bracket", group.isOxidation());

            block = group.get(MetalGroup.Deco.pan);
            MetalGroupBlockStates.horizontalFacing(generator, id(block), block, texture, "utensils/iron_pan", group.isOxidation());

        }
        if (group.lightsExist()) {
            block = group.get(MetalGroup.Lights.torch_holder);
            MetalGroupBlockStates.torchHolder(generator, id(block), block, texture);

            block = group.get(MetalGroup.Lights.brazier);
            MetalGroupBlockStates.brazier(generator, id(block), block, texture);

            block = group.get(MetalGroup.Lights.and_irons);
            MetalGroupBlockStates.lightFacing(generator, id(block), block, texture, "lights/andirons", "lights/andirons_filled", "lights/andirons_lit");

            block = group.get(MetalGroup.Lights.lantern);
            MetalGroupBlockStates.lantern(generator, id(block), block, texture, "lights/iron_lantern_off", "lights/iron_lantern_on");

        }
    }

    public static void horizontalFacing(BlockStateModelGenerator generator, String id, Block block, String texture, String parent, boolean oxidation) {

        if (oxidation) {
            applyTextureToModel(generator, id + "_0", "block/" + parent, texture + "_0");
            applyTextureToModel(generator, id + "_1", "block/" + parent, texture + "_1");
            applyTextureToModel(generator, id + "_2", "block/" + parent, texture + "_2");
            applyTextureToModel(generator, id + "_3", "block/" + parent, texture + "_3");

            generator.registerParentedItemModel(block, Identifier.of(modid, id + "_0"));
            CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.OXIDATION)
                    .register(Direction.NORTH, 0, modelOf(id + "_0", false, 0, 0))
                    .register(Direction.WEST, 0, modelOf(id + "_0", false, 270, 0))
                    .register(Direction.SOUTH, 0, modelOf(id + "_0", false, 180, 0))
                    .register(Direction.EAST, 0, modelOf(id + "_0", false, 90, 0))
                    .register(Direction.NORTH, 1, modelOf(id + "_1", false, 0, 0))
                    .register(Direction.WEST, 1, modelOf(id + "_1", false, 270, 0))
                    .register(Direction.SOUTH, 1, modelOf(id + "_1", false, 180, 0))
                    .register(Direction.EAST, 1, modelOf(id + "_1", false, 90, 0))
                    .register(Direction.NORTH, 2, modelOf(id + "_2", false, 0, 0))
                    .register(Direction.WEST, 2, modelOf(id + "_2", false, 270, 0))
                    .register(Direction.SOUTH, 2, modelOf(id + "_2", false, 180, 0))
                    .register(Direction.EAST, 2, modelOf(id + "_2", false, 90, 0))
                    .register(Direction.NORTH, 3, modelOf(id + "_3", false, 0, 0))
                    .register(Direction.WEST, 3, modelOf(id + "_3", false, 270, 0))
                    .register(Direction.SOUTH, 3, modelOf(id + "_3", false, 180, 0))
                    .register(Direction.EAST, 3, modelOf(id + "_3", false, 90, 0))
            );
        }else {
            applyTextureToModel(generator, id, "block/" + parent, texture);
            generator.registerParentedItemModel(block, Identifier.of(modid, id));
            CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING)
                    .register(Direction.NORTH, modelOf(id, false, 0, 0))
                    .register(Direction.WEST, modelOf(id, false, 270, 0))
                    .register(Direction.SOUTH, modelOf(id, false, 180, 0))
                    .register(Direction.EAST, modelOf(id, false, 90, 0))
            );
        }

    }

    public static void axis(BlockStateModelGenerator generator, String id, Block block, String texture, String parent, boolean oxidation) {

        if (oxidation) {
            applyTextureToModel(generator, id + "_0", "block/" + parent, texture + "_0");
            applyTextureToModel(generator, id + "_1", "block/" + parent, texture + "_1");
            applyTextureToModel(generator, id + "_2", "block/" + parent, texture + "_2");
            applyTextureToModel(generator, id + "_3", "block/" + parent, texture + "_3");

            generator.registerParentedItemModel(block, Identifier.of(modid, id + "_0"));
            CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_AXIS, ModProperties.OXIDATION)
                    .register(Direction.Axis.X, 0, modelOf(id + "_0", false, 90, 0))
                    .register(Direction.Axis.Z, 0, modelOf(id + "_0", false, 0, 0))
                    .register(Direction.Axis.X, 1, modelOf(id + "_1", false, 90, 0))
                    .register(Direction.Axis.Z, 1, modelOf(id + "_1", false, 0, 0))
                    .register(Direction.Axis.X, 2, modelOf(id + "_2", false, 90, 0))
                    .register(Direction.Axis.Z, 2, modelOf(id + "_2", false, 0, 0))
                    .register(Direction.Axis.X, 3, modelOf(id + "_3", false, 90, 0))
                    .register(Direction.Axis.Z, 3, modelOf(id + "_3", false, 0, 0))
            );
        }else {
            applyTextureToModel(generator, id, "block/" + parent, texture);
            generator.registerParentedItemModel(block, Identifier.of(modid, id));
            CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_AXIS)
                    .register(Direction.Axis.X, modelOf(id, false, 90,0))
                    .register(Direction.Axis.Z, modelOf(id, false, 0,0))
            );
        }

    }

    public static void light(BlockStateModelGenerator generator, String id, Block block, String texture, String parent, String filled, String lit) {

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

        generator.registerParentedItemModel(block, Identifier.of(modid, id+"_0_empty"));
        CreateVariants(generator, block, BlockStateVariantMap.models(ModProperties.FUELED_LIGHT, ModProperties.OXIDATION)
                .register(FueledLight.EMPTY,0, modelOf(id + "_0_empty"))
                .register(FueledLight.EMPTY,1, modelOf(id + "_1_empty"))
                .register(FueledLight.EMPTY,2, modelOf(id + "_2_empty"))
                .register(FueledLight.EMPTY,3, modelOf(id + "_3_empty"))

                .register(FueledLight.FILLED,0, modelOf(id + "_0_filled"))
                .register(FueledLight.FILLED,1, modelOf(id + "_1_filled"))
                .register(FueledLight.FILLED,2, modelOf(id + "_2_filled"))
                .register(FueledLight.FILLED,3, modelOf(id + "_3_filled"))

                .register(FueledLight.LIT,0, modelOf(id + "_0_lit"))
                .register(FueledLight.LIT,1, modelOf(id + "_1_lit"))
                .register(FueledLight.LIT,2, modelOf(id + "_2_lit"))
                .register(FueledLight.LIT,3, modelOf(id + "_3_lit"))
        );
    }

    //always from oxidated material
    public static void lightFacing(BlockStateModelGenerator generator, String id, Block block, String texture, String parent, String filled, String lit) {

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

        generator.registerParentedItemModel(block, Identifier.of(modid, id+"_0_empty"));
        CreateVariants(generator, block, BlockStateVariantMap.models(Properties.HORIZONTAL_FACING, ModProperties.FUELED_LIGHT, ModProperties.OXIDATION)
                .register(Direction.NORTH, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 0,0))
                .register(Direction.WEST, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 270,0))
                .register(Direction.SOUTH, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 180,0))
                .register(Direction.EAST, FueledLight.EMPTY, 0, modelOf(id + "_0_empty", false, 90,0))
                .register(Direction.NORTH, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 0,0))
                .register(Direction.WEST, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 270,0))
                .register(Direction.SOUTH, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 180,0))
                .register(Direction.EAST, FueledLight.EMPTY, 1, modelOf(id + "_1_empty", false, 90,0))
                .register(Direction.NORTH, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 0,0))
                .register(Direction.WEST, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 270,0))
                .register(Direction.SOUTH, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 180,0))
                .register(Direction.EAST, FueledLight.EMPTY, 2, modelOf(id + "_2_empty", false, 90,0))
                .register(Direction.NORTH, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 0,0))
                .register(Direction.WEST, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 270,0))
                .register(Direction.SOUTH, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 180,0))
                .register(Direction.EAST, FueledLight.EMPTY, 3, modelOf(id + "_3_empty", false, 90,0))

                .register(Direction.NORTH, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 0,0))
                .register(Direction.WEST, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 90,0))
                .register(Direction.SOUTH, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 180,0))
                .register(Direction.EAST, FueledLight.FILLED, 0, modelOf(id + "_0_filled", false, 270,0))
                .register(Direction.NORTH, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 0,0))
                .register(Direction.WEST, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 90,0))
                .register(Direction.SOUTH, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 180,0))
                .register(Direction.EAST, FueledLight.FILLED, 1, modelOf(id + "_1_filled", false, 270,0))
                .register(Direction.NORTH, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 0,0))
                .register(Direction.EAST, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 90,0))
                .register(Direction.SOUTH, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 180,0))
                .register(Direction.WEST, FueledLight.FILLED, 2, modelOf(id + "_2_filled", false, 270,0))
                .register(Direction.NORTH, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 0,0))
                .register(Direction.EAST, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 90,0))
                .register(Direction.SOUTH, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 180,0))
                .register(Direction.WEST, FueledLight.FILLED, 3, modelOf(id + "_3_filled", false, 270,0))

                .register(Direction.NORTH, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 0,0))
                .register(Direction.EAST, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 90,0))
                .register(Direction.SOUTH, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 180,0))
                .register(Direction.WEST, FueledLight.LIT, 0, modelOf(id + "_0_lit", false, 270,0))
                .register(Direction.NORTH, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 0,0))
                .register(Direction.EAST, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 90,0))
                .register(Direction.SOUTH, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 180,0))
                .register(Direction.WEST, FueledLight.LIT, 1, modelOf(id + "_1_lit", false, 270,0))
                .register(Direction.NORTH, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 0,0))
                .register(Direction.EAST, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 90,0))
                .register(Direction.SOUTH, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 180,0))
                .register(Direction.WEST, FueledLight.LIT, 2, modelOf(id + "_2_lit", false, 270,0))
                .register(Direction.NORTH, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 0,0))
                .register(Direction.EAST, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 90,0))
                .register(Direction.SOUTH, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 180,0))
                .register(Direction.WEST, FueledLight.LIT, 3, modelOf(id + "_3_lit", false, 270,0))
        );
    }

    private static void torchHolder(BlockStateModelGenerator generator, String id, Block block, String texture) {

        for (TorchHolderState state : ModProperties.TORCH_HOLDER_STATE.getValues()){
            for(int oxidated : ModProperties.OXIDATION.getValues()) {
                String loc = id + "_" + oxidated + "_" + state.asString();
                String parent = "block/" + (state == TorchHolderState.EMPTY ? "lights/torch_holder_empty" : "lights/torch_holder_filled");
                applyTextureToModel(generator, loc, parent, texture + "_" + oxidated);
            }
        }

        generator.registerParentedItemModel(block, Identifier.of(modid, id+"_0_empty"));

        BlockStateVariantMap.TripleProperty<WeightedVariant, TorchHolderState, Direction, Integer> map = BlockStateVariantMap.models(ModProperties.TORCH_HOLDER_STATE, Properties.HORIZONTAL_FACING, ModProperties.OXIDATION);

        for (TorchHolderState state : ModProperties.TORCH_HOLDER_STATE.getValues()) {
            for (int oxidated : ModProperties.OXIDATION.getValues()) {
                String loc = id + "_" + oxidated + "_" + state.asString();
                map
                        .register(state, Direction.NORTH, oxidated, modelOf(loc, true, 0, 0))
                        .register(state, Direction.SOUTH, oxidated, modelOf(loc, true, 180, 0))
                        .register(state, Direction.EAST, oxidated, modelOf(loc, true, 90, 0))
                        .register(state, Direction.WEST, oxidated, modelOf(loc, true, 270, 0));
            }
        }

        CreateVariants(generator, block, map);

    }

    private static void brazier(BlockStateModelGenerator generator, String id, Block block, String texture) {

        String parent = "block/lights/brazier";
        for(int oxidated : ModProperties.OXIDATION.getValues()) {
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

        generator.registerParentedItemModel(block, Identifier.of(modid, id+"_0_0"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Integer, Integer> map = BlockStateVariantMap.models(ModProperties.OXIDATION, ModProperties.FUEL_32);


        for (int oxidated : ModProperties.OXIDATION.getValues()) {
            String loc = id + "_" + oxidated;
            for(int fuel : ModProperties.FUEL_32.getValues()){
                if (fuel == 32){
                    map.register(oxidated, fuel, modelOf(loc + "_32", true, 0, 0));
                }else if (fuel == 31){
                    map.register(oxidated, fuel, modelOf(loc + "_31", true, 0, 0));
                }else if (fuel >= 29){
                    map.register(oxidated, fuel, modelOf(loc + "_29", true, 0, 0));
                }else if (fuel >= 27){
                    map.register(oxidated, fuel, modelOf(loc + "_27", true, 0, 0));
                }else if (fuel >= 25){
                    map.register(oxidated, fuel, modelOf(loc + "_25", true, 0, 0));
                }else if (fuel >= 20){
                    map.register(oxidated, fuel, modelOf(loc + "_20", true, 0, 0));
                }else if (fuel >= 15){
                    map.register(oxidated, fuel, modelOf(loc + "_15", true, 0, 0));
                }else if (fuel >= 10){
                    map.register(oxidated, fuel, modelOf(loc + "_10", true, 0, 0));
                }else if (fuel >= 5){
                    map.register(oxidated, fuel, modelOf(loc + "_5", true, 0, 0));
                }else if (fuel >= 1){
                    map.register(oxidated, fuel, modelOf(loc + "_1", true, 0, 0));
                }else if (fuel == 0){
                    map.register(oxidated, fuel, modelOf(loc + "_0", true, 0, 0));
                }
            }
        }

        CreateVariants(generator, block, map);

    }

    private static void lantern(BlockStateModelGenerator generator, String id, Block block, String texture, String parent0, String parent1) {

        for (int i : ModProperties.OXIDATION.getValues()) {
            applyTextureToModel(generator, id + "_unlit_" + i, "block/" + parent0, texture + "_" + i);
            applyTextureToModel(generator, id + "_lit_" + i, "block/" + parent1, texture + "_" + i);
        }
        generator.registerParentedItemModel(block, Identifier.of(modid, id+"_unlit_0"));

        BlockStateVariantMap.DoubleProperty<WeightedVariant, Integer, Boolean> map = BlockStateVariantMap.models(ModProperties.OXIDATION, Properties.LIT);

        for (int oxidation : ModProperties.OXIDATION.getValues()){
            map.register(oxidation, false, modelOf(id+"_unlit_"+oxidation));
            map.register(oxidation, true, modelOf(id+"_lit_"+oxidation));
        }

        CreateVariants(generator, block, map);

    }

}
