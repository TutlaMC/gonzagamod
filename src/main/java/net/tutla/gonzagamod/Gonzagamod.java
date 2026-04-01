package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class Gonzagamod implements ModInitializer {
    private static TutlaState state;
    public static final String MOD_ID = "gonzagamod";
    protected static final String version = "1.0.3";

    public static final Item TEST_ITEM = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "ai_video"),
            new Item(new Item.Settings())
    );

    @Override
    public void onInitialize() {
        System.out.println("erm? yes im the rizzler");
    }

    public static String getVersion(){
        return version;
    }
}
