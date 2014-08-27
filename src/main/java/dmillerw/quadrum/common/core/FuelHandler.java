package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.IFuelHandler;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.item.data.ItemLoader;
import dmillerw.quadrum.common.lib.IQuadrumBlock;
import dmillerw.quadrum.common.lib.IQuadrumItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

/**
 * @author dmillerw
 */
public class FuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        if (fuel.getItem() instanceof ItemBlock) {
            Block block = Block.getBlockFromItem(fuel.getItem());
            if (block instanceof IQuadrumBlock) {
                return BlockLoader.blockDataMap.get(((IQuadrumBlock) block).getName()).burnTime;
            }
        } else if (fuel.getItem() instanceof IQuadrumItem) {
            return ItemLoader.itemDataMap.get(((IQuadrumItem) fuel.getItem()).getName()).burnTime;
        }
        return 0;
    }
}
