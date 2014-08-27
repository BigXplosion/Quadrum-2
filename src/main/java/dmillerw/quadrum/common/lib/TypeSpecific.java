package dmillerw.quadrum.common.lib;

import dmillerw.quadrum.common.block.data.BlockData;

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
    public BlockData.BlockType value();
}
