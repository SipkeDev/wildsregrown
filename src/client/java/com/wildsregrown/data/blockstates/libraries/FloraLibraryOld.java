package com.wildsregrown.data.blockstates.libraries;

import com.sipke.api.features.Colors;
import com.wildsregrown.WildsRegrown;
import net.minecraft.client.color.item.Constant;
import net.minecraft.client.color.item.GrassColorSource;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import wildsregrown.api.block.properties.FloraStage;
import wildsregrown.api.block.properties.WRGProperties;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class FloraLibraryOld {

    public static void rootedFloraOneYear(BlockModelGenerators generator, Block block, String id) {

        int vars = 4;

        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_small");
            applyTextureToModel(generator, id + "_2" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_medium");
            applyTextureToModel(generator, id + "_3" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_large");
            applyTextureToModel(generator, id + "_3_flowering" + "_" + i, "block/flora/root_cluster_" + i + "_flowering", "block/flora/" + id + "_large", "block/flora/" + id + "_flowering");
            applyTextureToModel(generator, id + "_3_fruits" + "_" + i, "block/flora/root_cluster_" + i + "_flowering", "block/flora/" + id + "_large", "block/flora/" + id + "_buds");
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_3_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant, FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_4);

        String suffix;
        for (FloraStage stage : FloraStage.values()) {
            for (int i : WRGProperties.AGE_4.getPossibleValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING -> "_flowering";
                        case FRUITS -> "_fruits";
                        default -> "";
                    };
                }
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void rootedFloraTwoYear(BlockModelGenerators generator, Block block, String id) {

        String floraName = id;
        if (floraName.contains("chives")){
            floraName = "chives";
        }

        int vars = 5;
        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_small");
            applyTextureToModel(generator, id + "_2" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_medium");
            applyTextureToModel(generator, id + "_3" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_large");
            applyTextureToModel(generator, id + "_4" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_big");
            applyTextureToModel(generator, id + "_5" + "_"+i,"block/flora/root_cluster_" + i, "block/flora/" + floraName + "_huge");
            applyTextureToModel(generator, id + "_5_flowering" + "_"+i,"block/flora/root_cluster_"+i+"_flowering","block/flora/" + floraName + "_huge", "block/flora/" + floraName + "_flowering");
            applyTextureToModel(generator, id + "_5_fruits" + "_"+i,"block/flora/root_cluster_"+i+"_flowering","block/flora/" + floraName + "_huge", "block/flora/" + floraName + "_fruits");
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_3_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant,FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_6);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : WRGProperties.AGE_6.getPossibleValues()){
                suffix = "";
                if (i == 6) {
                    suffix = switch (stage) {
                        case FRUITS -> "_fruits";
                        case FLOWERING -> "_flowering";
                        default -> "";
                    };
                }
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void tintedFlower(BlockModelGenerators generator, Block block, String id) {

        int vars = 5;
        //Filter textures
        String texture = id;
        if (id.contains("artiplex")){texture = "artiplex";}
        else if (id.contains("lily")){texture = "lily";}
        else if (id.contains("sea_holly")){texture = "sea_holly";}
        else if (id.contains("blue_bottle")){texture = "blue_bottle";}
        else if (id.contains("orchid")){texture = "orchid";}
        else if (id.contains("tulip")){texture = "tulip";}
        else if (id.contains("campanula")){texture = "campanula";}
        else if (id.contains("foxglove")){texture = "foxglove";}

        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_small");
            applyTextureToModel(generator, id + "_2" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_medium");
            applyTextureToModel(generator, id + "_3" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_large");
            applyTextureToModel(generator, id + "_3_flowering" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_flowers");
            applyTextureToModel(generator, id + "_3_fruits" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_fruits");
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_3_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant,FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_4);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : WRGProperties.AGE_4.getPossibleValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING -> "_flowering";
                        case FRUITS -> "_fruits";
                        default -> "";
                    };
                }
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void flower(BlockModelGenerators generator, Block block, String id) {

        int vars = 5;
        //Filter textures
        String texture = id;
        if (id.contains("artiplex")){texture = "artiplex";}
        else if (id.contains("lily")){texture = "lily";}
        else if (id.contains("sea_holly")){texture = "sea_holly";}
        else if (id.contains("blue_bottle")){texture = "blue_bottle";}
        else if (id.contains("orchid")){texture = "orchid";}
        else if (id.contains("tulip")){texture = "tulip";}
        else if (id.contains("campanula")){texture = "campanula";}
        else if (id.contains("foxglove")){texture = "foxglove";}

        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_small");
            applyTextureToModel(generator, id + "_2" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_medium");
            applyTextureToModel(generator, id + "_3" + "_"+i,"block/flora/flower_" + i, "block/flora/" + texture + "_large");
            applyTextureToModel(generator, id + "_3_flowering" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_flowers");
            applyTextureToModel(generator, id + "_3_fruits" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_fruits");
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_3_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant,FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_4);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : WRGProperties.AGE_4.getPossibleValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING -> "_flowering";
                        case FRUITS -> "_fruits";
                        default -> "";
                    };
                }
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void tallGrass(BlockModelGenerators generator, Block block, String id) {
        int vars = 6;
        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_small");
            applyTextureToModel(generator, id + "_2" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_medium");
            applyTextureToModel(generator, id + "_3" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_large");
            applyTextureToModel(generator, id + "_3_buds" + "_"+i,"block/flora/tall_grass_"+i+"_flowering","block/flora/" + id + "_large", "block/flora/" + id + "_buds");
            applyTextureToModel(generator, id + "_3_flowering" + "_"+i,"block/flora/tall_grass_"+i+"_flowering","block/flora/" + id + "_large", "block/flora/" + id + "_flowering");
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_3_0"), new Constant(Colors.pastelGreen));
        PropertyDispatch.C2<MultiVariant,FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_4);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for(int i : WRGProperties.AGE_4.getPossibleValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING -> "_flowering";
                        case FRUITS -> "_buds";
                        default -> "";
                    };
                }
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void shrub(BlockModelGenerators generator, Block block) {
        String id = idFromBlock(block);
        //compress possible textures
        String texture = id.contains("heather") ? "heather" : id;
        int vars = 4;
        String[] names = {"sprout", "small", "medium", "large", "big", "huge"};
        for (int i = 0; i <= vars; i++) {
            for (int j = 0; j < names.length; j++) {
                applyTextureToModel(generator, id + "_" + j + "_" + i, "block/flora/shrub_" + i, "block/flora/" + texture + "_" + names[j], null, "block/flora/" + texture + "_" + names[j] + "_stem");
                applyTextureToModel(generator, id + "_" + j + "_buds" + "_" + i, "block/flora/shrub_"+i+"_flowering", "block/flora/" + texture + "_" + names[j], "block/flora/" + texture + "_" + names[j] + "_buds", "block/flora/" + texture + "_" + names[j] + "_stem");
                applyTextureToModel(generator, id + "_" + j + "_flowering" + "_" + i, "block/flora/shrub_"+i+"_flowering", "block/flora/" + texture + "_" + names[j], "block/flora/" + texture + "_" + names[j] + "_flowers", "block/flora/" + texture + "_" + names[j] + "_stem");
            }
        }
        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_5_flowering_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant, FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_6);

        for (FloraStage stage: FloraStage.values()) {
            String suffix;
            if (stage == FloraStage.FLOWERING) {
                suffix = "_flowering";
            } else if (stage == FloraStage.FRUITS) {
                suffix = "_buds";
            } else {
                suffix = "";
            }

            for(int i : WRGProperties.AGE_6.getPossibleValues()){
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    //Switches to larger uv in last age stages
    public static void tallShrub(BlockModelGenerators generator, Block block) {

        String id = idFromBlock(block);
        WildsRegrown.LOGGER.info(id);
        //compress possible textures
        String texture = id;
        if(texture.contains("spirea")){texture = "spirea";}
        if(texture.contains("hydrangea")){texture = "hydrangea";}
        if(texture.contains("sagebush")){texture = "sagebush";}

        int vars = 4;
        String[] names = {"sprout", "small", "medium", "large", "big", "huge"};
        for (int i = 0; i <= vars; i++) {
            for (int age = 0; age < names.length; age++) {
                applyTextureToModel(generator, id + "_" + age + "_" + i, "block/flora/tall_shrub_" + i, "block/flora/" + texture + "_" + names[age], null, "block/flora/" + texture + "_" + names[age] + "_stem");
                applyTextureToModel(generator, id + "_" + age + "_buds" + "_" + i, "block/flora/tall_shrub_" + i + "_flowering", "block/flora/" + texture + "_" + names[age], "block/flora/" + texture + "_" + names[age] + "_buds", "block/flora/" + texture + "_" + names[age] + "_stem");
                applyTextureToModel(generator, id + "_" + age + "_flowering" + "_" + i, "block/flora/tall_shrub_" + i + "_flowering", "block/flora/" + texture + "_" + names[age], "block/flora/" + texture + "_" + names[age] + "_flowers", "block/flora/" + texture + "_" + names[age] + "_stem");
            }
        }

        generator.registerSimpleTintedItemModel(block, Identifier.fromNamespaceAndPath(modid, root+id + "_5_flowering_0"), new GrassColorSource());
        PropertyDispatch.C2<MultiVariant, FloraStage, Integer> map = PropertyDispatch.initial(WRGProperties.FLORA_STAGE, WRGProperties.AGE_6);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : WRGProperties.AGE_6.getPossibleValues()){
                suffix = switch (stage) {
                    case FLOWERING -> "_flowering";
                    case FRUITS -> "_buds";
                    default -> "";
                };
                map.select(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }
}
