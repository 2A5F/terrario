package co.volight.terrario

import org.apache.logging.log4j.LogManager
import co.volight.terrario.Tr.logName
import co.volight.terrario.Tr.logger
import co.volight.terrario.Tr.blocks
import co.volight.terrario.Tr.items
import co.volight.terrario.blocks.OreBlock
import co.volight.terrario.core.*
import co.volight.terrario.items.Ore
import net.minecraft.block.Block
import net.minecraft.item.Item

object Tr: Idspace {
    const val id = "tr"
    const val logName = "[Terrario]"
    val logger = LogManager.getLogger()!!

    val blocks = listOf<Reg<out Block>>(
        OreBlock.Tin,
        OreBlock.Lead,
        OreBlock.Silver,
        OreBlock.Tungsten,
        OreBlock.Platinum,
    )
    val items = listOf<Reg<out Item>>(
        Ore.Tin,
        Ore.Lead,
        Ore.Silver,
        Ore.Tungsten,
        Ore.Platinum,
    )
}

@Suppress("unused")
fun init() {
    initBlocks()
    logger.info("$logName Blocks initialized")

    initItems()
    logger.info("$logName Items initialized")
}

fun initBlocks() {
    for (it in blocks) {
        it.regBlock()
    }
}

fun initItems() {
    for (it in blocks) {
        it.regBlockItem()
    }
    for (it in items) {
        it.regItem()
    }
}
