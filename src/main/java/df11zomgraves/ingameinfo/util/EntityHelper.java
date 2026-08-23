package df11zomgraves.ingameinfo.util;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class EntityHelper {
	public final static int WILDMARK = -1;
	
	public static int getItemCountInInventory(final Inventory inventory, final Item item) {
	    return getItemCountInInventory(inventory, item, WILDMARK);
	}
	
	public static int getItemCountInInventory(final Inventory inventory, final Item item, final int itemDamage) {
	    final int inventorySize = inventory.getContainerSize();
	    int count = 0;
	
	    for (int slot = 0; slot < inventorySize; slot++) {
	        final ItemStack itemStack = inventory.getItem(slot);
	
	        if (itemStack.getItem() == item && (itemDamage == WILDMARK || itemDamage == itemStack.getDamageValue())) {
	            count += itemStack.getCount();
	        }
	    }
	
	    return count;
	}
}
