package df11zomgraves.ingameinfo.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.world.effect.MobEffect;

public class InfoEffect extends Info {
	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private final MobEffect effect;
	private final int size;

	public InfoEffect(MobEffect effect) {
		this(effect, false, 0, 0);
	}

	public InfoEffect(MobEffect effect, final boolean large) {
		this(effect, large, 0, 0);
	}

	public InfoEffect(MobEffect effect, final boolean large, int x, int y) {
		super(x, y);
		this.effect = effect;
		this.size = large ? 18 : 9;
		if (large) {
			this.y = -2;
		}
	}

	@Override
	public int getWidth() {
		return this.size;
	}

	@Override
	public int getHeight() {
		return this.size;
	}

	@Override
	public String toString() {
		return String.format("InfoEffect{MobEffect: %s, x: %d, y: %d, offsetX: %d, offsetY: %d, children: %s}",
				this.effect, this.x, this.y, this.offsetX, this.offsetY, this.children);
	}

	@Override
	public void drawInfo(GuiGraphics guiGraphics) {
		MobEffectTextureManager textureManager = MINECRAFT.getMobEffectTextures();
		TextureAtlasSprite sprite = textureManager.get(effect);		
		PoseStack stack = guiGraphics.pose();
		stack.pushPose();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		stack.translate(getX(), getY(), 0);
		RenderSystem.setShaderTexture(0, sprite.atlasLocation());
		guiGraphics.blit(0, this.y, 0, this.size, this.size, sprite);
		stack.popPose();
	}

}
