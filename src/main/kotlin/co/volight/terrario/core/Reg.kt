package co.volight.terrario.core

import co.volight.terrario.Tr
import co.volight.terrario.itemgroups.Group
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.client.particle.Particle
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.particle.ParticleEffect
import net.minecraft.particle.ParticleType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

interface Idspace {
    val idspace: String get() = Tr.id
}

fun <S: Idspace> S.identifier(path: String) = Identifier(idspace, path)

interface Nameable {
    val name: String
}

interface Instance<T> : Idspace, Nameable {
    val impl: T
}

interface RegBasic: Idspace, Nameable

interface Reg<T>: Instance<T>, RegBasic

fun interface JoinItemGroup {
    fun joinItemGroup(settings: Item.Settings)
}

interface WithItemGroup: JoinItemGroup {
    override fun joinItemGroup(settings: Item.Settings) {
        settings.group(Group.main)
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

interface RegEntity<T: Entity>: Reg<EntityType<T>> {
    val render: (context: EntityRendererFactory.Context) -> EntityRenderer<T>
}

interface RegLivingEntity<T: LivingEntity>: RegEntity<T> {
    val attr: DefaultAttributeContainer.Builder
}

fun <S, T> S.makeEntity(create: (type: EntityType<T>, world: World) -> T) where S: RegEntity<T>, T: Entity = create

fun <S, T> S.makeRender(render: (context: EntityRendererFactory.Context) -> EntityRenderer<T>) where S: RegEntity<T>, T: Entity = render

fun <S> S.regEntityType() where S : RegEntity<out Entity> {
    Registry.register(Registry.ENTITY_TYPE, Identifier(idspace, name), impl)
}

fun <S> S.regEntityAttr() where S : RegLivingEntity<out LivingEntity> {
    FabricDefaultAttributeRegistry.register(impl, attr)
}

@Environment(EnvType.CLIENT)
fun <S, T> S.regEntityRender() where S : RegEntity<T>, T : Entity {
    EntityRendererRegistry.register(impl, render)
}

@Environment(EnvType.CLIENT)
fun <S, T> S.regEntityRender() where S : RegLivingEntity<T>, T : LivingEntity {
    EntityRendererRegistry.register(impl, render)
}

//fun regStat(name: String, statFormatter: StatFormatter = StatFormatter.DEFAULT): Identifier {
//    return Identifier(Tr.id, name).also {
//        Registry.register(Registry.CUSTOM_STAT, it, it)
//        Stats.CUSTOM.getOrCreateStat(it, statFormatter)
//    }
//}
//
//fun <T : ScreenHandler> regScreenHandler(name: String, factory: ScreenHandlerRegistry.SimpleClientHandlerFactory<T>): ScreenHandlerType<T> {
//    return ScreenHandlerRegistry.registerSimple(Identifier(Tr.id, name), factory)
//}

//interface ParticleReg<T : ParticleEffect>: Nameable, Idspace {
//    val type: ParticleType<T>
//
//    @Environment(EnvType.CLIENT)
//    fun createParticle(
//        parameters: T, world: ClientWorld, x: Double, y: Double, z: Double, vx: Double, vy: Double, vz: Double
//    ): Particle
//
//    fun regType() {
//        Registry.register(Registry.PARTICLE_TYPE, Identifier(idspace, name), type)
//    }
//    @Environment(EnvType.CLIENT)
//    fun regFactory() {
//        ParticleFactoryRegistry.getInstance().register(type, this::createParticle)
//    }
//}

interface RegParticle<T: ParticleEffect> : Reg<ParticleType<T>> {
    val factory: (parameters: T, world: ClientWorld, x: Double, y: Double, z: Double, vx: Double, vy: Double, vz: Double) -> Particle?
}

fun <S, T> S.makeFactory(f: (parameters: T, world: ClientWorld, x: Double, y: Double, z: Double, vx: Double, vy: Double, vz: Double) -> Particle?) where S : RegParticle<T>, T: ParticleEffect = f

fun <S> S.regParticle() where S : RegParticle<out ParticleEffect> {
    Registry.register(Registry.PARTICLE_TYPE, Identifier(idspace, name), impl)
}

@Environment(EnvType.CLIENT)
fun <S, T> S.regParticleFactory() where S : RegParticle<T>, T: ParticleEffect {
    ParticleFactoryRegistry.getInstance().register(impl, factory)
}
