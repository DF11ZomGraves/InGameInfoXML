package df11zomgraves.ingameinfo.handler;

import df11zomgraves.ingameinfo.network.RequestMSPTPacket;
import df11zomgraves.ingameinfo.network.RequestSeedPacket;
import df11zomgraves.ingameinfo.network.ResponseMSPTPacket;
import df11zomgraves.ingameinfo.network.ResponseSeedPacket;
import df11zomgraves.ingameinfo.reference.Names;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Names.MODID)
public class NeoForgeCommonEventHandler {
	@SubscribeEvent
	public static void onRegisterPackets(RegisterPayloadHandlersEvent event) {
		final PayloadRegistrar registrar = event.registrar("1");
		registrar.playToClient(ResponseSeedPacket.TYPE, ResponseSeedPacket.STREAM_CODEC, ResponseSeedPacket::handleMessage);
		registrar.playToClient(ResponseMSPTPacket.TYPE, ResponseMSPTPacket.STREAM_CODEC, ResponseMSPTPacket::handleMessage);
		registrar.playToServer(RequestSeedPacket.TYPE, RequestSeedPacket.STREAM_CODEC, RequestSeedPacket::handleMessage);
		registrar.playToServer(RequestMSPTPacket.TYPE, RequestMSPTPacket.STREAM_CODEC, RequestMSPTPacket::handleMessage);
	}
}
