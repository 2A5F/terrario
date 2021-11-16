package co.volight.terrario

import org.apache.logging.log4j.LogManager
import co.volight.terrario.Tr.logName
import co.volight.terrario.Tr.logger
import co.volight.terrario.Tr.blocks
import co.volight.terrario.blocks.OreBlock
import co.volight.terrario.core.BlockRegWithItem

object Tr {
    const val id = "tr"
    const val logName = "[Terrario]"
    val logger = LogManager.getLogger()!!

    val blocks by lazy {
        listOf<BlockRegWithItem<*>>(
            OreBlock.Tin,
            OreBlock.Lead,
            OreBlock.Silver,
            OreBlock.Tungsten,
            OreBlock.Platinum,
        )
    }
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
        it.reg()
    }
}

fun initItems() {
    for (it in blocks) {
        it.regBlockItem()
    }
}
