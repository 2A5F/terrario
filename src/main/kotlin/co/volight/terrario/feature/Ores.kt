package co.volight.terrario.feature

import co.volight.terrario.blocks.OreBlock
import co.volight.terrario.core.Reg
import co.volight.terrario.core.modifiersWithCount
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.HeightRangePlacementModifier
import net.minecraft.world.gen.feature.*

typealias McOreFeature = net.minecraft.world.gen.feature.OreFeature

object OreFeature {
    object Tin: Reg<ConfiguredFeature<*, *>> {
        override val name = OreBlock.Tin.name
        override val impl: ConfiguredFeature<*, *> by lazy {
            Feature.ORE.configure(
                OreFeatureConfig(
                    listOf(
                        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, OreBlock.Tin.impl.defaultState),
                    ),
                    10
                )
            )
        }
        object Normal: Reg<PlacedFeature> {
            override val name = Tin.name
            override val impl: PlacedFeature by lazy {
                Tin.impl.withPlacement(modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-16), YOffset.fixed(112))))
            }
        }
    }
    object Lead: Reg<ConfiguredFeature<*, *>> {
        override val name = OreBlock.Lead.name
        override val impl: ConfiguredFeature<*, *> by lazy {
            Feature.ORE.configure(
                OreFeatureConfig(
                    listOf(
                        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, OreBlock.Lead.impl.defaultState),
                    ),
                    9
                )
            )
        }
        object Upper: Reg<PlacedFeature> {
            override val name = "${Lead.name}_upper"
            override val impl: PlacedFeature by lazy {
                Lead.impl.withPlacement(modifiersWithCount(90, HeightRangePlacementModifier.trapezoid(YOffset.fixed(80), YOffset.fixed(384))))
            }
        }
        object Middle: Reg<PlacedFeature> {
            override val name = "${Lead.name}_middle"
            override val impl: PlacedFeature by lazy {
                Lead.impl.withPlacement(modifiersWithCount(10, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-24), YOffset.fixed(56))))
            }
        }
    }
    object Silver: Reg<ConfiguredFeature<*, *>> {
        override val name = OreBlock.Silver.name
        override val impl: ConfiguredFeature<*, *> by lazy {
            Feature.ORE.configure(
                OreFeatureConfig(
                    listOf(
                        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, OreBlock.Silver.impl.defaultState),
                    ),
                    10
                )
            )
        }
        object Normal: Reg<PlacedFeature> {
            override val name = Silver.name
            override val impl: PlacedFeature by lazy {
                Silver.impl.withPlacement(modifiersWithCount(32, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(32))))
            }
        }
    }
    object Tungsten: Reg<ConfiguredFeature<*, *>> {
        override val name = OreBlock.Tungsten.name
        override val impl: ConfiguredFeature<*, *> by lazy {
            Feature.ORE.configure(
                OreFeatureConfig(
                    listOf(
                        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, OreBlock.Tungsten.impl.defaultState),
                    ),
                    10
                )
            )
        }
        object Normal: Reg<PlacedFeature> {
            override val name = Tungsten.name
            override val impl: PlacedFeature by lazy {
                Tungsten.impl.withPlacement(modifiersWithCount(32, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-32), YOffset.fixed(32))))
            }
        }
    }
    object Platinum: Reg<ConfiguredFeature<*, *>> {
        override val name = OreBlock.Platinum.name
        override val impl: ConfiguredFeature<*, *> by lazy {
            Feature.ORE.configure(
                OreFeatureConfig(
                    listOf(
                        OreFeatureConfig.createTarget(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, OreBlock.Platinum.impl.defaultState),
                    ),
                    9
                )
            )
        }
        object Normal: Reg<PlacedFeature> {
            override val name = Platinum.name
            override val impl: PlacedFeature by lazy {
                Platinum.impl.withPlacement(modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(16))))
            }
        }
    }
}
