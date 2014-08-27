package dmillerw.quadrum.common.item;

import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemQuadrumDrink extends ItemQuadrum {

    public ItemQuadrumDrink(ItemData data) {
        super(data);
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        if (!player.capabilities.isCreativeMode) {
            --stack.stackSize;
        }

        ItemData data = ItemLoader.itemDataMap.get(name);

        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(data.consumeEffect.id, data.consumeEffect.duration * 20, data.consumeEffect.amplifier));
        }

        return stack.stackSize <= 0 ? null : stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return ItemLoader.itemDataMap.get(name).consumeDuration;
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
