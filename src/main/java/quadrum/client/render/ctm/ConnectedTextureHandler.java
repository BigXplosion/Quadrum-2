package quadrum.client.render.ctm;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import quadrum.util.Utils;

import gnu.trove.map.TMap;
import gnu.trove.map.hash.THashMap;

public class ConnectedTextureHandler {

	private static TMap<NameMetaPair, ConnectedTexture[]> connectedTextures = new THashMap<NameMetaPair, ConnectedTexture[]>();

	static IIcon registerIcon(IIconRegister reg, String icon) {
		return reg.registerIcon(Utils.getIconForRegister(icon));
	}

	public static void registerConnectedTexture(IIconRegister reg, Block block, int meta, String icon) {
		ConnectedTexture[] textures = new ConnectedTexture[6];
		for (int i = 0; i < 6; i++)
			textures[i] = new ConnectedTexture(reg, icon);
		connectedTextures.put(new NameMetaPair(block, meta), textures);
	}

	public static void registerConnectedTexture(IIconRegister reg, Block block, int meta, int side, String icon) {
		NameMetaPair pair = new NameMetaPair(block, meta);
		ConnectedTexture[] textures;
		if (connectedTextures.get(pair) == null)
			textures = new ConnectedTexture[6];
		else
			textures = connectedTextures.get(pair);
		textures[side] = new ConnectedTexture(reg, icon);
		connectedTextures.put(pair, textures);
	}

	public static ConnectedTexture getConnectedTexture(Block block, int meta, int side) {
		NameMetaPair pair = new NameMetaPair(block, meta);
		if (connectedTextures.get(pair) != null && connectedTextures.get(pair)[side] != null)
			return connectedTextures.get(pair)[side];
		else
			return null;
	}
}
