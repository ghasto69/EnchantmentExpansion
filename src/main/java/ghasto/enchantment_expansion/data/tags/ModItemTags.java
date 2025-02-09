package ghasto.enchantment_expansion.data.tags;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends FabricTagProvider<Item> {
    public ModItemTags(FabricDataOutput output,  CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.ITEM, registriesFuture);
    }

    public static final TagKey<Item> NETHERITE_FOOT_ARMOR_ENCHANTABLE = key("netherite_foot_armor_enchantable");
    public static final TagKey<Item> BREAKING_CURSE = key("breaking_curse");

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(NETHERITE_FOOT_ARMOR_ENCHANTABLE)
                .add(Items.NETHERITE_BOOTS);
    }

    private static TagKey<Item> key(String path) {
        return TagKey.create(Registries.ITEM, EnchantmentExpansion.asResource(path));
    }
}
