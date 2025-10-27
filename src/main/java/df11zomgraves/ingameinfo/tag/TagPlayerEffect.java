package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.gui.overlay.InfoEffect;
import df11zomgraves.ingameinfo.handler.ConfigurationHandler;
import df11zomgraves.ingameinfo.util.StringConvertUtils;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.Collection;

public abstract class TagPlayerEffect extends Tag {
	// TODO: this shouldn't be hardcoded...
	public static final int MAXIMUM_INDEX = 48 /* Potion.potionTypes.length */;

	protected static MobEffectInstance[] potionEffects = null;
	protected final int index;

	public TagPlayerEffect(final int index) {
		this.index = index;
	}

	@Override
	public String getName() {
		if (this.index == -1)
			return super.getName();
		return super.getName() + this.index;
	}

	@Override
	public String[] getAliases() {
		final String[] aliases = super.getAliases();
		final String[] aliasesIndexed = new String[aliases.length];
		for (int i = 0; i < aliases.length; i++) {
			aliasesIndexed[i] = aliases[i] + this.index;
		}
		return aliasesIndexed;
	}

	@Override
	public boolean isIndexed() {
		return true;
	}

	@Override
	public int getMaximumIndex() {
		return MAXIMUM_INDEX - 1;
	}

	@Override
	public String getCategory() {
		return "playerpotion";
	}

	protected void updatePotionEffects() {
		if (potionEffects == null) {
			final Collection<MobEffectInstance> potionEffectCollection = player.getActiveEffects();
			potionEffects = new MobEffectInstance[potionEffectCollection.size()];
			if (potionEffectCollection.size() > 0) {
				int index = 0;

				for (final MobEffectInstance potionEffect : potionEffectCollection) {
					potionEffects[index++] = potionEffect;
				}
			}
		}
	}

	public static class Effect extends TagPlayerEffect {
		public Effect(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			int potionIndex = this.index;
			if (potionEffects.length > potionIndex) {
				String str = I18n.get(potionEffects[potionIndex].getEffect().getDescriptionId());
				int amp = potionEffects[potionIndex].getAmplifier();
				if (ConfigurationHandler.numericAmplifier)
					return String.format("%s %d", str, amp + 1);
				else
					return str + StringConvertUtils.numToRoman(amp);
			}
			return "";
		}
	}

	public static class Duration extends TagPlayerEffect {
		public Duration(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			if (potionEffects.length > this.index) {
				int duration = potionEffects[this.index].getDuration();
				return StringUtil.formatTickDuration(duration);

			}
			return "0:00";
		}
	}

	public static class DurationTicks extends TagPlayerEffect {
		public DurationTicks(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			if (potionEffects.length > this.index) {
				return String.valueOf(potionEffects[this.index].getDuration());
			}
			return "0";
		}
	}

	public static class Negative extends TagPlayerEffect {
		public Negative(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			if (potionEffects.length > this.index) {
				boolean isBadEffect = potionEffects[this.index].getEffect().getCategory() == MobEffectCategory.HARMFUL;
				return String.valueOf(isBadEffect);
			}
			return "false";
		}
	}

	public static class Icon extends TagPlayerEffect {
		private final boolean large;

		public Icon(final int index, final boolean large) {
			super(index);
			this.large = large;
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			if (potionEffects.length > this.index) {
				final MobEffect mobEffect = potionEffects[this.index].getEffect();
				final InfoEffect effect = new InfoEffect(mobEffect, this.large);
				info.add(effect);
				return getIconTag(effect);
			}
			return "";
		}
	}
	
	public static class EffectCount extends TagPlayerEffect {

		public EffectCount(int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updatePotionEffects();
			return String.valueOf(potionEffects.length);
		}
	}

	public static void register() {
		for (int i = 0; i < MAXIMUM_INDEX; i++) {
			TagRegistry.INSTANCE.register(new Effect(i).setName("potioneffect"));
			TagRegistry.INSTANCE.register(new Duration(i).setName("potionduration"));
			TagRegistry.INSTANCE.register(new DurationTicks(i).setName("potiondurationticks"));
			TagRegistry.INSTANCE.register(new Negative(i).setName("potionnegative"));
			TagRegistry.INSTANCE.register(new Icon(i, false).setName("potionicon"));
			TagRegistry.INSTANCE.register(new Icon(i, true).setName("potionlargeicon"));
		}
		TagRegistry.INSTANCE.register(new EffectCount(-1).setName("effectcount"));
	}

	public static void releaseResources() {
		potionEffects = null;
	}
}
