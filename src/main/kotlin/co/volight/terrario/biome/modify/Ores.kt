package co.volight.terrario.biome.modify

import co.volight.terrario.core.Regable
import co.volight.terrario.core.regKeyOf
import co.volight.terrario.feature.OreFeature
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.util.registry.Registry
import net.minecraft.world.gen.GenerationStep

class OreBiomeModify {
    object Tin : Regable {
        override fun reg() {
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Tin.Normal.name)
            )
        }
    }
    object Lead: Regable {
        override fun reg() {
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Lead.Upper.name)
            )
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Lead.Middle.name)
            )
        }
    }
    object Silver : Regable {
        override fun reg() {
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Silver.Normal.name)
            )
        }
    }
    object Tungsten : Regable {
        override fun reg() {
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Tungsten.Normal.name)
            )
        }
    }
    object Platinum : Regable {
        override fun reg() {
            BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Feature.UNDERGROUND_ORES,
                regKeyOf(Registry.PLACED_FEATURE_KEY, OreFeature.Platinum.Normal.name)
            )
        }
    }
}
