package co.volight.terrario.items

import co.volight.terrario.core.Reg
import co.volight.terrario.core.RegEntity
import co.volight.terrario.core.WithItemGroup
import co.volight.terrario.core.settings
import co.volight.terrario.entities.SlimeEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.item.Item

typealias McSpawnEggItem = net.minecraft.item.SpawnEggItem

abstract class SpawnEggItem(
    name: String,
    val entity: RegEntity<out MobEntity>,
    primaryColor: Int,
    secondaryColor: Int
) : Reg<Item>, WithItemGroup {
    override val name = "${name}_spawn_egg"
    override val impl: Item by lazy {
        McSpawnEggItem(entity.impl, primaryColor, secondaryColor, settings())
    }

    object GreenSlime : SpawnEggItem("green_slime", SlimeEntity.Green, 0x42c539, 0x69ca62)
    object BlueSlime : SpawnEggItem("blue_slime", SlimeEntity.Blue, 0x406ebf, 0x6288cb)
    object RedSlime : SpawnEggItem("red_slime", SlimeEntity.Red, 0xbf4e40, 0xcb6e62)
    object PurpleSlime : SpawnEggItem("purple_slime", SlimeEntity.Purple, 0x8740bf, 0x9c62cb)
}
