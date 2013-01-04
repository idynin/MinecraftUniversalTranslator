package com.idynin.MinecraftUniversalTranslator;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.idynin.GoogleTranslateAPI.Translator;

public class MinecraftUniversalTranslator extends JavaPlugin {

	private MinecraftUniversalTranslatorCommandExecutor commandExecutor;
	private MinecraftUniversalTranslatorEventListener eventListener;
	private Translator translator;

	public Translator getTranslator() {
		return translator;
	}

	@Override
	public void onEnable() {
		getLogger().info("TestPlugin: onEnable()");
		PluginManager pm = this.getServer().getPluginManager();

		translator = new Translator(getDataFolder());
		commandExecutor = new MinecraftUniversalTranslatorCommandExecutor(this);
		eventListener = new MinecraftUniversalTranslatorEventListener(this);

		pm.registerEvents(eventListener, this);

		for (String command : commandExecutor.getCommandList()) {
			getCommand(command).setExecutor(commandExecutor);
		}

		this.saveDefaultConfig();

		cacheStoreTask.runTaskTimerAsynchronously(this, TickTime.FIVEMINUTES,
				TickTime.FIVEMINUTES);

	}

	private BukkitRunnable cacheStoreTask = new BukkitRunnable() {

		@Override
		public void run() {
			getServer().broadcastMessage("STORING CACHE");
			translator.storeCache();

		}
	};

	@Override
	public void onDisable() {
		getLogger().info("TestPlugin: onDisable()");

		cacheStoreTask.cancel();
		translator.destroy();
	}
}
