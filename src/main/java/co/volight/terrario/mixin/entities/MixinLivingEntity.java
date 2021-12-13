package co.volight.terrario.mixin.entities;

import co.volight.terrario.data.HasMagic;
import kotlin.math.MathKt;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity implements HasMagic {

    public MixinLivingEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    private static final TrackedData<Float> MAGIC = DataTracker.registerData(LivingEntity.class, TrackedDataHandlerRegistry.FLOAT);

    @Inject(method = "initDataTracker()V", at = @At("RETURN"))
    void initDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(MAGIC, 1.0f);
    }

    @Override
    public float getMagic() {
        return this.dataTracker.get(MAGIC);
    }

    @Override
    public void setMagic(float magic) {
        this.dataTracker.set(MAGIC, magic);
    }
}
