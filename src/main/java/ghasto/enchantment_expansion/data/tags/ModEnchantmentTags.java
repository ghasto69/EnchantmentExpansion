package ghasto.enchantment_expansion.data.tags;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTags extends FabricTagProvider<Enchantment> {
    public ModEnchantmentTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ENCHANTMENT, registriesFuture);
    }

    public static final TagKey<Enchantment> FIRE_ASPECT_EXCLUSIVE = key("fire_aspect_exclusive");

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(FIRE_ASPECT_EXCLUSIVE)
                .add(Enchantments.FIRE_ASPECT);
    }

    private static TagKey<Enchantment> key(String path) {
        return TagKey.create(Registries.ENCHANTMENT, EnchantmentExpansion.asResource(path));
    }
}
