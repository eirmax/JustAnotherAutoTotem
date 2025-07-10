package com.eirmax.fabric.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class KeyBindingRegistry {
    public static void init() {
        registerKeyBind("key.justanotherautototem.open_menu", GLFW.GLFW_KEY_R, "category.justanotherautototem");
        registerKeyBind("key.elytraswapplus.auto_swap", GLFW.GLFW_KEY_B, "category.justanotherautototem");
    }

    public static KeyMapping registerKeyBind(String translationKeyName, int defaultKeyId, String category) {
        KeyMapping bind = new KeyMapping(translationKeyName, defaultKeyId, category);
        KeyBindingHelper.registerKeyBinding(bind);
        ClientFabricKeybindEvent.keyMappings.add(bind);
        return bind;
    }
}
