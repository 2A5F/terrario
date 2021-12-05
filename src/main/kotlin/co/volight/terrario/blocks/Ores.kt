package co.volight.terrario.blocks

import co.volight.terrario.core.Reg
import co.volight.terrario.core.WithItemGroup
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Blocks
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.math.intprovider.UniformIntProvider

typealias McOreBlock = net.minecraft.block.OreBlock

object OreBlock {
    object Tin: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "tin_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f), UniformIntProvider.create(0, 2))
        }
        object Deepslate : Reg<McOreBlock>, WithItemGroup {
            override val name: String = "deepslate_${Tin.name}"
            override val impl by lazy {
                McOreBlock(FabricBlockSettings.copy(Tin.impl).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            }
        }
    }
    object Lead: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "lead_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
        object Deepslate : Reg<McOreBlock>, WithItemGroup {
            override val name: String = "deepslate_${Lead.name}"
            override val impl by lazy {
                McOreBlock(FabricBlockSettings.copy(Lead.impl).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            }
        }
    }
    object Silver: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "silver_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
        object Deepslate : Reg<McOreBlock>, WithItemGroup {
            override val name: String = "deepslate_${Silver.name}"
            override val impl by lazy {
                McOreBlock(FabricBlockSettings.copy(Silver.impl).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            }
        }
    }
    object Tungsten: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "tungsten_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
        object Deepslate : Reg<McOreBlock>, WithItemGroup {
            override val name: String = "deepslate_${Tungsten.name}"
            override val impl by lazy {
                McOreBlock(FabricBlockSettings.copy(Tungsten.impl).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            }
        }
    }
    object Platinum: Reg<McOreBlock>, WithItemGroup {
        override val name: String = "platinum_ore"
        override val impl by lazy {
            McOreBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(3.0f, 3.0f))
        }
        object Deepslate : Reg<McOreBlock>, WithItemGroup {
            override val name: String = "deepslate_${Platinum.name}"
            override val impl by lazy {
                McOreBlock(FabricBlockSettings.copy(Platinum.impl).mapColor(MapColor.DEEPSLATE_GRAY).strength(4.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE))
            }
        }
    }
}
