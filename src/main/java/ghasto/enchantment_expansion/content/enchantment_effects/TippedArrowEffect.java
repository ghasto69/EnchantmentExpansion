package ghasto.enchantment_expansion.content.enchantment_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public record TippedArrowEffect(LevelBasedValue value, List<Holder<MobEffect>> effects) implements EnchantmentEntityEffect {
    public static final MapCodec<TippedArrowEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("value").forGetter(TippedArrowEffect::value),
            MobEffect.CODEC.listOf().fieldOf("effects").forGetter(TippedArrowEffect::effects)
    ).apply(instance, TippedArrowEffect::new));

    @SafeVarargs
    public TippedArrowEffect(LevelBasedValue value, Holder<MobEffect>... effects) {
        this(value, List.of(effects));
    }

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if (user instanceof Arrow arrow)
            this.effects.forEach(effect -> {
                arrow.addEffect(new MobEffectInstance(effect, (int) this.value.calculate(enchantmentLevel)));
            });
    }

    @Override
    public @NotNull MapCodec<TippedArrowEffect> codec() {
        return CODEC;
    }
}