package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.block.ItemBlockQuadrum;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.handler.EntityDropHandler;
import dmillerw.quadrum.common.handler.FuelHandler;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import dmillerw.quadrum.common.lib.LanguageHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

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

                for (String string : blockData.oreDictionary) {
                    OreDictionary.registerOre(string, block);
                }
            }
        }

        for (ItemData itemData : ItemLoader.itemDataMap.values()) {
            if (itemData != null) {
                Item item = itemData.getItemType().createItem(itemData);
                GameRegistry.registerItem(item, itemData.name);
                ItemLoader.itemMap.put(itemData.name, item);

                for (String string : itemData.oreDictionary) {
                    OreDictionary.registerOre(string, item);
                }
            }
        }

        GameRegistry.registerFuelHandler(new FuelHandler());
        MinecraftForge.EVENT_BUS.register(new EntityDropHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        BlockLoader.verifyDrops();
    }
}
