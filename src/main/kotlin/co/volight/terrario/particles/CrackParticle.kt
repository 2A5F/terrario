package co.volight.terrario.particles

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.world.ClientWorld
import net.minecraft.item.ItemStack

typealias McCrackParticle = net.minecraft.client.particle.CrackParticle

@Environment(EnvType.CLIENT)
class CrackParticle(world: ClientWorld, x: Double, y: Double, z: Double, stack: ItemStack) : McCrackParticle(world, x, y, z, stack) {
}
