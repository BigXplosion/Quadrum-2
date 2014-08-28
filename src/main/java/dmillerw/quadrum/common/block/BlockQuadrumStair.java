package dmillerw.quadrum.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.lib.BlockStaticMethodHandler;
import dmillerw.quadrum.common.lib.IQuadrumBlock;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockQuadrumStair extends BlockStairs implements IQuadrumBlock {

    public final String name;

    public BlockQuadrumStair(BlockData data) {
        super(data.getSimilarBlock(), 0);

        this.name = data.name;

        setStepSound(data.getBlockSound());
        setHardness(data.hardness);
        setResistance(data.resistance);
        setBlockName(data.name);
        setCreativeTab(TabQuadrum.BLOCK);
        setLightOpacity(0);

        if (data.requiresTool) {
            setHarvestLevel(data.getHarvestTool(), data.miningLevel);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return BlockLoader.blockDataMap.get(name).transparent ? 1 : 0;
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
    public void registerBlockIcons(IIconRegister p_149651_1_) {

    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return TextureLoader.getBlockIcon(BlockLoader.blockDataMap.get(name), "default");
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return BlockStaticMethodHandler.getDrops(this, BlockLoader.blockDataMap.get(name), world, x, y, z, metadata, fortune);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return BlockLoader.blockDataMap.get(name).collision ? super.getCollisionBoundingBoxFromPool(world, x, y, z) : null;
    }

    @Override
    public String getName() {
        return name;
    }
}
