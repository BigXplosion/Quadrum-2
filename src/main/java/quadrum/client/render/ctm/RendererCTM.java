package quadrum.client.render.ctm;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import quadrum.Quadrum;
import quadrum.util.RenderUtil;

public class RendererCTM implements ISimpleBlockRenderingHandler {

	RenderBlocksCTM rendererCTM = new RenderBlocksCTM();

	@Override
	public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		RenderUtil.renderBlock(block, meta, renderer);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks rendererOld) {
		int meta = world.getBlockMetadata(x, y, z);

		boolean ct = false;
		for (int i = 0; i < 6; i++) {
			if (ConnectedTextureHandler.getConnectedTexture(block, meta, i) != null) {
				ct = true;
				break;
			}
		}

		if (ct) {
			rendererCTM.blockAccess = world;
			rendererCTM.rendererOld = rendererOld;
			rendererCTM.setRenderBoundsFromBlock(block);
			return rendererCTM.renderStandardBlock(block, x, y, z);
		} else {
			int oldBottom = rendererOld.uvRotateBottom;
			int oldEast = rendererOld.uvRotateEast;
			int oldNorth = rendererOld.uvRotateNorth;
			int oldSouth = rendererOld.uvRotateSouth;
			int oldTop = rendererOld.uvRotateTop;
			int oldWest = rendererOld.uvRotateWest;

			rendererOld.setRenderBoundsFromBlock(block);
			boolean res = rendererOld.renderStandardBlock(block, x, y, z);

			return res;
		}
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return Quadrum.ctmRenderID;
	}
}
