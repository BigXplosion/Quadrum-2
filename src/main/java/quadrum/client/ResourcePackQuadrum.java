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
			return new FileInputStream(new File(Quadrum.configDir, location.getResourcePath()));
		return null;
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		File file = new File(Quadrum.configDir, location.getResourcePath());
		System.out.println(file.exists() + "   " + file.getAbsolutePath());
		return file.exists();
	}

	@Override
	public Set getResourceDomains() {
		Set<String> domains = new HashSet<String>();
		domains.add("qresource");
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
}
