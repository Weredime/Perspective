package io.github.weredime.mods.perspective.mixin;

import java.util.List;

import io.github.weredime.mods.perspective.PerspectiveMod;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugHud.class)
public class DebugHudMixin {
    @Inject(at = @At("RETURN"), method = "getLeftText")
    protected void getLeftText(CallbackInfoReturnable<List<String>> ci) {
        if (PerspectiveMod.freelookAllowedHere) {
            ci.getReturnValue().add(String.format("[Perspective] FE: %s | TSFP: %d | KP: %s", PerspectiveMod.freelookEnabled, PerspectiveMod.ticksWhilePressed, PerspectiveMod.keyPressed));
        } else {
            ci.getReturnValue().add("[Perspective] FE: false | TSFP: -1 | KP: false");
        }
    }
}
