package quadrum.handler;

import java.util.Random;

import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraftforge.event.entity.living.LivingDropsEvent;

import quadrum.block.data.BlockData;
import quadrum.block.data.BlockLoader;
import quadrum.item.data.ItemData;
import quadrum.item.data.ItemLoader;

public class EntityDropHandler {

	@SubscribeEvent
	public void onEntityDrop(LivingDropsEvent event) {
		Random random = new Random();

		if (EntityList.classToStringMapping.containsKey(event.entityLiving.getClass())) {
			String entityType = ((String) EntityList.classToStringMapping.get(event.entityLiving.getClass())).toLowerCase();
			for (BlockData blockData : BlockLoader.blockDataList) {
				if (blockData.mobDrops.containsKey(entityType)) {
					float probability = blockData.mobDrops.get(entityType);
					if (random.nextFloat() <= probability)
						event.entityLiving.entityDropItem(new ItemStack(BlockLoader.blockMap.get(blockData.name)), 0);
				}
			}
			for (ItemData itemData : ItemLoader.itemDataList) {
				if (itemData.mobDrops.containsKey(entityType)) {
					float probability = itemData.mobDrops.get(entityType);
					if (random.nextFloat() <= probability)
						event.entityLiving.entityDropItem(new ItemStack(ItemLoader.itemMap.get(itemData.name)), 0);
				}
			}
		}
	}
}
