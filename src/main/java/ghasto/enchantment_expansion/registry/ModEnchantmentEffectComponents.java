package ghasto.enchantment_expansion.registry;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModEnchantmentEffectComponents {
    public static final DataComponentType<EnchantmentValueEffect> BOW_CHARGE_TIME = register(
            "bow_charge_time",
            builder -> builder.persistent(EnchantmentValueEffect.CODEC)
    );

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> factory) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, EnchantmentExpansion.asResource(id), factory.apply(DataComponentType.builder()).build());
    }

    public static void register() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment())
            EnchantmentExpansion.LOGGER.info("Registering enchantment entity effect components!");
    }
}

