package io.github.weredime.mods.perspective;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.InputUtil;

import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PerspectiveMod implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("perspective");
    public static KeyBinding keyBinding;
    public static boolean freelookAllowedHere = false;
    public static boolean keyPressed = false;
    public static int ticksWhilePressed = 0;
    public static boolean freelookEnabled = false;
    private boolean toggleFreelookEnabled = false;
    private static Perspective lastPerspective;
    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        AutoConfig.getConfigHolder(ModConfig.class).registerSaveListener((manager, data) -> {
            if (!data.freelookEnabled && freelookEnabled) {
                freelookEnabled = false;
                ticksWhilePressed = 0;
                toggleFreelookEnabled = false;
                keyPressed = false;
            }
            return ActionResult.SUCCESS;
        });
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.perspective.toggle",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.perspective.keybinds"
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                freelookEnabled = false; // Force-disable feature if client is disabled
                freelookAllowedHere = false; // Notify F3 menu that we are gone for good
                toggleFreelookEnabled = false;
                return;
            }
            if (!config.freelookEnabled) return;
            if (keyBinding.wasPressed()) {
                keyPressed = true;
                freelookEnabled = true;
                ticksWhilePressed++;
                lastPerspective = client.options.getPerspective();
                client.options.setPerspective(config.initialPerspective.toMCEnum());
            } else if (keyPressed && ticksWhilePressed <= 3 && !toggleFreelookEnabled) {
                freelookEnabled = true;
                toggleFreelookEnabled = true;
                keyPressed = false;
                ticksWhilePressed = 0;
            } else if (keyPressed && ticksWhilePressed <= 3 && toggleFreelookEnabled) {
                freelookEnabled = false;
                toggleFreelookEnabled = false;
                keyPressed = false;
                ticksWhilePressed = 0;
                client.options.setPerspective(lastPerspective);
            } else if (keyPressed) {
                keyPressed = false;
                ticksWhilePressed = 0;
                client.options.setPerspective(lastPerspective);
            }
        });
    }
}
