package dmillerw.quadrum.client.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.core.CommonProxy;
import net.minecraftforge.common.MinecraftForge;

/**
 * @author dmillerw
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        MinecraftForge.EVENT_BUS.register(TextureLoader.INSTANCE);
    }
}
