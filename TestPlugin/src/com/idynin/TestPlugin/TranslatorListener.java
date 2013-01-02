package com.idynin.TestPlugin;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TranslatorListener implements Listener {
	private TestPlugin plugin;

	public TranslatorListener(TestPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getLogger().info("ChatListener Created");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onChatEvent(final AsyncPlayerChatEvent event) {
		Logger.getLogger("Minecraft").info(
				"Caught Message: " + event.getMessage());
		final String message = event.getMessage();
		final Player player = event.getPlayer();
		plugin.getServer().getScheduler()
				.scheduleAsyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						plugin.translator.autoTranslate(message);
						if(plugin.translator.getLastDetectedLanguage().equalsIgnoreCase("en")){
							return;
						}
						for (Player r : event.getRecipients()) {
							r.sendMessage(player.getDisplayName() + ": "
									+ plugin.translator.autoTranslate(message));
						}
					}
				});

	}
}
