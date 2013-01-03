package com.idynin.MinecraftUniversalTranslator;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

// See http://jd.bukkit.org/apidocs/ for a full event list.
// http://wiki.bukkit.org/Event_API_Reference
public class MinecraftUniversalTranslatorEventListener implements Listener {

	private MinecraftUniversalTranslator plugin;

	public MinecraftUniversalTranslatorEventListener(
			MinecraftUniversalTranslator plugin) {

		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChatEvent(final AsyncPlayerChatEvent event) {
		Logger.getLogger("Minecraft").info(
				"Caught Message: " + event.getMessage());
		final String message = new String(event.getMessage());
		final Player player = event.getPlayer();
		plugin.getServer().getScheduler()
				.runTaskAsynchronously(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.getTranslator().autoTranslate(message);
						if (plugin
								.getTranslator()
								.getLastDetectedLanguage()
								.equals(plugin.getTranslator()
										.getTargetLanguage())) {
							return;
						}
						for (Player r : event.getRecipients()) {
							r.sendMessage(player.getDisplayName()
									+ ": "
									+ plugin.getTranslator().autoTranslate(
											message));

						}
					}
				});

	}
}
