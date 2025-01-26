package df11zomgraves.ingameinfo.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;

public class InfoText extends Info {
	private final Font fontRenderer;
	private final String text;

	public InfoText(final Font fontRenderer, final String text) {
		this(fontRenderer, text, 0, 0);
	}

	public InfoText(final Font fontRenderer, final String text, final int x, final int y) {
		super(x, y);
		this.fontRenderer = fontRenderer;
		this.text = text;
	}

	@Override
	public void drawInfo(PoseStack matrix) {
		fontRenderer.drawShadow(matrix, this.text, x, y, 0xFFFFFFFF);
	}

	@Override
	public int getWidth() {
		return fontRenderer.width(this.text);
	}

	@Override
	public int getHeight() {
		return fontRenderer.lineHeight;
	}

	@Override
	public String toString() {
		return String.format("InfoText{text: %s, x: %d, y: %d, offsetX: %d, offsetY: %d, children: %s}", this.text,
				this.x, this.y, this.offsetX, this.offsetY, this.children);
	}
}
