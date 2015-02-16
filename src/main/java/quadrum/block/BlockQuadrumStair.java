package quadrum.block;

import java.util.ArrayList;

import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import quadrum.block.data.BlockData;
import quadrum.client.texture.TextureLoader;
import quadrum.lib.BlockStaticMethodHandler;
import quadrum.lib.IQuadrumObject;

public class BlockQuadrumStair extends BlockStairs implements IQuadrumObject {
	private final BlockData blockData;

	public BlockQuadrumStair(BlockData blockData) {
		super(blockData.getSimilarBlock(), 0);

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
	public void registerBlockIcons(IIconRegister register) {
		TextureLoader.registerIcons(register, this);
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		return TextureLoader.getIcon(this, "default");
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
	public BlockData get() {
		return blockData;
	}
}
