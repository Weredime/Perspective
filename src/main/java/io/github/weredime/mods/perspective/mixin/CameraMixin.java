package io.github.weredime.mods.perspective.mixin;


import io.github.weredime.mods.perspective.CamOverridedEntity;
import io.github.weredime.mods.perspective.PerspectiveMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
@Mixin(Camera.class)
public abstract class CameraMixin {
    public boolean hasLoaded = false;
    @Shadow
    public abstract void setRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 0, shift = At.Shift.AFTER))
    public void lockRotation(BlockView focusedBlock, Entity cameraEntity, boolean isThirdPerson, boolean isFrontFacing, float f, CallbackInfo ci) {
        if (PerspectiveMod.freelookEnabled && cameraEntity instanceof ClientPlayerEntity) {
            CamOverridedEntity cameraEnt = (CamOverridedEntity) cameraEntity;
            if (!hasLoaded && MinecraftClient.getInstance().player != null) {
                cameraEnt.setCameraPitch(MinecraftClient.getInstance().player.getPitch());
                cameraEnt.setCameraYaw(MinecraftClient.getInstance().player.getYaw());
                hasLoaded = true;
            }
            this.setRotation(cameraEnt.getCameraYaw(), cameraEnt.getCameraPitch());

        }
        if (!PerspectiveMod.freelookEnabled && cameraEntity instanceof ClientPlayerEntity) {
            hasLoaded = false;
        }
    }

}