package dmillerw.quadrum.common.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author dmillerw
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TypeSpecific {
    public static enum Type {
        BLOCK,
        BLOCK_STAIR,
        BLOCK_SLAB,
        BLOCK_FENCE,
        ITEM,
        ITEM_FOOD,
    }
    public Type value();
}
