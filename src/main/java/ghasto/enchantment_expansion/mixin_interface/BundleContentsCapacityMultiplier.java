package ghasto.enchantment_expansion.mixin_interface;

import net.minecraft.world.item.component.BundleContents;

public interface BundleContentsCapacityMultiplier {
    float enchantmentExpansion$getCapacityMultiplier();
    BundleContents enchantmentExpansion$setCapacityMultiplier(float capacityMultiplier);
    static BundleContentsCapacityMultiplier cast(BundleContents bundleContents) {
        return (BundleContentsCapacityMultiplier) (Object) bundleContents;
    }
}
