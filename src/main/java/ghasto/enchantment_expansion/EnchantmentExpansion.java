package ghasto.enchantment_expansion;

import ghasto.enchantment_expansion.data.EnglishLangProvider;
import ghasto.enchantment_expansion.data.ModCurses;
import ghasto.enchantment_expansion.data.ModEnchantments;
import ghasto.enchantment_expansion.data.tags.ModBlockTags;
import ghasto.enchantment_expansion.data.tags.ModEnchantmentTags;
import ghasto.enchantment_expansion.data.tags.ModItemTags;
import ghasto.enchantment_expansion.events.ClumsinessEvent;
import ghasto.enchantment_expansion.events.RetributionEvent;
import ghasto.enchantment_expansion.events.StoneMendingEvent;
import ghasto.enchantment_expansion.registry.ModEnchantmentEffectComponents;
import ghasto.enchantment_expansion.registry.ModEnchantmentEffects;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnchantmentExpansion implements ModInitializer, DataGeneratorEntrypoint {
	public static final String MOD_ID = "enchantment_expansion";
	public static final Logger LOGGER = LoggerFactory.getLogger("Enchantment Expansion");

	@Override
	public void onInitialize() {
		ModEnchantmentEffectComponents.register();
		ModEnchantmentEffects.register();

		AttackEntityCallback.EVENT.register(new RetributionEvent());

		PlayerBlockBreakEvents.AFTER.register(new StoneMendingEvent());

		AttackEntityCallback.EVENT.register(new ClumsinessEvent());
		PlayerBlockBreakEvents.AFTER.register(new ClumsinessEvent());
	}

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack datapack = generator.createPack();
		datapack.addProvider(ModEnchantments::new);
		datapack.addProvider(ModItemTags::new);
		datapack.addProvider(ModBlockTags::new);
		datapack.addProvider(ModEnchantmentTags::new);
		datapack.addProvider(EnglishLangProvider::new);
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path.toLowerCase());
	}

	public static <T>ResourceKey<T> key(String path, ResourceKey<Registry<T>> registry) {
		return ResourceKey.create(registry, asResource(path));
	}
}