package com.github.lunatrius.ingameinfo.network;

import com.github.lunatrius.ingameinfo.InGameInfoXML;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ResponseMSPTPacket implements IMessage {
	public double mspt;
	
	public ResponseMSPTPacket() {
		
	}
	
	public ResponseMSPTPacket(double mspt) {
		this.mspt = mspt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.mspt = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeDouble(mspt);
	}
	
	public static class ResponseMSPTPacketHandler implements IMessageHandler<ResponseMSPTPacket, IMessage> {
		public ResponseMSPTPacketHandler() {
			
		}
		
		@Override
		public IMessage onMessage(ResponseMSPTPacket message, MessageContext ctx) {
			try {
				InGameInfoXML.mspt = message.mspt;
				InGameInfoXML.tps = (message.mspt == -1) ? -1 : Math.min(1000.0 / message.mspt, 20);
				InGameInfoXML.logger.info(InGameInfoXML.mspt);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}
	
}
