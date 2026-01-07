package com.wildsregrown.network.radial;

import com.wildsregrown.WildsRegrown;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RadialItemPayload(int stance) implements CustomPayload {

    public static final Identifier identifier = Identifier.of(WildsRegrown.modid, "stance");
    public static final Id<RadialItemPayload> PACKET_ID = new Id<>(identifier);
    // should you need to send more data, add the appropriate record parameters and change your codec:
    public static final PacketCodec<RegistryByteBuf, RadialItemPayload> codec = PacketCodec.tuple(
            PacketCodecs.INTEGER, RadialItemPayload::stance,
            RadialItemPayload::new
    );
 
    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}

