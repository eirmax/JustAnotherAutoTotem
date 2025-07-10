package com.eirmax.neoforge.client;

import com.eirmax.JustAnotherAutoTotem;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = JustAnotherAutoTotem.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.justanotherautototem.swap", GLFW.GLFW_KEY_R, "category.justanotherautototem")));
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.justanotherautototem", GLFW.GLFW_KEY_B, "category.justanotherautototem")));

        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
}