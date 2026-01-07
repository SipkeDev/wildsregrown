package com.wildsregrown.network;

import com.wildsregrown.WildsRegrown;
import com.wildsregrown.blocks.dungeon.StructureEntity;
import com.wildsregrown.network.payloads.StructureBlockPayload;
import com.wildsregrown.network.radial.InWorldResults;
import com.wildsregrown.network.radial.RadialBlockStatePayload;
import com.wildsregrown.network.radial.RadialItemPayload;
import com.wildsregrown.network.server.ConnectMessage;
import com.wildsregrown.network.server.DisconnectMessage;
import com.wildsregrown.registries.ModComponents;
import com.wildsregrown.world.WRGChunkGenerator;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.BlockStateComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static net.minecraft.world.World.OVERWORLD;

public class Networking {

    public static List<ServerPlayerEntity> connected_players = new ArrayList<>();

    public static void connectHandler(ServerPlayerEntity player) {
        for (ServerPlayerEntity entity : connected_players) {
            entity.sendMessage(Text.of("People are joining the server!"));
        }
        connected_players.add(player);
        player.sendMessage(Text.of("Make sure to use /wrg to find generation features quickly."));

        if(player.getEntityWorld() != null){
            ChunkGenerator chunkGenerator = player.getEntityWorld().getChunkManager().getChunkGenerator();
            if (chunkGenerator instanceof WRGChunkGenerator generator){
                int[] rgbMap = generator.getWorld().getWorldMap();
                StringBuilder madness = new StringBuilder();
                for (int map : rgbMap) {
                    //madness.append(",").append(Integer.toHexString(map));
                }
                //LOGGER.info(madness.toString());
                //player.networkHandler.send(new WorldMap(madness.toString().getBytes()));
            }
        }
    }

    public static void disconnectHandler(MinecraftServer server, ServerPlayNetworkHandler playNetworkHandler) {
        connected_players.remove(playNetworkHandler.player);
        for (ServerPlayerEntity entity : connected_players) {
            entity.sendMessage(Text.of("People are leaving the server..."));
        }
    }

    public static void initialize() {

        PayloadTypeRegistry.playC2S().register(ConnectMessage.PACKET_ID, ConnectMessage.codec);
        PayloadTypeRegistry.playS2C().register(DisconnectMessage.PACKET_ID, DisconnectMessage.codec);

        //Blockstate builder
        PayloadTypeRegistry.playC2S().register(RadialBlockStatePayload.PACKET_ID, RadialBlockStatePayload.codec);
        PayloadTypeRegistry.playC2S().register(RadialItemPayload.PACKET_ID, RadialItemPayload.codec);
        PayloadTypeRegistry.playS2C().register(InWorldResults.PACKET_ID, InWorldResults.codec);

        //StructureScreen
        PayloadTypeRegistry.playS2C().register(StructureBlockPayload.ID, StructureBlockPayload.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(StructureBlockPayload.ID, StructureBlockPayload.PACKET_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(ConnectMessage.PACKET_ID, (payload, context) -> Networking.connectHandler(context.player()));
        ServerPlayConnectionEvents.DISCONNECT.register((playNetworkHandler, server) -> Networking.disconnectHandler(server, playNetworkHandler));

        ServerPlayNetworking.registerGlobalReceiver(RadialBlockStatePayload.PACKET_ID, (payload, context) -> {

            final Map<String, String> map = new HashMap<>();
            AtomicReference<LoreComponent> lore = new AtomicReference<>(LoreComponent.DEFAULT);

            if (!payload.properties().equals("{}")) {
                Arrays.stream(payload.properties().substring(1, payload.properties().length() - 1).toLowerCase().split(", ")).forEach(Property -> {
                    final String[] PropertyPair = Property.split("=");
                    map.put(PropertyPair[0], PropertyPair[1]);
                    lore.set(lore.get().with(Text.of("§7" + StringUtils.capitalize(PropertyPair[0]) + ": §8" + PropertyPair[1])));
                });
            }

            ItemStack item = context.player().getMainHandStack();

            if (map.isEmpty()) {
                item.remove(DataComponentTypes.BLOCK_STATE);
                item.remove(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE);
                item.set(DataComponentTypes.LORE, lore.get());
            } else {
                item.set(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
                item.set(DataComponentTypes.BLOCK_STATE, new BlockStateComponent(map));
                item.set(DataComponentTypes.LORE, lore.get());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(RadialItemPayload.PACKET_ID, (payload, context) -> {
            if (context.player().getMainHandStack().getComponents().contains(ModComponents.ITEM_STANCE)) {
                context.player().getMainHandStack().set(ModComponents.ITEM_STANCE, payload.stance());
            }
        });

        ServerPlayNetworking.registerGlobalReceiver(StructureBlockPayload.ID, (payload, context) -> {
            BlockPos pos = new BlockPos(
                    payload.compound().getInt("posx",0),
                    payload.compound().getInt("posy",0),
                    payload.compound().getInt("posz",0)
            );
            if (context.server().getWorld(OVERWORLD).getBlockEntity(pos) instanceof StructureEntity entity){
                entity.deployPayload(payload);
                if (Objects.equals(payload.compound().getString("action"), "save")){
                    if (entity.save(context.server().getOverworld(), context.player())) {
                        context.player().sendMessage(Text.of("Structure saved"), true);
                    }
                }
                if (Objects.equals(payload.compound().getString("action"), "load")){
                    if (entity.load(context.server().getOverworld(), context.player())) {
                        context.player().sendMessage(Text.of("Structure loaded"), true);
                    }
                }
                if (Objects.equals(payload.compound().getString("action"), "floor")){
                    if (entity.createGlassFloor()) {
                        context.player().sendMessage(Text.of("Floor paved"), true);
                    }
                }
            }
        });

    }

}
