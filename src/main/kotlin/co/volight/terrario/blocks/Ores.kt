package co.volight.terrario.blocks

import co.volight.terrario.core.BlockRegWithItem
import co.volight.terrario.core.JoinItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.util.math.intprovider.UniformIntProvider

class OreBlock : net.minecraft.block.OreBlock, JoinItemGroup {
    constructor(settings: Settings, experienceDropped: UniformIntProvider): super(settings, experienceDropped)
    constructor(settings: Settings) : super(settings)

    object Tin: BlockRegWithItem<OreBlock> {
        override val name: String = "tin_ore"
        override val impl by lazy {
            OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f), UniformIntProvider.create(0, 2))
        }
    }
    object Lead: BlockRegWithItem<OreBlock> {
        override val name: String = "lead_ore"
        override val impl by lazy {
            OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Silver: BlockRegWithItem<OreBlock> {
        override val name: String = "silver_ore"
        override val impl by lazy {
            OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Tungsten: BlockRegWithItem<OreBlock> {
        override val name: String = "tungsten_ore"
        override val impl by lazy {
            OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Platinum: BlockRegWithItem<OreBlock> {
        override val name: String = "platinum_ore"
        override val impl by lazy {
            OreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
}
