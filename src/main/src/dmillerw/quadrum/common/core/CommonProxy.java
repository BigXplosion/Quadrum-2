package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.quadrum.common.block.BlockCustom;
import dmillerw.quadrum.common.block.ItemBlockCustom;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.item.ItemCustom;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockLoader.initialize();
        ItemLoader.initialize();

        for (BlockData blockData : BlockLoader.blocks) {
            if (blockData != null) {
                Block block = new BlockCustom(blockData);
                GameRegistry.registerBlock(block, ItemBlockCustom.class, blockData.name);
            }
        }

        for (ItemData itemData : ItemLoader.items) {
            if (itemData != null) {
                Item item = new ItemCustom(itemData);
                GameRegistry.registerItem(item, itemData.name);
            }
        }
    }
}
