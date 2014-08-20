package dmillerw.quadrum.client.texture;

import dmillerw.quadrum.Quadrum;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
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

    private boolean block;

    protected CustomAtlasSprite(String name, boolean block) {
        super(name);
        this.block = block;
    }

    @Override
    public void generateMipmaps(int length) {

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
            return false;
        }

        if (image != null && !failed) {
            loadSprite(new BufferedImage[] {image}, null, false);
        } else {
            Quadrum.log(Level.WARN, "Failed to load texture %s", (location.getResourcePath() + ".png"));
        }

        return false;
    }
}
