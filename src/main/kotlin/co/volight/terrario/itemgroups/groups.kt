package co.volight.terrario.itemgroups

import co.volight.terrario.Tr
import co.volight.terrario.blocks.OreBlock
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder

val mainGroup = FabricItemGroupBuilder.build(Identifier(Tr.id, "main")) { ItemStack(OreBlock.Tin.impl) }!!
