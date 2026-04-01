package net.tutla.gonzagamod.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.client.AutoUpdater;
import net.tutla.gonzagamod.client.ModChecker;
import net.tutla.gonzagamod.client.screen.BlacklistScreen;
import net.tutla.gonzagamod.client.screen.UpdateScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin() {
        super(Text.literal(""));
    }

    // Push all normal buttons down to make room
    @ModifyArg(method = "addNormalWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 0), index = 1)
    private int pushSingleplayerDown(int y) {
        return y + 24;
    }

    @ModifyArg(method = "addNormalWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 1), index = 1)
    private int pushMultiplayerDown(int y) {
        return y + 24;
    }

    @ModifyArg(method = "addNormalWidgets", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 2), index = 1)
    private int pushOnlineDown(int y) {
        return y + 24;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 0), index = 1)
    private int pushOptionsDown(int y) {
        return y + 24;
    }

    @ModifyArg(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;dimensions(IIII)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;", ordinal = 1), index = 1)
    private int pushQuitDown(int y) {
        return y + 24;
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        int y = this.height / 4 + 48; // same base as TitleScreen, top slot

        this.addDrawableChild(ButtonWidget.builder(
                Text.literal("Join SMP"),
                button -> {
                    ServerAddress address = ServerAddress.parse("156.67.218.80");
                    ServerInfo info = new ServerInfo("Server", "156.67.218.80", ServerInfo.ServerType.OTHER);
                    ConnectScreen.connect(this, this.client, address, info, false, null);
                }
        ).dimensions(this.width / 2 - 100, y, 200, 20).build());
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void render(DrawContext context, int mouseX, int mouseY, float deltaTicks, CallbackInfo ci){
        AutoUpdater.showUpdateScreen();
    }

}