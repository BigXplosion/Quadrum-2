package quadrum.lib;

import java.util.ArrayList;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import quadrum.block.data.BlockData;
import quadrum.lib.data.Drop;

public class BlockStaticMethodHandler {

	public static ArrayList<ItemStack> getDrops(Block block, BlockData blockData, World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> stackList = Lists.newArrayList();
		if (blockData.dropsSelf) {
			int count = block.quantityDropped(metadata, fortune, world.rand);
			for (int i = 0; i < count; i++) {
				Item item = block.getItemDropped(metadata, world.rand, fortune);
				if (item != null)
					stackList.add(new ItemStack(item, 1, block.damageDropped(metadata)));
			}
		}
		for (Drop drop : blockData.drops)
			stackList.add(new ItemStack(drop.getDrop(), drop.getDropAmount(), drop.damage));
		return stackList;
	}
}
