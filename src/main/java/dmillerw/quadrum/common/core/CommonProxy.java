package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.block.ItemBlockQuadrum;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import dmillerw.quadrum.common.lib.LanguageHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author dmillerw
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        BlockLoader.initialize();
        ItemLoader.initialize();

        LanguageHelper.loadDirectory(Quadrum.blockLangDir);
        LanguageHelper.loadDirectory(Quadrum.itemLangDir);

        for (BlockData blockData : BlockLoader.blockDataMap.values()) {
            if (blockData != null) {
                Block block = blockData.getBlockType().createBlock(blockData);
                GameRegistry.registerBlock(block, ItemBlockQuadrum.class, blockData.name);
                BlockLoader.blockMap.put(blockData.name, block);
            }
        }

        for (ItemData itemData : ItemLoader.itemDataMap.values()) {
            if (itemData != null) {
                Item item = itemData.getItemType().createItem(itemData);
                GameRegistry.registerItem(item, itemData.name);
                ItemLoader.itemMap.put(itemData.name, item);
            }
        }

        GameRegistry.registerFuelHandler(new FuelHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        BlockLoader.verifyDrops();
    }
}
