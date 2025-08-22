package com.eirmax.neoforge.client;

import com.eirmax.JustAnotherAutoTotem;
import com.eirmax.neoforge.config.AutoTotemConfigScreenNeoForge;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = JustAnotherAutoTotem.MOD_ID, value = Dist.CLIENT)
public class ClientNeoforgeKeybindEvent {
    public static final List<Lazy<KeyMapping>> keyMappings = new ArrayList<>();

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        keyMappings.add(Lazy.of(() -> new KeyMapping("key.justanotherautototem.open_menu", GLFW.GLFW_KEY_B, "category.justanotherautototem")));

        for (Lazy<KeyMapping> key : keyMappings) {
            event.register(key.get());
        }
    }
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ModLoadingContext.get().registerExtensionPoint(
                    IConfigScreenFactory.class,
                    () -> (mc, screen) -> {
                        try {
                            return AutoTotemConfigScreenNeoForge.create(screen);
                        } catch (Exception e) {
                            JustAnotherAutoTotem.LOGGER.info("Failed to create config screen");
                            return null;
                        }
                    }
            );
        });
    }
}