package df11zomgraves.ingameinfo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

/**
 * Hides the vanilla survival HUD / potion icons when configured to do so.
 *
 * <p>Replaces the Forge {@code RenderGuiOverlayEvent.Pre} cancellation of the
 * original mod.
 */
@Mixin(Gui.class)
public abstract class InGameHudMixin {
	@Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
	private void igi$hidePlayerHealth(final GuiGraphics guiGraphics, final CallbackInfo ci) {
		if (!ConfigurationHandler.showSurvivalHUD)
			ci.cancel();
	}

	@Inject(method = "renderVehicleHealth", at = @At("HEAD"), cancellable = true)
	private void igi$hideVehicleHealth(final GuiGraphics guiGraphics, final CallbackInfo ci) {
		if (!ConfigurationHandler.showSurvivalHUD)
			ci.cancel();
	}

	@Inject(method = "renderEffects", at = @At("HEAD"), cancellable = true)
	private void igi$hideEffects(final GuiGraphics guiGraphics, final CallbackInfo ci) {
		if (!ConfigurationHandler.showOverlayPotions)
			ci.cancel();
	}
}
