package co.volight.terrario.blocks

import co.volight.terrario.core.Reg
import co.volight.terrario.core.WithItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material
import net.minecraft.util.math.intprovider.UniformIntProvider

typealias McOreBlock = net.minecraft.block.OreBlock

object OreBlock {
    object Tin: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "tin_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f), UniformIntProvider.create(0, 2))
        }
    }
    object Lead: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "lead_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Silver: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "silver_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Tungsten: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "tungsten_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
    object Platinum: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "platinum_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
    }
}
