package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.gui.overlay.InfoIcon;
import df11zomgraves.ingameinfo.util.MBlockPos;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.LightLayer;

import java.util.Locale;

public abstract class TagPlayerGeneral extends Tag {
	protected final MBlockPos pos = new MBlockPos();

	@Override
	public String getCategory() {
		return "playergeneral";
	}

	public static class Light extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				int skyLevel = world.getBrightness(LightLayer.SKY, playerPosition) - world.getSkyDarken();
				int blockLevel = world.getBrightness(LightLayer.BLOCK, playerPosition);
				int light = Math.max(skyLevel, blockLevel);
				return String.valueOf(light);
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class LightEye extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				this.pos.set(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
				int skyLevel = world.getBrightness(LightLayer.SKY, this.pos) - world.getSkyDarken();
				int blockLevel = world.getBrightness(LightLayer.BLOCK, this.pos);
				int light = Math.max(skyLevel, blockLevel);
				return String.valueOf(light);
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class LightNoSun extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				int blockLevel = world.getBrightness(LightLayer.BLOCK, playerPosition);
				return String.valueOf(blockLevel);
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class LightNoSunEye extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				this.pos.set(player.getX(), player.getY() + player.getEyeHeight(), player.getZ());
				int blockLevel = world.getBrightness(LightLayer.BLOCK, this.pos);
				return String.valueOf(blockLevel);
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class LightSun extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				int skyLevel = world.getBrightness(LightLayer.SKY, playerPosition) - world.getSkyDarken();
				return String.valueOf(Mth.clamp(skyLevel, 0, 15));
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class LightSunEye extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				this.pos.set(playerPosition.getX(), player.getY() + player.getEyeHeight(), playerPosition.getZ());
				int skyLevel = world.getBrightness(LightLayer.SKY, this.pos) - world.getSkyDarken();
				return String.valueOf(Mth.clamp(skyLevel, 0, 15));
			} catch (final Exception e) {
				return "0";
			}
		}
	}

	public static class Score extends TagPlayerGeneral {
		@Override
		public String getValue() {
			try {
				return String.valueOf(player.getScore());
			} catch (final Exception var12) {
				return "0";
			}
		}
	}

	public static class GameMode extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return I18n.get("gameMode." + minecraft.gameMode.getPlayerMode().getName());
		}
	}

	public static class GameModeId extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(minecraft.gameMode.getPlayerMode().getId());
		}
	}

	public static class Health extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.format(Locale.ENGLISH, "%.2f", player.getHealth());
		}
	}

	public static class MaxHealth extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.format(Locale.ENGLISH, "%.2f", player.getMaxHealth());
		}
	}

	public static class Absorption extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.format(Locale.ENGLISH, "%.2f", player.getAbsorptionAmount());
		}
	}

	public static class Armor extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.getArmorValue());
		}
	}

	public static class FoodLevel extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.getFoodData().getFoodLevel());
		}
	}

	public static class MaxFoodLevel extends TagPlayerGeneral {
		@Override
		public String getValue() {
			// TODO: use Forge method when it's in
			return String.valueOf(20);
		}
	}

	public static class SaturationLevel extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.format(Locale.ENGLISH, "%.2f", player.getFoodData().getSaturationLevel());
		}
	}

    public static class Exhaustion extends TagPlayerGeneral {
        @Override
        public String getValue() {
            return String.format(Locale.ENGLISH, "%.2f", player.getFoodData().getExhaustionLevel());
        }
    }

	public static class AirTicks extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.getAirSupply());
		}
	}

	public static class MaxAirTicks extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.getMaxAirSupply());
		}
	}

	public static class PlayerLevel extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.experienceLevel);
		}
	}

	public static class CurrentExperience extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf((int) Math.ceil(player.experienceProgress * player.getXpNeededForNextLevel()));
		}
	}

	public static class ExperienceUntilNext extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String
					.valueOf((int) Math.floor((1.0 - player.experienceProgress) * player.getXpNeededForNextLevel()));
		}
	}

	public static class ExperienceCap extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.getXpNeededForNextLevel());
		}
	}

	public static class Username extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return player.getGameProfile().getName();
		}
	}

	public static class InWater extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isInWater());
		}
	}

	public static class Wet extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isInWaterRainOrBubble());
		}
	}

	public static class Alive extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isAlive());
		}
	}

	public static class Burning extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isOnFire());
		}
	}

	public static class Riding extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isPassenger());
		}
	}

	public static class Sneaking extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isShiftKeyDown());
		}
	}

	public static class Sprinting extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isSprinting());
		}
	}

	public static class Invisible extends TagPlayerGeneral {
		@Override
		public String getValue() {
			return String.valueOf(player.isInvisible());
		}
	}
	
	public static class ArmorToughness extends TagPlayerGeneral {
		@Override
		public String getValue() {
			if (player.getAttributes().hasAttribute(Attributes.ARMOR_TOUGHNESS))
				return String.valueOf(player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));
			return "0";
		}
	}
	
	public static class PlayerIcon extends TagPlayerGeneral {
		@Override
		public String getValue() {
			final PlayerInfo playerInfo = minecraft.getConnection().getPlayerInfo(player.getUUID());
			final InfoIcon icon = new InfoIcon(playerInfo.getSkinLocation());
			icon.setTextureData(8, 8, 8, 8, 64, 64);
			icon.setDisplayDimensions(0, 0, 8, 8);
			info.add(icon);
			return getIconTag(icon);
		}
	}

	public static void register() {
		TagRegistry.INSTANCE.register(new Light().setName("light"));
		TagRegistry.INSTANCE.register(new LightEye().setName("lighteye"));
		TagRegistry.INSTANCE.register(new LightNoSun().setName("lightnosun"));
		TagRegistry.INSTANCE.register(new LightNoSunEye().setName("lightnosuneye"));
		TagRegistry.INSTANCE.register(new LightSun().setName("lightsun"));
		TagRegistry.INSTANCE.register(new LightSunEye().setName("lightsuneye"));
		TagRegistry.INSTANCE.register(new Score().setName("score"));
		TagRegistry.INSTANCE.register(new GameMode().setName("gamemode"));
		TagRegistry.INSTANCE.register(new GameModeId().setName("gamemodeid"));
		TagRegistry.INSTANCE.register(new Health().setName("health").setAliases("healthpoints"));
		TagRegistry.INSTANCE.register(new MaxHealth().setName("maxhealth").setAliases("maxhealthpoints"));
		TagRegistry.INSTANCE.register(new Armor().setName("armor").setAliases("armorpoints"));
		TagRegistry.INSTANCE.register(new FoodLevel().setName("foodlevel").setAliases("foodpoints"));
		TagRegistry.INSTANCE.register(new MaxFoodLevel().setName("maxfoodlevel").setAliases("maxfoodpoints"));
		TagRegistry.INSTANCE.register(new SaturationLevel().setName("saturation").setAliases("foodsaturation"));
		TagRegistry.INSTANCE.register(new Exhaustion().setName("exhaustion").setAliases("foodexhaustion"));
		TagRegistry.INSTANCE.register(new AirTicks().setName("airticks"));
		TagRegistry.INSTANCE.register(new MaxAirTicks().setName("maxairticks"));
		TagRegistry.INSTANCE.register(new PlayerLevel().setName("playerlevel"));
		TagRegistry.INSTANCE.register(new CurrentExperience().setName("xpthislevel"));
		TagRegistry.INSTANCE.register(new ExperienceUntilNext().setName("xpuntilnext"));
		TagRegistry.INSTANCE.register(new ExperienceCap().setName("xpcap"));
		TagRegistry.INSTANCE.register(new Username().setName("username"));
		TagRegistry.INSTANCE.register(new InWater().setName("inwater").setAliases("underwater"));
		TagRegistry.INSTANCE.register(new Wet().setName("wet"));
		TagRegistry.INSTANCE.register(new Alive().setName("alive"));
		TagRegistry.INSTANCE.register(new Burning().setName("burning"));
		TagRegistry.INSTANCE.register(new Riding().setName("riding"));
		TagRegistry.INSTANCE.register(new Sneaking().setName("sneaking"));
		TagRegistry.INSTANCE.register(new Sprinting().setName("sprinting"));
		TagRegistry.INSTANCE.register(new Invisible().setName("invisible"));
		TagRegistry.INSTANCE.register(new Absorption().setName("absorption"));
		TagRegistry.INSTANCE.register(new ArmorToughness().setName("armortoughness"));
		TagRegistry.INSTANCE.register(new PlayerIcon().setName("playericon"));
	}
}
