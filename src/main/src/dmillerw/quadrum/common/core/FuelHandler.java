package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.IFuelHandler;
import dmillerw.quadrum.common.block.BlockCustom;
import dmillerw.quadrum.common.item.ItemCustom;
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
            if (block instanceof BlockCustom) {
                return ((BlockCustom)block).data.burnTime;
            }
        } else if (fuel.getItem() instanceof ItemCustom) {
            return ((ItemCustom)fuel.getItem()).data.burnTime;
        }
        return 0;
    }
}
