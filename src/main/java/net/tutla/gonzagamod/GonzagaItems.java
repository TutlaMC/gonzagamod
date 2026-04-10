package net.tutla.gonzagamod;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.tutla.gonzagamod.items.AiVideo;
import net.tutla.gonzagamod.items.CliftonMilk;
import net.tutla.gonzagamod.items.PeriPeriLoadedFries;

import java.util.ArrayList;
import java.util.function.Function;

public final class GonzagaItems {
    public static final ArrayList<Item> gonzagaItems = new ArrayList<>();
    public static final Item GONZAGA_AI_VIDEO = register("ai_video", AiVideo::new);
    public static final Item GONZAGA_PERIPERI_LOADED_FRIES = register("periperi_loaded_fries", PeriPeriLoadedFries::new);
    public static final Item GONZAGA_CLIFTON_MILK = register("clifton_milk", CliftonMilk::new);

    private static Item register(String name, Function<Item.Settings, Item> factory) {

        Identifier id = Identifier.of(Gonzagamod.MOD_ID, name);
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, id);

        Item.Settings settings = new Item.Settings().registryKey(key);
        Item item = factory.apply(settings);

        Item e = Registry.register(Registries.ITEM, id, item);
        gonzagaItems.add(e);
        return e;
    }

    public static void initialize() {

    }
}
