package com.idynin.MinecraftUniversalTranslator;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.idynin.GoogleTranslateAPI.Translator;

public class MinecraftUniversalTranslator extends JavaPlugin {

	private final MinecraftUniversalTranslatorCommandExecutor commandExecutor = new MinecraftUniversalTranslatorCommandExecutor(
			this);
	private final MinecraftUniversalTranslatorEventListener eventListener = new MinecraftUniversalTranslatorEventListener(
			this);
	private final Translator translator = new Translator(getDataFolder());

	public Translator getTranslator() {
		return translator;
	}

	// 20 ticks in 1 second.
	private final static int SECOND = 20;
	private final static int MINUTE = SECOND * 60;
	private final static int FIVEMINUTES = MINUTE * 5;
	private final static int FIFTEENMINUTES = FIVEMINUTES * 3;
	private final static int HALFHOUR = FIFTEENMINUTES * 2;
	private final static int HOUR = HALFHOUR * 2;

	@Override
	public void onEnable() {
		getLogger().info("TestPlugin: onEnable()");
		PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvents(eventListener, this);

		for (String command : commandExecutor.getCommandList()) {
			getCommand(command).setExecutor(commandExecutor);
		}

		this.saveDefaultConfig();

		cacheStoreTask.runTaskTimerAsynchronously(this, FIVEMINUTES,
				FIVEMINUTES);

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
		getServer().getLogger().info("TestPlugin: onDisable()");

		cacheStoreTask.cancel();
		translator.destroy();
	}
}
