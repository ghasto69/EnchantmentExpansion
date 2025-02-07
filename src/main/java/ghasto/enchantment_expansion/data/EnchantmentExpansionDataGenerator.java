package ghasto.enchantment_expansion.data;

import ghasto.enchantment_expansion.data.tags.ModBlockTags;
import ghasto.enchantment_expansion.data.tags.ModEnchantmentTags;
import ghasto.enchantment_expansion.data.tags.ModItemTags;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.NotNull;

public class EnchantmentExpansionDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack datapack = generator.createPack();
		datapack.addProvider(ModItemTags::new);
		datapack.addProvider(ModBlockTags::new);
		datapack.addProvider(ModEnchantmentTags::new);
		datapack.addProvider(EnglishLangProvider::new);
	}
}
