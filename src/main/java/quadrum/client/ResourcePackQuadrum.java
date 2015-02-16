package quadrum.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import quadrum.Quadrum;

public class ResourcePackQuadrum implements IResourcePack {

	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		if (resourceExists(location))
			return new FileInputStream(getResource(location));
		return null;
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		return getResource(location) != null;
	}

	@Override
	public Set getResourceDomains() {
		Set<String> domains = new HashSet<String>();
		domains.add("qResources");
		return domains;
	}

	@Override
	public IMetadataSection getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return null;
	}

	@Override
	public String getPackName() {
		return "CustomQuadrumResources";
	}

	private File getResource(ResourceLocation location) {
		File block = new File(Quadrum.blockTextureDir, location.getResourcePath());
		if (block.exists())
			return block;

		File item = new File(Quadrum.itemTextureDir, location.getResourcePath());
		if (item.exists())
			return item;

		return null;
	}
}
