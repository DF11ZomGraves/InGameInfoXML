package df11zomgraves.ingameinfo.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import df11zomgraves.ingameinfo.InGameInfoXML;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class InfoIcon extends Info {
	private final ResourceLocation resourceLocation;
	private int displayWidth;
	private int displayHeight;
	private int textureHeight;
	private int textureWidth;
	private float iconX;
	private float iconY;
	private int iconWidth;
	private int iconHeight;

	public InfoIcon(final String location) {
		this(new ResourceLocation(location));
	}

	public InfoIcon(final String location, final int displayX, final int displayY, final int displayWidth,
			final int displayHeight, final int iconX, final int iconY, final int iconWidth, final int iconHeight,
			final int textureWidth, final int textureHeight, final int x, final int y) {
		this(new ResourceLocation(location), displayX, displayY, displayWidth, displayHeight, iconX, iconY, iconWidth,
				iconHeight, textureWidth, textureHeight, x, y);
	}

	public InfoIcon(final ResourceLocation location) {
		this(location, 0, 0, 8, 8, 0, 0, 8, 8, 8, 8, 0, 0);
	}

	public InfoIcon(final ResourceLocation location, final int displayX, final int displayY, final int displayWidth,
			final int displayHeight, final int iconX, final int iconY, final int iconWidth, final int iconHeight,
			final int textureWidth, final int textureHeight, final int x, final int y) {
		super(x, y);
		this.resourceLocation = location;
		setDisplayDimensions(displayX, displayY, displayWidth, displayHeight);
		setTextureData(iconX, iconY, iconWidth, iconHeight, textureWidth, textureHeight);
	}

	public void setDisplayDimensions(final int displayX, final int displayY, final int displayWidth,
			final int displayHeight) {
		this.displayWidth = displayWidth;
		this.displayHeight = displayHeight;
	}

	public void setTextureData(final int iconX, final int iconY, final int iconWidth, final int iconHeight,
			final int textureWidth, final int textureHeight) {
		this.textureHeight = textureHeight;
		this.textureWidth = textureWidth;
		this.iconWidth = iconWidth;
		this.iconHeight = iconHeight;
		this.iconX = iconX;
		this.iconY = iconY;
	}

	@Override
	public void drawInfo(PoseStack matrix) {
		try {
			matrix.pushPose();
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, this.resourceLocation);
			RenderSystem.enableBlend();
			Gui.blit(matrix, this.x + this.offsetX, this.y + this.offsetY, this.displayWidth, this.displayHeight, this.iconX, this.iconY,
					this.iconWidth, this.iconHeight, this.textureWidth, this.textureHeight);
			matrix.popPose();
		} catch (final Exception e) {
			InGameInfoXML.logger.debug(e);
		}
	}

	@Override
	public int getWidth() {
		return this.displayWidth;
	}

	@Override
	public int getHeight() {
		return this.displayHeight;
	}

	@Override
	public String toString() {
		return String.format("InfoIcon{resource: %s, x: %d, y: %d, offsetX: %d, offsetY: %d, children: %s}",
				this.resourceLocation, this.x, this.y, this.offsetX, this.offsetY, this.children);
	}
}
