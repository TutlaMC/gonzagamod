package net.tutla.gonzagamod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.tutla.gonzagamod.TutlaState;

public class GonzagamodClient implements ClientModInitializer {
    private TutlaState state;

    private static boolean doShutdown = false;
    private int shutdownWait = 5*20;

    @Override
    public void onInitializeClient() {
        AutoUpdater.checkAndUpdate();
        ClientPlayConnectionEvents.INIT.register(((handler, client) -> {
            ModChecker.doCheck(client);
        }));

        ClientTickEvents.START_CLIENT_TICK.register((client -> {
            if (doShutdown){
                shutdownWait-=1;
            }
            if (shutdownWait <= 0){
                client.close();
            }
        }));;
    }


    public static void startDoShutdown() {
        doShutdown = true;
    }
}
