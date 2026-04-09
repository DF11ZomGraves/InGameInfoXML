package df11zomgraves.ingameinfo.network;

import java.util.function.Supplier;

import df11zomgraves.ingameinfo.util.MathUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class RequestMSPTPacket {
	public void encode(final FriendlyByteBuf buf) {
	}
	
	public static RequestMSPTPacket decode(final FriendlyByteBuf buf) {
		return new RequestMSPTPacket();
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			final ServerPlayer player = context.get().getSender();
			if (player != null) {
				MinecraftServer server = player.getServer();
				if (server == null)
					return;
				long[] times = server.getTickTime(player.level().dimension());
				if (times != null) {
					double mspt = MathUtils.mean(times) * 1.0E-6D;
					PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new ResponseMSPTPacket(mspt));
				}
			}
		});
		context.get().setPacketHandled(true);
	}
}
