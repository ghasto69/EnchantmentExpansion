package ghasto.enchantment_expansion.data;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import ghasto.enchantment_expansion.content.enchantment_effects.*;
import ghasto.enchantment_expansion.data.tags.ModEnchantmentTags;
import ghasto.enchantment_expansion.registry.ModEnchantmentEffectComponents;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ModEnchantments extends DatapackRegistryProvider<Enchantment> {
    /* Bow Enchantments */
    // True Shot: Makes arrows fired by the enchanted bow unaffected by gravity.
    public static final ResourceKey<Enchantment> BREEZE = key("breeze");
    //Quick Draw: Draws bow faster, levels 1-3.
    public static final ResourceKey<Enchantment> QUICK_DRAW = key("quick_draw");
    //Paralysis: Applies weakness and slowness to entity hit by the arrow of the enchanted bow, levels 1-3.
    public static final ResourceKey<Enchantment> PARALYSIS = key("paralysis");
    //Floating: Applies shulker floating to entity hit by the arrow of the enchanted bow, levels 1-3.
    public static final ResourceKey<Enchantment> FLOATING = key("floating");

    /* Weapon Enchantments */
    //Ice Aspect: Inflicts weakness and slowness on the entity being attacked, levels 1-2
    public static final ResourceKey<Enchantment> FROSTBITE = key("frostbite");
    //Wisdom: Increases experience dropped by mobs
    public static final ResourceKey<Enchantment> WISDOM = key("wisdon");
    //Swift Attack: Increases your attack speed
    public static final ResourceKey<Enchantment> SWIFT_ATTACK = key("swift_attack");
    //Lifesteal: Heals you when hit an entity
    public static final ResourceKey<Enchantment> LIFESTEAL = key("lifesteal");

    /* Armor Enchantments */
    //Stride: Increases the player's step height to 1 block
    public static final ResourceKey<Enchantment> STRIDE = key("stride");
    //Retribution: Damages the attacker's item more
    public static final ResourceKey<Enchantment> RETRIBUTION = key("retribution");

    /*  Tool Enchantments */
    //Stone Mending: Tool has a chance not to lose durability when mining stone related blocks
    public static final ResourceKey<Enchantment> STONE_MENDING = key("stone_mending");

    public static final ResourceKey<Enchantment> CAPACITY = key("capacity");

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        HolderGetter<Item> items = provider.lookupOrThrow(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = provider.lookupOrThrow(Registries.ENCHANTMENT);

        enchantment(
                BREEZE,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                        10,
                        2,
                        Enchantment.constantCost(10),
                        Enchantment.constantCost(20),
                        4,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b
                        .exclusiveWith(enchantments.getOrThrow(EnchantmentTags.BOW_EXCLUSIVE))
                        .withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new Breeze(LevelBasedValue.perLevel(0.7f, -0.1f))),
                entries
        );

        enchantment(
                QUICK_DRAW,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                        10,
                        3,
                        Enchantment.constantCost(10),
                        Enchantment.constantCost(20),
                        4,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b.withSpecialEffect(ModEnchantmentEffectComponents.BOW_CHARGE_TIME, new AddValue(LevelBasedValue.perLevel(-0.25f))),
                entries
        );

        enchantment(
                PARALYSIS,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                        10,
                        3,
                        Enchantment.constantCost(10),
                        Enchantment.constantCost(20),
                        4,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b.withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new TippedArrowEffect(
                        LevelBasedValue.perLevel(10),
                        MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
                )),
                entries
        );

        enchantment(
                FLOATING,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.BOW_ENCHANTABLE),
                        10,
                        3,
                        Enchantment.constantCost(10),
                        Enchantment.constantCost(20),
                        4,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b.withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new TippedArrowEffect(
                        LevelBasedValue.perLevel(7),
                        MobEffects.LEVITATION
                )),
                entries
        );

        enchantment(
                FROSTBITE,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
                        10,
                        2,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        5,
                        EquipmentSlotGroup.HAND
                ),
                b -> b
                        .exclusiveWith(enchantments.getOrThrow(ModEnchantmentTags.FIRE_ASPECT_EXCLUSIVE))
                        .withEffect(
                                EnchantmentEffectComponents.POST_ATTACK,
                                EnchantmentTarget.ATTACKER,
                                EnchantmentTarget.VICTIM,
                                new MobEffect(
                                        LevelBasedValue.perLevel(7),
                                        MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
                                )
                        ),
                entries
        );

        enchantment(
                WISDOM,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                        4,
                        5,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        6,
                        EquipmentSlotGroup.HAND
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.MOB_EXPERIENCE,
                        new AddValue(LevelBasedValue.perLevel(2f))
                ),
                entries
        );

        enchantment(
                SWIFT_ATTACK,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        6,
                        5,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        3,
                        EquipmentSlotGroup.MAINHAND
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(
                                SWIFT_ATTACK.location(),
                                Attributes.ATTACK_SPEED,
                                LevelBasedValue.perLevel(0.1f),
                                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                        )
                ),
                entries
        );

        enchantment(
                LIFESTEAL,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                        4,
                        2,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        6,
                        EquipmentSlotGroup.HAND
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.POST_ATTACK,
                        EnchantmentTarget.ATTACKER,
                        EnchantmentTarget.ATTACKER,
                        new Lifesteal(LevelBasedValue.perLevel(0.5f))
                ),
                entries
        );

        enchantment(
                STRIDE,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                        3,
                        1,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        4,
                        EquipmentSlotGroup.FEET
                ),
                b -> b.withEffect(
                        EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(
                                EnchantmentExpansion.asResource("stride"),
                                Attributes.STEP_HEIGHT,
                                LevelBasedValue.perLevel(0.4f),
                                AttributeModifier.Operation.ADD_VALUE
                        )
                ),
                entries
        );

        enchantment(
                RETRIBUTION,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                        3,
                        2,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        4,
                        EquipmentSlotGroup.ARMOR
                ),
                entries
        );

        enchantment(
                STONE_MENDING,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.PICKAXES),
                        3,
                        2,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        4,
                        EquipmentSlotGroup.MAINHAND
                ),
                entries
        );

        enchantment(
                CAPACITY,
                Enchantment.definition(
                        items.getOrThrow(ItemTags.BUNDLES),
                        3,
                        2,
                        Enchantment.dynamicCost(1, 10),
                        Enchantment.dynamicCost(1, 15),
                        4,
                        EquipmentSlotGroup.ANY
                ),
                entries
        );

        ModCurses.configure(provider, entries);
    }

    protected static void enchantment(ResourceKey<Enchantment> key, Enchantment.EnchantmentDefinition definition, UnaryOperator<Enchantment.Builder> builder, Entries entries) {
        entries.add(key, builder.apply(Enchantment.enchantment(definition)).build(key.location()));
    }

    protected static void enchantment(ResourceKey<Enchantment> key, Enchantment.EnchantmentDefinition definition, Entries entries) {
        enchantment(key, definition, UnaryOperator.identity(), entries);
    }

    protected static ResourceKey<Enchantment> key(String path) {
        return EnchantmentExpansion.key(path, Registries.ENCHANTMENT);
    }

    public ModEnchantments(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture, Registries.ENCHANTMENT);
    }
}
