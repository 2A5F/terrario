package co.volight.terrario.entities;

import net.minecraft.entity.Entity.RemovalReason
import net.minecraft.entity.EntityData
import net.minecraft.entity.SpawnReason
import net.minecraft.nbt.NbtCompound
import net.minecraft.world.LocalDifficulty
import net.minecraft.world.ServerWorldAccess

interface MobEntityDirectInvoke {
    fun directInitialize(
        world: ServerWorldAccess,
        difficulty: LocalDifficulty,
        spawnReason: SpawnReason,
        entityData: EntityData?,
        entityNbt: NbtCompound?
    ): EntityData? {
        throw NotImplementedError("Not yet implemented")
    }

    fun directRemove(reason: RemovalReason?) {
        throw NotImplementedError("Not yet implemented")
    }
}
