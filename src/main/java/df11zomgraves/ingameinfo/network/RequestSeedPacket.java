package df11zomgraves.ingameinfo.network;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

public class RequestSeedPacket {
	
	public RequestSeedPacket() {

	}
	
	public void encode(final FriendlyByteBuf buf) {
	}

	public static RequestSeedPacket decode(final FriendlyByteBuf buf) {
		return new RequestSeedPacket();
	}

	public void handleMessage(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			final ServerPlayer player = context.get().getSender();
			if (player != null) {
				long seed = player.serverLevel().getSeed();
				PacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new MessageSeed(seed));
			}
		});
		context.get().setPacketHandled(true);
	}
}
