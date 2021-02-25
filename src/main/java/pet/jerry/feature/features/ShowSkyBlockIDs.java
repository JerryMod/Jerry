package pet.jerry.feature.features;

import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.feature.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;
import pet.jerry.item.ItemUtils;

@FeatureInfo(id = "show_sb_ids", name = "Show SkyBlock IDs")
public class ShowSkyBlockIDs extends AbstractToggleableFeature {
	@SubscribeEvent
	void onTooltip(ItemTooltipEvent event) {
		if(!this.isEnabled()) return;

		String id = ItemUtils.getSkyBlockID(event.itemStack);
		if(null != id) {
			event.toolTip.add(EnumChatFormatting.DARK_GRAY + "SkyBlock ID: " + EnumChatFormatting.RED + id);
		}
	}
}
