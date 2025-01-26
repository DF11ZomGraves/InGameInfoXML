package df11zomgraves.ingameinfo.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.util.Vector2f;
import net.minecraft.client.gui.GuiComponent;
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

    public InfoIcon(final String location, final int displayX, final int displayY,
    		final int displayWidth, final int displayHeight, final int iconX, final int iconY, final int iconWidth, final int iconHeight,
    		final int textureWidth, final int textureHeight, final int x, final int y) {
        this(new ResourceLocation(location), displayX, displayY, displayWidth, displayHeight, iconX, iconY,
        		iconWidth, iconHeight, textureWidth, textureHeight, x, y);
    }

    public InfoIcon(final ResourceLocation location) {
        this(location, 0, 0, 8, 8, 0, 0, 8, 8, 8, 8, 0, 0);
    }

    public InfoIcon(final ResourceLocation location, final int displayX, final int displayY,
    		final int displayWidth, final int displayHeight, final int iconX, final int iconY, final int iconWidth, final int iconHeight,
    		final int textureWidth, final int textureHeight, final int x, final int y) {
        super(x, y);
        this.resourceLocation = location;
        setDisplayDimensions(displayX, displayY, displayWidth, displayHeight);
        setTextureData(iconX, iconY, iconWidth, iconHeight, textureWidth, textureHeight);
    }

    public void setDisplayDimensions(final int displayX, final int displayY, final int displayWidth, final int displayHeight) {
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        this.xy0.set(displayX, displayY);
        this.xy1.set(displayX + displayWidth, displayY + displayHeight);
    }

    public void setTextureData(final int iconX, final int iconY, final int iconWidth, final int iconHeight, final int textureWidth, final int textureHeight) {
        this.uv0.set((float) iconX / textureWidth, (float) iconY / textureHeight);
        this.uv1.set((float) (iconX + iconWidth) / textureWidth, (float) (iconY + iconHeight) / textureHeight);
    }

    @Override
    public void drawInfo(PoseStack matrix) {
        try {
        	matrix.pushPose();
            RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
            matrix.translate(getX(), getY(), 0);
    		RenderSystem.setShaderTexture(0, this.resourceLocation);
    		GuiComponent.blit(matrix, (int)this.xy0.x, (int)this.xy0.y, this.xy1.x, this.xy1.y, (int)this.uv0.x, (int)this.uv0.y,
					(int)this.uv1.x, (int)this.uv1.y);
//    		GuiComponent.blit(stack, this.x, this.y, displayHeight, displayWidth, (int)this.uv0.x, (int)this.uv0.y,
//    					(int)this.uv1.x, (int)this.uv1.y, displayWidth, displayWidth);
    		
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
        return String.format("InfoIcon{resource: %s, x: %d, y: %d, offsetX: %d, offsetY: %d, children: %s}", this.resourceLocation, this.x, this.y, this.offsetX, this.offsetY, this.children);
    }
}
