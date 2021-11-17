package co.volight.terrario.core

import co.volight.terrario.Tr
import co.volight.terrario.itemgroups.mainGroup
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.client.particle.Particle
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.stat.StatFormatter
import net.minecraft.stat.Stats
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

interface Idspace {
    val idspace: String get() = Tr.id
}

interface Nameable {
    val name: String
}

interface Instance<T> : Idspace, Nameable {
    val impl: T
}

interface Reg<T>: Instance<T>, Idspace, Nameable

fun interface JoinItemGroup {
    fun joinItemGroup(settings: Item.Settings)
}

interface WithItemGroup: JoinItemGroup {
    override fun joinItemGroup(settings: Item.Settings) {
        settings.group(mainGroup)
    }
}

fun <S> S.settings(): Item.Settings where S: Instance<out Item> {
    return FabricItemSettings()
}

@JvmName("settingsWithItemGroup")
fun <S> S.settings(): Item.Settings where S: Instance<out Item>, S: JoinItemGroup {
    return FabricItemSettings().also(::joinItemGroup)
}

fun <S> S.regBlock() where S: Instance<out Block>, S: Idspace, S: Nameable {
    Registry.register(Registry.BLOCK, Identifier(idspace, name), impl)
}

fun <S> S.regItem() where S: Instance<out Item>, S: Idspace, S: Nameable {
    Registry.register(Registry.ITEM, Identifier(idspace, name), impl)
}

fun <T> makeBlockItem(block: T): BlockItem where T: Block {
    if (block is JoinItemGroup) return makeBlockItem(block)
    return BlockItem(block, Item.Settings())
}

fun <T> JoinItemGroup.makeBlockItem(block: T): BlockItem where T: Block {
    return BlockItem(block, Item.Settings().also(::joinItemGroup))
}

fun <S> S.regBlockItem() where S: Instance<out Block>, S: Idspace, S: Nameable {
    if (this is JoinItemGroup) return regBlockItem()
    Registry.register(Registry.ITEM, Identifier(idspace, name), makeBlockItem(impl))
}

@JvmName("regBlockItemWithItemGroup")
fun <S> S.regBlockItem() where S: Instance<out Block>, S: Idspace, S: Nameable, S: JoinItemGroup {
    Registry.register(Registry.ITEM, Identifier(idspace, name), makeBlockItem(impl))
}

fun regStat(name: String, statFormatter: StatFormatter = StatFormatter.DEFAULT): Identifier {
    return Identifier(Tr.id, name).also {
        Registry.register(Registry.CUSTOM_STAT, it, it)
        Stats.CUSTOM.getOrCreateStat(it, statFormatter)
    }
}

fun <T : ScreenHandler> regScreenHandler(name: String, factory: ScreenHandlerRegistry.SimpleClientHandlerFactory<T>): ScreenHandlerType<T> {
    return ScreenHandlerRegistry.registerSimple(Identifier(Tr.id, name), factory)
}

interface ParticleReg<T : ParticleEffect>: Nameable, Idspace {
    val type: ParticleType<T>

    @Environment(EnvType.CLIENT)
    fun createParticle(
        parameters: T, world: ClientWorld, x: Double, y: Double, z: Double, vx: Double, vy: Double, vz: Double
    ): Particle

    fun regType() {
        Registry.register(Registry.PARTICLE_TYPE, Identifier(idspace, name), type)
    }
    @Environment(EnvType.CLIENT)
    fun regFactory() {
        ParticleFactoryRegistry.getInstance().register(type, this::createParticle)
    }
}

interface RegEntity<T : Entity> : Nameable, Idspace {
    val type: EntityType<T>

    fun regType() {
        Registry.register(Registry.ENTITY_TYPE, Identifier(idspace, name), type)
    }

    @Environment(EnvType.CLIENT)
    fun render(context: EntityRendererFactory.Context): EntityRenderer<T>

    @Environment(EnvType.CLIENT)
    fun regRender() {
        EntityRendererRegistry.register(type, ::render)
    }
}
