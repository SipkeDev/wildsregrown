package com.wildsregrown.registries.world;

import com.sipke.api.features.botanic.BotanicShape;
import com.sipke.api.features.botanic.trees.config.BranchRule;
import com.sipke.api.features.botanic.trees.config.TreeConfig;
import com.sipke.api.features.botanic.trees.config.TrunkRule;
import com.sipke.features.trees.BranchDirection;
import com.sipke.math.MapType;
import com.sipke.registeries.RegistryContainer;
import com.sipke.registeries.WorldRegistries;
import com.wildsregrown.WildsRegrown;

import static com.wildsregrown.WildsRegrown.modid;

public class Trees {

    //hard
    public static final RegistryContainer<TreeConfig> oak = register("oak", "oak", BotanicShape.circle, 12, 34, 600, 0.2f, 12f, new TrunkRule(0.09f, 2, 18, 1, MapType.hermite), new BranchRule(0.785f, 4, 1, BranchDirection.degree0), new BranchRule(0.57f, 1, -3, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> dense_oak = register("dense_oak", "oak", BotanicShape.circle,  9, 28, 600, 0.215f, 9f, new TrunkRule(0.125f, 1, 8, 1, MapType.quintic), new BranchRule(0.57f, 1, 1, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> large_oak = register("large_oak", "oak", BotanicShape.circle,  13, 50, 600, 0.315f, 18f, new TrunkRule(0.125f, 3, 25, 3, MapType.inverseHermite), new BranchRule(0.57f, 3, 2, BranchDirection.degree0), new BranchRule(0.57f, 2, -2, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> beech = register("beech", "beech", BotanicShape.circle, 10, 24, 250, 0.215f, 5f, new TrunkRule(0.08f, 2, 16, 1, MapType.linear), new BranchRule(0.785f, 4, 2, BranchDirection.degree0), new BranchRule(0.57f, 2, 1, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> ash = register("ash","ash", BotanicShape.circle, 10, 14, 200, 0.215f, 8f, new TrunkRule(0.15f, 0, 14, 1, MapType.linear), new BranchRule(0.785f, 2, 2, BranchDirection.degree0), new BranchRule(0.57f, 1, -2, BranchDirection.degree45));

    //soft
    public static final RegistryContainer<TreeConfig> spruce = register("spruce", "spruce", BotanicShape.cone,  8, 42, 500, 0.25f, 12f,  new TrunkRule(0.215f, 1, 38, 1, MapType.linear), new BranchRule(0.57f, 4, -1, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> giant_spruce = register("giant_spruce", "spruce", BotanicShape.cone,  11, 60, 500, 0.49f, 12f,  new TrunkRule(0.215f, 2, 50, 2, MapType.linear), new BranchRule(0.57f, 4, -2, BranchDirection.degree45), new BranchRule(0.57f, 1, -1, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> larch = register("larch", "larch", BotanicShape.cone,  11, 34, 1000, 0.315f, 15f, new TrunkRule(0.16f, 1, 32, 1, MapType.linear), new BranchRule(0.57f, 3, -1, BranchDirection.degree0), new BranchRule(0.57f, 2, -1, BranchDirection.degreeRand));
    public static final RegistryContainer<TreeConfig> giant_larch = register("giant_larch", "larch", BotanicShape.cone,  13, 40, 1000, 0.49f, 15f, new TrunkRule(0.16f, 2, 38, 2, MapType.linear), new BranchRule(0.57f, 3, -2, BranchDirection.degree0), new BranchRule(0.57f, 2, -1, BranchDirection.degreeRand));
    public static final RegistryContainer<TreeConfig> silver_birch = register("silver_birch", "birch", BotanicShape.circle, 8, 14, 50, 0.35f,9f, new TrunkRule(0.785f, 2, 8, 0, MapType.linear), new BranchRule(0.785f, 4, 2, BranchDirection.degree0), new BranchRule(0.5f, 3, -2,BranchDirection.degree90));
    public static final RegistryContainer<TreeConfig> tall_birch = register("tall_birch", "birch", BotanicShape.cone,6, 25, 50, 0.55f,25f, new TrunkRule(0.215f, 2, 23, 1, MapType.linear), new BranchRule(0.57f, 2, 2,BranchDirection.degree90), new BranchRule(0.5f, 1, -1,BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> dwarf_birch = register("dwarf_birch", "birch", BotanicShape.circle,  3, 2, 50, 0.5f, 1f, new TrunkRule(0.75f, 0, 1, 0, MapType.linear), new BranchRule(0.85f, 0, 0,BranchDirection.degree45),new BranchRule(0.57f, 0, 0,BranchDirection.degreeRand));
    public static final RegistryContainer<TreeConfig> weeping_willow = register("weeping_willow", "willow", BotanicShape.circle, 9, 16, 35, 0.35f,16f, new TrunkRule(0.215f, 0, 12, 1, MapType.linear), new BranchRule(0.57f, 3, 2,BranchDirection.degree90), new BranchRule(0.5f, 2, -1,BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> bebb_willow = register("bebb_willow", "willow", BotanicShape.cone, 3, 9, 20, 0.245f,8f, new TrunkRule(0.215f, 0, 5, 1, MapType.linear), new BranchRule(0.57f, 2,1, BranchDirection.degree90), new BranchRule(0.5f, 0, -1,BranchDirection.degree45));

    //magic
    public static final RegistryContainer<TreeConfig> ancient_oak = register("ancient_oak", "ancient_oak", BotanicShape.circle, 12, 60, 5000, 0.215f, 44f, new TrunkRule(0.125f, 1, 48, 5, MapType.quintic), new BranchRule(0.57f, 2, 1, BranchDirection.degree0), new BranchRule(0.57f, 2, -2, BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> glowing_willow = register("glowing_willow", "glowing_willow", BotanicShape.circle, 12, 34, 2000, 0.25f,22f, new TrunkRule(0.215f, 0, 18, 2, MapType.linear), new BranchRule(0.57f, 3, 2,BranchDirection.degree90), new BranchRule(0.5f, 2, -1,BranchDirection.degree45));
    public static final RegistryContainer<TreeConfig> jacaranda = register("jacaranda", "jacaranda", BotanicShape.circle, 12, 50, 3000, 0.25f,32f, new TrunkRule(0.125f, 2, 38, 3, MapType.linear), new BranchRule(0.57f, 3, 2, BranchDirection.degree90), new BranchRule(0.5f, 2, -2,BranchDirection.degree45));

    //fruit
    public static final RegistryContainer<TreeConfig> apple = register("apple", "apple", BotanicShape.circle, 9, 16, 100, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));
    public static final RegistryContainer<TreeConfig> pear = register("pear", "pear", BotanicShape.circle, 6, 24, 350, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));
    public static final RegistryContainer<TreeConfig> plum = register("plum", "plum", BotanicShape.circle, 12, 12, 30, 0.125f,8f, new TrunkRule(0.25f, 1, 12, 1, MapType.linear), new BranchRule(0.785f, 2, 1,BranchDirection.degree45), new BranchRule(0.5f, 2, -1,BranchDirection.degreeRand));

    private static RegistryContainer<TreeConfig> register(String name, String family, BotanicShape shape, int radius, int height, int age, float branchHeight, float growFactor, TrunkRule trunkRule, BranchRule innerRule, BranchRule outerRule) {
        return WorldRegistries.TREES.register(new TreeConfig(modid, name, family, shape, radius, height, age, branchHeight, growFactor, trunkRule, innerRule, outerRule));
    }

    public static void init(){
        WildsRegrown.LOGGER.info("Registered Trees");
    }

}
