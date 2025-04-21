package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.gui.overlay.InfoItem;
import df11zomgraves.ingameinfo.util.EntityHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class TagPlayerEquipment extends Tag {
	public static final String[] TYPES = new String[] { "offhand", "mainhand", "helmet", "chestplate", "leggings", "boots" };
	public static final int[] SLOTS = new int[] { -2, -1, 3, 2, 1, 0 };
	protected final int slot;

	public TagPlayerEquipment(final int slot) {
		this.slot = slot;
	}

	@Override
	public String getCategory() {
		return "playerequipment";
	}

	protected ItemStack getItemStack(final int slot) {
		if (slot == -2)
			return player.getOffhandItem();
		if (slot == -1)
			return player.getMainHandItem();
		return player.getInventory().getArmor(slot);
	}

	public static class Name extends TagPlayerEquipment {
		public Name(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty()) {
				return "";
			}
			final int regularArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.ARROW);
			final int spectralArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.SPECTRAL_ARROW);
			final int tippedArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.TIPPED_ARROW);
			int arrowCount = regularArrows + spectralArrows + tippedArrows;
			Item item = itemStack.getItem();
			boolean showArrows;
			showArrows = item instanceof BowItem || item  instanceof CrossbowItem;
			final String arrows = showArrows ? " (" + arrowCount + ")" : "";
			return itemStack.getDisplayName().getString() + arrows;
		}
	}
	
	public static class ArrowCount extends TagPlayerEquipment {
		
		public ArrowCount(int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final int regularArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.ARROW);
			final int spectralArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.SPECTRAL_ARROW);
			final int tippedArrows = EntityHelper.getItemCountInInventory(player.getInventory(), Items.TIPPED_ARROW);
			int arrowCount = regularArrows + spectralArrows + tippedArrows;
			return String.valueOf(arrowCount);
		}
	}

	public static class UniqueName extends TagPlayerEquipment {
		public UniqueName(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty())
				return "";
			ResourceLocation res = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
			return res.toString();
		}
	}

	public static class Damage extends TagPlayerEquipment {
		public Damage(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty()) {
				return String.valueOf(-1);
			}

			return String.valueOf(itemStack.isDamageableItem() ? itemStack.getDamageValue() : 0);
		}
	}

	public static class MaximumDamage extends TagPlayerEquipment {
		public MaximumDamage(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty()) {
				return String.valueOf(-1);
			}

			return String.valueOf(itemStack.isDamageableItem() ? itemStack.getMaxDamage() : 0);
		}
	}

	public static class DamageLeft extends TagPlayerEquipment {
		public DamageLeft(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty()) {
				return String.valueOf(-1);
			}

			return String.valueOf(
					itemStack.isDamageableItem() ? itemStack.getMaxDamage() - itemStack.getDamageValue() : 0);
		}
	}

	public static class Quantity extends TagPlayerEquipment {
		public Quantity(final int slot) {
			super(slot);
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			if (itemStack.isEmpty()) {
				return String.valueOf(0);
			}

			return String.valueOf(EntityHelper.getItemCountInInventory(player.getInventory(), itemStack.getItem(),
					itemStack.getDamageValue()));
		}
	}

	public static class Icon extends TagPlayerEquipment {
		private final boolean large;

		public Icon(final int slot, final boolean large) {
			super(slot);
			this.large = large;
		}

		@Override
		public String getValue() {
			final ItemStack itemStack = getItemStack(this.slot);
			final InfoItem item = new InfoItem(itemStack, this.large);
			info.add(item);
			return getIconTag(item);
		}
	}

	public static void register() {
		for (int i = 0; i < TYPES.length; i++) {
			TagRegistry.INSTANCE.register(new Name(SLOTS[i]).setName(TYPES[i] + "name"));
			TagRegistry.INSTANCE.register(new UniqueName(SLOTS[i]).setName(TYPES[i] + "uniquename"));
			TagRegistry.INSTANCE.register(new Damage(SLOTS[i]).setName(TYPES[i] + "damage"));
			TagRegistry.INSTANCE.register(new MaximumDamage(SLOTS[i]).setName(TYPES[i] + "maxdamage"));
			TagRegistry.INSTANCE.register(new DamageLeft(SLOTS[i]).setName(TYPES[i] + "damageleft"));
			TagRegistry.INSTANCE.register(new Quantity(SLOTS[i]).setName(TYPES[i] + "quantity"));
			TagRegistry.INSTANCE.register(new Icon(SLOTS[i], false).setName(TYPES[i] + "icon"));
			TagRegistry.INSTANCE.register(new Icon(SLOTS[i], true).setName(TYPES[i] + "largeicon"));
		}
		TagRegistry.INSTANCE.register(new ArrowCount(0).setName("arrowcount"));
	}
}
