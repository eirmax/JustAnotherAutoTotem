package com.eirmax.config;

import com.eirmax.config.FileWriter.AutoTotemFileBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AutoTotemConfigScreen {
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("config.justanotherautototem.title"));

        ConfigCategory main = builder.getOrCreateCategory(Component.literal("Settings"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        main.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("config.justanotherautototem.enabled"),
                        AutoTotemFileBuilder.INSTANCE.isEnabled)
                .setTooltip(Component.translatable("config.justanotherautototem.enabled.tooltip"))
                .setSaveConsumer(val -> AutoTotemFileBuilder.INSTANCE.isEnabled = val)
                .build());

        main.addEntry(entryBuilder.startIntField(
                        Component.translatable("config.justanotherautototem.delay"),
                        AutoTotemFileBuilder.INSTANCE.Delay)
                .setTooltip(Component.translatable("config.justanotherautototem.delay.tooltip"))
                .setMin(0)
                .setMax(10000000)
                .setSaveConsumer(val -> {
                    AutoTotemFileBuilder.INSTANCE.Delay = val;
                    AutoTotemFileBuilder.save();
                })
                .build());

        main.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("config.justanotherautototem.hotbar_only"),
                        AutoTotemFileBuilder.INSTANCE.hotbarOnly)
                .setTooltip(Component.translatable("config.justanotherautototem.hotbar_only.tooltip"))
                .setSaveConsumer(val -> AutoTotemFileBuilder.INSTANCE.hotbarOnly = val)
                .build());

        builder.setSavingRunnable(AutoTotemFileBuilder::save);
        return builder.build();
    }
}