package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.quadrum.common.block.BlockCustom;
import dmillerw.quadrum.common.block.ItemBlockCustom;
import dmillerw.quadrum.common.data.BlockData;
import dmillerw.quadrum.common.data.BlockLoader;
import net.minecraft.block.Block;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockLoader.initialize();

        for (BlockData blockData : BlockLoader.blocks) {
            if (blockData != null) {
                Block block = new BlockCustom(blockData);
                GameRegistry.registerBlock(block, ItemBlockCustom.class, blockData.name);
            }
        }
    }
}
