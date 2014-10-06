package dmillerw.quadrum.client.texture;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.IQuadrumObject;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import org.apache.logging.log4j.Level;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author dmillerw
 */
public class TextureLoader {

    public static final TextureLoader INSTANCE = new TextureLoader();

    private static final String[] BLOCK_TEXTURE_TYPES = new String[] {"front", "back", "left", "right", "top", "bottom", "default"};

    private static Map<String, Map<String, QuadrumSprite>> iconMap = Maps.newHashMap();

    public static void registerIcons(IIconRegister iconRegister, IQuadrumObject quadrumObject) {
        if (quadrumObject.get() instanceof BlockData) {
            BlockData blockData = (BlockData) quadrumObject.get();
            Map<String, QuadrumSprite> subIconMap = Maps.newHashMap();

            if (blockData.textureInfo.isEmpty()) {
                QuadrumSprite quadrumSprite = QuadrumSprite.safelyConstruct(blockData.defaultTexture, true);
                if (quadrumSprite != null) quadrumSprite.register((TextureMap) iconRegister);
                subIconMap.put("default", quadrumSprite);
            } else {
                for (String type : BLOCK_TEXTURE_TYPES) {
                    String icon = (type.equals("default") ? blockData.defaultTexture : blockData.textureInfo.get(type));
                    if (icon != null && !(icon.trim().isEmpty())) {
                        QuadrumSprite quadrumSprite = QuadrumSprite.safelyConstruct(icon, true);
                        if (quadrumSprite != null) quadrumSprite.register((TextureMap) iconRegister);
                        subIconMap.put(type, quadrumSprite);
                    }
                }
            }

            iconMap.put(blockData.name, subIconMap);
        } else if (quadrumObject.get() instanceof ItemData) {
            ItemData itemData = (ItemData) quadrumObject.get();
            Map<String, QuadrumSprite> subIconMap = Maps.newHashMap();

            QuadrumSprite quadrumSprite = QuadrumSprite.safelyConstruct(itemData.texture, false);
            if (quadrumSprite != null) quadrumSprite.register((TextureMap) iconRegister);
            subIconMap.put("default", quadrumSprite);

            iconMap.put(itemData.name, subIconMap);
        }
    }

    public static QuadrumSprite getIcon(IQuadrumObject quadrumObject, String identifier) {
        Map<String, QuadrumSprite> subMap = Maps.newHashMap();
        if (quadrumObject.get() instanceof BlockData) {
            subMap = iconMap.get(((BlockData) quadrumObject.get()).name);
        } else if (quadrumObject.get() instanceof ItemData) {
            subMap = iconMap.get(((ItemData) quadrumObject.get()).name);
        }

        if (!(subMap.containsKey(identifier))) {
            identifier = "default";
        }

        QuadrumSprite quadrumSprite = subMap.get(identifier);

        if (quadrumSprite == null || quadrumSprite.isEmpty()) {
            return null;
        }

        return quadrumSprite;
    }

    @SubscribeEvent
    public void onTextureStitched(TextureStitchEvent.Post event) {
        if (Quadrum.dumpBlockMap) {
            File file = new File(Quadrum.configDir, "block_out.png");
            dumpTexture(file);
            Quadrum.log(Level.INFO, "Dumping block texture map to " + file.getAbsolutePath());
        }

        if (Quadrum.dumpItemMap) {
            File file = new File(Quadrum.configDir, "item_out.png");
            dumpTexture(file);
            Quadrum.log(Level.INFO, "Dumping item texture map to " + file.getAbsolutePath());
        }
    }

    private void dumpTexture(File file) {
        int format = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_COMPONENTS);
        int width = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH);
        int height = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT);
        int channels = 0;
        int byteCount = 0;
        switch (format) {
            case GL11.GL_RGB: channels = 3; break;
            case GL11.GL_RGBA:
            default: channels = 4; break;
        }
        byteCount = width * height * channels;
        ByteBuffer bytes = BufferUtils.createByteBuffer(byteCount);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, format, GL11.GL_UNSIGNED_BYTE, bytes);
        final String ext = "PNG";
        for (int x=0; x<width; x++) {
            for (int y=0; y<height; y++) {
                int i = (x + (width * y)) * channels;
                int r = bytes.get(i) & 0xFF;
                int g = bytes.get(i + 1) & 0xFF;
                int b = bytes.get(i + 2) & 0xFF;
                image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
            }
        }
        try {
            ImageIO.write(image, ext, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
