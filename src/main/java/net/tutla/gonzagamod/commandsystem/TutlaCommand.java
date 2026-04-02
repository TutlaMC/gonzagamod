package net.tutla.gonzagamod.commandsystem;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public abstract class TutlaCommand {
    public final String name;
    public final String description;
    public TutlaCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    protected LiteralArgumentBuilder<ServerCommandSource> build() {
        return CommandManager.literal(name)
                .executes(ctx -> run(ctx) ? 1 : 0);
    }
    public void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(build());
    }

    public abstract boolean run(CommandContext<ServerCommandSource> ctx);
}
