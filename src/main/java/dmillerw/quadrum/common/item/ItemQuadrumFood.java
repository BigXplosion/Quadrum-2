package dmillerw.quadrum.common.item;

import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import dmillerw.quadrum.common.lib.IQuadrumItem;
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
public class ItemQuadrumFood extends ItemFood implements IQuadrumItem {

    public final String name;

    public ItemQuadrumFood(ItemData data) {
        super(data.foodAmount, data.foodSaturation, data.wolfFood);

        this.name = data.name;

        if (data.consumeEffect != null && data.consumeEffect.getPotionEffect() != null) {
            setPotionEffect(data.consumeEffect.getPotionEffect().id, data.consumeEffect.duration, data.consumeEffect.amplifier, data.consumeEffect.probability);
        }

        if (data.alwaysEdible) {
            setAlwaysEdible();
        }

        setUnlocalizedName(data.name);
        setMaxStackSize(data.maxStackSize);
        setCreativeTab(TabQuadrum.ITEM);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return ItemLoader.itemDataMap.get(name).consumeDuration;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        for (String str : ItemLoader.itemDataMap.get(name).lore) {
            list.add(str);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return ItemLoader.itemDataMap.get(name).hasEffect;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return TextureLoader.getItemIcon(ItemLoader.itemDataMap.get(name));
    }

    @Override
    public void registerIcons(IIconRegister register) {

    }

    @Override
    public String getName() {
        return name;
    }
}
