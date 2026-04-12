package com.github.lunatrius.ingameinfo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestSeedPacket implements IMessage {
	public long seed;
	
	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class RequestSeedPacketHandler implements IMessageHandler<RequestSeedPacket, ResponseSeedPacket> {
		public RequestSeedPacketHandler() {
			
		}
		
		@Override
		public ResponseSeedPacket onMessage(RequestSeedPacket message, MessageContext ctx) {
			final EntityPlayerMP player = ctx.getServerHandler().player;
			try {
				long seed = player.world.getSeed();
				ResponseSeedPacket resp = new ResponseSeedPacket(seed);
				PacketHandler.INSTANCE.sendTo(resp, player);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

}
