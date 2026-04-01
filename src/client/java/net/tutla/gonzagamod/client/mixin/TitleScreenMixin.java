package net.tutla.gonzagamod.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.client.ModChecker;
import net.tutla.gonzagamod.client.screen.BlacklistScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Shadow
    protected abstract int addNormalWidgets(int y, int spacingY);

    @Unique
    private static boolean checked = false;

    @Inject(method = "init", at = @At("HEAD"))
    private void onInit(CallbackInfo ci) {
        if (checked) return;
        checked = true;

        MinecraftClient client = MinecraftClient.getInstance();
        ModChecker.doCheck(client);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void tailOnInit(CallbackInfo ci) {
        int y = this.height / 4 + 48 - 24; // one slot above singleplayer

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Join SMP"),
                button -> {
                    ServerAddress address = ServerAddress.parse("156.67.218.80");
                    ServerInfo info = new ServerInfo("Server", "156.67.218.80", ServerInfo.ServerType.OTHER);
                    ConnectScreen.connect(this, this.client, address, info, false, null);
                }
        ).dimensions(this.width / 2 - 100, y, 200, 20).build());
    }

}