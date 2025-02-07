package ghasto.enchantment_expansion.content.enchantment_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record MobEffect(LevelBasedValue value, List<Holder<net.minecraft.world.effect.MobEffect>> effects) implements EnchantmentEntityEffect {
    public static final MapCodec<MobEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("value").forGetter(MobEffect::value),
            net.minecraft.world.effect.MobEffect.CODEC.listOf().fieldOf("effects").forGetter(MobEffect::effects)
    ).apply(instance, MobEffect::new));

    @SafeVarargs
    public MobEffect(LevelBasedValue value, Holder<net.minecraft.world.effect.MobEffect>... effects) {
        this(value, List.of(effects));
    }

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (user instanceof LivingEntity livingEntity)
            this.effects.forEach(effect -> {
                livingEntity.addEffect(new MobEffectInstance(effect, (int) this.value.calculate(enchantmentLevel)));
            });
    }

    @Override
    public @NotNull MapCodec<MobEffect> codec() {
        return CODEC;
    }
}