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
    public void initSprite(int p_110971_1_, int p_110971_2_, int p_110971_3_, int p_110971_4_, boolean p_110971_5_) {
        super.initSprite(p_110971_1_, p_110971_2_, p_110971_3_, p_110971_4_, p_110971_5_);
        lastWidth = p_110971_1_;
        lastHeight = p_110971_2_;
        lastX = p_110971_3_;
        lastY = p_110971_4_;
    }

    public void restore() {
        initSprite(lastWidth, lastHeight, lastX, lastY, false);
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        boolean failed = false;
        BufferedImage image = null;

        try {
            if (block) {
                image = ImageIO.read(new File(Quadrum.blockTextureDir, location.getResourcePath() + ".png"));
            } else {
                image = ImageIO.read(new File(Quadrum.itemTextureDir, location.getResourcePath() + ".png"));
            }
        } catch (IOException ex) {
            Quadrum.log(Level.WARN, "Failed to load texture %s. Reason: %s", (location.getResourcePath() + ".png"), ex.getMessage());
            failed = true;
            return true;
        }

        if (image != null && !failed) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            BufferedImage[] array = new BufferedImage[1 + gameSettings.mipmapLevels];
            array[0] = image;
            this.loadSprite(array, null, (float) gameSettings.anisotropicFiltering > 1.0F);
            return false;
        } else {
            Quadrum.log(Level.WARN, "Failed to load texture %s", (location.getResourcePath() + ".png"));
        }

        return true;
    }
}
