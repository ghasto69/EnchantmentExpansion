package ghasto.enchantment_expansion.registry;

import com.mojang.serialization.MapCodec;
import ghasto.enchantment_expansion.EnchantmentExpansion;
import ghasto.enchantment_expansion.content.enchantment_effects.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;

public class ModEnchantmentEffects {
    public static final MapCodec<Breeze> BREEZE = register("breeze", Breeze.CODEC);
    public static final MapCodec<MobEffect> INFLICT_MOB_EFFECT = register("inflict_mob_effect", MobEffect.CODEC);
    public static final MapCodec<Lifesteal> LIFESTEAL = register("lifesteal", Lifesteal.CODEC);
    public static final MapCodec<TippedArrowEffect> TIPPED_ARROW_EFFECT = register("tipped_arrow_effect", TippedArrowEffect.CODEC);
    public static final MapCodec<Aquaphobia> AQUAPHOBIA = register("aquaphobia", Aquaphobia.CODEC);
    public static final MapCodec<Clumsiness> CLUMSINESS = register("clumsiness", Clumsiness.CODEC);

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String id, MapCodec<T> entry) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_ENTITY_EFFECT_TYPE, EnchantmentExpansion.asResource(id), entry);
    }

    public static void register() {
        if(FabricLoader.getInstance().isDevelopmentEnvironment())
            EnchantmentExpansion.LOGGER.info("Registering enchantment entity effects!");
    }
}
