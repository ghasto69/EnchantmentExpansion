package ghasto.enchantment_expansion.registry;

import com.ghasto.froglight.registry.FroglightDynamicRegistry;
import com.ghasto.froglight.util.DynamicRegistryContext;
import ghasto.enchantment_expansion.EnchantmentExpansion;
import ghasto.enchantment_expansion.content.enchantment_effects.*;
import ghasto.enchantment_expansion.data.tags.ModBlockTags;
import ghasto.enchantment_expansion.data.tags.ModEnchantmentTags;
import ghasto.enchantment_expansion.data.tags.ModItemTags;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModEnchantments {
    /* Bow Enchantments */
    // True Shot: Makes arrows fired by the enchanted bow unaffected by gravity.
    public static final ResourceKey<Enchantment> BREEZE = register("breeze", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.BOW_ENCHANTABLE),
                            10,
                            2,
                            Enchantment.constantCost(10),
                            Enchantment.constantCost(20),
                            4,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .exclusiveWith(context.lookup(Registries.ENCHANTMENT).getOrThrow(EnchantmentTags.BOW_EXCLUSIVE))
            .withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new Breeze(LevelBasedValue.perLevel(0.7f, -0.1f)))
    );
    //Quick Draw: Draws bow faster, levels 1-3.
    public static final ResourceKey<Enchantment> QUICK_DRAW = register("quick_draw", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.BOW_ENCHANTABLE),
                            10,
                            3,
                            Enchantment.constantCost(10),
                            Enchantment.constantCost(20),
                            4,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .withSpecialEffect(ModEnchantmentEffectComponents.BOW_CHARGE_TIME, new AddValue(LevelBasedValue.perLevel(-0.25f)))
    );
    //Paralysis: Applies weakness and slowness to entity hit by the arrow of the enchanted bow, levels 1-3.
    public static final ResourceKey<Enchantment> PARALYSIS = register("paralysis", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.BOW_ENCHANTABLE),
                            10,
                            3,
                            Enchantment.constantCost(10),
                            Enchantment.constantCost(20),
                            4,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new TippedArrowEffect(
                    LevelBasedValue.perLevel(10),
                    MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
            ))
    );
    //Floating: Applies shulker floating to entity hit by the arrow of the enchanted bow, levels 1-3.
    public static final ResourceKey<Enchantment> FLOATING = register("floating", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.BOW_ENCHANTABLE),
                            10,
                            3,
                            Enchantment.constantCost(10),
                            Enchantment.constantCost(20),
                            4,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .withEffect(EnchantmentEffectComponents.PROJECTILE_SPAWNED, new TippedArrowEffect(
                    LevelBasedValue.perLevel(7),
                    MobEffects.LEVITATION
            ))
    );

    /* Weapon Enchantments */
    //Ice Aspect: Inflicts weakness and slowness on the entity being attacked, levels 1-2
    public static final ResourceKey<Enchantment> FROSTBITE = register("frostbite", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.FIRE_ASPECT_ENCHANTABLE),
                            10,
                            2,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(1, 15),
                            5,
                            EquipmentSlotGroup.HAND
                    ))
            .exclusiveWith(context.lookup(Registries.ENCHANTMENT).getOrThrow(ModEnchantmentTags.FIRE_ASPECT_EXCLUSIVE))
            .withEffect(
                    EnchantmentEffectComponents.POST_ATTACK,
                    EnchantmentTarget.ATTACKER,
                    EnchantmentTarget.VICTIM,
                    new MobEffect(
                            LevelBasedValue.perLevel(7),
                            MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.DIG_SLOWDOWN
                    )
            )
    );

    //Wisdom: Increases experience dropped by mobs
    public static final ResourceKey<Enchantment> WISDOM = register("wisdom", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.SHARP_WEAPON_ENCHANTABLE),
                            4,
                            5,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(1, 15),
                            6,
                            EquipmentSlotGroup.HAND
                    ))
            .withEffect(
                    EnchantmentEffectComponents.MOB_EXPERIENCE,
                    new AddValue(LevelBasedValue.perLevel(2f))
            )
    );
    //Swift Attack: Increases your attack speed
    public static final ResourceKey<Enchantment> SWIFT_ATTACK = register("swift_attack", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                            6,
                            5,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(1, 15),
                            3,
                            EquipmentSlotGroup.MAINHAND
                    ))
            .withEffect(
                    EnchantmentEffectComponents.ATTRIBUTES,
                    new EnchantmentAttributeEffect(
                            EnchantmentExpansion.asResource("swift_attack"),
                            Attributes.ATTACK_SPEED,
                            LevelBasedValue.perLevel(0.1f),
                            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    )
            )
    );
    //Lifesteal: Heals you when hit an entity
    public static final ResourceKey<Enchantment> LIFESTEAL = register("lifesteal", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                            4,
                            2,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(1, 15),
                            6,
                            EquipmentSlotGroup.HAND
                    ))
            .withEffect(
                    EnchantmentEffectComponents.POST_ATTACK,
                    EnchantmentTarget.ATTACKER,
                    EnchantmentTarget.ATTACKER,
                    new Lifesteal(LevelBasedValue.perLevel(0.5f))
            )
    );

    /* Armor Enchantments */
    //Stride: Increases the player's step height to 1 block
    public static final ResourceKey<Enchantment> STRIDE = register("stride", context -> Enchantment.enchantment(
                    Enchantment.definition(
                            context.lookup(Registries.ITEM).getOrThrow(ItemTags.FOOT_ARMOR_ENCHANTABLE),
                            3,
                            1,
                            Enchantment.dynamicCost(1, 10),
                            Enchantment.dynamicCost(1, 15),
                            4,
                            EquipmentSlotGroup.FEET
                    ))
            .withEffect(
                    EnchantmentEffectComponents.ATTRIBUTES,
                    new EnchantmentAttributeEffect(
                            EnchantmentExpansion.asResource("stride"),
                            Attributes.STEP_HEIGHT,
                            LevelBasedValue.perLevel(0.4f),
                            AttributeModifier.Operation.ADD_VALUE
                    )
            )
    );
    //Retribution: Damages the attacker's item more
    public static final ResourceKey<Enchantment> RETRIBUTION = register("retribution", context -> Enchantment.enchantment(
            Enchantment.definition(
                    context.lookup(Registries.ITEM).getOrThrow(ItemTags.ARMOR_ENCHANTABLE),
                    3,
                    2,
                    Enchantment.dynamicCost(1, 10),
                    Enchantment.dynamicCost(1, 15),
                    4,
                    EquipmentSlotGroup.ARMOR
            ))
    );

    /*  Tool Enchantments */
    //Stone Mending: Tool has a chance not to lose durability when mining stone related blocks
    public static final ResourceKey<Enchantment> STONE_MENDING = register("stone_mending", context -> Enchantment.enchantment(
            Enchantment.definition(
                    context.lookup(Registries.ITEM).getOrThrow(ItemTags.PICKAXES),
                    3,
                    2,
                    Enchantment.dynamicCost(1, 10),
                    Enchantment.dynamicCost(1, 15),
                    4,
                    EquipmentSlotGroup.MAINHAND
            ))
    );

    /* Misc */
    //Capacity: Bundles can store more items
    public static final ResourceKey<Enchantment> CAPACITY = register("capacity", context -> Enchantment.enchantment(
            Enchantment.definition(
                    context.lookup(Registries.ITEM).getOrThrow(ModItemTags.BUNDLES),
                    3,
                    3,
                    Enchantment.dynamicCost(1, 10),
                    Enchantment.dynamicCost(1, 15),
                    4,
                    EquipmentSlotGroup.ANY
            ))
    );

    public static void registerEnchantmentEvents() {
        //Retribution
        AttackEntityCallback.EVENT.register((attacker, level, hand, entity, hitResult) -> {
            if (level.isClientSide() || !(entity instanceof LivingEntity victim))
                return InteractionResult.PASS;

            ItemStack attackingItem = attacker.getWeaponItem();
            if (attackingItem.isEmpty() || !attackingItem.isDamageableItem())
                return InteractionResult.PASS;

            // Iterate through the victim's armor slots
            for (ItemStack armorStack : victim.getArmorSlots()) {
                // Get the level of the Retribution enchantment
                int retributionLevel = armorStack.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(RETRIBUTION, level));
                if (retributionLevel > 0) {
                    int currentDamage = attackingItem.getDamageValue();

                    // Apply damage or consume the item
                    if (currentDamage > retributionLevel) {
                        attackingItem.setDamageValue(currentDamage + retributionLevel);
                    } else {
                        return InteractionResult.CONSUME;
                    }
                }
            }
            return InteractionResult.PASS;
        });

        //Stone Mending
        PlayerBlockBreakEvents.AFTER.register((level, player, pos, state, blockEntity) -> {
            ItemStack item = player.getMainHandItem();
            if (item.isDamaged() && state.is(ModBlockTags.STONE_MENDING_APPLICABLE)) {
                if (player.getRandom().nextFloat() <= 0.25 * item.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(ModEnchantments.STONE_MENDING, level))) {
                    item.setDamageValue(item.getDamageValue() - 1);
                }
            }
        });
    }

    protected static ResourceKey<Enchantment> register(String name, Function<DynamicRegistryContext, Enchantment.Builder> factory) {
        ResourceLocation location = EnchantmentExpansion.asResource(name);
        ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, location);
        FroglightDynamicRegistry.register(Registries.ENCHANTMENT, key, context -> factory.apply(context).build(location));
        return key;
    }

    public static void registerEnchantments() {
    }
}
