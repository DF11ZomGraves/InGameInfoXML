package df11zomgraves.ingameinfo.network;

import df11zomgraves.ingameinfo.InGameInfoXML;
import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ResponseMSPTPacket(double mspt) implements CustomPacketPayload {
	public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(Names.MODID, "response_mspt");
	public static final Type<ResponseMSPTPacket> TYPE = new Type<>(ID);
	public static final StreamCodec<RegistryFriendlyByteBuf, ResponseMSPTPacket> STREAM_CODEC = StreamCodec
			.composite(ByteBufCodecs.DOUBLE, ResponseMSPTPacket::mspt, ResponseMSPTPacket::new);

	public static void handleMessage(ResponseMSPTPacket message, IPayloadContext context) {
		context.enqueueWork(() -> {
			InGameInfoXML.mspt = message.mspt;
			InGameInfoXML.tps = (message.mspt == -1) ? -1 : Math.min(1000.0 / message.mspt, 20);
			InGameInfoXML.serverInstalled = true;
		});
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}
}
