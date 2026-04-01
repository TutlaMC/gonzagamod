package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;

public class Gonzagamod implements ModInitializer {
    private static TutlaState state;
    protected static final String version = "1.0.3";

    @Override
    public void onInitialize() {
    }

    public static String getVersion(){
        return version;
    }
}
