package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.util.ChunkHelper;
import net.minecraft.Util;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import java.util.Locale;
import java.util.Objects;

public abstract class TagWorld extends Tag {
	@Override
	public String getCategory() {
		return "world";
	}

//	public static class Name extends TagWorld {
//		@Override
//		public String getValue() {
//			if (server != null) {
//				final WorldServer worldServer = DimensionManager.getWorld(player.dimension);
//				if (worldServer != null) {
//					return worldServer.getWorldInfo().getWorldName();
//				}
//			}
//			return world.getWorldInfo().getWorldName();
//		}
//	}

//	TODO: removed, 1.18 not support.
//	public static class Size extends TagWorld {
//		@Override
//		public String getValue() {
//			MinecraftServer server = minecraft.player.getServer();
//			if (server != null) {
//				final WorldServer worldServer = DimensionManager.getWorld(player.dimension);
//				if (worldServer != null) {
//					return String.valueOf(worldServer.getWorldInfo().getSizeOnDisk());
//				}
//			}
//			return String.valueOf(world.getWorldInfo().getSizeOnDisk());
//		}
//	}

//	public static class SizeMB extends TagWorld {
//		@Override
//		public String getValue() {
//			if (server != null) {
//				final WorldServer worldServer = DimensionManager.getWorld(player.dimension);
//				if (worldServer != null) {
//					return String.format(Locale.ENGLISH, "%.1f",
//							worldServer.getWorldInfo().getSizeOnDisk() / 1048576.0);
//				}
//			}
//			return String.format(Locale.ENGLISH, "%.1f", world.getWorldInfo().getSizeOnDisk() / 1048576.0);
//		}
//	}

	public static class Seed extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(seed);
		}
	}

	public static class Difficulty extends TagWorld {
		@Override
		public String getValue() {
			String difficulty = world.getDifficulty().getDisplayName().getString();;
			return I18n.get(difficulty);
		}
	}

	public static class DifficultyId extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(world.getDifficulty().getId());
		}
	}

	public static class LocalDifficulty extends TagWorld {
		@Override
		public String getValue() {
			DifficultyInstance diff = player.level.getCurrentDifficultyAt(playerPosition);
			return String.format(Locale.ENGLISH, "%.2f", diff.getSpecialMultiplier());
		}
	}

	public static class Dimension extends TagWorld {
		@Override
		public String getValue() {
			ResourceKey<Level> dim = world.dimension();
			return dim.location().toString();
		}
	}

	public static class CurrentBiome extends TagWorld {
		String rawBiomeStr;

		@Override
		public String getValue() {
			Holder<Biome> biome = Objects.requireNonNull(world).getBiome(playerPosition);
			biome.unwrapKey().ifPresent(key -> {
				rawBiomeStr = Util.makeDescriptionId("biome", key.location());
			});
			return I18n.get(rawBiomeStr);
		}
	}

	public static class Daytime extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(world.isDay());
		}
	}

	public static class MoonPhase extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(world.getMoonPhase());
		}
	}

	public static class Raining extends TagWorld {
		@Override
		public String getValue() {
			Holder<Biome> biome = world.getBiome(playerPosition);
			boolean canRain = biome.value().warmEnoughToRain(playerPosition);
			return String.valueOf(world.isRaining() && canRain);
		}
	}

	public static class Thundering extends TagWorld {
		@Override
		public String getValue() {
			Holder<Biome> biome = world.getBiome(playerPosition);
			boolean canRain = biome.value().warmEnoughToRain(playerPosition);
			return String.valueOf(world.isThundering() && canRain);
		}
	}

	public static class Snowing extends TagWorld {
		@Override
		public String getValue() {
			Holder<Biome> biome = world.getBiome(playerPosition);
			boolean canSnow = biome.value().coldEnoughToSnow(playerPosition);
			return String.valueOf(world.isRaining() && canSnow);
		}
	}

//	TODO: removed, 1.18 not support.
//	public static class NextWeatherChange extends TagWorld {
//		@Override
//		public String getValue() {
//			if (server == null)
//				return "?";
//			final WorldInfo worldInfo = server.getWorld(0).getWorldInfo();
//			final int clearTime = worldInfo.getCleanWeatherTime();
//			final float seconds = (clearTime > 0 ? clearTime : worldInfo.getRainTime()) / 20f;
//			if (seconds < 60) {
//				return String.format(Locale.ENGLISH, "%.1fs", seconds);
//			} else if (seconds < 3600) {
//				return String.format(Locale.ENGLISH, "%.1fm", seconds / 60);
//			}
//			return String.format(Locale.ENGLISH, "%.1fh", seconds / 3600);
//		}
//	}

	public static class Slimes extends TagWorld {
		@Override
		public String getValue() {
			boolean slimeChunk = ChunkHelper.isSlimeChunk(seed, playerPosition);
			int playerPos = playerPosition.y;
			boolean isSwamp = world.getBiome(playerPosition).is(Biomes.SWAMP);
			return String.valueOf(seed != 0 && slimeChunk || isSwamp && playerPos > 50 && playerPos < 70);
		}
	}

	public static class SlimeChunk extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(seed != 0 && ChunkHelper.isSlimeChunk(seed, playerPosition));
		}
	}

	public static class Hardcore extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(player.level.getLevelData().isHardcore());
		}
	}

	public static class Temperature extends TagWorld {
		@Override
		public String getValue() {
			return String.valueOf(world.getBiome(playerPosition).value().getBaseTemperature());
		}
	}

//	TODO: removed, 1.18 not support.
//	public static class LocalTemperature extends TagWorld {
//		@Override
//		public String getValue() {
//			return String.format(Locale.ENGLISH, "%.2f",
//					world.getBiome(playerPosition).getTemperature(playerPosition) * 100);
//		}
//	}

//	public static class Humidity extends TagWorld {
//		@Override
//		public String getValue() {
//			float f = world.getBiome(playerPosition).value().getDownfall();
//			return String.format(Locale.ENGLISH, "%.0f", f * 100);
//		}
//	}
	
	// reference net.minecraftforge.server.command.TPSCommand
//	private static long mean(long[] values) {
//		long sum = 0L;
//		for (long v : values)
//			sum += v;
//		return sum / values.length;
//	}

//	public static class TPS extends TagWorld {
//		@Override
//		public String getValue() {
//			ResourceKey<Level> dim = world.dimension();
//			long[] times = minecraft.player.getServer().getTickTime(dim);
//			if (times == null)
//				return "-1";
//			double worldTickTime = mean(times) * 1.0E-6D;
//			double worldTPS = Math.min(1000.0 / worldTickTime, 20);
//			return String.format("###0.00", worldTPS);
//		}
//	}

//	public static class MSPT extends TagWorld {
//		@Override
//		public String getValue() {
//			ResourceKey<Level> dim = world.dimension();
//			long[] times = minecraft.player.getServer().getTickTime(dim);
//			if (times == null)
//				return "-1";
//			double worldTickTime = mean(times) * 1.0E-6D;
//			return String.format("###0.00", worldTickTime);
//		}
//	}

	public static void register() {
//		TagRegistry.INSTANCE.register(new Name().setName("worldname"));
//		TagRegistry.INSTANCE.register(new Size().setName("worldsize"));
//		TagRegistry.INSTANCE.register(new SizeMB().setName("worldsizemb"));
		TagRegistry.INSTANCE.register(new Seed().setName("seed"));
		TagRegistry.INSTANCE.register(new Difficulty().setName("difficulty"));
		TagRegistry.INSTANCE.register(new DifficultyId().setName("difficultyid"));
		TagRegistry.INSTANCE.register(new LocalDifficulty().setName("localdifficulty"));
		TagRegistry.INSTANCE.register(new Dimension().setName("dimension"));
		TagRegistry.INSTANCE.register(new CurrentBiome().setName("biome"));
		TagRegistry.INSTANCE.register(new Daytime().setName("daytime"));
		TagRegistry.INSTANCE.register(new MoonPhase().setName("moonphase"));
		TagRegistry.INSTANCE.register(new Raining().setName("raining"));
		TagRegistry.INSTANCE.register(new Thundering().setName("thundering"));
		TagRegistry.INSTANCE.register(new Snowing().setName("snowing"));
//		TagRegistry.INSTANCE.register(new NextWeatherChange().setName("nextweatherchange").setAliases("nextrain"));
		TagRegistry.INSTANCE.register(new Slimes().setName("slimes"));
		TagRegistry.INSTANCE.register(new SlimeChunk().setName("slimechunk"));
		TagRegistry.INSTANCE.register(new Hardcore().setName("hardcore"));
		TagRegistry.INSTANCE.register(new Temperature().setName("temperature"));
//		TagRegistry.INSTANCE.register(new LocalTemperature().setName("localtemperature"));
//		TagRegistry.INSTANCE.register(new Humidity().setName("humidity"));
//		TagRegistry.INSTANCE.register(new TPS().setName("tps"));
//		TagRegistry.INSTANCE.register(new MSPT().setName("mspt"));
	}
}
