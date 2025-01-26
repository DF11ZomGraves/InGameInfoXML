package com.github.lunatrius.ingameinfo.handler;

import com.github.lunatrius.ingameinfo.network.PacketHandler;
import com.github.lunatrius.ingameinfo.network.message.MessageSeed;
import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.Tag;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PlayerHandler {
	private final Minecraft minecraft = Minecraft.getMinecraft();

	@SubscribeEvent
	public void onPlayerLogin(final PlayerEvent.PlayerLoggedInEvent event) {
		Tag.setSeed(ConfigurationHandler.serverSeed);
		if (minecraft.isSingleplayer()) {
			EntityPlayerMP player = (EntityPlayerMP) event.player;
			long seed;
			seed = player.world.getSeed();
			Reference.logger.info("Seed: " + seed);
			Tag.setSeed(seed);
		} else
			try {
				PacketHandler.INSTANCE.sendTo(new MessageSeed(event.player.world.getSeed()),
						(EntityPlayerMP) event.player);
			} catch (final Exception ex) {
				Reference.logger.error("Failed to send the seed!", ex);
			}
	}

	@SubscribeEvent
	public void onPlayerLoggout(final PlayerEvent.PlayerLoggedOutEvent event) {
		Tag.setSeed(ConfigurationHandler.serverSeed);
	}
}
