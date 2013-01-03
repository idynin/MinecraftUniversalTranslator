package main.java.com.idynin.MCUniversalTranslator;

import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class TranslatorListener implements Listener {
	private MCUniversalTranslatorPlugin plugin;

	public TranslatorListener(MCUniversalTranslatorPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getLogger().info("ChatListener Created");
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
						plugin.translator.autoTranslate(message);
						if (plugin.translator.getLastDetectedLanguage()
								.equals(plugin.translator.getTargetLanguage())) {
							return;
						}
						for (Player r : event.getRecipients()) {
							if (plugin.playerEnabled(r)) {
								r.sendMessage(player.getDisplayName()
										+ ": "
										+ plugin.translator
												.autoTranslate(message));
							}
						}
					}
				});

	}
}