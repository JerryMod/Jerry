package pet.jerry.event;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public class TileEntityPositionSetEvent extends Event {
	private final TileEntity entity;
	private final BlockPos pos;

	public TileEntityPositionSetEvent(TileEntity entity, BlockPos pos) {
		this.entity = entity;
		this.pos = pos;
	}

	public BlockPos getPos() {
		return pos;
	}

	public TileEntity getTileEntity() {
		return entity;
	}
}
