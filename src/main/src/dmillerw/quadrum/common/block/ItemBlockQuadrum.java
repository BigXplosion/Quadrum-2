package dmillerw.quadrum.common.block;

import dmillerw.quadrum.common.block.data.BlockData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author dmillerw
 */
public class ItemBlockQuadrum extends ItemBlock {

    private final BlockData data;

    public ItemBlockQuadrum(Block block) {
        super(block);
        if (block instanceof BlockQuadrumSlab) {
            data = ((BlockQuadrumSlab)block).data;
        } else if (block instanceof BlockQuadrumStair) {
            data = ((BlockQuadrumStair)block).data;
        } else if (block instanceof BlockQuadrumFence) {
            data = ((BlockQuadrumFence)block).data;
        } else {
            data = ((BlockQuadrum)block).data;
        }
        setMaxStackSize(data.maxStackSize);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
        boolean result = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (data.getBlockType() == BlockData.BlockType.BLOCK && result) {
            int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            if (l == 0) world.setBlockMetadataWithNotify(x, y, z, 2, 2);
            if (l == 1) world.setBlockMetadataWithNotify(x, y, z, 5, 2);
            if (l == 2) world.setBlockMetadataWithNotify(x, y, z, 3, 2);
            if (l == 3) world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        return result;
    }
}
