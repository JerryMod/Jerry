package pet.jerry.feature.features.dungeon;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pet.jerry.annotation.FeatureInfo;
import pet.jerry.feature.AbstractToggleableFeature;

@FeatureInfo(id = "blood_full", name = "Blood Full Notification")
public class BloodFullFeature extends AbstractToggleableFeature {

    @SubscribeEvent(receiveCanceled = true)
    public void onChatMessage(ClientChatReceivedEvent event) {
        if (event.type == 0 && event.message.getUnformattedText().startsWith("[BOSS] The Watcher: That will be enough for now.")) {
            mc.ingameGUI.displayTitle("§cBlood Full", "", 10, 200, 10);
            mc.thePlayer.playSound("note.pling", 1, 1);
        }
    }

}
