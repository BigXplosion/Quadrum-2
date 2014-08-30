package dmillerw.quadrum.client.texture;

import dmillerw.quadrum.Quadrum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Level;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author dmillerw
 */
public class CustomAtlasSprite extends TextureAtlasSprite {

    private int lastMapWidth;
    private int lastMapHeight;
    private int lastWidth;
    private int lastHeight;
    private int lastX;
    private int lastY;

    private boolean block;

    protected CustomAtlasSprite(String name, boolean block) {
        super(name);
        this.block = block;
    }

    @Override
    public void initSprite(int mapWidth, int mapHeight, int originX, int originY, boolean rotated) {
        super.initSprite(mapWidth, mapHeight, originX, originY, rotated);
        lastMapWidth = mapWidth;
        lastMapHeight = mapHeight;
        lastX = originX;
        lastY = originY;
    }

    public void restore() {
        this.width = lastWidth;
        this.height = lastHeight;
        initSprite(lastMapWidth, lastMapHeight, lastX, lastY, false);
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        BufferedImage image = null;

        try {
            if (block) {
                image = ImageIO.read(new File(Quadrum.blockTextureDir, location.getResourcePath() + ".png"));
            } else {
                image = ImageIO.read(new File(Quadrum.itemTextureDir, location.getResourcePath() + ".png"));
            }
        } catch (IOException ex) {
            Quadrum.log(Level.WARN, "Failed to load texture %s. Reason: %s", (location.getResourcePath() + ".png"), ex.getMessage());
            if (block) {
                TextureLoader.INSTANCE.removeBlockIcon(this.getIconName());
            } else {
                TextureLoader.INSTANCE.removeItemIcon(this.getIconName());
            }
            return true;
        } finally {
            if (image != null) {
                lastWidth = image.getWidth();
                lastHeight = image.getHeight();
                GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
                BufferedImage[] array = new BufferedImage[1 + gameSettings.mipmapLevels];
                array[0] = image;
                this.loadSprite(array, null, (float) gameSettings.anisotropicFiltering > 1.0F);
            }
        }

        return true;
    }
}
