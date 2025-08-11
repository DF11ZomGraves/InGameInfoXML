package df11zomgraves.ingameinfo.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class InfoItem extends Info {
	private static final Minecraft MINECRAFT = Minecraft.getInstance();
	private final ItemStack itemStack;
	private final boolean large;
	private final int size;
	private boolean forceRender;

	public InfoItem(final ItemStack itemStack) {
		this(itemStack, false);
	}

	public InfoItem(final ItemStack itemStack, final boolean large) {
		this(itemStack, large, 0, 0);
	}

	public InfoItem(final ItemStack itemStack, final boolean large, final int x, final int y) {
		this(itemStack, large, x, y, false);
	}

	public InfoItem(final ItemStack itemStack, final boolean large, final int x, final int y,
			boolean forceRender) {
		super(x, y);
		MINECRAFT.getItemRenderer();
		this.itemStack = itemStack;
		this.large = large;
		this.size = large ? 16 : 8;
		if (large) {
			this.y = -4;
		}
		this.forceRender = forceRender;
	}

	@Override
	public void drawInfo(GuiGraphics guiGraphics) {
		if (this.itemStack.isEmpty())
			return;
		PoseStack stack = RenderSystem.getModelViewStack();
		stack.pushPose();
		if (!large) {
			stack.scale(0.5f, 0.5f, 0.5f);
			stack.translate(getX() * 2, getY() * 2, 0);
		} else
			stack.translate(getX(), getY(), 0);
		RenderSystem.applyModelViewMatrix();
		guiGraphics.renderItem(itemStack, 0, 0);
		if (forceRender)
			guiGraphics.renderItemDecorations(MINECRAFT.font, itemStack, 0, 0);
		else if (ConfigurationHandler.showOverlayItemIcons)
			guiGraphics.renderItemDecorations(MINECRAFT.font, itemStack, 0, 0, "");
		stack.popPose();
		RenderSystem.applyModelViewMatrix();
	}

	@Override
	public int getWidth() {
		return this.forceRender ? this.size : !this.itemStack.isEmpty() ? this.size : 0;
	}

	@Override
	public int getHeight() {
		return this.forceRender ? this.size : !this.itemStack.isEmpty() ? this.size : 0;
	}

	@Override
	public String toString() {
		return String.format("InfoItem{itemStack: %s, x: %d, y: %d, offsetX: %d, offsetY: %d, children: %s}",
				this.itemStack, this.x, this.y, this.offsetX, this.offsetY, this.children);
	}
}
