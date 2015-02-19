package quadrum.block.data;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.annotations.SerializedName;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;

import quadrum.lib.Required;
import quadrum.lib.TabQuadrum;
import quadrum.lib.TypeSpecific;
import quadrum.lib.data.Drop;
import quadrum.lib.data.MeltingData;

public class BlockData {

	/* INTERPRETED VALUES */
	private Material blockMaterial;
	private Block.SoundType blockSound;
	private CreativeTabs blockTab;

	@Required
	public String name = "";
	@Required
	@SerializedName("default-texture")
	public String defaultTexture = "";
	@Required
	public String material = "";
	public String type = "block";
	@SerializedName("creative-tab")
	public String creativeTab = "";

	@TypeSpecific(TypeSpecific.Type.BLOCK)
	@SerializedName("texture-info")
	public Map<String, String> textureInfo = Maps.newHashMap();

	@SerializedName("mob-drops")
	public Map<String, Float> mobDrops = Maps.newHashMap();

	@SerializedName("ore-dictionary")
	public String[] oreDictionary = new String[0];

	public Drop[] drops = new Drop[0];

	@TypeSpecific({TypeSpecific.Type.BLOCK, TypeSpecific.Type.BLOCK_SLAB})
	@SerializedName("melting-data")
	public MeltingData meltingData;

	@TypeSpecific({TypeSpecific.Type.BLOCK, TypeSpecific.Type.BLOCK_SLAB})
	public float slickness = 0.6F;
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
	@TypeSpecific(TypeSpecific.Type.BLOCK)
	@SerializedName("connected-texture")
	public boolean connectedTexture;
	@TypeSpecific(TypeSpecific.Type.BLOCK)
	public boolean multipart;

	/* START GETTERS */
	public TypeSpecific.Type getBlockType() {
		return TypeSpecific.Type.fromString(type, TypeSpecific.Type.BLOCK);
	}

	public Material getBlockMaterial() {
		if (blockMaterial == null) {
			if (material.equalsIgnoreCase("stone") || material.equalsIgnoreCase("rock"))
				blockMaterial = Material.rock;
			else if (material.equalsIgnoreCase("wood"))
				blockMaterial = Material.wood;
			else if (material.equalsIgnoreCase("ground") || material.equalsIgnoreCase("dirt") || material.equalsIgnoreCase("grass"))
				blockMaterial = Material.ground;
			else if (material.equalsIgnoreCase("iron") || material.equalsIgnoreCase("metal"))
				blockMaterial = Material.iron;
			else
				blockMaterial = Material.rock;
		}
		return blockMaterial;
	}

	public Block getSimilarBlock() {
		Material material1 = getBlockMaterial();
		if (material1 == Material.rock)
			return Blocks.stone;
		else if (material1 == Material.wood)
			return Blocks.planks;
		else if (material1 == Material.ground)
			return Blocks.dirt;
		else if (material1 == Material.iron)
			return Blocks.iron_block;
		else
			return Blocks.stone;
	}

	public Block.SoundType getBlockSound() {
		if (blockSound == null) {
			Material material1 = getBlockMaterial();
			if (material1 == Material.rock)
				blockSound = Block.soundTypeStone;
			else if (material1 == Material.wood)
				blockSound = Block.soundTypeWood;
			else if (material1 == Material.ground)
				blockSound = Block.soundTypeGravel;
			else if (material1 == Material.iron)
				blockSound = Block.soundTypeMetal;
		}
		return blockSound;
	}

	public String getHarvestTool() {
		Material material1 = getBlockMaterial();
		if (material1 == Material.rock)
			return "pickaxe";
		else if (material1 == Material.wood)
			return "axe";
		else if (material1 == Material.ground)
			return "shovel";
		else if (material1 == Material.iron)
			return "pickaxe";
		return "pickaxe";
	}

	public CreativeTabs getCreativeTab() {
		if (blockTab == null) {
			if (creativeTab.equalsIgnoreCase("blocks"))
				return CreativeTabs.tabBlock;
			else if (creativeTab.equalsIgnoreCase("decorations"))
				return CreativeTabs.tabDecorations;
			else if (creativeTab.equalsIgnoreCase("redstone"))
				return CreativeTabs.tabRedstone;
			else if (creativeTab.equalsIgnoreCase("transport"))
				return CreativeTabs.tabTransport;
			else if (creativeTab.equalsIgnoreCase("mics"))
				return CreativeTabs.tabMisc;
			else if (creativeTab.equalsIgnoreCase("food"))
				return CreativeTabs.tabFood;
			else if (creativeTab.equalsIgnoreCase("tools"))
				return CreativeTabs.tabTools;
			else if (creativeTab.equalsIgnoreCase("combat"))
				return CreativeTabs.tabCombat;
			else if (creativeTab.equalsIgnoreCase("brewing"))
				return CreativeTabs.tabBrewing;
			else if (creativeTab.equalsIgnoreCase("materials"))
				return CreativeTabs.tabMaterials;
			return TabQuadrum.BLOCK;
		}
		return blockTab;
	}
	/* END GETTERS */

	public void reload(Block parent) {

	}
}
