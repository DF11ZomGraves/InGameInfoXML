package com.github.lunatrius.ingameinfo.handler;

import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.network.message.MessageSeed;
import com.github.lunatrius.ingameinfo.tag.Tag;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerHandler {
	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		try {
			PacketHandler.INSTANCE.sendTo(new MessageSeed(event.player.world.getSeed()), (EntityPlayerMP) event.player);
		} catch (final Exception ex) {
			Tag.setSeed(ConfigurationHandler.serverSeed);
		}
	}

	@SubscribeEvent
	public void onPlayerLoggout(final PlayerEvent.PlayerLoggedOutEvent event) {
		Tag.setSeed(ConfigurationHandler.serverSeed);
	}
}
