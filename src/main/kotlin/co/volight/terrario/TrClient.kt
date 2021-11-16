package co.volight.terrario

import co.volight.terrario.Tr.logName
import co.volight.terrario.Tr.logger

@Suppress("unused")
fun initClient() {
    initBlockLayer()
    logger.info("$logName Block layers initialized")

    initBlockColors()
    logger.info("$logName Block colors initialized")
}

fun initBlockLayer() {

}

fun initBlockColors() {

}
