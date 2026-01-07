package com.wildsregrown.mixin;

import net.minecraft.block.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(Blocks.class)
public abstract class CreativeBlockMixin {
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 257,
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;"))
    private static AbstractBlock.Settings command(AbstractBlock.Settings settings) {
        return AbstractBlock.Settings.create().mapColor(MapColor.GRAY).requiresTool().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 470,
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;"))
    private static AbstractBlock.Settings r_command(AbstractBlock.Settings settings) {
        return AbstractBlock.Settings.create().mapColor(MapColor.GRAY).requiresTool().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 471,
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;"))
    private static AbstractBlock.Settings c_command(AbstractBlock.Settings settings) {
        return AbstractBlock.Settings.create().mapColor(MapColor.GRAY).requiresTool().strength(1F, 1F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 655,
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;"))
    private static AbstractBlock.Settings jigsaw(AbstractBlock.Settings settings) {
        return AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).requiresTool().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 654,
            target = "Lnet/minecraft/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/block/AbstractBlock$Settings;)Lnet/minecraft/block/Block;"))
    private static AbstractBlock.Settings structure(AbstractBlock.Settings settings) {
        return AbstractBlock.Settings.create().mapColor(MapColor.LIGHT_GRAY).requiresTool().strength(5F, 6F);
    }
}