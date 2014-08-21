package dmillerw.quadrum.common.item.data;

import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.common.lib.Required;

/**
 * @author dmillerw
 */
public class ItemData {

    @Required
    public String name = "";
    @Required
    public String texture = "";

    public String[] lore = new String[0];
    @SerializedName("ore-dictionary")
    public String[] oreDictionary = new String[0];

    public int maxStackSize = 64;
}
