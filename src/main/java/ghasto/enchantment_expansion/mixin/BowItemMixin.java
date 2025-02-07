package ghasto.enchantment_expansion.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public abstract class BowItemMixin extends ProjectileWeaponItem {
    public BowItemMixin(Properties properties) {
        super(properties);
    }

    @WrapOperation(
            method = "releaseUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/BowItem;getPowerForTime(I)F"
            )
    )
    float getPowerForTimeWrapOp(int useTicks, Operation<Float> original, @Local(argsOnly = true) LivingEntity user, @Local(argsOnly = true) ItemStack stack) {
        float f = useTicks / (float) getPullTime(stack, user);

        if (f > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Unique
    private static int getPullTime(ItemStack stack, LivingEntity user) {
        float f = ModEnchantmentHelper.getBowChargeTime(stack, user, 1.75f);
        return Mth.floor(f * 20.0f);
    }
}
