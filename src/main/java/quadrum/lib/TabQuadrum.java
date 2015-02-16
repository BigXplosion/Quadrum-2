package quadrum.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TabQuadrum extends CreativeTabs {

	public static final TabQuadrum BLOCK = new TabQuadrum("block", true);
	public static final TabQuadrum ITEM = new TabQuadrum("item", false);

	private final boolean block;

	public TabQuadrum(String lable, boolean block) {
		super("quadrum." + lable);
		this.block = block;
	}

	@Override
	public Item getTabIconItem() {
		return (block ? Item.getItemFromBlock(Blocks.stone) : Items.stick);
	}
}
