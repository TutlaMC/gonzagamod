package net.tutla.gonzagamod.client.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {
    @Mutable
    @Shadow
    @Final
    private Text text;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void onInit(Text originalText, CallbackInfo ci) {
        // skip the static constants (MERRY_X_MAS etc) which have specific text
        // only replace randomly generated ones
        List<String> splashes = List.of(
                "clayton made the video",
                "bros on the amra list",
                "jax"
        );
        String chosen = splashes.get(new Random().nextInt(splashes.size()));
        this.text = Text.literal(chosen);
    }
}
