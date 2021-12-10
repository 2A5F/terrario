package co.volight.terrario.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {
    @Accessor
    Random getRandom();

    @Accessor
    MinecraftClient getClient();

    @Invoker
    PlayerEntity callGetCameraPlayer();

    @Invoker
    TextRenderer callGetTextRenderer();

    @Accessor
    int getRenderHealthValue();

    @Accessor
    int getScaledWidth();

    @Accessor
    int getScaledHeight();
}
