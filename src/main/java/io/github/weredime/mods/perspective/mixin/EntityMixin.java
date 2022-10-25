package io.github.weredime.mods.perspective.mixin;

import io.github.weredime.mods.perspective.CamOverridedEntity;
import io.github.weredime.mods.perspective.PerspectiveMod;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements CamOverridedEntity {
    @Shadow
    private float yaw;
    @Shadow
    private float pitch;
    @Unique
    private float cameraPitch;
    @Unique
    private float cameraYaw;

    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    public void changeCamLookDirection(double xDelta, double yDelta, CallbackInfo ci) {
        if (PerspectiveMod.freelookEnabled) {
            double pitchDelta = (yDelta * 0.15);
            double yawDelta = (xDelta * 0.15);

            this.cameraPitch = MathHelper.clamp(this.cameraPitch + (float) pitchDelta, -90.0f, 90.0f);
            this.cameraYaw += (float) yawDelta;

            ci.cancel();
        }
    }
    @Unique
    public float getCameraPitch() {
        return this.cameraPitch;
    }

    @Unique
    public float getCameraYaw() {
        return this.cameraYaw;
    }

    @Unique
    public void setCameraPitch(float pitch) {
        this.cameraPitch = pitch;
    }

    @Unique
    public void setCameraYaw(float yaw) {
        this.cameraYaw = yaw;
    }


    @Unique
    public float getYaw() {
        return this.yaw;
    }

    @Unique
    public float getPitch() {
        return this.pitch;
    }
}
