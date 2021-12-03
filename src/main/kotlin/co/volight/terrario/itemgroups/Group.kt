package co.volight.terrario.itemgroups

import co.volight.terrario.Tr
import co.volight.terrario.items.TrLogo
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder

object Group {
    val main = FabricItemGroupBuilder.build(Identifier(Tr.id, "main")) { ItemStack(TrLogo.impl) }!!
}
