package dmillerw.quadrum.client.texture;

import dmillerw.quadrum.Quadrum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
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
public class QuadrumSprite extends TextureAtlasSprite {

    public static QuadrumSprite safelyConstruct(String name, boolean block) {
        // Verify if the texture actually exists
        boolean exists;
        if (block) {
            exists = new File(Quadrum.blockTextureDir, name + ".png").exists();
        } else {
            exists = new File(Quadrum.itemTextureDir, name + ".png").exists();
        }
        return exists ? new QuadrumSprite(name, block) : null;
    }

    public boolean block;
    public boolean failed;

    public QuadrumSprite(String name, boolean block) {
        super(name);
        this.block = block;
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location) {
        BufferedImage image;
        try {
            if (block) {
                image = ImageIO.read(new File(Quadrum.blockTextureDir, location.getResourcePath() + ".png"));
            } else {
                image = ImageIO.read(new File(Quadrum.itemTextureDir, location.getResourcePath() + ".png"));
            }
        } catch (IOException ex) {
            Quadrum.log(Level.WARN, "Failed to load " + (block ? "block" : "item") + " texture %s. Reason: %s", (location.getResourcePath() + ".png"), ex.getMessage());
            if (Quadrum.textureStackTrace) ex.printStackTrace();
            failed = true;
            return true;
        }

        if (image != null) {
            GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
            BufferedImage[] array = new BufferedImage[1 + gameSettings.mipmapLevels];
            array[0] = image;
            this.loadSprite(array, null, (float) gameSettings.anisotropicFiltering > 1.0F);
            return false;
        }

        return true;
    }

    public boolean isEmpty() {
        return height == 0 || width == 0;
    }

    public QuadrumSprite register(TextureMap textureMap) {
        textureMap.setTextureEntry(getIconName(), this);
        return this;
    }
}
