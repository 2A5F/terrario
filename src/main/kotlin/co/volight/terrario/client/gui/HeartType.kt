package co.volight.terrario.client.gui

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity

@Environment(value = EnvType.CLIENT)
enum class HeartType(val textureIndex: Int, val hasBlinkingTexture: Boolean) {
    CONTAINER(0, false), NORMAL(2, true), POISIONED(4, true),
    WITHERED(6, true), ABSORBING(8, false), FROZEN(9, false);

    fun getU(halfHeart: Boolean, blinking: Boolean): Int {
        val i = if (this == CONTAINER) {
            if (blinking) 1 else 0
        } else {
            val j = if (halfHeart) 1 else 0
            val k = if (hasBlinkingTexture && blinking) 2 else 0
            j + k
        }
        return 16 + (textureIndex * 2 + i) * 9
    }

    companion object {
        @JvmStatic
        fun fromPlayerState(player: PlayerEntity): HeartType {
            return if (player.hasStatusEffect(StatusEffects.POISON)) POISIONED
            else if (player.hasStatusEffect(StatusEffects.WITHER)) WITHERED
            else if (player.isFreezing) FROZEN else NORMAL
        }
    }
}