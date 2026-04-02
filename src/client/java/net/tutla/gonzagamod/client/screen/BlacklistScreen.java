package net.tutla.gonzagamod.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.client.GonzagamodClient;

import java.util.List;

public class BlacklistScreen extends Screen {
    private final List<String> allBlacklisted;

    public BlacklistScreen(List<String> allBlacklisted) {
        super(Text.literal("Blacklisted Mods Detected"));
        this.allBlacklisted = allBlacklisted;
    }

    @Override
    public boolean shouldCloseOnEsc() { return false; }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("okay daddy tutla"), button -> {
                    MinecraftClient.getInstance().close();
                })
                .dimensions(this.width / 2 - 50, this.height - 50, 100, 20)
                .build());
        GonzagamodClient.startDoShutdown();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xFF000000);

        context.drawText(this.textRenderer, "Blacklisted Mods / ResourcePacks Detected", 50, 20, 0xFFFF5555, false);
        context.drawText(this.textRenderer, "Remove these and restart:", 50, 35, 0xFFAAAAAA, false);

        int y = 55;
        for (String entry : allBlacklisted) {
            context.drawText(this.textRenderer, "- " + entry, 50, y, 0xFFFF5555, false);
            y += 12;
        }

        super.render(context, mouseX, mouseY, delta);
    }
}