package dmillerw.quadrum.common.lib;

import dmillerw.quadrum.common.block.*;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.item.ItemQuadrum;
import dmillerw.quadrum.common.item.ItemQuadrumDrink;
import dmillerw.quadrum.common.item.ItemQuadrumFood;
import dmillerw.quadrum.common.item.data.ItemData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;

/**
 * @author dmillerw
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeSpecific {
    public static enum Type {
        BLOCK("block", BlockQuadrum.class),
        BLOCK_STAIR("stair", BlockQuadrumStair.class),
        BLOCK_SLAB("slab", BlockQuadrumSlab.class),
        BLOCK_FENCE("fence", BlockQuadrumFence.class),
        BLOCK_WALL("wall", BlockQuadrumWall.class),
        ITEM("item", ItemQuadrum.class),
        ITEM_FOOD("food", ItemQuadrumFood.class),
        ITEM_DRINK("drink", ItemQuadrumDrink.class);

        private String typeName;

        private Class<?> clazz;

        private Type(String typeName, Class<?> clazz) {
            this.typeName = typeName;
            this.clazz = clazz;
        }

        public static Type fromString(String str, Type defaultType) {
            for (Type type : values()) {
                if (str.equalsIgnoreCase(type.typeName)) return type;
            }
            return defaultType;
        }

        public Block createBlock(BlockData data) {
            try {
                Constructor constructor = clazz.getConstructor(BlockData.class);
                return (Block) constructor.newInstance(data);
            } catch (Exception ex) {
                // LOG
                return new BlockQuadrum(data);
            }
        }

        public Item createItem(ItemData data) {
            try {
                Constructor constructor = clazz.getConstructor(ItemData.class);
                return (Item) constructor.newInstance(data);
            } catch (Exception ex) {
                // LOG
                return new ItemQuadrum(data);
            }
        }
    }
    public Type[] value();
}
