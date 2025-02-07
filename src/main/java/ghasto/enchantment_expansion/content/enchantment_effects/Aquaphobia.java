package ghasto.enchantment_expansion.content.enchantment_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ghasto.enchantment_expansion.mixin_interface.ArrowGravityAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record Aquaphobia(LevelBasedValue drowningValue, LevelBasedValue hurtValue) implements EnchantmentEntityEffect {
    public static final MapCodec<Aquaphobia> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("drowning_value").forGetter(Aquaphobia::drowningValue),
            LevelBasedValue.CODEC.fieldOf("hurt_value").forGetter(Aquaphobia::hurtValue)
    ).apply(instance, Aquaphobia::new));

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if(user instanceof LivingEntity livingEntity) {
            int airRemoved = Math.round(this.drowningValue.calculate(enchantmentLevel));
            if(livingEntity.isInWater()) {
                int victimAir = livingEntity.getAirSupply();

                if (victimAir >= airRemoved)
                    livingEntity.setAirSupply(victimAir - airRemoved);

                livingEntity.removeEffect(MobEffects.WATER_BREATHING);
            }
            if(livingEntity.isInWaterRainOrBubble()) {
                livingEntity.hurt(serverLevel.damageSources().drown(), this.hurtValue.calculate(enchantmentLevel));
            }
        }
    }

    @Override
    public @NotNull MapCodec<Aquaphobia> codec() {
        return CODEC;
    }
}
