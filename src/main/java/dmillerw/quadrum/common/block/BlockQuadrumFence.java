package dmillerw.quadrum.common.block;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.lib.data.Drop;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.block.BlockFence;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockQuadrumFence extends BlockFence {

    public final BlockData data;

    public BlockQuadrumFence(BlockData data) {
        super("", data.getBlockMaterial());

        this.data = data;
        setStepSound(data.getBlockSound());
        setHardness(data.hardness);
        setResistance(data.resistance);
        setBlockName(data.name);
        setCreativeTab(TabQuadrum.BLOCK);

        if (data.requiresTool) {
            setHarvestLevel(data.getHarvestTool(), data.miningLevel);
        }

        for (String string : data.oreDictionary) {
            OreDictionary.registerOre(string, this);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return data.transparent ? 1 : 0;
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
        return TextureLoader.getBlockIcon(data, "default");
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        if (data.drops.length == 0 && data.dropsSelf) {
            return super.getDrops(world, x, y, z, metadata, fortune);
        }
        ArrayList<ItemStack> stackList = Lists.newArrayList();
        for (Drop drop : data.drops) {
            stackList.add(new ItemStack(drop.getDrop(), drop.getDropAmount(), drop.damage));
        }
        return stackList;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return data.collision ? super.getCollisionBoundingBoxFromPool(world, x, y, z) : null;
    }
}
