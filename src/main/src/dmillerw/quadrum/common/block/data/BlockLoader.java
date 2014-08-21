package dmillerw.quadrum.common.block.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import dmillerw.quadrum.Quadrum;
import dmillerw.quadrum.common.lib.ExtensionFilter;
import dmillerw.quadrum.common.lib.JsonVerification;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
                JsonObject object = Quadrum.gson.fromJson(new FileReader(file), JsonObject.class);

                if (object != null && JsonVerification.verifyRequirements(file, object, BlockData.class)) {
                    BlockData blockData = Quadrum.gson.fromJson(object, BlockData.class);
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
