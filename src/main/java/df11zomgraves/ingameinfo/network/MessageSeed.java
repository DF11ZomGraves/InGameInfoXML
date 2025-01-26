package df11zomgraves.ingameinfo.network;

import java.util.function.Supplier;

import df11zomgraves.ingameinfo.tag.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class MessageSeed {
	public long seed;

	public MessageSeed() {
		this.seed = 0;
	}

	public MessageSeed(final long seed) {
		this.seed = seed;
	}

	public void encode(final FriendlyByteBuf buf) {
		buf.writeVarLong(this.seed);
	}

	public static MessageSeed decode(final FriendlyByteBuf buf) {
		return new MessageSeed(buf.readVarLong());
	}

	public void handleMessage(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Tag.setSeed(seed);
		});
		context.get().setPacketHandled(true);
	}
}
