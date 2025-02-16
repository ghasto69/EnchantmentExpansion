package ghasto.enchantment_expansion.data;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import ghasto.enchantment_expansion.content.enchantment_effects.Aquaphobia;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

import java.util.concurrent.CompletableFuture;

import static ghasto.enchantment_expansion.data.ModEnchantments.enchantment;
import static ghasto.enchantment_expansion.data.ModEnchantments.key;

public class ModCurses {
    public static final ResourceKey<Enchantment> AQUAPHOBIA = key("aquaphobia_curse");
    public static final ResourceKey<Enchantment> BREAKING = key("breaking_curse");
    public static final ResourceKey<Enchantment> CLUMSINESS = key("clumsiness_curse");

    protected static void configure(HolderLookup.Provider provider, FabricDynamicRegistryProvider.Entries entries) {
        HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);

        enchantment(
                AQUAPHOBIA,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.HEAD
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.TICK,
                        new Aquaphobia(
                                LevelBasedValue.perLevel(1),
                                LevelBasedValue.perLevel(0.5f)
                        )
                ),
                entries
        );

        enchantment(
                BREAKING,
                Enchantment.definition(
                        items.getOrThrow(ConventionalItemTags.TOOLS),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.ITEM_DAMAGE,
                        new AddValue(LevelBasedValue.perLevel(1))
                ),
                entries
        );

        enchantment(
                CLUMSINESS,
                Enchantment.definition(
                        items.getOrThrow(ConventionalItemTags.TOOLS),
                        1,
                        3,
                        Enchantment.constantCost(25),
                        Enchantment.constantCost(50),
                        8,
                        EquipmentSlotGroup.MAINHAND
                ),
                entries
        );
    }
}
