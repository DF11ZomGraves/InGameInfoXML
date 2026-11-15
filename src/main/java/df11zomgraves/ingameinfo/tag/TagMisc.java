package df11zomgraves.ingameinfo.tag;

import java.util.Collection;

import df11zomgraves.ingameinfo.gui.overlay.InfoIcon;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;

public abstract class TagMisc extends Tag {

	@Override
	public String getCategory() {
		return "misc";
	}

	public static class MemoryMaximum extends TagMisc {
		@Override
		public String getValue() {
			return String.valueOf(Runtime.getRuntime().maxMemory());
		}
	}

	public static class MemoryTotal extends TagMisc {
		@Override
		public String getValue() {
			return String.valueOf(Runtime.getRuntime().totalMemory());
		}
	}

	public static class MemoryFree extends TagMisc {
		@Override
		public String getValue() {
			return String.valueOf(Runtime.getRuntime().freeMemory());
		}
	}

	public static class MemoryUsed extends TagMisc {
		@Override
		public String getValue() {
			return String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
		}
	}

	public static class FPS extends TagMisc {
		@Override
		public String getValue() {
			String fpsString = minecraft.fpsString;
			return fpsString.split(" ")[0];
		}
	}

	public static class EntitiesRendered extends TagMisc {
		@Override
		public String getValue() {
			String entitiesRendered = minecraft.levelRenderer.getEntityStatistics();
			return entitiesRendered.split("/")[0].split(" ")[1];
		}
	}

	public static class EntitiesTotal extends TagMisc {
		@Override
		public String getValue() {
			String entitiesTotal = minecraft.levelRenderer.getEntityStatistics();
			return entitiesTotal.split("/")[1].split(",")[0];
		}
	}
	
	public static class ResourcePack extends TagMisc {
		@Override
		public String getValue() {
			final PackRepository resourcePackRepository = minecraft.getResourcePackRepository();
			if (resourcePackRepository == null)
				return "";
			final Collection<Pack> selectedPacks = resourcePackRepository.getSelectedPacks();
			String result = "";
			String packName;
			for (Pack pack : selectedPacks) {
				final String id = pack.getId();
				if (id == null || "vanilla".equals(id) || id.startsWith("mod_resources"))
					continue;
				packName = pack.getTitle().getString();
				if (!result.isEmpty())
					result += ";";
				result += packName;
			}
			return result;
		}
	}

	public static class Server extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			final int j = str.lastIndexOf(":");
			if (i < 0)
				return "localhost";
			final String name = (i == 0) ? str.substring(i + 1, j) : str.substring(0, i);
			final String port = str.substring(j + 1);
			return name + (port.equals("25565") ? "" : ":" + port);
		}
	}

	public static class ServerName extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			if (i < 0)
				return "localhost";
			if (i == 0)
				return str.substring(i + 1, str.lastIndexOf(":"));
			return str.substring(0, i);
		}
	}

	public static class ServerIP extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			if (i < 0)
				return "127.0.0.1";
			return str.substring(i + 1, str.lastIndexOf(":"));
		}
	}

	public static class ServerPort extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			if (i < 0)
				return "-1";
			return str.substring(str.lastIndexOf(":") + 1);
		}
	}

	public static class Ping extends TagMisc {
		@Override
		public String getValue() {
			int ping = -1;
			try {
				 final PlayerInfo playerInfo = minecraft.getConnection().getPlayerInfo(player.getUUID());
				 ping = playerInfo.getLatency();
			} catch (final Exception e) {
				ping = -1;
			}
			return String.valueOf(ping);
		}
	}

	public static class PingIcon extends TagMisc {
		@Override
		public String getValue() {
			int ping = -1;
			try {
				final PlayerInfo playerInfo = minecraft.getConnection().getPlayerInfo(player.getUUID());
				ping = playerInfo.getLatency();
				int pingIndex = 4;
				if (ping < 0)
					pingIndex = 5;
				else if (ping < 150)
					pingIndex = 0;
				else if (ping < 300)
					pingIndex = 1;
				else if (ping < 600)
					pingIndex = 2;
				else if (ping < 1000)
					pingIndex = 3;
				
				final InfoIcon icon = new InfoIcon("textures/gui/icons.png");
				icon.setDisplayDimensions(0, 0, 10, 8);
				icon.setTextureData(0, 176 + pingIndex * 8, 10, 8, 256, 256);
				info.add(icon);
				return getIconTag(icon);
			} catch (final Exception e) {
			}
			return "-1";
		}
	}

	public static class MinecraftVersion extends TagMisc {
		@Override
		public String getValue() {
			return SharedConstants.getCurrentVersion().getName();
		}
	}

	/** Fabric does not have a Forge version: the loader version is reported instead. */
	public static class FabricVersion extends TagMisc {
		@Override
		public String getValue() {
			return getLoaderVersion();
		}
	}

	public static class MCPVersion extends TagMisc {
		@Override
		public String getValue() {
			return SharedConstants.getCurrentVersion().getName();
		}
	}

	public static class ModsTotal extends TagMisc {
		@Override
		public String getValue() {
			return String.format("%d", getModCount());
		}
	}

	public static class ModsActive extends TagMisc {
		@Override
		public String getValue() {
			return String.format("%d", getModCount());
		}
	}

	public static class JavaVersion extends TagMisc {
		@Override
		public String getValue() {
			String javaVersion = String.format("%s %sbit", System.getProperty("java.version"),
					minecraft.is64Bit() ? "64" : "32");
			return javaVersion;
		}
	}

	private static String getLoaderVersion() {
		try {
			return FabricLoader.getInstance().getModContainer("fabricloader")
					.map(container -> container.getMetadata().getVersion().getFriendlyString()).orElse("unknown");
		} catch (final Exception e) {
			return "unknown";
		}
	}

	private static int getModCount() {
		try {
			return FabricLoader.getInstance().getAllMods().size();
		} catch (final Exception e) {
			return 0;
		}
	}

	public static void register() {
		TagRegistry.INSTANCE.register(new EntitiesRendered().setName("entitiesrendered"));
		TagRegistry.INSTANCE.register(new EntitiesTotal().setName("entitiestotal"));
		TagRegistry.INSTANCE.register(new FabricVersion().setName("fabricversion"));
		TagRegistry.INSTANCE.register(new FPS().setName("fps"));
		TagRegistry.INSTANCE.register(new JavaVersion().setName("javaversion"));
		TagRegistry.INSTANCE.register(new MCPVersion().setName("mcpversion"));
		TagRegistry.INSTANCE.register(new MemoryFree().setName("memfree"));
		TagRegistry.INSTANCE.register(new MemoryMaximum().setName("memmax"));
		TagRegistry.INSTANCE.register(new MemoryTotal().setName("memtotal"));
		TagRegistry.INSTANCE.register(new MemoryUsed().setName("memused"));
		TagRegistry.INSTANCE.register(new MinecraftVersion().setName("mcversion"));
		TagRegistry.INSTANCE.register(new ModsActive().setName("modsactive"));
		TagRegistry.INSTANCE.register(new ModsTotal().setName("modstotal"));
		TagRegistry.INSTANCE.register(new PingIcon().setName("pingicon"));
		TagRegistry.INSTANCE.register(new Ping().setName("ping"));
		TagRegistry.INSTANCE.register(new ServerIP().setName("serverip"));
		TagRegistry.INSTANCE.register(new ServerName().setName("servername"));
		TagRegistry.INSTANCE.register(new ServerPort().setName("serverport"));
		TagRegistry.INSTANCE.register(new Server().setName("server"));
		TagRegistry.INSTANCE.register(new ResourcePack().setName("resourcepack"));
	}
}
