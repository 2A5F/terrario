package co.volight.terrario.data

import net.minecraft.entity.LivingEntity

interface HasMagic {
    var magic: Float
}

var LivingEntity.magic: Float
    get() = (this as HasMagic).magic
    set(value) {
        (this as HasMagic).magic = value
    }
