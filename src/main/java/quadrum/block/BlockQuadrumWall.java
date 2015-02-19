package quadrum.block;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import quadrum.block.data.BlockData;
import quadrum.lib.BlockStaticMethodHandler;
import quadrum.lib.IQuadrumObject;
import quadrum.util.Utils;

public class BlockQuadrumWall extends BlockWall implements IQuadrumObject {

	private final BlockData blockData;

	IIcon icon;

	public BlockQuadrumWall(BlockData blockData) {
		super(blockData.getSimilarBlock());

		this.blockData = blockData;

		setStepSound(blockData.getBlockSound());
		setHardness(blockData.hardness);
		setResistance(blockData.resistance);
		setBlockName(blockData.name);
		setCreativeTab(blockData.getCreativeTab());
		setLightOpacity(0);

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

	@Override
	public boolean canConnectWallTo(IBlockAccess world, int x, int y, int z) {
		Block neighbour = world.getBlock(x, y, z);
		return neighbour instanceof BlockFenceGate || neighbour instanceof BlockWall || (neighbour.isOpaqueCube() && neighbour.renderAsNormalBlock());
	}

	@Override
	public BlockData get() {
		return blockData;
	}
}
