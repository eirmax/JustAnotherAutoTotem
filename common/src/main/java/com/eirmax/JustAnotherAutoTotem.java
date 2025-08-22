package com.eirmax;

import com.eirmax.config.FileWriter.AutoTotemFileBuilder;

import java.util.logging.Logger;

public final class JustAnotherAutoTotem {
    public static final String MOD_ID = "justanotherautototem";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static void init() {

        AutoTotemFileBuilder.load();


    }
}
