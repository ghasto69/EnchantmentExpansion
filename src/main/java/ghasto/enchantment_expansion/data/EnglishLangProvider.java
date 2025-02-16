package ghasto.enchantment_expansion.data;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translations) {
        /* Bow Enchantments */
        translations.addEnchantment(ModEnchantments.BREEZE, "Breeze");
        translations.addEnchantment(ModEnchantments.PARALYSIS, "Paralysis");
        translations.addEnchantment(ModEnchantments.QUICK_DRAW, "Quick Draw");
        translations.addEnchantment(ModEnchantments.FLOATING, "Floating");

        /* Sword Enchantments */
        translations.addEnchantment(ModEnchantments.FROSTBITE, "Frostbite");
        translations.addEnchantment(ModEnchantments.LIFESTEAL, "Lifesteal");
        translations.addEnchantment(ModEnchantments.WISDOM, "Wisdom");
        translations.addEnchantment(ModEnchantments.SWIFT_ATTACK, "Swift Attack");

        /* Armor Enchantments */
        translations.addEnchantment(ModEnchantments.STRIDE, "Stride");
        translations.addEnchantment(ModEnchantments.RETRIBUTION, "Retribution");

        /* Tool Enchantments */
        translations.addEnchantment(ModEnchantments.STONE_MENDING, "Stone Mending");

        try {
            Path existingFilePath = dataOutput.getModContainer().findPath("assets/" + EnchantmentExpansion.MOD_ID + "/lang/en_us.existing.json").get();
            translations.add(existingFilePath);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add existing language file!", e);
        }
    }
}