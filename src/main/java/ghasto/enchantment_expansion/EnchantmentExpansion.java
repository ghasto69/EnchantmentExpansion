package ghasto.enchantment_expansion;

import ghasto.enchantment_expansion.mixin_interface.BundleContentsCapacityMultiplier;
import ghasto.enchantment_expansion.registry.ModCurses;
import ghasto.enchantment_expansion.registry.ModEnchantmentEffectComponents;
import ghasto.enchantment_expansion.registry.ModEnchantmentEffects;
import ghasto.enchantment_expansion.registry.ModEnchantments;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.registry.DynamicRegistrySetupCallback;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BundleContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class EnchantmentExpansion implements ModInitializer {
	public static final String MOD_ID = "enchantment_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger("Enchantment Expansion");

	@Override
	public void onInitialize() {
		ModEnchantmentEffectComponents.register();
		ModEnchantmentEffects.register();
		ModEnchantments.registerEnchantmentEvents();
		ModEnchantments.registerEnchantments();
		ModCurses.registerCurses();
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path.toLowerCase());
	}
}