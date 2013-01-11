package com.idynin.MinecraftUniversalTranslator;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.idynin.TranslateAPI.Translation;

// See http://jd.bukkit.org/apidocs/ for a full event list.
// http://wiki.bukkit.org/Event_API_Reference
public class MinecraftUniversalTranslatorEventListener implements Listener {

	private MinecraftUniversalTranslator plugin;

	public MinecraftUniversalTranslatorEventListener(
			MinecraftUniversalTranslator plugin) {

		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onLoginEvent(final PlayerJoinEvent event) {
		plugin.getServer().broadcast(event.getPlayer().getAddress().toString(),
				Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
		if (event.getPlayer().isOp()) {
			event.getPlayer().setLevel(1337);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChatEvent(final AsyncPlayerChatEvent event) {
		Logger.getLogger("Minecraft").info(
				"Caught Message: " + event.getMessage());
		final String message = new String(event.getMessage());
		final Player player = event.getPlayer();

		new BukkitRunnable() {

			@Override
			public void run() {
				Translation translation = plugin.getTranslator().translate(
						message);

				if (translation.getFromLanguage().equals(
						translation.getToLanguage())) {
					return;
				}
				for (Player r : event.getRecipients()) {
					r.sendMessage(player.getDisplayName() + ": "
							+ translation.getTranslatedText());

				}
			}
		}.runTaskAsynchronously(plugin);

	}
}
