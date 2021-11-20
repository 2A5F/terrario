package co.volight.terrario.mixin.entities;

import co.volight.terrario.entities.MobEntityDirectInvoke;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlimeEntity.class)
public abstract class MixinSlimeEntityDirectInvoke extends MobEntity implements MobEntityDirectInvoke {
    protected MixinSlimeEntityDirectInvoke(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public @Nullable EntityData directInitialize(@NotNull ServerWorldAccess world, @NotNull LocalDifficulty difficulty, @NotNull SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    public void directRemove(Entity.RemovalReason reason) {
        super.remove(reason);
    }
}
