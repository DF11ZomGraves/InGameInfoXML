package df11zomgraves.ingameinfo.gui.overlay;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

public abstract class Info {
    public final List<Info> children = new ArrayList<Info>();
    public int x;
    public int y;
    public int offsetX;
    public int offsetY;

    protected Info(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.offsetX = 0;
        this.offsetY = 0;
    }

    public void draw(PoseStack matrix) {
        drawInfo(matrix);

        for (final Info child : this.children) {
            child.offsetX = this.x;
            child.offsetY = this.y;

            child.draw(matrix);
        }
    }

    public abstract void drawInfo(PoseStack matrix);

    public int getX() {
        return this.x + this.offsetX;
    }

    public int getY() {
        return this.y + this.offsetY;
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    @Override
    public String toString() {
        return "Info";
    }
}
