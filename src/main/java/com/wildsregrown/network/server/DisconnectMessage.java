package com.wildsregrown.network.server;

import com.wildsregrown.WildsRegrown;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public class DisconnectMessage implements CustomPayload {

    public static final Identifier exit = Identifier.of(WildsRegrown.modid, "exit");
    public static final Id<DisconnectMessage> PACKET_ID = new Id<>(exit);
    public static final PacketCodec<RegistryByteBuf, DisconnectMessage> codec = PacketCodec.of(DisconnectMessage::write, DisconnectMessage::read);

    public static DisconnectMessage read(RegistryByteBuf buf) {
        return new DisconnectMessage();
    }

    public void write(RegistryByteBuf buf) {}

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
