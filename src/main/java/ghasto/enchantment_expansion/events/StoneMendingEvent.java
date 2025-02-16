package ghasto.enchantment_expansion.events;

import ghasto.enchantment_expansion.data.tags.ModBlockTags;
import ghasto.enchantment_expansion.data.ModEnchantments;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class StoneMendingEvent implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        ItemStack item = player.getMainHandItem();
        if (item.isDamaged() && blockState.is(ModBlockTags.STONE_MENDING_APPLICABLE)) {
            if (player.getRandom().nextFloat() <= 0.25 * item.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(ModEnchantments.STONE_MENDING, level))) {
                item.setDamageValue(item.getDamageValue() - 1);
            }
        }
    }
}
