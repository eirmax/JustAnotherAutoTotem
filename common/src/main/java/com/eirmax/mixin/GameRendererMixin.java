package com.eirmax.mixin;

import com.eirmax.utils.TotemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Inject(method = "displayItemActivation", at = @At("TAIL"))
    private void onTotemUsed(ItemStack itemStack, CallbackInfo ci) {
        if (!itemStack.is(Items.TOTEM_OF_UNDYING)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int slot = TotemHelper.getTotemSlot(mc.player.getInventory());
        if (slot == -1) return;

        TotemHelper.slotTick(mc.player, slot);
    }
}