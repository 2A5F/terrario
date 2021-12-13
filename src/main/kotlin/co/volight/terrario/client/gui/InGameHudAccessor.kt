@file:Environment(value = EnvType.CLIENT)

package co.volight.terrario.client.gui

import co.volight.terrario.mixin.client.gui.InGameHudAccessor
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.font.TextRenderer
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity

val InGameHudAccessor.cameraPlayer: PlayerEntity get() = callGetCameraPlayer()

val InGameHudAccessor.textRenderer: TextRenderer get()  = callGetTextRenderer()

val InGameHudAccessor.riddenEntity: LivingEntity? get() = callGetRiddenEntity()

fun InGameHudAccessor.getHeartCount(entity: LivingEntity?) = callGetHeartCount(entity)

fun InGameHudAccessor.getHeartRows(heartCount: Int) = callGetHeartRows(heartCount)
