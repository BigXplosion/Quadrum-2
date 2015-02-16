package quadrum.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import quadrum.item.data.ItemData;

public class ItemQuadrumDrink extends ItemQuadrum {
	public ItemQuadrumDrink(ItemData itemData) {
		super(itemData);
	}

	@Override
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
		if (!player.capabilities.isCreativeMode)
			--stack.stackSize;

		if (!world.isRemote)
			player.addPotionEffect(new PotionEffect(itemData.consumeEffect.id, itemData.consumeEffect.duration * 20, itemData.consumeEffect.amplifier));

		return stack.stackSize <= 0 ? null : stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return itemData.consumeDuration;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.drink;
	}

	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
		return stack;
	}
}
