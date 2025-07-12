package com.eirmax.neoforge.client;

import com.eirmax.JustAnotherAutoTotem;
import com.eirmax.neoforge.config.AutoTotemConfigScreenNeoForge;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.util.Lazy;

@EventBusSubscriber(modid = JustAnotherAutoTotem.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientNeoforgeEvent {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        for (Lazy<KeyMapping> key : ClientNeoforgeKeybindEvent.keyMappings) {
            while (Minecraft.getInstance().player != null && key.get().consumeClick()) {
                if (key.get().getName().equals("key.justanotherautototem.open_menu")) {
                    if (Minecraft.getInstance().screen == null) {
                        Minecraft.getInstance().setScreen(AutoTotemConfigScreenNeoForge.create(null));
                    }
                }
            }
        }
    }
}