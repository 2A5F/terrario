package co.volight.terrario.mixin.client.gui;

import co.volight.terrario.client.gui.HudKt;
import co.volight.terrario.mixin.InGameHudAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(value = EnvType.CLIENT)
public abstract class MixinInGameHud extends DrawableHelper implements InGameHudAccessor {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
            at = @At("RETURN"))
    void renderHealthBarText(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        HudKt.renderHealthBarText(this, matrices, tickDelta, ci);
    }

    @Redirect(method = "renderStatusBars",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderHealthBar(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/entity/player/PlayerEntity;IIIIFIIIZ)V"))
    void renderHealthBar(InGameHud instance, MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        HudKt.renderHealthBar(this, matrices, player, lines, regeneratingHeartIndex, maxHealth, lastHealth, health, absorption, blinking);
    }

}
