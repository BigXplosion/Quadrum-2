package quadrum.block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Maps;

import org.apache.logging.log4j.Level;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import quadrum.Quadrum;
import quadrum.block.data.BlockData;
import quadrum.lib.BlockStaticMethodHandler;
import quadrum.lib.IQuadrumObject;
import quadrum.util.Utils;

public class BlockQuadrum extends Block implements IQuadrumObject {

	public static final List VALID_TEXTURES = Arrays.asList("front", "back", "left", "right", "top", "bottom");

	public final BlockData blockData;

	Map<String, IIcon> icons;

	public BlockQuadrum(BlockData blockData) {
		super(blockData.getBlockMaterial());

		this.blockData = blockData;
		icons = Maps.newHashMap();

		setTickRandomly(true);
		setStepSound(blockData.getBlockSound());
		setLightLevel((float) blockData.lightLevel / (float) 15);
		setHardness(blockData.hardness);
		setResistance(blockData.resistance);
		setBlockName(blockData.name);
		setCreativeTab(blockData.getCreativeTab());

		opaque = !blockData.transparent;
		lightOpacity = !blockData.transparent ? 255 : 0;
		if (blockData.slickness > 0)
			slipperiness = blockData.slickness;
		else
			Quadrum.log(Level.WARN, "%s had a slickness value of 0 or lower, this could cause unstable blocks or even world corruption when not used right. Slickness has not been set!", blockData.name);

		if (blockData.requiresTool)
			setHarvestLevel(blockData.getHarvestTool(), blockData.miningLevel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return blockData.transparent ? 1 : 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icons.put("default", register.registerIcon(Utils.getIconForRegister(blockData.defaultTexture)));
		registerIcons(register);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if (icons.size() > 1 && !blockData.textureInfo.isEmpty()) {
			ForgeDirection dir = ForgeDirection.getOrientation(meta);

			if (meta == 0)
				dir = ForgeDirection.SOUTH;

			if (side == dir.ordinal())
				return getBlockIcon("front");
			if (side == dir.getOpposite().ordinal())
				return getBlockIcon("back");
			if (side == dir.getRotation(ForgeDirection.UP).ordinal())
				return getBlockIcon("left");
			if (side == dir.getRotation(ForgeDirection.UP).getOpposite().ordinal())
				return getBlockIcon("right");
			if (side == 0)
				return getBlockIcon("bottom");
			if (side == 1)
				return getBlockIcon("top");
		}
		return icons.get("default");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean renderAsNormalBlock() {
		return !blockData.transparent || !blockData.connectedTexture;
	}

	@Override
	public boolean isOpaqueCube() {
		return blockData != null && !blockData.transparent;
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		return BlockStaticMethodHandler.getDrops(this, blockData, world, x, y, z, metadata, fortune);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return blockData.collision ? super.getCollisionBoundingBoxFromPool(world, x, y, z) : null;
	}

	public void updateTick(World world, int x, int y, int z, Random random) {
		if (blockData.meltingData != null && blockData.meltingData.getFluid() != null && world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > blockData.meltingData.light - this.getLightOpacity())
			world.setBlock(x, y, z, blockData.meltingData.getFluid().getBlock());
	}

	@Override
	public boolean canProvidePower() {
		return blockData.redstoneLevel > 0;
	}

	@Override
	public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
		return blockData.redstoneLevel;
	}

	@Override
	public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
		return blockData.flammable;
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
		return blockData.soil;
	}

	@Override
	public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
		return type.getPeacefulCreature() || blockData.canMonsterSpawn;
	}

	@Override
	public BlockData get() {
		return blockData;
	}

	public IIcon getBlockIcon(String type) {
		return icons.containsKey(type) ? icons.get(type) : icons.get("default");
	}

	public void registerIcons(IIconRegister register) {
		if (!blockData.textureInfo.isEmpty()) {
			for (Map.Entry<String, String> entry : blockData.textureInfo.entrySet()) {
				if (VALID_TEXTURES.contains(entry.getKey().toLowerCase()))
					icons.put(entry.getKey().toLowerCase(), register.registerIcon(Utils.getIconForRegister(entry.getValue())));
				else
					Quadrum.log(Level.WARN, "failed to get a texture from %s: %s is not a valid direction", blockData.name, entry.getKey());
			}
		}
	}
}
