package com.eirmax.config;

import com.eirmax.JustAnotherAutoTotem;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = JustAnotherAutoTotem.MOD_ID)
public class AutoTotemValidations {

    public boolean isEnabled = true;
    public int Delay = 0;
    public boolean hotbarOnly = false;

    public AutoTotemValidations() {}
}
