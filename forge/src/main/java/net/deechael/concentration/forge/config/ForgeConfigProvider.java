package net.deechael.concentration.forge.config;

import net.deechael.concentration.config.Config;
import net.deechael.concentration.config.ConfigProvider;

public class ForgeConfigProvider implements ConfigProvider {

    @Override
    public Config ensureLoaded() {
        return ConcentrationConfigForge.ensureLoaded();
    }

}
