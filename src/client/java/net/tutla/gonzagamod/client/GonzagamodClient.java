package net.tutla.gonzagamod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.tutla.gonzagamod.TutlaState;

public class GonzagamodClient implements ClientModInitializer {
    private TutlaState state;

    @Override
    public void onInitializeClient() {
        AutoUpdater.checkAndUpdate();
        ClientPlayConnectionEvents.INIT.register(((handler, client) -> {
            ModChecker.doCheck(client);
        }));
    }
}
