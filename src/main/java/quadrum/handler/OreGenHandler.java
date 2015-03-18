package quadrum.handler;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import cpw.mods.fml.common.IWorldGenerator;

public class OreGenHandler implements IWorldGenerator {

	public WorldGenMinable generator;
	public int veinSize;
	public int amount;
	public int minHeight;
	public int maxHeight;
	public boolean nether;

	public OreGenHandler(Block block, int amount, int veinSize, int maxHeight, int minHeight, boolean nether) {
		this.generator = new WorldGenMinable(block, 0, veinSize, nether ? Blocks.netherrack : Blocks.stone);
		this.amount = amount;
		this.veinSize = veinSize;
		this.maxHeight = maxHeight;
		this.minHeight = minHeight;
		this.nether = nether;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.isHellWorld && nether) {
			generate(random, chunkX, chunkZ, world);
		} else if (world.provider.isSurfaceWorld() && world.provider.terrainType != WorldType.FLAT && !nether) {
			generate(random, chunkX, chunkZ, world);
		}
	}

	public void generate(Random random, int chunkX, int chunkZ, World world) {
		for (int i = 0; i < amount ; i++) {
			int x = chunkX + random.nextInt(16);
			int y = minHeight + random.nextInt(maxHeight - minHeight);
			int z = chunkZ + random.nextInt(16);
			generator.generate(world, random, x, y, z);
		}
	}
}
