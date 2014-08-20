package dmillerw.quadrum.common.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dmillerw.quadrum.Quadrum;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author dmillerw
 */
public class BlockLoader {

    public static class ExtensionFilter implements FilenameFilter {
        private String extension;
        public ExtensionFilter(String extension) {
            this.extension = extension;
        }
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(extension);
        }
    }

    public static Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        BlockLoader.gson = gsonBuilder.create();
    }

    public static BlockData[] blocks;

    public static void initialize() {
        List<BlockData> list = Lists.newArrayList();

        for (File file : Quadrum.blockDir.listFiles(new ExtensionFilter("json"))) {
            try {
                BlockData blockData = gson.fromJson(new FileReader(file), BlockData.class);
                if (blockData == null) {
                    Quadrum.log(Level.WARN, "Completely failed to parse %s", file.getName());
                    return;
                }

                if (blockData.name.isEmpty()) {
                    Quadrum.log(Level.WARN, "Ran into an error while loading %s. A name must be defined!", file.getName());
                    return;
                }


                Map<String, String> loweredMap = Maps.newHashMap();
                for (Map.Entry<String, String> entry : blockData.textureInfo.entrySet()) {
                    loweredMap.put(entry.getKey().toLowerCase(), entry.getValue());
                }
                blockData.textureInfo.clear();
                blockData.textureInfo.putAll(loweredMap);

                System.out.println(blockData.textureInfo);

                for (Drop drop : blockData.drops) {
                    if (drop.getDrop() == null) {
                        Quadrum.log(Level.WARN, "Ran into an error while loading %s. %s is an invalid drop item!", file.getName(), drop.item);
                    } else {
                        list.add(blockData);
                    }
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to parse %s. Reason: %s", file.getName(), ex.toString());
            }
        }
        blocks = list.toArray(new BlockData[list.size()]);

        // Scan for lang file
        for (File file : Quadrum.blockLangDir.listFiles(new ExtensionFilter("lang"))) {
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
