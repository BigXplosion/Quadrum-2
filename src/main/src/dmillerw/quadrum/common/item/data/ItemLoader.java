package dmillerw.quadrum.common.item.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dmillerw
 */
public class ItemLoader {

    public static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        ItemLoader.gson = gsonBuilder.create();
    }

    public static ItemData[] items;

    public static void initialize() {
        List<ItemData> list = Lists.newArrayList();

        for (File file : Quadrum.itemDir.listFiles(new ExtensionFilter("json"))) {
            try {
                ItemData itemData = gson.fromJson(new FileReader(file), ItemData.class);
                if (itemData == null) {
                    // LOG
                    return;
                }

                if (itemData.name.isEmpty()) {
                    // LOG
                    return;
                }

                list.add(itemData);
            } catch (IOException ex) {
                // LOG
                ex.printStackTrace();
            }
        }
        items = list.toArray(new ItemData[list.size()]);

        for (File file : Quadrum.itemLangDir.listFiles(new ExtensionFilter("lang"))) {
            try {
                String lang = file.getName().substring(0, file.getName().lastIndexOf("."));
                Properties properties = new Properties();
                properties.load(new FileInputStream(file));
                HashMap<String, String> map = Maps.newHashMap();
                for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                    map.put(entry.getKey().toString(), entry.getValue().toString());
                }
                LanguageRegistry.instance().injectLanguage(lang, map);
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to parse %s. Reason: %s", file.getName(), ex.toString());
            }
        }
    }
}
