package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;
import net.tutla.gonzagamod.downloader.AutoUpdater;

public class Gonzagamod implements ModInitializer {
    private static TutlaState state;
    public static final String MOD_ID = "gonzagamod";
    protected static final String version = "1.0.5";

    @Override
    public void onInitialize() {
        GonzagaItems.initialize();
        AutoUpdater.checkAndUpdate();
        System.out.println("erm? yes im the rizzler");
    }

    public static String getVersion(){
        return version;
    }
}