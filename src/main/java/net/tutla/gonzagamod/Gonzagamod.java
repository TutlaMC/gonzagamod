package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class Gonzagamod implements ModInitializer {
    private static TutlaState state;
    public static final String MOD_ID = "gonzagamod";
    protected static final String version = "1.0.4";

    public static Item TEST_ITEM;

    @Override
    public void onInitialize() {
        TEST_ITEM = Registry.register(
                Registries.ITEM,
                Identifier.of(MOD_ID, "ai_video"),
                new Item(new Item.Settings().registryKey(
                        RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "ai_video"))
                ))
        );
        System.out.println("erm? yes im the rizzler");
    }

    public static String getVersion(){
        return version;
    }
}
