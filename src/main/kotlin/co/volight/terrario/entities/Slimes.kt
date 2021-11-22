package co.volight.terrario.entities

import co.volight.terrario.core.*
import co.volight.terrario.particles.GelParticle
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.minecraft.client.MinecraftClient
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.LivingEntityRenderer
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.FeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.loot.LootTables
import net.minecraft.nbt.NbtCompound
import net.minecraft.particle.ParticleEffect
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess
import net.minecraft.world.World

typealias McSlimeEntity = net.minecraft.entity.mob.SlimeEntity
typealias McSlimeEntityRenderer = net.minecraft.client.render.entity.SlimeEntityRenderer
typealias McSlimeEntityModel<T> = net.minecraft.client.render.entity.model.SlimeEntityModel<T>
typealias MCSlimeOverlayFeatureRenderer<T> = net.minecraft.client.render.entity.feature.SlimeOverlayFeatureRenderer<T>

object Slime {
    abstract class Basic(override val name: String, open val scale: Float = 1.0f, val particles: () -> ParticleEffect): RegLivingEntity<SlimeEntity> {
        open val size = 2
        override val impl: EntityType<SlimeEntity> by lazy {
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, makeEntity { type, world ->
                SlimeEntity(type, world, SlimeEntityData(size, scale), particles)
            }) .dimensions(EntityDimensions.changing(1.96f, 1.96f).scaled(scale)).build()
        }
        override val attr: DefaultAttributeContainer.Builder by lazy {
            HostileEntity.createHostileAttributes()
        }
        override val render = makeRender { SlimeEntityRenderer(it, name, scale) }
    }
    object Green: Basic("green_slime", 0.875f, particles = { GelParticle.Green.impl })
    object Blue: Basic("blue_slime", particles = { GelParticle.Blue.impl })
    object Red: Basic("red_slime", particles = { GelParticle.Red.impl })
    object Purple: Basic("purple_slime", 1.125f, particles = { GelParticle.Purple.impl })
}

data class SlimeEntityData(val size: Int, val scale: Float)

open class SlimeEntity(entityType: EntityType<out SlimeEntity>, world: World, val data: SlimeEntityData, val particles: () -> ParticleEffect) : McSlimeEntity(entityType, world) {
    override fun initialize(
        world: ServerWorldAccess,
        difficulty: LocalDifficulty,
        spawnReason: SpawnReason,
        entityData: EntityData?,
        entityNbt: NbtCompound?
    ): EntityData? {
        val self = this as MobEntityDirectInvoke
        this.setSize(data.size, true)
        return self.directInitialize(world, difficulty, spawnReason, entityData, entityNbt)
    }

    override fun remove(reason: RemovalReason) {
        val self = this as MobEntityDirectInvoke
        self.directRemove(reason)
    }

    override fun getParticles(): ParticleEffect = particles()

    override fun getLootTableId(): Identifier = type.lootTableId
}

@Environment(EnvType.CLIENT)
open class SlimeEntityRenderer(context: EntityRendererFactory.Context, texture_name: String, val scale: Float = 1f) : MobEntityRenderer<SlimeEntity, SlimeEntityModel<SlimeEntity>>(
    context,
    SlimeEntityModel(SlimeEntityModel.EmptyModelData.createModel()),
    0.25f
), Idspace {
    init {
        init()
    }

    private fun init() {
        addSlimeOverlayFeature()
    }

    protected open fun addSlimeOverlayFeature() {
        addFeature(SlimeOverlayFeatureRenderer(this))
    }

    private val texture = identifier("textures/entity/slime/${texture_name}.png")

    override fun getTexture(slimeEntity: SlimeEntity) = texture

    override fun render(slimeEntity: SlimeEntity, f: Float, g: Float, matrixStack: MatrixStack?, vertexConsumerProvider: VertexConsumerProvider?, i: Int) {
        shadowRadius = 0.25f * slimeEntity.size.toFloat()
        
        super.render(slimeEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }

    override fun scale(slimeEntity: SlimeEntity, matrixStack: MatrixStack, f: Float) {
        val s = 0.999f * scale;
        matrixStack.scale(s, s, s)
        matrixStack.translate(0.0, 0.001, 0.0)
        val h = slimeEntity.size.toFloat()
        val i = MathHelper.lerp(f, slimeEntity.lastStretch, slimeEntity.stretch) / (h * 0.5f + 1.0f)
        val j = 1.0f / (i + 1.0f)
        matrixStack.scale(j * h, 1.0f / j * h, j * h)
    }
}

@Environment(EnvType.CLIENT)
open class SlimeEntityModel<T: Entity>(private val root: ModelPart) : SinglePartEntityModel<T>() {
    companion object {
        val EmptyModelData: TexturedModelData by lazy {
            TexturedModelData.of(ModelData(), 64, 32)
        }
        val OuterTexturedModelData: TexturedModelData by lazy {
            val modelData = ModelData()
            val modelPartData = modelData.root
            modelPartData.addChild(
                EntityModelPartNames.CUBE,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, 16.0f, -4.0f, 8.0f, 8.0f, 8.0f),
                ModelTransform.NONE
            )
            TexturedModelData.of(modelData, 64, 32)
        }
    }

    override fun setAngles(entity: T, limbAngle: Float, limbDistance: Float, animationProgress: Float, headYaw: Float, headPitch: Float) {}

    override fun getPart(): ModelPart= root
}

open class SlimeOverlayFeatureRenderer<T: LivingEntity>(context: FeatureRendererContext<T, SlimeEntityModel<T>>) :
    FeatureRenderer<T, SlimeEntityModel<T>>(context) {

    private val model = SlimeEntityModel<T>(SlimeEntityModel.OuterTexturedModelData.createModel())

    override fun render(
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        entity: T,
        limbAngle: Float,
        limbDistance: Float,
        tickDelta: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        val minecraftClient = MinecraftClient.getInstance()
        val bl = minecraftClient.hasOutline(entity) && entity.isInvisible
        if (entity.isInvisible && !bl) return
        val vertexConsumer: VertexConsumer =
            if (bl) vertexConsumers.getBuffer(RenderLayer.getOutline(getTexture(entity)))
            else vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(getTexture(entity)))
        this.contextModel.copyStateTo(model)
        model.animateModel(entity, limbAngle, limbDistance, tickDelta)
        model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch)
        model.render(
            matrices,
            vertexConsumer,
            light,
            LivingEntityRenderer.getOverlay(entity, 0.0f),
            1.0f,
            1.0f,
            1.0f,
            1.0f
        )
    }
}