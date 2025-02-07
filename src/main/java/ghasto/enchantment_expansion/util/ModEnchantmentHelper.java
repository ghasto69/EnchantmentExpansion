package ghasto.enchantment_expansion.util;

import ghasto.enchantment_expansion.registry.ModEnchantmentEffectComponents;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.MutableFloat;

public class ModEnchantmentHelper {
    public static void modifyBowChargeTime(RandomSource random, int level, MutableFloat bowChargeTime, Enchantment enchantment) {
        enchantment.modifyUnfilteredValue(ModEnchantmentEffectComponents.BOW_CHARGE_TIME, random, level, bowChargeTime);
    }

    public static float getBowChargeTime(ItemStack stack, LivingEntity user, float baseBowChargeTime) {
        MutableFloat mutableFloat = new MutableFloat(baseBowChargeTime);
        stack.getEnchantments().entrySet().forEach(entry -> modifyBowChargeTime(user.getRandom(), entry.getIntValue(), mutableFloat, entry.getKey().value()));
        return Math.max(0.0f, mutableFloat.floatValue());
    }

    public static Holder<Enchantment> getHolder(ResourceKey<Enchantment> key, Level level) {
        return level.holderLookup(Registries.ENCHANTMENT).getOrThrow(key);
    }
}
