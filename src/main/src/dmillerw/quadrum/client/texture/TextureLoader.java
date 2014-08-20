package dmillerw.quadrum.client.texture;

import com.google.common.collect.Maps;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.quadrum.common.data.BlockData;
import dmillerw.quadrum.common.data.BlockLoader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;

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

    public static final TextureLoader INSTANCE = new TextureLoader();

    private Map<String, IIcon> blockTextures = Maps.newHashMap();
    private Map<String, IIcon> itemTextures = Maps.newHashMap();

    public IIcon getBlockIcon(String name) {
        return blockTextures.get(name);
    }

    public IIcon getItemIcon(String name) {
        return itemTextures.get(name);
    }

    @SubscribeEvent
    public void onTextureStich(TextureStitchEvent.Pre event) {
        blockTextures.clear();
        itemTextures.clear();

        if (event.map.getTextureType() == 0) {
            for (BlockData block : BlockLoader.blocks) {
                IIcon icon = new CustomAtlasSprite(block.defaultTexture, true);
                event.map.setTextureEntry(block.defaultTexture, (TextureAtlasSprite) icon);
                blockTextures.put(block.defaultTexture, icon);

                for (String string : block.textureInfo.values()) {
                    icon = new CustomAtlasSprite(string, true);
                    event.map.setTextureEntry(string, (TextureAtlasSprite) icon);
                    blockTextures.put(string, icon);
                }
            }
        } else if (event.map.getTextureType() == 1) {

        }
    }
}
