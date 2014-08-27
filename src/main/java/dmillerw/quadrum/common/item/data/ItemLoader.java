package dmillerw.quadrum.common.item.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import dmillerw.quadrum.common.lib.JsonVerification;
import dmillerw.quadrum.common.lib.TypeSpecific;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class ItemLoader {

    public static Map<String, Item> itemMap = Maps.newHashMap();
    public static Map<String, ItemData> itemDataMap = Maps.newHashMap();

    public static void initialize() {
        for (File file : Quadrum.itemDir.listFiles(new ExtensionFilter("json"))) {
            try {
                JsonObject jsonObject = Quadrum.gson.fromJson(new FileReader(file), JsonObject.class);

                if (jsonObject != null && JsonVerification.verifyRequirements(file, jsonObject, ItemData.class)) {
                    try {
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

                            if (typeSpecific != null && !Arrays.asList(typeSpecific.value()).contains(itemData.getItemType()) && keys.contains(name)) {
                                Quadrum.log(Level.INFO, "%s contains the key %s, but that key can't be applied to the %s block type. It will be ignored.", file.getName(), name, itemData.getItemType());
                            }
                        }

                        Map loweredMap = Maps.newHashMap();
                        for (Map.Entry<String, Float> entry : itemData.mobDrops.entrySet()) {
                            loweredMap.put(entry.getKey().toLowerCase(), entry.getValue());
                        }
                        itemData.mobDrops.clear();
                        itemData.mobDrops.putAll(loweredMap);

                        itemDataMap.put(itemData.name, itemData);
                    } catch (JsonSyntaxException ex) {
                        Quadrum.log(Level.WARN, "Ran into an issue while parsing %s. Reason: %s", file.getName(), ex.toString());
                    }
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to generate item from %s. Reason: %s", file.getName(), ex.toString());
            }
        }
    }
}
