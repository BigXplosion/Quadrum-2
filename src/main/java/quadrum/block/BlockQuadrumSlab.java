package quadrum.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import quadrum.block.data.BlockData;
import quadrum.lib.BlockStaticMethodHandler;
import quadrum.lib.IQuadrumObject;
import quadrum.util.Utils;

public class BlockQuadrumSlab extends BlockSlab implements IQuadrumObject {

	private final BlockData blockData;

	IIcon icon;

	public BlockQuadrumSlab(BlockData blockData) {
		super(false, blockData.getBlockMaterial());

		this.blockData = blockData;

		setTickRandomly(true);
		setStepSound(blockData.getBlockSound());
		setHardness(blockData.hardness);
		setResistance(blockData.resistance);
		setBlockName(blockData.name);
		setCreativeTab(blockData.getCreativeTab());
		setLightOpacity(0);

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
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icon = register.registerIcon(Utils.getIconForRegister(blockData.defaultTexture));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return icon;
	}

	@Override
	public String func_150002_b(int p_150002_1_) {
		return "";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Item getItem(World world, int x, int y, int z) {
		return Item.getItemFromBlock(this);
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
		BlockData data = blockData;
		if (data.meltingData != null && data.meltingData.getFluid() != null && world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) > data.meltingData.light - this.getLightOpacity())
			world.setBlock(x, y, z, data.meltingData.getFluid().getBlock());
	}

	@Override
	public BlockData get() {
		return blockData;
	}
}
