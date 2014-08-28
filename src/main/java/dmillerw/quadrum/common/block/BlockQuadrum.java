package dmillerw.quadrum.common.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dmillerw.quadrum.client.texture.TextureLoader;
import dmillerw.quadrum.common.block.data.BlockData;
import dmillerw.quadrum.common.block.data.BlockLoader;
import dmillerw.quadrum.common.lib.BlockStaticMethodHandler;
import dmillerw.quadrum.common.lib.IQuadrumBlock;
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

import java.util.ArrayList;

/**
 * @author dmillerw
 */
public class BlockQuadrum extends Block implements IQuadrumBlock {

    public final String name;

    public BlockQuadrum(BlockData data) {
        super(data.getBlockMaterial());

        this.name = data.name;

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
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return BlockLoader.blockDataMap.get(name).transparent ? 1 : 0;
    }

    @Override
    public void registerBlockIcons(IIconRegister p_149651_1_) {

    }

    @Override
    public IIcon getIcon(int side, int meta) {
        ForgeDirection forgeSide = ForgeDirection.getOrientation(side);
        ForgeDirection front = ForgeDirection.getOrientation(meta);
        if (meta == 0) front = ForgeDirection.SOUTH;

        BlockData data = BlockLoader.blockDataMap.get(name);

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
        return !BlockLoader.blockDataMap.get(name).transparent;
    }

    @Override
    public boolean isOpaqueCube() {
        return BlockLoader.blockDataMap.get(name) != null && !BlockLoader.blockDataMap.get(name).transparent;
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
    public boolean canProvidePower() {
        return BlockLoader.blockDataMap.get(name).redstoneLevel > 0;
    }

    @Override
    public int isProvidingWeakPower(IBlockAccess world, int x, int y, int z, int side) {
        return BlockLoader.blockDataMap.get(name).redstoneLevel;
    }

    @Override
    public boolean isFlammable(IBlockAccess world, int x, int y, int z, ForgeDirection face) {
        return BlockLoader.blockDataMap.get(name).flammable;
    }

    @Override
    public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable) {
        return BlockLoader.blockDataMap.get(name).soil;
    }

    @Override
    public String getName() {
        return name;
    }
}
