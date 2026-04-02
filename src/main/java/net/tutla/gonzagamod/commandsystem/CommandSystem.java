package net.tutla.gonzagamod.commandsystem;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.tutla.gonzagamod.commandsystem.command.DayCommand;
import net.tutla.gonzagamod.commandsystem.command.TpCommand;

import java.util.List;

public class CommandSystem {
    private final List<TutlaCommand> commands = List.of(
            new TpCommand(),
            new DayCommand()
    );
    public void registerAll(CommandDispatcher<ServerCommandSource> dispatcher){
        for (TutlaCommand cmd : commands){
            cmd.register(dispatcher);
        }
    }
}
