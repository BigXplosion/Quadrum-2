package dmillerw.quadrum.common.item.data;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import dmillerw.quadrum.common.lib.JsonVerification;
import dmillerw.quadrum.common.lib.TypeSpecific;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

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
                    ItemData itemData = Quadrum.gson.fromJson(jsonObject, ItemData.class);

                    List<String> keys = Lists.newArrayList();
                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        keys.add(entry.getKey());
                    }

                    for (Field field : BlockData.class.getDeclaredFields()) {
                        String name = field.getName();
                        if (field.getAnnotation(SerializedName.class) != null) {
                            name = field.getAnnotation(SerializedName.class).value();
                        }

                        TypeSpecific typeSpecific = field.getAnnotation(TypeSpecific.class);

                        if (typeSpecific != null && typeSpecific.value() != itemData.getItemType() && keys.contains(name)) {
                            Quadrum.log(Level.INFO, "%s contains the key %s, but that key can't be applied to the %s block type. It will be ignored.", file.getName(), name, itemData.getItemType());
                        }
                    }

                    list.add(itemData);
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to generate item from %s. Reason: %s", file.getName(), ex.toString());
            }
        }
        items = list.toArray(new ItemData[list.size()]);
    }
}
