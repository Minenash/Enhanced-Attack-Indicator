package com.minenash.enhanced_attack_indicator;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import com.minenash.enhanced_attack_indicator.config.Config;
import com.minenash.enhanced_attack_indicator.mixin.ClientPlayerInteractionManagerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.*;

public class EnhancedAttackIndicator implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		Config.init("enhanced_attack_indicator", Config.class);
	}

	public static float getProgress(float weaponProgress) {

		if (Config.weaponCoolDownImportance == Config.WeaponCoolDownImportance.FIRST && weaponProgress < 1.0F)
			return weaponProgress;

		if (Config.showBlockBreaking) {
			float breakingProgress = ((ClientPlayerInteractionManagerAccessor) MinecraftClient.getInstance().interactionManager).getCurrentBreakingProgress();

			if (breakingProgress > 0)
				return breakingProgress;
		}

		ClientPlayerEntity player = MinecraftClient.getInstance().player;

		if (Config.showRangeWeaponDraw) {
			ItemStack stack = player.getActiveItem();
			Item item = stack.getItem();

			if (item == Items.BOW) {
				float progress = BowItem.getPullProgress(72000 - player.getItemUseTimeLeft());
				return progress == 1.0F ? 2.0F : progress;
			}
			if (item == Items.CROSSBOW) {
				float progress = (stack.getMaxUseTime() - player.getItemUseTimeLeft()) / (float) CrossbowItem.getPullTime(stack);
				return progress >= 1.0F ? 2.0F : progress;
			}
			if (item == Items.TRIDENT) {
				float progress = (stack.getMaxUseTime() - player.getItemUseTimeLeft()) / 10.0F;
				return progress >= 1.0F ? 2.0F : progress;
			}
			if (item.isFood()) {
				float itemCooldown = (float) player.getItemUseTime() / stack.getMaxUseTime();
				return itemCooldown == 0.0F ? 1.0F : itemCooldown;
			}
		}

		if (Config.weaponCoolDownImportance == Config.WeaponCoolDownImportance.MIDDLE && weaponProgress < 1.0F)
			return weaponProgress;

		ItemStack mainHand = player.getMainHandStack();
		ItemStack offHand = player.getOffHandStack();

		if (Config.showItemCooldowns) {
			float cooldown = player.getItemCooldownManager().getCooldownProgress(offHand.getItem(), 0);
			if (cooldown != 0.0F)
				return cooldown;

			cooldown = player.getItemCooldownManager().getCooldownProgress(mainHand.getItem(), 0);
			if (cooldown != 0.0F)
				return cooldown;
		}

		if (Config.showRangeWeaponDraw && (mainHand.getItem() == Items.CROSSBOW && mainHand.getTag().getBoolean("Charged")
		                                 || offHand.getItem() == Items.CROSSBOW && offHand.getTag().getBoolean("Charged")))
			return 2.0F;

		if (Config.weaponCoolDownImportance == Config.WeaponCoolDownImportance.LAST)
			return weaponProgress;

		return 1.0F;

	}

}
