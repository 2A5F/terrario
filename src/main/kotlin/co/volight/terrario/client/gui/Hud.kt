@file:Environment(value = EnvType.CLIENT)

package co.volight.terrario.client.gui

import co.volight.terrario.core.toFixed
import co.volight.terrario.mixin.client.gui.InGameHudAccessor
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.text.LiteralText
import net.minecraft.util.math.MathHelper
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

inline val InGameHudAccessor.HealthLine get() = 10
inline val InGameHudAccessor.HealthSize get() = 10
inline val InGameHudAccessor.HealthWidth get() = 8
inline val InGameHudAccessor.HealthHeight get() = 9
inline val InGameHudAccessor.HealthYOffset get() = 3
inline val InGameHudAccessor.HealthBarLeft get() = scaledWidth - 16 - 8 - 10 * HealthWidth
inline val InGameHudAccessor.HealthBarTop get() = 8

fun <T> T.renderHealthBarText(matrices: MatrixStack, tickDelta: Float, ci: CallbackInfo) where T : InGameHudAccessor {
    if (client.options.hudHidden) return
    if (!client.interactionManager!!.hasStatusBars()) return
    val player: PlayerEntity = cameraPlayer
    val lastHealth = player.health
    val maxHealth = maxOf(player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH).toFloat(), renderHealthValue.toFloat(), lastHealth)
    client.profiler.push("TerrarioHealthText")
    val text = LiteralText(lastHealth.toFixed(2)).append(" / ").append(maxHealth.toFixed(2))
    matrices.push()
    val width = textRenderer.getWidth(text)
    textRenderer.drawWithShadow(matrices, text, (HealthBarLeft - 8 - width).toFloat(), 9f, 0xFFFFFF)
    matrices.pop()
    client.profiler.pop()
}

fun <T> T.renderHealthBar(
    matrices: MatrixStack, player: PlayerEntity, lines: Int, regeneratingHeartIndex: Int, maxHealth: Float, lastHealth: Int, health: Int,
    absorption: Int, blinking: Boolean
) where T : DrawableHelper, T : InGameHudAccessor {
    val left = HealthBarLeft
    val top = HealthBarTop
    val heartType = HeartType.fromPlayerState(player)
    val hardcoreVOffset = 9 * if (player.world.levelProperties.isHardcore) 5 else 0
    val redHearts = MathHelper.ceil(maxHealth.toDouble() / HealthSize)
    val goldHearts = MathHelper.ceil(absorption.toDouble() / HealthSize)
    var line = 0
    for (x in 0 until redHearts) {
        val hx = left + (x % 10) * HealthWidth
        line = x / HealthLine
        var hy = top + line * (HealthHeight + HealthYOffset)
        val upperLine = (x + 1) * HealthSize
        val lowerLine = x * HealthSize
        if (lastHealth + absorption <= 4) {
            hy += random.nextInt(2)
        }
        if (x == regeneratingHeartIndex) {
            hy -= 2
        }
//        if (upperLine < maxHealth) {
//            drawHeart(matrices, HeartType.CONTAINER, hx, hy, hardcoreVOffset, blinking, false)
//        } else {
//            val heartHealth = maxHealth - lowerLine
//            val rat = heartHealth / HealthSize
//            matrices.push()
//            val transOffset = 0.5 * (HealthWidth + 1) - rat * (HealthWidth + 1) * 0.5
//            matrices.translate(hx.toDouble() + transOffset, hy.toDouble() + transOffset, 0.0)
//            matrices.scale(rat, rat, rat)
//            drawHeart(matrices, HeartType.CONTAINER, 0, 0, hardcoreVOffset, blinking, false)
//            matrices.pop()
//        }
        RenderSystem.enableBlend()
        if (upperLine < health) {
            drawHeart(matrices, heartType, hx, hy, hardcoreVOffset, true, false)
        } else {
            val heartHealth = health - lowerLine
            if (heartHealth > 0) {
                val rat = heartHealth.toFloat() / HealthSize
                matrices.push()
                val transOffset = 0.5 * (HealthWidth + 1) - rat * (HealthWidth + 1) * 0.5
                matrices.translate(hx.toDouble() + transOffset, hy.toDouble() + transOffset, 0.0)
                matrices.scale(rat, rat, rat)
                drawHeart(matrices, heartType, 0, 0, hardcoreVOffset, true, false)
                matrices.pop()
            }
        }
        RenderSystem.disableBlend()
        if (upperLine < lastHealth) {
            drawHeart(matrices, HeartType.CONTAINER, hx, hy, hardcoreVOffset, blinking, false)
            drawHeart(matrices, heartType, hx, hy, hardcoreVOffset, false, false)
        } else {
            val heartHealth = lastHealth - lowerLine
            if (heartHealth > 0) {
                val rat = heartHealth.toFloat() / HealthSize
                matrices.push()
                val transOffset = 0.5 * (HealthWidth + 1) - rat * (HealthWidth + 1) * 0.5
                matrices.translate(hx.toDouble() + transOffset, hy.toDouble() + transOffset, 0.0)
                matrices.scale(rat, rat, rat)
                drawHeart(matrices, HeartType.CONTAINER, 0, 0, hardcoreVOffset, blinking, false)
                drawHeart(matrices, heartType, 0, 0, hardcoreVOffset, false, false)
                matrices.pop()
            }
        }
    }
//    for (m in redHearts + goldHearts - 1 downTo 0) {
//        var s: Int = 0
//        val n = m / 10
//        val o = m % 10
//        val p = x + o * 8
//        var q = y - n * lines
//        if (lastHealth + absorption <= 4) {
//            q += random.nextInt(2)
//        }
//        if (m < redHearts && m == regeneratingHeartIndex) {
//            q -= 2
//        }
//        drawHeart(matrices, HeartType.CONTAINER, p, q, hardcoreVOffset, blinking, false)
//        val r = m * 2
//        val bl: Boolean = m >= redHearts
//        if (bl && r - l.also { s = it } < absorption) {
//            val bl22 = s + 1 == absorption
//            drawHeart(matrices, if (heartType == HeartType.WITHERED) heartType else HeartType.ABSORBING, p, q, hardcoreVOffset, false, bl22)
//        }
//        if (blinking && r < health) {
//            s = if (r + 1 == health) 1 else 0
//            drawHeart(matrices, heartType, p, q, hardcoreVOffset, true, s != 0)
//        }
//        if (r >= lastHealth) continue
//        s = if (r + 1 == lastHealth) 1 else 0
//        drawHeart(matrices, heartType, p, q, hardcoreVOffset, false, s != 0)
//    }
}

fun <T : DrawableHelper> T.drawHeart(matrices: MatrixStack?, type: HeartType, x: Int, y: Int, v: Int, blinking: Boolean, halfHeart: Boolean) {
    drawTexture(matrices, x, y, type.getU(halfHeart, blinking), v, 9, 9)
}
