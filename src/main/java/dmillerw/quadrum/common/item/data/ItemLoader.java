package dmillerw.quadrum.common.item.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import dmillerw.quadrum.common.lib.JsonVerification;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author dmillerw
 */
public class ItemLoader {

    public static ItemData[] items;

    public static void initialize() {
        List<ItemData> list = Lists.newArrayList();

        for (File file : Quadrum.itemDir.listFiles(new ExtensionFilter("json"))) {
            try {
                JsonObject jsonObject = Quadrum.gson.fromJson(new FileReader(file), JsonObject.class);

                if (jsonObject != null && JsonVerification.verifyRequirements(file, jsonObject, ItemData.class)) {
                    list.add(Quadrum.gson.fromJson(jsonObject, ItemData.class));
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to generate item from %s. Reason: %s", file.getName(), ex.toString());
            }
        }
        items = list.toArray(new ItemData[list.size()]);
    }
}
