package df11zomgraves.ingameinfo.network;

import java.util.function.Supplier;

import df11zomgraves.ingameinfo.tag.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ResponseMSPTPacket {
	public double mspt;
	
	public ResponseMSPTPacket(final double mspt) {
		this.mspt = mspt;
	}
	
	public void encode(final FriendlyByteBuf buf) {
		buf.writeDouble(mspt);
	}
	
	public static ResponseMSPTPacket decode(final FriendlyByteBuf buf) {
		return new ResponseMSPTPacket(buf.readDouble());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			Tag.setMSPT(mspt);
		});
		context.get().setPacketHandled(true);
	}
}
