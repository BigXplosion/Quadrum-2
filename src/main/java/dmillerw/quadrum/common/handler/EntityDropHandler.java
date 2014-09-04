package dmillerw.quadrum.common.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.item.data.ItemData;
import dmillerw.quadrum.common.item.data.ItemLoader;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

import java.util.Random;

/**
 * @author dmillerw
 */
public class EntityDropHandler {

    @SubscribeEvent
    public void onEntityDrop(LivingDropsEvent event) {
        Random random = new Random();

        if (EntityList.classToStringMapping.containsKey(event.entityLiving.getClass())) {
            String entityType = ((String) EntityList.classToStringMapping.get(event.entityLiving.getClass())).toLowerCase();
            for (BlockData blockData : BlockLoader.blockDataList) {
                if (blockData.mobDrops.containsKey(entityType)) {
                    float probability = blockData.mobDrops.get(entityType);
                    if (random.nextFloat() <= probability) {
                        event.entityLiving.entityDropItem(new ItemStack(BlockLoader.blockMap.get(blockData.name)), 0);
                    }
                }
            }
            for (ItemData itemData : ItemLoader.itemDataList) {
                if (itemData.mobDrops.containsKey(entityType)) {
                    float probability = itemData.mobDrops.get(entityType);
                    if (random.nextFloat() <= probability) {
                        event.entityLiving.entityDropItem(new ItemStack(ItemLoader.itemMap.get(itemData.name)), 0);
                    }
                }
            }
        }
    }
}
