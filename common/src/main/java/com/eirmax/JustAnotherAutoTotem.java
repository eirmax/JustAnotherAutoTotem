package com.eirmax;

import com.eirmax.config.FileWriter.AutoTotemFileBuilder;

public final class JustAnotherAutoTotem {
    public static final String MOD_ID = "justanotherautototem";

    public static void init() {

        AutoTotemFileBuilder.load();
    }
}
