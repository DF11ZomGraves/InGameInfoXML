package df11zomgraves.ingameinfo;

import df11zomgraves.ingameinfo.network.PacketHandler;
import net.fabricmc.api.ModInitializer;

/**
 * Common (server side) entrypoint of InGame Info XML.
 *
 * <p>Only the seed / MSPT request handlers are registered here, so that a
 * dedicated server running this mod can answer the client side overlay.
 * The mod is fully usable on a server that does not have it installed.
 */
public class InGameInfoXMLServer implements ModInitializer {
	@Override
	public void onInitialize() {
		PacketHandler.initServer();
	}
}
