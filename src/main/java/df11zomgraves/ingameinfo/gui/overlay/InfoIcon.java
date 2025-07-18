package df11zomgraves.ingameinfo.gui.overlay;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;

import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.util.Vector2f;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public class InfoIcon extends Info {
	private final ResourceLocation resourceLocation;
	private final Vector2f xy0 = new Vector2f();
	private final Vector2f xy1 = new Vector2f();
	private final Vector2f uv0 = new Vector2f();
	private final Vector2f uv1 = new Vector2f();
	private int displayWidth;
	private int displayHeight;

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

		this.xy0.set(displayX, displayY);
		this.xy1.set(displayX + displayWidth, displayY + displayHeight);
	}

	public void setTextureData(final int iconX, final int iconY, final int iconWidth, final int iconHeight,
			final int textureWidth, final int textureHeight) {
		this.uv0.set((float) iconX / textureWidth, (float) iconY / textureHeight);
		this.uv1.set((float) (iconX + iconWidth) / textureWidth, (float) (iconY + iconHeight) / textureHeight);
	}

	@Override
	public void drawInfo(GuiGraphics guiGraphics) {
		float x1 = xy0.x + this.x + this.offsetX;
		float y1 = xy0.y + this.y + this.offsetY;
		float x2 = xy1.x + this.x + this.offsetX;
		float y2 = xy1.y + this.y + this.offsetY;
		try {
			RenderSystem.setShader(GameRenderer::getPositionTexShader);
			RenderSystem.setShaderTexture(0, this.resourceLocation);
			final Matrix4f pose = guiGraphics.pose().last().pose();
			RenderSystem.enableBlend();
			Tesselator tessellator = Tesselator.getInstance();
			BufferBuilder bufferbuilder = tessellator.getBuilder();
			bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
			bufferbuilder.vertex(pose, x1, y1, 0).uv(this.uv0.x, this.uv0.y).endVertex();
			bufferbuilder.vertex(pose, x1, y2, 0).uv(this.uv0.x, this.uv1.y).endVertex();
			bufferbuilder.vertex(pose, x2, y2, 0).uv(this.uv1.x, this.uv1.y).endVertex();
			bufferbuilder.vertex(pose, x2, y1, 0).uv(this.uv1.x, this.uv0.y).endVertex();
			BufferUploader.drawWithShader(bufferbuilder.end());
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
