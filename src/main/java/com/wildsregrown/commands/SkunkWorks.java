
package com.wildsregrown.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sipke.api.features.botanic.trees.graph.TreeGraph;
import com.sipke.api.features.botanic.trees.graph.TreeNode;
import com.sipke.features.trees.TreeBuilder;
import com.wildsregrown.registries.world.Trees;
import java.util.Random;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Blocks;

public class SkunkWorks {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((Commands.literal("skunkworks").requires(CommandSourceStack::isPlayer))
                        .executes((context) -> skunkworks(context.getSource()))
        );
    }

    public static int skunkworks(CommandSourceStack source) {

        ServerLevel world = source.getLevel();
        BlockPos pos = source.getPlayer().blockPosition();
        TreeGraph graph = TreeGraph.create(Trees.ancient_oak.getKey());
        TreeBuilder.update(graph,new Random().nextInt(),12, 1);
        //render(graph.getRoot(), pos, world);

        return 0;
    }

    private static void render(TreeNode localRoot, BlockPos pos, ServerLevel world) {
        if (localRoot != null) {

            BlockPos newPos = new BlockPos(
                    pos.getX() + localRoot.getX(),
                    pos.getY() + localRoot.getY(),
                    pos.getZ() + localRoot.getZ()
            );

            if (localRoot.volume > 1) {
                world.setBlockAndUpdate(newPos, Blocks.GLASS.defaultBlockState());
            }else
            if (localRoot.volume > 0.5) {
                world.setBlockAndUpdate(newPos, Blocks.BLACK_STAINED_GLASS.defaultBlockState());
            }else
            if (localRoot.volume > 0.25) {
                world.setBlockAndUpdate(newPos, Blocks.WHITE_STAINED_GLASS.defaultBlockState());
            }else {
                world.setBlockAndUpdate(newPos, Blocks.OAK_LEAVES.defaultBlockState());
            }

            render(localRoot.left, pos, world);
            render(localRoot.right, pos, world);
        }
    }

}
