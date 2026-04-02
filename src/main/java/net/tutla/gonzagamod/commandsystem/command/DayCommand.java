package net.tutla.gonzagamod.commandsystem.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.commandsystem.TutlaCommand;

import java.util.Objects;

public class DayCommand extends TutlaCommand {
    public DayCommand(){
        super("day","gets the day");
    }

    public boolean run(CommandContext<ServerCommandSource> ctx) {
        MinecraftServer server = ctx.getSource().getServer();
        ServerWorld world = server.getOverworld();

        long timeOfDay = world.getTimeOfDay();
        long dayCount = world.getDay();

        ctx.getSource().sendFeedback(() -> Text.literal("Day: " + dayCount), false);
        return true;
    }
}
