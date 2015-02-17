package quadrum.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import quadrum.item.data.ItemData;
import quadrum.lib.IQuadrumObject;

public class ItemQuadrum extends Item implements IQuadrumObject {

	protected final ItemData itemData;

	public IIcon icon;

	public ItemQuadrum(ItemData itemData) {
		super();

		this.itemData = itemData;

		setUnlocalizedName(itemData.name);
		setMaxStackSize(itemData.maxStackSize);
		setCreativeTab(itemData.getCreativeTab());
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
		for (String str : itemData.lore)
			list.add(str);
	}

	@Override
	public boolean hasEffect(ItemStack stack, int pass) {
		return itemData.hasEffect;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister register) {
		icon = register.registerIcon("qresource:" + itemData.texture);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int damage) {
		return icon;
	}

	@Override
	public Object get() {
		return itemData;
	}
}
