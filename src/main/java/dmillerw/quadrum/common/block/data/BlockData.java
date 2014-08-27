package dmillerw.quadrum.common.block.data;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;
import dmillerw.quadrum.common.lib.Required;
import dmillerw.quadrum.common.lib.TypeSpecific;
import dmillerw.quadrum.common.lib.data.Drop;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

import java.util.Map;

/**
 * @author dmillerw
 */
public class BlockData {

    /* INTERPRETED VALUES */
    private Material blockMaterial;
    private Block.SoundType blockSound;

    @Required
    public String name = "";
    @Required
    @SerializedName("default-texture")
    public String defaultTexture = "";
    @Required
    public String material = "";
    public String type = "block";

    @TypeSpecific(TypeSpecific.Type.BLOCK)
    @SerializedName("texture-info")
    public Map<String, String> textureInfo = Maps.newHashMap();

    @SerializedName("mob-drops")
    public Map<String, Float> mobDrops = Maps.newHashMap();

    @SerializedName("ore-dictionary")
    public String[] oreDictionary = new String[0];

    public Drop[] drops = new Drop[0];

    public float hardness = 2F;
    public float resistance = 2F;

    @TypeSpecific(TypeSpecific.Type.BLOCK)
    @SerializedName("light-level")
    public int lightLevel = 0;
    @TypeSpecific(TypeSpecific.Type.BLOCK)
    @SerializedName("redstone-level")
    public int redstoneLevel = 0;
    @SerializedName("burn-time")
    public int burnTime = 0;
    @SerializedName("max-stack-size")
    public int maxStackSize = 64;
    @SerializedName("mining-level")
    public int miningLevel = 0;

    public boolean transparent = false;
    public boolean collision = true;
    @TypeSpecific(TypeSpecific.Type.BLOCK)
    public boolean flammable = false;
    @TypeSpecific(TypeSpecific.Type.BLOCK)
    public boolean soil = false;
    @SerializedName("require-tool")
    public boolean requiresTool = true;
    @SerializedName("drops-self")
    public boolean dropsSelf = true;

    /* START GETTERS */
    public TypeSpecific.Type getBlockType() {
        return TypeSpecific.Type.fromString(type, TypeSpecific.Type.BLOCK);
    }

    public Material getBlockMaterial() {
        if (blockMaterial == null) {
            if (material.equalsIgnoreCase("stone") || material.equalsIgnoreCase("rock")) {
                blockMaterial = Material.rock;
            } else if (material.equalsIgnoreCase("wood")) {
                blockMaterial = Material.wood;
            } else if (material.equalsIgnoreCase("ground") || material.equalsIgnoreCase("dirt") || material.equalsIgnoreCase("grass")) {
                blockMaterial = Material.ground;
            } else if (material.equalsIgnoreCase("iron") || material.equalsIgnoreCase("metal")) {
                blockMaterial = Material.iron;
            } else {
                blockMaterial = Material.rock;
            }
        }
        return blockMaterial;
    }

    public Block getSimilarBlock() {
        Material material1 = getBlockMaterial();
        if (material1 == Material.rock) {
            return Blocks.stone;
        } else if (material1 == Material.wood) {
            return Blocks.planks;
        } else if (material1 == Material.ground) {
            return Blocks.dirt;
        } else if (material1 == Material.iron) {
            return Blocks.iron_block;
        } else {
            return Blocks.stone;
        }
    }

    public Block.SoundType getBlockSound() {
        if (blockSound == null) {
            Material material1 = getBlockMaterial();
            if (material1 == Material.rock) {
                blockSound = Block.soundTypeStone;
            } else if (material1 == Material.wood) {
                blockSound = Block.soundTypeWood;
            } else if (material1 == Material.ground) {
                blockSound = Block.soundTypeGrass;
            } else if (material1 == Material.iron) {
                blockSound = Block.soundTypeMetal;
            }
        }
        return blockSound;
    }

    public String getHarvestTool() {
        Material material1 = getBlockMaterial();
        if (material1 == Material.rock) {
            return "pickaxe";
        } else if (material1 == Material.wood) {
            return "axe";
        } else if (material1 == Material.ground) {
            return "shovel";
        } else if (material1 == Material.iron) {
            return "pickaxe";
        }
        return "pickaxe";
    }
    /* END GETTERS */

    public void reload(Block parent) {

    }
}
