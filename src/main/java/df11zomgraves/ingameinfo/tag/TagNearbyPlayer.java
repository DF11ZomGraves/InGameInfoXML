package df11zomgraves.ingameinfo.tag;

import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import df11zomgraves.ingameinfo.gui.overlay.InfoIcon;

public abstract class TagNearbyPlayer extends Tag {
	public static final int MAXIMUM_INDEX = 16;

	private static final Comparator<Player> PLAYER_DISTANCE_COMPARATOR = (Player playerA, Player playerB) -> {
		if (Tag.player == null) {
			return 0;
		}

		final double distanceA = Tag.player.distanceToSqr(playerA);
		final double distanceB = Tag.player.distanceToSqr(playerB);
		if (distanceA > distanceB) {
			return 1;
		} else if (distanceA < distanceB) {
			return -1;
		}
		return 0;
	};
	protected static Player[] nearbyPlayers = null;
	protected final int index;

	public TagNearbyPlayer(final int index) {
		this.index = index;
	}

	@Override
	public String getName() {
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
		return "nearbyplayer";
	}

	protected static void updateNearbyPlayers() {
		if (nearbyPlayers == null) {
			final List<Player> playerList = new ArrayList<Player>();
			for (final Player player : world.players()) {
				if (player != Tag.player && !player.isShiftKeyDown()) {
					playerList.add(player);
				}
			}

			Collections.sort(playerList, PLAYER_DISTANCE_COMPARATOR);
			nearbyPlayers = playerList.toArray(new Player[playerList.size()]);
		}
	}

	public static class Name extends TagNearbyPlayer {
		public Name(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updateNearbyPlayers();
			if (nearbyPlayers.length > this.index) {
				return nearbyPlayers[this.index].getDisplayName().getString();
			}
			return "";
		}
	}

	public static class Distance extends TagNearbyPlayer {
		public Distance(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updateNearbyPlayers();
			if (nearbyPlayers.length > this.index) {
				return String.format(Locale.ENGLISH, "%.2f", nearbyPlayers[this.index].distanceTo(player));
			}
			return "-1";
		}
	}

	public static class Icon extends TagNearbyPlayer {
		public Icon(final int index) {
			super(index);
		}

		@Override
		public String getValue() {
			updateNearbyPlayers();
			if (nearbyPlayers.length > this.index) {
				final ClientPacketListener connection = minecraft.getConnection();
				if (connection == null) {
					return "";
				}

				final PlayerInfo playerInfo = connection.getPlayerInfo(nearbyPlayers[this.index].getUUID());

				final InfoIcon icon = new InfoIcon(playerInfo.getSkinLocation());
				icon.setTextureData(8, 8, 8, 8, 64, 64);
				icon.setDisplayDimensions(0, 0, 8, 8);
				info.add(icon);
				return getIconTag(icon);
			}
			return "";
		}
	}

	public static void register() {
		for (int i = 0; i < MAXIMUM_INDEX; i++) {
			TagRegistry.INSTANCE.register(new Name(i).setName("nearbyplayername"));
			TagRegistry.INSTANCE.register(new Distance(i).setName("nearbyplayerdistance"));
			TagRegistry.INSTANCE.register(new Icon(i).setName("nearbyplayericon"));
		}
	}

	public static void releaseResources() {
		nearbyPlayers = null;
	}
}
