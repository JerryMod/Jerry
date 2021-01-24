package pet.jerry.feature.features.dungeon;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.Jerry;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.event.BlazeModelRenderEvent;
import pet.jerry.event.DungeonConnectionEvent;
import pet.jerry.event.SkyBlockConnectionEvent;
import pet.jerry.event.TileEntityPositionSetEvent;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.util.Utils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@FeatureInfo(id = "blaze_solver", name = "Blaze Room Solver")
public class BlazeRoomSolver extends AbstractToggleableFeature {
	private BlazeRenderListener listener;

	@SubscribeEvent
	void onEntitySpawn(TileEntityPositionSetEvent event) {
		if (event.getTileEntity() instanceof TileEntityChest
				&& Jerry.INSTANCE.getSkyBlock().getCurrentDungeon() != null
				&& null == listener) {
			if (mc.theWorld.getBlockState(event.getPos().up(1)).getBlock().equals(Blocks.iron_bars)) {
				BlockPos eighty = new BlockPos(event.getPos().getX(), 80, event.getPos().getZ());
				boolean isAscending = !(mc.theWorld.getBlockState(eighty).getBlock().equals(Blocks.iron_bars)
						|| mc.theWorld.getBlockState(eighty).getBlock().equals(Blocks.air)
						|| mc.theWorld.getBlockState(eighty).getBlock().equals(Blocks.chest));

				this.listener = new BlazeRenderListener((TileEntityChest) event.getTileEntity(), isAscending ? SortDirection.ASCENDING : SortDirection.DESCENDING);
				MinecraftForge.EVENT_BUS.register(listener);
				Utils.addChatMessage("Blaze chest located at " + event.getPos() + ". Starts at top: " + (isAscending ? "yes" : "no"));
			}
		}
	}

	@SubscribeEvent
	void onDungeonLeave(DungeonConnectionEvent.Exit event) {
		unlisten();
	}

	@SubscribeEvent
	void onSkyBlockLeave(SkyBlockConnectionEvent.Exit event) {
		unlisten();
	}

	private void unlisten() {
		if(null != listener) {
			MinecraftForge.EVENT_BUS.unregister(listener);
			this.listener = null;
		}
	}

	enum SortDirection {
		ASCENDING(Comparator.comparing(EntityBlaze::getHealth).reversed()),
		DESCENDING(Comparator.comparing(EntityBlaze::getHealth));

		private final Comparator<EntityBlaze> comparator;

		SortDirection(Comparator<EntityBlaze> comparator) {
			this.comparator = comparator;
		}

		public Comparator<EntityBlaze> getComparator() {
			return comparator;
		}
	}

	class BlazeRenderListener {
		private final TileEntityChest chest;
		private final SortDirection sortDirection;

		BlazeRenderListener(TileEntityChest chest, SortDirection sortDirection) {
			this.chest = chest;
			this.sortDirection = sortDirection;
		}

		@SubscribeEvent
		void renderBlaze(BlazeModelRenderEvent event) {
			if (!BlazeRoomSolver.this.isEnabled() || Jerry.INSTANCE.getSkyBlock().getCurrentDungeon() == null) return;

			List<Integer> entityIDs = event.getBlaze().worldObj.getEntities(EntityBlaze.class, (blaze) ->
					blaze != null && blaze.getDistance(chest.getPos().getX(), blaze.posY, chest.getPos().getZ()) < 16)
					.stream().sorted(this.sortDirection.getComparator()).map(EntityBlaze::getEntityId)
					.collect(Collectors.toList());

			int idx = entityIDs.indexOf(event.getBlaze().getEntityId());

			if (event instanceof BlazeModelRenderEvent.Pre) {
				GlStateManager.disableTexture2D();
				GlStateManager.enableAlpha();
				switch (idx) {
					case 0:
						GlStateManager.color(1, 0.12f, 0.12f, 1);
						break;
					case 1:
						GlStateManager.color(1, 0.41f, 0.12f, 1f);
						break;
					case 2:
						GlStateManager.color(1, 0.73f, 0.12f, 1f);
						break;
					default:
						GlStateManager.color(1, 1, 1, 1f);
				}
			} else if (event instanceof BlazeModelRenderEvent.Post) {
				GlStateManager.disableAlpha();
				GlStateManager.enableTexture2D();
			}
		}
	}
}
