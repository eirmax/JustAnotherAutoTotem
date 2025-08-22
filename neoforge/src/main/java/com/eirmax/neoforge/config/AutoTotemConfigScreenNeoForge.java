package com.eirmax.neoforge.config;

import com.eirmax.config.FileWriter.AutoTotemFileBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AutoTotemConfigScreenNeoForge {
    public static Screen create(Screen parent) {
        try {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("config.justanotherautototem.title"))
                    .setDefaultBackgroundTexture(null);

            ConfigCategory main = builder.getOrCreateCategory(Component.literal("Settings"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            main.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("config.justanotherautototem.enabled"),
                            AutoTotemFileBuilder.INSTANCE.isEnabled)
                    .setDefaultValue(true)
                    .setSaveConsumer(val -> {
                        AutoTotemFileBuilder.INSTANCE.isEnabled = val;
                        AutoTotemFileBuilder.save();
                    })
                    .build());

            main.addEntry(entryBuilder.startIntField(
                            Component.translatable("config.justanotherautototem.delay"),
                            AutoTotemFileBuilder.INSTANCE.Delay)
                    .setDefaultValue(0)
                    .setTooltip(Component.translatable("config.justanotherautototem.delay.tooltip"))
                    .setMin(0)
                    .setSaveConsumer(val -> {
                        AutoTotemFileBuilder.INSTANCE.Delay = val;
                        AutoTotemFileBuilder.save();
                    })
                    .build());

            main.addEntry(entryBuilder.startBooleanToggle(
                            Component.translatable("config.justanotherautototem.hotbar_only"),
                            AutoTotemFileBuilder.INSTANCE.hotbarOnly)
                    .setDefaultValue(false)
                    .setTooltip(Component.translatable("config.justanotherautototem.hotbar_only.tooltip"))
                    .setSaveConsumer(val -> {
                        AutoTotemFileBuilder.INSTANCE.hotbarOnly = val;
                        AutoTotemFileBuilder.save();
                    })
                    .build());

            builder.setSavingRunnable(AutoTotemFileBuilder::save);
            return builder.build();

        } catch (Exception e) {
            return createFallbackScreen(parent);
        }
    }

    private static Screen createFallbackScreen(Screen parent) {
        return parent;
    }
}