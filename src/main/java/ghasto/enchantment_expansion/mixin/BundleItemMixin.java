package ghasto.enchantment_expansion.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ghasto.enchantment_expansion.mixin_interface.BundleContentsChicken;
import ghasto.enchantment_expansion.data.ModEnchantments;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BundleItem.class)
@Debug(export = true)
public abstract class BundleItemMixin {
    @Shadow public abstract boolean isBarVisible(ItemStack itemStack);

    @ModifyExpressionValue(
            method = "overrideStackedOnOther",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/item/component/BundleContents;)Lnet/minecraft/world/item/component/BundleContents$Mutable;"
            )
    )
    private BundleContents.Mutable overrideStackedOnOther(BundleContents.Mutable original, ItemStack itemStack, Slot slot, ClickAction clickAction, Player player) {
        return calculateCapacityMultiplier(original, itemStack, player);
    }

    @ModifyExpressionValue(
            method = "overrideOtherStackedOnMe",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/world/item/component/BundleContents;)Lnet/minecraft/world/item/component/BundleContents$Mutable;"
            )
    )
    private BundleContents.Mutable overrideOtherStackedOnMe(BundleContents.Mutable original, ItemStack itemStack, ItemStack itemStack2, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        return calculateCapacityMultiplier(original, itemStack, player);
    }

    @Unique
    private BundleContents.Mutable calculateCapacityMultiplier(BundleContents.Mutable original, ItemStack itemStack, Player player) {
        int level = itemStack.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(ModEnchantments.CAPACITY, player.level()));
        return ((BundleContentsChicken) original).enchantmentExpansion$setCapacityMultiplier(level == 0 ? 1 : 1 + level * 0.5f);
    }
}
