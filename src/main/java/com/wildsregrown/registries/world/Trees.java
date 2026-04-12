package com.wildsregrown.registries.world;

import com.sipke.api.features.trees.config.BranchRule;
import com.sipke.api.features.trees.config.TreeConfig;
import com.sipke.api.features.trees.config.TrunkRule;
import com.sipke.api.features.trees.type.TreeShape;
import com.sipke.features.trees.BranchDirection;
import com.sipke.math.MapType;
import com.sipke.registeries.WorldRegistries;
import com.sipke.registeries.core.RegistryObject;
import com.wildsregrown.WildsRegrown;

import java.util.ArrayList;
import java.util.Objects;

public class Trees {

    //hard
    public static final RegistryObject<TreeConfig> oak = register("oak", "oak", TreeShape.circle, 12, 34, 600, 0.1f, 12f, new TrunkRule(0.09f, 2, 18, 1, MapType.hermite), new BranchRule(0.785f, 4, 1, BranchDirection.degree0), new BranchRule(0.57f, 1, -3, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> dense_oak = register("dense_oak", "oak", TreeShape.circle,  9, 28, 600, 0.215f, 9f, new TrunkRule(0.125f, 1, 8, 1, MapType.quintic), new BranchRule(0.57f, 1, 1, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> large_oak = register("large_oak", "oak", TreeShape.circle,  13, 50, 600, 0.125f, 18f, new TrunkRule(0.125f, 3, 25, 3, MapType.inverseHermite), new BranchRule(0.57f, 3, 2, BranchDirection.degree0), new BranchRule(0.57f, 2, -2, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> beech = register("beech", "beech", TreeShape.circle, 10, 24, 250, 0.125f, 5f, new TrunkRule(0.08f, 2, 16, 1, MapType.linear), new BranchRule(0.785f, 4, 2, BranchDirection.degree0), new BranchRule(0.57f, 2, 1, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> ash = register("ash","ash", TreeShape.circle, 10, 14, 200, 0.125f, 8f, new TrunkRule(0.15f, 0, 14, 1, MapType.linear), new BranchRule(0.785f, 2, 2, BranchDirection.degree0), new BranchRule(0.57f, 1, -2, BranchDirection.degree45));

    //soft
    public static final RegistryObject<TreeConfig> spruce = register("spruce", "spruce", TreeShape.cone,  8, 42, 500, 0.15f, 12f,  new TrunkRule(0.215f, 1, 38, 1, MapType.linear), new BranchRule(0.57f, 4, -1, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> giant_spruce = register("giant_spruce", "spruce", TreeShape.cone,  11, 60, 500, 0.34f, 12f,  new TrunkRule(0.215f, 2, 50, 2, MapType.linear), new BranchRule(0.57f, 4, -2, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> larch = register("larch", "larch", TreeShape.cone,  11, 34, 1000, 0.215f, 15f, new TrunkRule(0.16f, 1, 32, 1, MapType.linear), new BranchRule(0.57f, 3, -1, BranchDirection.degree0), new BranchRule(0.57f, 2, -1, BranchDirection.degreeRand));
    public static final RegistryObject<TreeConfig> giant_larch = register("giant_larch", "larch", TreeShape.cone,  13, 40, 1000, 0.34f, 15f, new TrunkRule(0.16f, 2, 38, 2, MapType.linear), new BranchRule(0.57f, 3, -2, BranchDirection.degree0), new BranchRule(0.57f, 2, -1, BranchDirection.degreeRand));
    public static final RegistryObject<TreeConfig> silver_birch = register("silver_birch", "birch", TreeShape.circle, 8, 14, 50, 0.25f,9f, new TrunkRule(0.785f, 2, 8, 0, MapType.linear), new BranchRule(0.785f, 4, 2, BranchDirection.degree0), new BranchRule(0.5f, 3, -2,BranchDirection.degree90));
    public static final RegistryObject<TreeConfig> tall_birch = register("tall_birch", "birch", TreeShape.cone,6, 25, 50, 0.45f,25f, new TrunkRule(0.215f, 2, 23, 1, MapType.linear), new BranchRule(0.57f, 2, 2,BranchDirection.degree90), new BranchRule(0.5f, 1, -1,BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> dwarf_birch = register("dwarf_birch", "birch", TreeShape.circle,  3, 2, 50, 0f, 1f, new TrunkRule(0.75f, 0, 1, 0, MapType.linear), new BranchRule(0.85f, 0, 0,BranchDirection.degree45),new BranchRule(0.57f, 0, 0,BranchDirection.degreeRand));
    public static final RegistryObject<TreeConfig> weeping_willow = register("weeping_willow", "willow", TreeShape.circle, 9, 16, 35, 0.25f,16f, new TrunkRule(0.215f, 0, 12, 1, MapType.linear), new BranchRule(0.57f, 3, 2,BranchDirection.degree90), new BranchRule(0.5f, 2, -1,BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> bebb_willow = register("bebb_willow", "willow", TreeShape.cone, 3, 9, 20, 0.125f,8f, new TrunkRule(0.215f, 0, 5, 1, MapType.linear), new BranchRule(0.57f, 2,1, BranchDirection.degree90), new BranchRule(0.5f, 0, -1,BranchDirection.degree45));

    //magic
    public static final RegistryObject<TreeConfig> ancient_oak = register("ancient_oak", "ancient_oak", TreeShape.circle, 12, 60, 5000, 0.215f, 44f, new TrunkRule(0.125f, 1, 48, 5, MapType.quintic), new BranchRule(0.57f, 2, 1, BranchDirection.degree0), new BranchRule(0.57f, 2, -2, BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> glowing_willow = register("glowing_willow", "glowing_willow", TreeShape.circle, 12, 34, 2000, 0.25f,22f, new TrunkRule(0.215f, 0, 18, 2, MapType.linear), new BranchRule(0.57f, 3, 2,BranchDirection.degree90), new BranchRule(0.5f, 2, -1,BranchDirection.degree45));
    public static final RegistryObject<TreeConfig> jacaranda = register("jacaranda", "jacaranda", TreeShape.circle, 12, 50, 3000, 0.25f,32f, new TrunkRule(0.125f, 2, 38, 3, MapType.linear), new BranchRule(0.57f, 3, 2, BranchDirection.degree90), new BranchRule(0.5f, 2, -2,BranchDirection.degree45));

    //fruit
    public static final RegistryObject<TreeConfig> apple = register("apple", "apple", TreeShape.circle, 9, 16, 100, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));
    public static final RegistryObject<TreeConfig> pear = register("pear", "pear", TreeShape.circle, 6, 24, 350, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));
    public static final RegistryObject<TreeConfig> plum = register("plum", "plum", TreeShape.circle, 12, 12, 30, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));

    private static RegistryObject<TreeConfig> register(String name, String family, TreeShape shape, int radius, int height, int age, float branchHeight, float growFactor, TrunkRule trunkRule, BranchRule innerRule, BranchRule outerRule) {
        return WorldRegistries.TREES.register(new TreeConfig(name, family, shape, radius, height, age, branchHeight, growFactor, trunkRule, innerRule, outerRule));
    }

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Trees");
        WorldRegistries.TREES.bootstrap();
    }

}
