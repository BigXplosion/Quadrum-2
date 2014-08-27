package dmillerw.quadrum.common.lib;

import com.google.common.collect.Lists;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.lib.data.Drop;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockStaticMethodHandler {

    public static ArrayList<ItemStack> getDrops(Block block, BlockData blockData, World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> stackList = Lists.newArrayList();
        if (blockData.dropsSelf) {
            int count = block.quantityDropped(metadata, fortune, world.rand);
            for (int i = 0; i < count; i++) {
                Item item = block.getItemDropped(metadata, world.rand, fortune);
                if (item != null) {
                    stackList.add(new ItemStack(item, 1, block.damageDropped(metadata)));
                }
            }
        }
        for (Drop drop : blockData.drops) {
            stackList.add(new ItemStack(drop.getDrop(), drop.getDropAmount(), drop.damage));
        }
        return stackList;
    }
}
