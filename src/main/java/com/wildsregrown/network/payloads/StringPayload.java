package com.wildsregrown.network.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public record StringPayload(int integer) implements CustomPayload {
    public static final Id<StringPayload> ID = new Id<>(Identifier.of(modid, "integer_payload"));
    public static final PacketCodec<ByteBuf, StringPayload> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER,
                    StringPayload::integer,
                    StringPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}