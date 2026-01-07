package com.wildsregrown.network.server;

import com.wildsregrown.WildsRegrown;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

//todo find a way to send a large object
public record WorldMap(byte[] bytes) implements CustomPayload {

    public static final Identifier id = Identifier.of(WildsRegrown.modid, "worldMap");
    public static final Id<WorldMap> PACKET_ID = new Id<>(id);
    public static final PacketCodec<RegistryByteBuf, WorldMap> codec = PacketCodec.tuple(
            PacketCodecs.BYTE_ARRAY, WorldMap::bytes,
            WorldMap::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
