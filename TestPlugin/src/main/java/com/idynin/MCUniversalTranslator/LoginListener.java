package main.java.com.idynin.MCUniversalTranslator;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LoginListener implements Listener {
	private JavaPlugin plugin;

	public LoginListener(JavaPlugin plugin) {
		this.plugin = plugin;
		this.plugin.getLogger().info("LoginListener Created");
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerLogin(PlayerJoinEvent event) {
		Logger.getLogger("Minecraft").info(
				"Detected Player Login: " + event.getPlayer().getDisplayName());
		event.getPlayer().sendMessage("TestPlugin sees you log in...");

	}
}
