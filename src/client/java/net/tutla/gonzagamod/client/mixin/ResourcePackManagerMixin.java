package net.tutla.gonzagamod.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.text.Text;
import net.tutla.gonzagamod.client.ModChecker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.function.Consumer;

@Mixin(PackScreen.class)
public class ResourcePackManagerMixin {
    @Inject(method = "refresh", at = @At("TAIL"))
    public void onEnable(CallbackInfo ci){
        ModChecker.doCheck(MinecraftClient.getInstance());
    }
}
