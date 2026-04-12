package com.github.lunatrius.ingameinfo.network;

import com.github.lunatrius.ingameinfo.reference.Reference;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MODID);

	public static void init() {
		int i = 0;
		INSTANCE.registerMessage(ResponseSeedPacket.ResponseSeedPacketHandler.class, ResponseSeedPacket.class, i++,
				Side.CLIENT);
		INSTANCE.registerMessage(RequestSeedPacket.RequestSeedPacketHandler.class, RequestSeedPacket.class, i++,
				Side.SERVER);
		INSTANCE.registerMessage(ResponseMSPTPacket.ResponseMSPTPacketHandler.class, ResponseMSPTPacket.class, i++,
				Side.CLIENT);
		INSTANCE.registerMessage(RequestMSPTPacket.RequestMSPTPacketHandler.class, RequestMSPTPacket.class, i++,
				Side.SERVER);
	}
}
