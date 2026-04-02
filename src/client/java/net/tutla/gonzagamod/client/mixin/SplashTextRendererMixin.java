package net.tutla.gonzagamod.client.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
        List<String> splashes = List.of(
                "clayton made the video",
                "bros on the amra list",
                "jax loves shaza",
                "is samvit studying for NEET?",
                "clifton, dont goon to this",
                "ehan stop cheating",
                "darrien hacked me",
                "who has a gun?",
                "vulkan better",
                "#bringbackclayton",
                "screw you big p"
        );
        String chosen = splashes.get(new Random().nextInt(splashes.size()));
        this.text = Text.literal(chosen);
    }
}
