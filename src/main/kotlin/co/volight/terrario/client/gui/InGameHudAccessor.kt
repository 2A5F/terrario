package co.volight.terrario.client.gui

import co.volight.terrario.mixin.InGameHudAccessor
import net.minecraft.client.font.TextRenderer
import net.minecraft.entity.player.PlayerEntity

val InGameHudAccessor.cameraPlayer: PlayerEntity get() = callGetCameraPlayer()

val InGameHudAccessor.textRenderer: TextRenderer get()  = callGetTextRenderer()
