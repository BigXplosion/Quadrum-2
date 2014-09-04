package dmillerw.quadrum.common.item;

import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.IQuadrumObject;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrumFood extends ItemFood implements IQuadrumObject {

    private final ItemData itemData;

    public ItemQuadrumFood(ItemData itemData) {
        super(itemData.foodAmount, itemData.foodSaturation, itemData.wolfFood);

        this.itemData = itemData;

        if (itemData.consumeEffect != null && itemData.consumeEffect.getPotionEffect() != null) {
            setPotionEffect(itemData.consumeEffect.getPotionEffect().id, itemData.consumeEffect.duration, itemData.consumeEffect.amplifier, itemData.consumeEffect.probability);
        }

        if (itemData.alwaysEdible) {
            setAlwaysEdible();
        }

        setUnlocalizedName(itemData.name);
        setMaxStackSize(itemData.maxStackSize);
        setCreativeTab(TabQuadrum.ITEM);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return itemData.consumeDuration;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        for (String str : itemData.lore) {
            list.add(str);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return itemData.hasEffect;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return TextureLoader.getItemIcon(itemData);
    }

    @Override
    public void registerIcons(IIconRegister register) {

    }

    @Override
    public ItemData get() {
        return itemData;
    }
}
