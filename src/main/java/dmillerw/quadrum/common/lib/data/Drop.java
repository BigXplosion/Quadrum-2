package dmillerw.quadrum.common.lib.data;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

import java.util.Random;

/**
 * @author dmillerw
 */
public class Drop {

    private static Random random = new Random();

    private Item dropItem;

    public String item;
    public int damage = 0;
    public String amount = "1";

    public Item getDrop() {
        if (dropItem == null) {
            dropItem = GameData.getItemRegistry().getRaw(item);
        }
        return dropItem;
    }

    public int getDropAmount() {
        if (!amount.contains("-")) {
            return Integer.parseInt(amount);
        } else {
            int low = Integer.parseInt(amount.substring(0, amount.indexOf("-")));
            int high = Integer.parseInt(amount.substring(amount.indexOf("-") + 1, amount.length()));
            return MathHelper.getRandomIntegerInRange(random, low, high);
        }
    }
}
