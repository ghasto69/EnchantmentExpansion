package ghasto.enchantment_expansion.events;

import ghasto.enchantment_expansion.data.ModCurses;
import ghasto.enchantment_expansion.util.ModEnchantmentHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class ClumsinessEvent implements PlayerBlockBreakEvents.After, AttackEntityCallback {
    @Override
    public void afterBlockBreak(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity) {
        clumsiness(player, level);
    }

    @Override
    public InteractionResult interact(Player player, Level level, InteractionHand interactionHand, Entity entity, EntityHitResult entityHitResult) {
        clumsiness(player, level);
        return InteractionResult.PASS;
    }

    private void clumsiness(Player player, Level level) {
        ItemStack itemStack = player.getMainHandItem();
        int clumsiness = itemStack.getEnchantments().getLevel(ModEnchantmentHelper.getHolder(ModCurses.CLUMSINESS, level));
        if(clumsiness > 0) {
            if(player.getRandom().nextFloat() >= clumsiness * 0.25) {
                player.drop(itemStack.copy(), true);
                itemStack.shrink(1);
            }
        }
    }
}
