package com.wildsregrown.network.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public record StructureBlockPayload(NbtCompound compound) implements CustomPayload {
    public static final Id<StructureBlockPayload> ID = new Id<>(Identifier.of(modid, "structure_block_payload"));

    public static final PacketCodec<ByteBuf, StructureBlockPayload> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.UNLIMITED_NBT_COMPOUND, StructureBlockPayload::compound,
                    StructureBlockPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}