package co.volight.terrario.items

import co.volight.terrario.core.Reg
import co.volight.terrario.core.WithItemGroup
import co.volight.terrario.core.settings
import net.minecraft.item.Item

object Gel {
    object GreenGel: Reg<Item>, WithItemGroup {
        override val name = "green_gel"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object BlueGel: Reg<Item>, WithItemGroup {
        override val name = "blue_gel"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object RedGel: Reg<Item>, WithItemGroup {
        override val name = "red_gel"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
    object PurpleGel: Reg<Item>, WithItemGroup {
        override val name = "purple_gel"
        override val impl: Item by lazy {
            Item(settings())
        }
    }
}
