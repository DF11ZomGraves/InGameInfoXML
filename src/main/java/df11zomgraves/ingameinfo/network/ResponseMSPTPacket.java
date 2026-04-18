package df11zomgraves.ingameinfo.network;

import java.util.function.Supplier;

import df11zomgraves.ingameinfo.InGameInfoXML;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class ResponseMSPTPacket {
	public double mspt;
	
	public ResponseMSPTPacket() {
		this.mspt = -1;
	}
	
	public ResponseMSPTPacket(final double mspt) {
		this.mspt = mspt;
	}
	
	public void encode(final FriendlyByteBuf buf) {
		buf.writeDouble(this.mspt);
	}
	
	public static ResponseMSPTPacket decode(final FriendlyByteBuf buf) {
		return new ResponseMSPTPacket(buf.readDouble());
	}
	
	public void handleMessage(Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			InGameInfoXML.mspt = this.mspt;
			InGameInfoXML.tps = (this.mspt == -1) ? -1 : Math.min(1000.0 / this.mspt, 20);
			InGameInfoXML.serverInstalled = true;
		});
		context.get().setPacketHandled(true);
	}
}
