package ghasto.enchantment_expansion.content.enchantment_effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ghasto.enchantment_expansion.mixin_interface.ArrowGravityAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public record Clumsiness(LevelBasedValue chanceToDropItem) implements EnchantmentEntityEffect {
    public static final MapCodec<Clumsiness> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            LevelBasedValue.CODEC.fieldOf("chance_to_drop_item").forGetter(Clumsiness::chanceToDropItem)
    ).apply(instance, Clumsiness::new));

    @Override
    public void apply(ServerLevel serverLevel, int enchantmentLevel, EnchantedItemInUse context, Entity user, Vec3 pos) {
        if(user instanceof Player player) {
            ItemStack item = context.itemStack();
            player.drop(item, true);
            //item.shrink(1);
        }
    }

    @Override
    public @NotNull MapCodec<Clumsiness> codec() {
        return CODEC;
    }
}