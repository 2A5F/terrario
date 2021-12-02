package co.volight.terrario

import org.apache.logging.log4j.LogManager
import co.volight.terrario.Tr.logName
import co.volight.terrario.Tr.logger
import co.volight.terrario.Tr.blocks
import co.volight.terrario.Tr.entities
import co.volight.terrario.Tr.livingEntities
import co.volight.terrario.Tr.items
import co.volight.terrario.Tr.particles
import co.volight.terrario.blocks.OreBlock
import co.volight.terrario.core.*
import co.volight.terrario.entities.Slime
import co.volight.terrario.items.Gel
import co.volight.terrario.items.Ore
import co.volight.terrario.items.SpawnEgg
import co.volight.terrario.items.TrLogo
import co.volight.terrario.particles.GelParticle
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.particle.ParticleEffect

object Tr : Idspace {
    const val id = "tr"
    const val logName = "[Terrario]"
    val logger = LogManager.getLogger()!!

    val blocks = listOf<Reg<out Block>>(
        OreBlock.Tin,
        OreBlock.Lead,
        OreBlock.Silver,
        OreBlock.Tungsten,
        OreBlock.Platinum,
    )
    val items = listOf<Reg<out Item>>(
        TrLogo,
        Ore.Tin,
        Ore.Lead,
        Ore.Silver,
        Ore.Tungsten,
        Ore.Platinum,
        Gel.Green,
        Gel.Blue,
        Gel.Red,
        Gel.Purple,
        SpawnEgg.GreenSlime,
        SpawnEgg.BlueSlime,
        SpawnEgg.RedSlime,
        SpawnEgg.PurpleSlime,
    )
    val entities = listOf<RegEntity<out Entity>>()
    val livingEntities = listOf<RegLivingEntity<out LivingEntity>>(
        Slime.Green,
        Slime.Blue,
        Slime.Red,
        Slime.Purple,
    )
    val particles = listOf<RegParticle<out ParticleEffect>>(
        GelParticle.Green,
        GelParticle.Blue,
        GelParticle.Red,
        GelParticle.Purple
    )
}

@Suppress("unused")
fun init() {
    initBlocks()
    logger.info("$logName Blocks initialized")

    initItems()
    logger.info("$logName Items initialized")

    initEntityTypes()
    logger.info("$logName Entity types initialized")

    initEntityAttrs()
    logger.info("$logName Entity attrs initialized")

    initParticles()
    logger.info("$logName Particles initialized")
}

fun initBlocks() {
    for (it in blocks) {
        it.regBlock()
    }
}

fun initItems() {
    for (it in blocks) {
        it.regBlockItem()
    }
    for (it in items) {
        it.regItem()
    }
}

fun initEntityTypes() {
    for (it in entities) {
        it.regEntityType()
    }
    for (it in livingEntities) {
        it.regEntityType()
    }
}

fun initEntityAttrs() {
    for (it in livingEntities) {
        it.regEntityAttr()
    }
}

fun initParticles() {
    for (it in particles) {
        it.regParticle()
    }
}

@Suppress("unused")
@Environment(EnvType.CLIENT)
fun initClient() {
    initBlockLayers()
    logger.info("$logName Block layers initialized")

    initBlockColors()
    logger.info("$logName Block colors initialized")

    initEntityRenders()
    logger.info("$logName Entity renders initialized")

    initParticleFactories()
    logger.info("$logName Particle factories initialized")
}

@Environment(EnvType.CLIENT)
fun initBlockLayers() {

}

@Environment(EnvType.CLIENT)
fun initBlockColors() {

}

@Environment(EnvType.CLIENT)
fun initEntityRenders() {
    for (it in entities) {
        it.regEntityRender()
    }
    for (it in livingEntities) {
        it.regEntityRender()
    }
}

fun initParticleFactories() {
    for (it in particles) {
        it.regParticleFactory()
    }
}
