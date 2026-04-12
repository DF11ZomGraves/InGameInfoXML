package com.github.lunatrius.ingameinfo.network;

import com.github.lunatrius.ingameinfo.util.MathUtils;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RequestMSPTPacket implements IMessage {
	public double mspt;

	@Override
	public void fromBytes(ByteBuf buf) {

	}

	@Override
	public void toBytes(ByteBuf buf) {

	}

	public static class RequestMSPTPacketHandler implements IMessageHandler<RequestMSPTPacket, ResponseMSPTPacket> {
		public RequestMSPTPacketHandler() {
			
		}

		@Override
		public ResponseMSPTPacket onMessage(RequestMSPTPacket message, MessageContext ctx) {
			try {
				final EntityPlayerMP player = ctx.getServerHandler().player;
				double mspt = -1;
				long[] times = player.mcServer.worldTickTimes.get(player.dimension);
				if (times != null)
					mspt = MathUtils.mean(times) * 1.0E-6D;
				ResponseMSPTPacket resp = new ResponseMSPTPacket(mspt);
				PacketHandler.INSTANCE.sendTo(resp, player);
			} catch (Exception e) {
				e.printStackTrace();
			}			
			return null;
		}

	}
}
