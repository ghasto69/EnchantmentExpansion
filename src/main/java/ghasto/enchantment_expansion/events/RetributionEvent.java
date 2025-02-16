package ghasto.enchantment_expansion.events;

import ghasto.enchantment_expansion.data.ModEnchantments;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class RetributionEvent implements AttackEntityCallback {
    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, Entity entity, @Nullable EntityHitResult entityHitResult) {
        if (level.isClientSide() || !(entity instanceof LivingEntity victim))
            return InteractionResult.PASS;

        ItemStack attackingItem = player.getWeaponItem();
        if (attackingItem.isEmpty() || !attackingItem.isDamageableItem())
            return InteractionResult.PASS;

        // Iterate through the victim's armor slots
        for (ItemStack armorStack : victim.getArmorSlots()) {
            // Get the level of the Retribution enchantment
            int retributionLevel = armorStack.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(ModEnchantments.RETRIBUTION, level));
            if (retributionLevel > 0) {
                // Apply damage or consume the item
                attackingItem.hurtAndBreak(retributionLevel, player, player.getEquipmentSlotForItem(attackingItem));
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }
}
