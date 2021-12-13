package co.volight.terrario.core

fun Float.toFixed(digits: Int) = "%.${digits}f".format(this)
fun Double.toFixed(digits: Int) = "%.${digits}f".format(this)

val Number.f32 get() = toFloat()
val Number.f64 get() = toDouble()
val Number.i8 get() = toByte()
val Number.i16 get() = toShort()
val Number.i32 get() = toInt()
val Number.i64 get() = toLong()
