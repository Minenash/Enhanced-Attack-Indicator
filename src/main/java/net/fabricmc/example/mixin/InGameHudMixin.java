package net.fabricmc.example.mixin;

import net.fabricmc.example.EnhancedAttackIndicator;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	boolean renderFullness = false;

	@Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
	private float setBarProgress(ClientPlayerEntity player, float baseTime) {
		float progress = player.getAttackCooldownProgress(baseTime);
		if (progress < 1.0F)
			return progress;

		progress = EnhancedAttackIndicator.getProgress();

		if (progress == 2.0F)
			renderFullness = true;

		return progress == 2.0F ? 1.0F : progress;

	}

//	@Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", ordinal = 2,target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"))
//	private void addFullness(InGameHud hud, MatrixStack matrices, int x, int y, int _u, int v, int width, int _height) {
//		hud.drawTexture(matrices, x, y, renderFullness? 68 : 36, v, width, renderFullness? 16 : 4);
//		renderFullness = false;
//	}

	@ModifyVariable(method = "renderCrosshair", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;targetedEntity:Lnet/minecraft/entity/Entity;"), index = 5)
	private boolean showPlus(boolean _original) {
		boolean render = renderFullness;
		renderFullness = false;
		return render;
	}
}
