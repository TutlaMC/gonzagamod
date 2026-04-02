package net.tutla.gonzagamod.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.downloader.AutoUpdater;

public class UpdateScreen extends Screen {
    public UpdateScreen() {
        super(Text.literal("Update Downloaded"));
    }

    @Override
    public boolean shouldCloseOnEsc() { return false; }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, this.width, this.height, 0xFF000000);
        context.drawText(textRenderer, "Mod updated! Please restart your game.", 50, 50, 0xFFFFFFFF, false);
        context.drawText(textRenderer, "Close and reopen Minecraft to apply.", 50, 65, 0xFFAAAAAA, false);
        context.drawText(textRenderer, "Changelog: ", 50, 95, 0xFFAAAAAA, false);
        context.drawText(textRenderer, AutoUpdater.updateContent, 50, 110, 0xFFAAAAAA, false);
        super.render(context, mouseX, mouseY, delta);
    }
}