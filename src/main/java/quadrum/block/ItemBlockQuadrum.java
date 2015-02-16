package quadrum.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import quadrum.block.data.BlockData;
import quadrum.lib.IQuadrumObject;
import quadrum.lib.TypeSpecific;

public class ItemBlockQuadrum extends ItemBlock {
	private final BlockData blockData;

	public ItemBlockQuadrum(Block block) {
		super(block);
		if (block instanceof IQuadrumObject && ((IQuadrumObject) block).get() instanceof BlockData) {
			blockData = (BlockData) ((IQuadrumObject) block).get();
			setMaxStackSize(blockData.maxStackSize);
		} else
			throw new RuntimeException("A non Quadrum block tried to use the Quadrum ItemBlock");
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
		if (blockData.getBlockType() == TypeSpecific.Type.BLOCK && result) {
			int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
			if (l == 0)
				world.setBlockMetadataWithNotify(x, y, z, 2, 2);
			if (l == 1)
				world.setBlockMetadataWithNotify(x, y, z, 5, 2);
			if (l == 2)
				world.setBlockMetadataWithNotify(x, y, z, 3, 2);
			if (l == 3)
				world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		return result;
	}
}
