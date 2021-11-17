package co.volight.terrario.items

import co.volight.terrario.core.Reg
import co.volight.terrario.core.settings
import co.volight.terrario.core.WithItemGroup
import net.minecraft.item.Item

object Ore {
    object Tin: Reg<Item>, WithItemGroup {
        override val name = "raw_tin"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object Lead: Reg<Item>, WithItemGroup {
        override val name = "raw_lead"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object Silver: Reg<Item>, WithItemGroup {
        override val name = "raw_silver"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object Tungsten: Reg<Item>, WithItemGroup {
        override val name = "raw_tungsten"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object Platinum: Reg<Item>, WithItemGroup {
        override val name = "raw_platinum"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
}
