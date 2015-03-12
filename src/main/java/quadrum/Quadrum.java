package quadrum;

import java.io.File;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

import quadrum.client.ResourcePackQuadrum;
import quadrum.lib.data.Effect;
import quadrum.lib.data.EffectDeserializer;
import quadrum.proxy.CommonProxy;

@Mod(modid = "Quadrum", name = "Quadrum", version = "@Version@")
public class Quadrum {

	@SidedProxy(serverSide = "quadrum.proxy.CommonProxy", clientSide = "quadrum.proxy.ClientProxy")
	public static CommonProxy proxy;

	@Mod.Instance("Quadrum")
	public static Quadrum instance;

	public static Gson gson;

	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Effect.class, new EffectDeserializer());
		gsonBuilder.setPrettyPrinting();
		gson = gsonBuilder.create();
	}

	public static Logger logger;

	public static File configDir;
	public static File blockDir;
	public static File itemDir;

	public static boolean textureStackTrace;
	public static boolean dumpBlockMap;
	public static boolean dumpItemMap;

	public static void log(Level level, String message, Object... args) {
		logger.log(level, String.format(message, args));
	}

	//Putting it in the constructor makes sure to add the ResourcePack before the first scan by FML so it won't get logged as not found on the first texture load.
	public Quadrum() {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
			Minecraft.getMinecraft().defaultResourcePacks.add(new ResourcePackQuadrum());
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		configDir = new File(event.getModConfigurationDirectory(), "Quadrum/");
		blockDir = new File(configDir, "block/");
		itemDir = new File(configDir, "item/");

		if (!blockDir.exists())
			blockDir.mkdirs();

		if (!itemDir.exists())
			itemDir.mkdirs();

		if (event.getSide() == Side.CLIENT) {
			File textures = new File(configDir, "textures/");
			File blockTextures = new File(textures, "blocks/");
			File itemTextures = new File(textures, "items/");
			File lang = new File(configDir, "lang/");

			if (!textures.exists())
				textures.mkdirs();
			if (!blockTextures.exists())
				blockTextures.mkdirs();
			if (!itemTextures.exists())
				itemTextures.mkdirs();
			if (!lang.exists())
				lang.mkdirs();
		}

		proxy.preInit(event);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}
