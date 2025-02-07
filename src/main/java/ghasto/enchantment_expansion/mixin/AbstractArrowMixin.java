package ghasto.enchantment_expansion.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import ghasto.enchantment_expansion.mixin_interface.ArrowGravityAccessor;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin implements ArrowGravityAccessor {
	@Unique
	float gravityMultiplier = 1.0f;

	@ModifyReturnValue(method = "getDefaultGravity", at = @At("RETURN"))
	private double getGravity(double original) {
		return original * this.gravityMultiplier;
	}

	@Override
	public void enchantmentExpansion$setGravityMultiplier(float gravityMultiplier) {
		this.gravityMultiplier = gravityMultiplier;
	}
}