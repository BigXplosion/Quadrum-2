package quadrum.proxy;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import quadrum.Quadrum;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Minecraft.getMinecraft().defaultResourcePacks.add(Quadrum.instance);
		super.preInit(event);
	}
}
