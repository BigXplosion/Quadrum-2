package dmillerw.quadrum.common.block;

import com.google.common.collect.Lists;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.Drop;
import dmillerw.quadrum.common.lib.TabQuadrum;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockCustom extends Block {

    public final BlockData data;

    public BlockCustom(BlockData data) {
        super(data.getBlockMaterial());

        this.data = data;
        setStepSound(data.getBlockSound());
        setLightLevel((float) data.lightLevel / (float) 15);
        setHardness(data.hardness);
        setResistance(data.resistance);
        setBlockName(data.name);
        setCreativeTab(TabQuadrum.BLOCK);

        this.opaque = !data.transparent;
        this.lightOpacity = !data.transparent ? 255 : 0;

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
    public void registerBlockIcons(IIconRegister p_149651_1_) {

    }

    @Override
    public IIcon getIcon(int side, int meta) {
        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
        ForgeDirection front = ForgeDirection.getOrientation(meta);
        if (meta == 0) front = ForgeDirection.SOUTH;

        if (forgeSide == front) {
            return TextureLoader.getBlockIcon(data, "front");
        } else if (forgeSide == front.getRotation(ForgeDirection.UP)) {
            return TextureLoader.getBlockIcon(data, "left");
        } else if (forgeSide == front.getRotation(ForgeDirection.UP).getOpposite()) {
            return TextureLoader.getBlockIcon(data, "right");
        } else if (forgeSide == front.getOpposite()) {
            return TextureLoader.getBlockIcon(data, "back");
        } else if (side == 0) {
            return TextureLoader.getBlockIcon(data, "bottom");
        } else if (side == 1) {
            return TextureLoader.getBlockIcon(data, "top");
        } else {
            return TextureLoader.getBlockIcon(data, "default");
        }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        return getIcon(side, world.getBlockMetadata(x, y, z));
    }

    @Override
    public boolean renderAsNormalBlock() {
        return !data.transparent;
    }

    @Override
    public boolean isOpaqueCube() {
        return data != null && !data.transparent;
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

    @Override
    public boolean canProvidePower() {
        return data.redstoneLevel > 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return data.redstoneLevel;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return data.flammable;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        return data.soil;
    }
}
