package net.fabricmc.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;

public class EnhancedAttackIndicator implements ModInitializer {

	@Override
	public void onInitialize() {
	}

	public static float getProgress() {

		float breakingProgress = ((ClientPlayerInteractionManagerAccessor)MinecraftClient.getInstance().interactionManager).getCurrentBreakingProgress();

		if (breakingProgress > 0)
			return breakingProgress;

		ClientPlayerEntity player = MinecraftClient.getInstance().player;

		ItemStack stack = player.getActiveItem();
		Item item = stack.getItem();


		if (item == Items.BOW) {
			float progress = BowItem.getPullProgress(72000 - player.getItemUseTimeLeft());
			return progress == 1.0F? 2.0F : progress;
		}
		else if (item == Items.CROSSBOW) {
			float progress = (stack.getMaxUseTime() - player.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(stack);
			return progress >= 1.0F? 2.0F : progress;
		}
		else if (item == Items.TRIDENT) {
			float progress = (stack.getMaxUseTime() - player.getItemUseTimeLeft()) / 10.0F;
			return progress >= 1.0F? 2.0F : progress;
		}
		else if (item.isFood()) {
			float itemCooldown = (float) player.getItemUseTime() / stack.getMaxUseTime();
			return itemCooldown == 0.0F? 1.0F : itemCooldown;
		}

		stack = player.getMainHandStack();
		if (stack == ItemStack.EMPTY)
			stack = player.getOffHandStack();
		if (stack == ItemStack.EMPTY)
			return 1.0F;

		item = stack.getItem();

		if (item == Items.CROSSBOW && stack.getTag().getBoolean("Charged"))
			return 2.0F;

		float itemCooldown = player.getItemCooldownManager().getCooldownProgress(item, 0);
		return itemCooldown == 0.0F? 1.0F : itemCooldown;
	}

}
