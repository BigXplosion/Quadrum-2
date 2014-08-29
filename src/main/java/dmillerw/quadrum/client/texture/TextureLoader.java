package dmillerw.quadrum.client.texture;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
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

    public static IIcon getBlockIcon(BlockData data, String side) {
        if (side.equalsIgnoreCase("default")) {
            return INSTANCE.getBlockIcon(data.defaultTexture);
        }

        if (data.textureInfo.containsKey(side)) {
            return INSTANCE.getBlockIcon(data.textureInfo.get(side));
        } else {
            return INSTANCE.getBlockIcon(data.defaultTexture);
        }
    }

    public static IIcon getItemIcon(ItemData data) {
        return INSTANCE.getItemIcon(data.texture);
    }

    public static final TextureLoader INSTANCE = new TextureLoader();

    private TextureMap blockMap;
    private TextureMap itemMap;

    private Map<String, CustomAtlasSprite> blockMapping;
    private Map<String, CustomAtlasSprite> itemMapping;

    private void registerBlockIcon(String string) {
        if (blockMap.getTextureExtry("quadrum:" + string) != null || blockMapping.containsKey(string)) {
            return;
        }

        CustomAtlasSprite icon = new CustomAtlasSprite(string, true);
        blockMap.setTextureEntry("quadrum:" + string, icon);
        blockMapping.put(string, icon);
    }

    private void registerItemIcon(String string) {
        if (itemMap.getTextureExtry("quadrum:" + string) != null || itemMapping.containsKey(string)) {
            return;
        }

        CustomAtlasSprite icon = new CustomAtlasSprite(string, false);
        itemMap.setTextureEntry("quadrum:" + string, icon);
        itemMapping.put(string, icon);
    }

    public IIcon getBlockIcon(String name) {
        return blockMapping.get(name);
    }

    public IIcon getItemIcon(String name) {
        return itemMapping.get(name);
    }

    @SubscribeEvent
    public void onTextureStich(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            blockMap = event.map;
            blockMapping = Maps.newHashMap();
            for (BlockData block : BlockLoader.blockDataMap.values()) {
                registerBlockIcon(block.defaultTexture);

                for (String string : block.textureInfo.values()) {
                    registerBlockIcon(string);
                }
            }
        } else if (event.map.getTextureType() == 1) {
            itemMap = event.map;
            itemMapping = Maps.newHashMap();
            for (ItemData item : ItemLoader.itemDataMap.values()) {
                registerItemIcon(item.texture);
            }
        }
    }

    @SubscribeEvent
    public void onTextureStitched(TextureStitchEvent.Post event) {
        if (event.map.getTextureType() == 0) {
            for (CustomAtlasSprite customAtlasSprite : blockMapping.values()) {
                customAtlasSprite.restore();
            }
        } else if (event.map.getTextureType() == 1) {
            for (CustomAtlasSprite customAtlasSprite : itemMapping.values()) {
                customAtlasSprite.restore();
            }
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
