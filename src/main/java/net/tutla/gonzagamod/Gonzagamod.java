package net.tutla.gonzagamod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.tutla.gonzagamod.commandsystem.CommandSystem;
import net.tutla.gonzagamod.downloader.AutoSetup;
import net.tutla.gonzagamod.downloader.AutoUpdater;
import net.tutla.gonzagamod.items.PeriPeriLoadedFries;

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

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient()) return ActionResult.PASS;

            ItemStack stack = player.getStackInHand(hand);

            if (stack.getItem() == Items.STICK) {
                if (entity instanceof PlayerEntity target) {
                    target.giveItemStack(new ItemStack(GonzagaItems.GONZAGA_CLIFTON_MILK));
                }

                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        });

        System.out.println("erm? yes im the rizzler");
    }



    public static String getVersion(){
        return version;
    }
}