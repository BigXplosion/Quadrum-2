package dmillerw.quadrum.common.handler;

import cpw.mods.fml.common.IFuelHandler;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.IQuadrumObject;
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
            if (block instanceof IQuadrumObject) {
                return ((BlockData)((IQuadrumObject) block).get()).burnTime;
            }
        } else if (fuel.getItem() instanceof IQuadrumObject) {
            return ((ItemData)((IQuadrumObject) fuel.getItem()).get()).burnTime;
        }
        return 0;
    }
}
