package co.volight.terrario.items

import co.volight.terrario.core.Reg
import co.volight.terrario.core.WithItemGroup
import co.volight.terrario.core.settings
import net.minecraft.item.Item

abstract class SimpleItem(override val name: String): Reg<Item>, WithItemGroup {
    override val impl: Item by lazy {
        Item(settings())
    }
}

object TrLogo: Reg<Item> {
    override val name = "tr"
    override val impl: Item by lazy {
        Item(settings())
    }
}
