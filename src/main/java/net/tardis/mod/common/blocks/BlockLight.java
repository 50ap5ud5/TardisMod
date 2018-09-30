package net.tardis.mod.common.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.tardis.mod.client.creativetabs.TardisTabs;

public class BlockLight extends BlockBase {

	public BlockLight() {
        setCreativeTab(TardisTabs.MAIN);
	}

	@Override
	public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
		return 15;
	}

}
