package co.volight.terrario.particles

import co.volight.terrario.core.RegParticle
import co.volight.terrario.core.makeFactory
import co.volight.terrario.items.Gel
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.particle.DefaultParticleType

object GelParticle {
     abstract class Basic(override val name: String, item: () -> Item): RegParticle<DefaultParticleType> {
        override val impl: DefaultParticleType = FabricParticleTypes.simple()
        override val factory = makeFactory { _, world, x, y, z, _, _, _ ->
            CrackParticle(world, x, y, z, ItemStack(item()))
        }
    }
    object Green: Basic("item_green_gel", { Gel.Green.impl })
    object Blue: Basic("item_blue_gel", { Gel.Blue.impl })
    object Red: Basic("item_red_gel", { Gel.Red.impl })
    object Purple: Basic("item_purple_gel", { Gel.Purple.impl })
}
