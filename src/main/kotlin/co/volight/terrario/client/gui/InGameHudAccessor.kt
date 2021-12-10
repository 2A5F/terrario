@file:Environment(value = EnvType.CLIENT)

package co.volight.terrario.client.gui

import co.volight.terrario.mixin.client.gui.InGameHudAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.font.TextRenderer
import net.minecraft.entity.player.PlayerEntity

val InGameHudAccessor.cameraPlayer: PlayerEntity get() = callGetCameraPlayer()

val InGameHudAccessor.textRenderer: TextRenderer get()  = callGetTextRenderer()
