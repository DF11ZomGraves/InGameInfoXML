package df11zomgraves.ingameinfo.network;

import java.util.function.Predicate;

import df11zomgraves.ingameinfo.reference.Names;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
	private static final String PROTOCOL_VERSION = "1";
	private static final Predicate<String> ABSENT_OR_EQUAL = NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals);
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(Names.MODID, "igi_network"), () -> PROTOCOL_VERSION, ABSENT_OR_EQUAL, ABSENT_OR_EQUAL);

	public PacketHandler() {

	}

	public static void init() {
		int id = 0;
		INSTANCE.registerMessage(id++, RequestSeedPacket.class, RequestSeedPacket::encode,
				RequestSeedPacket::decode, RequestSeedPacket::handleMessage);
		INSTANCE.registerMessage(id++, MessageSeed.class, MessageSeed::encode, MessageSeed::decode,
				MessageSeed::handleMessage);
	}
}
