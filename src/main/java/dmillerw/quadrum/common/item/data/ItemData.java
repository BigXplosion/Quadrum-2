package dmillerw.quadrum.common.item.data;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.common.lib.Required;
import dmillerw.quadrum.common.lib.TabQuadrum;
import dmillerw.quadrum.common.lib.TypeSpecific;
import dmillerw.quadrum.common.lib.data.Effect;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import java.util.Map;

/**
 * @author dmillerw
 */
public class ItemData {

    private CreativeTabs itemTab;

    @Required
    public String name = "";
    @Required
    public String texture = "";
    public String type = "";

    @SerializedName("creative-tab")
    public String creativeTab = "";

    @SerializedName("mob-drops")
    public Map<String, Float> mobDrops = Maps.newHashMap();

    @TypeSpecific({TypeSpecific.Type.ITEM_FOOD, TypeSpecific.Type.ITEM_DRINK})
    @SerializedName("consume-effect")
    public Effect consumeEffect;

    public String[] lore = new String[0];
    @SerializedName("ore-dictionary")
    public String[] oreDictionary = new String[0];

    @TypeSpecific(TypeSpecific.Type.ITEM_FOOD)
    @SerializedName("food-saturation")
    public float foodSaturation = 0.6F;

    @TypeSpecific({TypeSpecific.Type.ITEM_FOOD, TypeSpecific.Type.ITEM_DRINK})
    @SerializedName("consume-duration")
    public int consumeDuration = 32;
    @TypeSpecific(TypeSpecific.Type.ITEM_FOOD)
    @SerializedName("food-amount")
    public int foodAmount = 0;
    @SerializedName("burn-time")
    public int burnTime;
    public int maxStackSize = 64;

    @TypeSpecific(TypeSpecific.Type.ITEM_FOOD)
    @SerializedName("wolf-food")
    public boolean wolfFood = false;
    @TypeSpecific(TypeSpecific.Type.ITEM_FOOD)
    @SerializedName("always-editble")
    public boolean alwaysEdible = false;
    @SerializedName("has-effect")
    public boolean hasEffect = false;

    /* START GETTERS */
    public TypeSpecific.Type getItemType() {
        return TypeSpecific.Type.fromString(type, TypeSpecific.Type.ITEM);
    }

    public CreativeTabs getCreativeTab() {
        if (itemTab == null)  {
            if (creativeTab.equalsIgnoreCase("blocks")) {
                return CreativeTabs.tabBlock;
            } else if (creativeTab.equalsIgnoreCase("decorations")) {
                return CreativeTabs.tabDecorations;
            } else if (creativeTab.equalsIgnoreCase("redstone")) {
                return CreativeTabs.tabRedstone;
            } else if (creativeTab.equalsIgnoreCase("transport")) {
                return CreativeTabs.tabTransport;
            } else if (creativeTab.equalsIgnoreCase("mics")) {
                return CreativeTabs.tabMisc;
            } else if (creativeTab.equalsIgnoreCase("food")) {
                return CreativeTabs.tabFood;
            } else if (creativeTab.equalsIgnoreCase("tools")) {
                return CreativeTabs.tabTools;
            } else if (creativeTab.equalsIgnoreCase("combat")) {
                return CreativeTabs.tabCombat;
            } else if (creativeTab.equalsIgnoreCase("brewing")) {
                return CreativeTabs.tabBrewing;
            } else if (creativeTab.equalsIgnoreCase("materials")) {
                return CreativeTabs.tabMaterials;
            }
            return TabQuadrum.ITEM;
        }
        return itemTab;
    }
    /* END GETTERS */

    public void reload(Item parent) {

    }
}
