package com.wildsregrown.network.payloads;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public record StructureSavePayload(int x, int y, int z) implements CustomPayload {

    public static final Id<StructureSavePayload> ID = new Id<>(Identifier.of(modid, "pos_payload"));
    public static final PacketCodec<RegistryByteBuf, StructureSavePayload> codec = PacketCodec.tuple(
            PacketCodecs.INTEGER, StructureSavePayload::z,
            PacketCodecs.INTEGER, StructureSavePayload::y,
            PacketCodecs.INTEGER, StructureSavePayload::z,
            StructureSavePayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}