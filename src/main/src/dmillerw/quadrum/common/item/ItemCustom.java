package dmillerw.quadrum.common.item;

import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

/**
 * @author dmillerw
 */
public class ItemCustom extends Item {

    public final ItemData data;

    public ItemCustom(ItemData data) {
        super();

        this.data = data;

        setUnlocalizedName(data.name);
        setMaxStackSize(data.maxStackSize);
        setCreativeTab(TabQuadrum.ITEM);

        for (String string : data.oreDictionary) {
            OreDictionary.registerOre(string, this);
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean debug) {
        for (String str : data.lore) {
            list.add(str);
        }
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return TextureLoader.getItemIcon(data);
    }
}
