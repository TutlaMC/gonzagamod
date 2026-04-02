package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.tutla.gonzagamod.commandsystem.CommandSystem;
import net.tutla.gonzagamod.downloader.AutoSetup;
import net.tutla.gonzagamod.downloader.AutoUpdater;

public class Gonzagamod implements ModInitializer {
    private static TutlaState state;
    public static final String MOD_ID = "gonzagamod";
    protected static final String version = "1.0.5";

    public static CommandSystem commandSystem = new CommandSystem();

    @Override
    public void onInitialize() {
        GonzagaItems.initialize();
        AutoSetup.run();

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            commandSystem.registerAll(dispatcher);
        });

        System.out.println("erm? yes im the rizzler");
    }



    public static String getVersion(){
        return version;
    }
}