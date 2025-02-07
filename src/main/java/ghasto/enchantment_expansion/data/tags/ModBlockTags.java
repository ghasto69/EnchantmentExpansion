package ghasto.enchantment_expansion.data.tags;

import ghasto.enchantment_expansion.EnchantmentExpansion;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModBlockTags extends FabricTagProvider<Block> {
    public ModBlockTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.BLOCK, registriesFuture);
    }

    public static final TagKey<Block> STONE_MENDING_APPLICABLE = key("stone_mending_applicable");

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateTagBuilder(STONE_MENDING_APPLICABLE)
                .forceAddTag(ConventionalBlockTags.STONES)
                .forceAddTag(ConventionalBlockTags.COBBLESTONES)
                .add(Blocks.SANDSTONE, Blocks.RED_SANDSTONE);
    }

    private static TagKey<Block> key(String path) {
        return TagKey.create(Registries.BLOCK, EnchantmentExpansion.asResource(path));
    }
}
