package dmillerw.quadrum.common.item;

import dmillerw.quadrum.client.texture.QuadrumSprite;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.lib.IQuadrumObject;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * @author dmillerw
 */
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
        for (String str : itemData.lore) {
            list.add(str);
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack, int pass) {
        return itemData.hasEffect;
    }

    @Override
    public void registerIcons(IIconRegister register) {
        QuadrumSprite quadrumSprite = new QuadrumSprite(itemData.texture, false).register((TextureMap) register);
        icon = quadrumSprite;
    }

    @Override
    public IIcon getIconFromDamage(int damage) {
        return icon;
    }

    @Override
    public Object get() {
        return itemData;
    }
}
