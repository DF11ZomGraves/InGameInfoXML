package df11zomgraves.ingameinfo.network;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record RequestSeedPacket(long seed) implements CustomPacketPayload {
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Names.MODID, "request_seed");
	public static final Type<RequestSeedPacket> TYPE = new Type<>(ID);

	public static final StreamCodec<RegistryFriendlyByteBuf, RequestSeedPacket> STREAM_CODEC = StreamCodec
			.composite(ByteBufCodecs.VAR_LONG, RequestSeedPacket::seed, RequestSeedPacket::new);

	public static void handleMessage(RequestSeedPacket message, IPayloadContext context) {
			context.enqueueWork(() -> {
				final ServerPlayer player = (ServerPlayer) context.player();
				if (player != null) {
					long seed = player.serverLevel().getSeed();
					PacketDistributor.sendToPlayer(player, new ResponseSeedPacket(seed));
				}
			});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
