package df11zomgraves.ingameinfo.network;

import df11zomgraves.ingameinfo.reference.Names;
import df11zomgraves.ingameinfo.util.MathUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestMSPTPacket(double mspt) implements CustomPacketPayload  {
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Names.MODID, "request_mspt");
	public static final Type<RequestMSPTPacket> TYPE = new Type<>(ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, RequestMSPTPacket> STREAM_CODEC = StreamCodec
			.composite(ByteBufCodecs.DOUBLE, RequestMSPTPacket::mspt, RequestMSPTPacket::new);
	
	public static void handleMessage(RequestMSPTPacket message, IPayloadContext context) {
		context.enqueueWork(() -> {
			final ServerPlayer player = (ServerPlayer) context.player();
			if (player != null) {
				MinecraftServer server = player.getServer();
				if (server == null)
					return;
				long[] times = server.getTickTime(player.level().dimension());
				if (times != null) {
					double mspt = MathUtils.mean(times) * 1.0E-6D;
					PacketDistributor.sendToPlayer(player, new ResponseMSPTPacket(mspt));
				}
			}
		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
