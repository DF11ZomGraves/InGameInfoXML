package df11zomgraves.ingameinfo.network;

import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.util.StringConvertUtils;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;

/**
 * Client side networking of InGame Info XML.
 *
 * <p>This class references client only API and is therefore only loaded from the
 * client entrypoint.
 */
public class ClientPacketHandler {
	public static void initClient() {
		ClientPlayNetworking.registerGlobalReceiver(PacketHandler.CHANNEL_RESPONSE_SEED,
				(client, handler, buf, responseSender) -> {
					final long seed = buf.readVarLong();
					client.execute(() -> {
						InGameInfoXML.seed = seed;
						StringConvertUtils.sendSeedToChat(seed);
					});
				});

		ClientPlayNetworking.registerGlobalReceiver(PacketHandler.CHANNEL_RESPONSE_MSPT,
				(client, handler, buf, responseSender) -> {
					final double mspt = buf.readDouble();
					client.execute(() -> {
						InGameInfoXML.mspt = mspt;
						InGameInfoXML.tps = (mspt == -1) ? -1 : Math.min(1000.0 / mspt, 20);
						InGameInfoXML.serverInstalled = true;
					});
				});
	}

	public static void sendRequestSeed() {
		ClientPlayNetworking.send(PacketHandler.CHANNEL_REQUEST_SEED, new FriendlyByteBuf(Unpooled.buffer()));
	}

	public static void sendRequestMSPT() {
		ClientPlayNetworking.send(PacketHandler.CHANNEL_REQUEST_MSPT, new FriendlyByteBuf(Unpooled.buffer()));
	}
}