@file:Environment(value = EnvType.CLIENT)

package co.volight.terrario.client.gui

import co.volight.terrario.core.f32
import co.volight.terrario.core.i64
import co.volight.terrario.core.toFixed
import co.volight.terrario.mixin.client.gui.InGameHudAccessor
import com.mojang.blaze3d.systems.RenderSystem
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tag.FluidTags
import net.minecraft.text.LiteralText
import net.minecraft.util.Util
import net.minecraft.util.math.MathHelper
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

inline val InGameHudAccessor.HealthLine get() = 10
inline val InGameHudAccessor.HealthSize get() = 10
inline val InGameHudAccessor.HealthWidth get() = 8
inline val InGameHudAccessor.HealthHeight get() = 9
inline val InGameHudAccessor.HealthYOffset get() = 3
inline val InGameHudAccessor.HealthBarLeft get() = scaledWidth - 16 - 8 - 10 * HealthWidth
inline val InGameHudAccessor.HealthBarTop get() = 8

fun <T> T.renderStatusBars(matrices: MatrixStack) where T : DrawableHelper, T : InGameHudAccessor {
    val playerEntity: PlayerEntity = this.cameraPlayer
    val i = MathHelper.ceil(playerEntity.health)
    val bl = this.heartJumpEndTick > this.ticks.i64 && (this.heartJumpEndTick - this.ticks.i64) / 3L % 2L == 1L
    val l = Util.getMeasuringTimeMs()
    if (i < this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
        this.heartJumpEndTick = (this.ticks + 20).i64
    } else if (i > this.lastHealthValue && playerEntity.timeUntilRegen > 0) {
        this.lastHealthCheckTime = l
        this.heartJumpEndTick = (this.ticks + 10).i64
    }
    if (l - this.lastHealthCheckTime > 1000L) {
        this.lastHealthValue = i
        this.renderHealthValue = i
        this.lastHealthCheckTime = l
    }
    this.lastHealthValue = i
    val j: Int = this.renderHealthValue
    this.random.setSeed((this.ticks * 312871).i64)
    val hungerManager = playerEntity.hungerManager
    val k = hungerManager.foodLevel
    val m: Int = this.scaledWidth / 2 - 91
    val n: Int = this.scaledWidth / 2 + 91
    val o: Int = this.scaledHeight - 39
    val f = maxOf(playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH).f32, j.f32, i.f32)
    val p = MathHelper.ceil(playerEntity.absorptionAmount)
    val q = MathHelper.ceil((f + p.f32) / 2.0f / 10.0f)
    val r = maxOf(10 - (q - 2), 3)
    val s = o - (q - 1) * r - 10
    var t = o - 10
    val u = playerEntity.armor
    var v = -1
    if (playerEntity.hasStatusEffect(StatusEffects.REGENERATION)) {
        v = this.ticks % MathHelper.ceil(f + 5.0f)
    }
    this.client.profiler.push("armor")
    var x: Int
    for (w in 0..9) {
        if (u > 0) {
            x = m + w * 8
            if (w * 2 + 1 < u) {
                this.drawTexture(matrices, x, s, 34, 9, 9, 9)
            }
            if (w * 2 + 1 == u) {
                this.drawTexture(matrices, x, s, 25, 9, 9, 9)
            }
            if (w * 2 + 1 > u) {
                this.drawTexture(matrices, x, s, 16, 9, 9, 9)
            }
        }
    }
    this.client.profiler.swap("health")
    this.renderHealthBar(matrices, playerEntity, r, v, f, i, j, p, bl)
    val w: LivingEntity? = this.riddenEntity
    x = this.getHeartCount(w)
    var y: Int
    var z: Int
    var aa: Int
    var ac: Int
    if (x == 0) {
        this.client.profiler.swap("food")
        y = 0
        while (y < 10) {
            z = o
            aa = 16
            var ab = 0
            if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                aa += 36
                ab = 13
            }
            if (playerEntity.hungerManager.saturationLevel <= 0.0f && this.ticks % (k * 3 + 1) == 0) {
                z = o + (this.random.nextInt(3) - 1)
            }
            ac = n - y * 8 - 9
            this.drawTexture(matrices, ac, z, 16 + ab * 9, 27, 9, 9)
            if (y * 2 + 1 < k) {
                this.drawTexture(matrices, ac, z, aa + 36, 27, 9, 9)
            }
            if (y * 2 + 1 == k) {
                this.drawTexture(matrices, ac, z, aa + 45, 27, 9, 9)
            }
            ++y
        }
        t -= 10
    }
    this.client.profiler.swap("air")
    y = playerEntity.maxAir
    z = minOf(playerEntity.air, y)
    if (playerEntity.isSubmergedIn(FluidTags.WATER) || z < y) {
        aa = this.getHeartRows(x) - 1
        t -= aa * 10
        val ab = MathHelper.ceil((z - 2).toDouble() * 10.0 / y.toDouble())
        ac = MathHelper.ceil(z.toDouble() * 10.0 / y.toDouble()) - ab
        for (ad in 0 until ab + ac) {
            if (ad < ab) {
                this.drawTexture(matrices, n - ad * 8 - 9, t, 16, 18, 9, 9)
            } else {
                this.drawTexture(matrices, n - ad * 8 - 9, t, 25, 18, 9, 9)
            }
        }
    }
    this.client.profiler.pop()
}

fun <T> T.renderHealthBarText(matrices: MatrixStack, tickDelta: Float, ci: CallbackInfo) where T : InGameHudAccessor {
    if (client.options.hudHidden) return
    if (!client.interactionManager!!.hasStatusBars()) return
    val player: PlayerEntity = cameraPlayer
    val lastHealth = player.health
    val maxHealth = maxOf(player.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH).f32, renderHealthValue.f32, lastHealth)
    client.profiler.push("TerrarioHealthText")
    val text = LiteralText(lastHealth.toFixed(2)).append(" / ").append(maxHealth.toFixed(2))
    matrices.push()
    val width = textRenderer.getWidth(text)
    textRenderer.drawWithShadow(matrices, text, (HealthBarLeft - 8 - width).f32, 9f, 0xFFFFFF)
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
                val rat = heartHealth.f32 / HealthSize
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
                val rat = heartHealth.f32 / HealthSize
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
