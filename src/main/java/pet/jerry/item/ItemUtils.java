package pet.jerry.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public final class ItemUtils {
	private static final String EXTRA_ATTRIBUTES = "ExtraAttributes";

	public static String getSkyBlockID(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound compound = stack.getTagCompound();
			if(compound.hasKey(EXTRA_ATTRIBUTES)) {
				compound = compound.getCompoundTag(EXTRA_ATTRIBUTES);
				if(compound.hasKey("id")) {
					return compound.getString("id");
				}
			}
		}

		return null;
	}
}
