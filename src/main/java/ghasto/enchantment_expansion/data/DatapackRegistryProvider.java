package ghasto.enchantment_expansion.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public abstract class DatapackRegistryProvider<T> extends FabricDynamicRegistryProvider {
    private final ResourceKey<Registry<T>> registry;

    public DatapackRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, ResourceKey<Registry<T>> registry) {
        super(output, registriesFuture);
        this.registry = registry;
    }


    @Override
    public @NotNull String getName() {
        return "Datapack Registry: " + this.registry.location().getPath();
    }
}
