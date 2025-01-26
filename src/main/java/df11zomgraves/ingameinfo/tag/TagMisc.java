package df11zomgraves.ingameinfo.tag;

import df11zomgraves.ingameinfo.gui.overlay.InfoIcon;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraftforge.fml.ModList;

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

	public static class Server extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			final int j = str.indexOf(":");
			if (i < 0) {
				return "localhost";
			}

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
			if (i < 0) {
				return "localhost";
			} else if (i == 0) {
				return str.substring(i + 1, str.indexOf(":"));
			}
			return str.substring(0, i);
		}
	}

	public static class ServerIP extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			if (i < 0) {
				return "127.0.0.1";
			}
			return str.substring(i + 1, str.indexOf(":"));
		}
	}

	public static class ServerPort extends TagMisc {
		@Override
		public String getValue() {
			final String str = player.connection.getConnection().getRemoteAddress().toString();
			final int i = str.indexOf("/");
			if (i < 0) {
				return "-1";
			}
			return str.substring(str.indexOf(":") + 1);
		}
	}

	public static class Ping extends TagMisc {
		@Override
		public String getValue() {
			int ping = -1;
			try {
				 final PlayerInfo playerInfo =  minecraft.getConnection().getPlayerInfo(player.getUUID());
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
				if (ping < 0) {
					pingIndex = 5;
				} else if (ping < 150) {
					pingIndex = 0;
				} else if (ping < 300) {
					pingIndex = 1;
				} else if (ping < 600) {
					pingIndex = 2;
				} else if (ping < 1000) {
					pingIndex = 3;
				}
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
			return net.minecraftforge.versions.mcp.MCPVersion.getMCPVersion();
		}
	}

	public static class ForgeVersion extends TagMisc {
		@Override
		public String getValue() {
			return net.minecraftforge.versions.forge.ForgeVersion.getVersion();
		}
	}

	public static class MCPVersion extends TagMisc {
		@Override
		public String getValue() {
			return net.minecraftforge.versions.mcp.MCPVersion.getMCPVersion();
		}
	}

	public static class ModsTotal extends TagMisc {
		@Override
		public String getValue() {
			return String.format("%d", ModList.get().size());
		}
	}

	public static class ModsActive extends TagMisc {
		@Override
		public String getValue() {
			return String.format("%d", ModList.get().size());
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

	public static void register() {
		TagRegistry.INSTANCE.register(new EntitiesRendered().setName("entitiesrendered"));
		TagRegistry.INSTANCE.register(new EntitiesTotal().setName("entitiestotal"));
		TagRegistry.INSTANCE.register(new ForgeVersion().setName("forgeversion"));
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
	}
}
