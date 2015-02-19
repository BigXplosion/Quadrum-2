package quadrum.client.render.ctm;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class ConnectedTexture {

	public IIcon icon;
	public TextureSubmap submap;
	public TextureSubmap submapSmall;

	public ConnectedTexture(IIconRegister reg, String iconName) {
		this.icon = ConnectedTextureHandler.registerIcon(reg, iconName);
		this.submap = new TextureSubmap(ConnectedTextureHandler.registerIcon(reg, iconName + "-ctm"), 4, 4);
		this.submapSmall = new TextureSubmap(this.icon, 2, 2);
	}
}
