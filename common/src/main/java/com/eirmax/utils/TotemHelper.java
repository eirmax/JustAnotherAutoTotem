package com.eirmax.utils;

import com.eirmax.config.FileWriter.AutoTotemFileBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.HashedStack;
import net.minecraft.network.protocol.game.ServerboundContainerClickPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TotemHelper {
    public static volatile boolean shouldMoveTotem = false;
    public static boolean TotemInOffhand = false;
    public static boolean TotemInMainhand = false;

    private static long lastSwapTime = 0;

    public static void updateTotemState(Player player) {
        if (player == null) return;
        Inventory inv = player.getInventory();
        TotemInMainhand = !inv.getItem(inv.getSelectedSlot()).isEmpty() && inv.getItem(inv.getSelectedSlot()).getItem() == Items.TOTEM_OF_UNDYING;
        TotemInOffhand = !inv.getItem(45).isEmpty() && inv.getItem(45).getItem() == Items.TOTEM_OF_UNDYING;
    }

    public static int getTotemSlot(Inventory inventory) {
        boolean hotbarOnly = AutoTotemFileBuilder.INSTANCE.hotbarOnly;

        if (hotbarOnly) {
            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
                }
            }
            return -1;
        } else {
            for (int i = 9; i < 36; i++) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
                }
            }
            for (int i = 0; i < 9; i++) {
                ItemStack stack = inventory.getItem(i);
                if (!stack.isEmpty() && stack.getItem() == Items.TOTEM_OF_UNDYING) {
                    return i;
                }
            }
            return -1;
        }
    }

    public static void slotTick(Player player, int slot) {
        if (player == null) return;

        int delay = AutoTotemFileBuilder.INSTANCE.Delay;
        long now = System.currentTimeMillis();
        if (delay >= 0 && now - lastSwapTime < delay) {
            return;
        }
        lastSwapTime = now;

        var inv = player.getInventory();
        AbstractContainerMenu screenHandler = player.containerMenu;
        var networkHandler = Minecraft.getInstance().getConnection();
        if (networkHandler == null) return;

        ItemStack offhandStack = inv.getItem(45);
        ItemStack mainHandStack = inv.getItem(inv.getSelectedSlot());

        boolean offhandNeedsTotem = offhandStack.isEmpty() || offhandStack.getItem() != Items.TOTEM_OF_UNDYING;

        if (TotemInOffhand || (TotemInMainhand && offhandNeedsTotem)) {
            if (!offhandNeedsTotem) return;

            if (slot < 9) {
                int previousSlot = inv.getSelectedSlot();
                networkHandler.send(new ServerboundSetCarriedItemPacket(slot));
                networkHandler.send(new ServerboundPlayerActionPacket(
                        ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND,
                        BlockPos.ZERO,
                        Direction.DOWN
                ));
                networkHandler.send(new ServerboundSetCarriedItemPacket(previousSlot));
            } else {
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) slot,
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) 45,
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
            }
        } else if (TotemInMainhand && !offhandNeedsTotem) {
            if (!mainHandStack.isEmpty() && mainHandStack.getItem() == Items.TOTEM_OF_UNDYING) return;
            if (slot < 9) {
                networkHandler.send(new ServerboundSetCarriedItemPacket(slot));
            } else {
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) slot,
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) inv.getSelectedSlot(),
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
            }
        } else {
            if (!offhandNeedsTotem) return;

            if (slot < 9) {
                int previousSlot = inv.getSelectedSlot();
                networkHandler.send(new ServerboundSetCarriedItemPacket(slot));
                networkHandler.send(new ServerboundPlayerActionPacket(
                        ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND,
                        BlockPos.ZERO,
                        Direction.DOWN
                ));
                networkHandler.send(new ServerboundSetCarriedItemPacket(previousSlot));
            } else {
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) slot,
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
                networkHandler.send(new ServerboundContainerClickPacket(
                        screenHandler.containerId,
                        screenHandler.getStateId(),
                        (short) 45,
                        (byte) 0,
                        ClickType.PICKUP,
                        new Int2ObjectOpenHashMap<>(),
                        HashedStack.EMPTY
                ));
            }
        }
    }

    public static void resetTotemState() {
        TotemInOffhand = false;
        TotemInMainhand = false;
    }
}