package quadrum.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
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

import quadrum.block.data.BlockData;
import quadrum.client.texture.QuadrumSprite;
import quadrum.client.texture.TextureLoader;
import quadrum.lib.BlockStaticMethodHandler;
import quadrum.lib.IQuadrumObject;

public class BlockQuadrum extends Block implements IQuadrumObject {

	public final BlockData blockData;

	public QuadrumSprite icon;

	public BlockQuadrum(BlockData blockData) {
		super(blockData.getBlockMaterial());

		this.blockData = blockData;

		setTickRandomly(true);
		setStepSound(blockData.getBlockSound());
		setLightLevel((float) blockData.lightLevel / (float) 15);
		setHardness(blockData.hardness);
		setResistance(blockData.resistance);
		setBlockName(blockData.name);
		setCreativeTab(blockData.getCreativeTab());

		opaque = !blockData.transparent;
		lightOpacity = !blockData.transparent ? 255 : 0;
		slipperiness = blockData.slickness;

		if (blockData.requiresTool)
			setHarvestLevel(blockData.getHarvestTool(), blockData.miningLevel);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass() {
		return blockData.transparent ? 1 : 0;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegister) {
		TextureLoader.registerIcons(iconRegister, this);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
		ForgeDirection front = ForgeDirection.getOrientation(meta);
		if (meta == 0)
			front = ForgeDirection.SOUTH;

		if (forgeSide == front)
			return TextureLoader.getIcon(this, "front");
		else if (forgeSide == front.getRotation(ForgeDirection.UP))
			return TextureLoader.getIcon(this, "left");
		else if (forgeSide == front.getRotation(ForgeDirection.UP).getOpposite())
			return TextureLoader.getIcon(this, "right");
		else if (forgeSide == front.getOpposite())
			return TextureLoader.getIcon(this, "back");
		else if (side == 0)
			return TextureLoader.getIcon(this, "bottom");
		else if (side == 1)
			return TextureLoader.getIcon(this, "top");
		else
			return TextureLoader.getIcon(this, "default");
	}

	@Override
	public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
		return getIcon(side, world.getBlockMetadata(x, y, z));
	}

	@Override
	public boolean renderAsNormalBlock() {
		return !blockData.transparent;
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
	public BlockData get() {
		return blockData;
	}
}
