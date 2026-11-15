package df11zomgraves.ingameinfo.network;

import df11zomgraves.ingameinfo.reference.Names;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

/**
 * Common networking of InGame Info XML.
 *
 * <p>The Forge {@code SimpleChannel} is replaced by Fabric's networking API.
 * Only the server side receivers live in this class so that it can safely be
 * loaded on a dedicated server.
 */
public class PacketHandler {
	public static final ResourceLocation CHANNEL_REQUEST_SEED = new ResourceLocation(Names.MODID, "request_seed");
	public static final ResourceLocation CHANNEL_RESPONSE_SEED = new ResourceLocation(Names.MODID, "response_seed");
	public static final ResourceLocation CHANNEL_REQUEST_MSPT = new ResourceLocation(Names.MODID, "request_mspt");
	public static final ResourceLocation CHANNEL_RESPONSE_MSPT = new ResourceLocation(Names.MODID, "response_mspt");

	private PacketHandler() {
	}

	public static void initServer() {
		ServerPlayNetworking.registerGlobalReceiver(CHANNEL_REQUEST_SEED, PacketHandler::onRequestSeed);
		ServerPlayNetworking.registerGlobalReceiver(CHANNEL_REQUEST_MSPT, PacketHandler::onRequestMSPT);
	}

	private static void onRequestSeed(final MinecraftServer server, final ServerPlayer player,
			final ServerGamePacketListenerImpl handler, final FriendlyByteBuf buf, final PacketSender responseSender) {
		final long seed = player.serverLevel().getSeed();
		server.execute(() -> {
			final FriendlyByteBuf out = new FriendlyByteBuf(Unpooled.buffer());
			out.writeVarLong(seed);
			ServerPlayNetworking.send(player, CHANNEL_RESPONSE_SEED, out);
		});
	}

	private static void onRequestMSPT(final MinecraftServer server, final ServerPlayer player,
			final ServerGamePacketListenerImpl handler, final FriendlyByteBuf buf, final PacketSender responseSender) {
		final double mspt = getTickTime(server);
		server.execute(() -> {
			final FriendlyByteBuf out = new FriendlyByteBuf(Unpooled.buffer());
			out.writeDouble(mspt);
			ServerPlayNetworking.send(player, CHANNEL_RESPONSE_MSPT, out);
		});
	}

	/** Average tick time (in milliseconds) of the running server. */
	private static double getTickTime(final MinecraftServer server) {
		try {
			final float average = server.getAverageTickTime();
			return average <= 0 ? -1 : average;
		} catch (final Exception e) {
			return -1;
		}
	}
}