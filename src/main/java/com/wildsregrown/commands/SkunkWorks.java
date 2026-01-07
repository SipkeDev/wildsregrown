
package com.wildsregrown.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.sipke.api.features.trees.graph.TreeGraph;
import com.sipke.api.features.trees.graph.TreeNode;
import com.sipke.features.trees.TreeBuilder;
import com.sipke.registeries.Trees;
import net.minecraft.block.Blocks;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

public class SkunkWorks {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register((CommandManager.literal("skunkworks").requires(ServerCommandSource::isExecutedByPlayer))
                        .executes((context) -> skunkworks(context.getSource()))
        );
    }

    public static int skunkworks(ServerCommandSource source) {

        ServerWorld world = source.getWorld();
        BlockPos pos = source.getPlayer().getBlockPos();
        TreeGraph graph = TreeGraph.create(Trees.ancient_oak.getKey());
        TreeBuilder.update(graph,new Random().nextInt(),12, 1);
        render(graph.getRoot(), pos, world);

        return 0;
    }

    private static void render(TreeNode localRoot, BlockPos pos, ServerWorld world) {
        if (localRoot != null) {

            BlockPos newPos = new BlockPos(
                    pos.getX() + localRoot.getX(),
                    pos.getY() + localRoot.getY(),
                    pos.getZ() + localRoot.getZ()
            );

            if (localRoot.volume > 1) {
                world.setBlockState(newPos, Blocks.GLASS.getDefaultState());
            }else
            if (localRoot.volume > 0.5) {
                world.setBlockState(newPos, Blocks.BLACK_STAINED_GLASS.getDefaultState());
            }else
            if (localRoot.volume > 0.25) {
                world.setBlockState(newPos, Blocks.WHITE_STAINED_GLASS.getDefaultState());
            }else {
                world.setBlockState(newPos, Blocks.OAK_LEAVES.getDefaultState());
            }

            render(localRoot.left, pos, world);
            render(localRoot.right, pos, world);
        }
    }

}
