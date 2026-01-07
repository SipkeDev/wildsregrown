package com.wildsregrown.data.blockstates.libraries;

import com.sipke.api.features.Colors;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.flora.Flora;
import com.wildsregrown.blocks.properties.*;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.BlockStateVariantMap;
import net.minecraft.client.render.item.tint.ConstantTintSource;
import net.minecraft.client.render.item.tint.GrassTintSource;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;
import static com.wildsregrown.data.DataGeneratorProvider.idFromBlock;
import static com.wildsregrown.data.blockstates.libraries.BlockStateLibrary.*;

public class FloraLibrary {

    public static void rootedFloraOneYear(BlockStateModelGenerator generator, Block block, String id) {

        int vars = 4;

        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_small");
            applyTextureToModel(generator, id + "_2" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_medium");
            applyTextureToModel(generator, id + "_3" + "_" + i, "block/flora/root_cluster_" + i, "block/flora/" + id + "_large");
            applyTextureToModel(generator, id + "_3_buds" + "_" + i, "block/flora/root_cluster_" + i + "_flowering", "block/flora/" + id + "_large", "block/flora/" + id + "_buds");
            applyTextureToModel(generator, id + "_3_flowering" + "_" + i, "block/flora/root_cluster_" + i + "_flowering", "block/flora/" + id + "_large", "block/flora/" + id + "_flowering");
        }

        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_3_0"), new GrassTintSource());
        BlockStateVariantMap.DoubleProperty<WeightedVariant, FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_4);

        String suffix;
        for (FloraStage stage : FloraStage.values()) {
            for (int i : ModProperties.AGE_4.getValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING, FRUITS -> "_flowering";
                        case BUDS -> "_buds";
                        default -> "";
                    };
                }
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void rootedFloraTwoYear(BlockStateModelGenerator generator, Block block, String id) {

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
            applyTextureToModel(generator, id + "_5_buds" + "_"+i,"block/flora/root_cluster_"+i+"_flowering","block/flora/" + floraName + "_huge", "block/flora/" + floraName + "_buds");
            applyTextureToModel(generator, id + "_5_flowering" + "_"+i,"block/flora/root_cluster_"+i+"_flowering","block/flora/" + floraName + "_huge", "block/flora/" + floraName + "_flowering");
            applyTextureToModel(generator, id + "_5_fruits" + "_"+i,"block/flora/root_cluster_"+i+"_flowering","block/flora/" + floraName + "_huge", "block/flora/" + floraName + "_fruits");
        }

        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_3_0"), new GrassTintSource());
        BlockStateVariantMap.DoubleProperty<WeightedVariant,FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_6);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : ModProperties.AGE_6.getValues()){
                suffix = "";
                if (i == 6) {
                    suffix = switch (stage) {
                        case FRUITS -> "_fruits";
                        case FLOWERING -> "_flowering";
                        case BUDS -> "_buds";
                        default -> "";
                    };
                }
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void flower(BlockStateModelGenerator generator, Block block, String id) {

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
            applyTextureToModel(generator, id + "_3_buds" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_buds");
            applyTextureToModel(generator, id + "_3_flowering" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_flowers");
            applyTextureToModel(generator, id + "_3_fruits" + "_"+i,"block/flora/flower_"+i+"_flowering","block/flora/" + texture + "_large", "block/flora/" + texture + "_fruits");
        }

        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_3_0"), new GrassTintSource());
        BlockStateVariantMap.DoubleProperty<WeightedVariant,FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_4);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : ModProperties.AGE_4.getValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING -> "_flowering";
                        case FRUITS -> "_fruits";
                        case BUDS -> "_buds";
                        default -> "";
                    };
                }
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void tallGrass(BlockStateModelGenerator generator, Block block, String id) {
        int vars = 6;
        for (int i = 0; i < vars; i++) {
            applyTextureToModel(generator, id + "_0" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_sprout");
            applyTextureToModel(generator, id + "_1" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_small");
            applyTextureToModel(generator, id + "_2" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_medium");
            applyTextureToModel(generator, id + "_3" + "_"+i,"block/flora/tall_grass_" + i, "block/flora/" + id + "_large");
            applyTextureToModel(generator, id + "_3_buds" + "_"+i,"block/flora/tall_grass_"+i+"_flowering","block/flora/" + id + "_large", "block/flora/" + id + "_buds");
            applyTextureToModel(generator, id + "_3_flowering" + "_"+i,"block/flora/tall_grass_"+i+"_flowering","block/flora/" + id + "_large", "block/flora/" + id + "_flowering");
        }

        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_3_0"), new ConstantTintSource(Colors.pastelGreen));
        BlockStateVariantMap.DoubleProperty<WeightedVariant,FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_4);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for(int i : ModProperties.AGE_4.getValues()){
                suffix = "";
                if (i == 3) {
                    suffix = switch (stage) {
                        case FLOWERING, FRUITS -> "_flowering";
                        case BUDS -> "_buds";
                        default -> "";
                    };
                }
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    public static void shrub(BlockStateModelGenerator generator, Block block) {
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
        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_5_flowering_0"), new GrassTintSource());
        BlockStateVariantMap.DoubleProperty<WeightedVariant, FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_6);

        for (FloraStage stage: FloraStage.values()) {
            String suffix;
            if (stage == FloraStage.FLOWERING) {
                suffix = "_flowering";
            } else if (stage == FloraStage.BUDS || stage == FloraStage.FRUITS) {
                suffix = "_buds";
            } else {
                suffix = "";
            }

            for(int i : ModProperties.AGE_6.getValues()){
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }

    //Switches to larger uv in last age stages
    public static void tallShrub(BlockStateModelGenerator generator, Block block) {

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

        generator.registerTintedItemModel(block, Identifier.of(modid, id + "_5_flowering_0"), new GrassTintSource());
        BlockStateVariantMap.DoubleProperty<WeightedVariant, FloraStage, Integer> map = BlockStateVariantMap.models(ModProperties.FLORAL_STAGE, ModProperties.AGE_6);

        String suffix;
        for (FloraStage stage: FloraStage.values()) {
            for (int i : ModProperties.AGE_6.getValues()){
                suffix = switch (stage) {
                    case FLOWERING -> "_flowering";
                    case BUDS, FRUITS -> "_buds";
                    default -> "";
                };
                map.register(stage, i, modelOf(id + "_" + i + suffix, vars));
            }
        }
        CreateVariants(generator, block, map);
    }
}
