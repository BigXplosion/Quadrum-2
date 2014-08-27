package dmillerw.quadrum.common.item;

import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemQuadrumFood extends ItemFood {

    public final ItemData data;

    public ItemQuadrumFood(ItemData data) {
        super(data.foodAmount, data.foodSaturation, data.wolfFood);

        this.data = data;

        if (data.consumeEffect != null && data.consumeEffect.getPotionEffect() != null) {
            setPotionEffect(data.consumeEffect.getPotionEffect().id, data.consumeEffect.duration, data.consumeEffect.amplifier, data.consumeEffect.probability);
        }

        setUnlocalizedName(data.name);
        setMaxStackSize(data.maxStackSize);
        setCreativeTab(TabQuadrum.ITEM);

        for (String string : data.oreDictionary) {
            OreDictionary.registerOre(string, this);
        }
    }

    @Override
    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return data.consumeDuration;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        for (String str : data.lore) {
            list.add(str);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return data.hasEffect;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return TextureLoader.getItemIcon(data);
    }

    @Override
    public void registerIcons(IIconRegister register) {

    }
}
