package com.idynin.MinecraftUniversalTranslator;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.idynin.TranslateAPI.Translator;
import com.idynin.TranslateAPI.adapters.GoogleTranslatorAdapter;

public class MinecraftUniversalTranslator extends JavaPlugin {

	private boolean debug = false;

	private MinecraftUniversalTranslatorCommandExecutor commandExecutor;
	private MinecraftUniversalTranslatorEventListener eventListener;
	private Translator translator;

	protected FileConfiguration config;

	private File pluginFile;

	private File pluginUpdateFile;

	private MinecraftUniversalTranslator plugin;

	private BukkitRunnable pluginUpdateDetector = new BukkitRunnable() {

		@Override
		public void run() {
			if (pluginUpdateFile.exists()) {
				this.cancel();
				getLogger().info("Found update, updating...");

				new BukkitRunnable() {

					@Override
					public void run() {
						Bukkit.reload();

					}
				}.runTask(plugin);
			}
		}
	};

	private BukkitRunnable cacheStoreTask = new BukkitRunnable() {

		@Override
		public void run() {
			getServer().broadcast(
					"" + ChatColor.RED + ChatColor.ITALIC
							+ "        NOT STORING CACHE",
					Server.BROADCAST_CHANNEL_ADMINISTRATIVE);
			//translator.storeCache();

		}
	};

	public Translator getTranslator() {
		return translator;
	}

	@Override
	public void onDisable() {
		pluginUpdateDetector.cancel();
		cacheStoreTask.cancel();

		//translator.destroy();
	}

	@Override
	public void onEnable() {
		plugin = this;

		PluginManager pm = this.getServer().getPluginManager();

		//translator = new Translator(getDataFolder());
		translator = new Translator(new GoogleTranslatorAdapter());
		commandExecutor = new MinecraftUniversalTranslatorCommandExecutor(this);
		eventListener = new MinecraftUniversalTranslatorEventListener(this);

		pm.registerEvents(eventListener, this);

		for (String command : commandExecutor.getCommandList()) {
			getCommand(command).setExecutor(commandExecutor);
		}

		saveDefaultConfig();

		config = this.getConfig();
		if (config.isSet("debug")) {
			this.debug = config.getBoolean("debug");
		}

		cacheStoreTask.runTaskTimerAsynchronously(this, TickTime.MINUTE,
				TickTime.FIVEMINUTES);

		if (debug) {

			pluginFile = getFile();

			pluginUpdateFile = new File(pluginFile.getParentFile()
					.getAbsoluteFile() + "/update/" + pluginFile.getName());

			pluginUpdateDetector.runTaskTimerAsynchronously(this,
					TickTime.SECOND, TickTime.SECOND * 2);

			getServer()
					.broadcast(
							""
									+ ChatColor.GREEN
									+ ChatColor.ITALIC
									+ "--     New Version of Minecraft Universal Translator Loaded!",
							Server.BROADCAST_CHANNEL_USERS);
		}

	}
}
