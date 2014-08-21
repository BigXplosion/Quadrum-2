package dmillerw.quadrum.common.block.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.Quadrum;
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
public class BlockLoader {

    public static BlockData[] blocks;

    public static void initialize() {
        List<BlockData> list = Lists.newArrayList();

        for (File file : Quadrum.blockDir.listFiles(new ExtensionFilter("json"))) {
            try {
                JsonObject jsonObject = Quadrum.gson.fromJson(new FileReader(file), JsonObject.class);

                if (jsonObject != null && JsonVerification.verifyRequirements(file, jsonObject, BlockData.class)) {
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

                        if (typeSpecific != null && typeSpecific.value() != blockData.getBlockType() && keys.contains(name)) {
                            Quadrum.log(Level.INFO, "%s contains the key %s, but that key can't be applied to the %s block type. It will be ignored.", file.getName(), name, blockData.getBlockType());
                        }
                    }

                    Map<String, String> loweredMap = Maps.newHashMap();
                    for (Map.Entry<String, String> entry : blockData.textureInfo.entrySet()) {
                        loweredMap.put(entry.getKey().toLowerCase(), entry.getValue());
                    }
                    blockData.textureInfo.clear();
                    blockData.textureInfo.putAll(loweredMap);

                    list.add(blockData);
                }
            } catch (IOException ex) {
                Quadrum.log(Level.WARN, "Completely failed to generate block from %s. Reason: %s", file.getName(), ex.toString());
            }
        }
        blocks = list.toArray(new BlockData[list.size()]);
    }

    public static void verifyDrops() {
        for (BlockData blockData : blocks) {
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
