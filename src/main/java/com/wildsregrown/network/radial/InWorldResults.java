package com.wildsregrown.network.radial;

import com.wildsregrown.WildsRegrown;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record InWorldResults(String results, String placed) implements CustomPayload {

    public static final Identifier identifier = Identifier.of(WildsRegrown.modid, "in_world");
    public static final CustomPayload.Id<InWorldResults> PACKET_ID = new CustomPayload.Id<>(identifier);
    // should you need to send more data, add the appropriate record parameters and change your codec:
    public static final PacketCodec<RegistryByteBuf, InWorldResults> codec = PacketCodec.tuple(
            PacketCodecs.STRING, InWorldResults::results,
            PacketCodecs.STRING, InWorldResults::placed,
            InWorldResults::new
    );

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
