package net.tutla.gonzagamod.commandsystem.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.commandsystem.TutlaCommand;

import java.util.Objects;

public class TpCommand extends TutlaCommand {
    public TpCommand(){
        super("tpa","do /tpa");
    }

    @Override
    protected LiteralArgumentBuilder<ServerCommandSource> build() {
        return CommandManager.literal(name)
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(ctx -> {
                            run(ctx);
                            return 1;
                        })
                );
    }

    public boolean run(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(() -> Text.literal(
                "sum mf named " + Objects.requireNonNull(ctx.getSource().getPlayer()).getName().getString() + " thought he could /tp on ts. dumbass"
        ), false);
        return true;
    }
}
