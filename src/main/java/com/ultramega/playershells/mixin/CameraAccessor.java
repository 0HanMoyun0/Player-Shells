package com.ultramega.playershells.mixin;

import net.minecraft.client.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Accessor("detached")
    void playershells$setDetached(boolean detached);

    @Invoker("setRotation")
    void playershells$setRotation(float yaw, float pitch);

    @Invoker("setPosition")
    void playershells$setPosition(double x, double y, double z);
}
