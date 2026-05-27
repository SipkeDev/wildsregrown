package com.wildsregrown.mixin.block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;


@Mixin(Blocks.class)
public abstract class CreativeBlockMixin {
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 257,
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"))
    private static BlockBehaviour.Properties command(BlockBehaviour.Properties settings) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 470,
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"))
    private static BlockBehaviour.Properties r_command(BlockBehaviour.Properties settings) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 471,
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"))
    private static BlockBehaviour.Properties c_command(BlockBehaviour.Properties settings) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(1F, 1F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 655,
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"))
    private static BlockBehaviour.Properties jigsaw(BlockBehaviour.Properties settings) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(5F, 6F);
    }
    @ModifyArg(index = 2, method = "<clinit>", at = @At(value = "INVOKE", ordinal = 654,
            target = "Lnet/minecraft/world/level/block/Blocks;register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)Lnet/minecraft/world/level/block/Block;"))
    private static BlockBehaviour.Properties structure(BlockBehaviour.Properties settings) {
        return BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(5F, 6F);
    }
}