package co.volight.terrario


import co.volight.terrario.Tr.biomeModifies
import org.apache.logging.log4j.LogManager
import co.volight.terrario.Tr.logName
import co.volight.terrario.Tr.logger
import co.volight.terrario.Tr.blocks
import co.volight.terrario.Tr.configuredFeatures
import co.volight.terrario.Tr.entities
import co.volight.terrario.Tr.livingEntities
import co.volight.terrario.Tr.items
import co.volight.terrario.Tr.particles
import co.volight.terrario.Tr.placedFeatures
import co.volight.terrario.biome.modify.OreBiomeModify
import co.volight.terrario.blocks.OreBlock
import co.volight.terrario.core.*
import co.volight.terrario.entities.SlimeEntity
import co.volight.terrario.feature.OreFeature
import co.volight.terrario.items.GelItem
import co.volight.terrario.items.OreItem
import co.volight.terrario.items.SpawnEggItem
import co.volight.terrario.items.TrLogo
import co.volight.terrario.particles.GelParticle
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.Block
import net.minecraft.entity.Entity
import net.minecraft.entity.LivingEntity
import net.minecraft.item.Item
import net.minecraft.particle.ParticleEffect
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.PlacedFeature

object Tr : Idspace {
    const val id = "tr"
    const val logName = "[Terrario]"
    val logger = LogManager.getLogger()!!

    val blocks = listOf<Reg<out Block>>(
        OreBlock.Tin,
        OreBlock.Tin.Deepslate,
        OreBlock.Lead,
        OreBlock.Lead.Deepslate,
        OreBlock.Silver,
        OreBlock.Silver.Deepslate,
        OreBlock.Tungsten,
        OreBlock.Tungsten.Deepslate,
        OreBlock.Platinum,
        OreBlock.Platinum.Deepslate,
    )
    val items = listOf<Reg<out Item>>(
        TrLogo,
        OreItem.Tin,
        OreItem.Lead,
        OreItem.Silver,
        OreItem.Tungsten,
        OreItem.Platinum,
        GelItem.Green,
        GelItem.Blue,
        GelItem.Red,
        GelItem.Purple,
        SpawnEggItem.GreenSlime,
        SpawnEggItem.BlueSlime,
        SpawnEggItem.RedSlime,
        SpawnEggItem.PurpleSlime,
    )
    val entities = listOf<RegEntity<out Entity>>()
    val livingEntities = listOf<RegLivingEntity<out LivingEntity>>(
        SlimeEntity.Green,
        SlimeEntity.Blue,
        SlimeEntity.Red,
        SlimeEntity.Purple,
    )
    val particles = listOf<RegParticle<out ParticleEffect>>(
        GelParticle.Green,
        GelParticle.Blue,
        GelParticle.Red,
        GelParticle.Purple,
    )
    val configuredFeatures = listOf<Reg<out ConfiguredFeature<*, *>>>(
        OreFeature.Tin,
        OreFeature.Lead,
        OreFeature.Silver,
        OreFeature.Tungsten,
        OreFeature.Platinum,
    )
    val placedFeatures = listOf<Reg<out PlacedFeature>>(
        OreFeature.Tin.Normal,
        OreFeature.Lead.Upper,
        OreFeature.Lead.Middle,
        OreFeature.Silver.Normal,
        OreFeature.Tungsten.Normal,
        OreFeature.Platinum.Normal,
    )
    val biomeModifies = listOf(
        OreBiomeModify.Tin,
        OreBiomeModify.Lead,
        OreBiomeModify.Silver,
        OreBiomeModify.Tungsten,
        OreBiomeModify.Platinum,
    )
}

// clear stone command
// /fill ~-8 ~-64 ~-8 ~8 ~ ~8 minecraft:air replace #tr:clear_stone

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

    initConfiguredFeatures()
    logger.info("$logName Configured Features initialized")

    initPlacedFeatures()
    logger.info("$logName Placed Features initialized")

    initBiomeModifies()
    logger.info("$logName Biome Modifies initialized")
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

fun initConfiguredFeatures() {
    for (it in configuredFeatures) {
        it.regConfiguredFeature()
    }
}

fun initPlacedFeatures() {
    for (it in placedFeatures) {
        it.regPlacedFeature()
    }
}

fun initBiomeModifies() {
    for (it in biomeModifies) {
        it.reg()
    }
}

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
