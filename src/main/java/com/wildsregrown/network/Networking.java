package com.wildsregrown.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemLore;
import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class Networking {

    public static void initialize() {

        /*
        ServerPlayNetworking.registerGlobalReceiver(RadialBlockStatePayload.PACKET_ID, (payload, context) -> {

            final Map<String, String> map = new HashMap<>();
            AtomicReference<ItemLore> lore = new AtomicReference<>(ItemLore.EMPTY);

            if (!payload.properties().equals("{}")) {
                Arrays.stream(payload.properties().substring(1, payload.properties().length() - 1).toLowerCase().split(", ")).forEach(Property -> {
                    final String[] PropertyPair = Property.split("=");
                    map.put(PropertyPair[0], PropertyPair[1]);
                    lore.set(lore.get().withLineAdded(Component.nullToEmpty("§7" + StringUtils.capitalize(PropertyPair[0]) + ": §8" + PropertyPair[1])));
                });
            }

            ItemStack item = context.player().getMainHandItem();

            if (map.isEmpty()) {
                item.remove(DataComponents.BLOCK_STATE);
                item.remove(DataComponents.ENCHANTMENT_GLINT_OVERRIDE);
                item.set(DataComponents.LORE, lore.get());
            } else {
                item.set(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
                item.set(DataComponents.BLOCK_STATE, new BlockItemStateProperties(map));
                item.set(DataComponents.LORE, lore.get());
            }
        });

         */


    }

}
