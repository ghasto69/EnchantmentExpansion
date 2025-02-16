package ghasto.enchantment_expansion.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import ghasto.enchantment_expansion.mixin_interface.BundleContentsChicken;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BundleContents.Mutable.class)
public class BundleContentsMutableMixin implements BundleContentsChicken {
    @Unique
    private float capacityMultiplier;

    @Override
    public float enchantmentExpansion$getCapacityMultiplier() {
        return this.capacityMultiplier;
    }

    @Override
    public BundleContents.Mutable enchantmentExpansion$setCapacityMultiplier(float capacityMultiplier) {
        this.capacityMultiplier = capacityMultiplier;
        return (BundleContents.Mutable) (Object) this;
    }

    @ModifyExpressionValue(
            method = "getMaxAmountToAdd",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/BundleContents;getWeight(Lnet/minecraft/world/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;"
            )
    )
    private Fraction getMaxAmountToAdd(Fraction original) {
        return this.modifyWeight(original);
    }

    @ModifyExpressionValue(
            method = "tryInsert",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/BundleContents;getWeight(Lnet/minecraft/world/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;"
            )
    )
    private Fraction tryInsert(Fraction original) {
        return this.modifyWeight(original);
    }

    @ModifyExpressionValue(
            method = "removeOne",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/component/BundleContents;getWeight(Lnet/minecraft/world/item/ItemStack;)Lorg/apache/commons/lang3/math/Fraction;"
            )
    )
    private Fraction removeOne(Fraction original) {
        return this.modifyWeight(original);
    }

    @Unique
    private Fraction modifyWeight(Fraction original) {
        return original.divideBy(Fraction.getFraction(this.capacityMultiplier));
    }

}
