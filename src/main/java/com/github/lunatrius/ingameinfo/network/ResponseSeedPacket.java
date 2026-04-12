package com.github.lunatrius.ingameinfo.network;

import com.github.lunatrius.ingameinfo.InGameInfoXML;
import com.github.lunatrius.ingameinfo.handler.ConfigurationHandler;
import com.github.lunatrius.ingameinfo.reference.Names;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ResponseSeedPacket implements IMessage {
	public long seed;

	public ResponseSeedPacket() {
		
	}
	
	public ResponseSeedPacket(long seed) {
		this.seed = seed;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.seed = buf.readLong();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(seed);
	}

	public static class ResponseSeedPacketHandler implements IMessageHandler<ResponseSeedPacket, IMessage> {
		public ResponseSeedPacketHandler() {
			
		}
		
		@Override
		public IMessage onMessage(ResponseSeedPacket message, MessageContext ctx) {
			try {
				InGameInfoXML.seed = message.seed;
				if (ConfigurationHandler.sendSeedToChat) {
					Minecraft mc = Minecraft.getMinecraft();
					mc.ingameGUI.addChatMessage(ChatType.CHAT,
							new TextComponentTranslation(Names.SEED_CHAT, InGameInfoXML.seed));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
