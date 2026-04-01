package net.tutla.gonzagamod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.TutlaState;
import net.tutla.gonzagamod.client.screen.BlacklistScreen;

import java.util.ArrayList;
import java.util.List;

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
