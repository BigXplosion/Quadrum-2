package dmillerw.quadrum.common.data;

import com.google.gson.annotations.SerializedName;

/**
 * @author dmillerw
 */
public class ItemData {

    public String name = "";
    public String texture = "";

    public String[] lore = new String[0];
    @SerializedName("ore-dictionary")
    public String[] oreDictionary = new String[0];

    public int maxStackSize = 64;
}
