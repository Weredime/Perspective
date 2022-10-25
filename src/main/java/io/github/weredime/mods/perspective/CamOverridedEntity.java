package io.github.weredime.mods.perspective;

import net.minecraft.world.entity.EntityLike;

public interface CamOverridedEntity extends EntityLike {
    float getCameraPitch();
    float getCameraYaw();

    void setCameraPitch(float pitch);
    void setCameraYaw(float yaw);
}
