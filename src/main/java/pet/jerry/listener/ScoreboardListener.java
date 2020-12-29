package pet.jerry.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pet.jerry.event.SkyBlockConnectionEvent;

import java.util.Locale;

@SideOnly(Side.CLIENT)
public class ScoreboardListener {
	private static final Minecraft mc = Minecraft.getMinecraft();
	private long lastSkyBlockScoreboard = -1L;
	private boolean inSkyBlock = false;

	@SubscribeEvent
	public void onUpdate(TickEvent.ClientTickEvent event) {
		if(mc.theWorld != null && mc.thePlayer != null && !mc.isIntegratedServerRunning() && mc.getCurrentServerData() != null) {
			if(mc.getCurrentServerData().serverIP.toLowerCase(Locale.ROOT).endsWith("hypixel.net")) {
				boolean anyScoreboard = false;
				boolean skyblockScoreboardFound = false;
				Scoreboard sb = mc.theWorld.getScoreboard();
				for (ScoreObjective scoreObjective : sb.getScoreObjectives()) {
					anyScoreboard = true;
					if(scoreObjective.getName().equalsIgnoreCase("SBScoreboard")) {
						skyblockScoreboardFound = true;
						break;
					}
				}

				if(anyScoreboard) {
					if(!skyblockScoreboardFound && inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Exit());
						inSkyBlock = false;
					} else if(skyblockScoreboardFound && !inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Enter());
						inSkyBlock = true;
					}
				} else {
					if(System.currentTimeMillis() - lastSkyBlockScoreboard >= 10_000 && inSkyBlock) {
						MinecraftForge.EVENT_BUS.post(new SkyBlockConnectionEvent.Exit());
					}
				}

				if(skyblockScoreboardFound)
					lastSkyBlockScoreboard = System.currentTimeMillis();
			}
		}
	}
}
