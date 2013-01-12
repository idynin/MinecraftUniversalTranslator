package com.idynin.MinecraftUniversalTranslator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.idynin.TranslateAPI.Language;
import com.idynin.TranslateAPI.Translation;
import com.idynin.TranslateAPI.TranslationQuery;
import com.idynin.TranslateAPI.Translator;

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

		plugin.getPlayerManager().add(event.getPlayer().getName());
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
				PlayerManager pm = plugin.getPlayerManager();
				Translator tr = plugin.getTranslator();
				MUTuser suser = pm.get(player.getName());
				Language sourceLang = suser.getPrimaryLanguage();
				EnumSet<Language> targets = pm.getTargetLanguages();

				List<TranslationQuery> queryList = tr.queryConstructor(message,
						sourceLang, targets);
				List<Translation> results = tr.translate(queryList);

				HashMap<Language, Translation> langtranmap = new HashMap<Language, Translation>();

				for (Translation t : results) {
					langtranmap.put(t.getToLanguage(), t);
				}

				Language detectedLang = results.get(0).getFromLanguage();
				
				pm.get(player.getName()).addDetectedLanguage(detectedLang);

				MUTuser ruser;
				for (Player recipient : event.getRecipients()) {
					ruser = pm.get(recipient.getName());
					if (ruser.getPreferedLanguage().equals(detectedLang)) {
						// continue;
					}
					recipient.sendMessage(player.getDisplayName() + ": "
							+ langtranmap.get(ruser.getPreferedLanguage()));
				}
			}
		}.runTaskAsynchronously(plugin);

	}
}
