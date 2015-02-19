package quadrum.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import quadrum.Quadrum;
import quadrum.client.render.ctm.RendererCTM;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		Quadrum.ctmRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(Quadrum.ctmRenderID, new RendererCTM());
	}
}
