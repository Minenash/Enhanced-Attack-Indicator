package com.minenash.enhanced_attack_indicator.config;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuEntryPoint implements ModMenuApi {

    @Override
    public String getModId() {
        return "seamless_loading_screen";
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return Config::getModConfigScreen;
    }

}
