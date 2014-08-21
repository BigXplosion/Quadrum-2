package dmillerw.quadrum.common.core;

import cpw.mods.fml.common.IFuelHandler;
import dmillerw.quadrum.common.block.BlockQuadrum;
import dmillerw.quadrum.common.item.ItemQuadrum;
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
            if (block instanceof BlockQuadrum) {
                return ((BlockQuadrum)block).data.burnTime;
            }
        } else if (fuel.getItem() instanceof ItemQuadrum) {
            return ((ItemQuadrum)fuel.getItem()).data.burnTime;
        }
        return 0;
    }
}
