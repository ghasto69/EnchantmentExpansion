package ghasto.enchantment_expansion.registry;

import ghasto.enchantment_expansion.content.enchantment_effects.Aquaphobia;
import ghasto.enchantment_expansion.content.enchantment_effects.Clumsiness;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

import static ghasto.enchantment_expansion.registry.ModEnchantments.register;

@SuppressWarnings("unused")
public class ModCurses {
    public static final ResourceKey<Enchantment> AQUAPHOBIA = register("aquaphobia_curse", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.HEAD_ARMOR_ENCHANTABLE),
                            1,
                            3,
                            Enchantment.constantCost(25),
                            Enchantment.constantCost(50),
                            8,
                            EquipmentSlotGroup.HEAD
                    ))
            .withEffect(
                    EnchantmentEffectComponents.TICK,
                    new Aquaphobia(
                            LevelBasedValue.perLevel(1),
                            LevelBasedValue.perLevel(0.5f)
                    )
            )
    );

    public static final ResourceKey<Enchantment> BREAKING= register("breaking_curse", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ConventionalItemTags.TOOLS),
                            1,
                            3,
                            Enchantment.constantCost(25),
                            Enchantment.constantCost(50),
                            8,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .withEffect(
                    EnchantmentEffectComponents.ITEM_DAMAGE,
                    new AddValue(LevelBasedValue.perLevel(1))
            )
    );

    public static final ResourceKey<Enchantment> CLUMSINESS = register("clumsiness_curse", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ConventionalItemTags.TOOLS),
                            1,
                            3,
                            Enchantment.constantCost(25),
                            Enchantment.constantCost(50),
                            8,
                            EquipmentSlotGroup.MAINHAND
                    ))
    );

    public static void registerCurseEvents() {

    }

    public static void registerCurses() {}
}
