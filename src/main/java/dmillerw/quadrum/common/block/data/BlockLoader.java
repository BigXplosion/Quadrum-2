package dmillerw.quadrum.common.block.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import dmillerw.quadrum.common.lib.JsonVerification;
import dmillerw.quadrum.common.lib.TypeSpecific;
import dmillerw.quadrum.common.lib.data.Drop;
import net.minecraft.block.Block;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author dmillerw
 */
public class BlockLoader {

    public static List<BlockData> blockDataList = Lists.newArrayList();

    public static Map<String, Block> blockMap = Maps.newHashMap();

    public static void initialize() {
        for (File file : Quadrum.blockDir.listFiles(new ExtensionFilter("json"))) {
            try {
                JsonElement jsonElement = Quadrum.gson.fromJson(new FileReader(file), JsonElement.class);
                if (jsonElement != null) {
                    if (jsonElement.isJsonArray()) {
                        Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
                        while (iterator.hasNext()) {
                            JsonElement element = iterator.next();
                            if (element != null && element.isJsonObject()) {
                                if (JsonVerification.verifyRequirements(file, element.getAsJsonObject(), BlockData.class)) {
                                    parse(file.getName(), element.getAsJsonObject());
                                }
                            }
                        }
                    } else if (jsonElement.isJsonObject()) {
                        parse(file.getName(), jsonElement.getAsJsonObject());
                    }
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to generate block from %s. Reason: %s", file.getName(), ex.toString());
            }
        }
    }

    private static void parse(String filename, JsonObject jsonObject) {
        try {
            BlockData blockData = Quadrum.gson.fromJson(jsonObject, BlockData.class);

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

                if (typeSpecific != null && !Arrays.asList(typeSpecific.value()).contains(blockData.getBlockType()) && keys.contains(name)) {
                    Quadrum.log(Level.INFO, "%s contains the key %s, but that key can't be applied to the %s block type. It will be ignored.", filename, name, blockData.getBlockType());
                }
            }

            Map loweredMap = Maps.newHashMap();
            for (Map.Entry<String, String> entry : blockData.textureInfo.entrySet()) {
                loweredMap.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            blockData.textureInfo.clear();
            blockData.textureInfo.putAll(loweredMap);

            loweredMap.clear();
            for (Map.Entry<String, Float> entry : blockData.mobDrops.entrySet()) {
                loweredMap.put(entry.getKey().toLowerCase(), entry.getValue());
            }
            blockData.mobDrops.clear();
            blockData.mobDrops.putAll(loweredMap);

            blockDataList.add(blockData);
        } catch (JsonSyntaxException ex) {
            Quadrum.log(Level.WARN, "Ran into an issue while parsing %s. Reason: %s", filename, ex.toString());
        }
    }

    public static void verifyDrops() {
        for (BlockData blockData : blockDataList) {
            List<Drop> dropList = Lists.newArrayList();
            for (Drop drop : blockData.drops) {
                if (drop.item.isEmpty()) {
                    Quadrum.log(Level.WARN, "Block %s has a drop defined, but doesn't specify an item!");
                } else {
                    if (drop.getDrop() == null) {
                        Quadrum.log(Level.WARN, "Block %s defines %s as a drop item, but that item doesn't exist", blockData.name, drop.item);
                    } else {
                        dropList.add(drop);
                    }
                }
            }
            blockData.drops = dropList.toArray(new Drop[dropList.size()]);
        }
    }
}
