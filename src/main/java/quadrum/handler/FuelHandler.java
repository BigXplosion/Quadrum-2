package quadrum.handler;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.IFuelHandler;

import quadrum.block.data.BlockData;
import quadrum.item.data.ItemData;
import quadrum.lib.IQuadrumObject;

public class FuelHandler implements IFuelHandler {
	@Override
	public int getBurnTime(ItemStack fuel) {
		if (fuel.getItem() instanceof ItemBlock) {
			Block block = Block.getBlockFromItem(fuel.getItem());
			if (block instanceof IQuadrumObject)
				return ((BlockData) ((IQuadrumObject) block).get()).burnTime;
		} else if (fuel.getItem() instanceof IQuadrumObject)
			return ((ItemData) ((IQuadrumObject) fuel.getItem()).get()).burnTime;
		return 0;
	}
}
