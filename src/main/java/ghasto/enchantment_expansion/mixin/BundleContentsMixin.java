package ghasto.enchantment_expansion.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import ghasto.enchantment_expansion.mixin_interface.BundleContentsCapacityMultiplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.apache.commons.lang3.math.Fraction;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.function.Function;


@Debug(export = true)
@Mixin(BundleContents.class)
public class BundleContentsMixin implements BundleContentsCapacityMultiplier {
    @Unique
    private float enchantmentExpansion$capacityMultiplier;

    @ModifyExpressionValue(
            method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;xmap(Ljava/util/function/Function;Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;")
    )
    private static Codec<BundleContents> modifyCodec(Codec<BundleContents> codec) {
        return Codec.withAlternative(codec, RecordCodecBuilder.create(instance -> instance.group(
                                codec.fieldOf("value").forGetter(Function.identity()),
                                Codec.FLOAT.fieldOf("capacity").forGetter(contents -> ((BundleContentsCapacityMultiplier) (Object) contents).enchantmentExpansion$getCapacityMultiplier())
                        ).apply(instance, (contents, capacity) -> ((BundleContentsCapacityMultiplier) (Object) contents).enchantmentExpansion$setCapacityMultiplier(capacity))
                )
        );
    }

    @Override
    public float enchantmentExpansion$getCapacityMultiplier() {
        return this.enchantmentExpansion$capacityMultiplier;
    }

    @Override
    public BundleContents enchantmentExpansion$setCapacityMultiplier(float capacityMultiplier) {
        this.enchantmentExpansion$capacityMultiplier = capacityMultiplier;
        return (BundleContents) (Object) this;
    }

    @Inject(
            method = "<init>(Ljava/util/List;Lorg/apache/commons/lang3/math/Fraction;)V",
            at = @At("TAIL")
    )
    private void setDefaultCapacityMultiplier(List<ItemStack> list, Fraction fraction, CallbackInfo ci) {
        ((BundleContentsCapacityMultiplier) this).enchantmentExpansion$setCapacityMultiplier(1.0f);
    }

}
