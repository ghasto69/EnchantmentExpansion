package ghasto.enchantment_expansion.content.enchantment_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record Lifesteal(LevelBasedValue value) implements EnchantmentEntityEffect {
    public static final MapCodec<Lifesteal> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        LevelBasedValue.CODEC.fieldOf("value").forGetter(Lifesteal::value)
    ).apply(instance, Lifesteal::new));

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if(user instanceof LivingEntity attacker)
            attacker.heal(this.value.calculate(enchantmentLevel));
    }

    @Override
    public @NotNull MapCodec<Lifesteal> codec() {
        return CODEC;
    }
}
