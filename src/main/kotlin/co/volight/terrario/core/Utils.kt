package co.volight.terrario.core

fun Float.toFixed(digits: Int) = "%.${digits}f".format(this)
fun Double.toFixed(digits: Int) = "%.${digits}f".format(this)

