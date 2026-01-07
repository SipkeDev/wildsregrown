package com.wildsregrown.blocks.wood.tree;

import com.sipke.api.features.trees.graph.TreeGraph;
import com.sipke.api.features.trees.graph.TreeNode;
import com.sipke.api.features.trees.type.TreeType;
import com.sipke.features.trees.TreeBuilder;
import com.sipke.math.MathUtil;
import com.sipke.registeries.Trees;
import com.wildsregrown.registries.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static com.wildsregrown.WildsRegrown.modid;

public class TreeEntity extends BlockEntity{

    private TreeGraph treeGraph;
    private int seed;

    public TreeEntity(BlockPos pos, BlockState state) {
        super(ModEntities.tree, pos, state);
    }

    public void fell(){
        remove(treeGraph.getRoot(), pos, this.world);
    }

    public void spawn(int key, int seed, TreeGraph graph, StructureWorldAccess world) {
        this.seed = seed;
        this.treeGraph = graph;
        Block log = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_log"));
        Block leaves = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_leaves"));
        render(treeGraph.getRoot(), pos, world, log, leaves);
    }

    public void reset(int key){
        if (treeGraph == null){
            treeGraph = TreeGraph.create(key);
        }
        if (this.world instanceof ServerWorld serverWorld) {
            remove(treeGraph.getRoot(), pos, serverWorld);
            treeGraph = TreeGraph.create(key);
            TreeBuilder.update(treeGraph, seed,1, 1);

            Block log = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_log"));
            Block leaves = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_leaves"));

            render(treeGraph.getRoot(), pos, serverWorld, log, leaves);
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        if (this.treeGraph != null){
            view.putInt("tree", this.treeGraph.getTreeKey());
            view.putInt("age", this.treeGraph.age);
            view.putInt("seed", seed);
            view.putByteArray("root", TreeGraph.encode(this.treeGraph.getRoot()));
        }
    }

    @Override
    protected void readData(ReadView view) {
        super.readData(view);
        this.seed = view.getInt("seed",0);
        int key = view.getInt("tree", Trees.oak.getKey());
        int age = view.getInt("tree",0);
        Optional<byte[]> array = view.getOptionalByteArray("root");
        if (array.isPresent()) {
            TreeNode root = TreeGraph.decode(array.get());
            this.treeGraph = TreeGraph.load(key, root, age);
        }
    }

    public void update(World world, BlockPos pos, Random random) {
        if (treeGraph != null) {
            remove(treeGraph.getRoot(), pos, world);

            TreeBuilder.update(treeGraph, seed, 1,0.5f+random.nextFloat());

            Block log = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_log"));
            Block leaves = Registries.BLOCK.get(Identifier.of(modid, treeGraph.getFamily() + "_leaves"));

            render(treeGraph.getRoot(), pos, world, log, leaves);
        }
    }

    private static void render(TreeNode node, BlockPos pos, WorldAccess world, Block log, Block leaves) {
        if (node != null) {

            BlockPos newPos = new BlockPos(
                    pos.getX() + node.getX(),
                    pos.getY() + node.getY(),
                    pos.getZ() + node.getZ()
            );

            switch (node.type){
                case root -> {
                    TreeRenderer.renderRoot(world, node, newPos, log.getDefaultState());
                }
                case trunk -> {
                    TreeRenderer.renderTrunk(world, node, newPos, log.getDefaultState(), true);
                }
                case thin_branch -> {
                    world.setBlockState(newPos, log.getDefaultState(), 2);
                }
                case branch -> {
                    world.setBlockState(newPos, log.getDefaultState(), 2);
                }
                default -> {
                    world.setBlockState(newPos, log.getDefaultState(), 2);
                }
            }

            if (node.isLeafNode()) {
                if (node.type == TreeType.trunk){
                    TreeRenderer.renderDynamicLeafCluster(world, newPos, leaves.getDefaultState(), (node.depth/7)+2);
                }else if (node.depth <= 4){
                    TreeRenderer.renderSmallLeafCluster(world, newPos, leaves.getDefaultState());
                }else {
                    TreeRenderer.renderDynamicLeafCluster(world, newPos, leaves.getDefaultState(), MathUtil.min(node.depth/7, 4));
                }
            }

            render(node.left, pos, world, log, leaves);
            render(node.right, pos, world, log, leaves);

        }
    }

    private static void remove(TreeNode node, BlockPos pos, net.minecraft.world.World world) {
        if (node != null) {

            BlockPos newPos = new BlockPos(
                    pos.getX() + node.getX(),
                    pos.getY() + node.getY(),
                    pos.getZ() + node.getZ()
            );

            if (node.type != TreeType.root) {
                world.setBlockState(newPos, Blocks.AIR.getDefaultState());
            }
            if (node.type == TreeType.trunk){
                TreeRenderer.renderTrunk(world, node, newPos, Blocks.AIR.getDefaultState(), true);
            }

            if (node.isLeafNode()) {
                TreeRenderer.removeDynamicLeafCluster(world, newPos,node.depth/5);
            }

            remove(node.left, pos, world);
            remove(node.right, pos, world);
        }
    }

}
