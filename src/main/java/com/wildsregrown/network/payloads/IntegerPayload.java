package com.wildsregrown.network.payloads;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static com.wildsregrown.WildsRegrown.modid;

public record IntegerPayload(int integer) implements CustomPayload {
    public static final Id<IntegerPayload> ID = new Id<>(Identifier.of(modid, "integer_payload"));

    public static final PacketCodec<ByteBuf, IntegerPayload> PACKET_CODEC =
            PacketCodec.tuple(
                    PacketCodecs.INTEGER,
                    IntegerPayload::integer,
                    IntegerPayload::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}