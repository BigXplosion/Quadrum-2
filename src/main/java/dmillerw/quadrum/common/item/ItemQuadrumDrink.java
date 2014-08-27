package dmillerw.quadrum.common.item;

import dmillerw.quadrum.common.item.data.ItemData;
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

        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(data.consumeEffect.id, data.consumeEffect.duration * 20, data.consumeEffect.amplifier));
        }

        return stack.stackSize <= 0 ? null : stack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return data.consumeDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.drink;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
        p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
        return p_77659_1_;
    }
}
