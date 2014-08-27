package dmillerw.quadrum.common.item.data;

import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.common.lib.Required;
import dmillerw.quadrum.common.lib.TypeSpecific;
import dmillerw.quadrum.common.lib.data.Effect;

/**
 * @author dmillerw
 */
public class ItemData {

    @Required
    public String name = "";
    @Required
    public String texture = "";
    public String type = "";

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
    @SerializedName("has-effect")
    public boolean hasEffect = false;

    public TypeSpecific.Type getItemType() {
        return TypeSpecific.Type.fromString(type, TypeSpecific.Type.ITEM);
    }
}
