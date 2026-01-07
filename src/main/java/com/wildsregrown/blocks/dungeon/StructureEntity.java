package com.wildsregrown.blocks.dungeon;

import com.sipke.math.MathUtil;
import com.wildsregrown.WildsRegrown;
import com.wildsregrown.network.payloads.StructureBlockPayload;
import com.wildsregrown.registries.ModEntities;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.sipke.WorldConstants.chunkSize;
import static com.wildsregrown.WildsRegrown.modid;

public class StructureEntity extends BlockEntity {

    private String name;
    private int x0;
    private int y0;
    private int z0;
    private int x1;
    private int y1;
    private int z1;
    private boolean showBoundingBox;

    public StructureEntity(BlockPos pos, BlockState state) {
        super(ModEntities.structureBlock, pos, state);
        this.name = "new_structure";
        this.x0 = 0;
        this.y0 = 0;
        this.z0 = 0;
        this.x1 = 0;
        this.y1 = 0;
        this.z1 = 0;
        this.showBoundingBox = true;
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public void deployPayload(StructureBlockPayload payload){
        this.x0 = payload.compound().getInt("x0").get();
        this.y0 = payload.compound().getInt("y0").get();
        this.z0 = payload.compound().getInt("z0").get();
        this.x1 = payload.compound().getInt("x1").get();
        this.y1 = payload.compound().getInt("y1").get();
        this.z1 = payload.compound().getInt("z1").get();
        this.name = payload.compound().getString("name").get();
        this.showBoundingBox = payload.compound().getBoolean("show").get();
        this.markDirty();
        if (hasWorld()) {
            getWorld().updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 2);
        }
    }

    public void openScreen(ServerPlayerEntity player){
        NbtCompound compound = new NbtCompound();
        compound.putInt("posx", pos.getX());
        compound.putInt("posy", pos.getY());
        compound.putInt("posz", pos.getZ());
        compound.putString("name", name);
        compound.putBoolean("show", showBoundingBox);
        compound.putInt("x0", this.x0);
        compound.putInt("y0", this.y0);
        compound.putInt("z0", this.z0);
        compound.putInt("x1", this.x1);
        compound.putInt("y1", this.y1);
        compound.putInt("z1", this.z1);
        ServerPlayNetworking.send(player, new StructureBlockPayload(compound));
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);
        view.putString("name", name);
        view.putBoolean("show", showBoundingBox);
        view.putInt("x0", this.x0);
        view.putInt("y0", this.y0);
        view.putInt("z0", this.z0);
        view.putInt("x1", this.x1);
        view.putInt("y1", this.y1);
        view.putInt("z1", this.z1);
    }

    @Override
    protected void readData(ReadView view) {
        this.name = view.getString("name", "empty");
        this.x0 = view.getInt("x0", 0);
        this.y0 = view.getInt("y0", 0);
        this.z0 = view.getInt("z0", 0);
        this.x1 = view.getInt("x1", 0);
        this.y1 = view.getInt("y1", 0);
        this.z1 = view.getInt("z1", 0);
        this.showBoundingBox = view.getBoolean("show", true);
    }

    public boolean save(ServerWorld world, ServerPlayerEntity player) {

        StructureTemplateManager templateManager = world.getStructureTemplateManager();
        StructureTemplate template;

        int sx = this.pos.getX() + this.x0;
        int sy = this.pos.getY() + this.y0;
        int sz = this.pos.getZ() + this.z0;
        int sizeX = this.x1 + MathUtil.abs(this.x0);
        int sizeY = this.y1 + MathUtil.abs(this.y0);
        int sizeZ = this.z1 + MathUtil.abs(this.z0);

        boolean succes = true;

        int cols = (sizeX/chunkSize) + 1;
        int rows = (sizeZ/chunkSize) + 1;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {

                int dx = i * chunkSize;
                int dz = j * chunkSize;
                int k = i * rows + j;
                WildsRegrown.LOGGER.info("Piece: " + k);

                Vec3i size = new Vec3i(
                        MathUtil.min(sizeX - dx, chunkSize),
                        sizeY,
                        MathUtil.min(sizeZ - dz, chunkSize)
                );
                Identifier identifier = Identifier.of(modid, name + "_" + k);

                WildsRegrown.LOGGER.info(identifier.getNamespace());
                try {
                    template = templateManager.getTemplateOrBlank(identifier);
                } catch (InvalidIdentifierException var) {
                    return false;
                }

                BlockPos blockPos = new BlockPos(sx + dx, sy, sz + dz);

                List<Block> ignore = new ArrayList<>();
                ignore.add(Blocks.STRUCTURE_VOID);
                template.saveFromWorld(world, blockPos, size, false, ignore);
                template.setAuthor(modid);
                if (templateManager.saveTemplate(identifier)){
                    player.sendMessage(Text.of("Saved piece: " + k), false);
                }else {
                    if (succes){
                        succes = false;
                    }
                }
            }
        }

        //return if succes Message should be send to player
        return succes;
    }

    public boolean load(ServerWorld world, ServerPlayerEntity player) {

        Identifier identifier = Identifier.of(modid, this.name + "_0");
        StructureTemplateManager templateManager = world.getStructureTemplateManager();
        StructureTemplate template = templateManager.getTemplate(identifier).orElse(null);

        WildsRegrown.LOGGER.info(identifier.toTranslationKey());
        if (template == null) {
            WildsRegrown.LOGGER.info("NOT FOUND");
            player.sendMessage(Text.of("Not found"), true);
            return false;
        } else {

            int startX = this.pos.getX() + x0;
            int startY = this.pos.getY() + y0;
            int startZ = this.pos.getZ() + z0;
            int sizeX = x1 + MathUtil.abs(x0);
            int sizeY = y1 + MathUtil.abs(y0);
            int sizeZ = z1 + MathUtil.abs(z0);

            boolean succes = true;

            int cols = (sizeX/chunkSize) + 1;
            int rows = (sizeZ/chunkSize) + 1;
            for (int i = 0; i < cols; i++) {
                for (int j = 0; j < rows; j++) {

                    int dx = i*chunkSize;
                    int dz = j*chunkSize;
                    int k = i*rows+j;

                    Identifier id = Identifier.of(modid, this.name + "_" + k);
                    StructureTemplate newTemplate = templateManager.getTemplate(id).orElse(null);
                    if (newTemplate != null){
                        BlockPos pos = new BlockPos(
                                startX+dx,
                                startY,
                                startZ+dz
                        );
                        StructurePlacementData data = new StructurePlacementData();
                        succes = newTemplate.place(world, pos, pos, data, world.getRandom(), 19);
                        if (succes){
                            player.sendMessage(Text.of("Placed piece: " + k), false);
                        }
                    }else {
                        WildsRegrown.LOGGER.info("Not found: " + id.toTranslationKey());
                    }

                }
            }

            this.markDirty();
            return succes;
        }
    }

    public boolean createGlassFloor(){
        if (this.world instanceof ServerWorld world){

            int startX = this.pos.getX() + x0;
            int startZ = this.pos.getZ() + z0;
            int sizeX = x1 + MathUtil.abs(x0);
            int sizeZ = z1 + MathUtil.abs(z0);

            boolean succes = true;

            BlockPos.Mutable mutable = new BlockPos.Mutable(startX, (this.pos.getY()+y0)-1, startZ);
            int tx = startX+sizeX, tz = startZ+sizeZ;
            for (int i = startX; i < tx; i++) {
                for (int j = startZ; j < tz; j++) {
                    mutable.setX(i);
                    mutable.setZ(j);
                    world.setBlockState(mutable, Blocks.GLASS.getDefaultState());
                }
            }

            //return if succes Message should be send to player
            return succes;
        }
        return false;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return this.createComponentlessNbt(registries);
    }

    public String getName() {
        return name;
    }

    public boolean doesShow() {
        return showBoundingBox;
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public int getZ0() {
        return z0;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getZ1() {
        return z1;
    }
}
