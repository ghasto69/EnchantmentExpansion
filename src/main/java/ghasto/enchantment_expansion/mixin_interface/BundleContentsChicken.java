package ghasto.enchantment_expansion.mixin_interface;

import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;

public interface BundleContentsChicken {
    float enchantmentExpansion$getCapacityMultiplier();
    BundleContents.Mutable enchantmentExpansion$setCapacityMultiplier(float capacityMultiplier);
}
