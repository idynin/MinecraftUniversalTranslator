package main.java.com.idynin.MCUniversalTranslator;

import java.util.HashSet;

import main.java.com.idynin.googletranslateapi.Language;
import main.java.com.idynin.googletranslateapi.Translator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class MCUniversalTranslatorPlugin extends JavaPlugin {

	protected Translator translator;

	private HashSet<Player> translateUsers = new HashSet<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (command.getName().equalsIgnoreCase("tran")) {
			tran(sender, args);
			return true;
		}
		return false;
	}

	public boolean playerEnabled(Player p) {
		return translateUsers.contains(p);
	}

	public void tran(CommandSender sender, String[] args) {
		Player p = (Player) sender;
		if (args.length < 1) {
			p.sendMessage("/tran <command>");
		} else if (args[0].equalsIgnoreCase("add")) {
			translateUsers.add(getServer().getPlayer(args[1]));
			p.sendMessage("added " + getServer().getPlayer(args[1]).getName());
		} else if (args[0].equalsIgnoreCase("remove")) {
			translateUsers.remove(getServer().getPlayer(args[1]));
			p.sendMessage("removed " + getServer().getPlayer(args[1]).getName());
		} else if (args[0].equalsIgnoreCase("clearcache")) {
			translator.clearCache();
			p.sendMessage("cleared cache");
		} else if (args[0].equalsIgnoreCase("storecache")) {
			translator.storeCache();
			p.sendMessage("saved cache");
		} else if (args[0].equalsIgnoreCase("list")) {
			p.sendMessage(translateUsers.toString());
		} else if (args[0].equalsIgnoreCase("tl")) {
			Language lang = Language.getBestLanguage(args[1].toLowerCase());
			translator.setTargetLanguage(lang);
			p.sendMessage("target language set to " + lang);
		}
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		// super.onEnable();
		getLogger().info("TestPlugin: onEnable()");
		PluginManager pm = getServer().getPluginManager();

		this.saveDefaultConfig();

		translator = new Translator(getDataFolder());
		pm.registerEvents(new LoginListener(this), this);
		pm.registerEvents(new TranslatorListener(this), this);

		int fiveminuteticks = 20 * 60 * 5;
		getServer().getScheduler().runTaskTimerAsynchronously(this,
				new BukkitRunnable() {

					@Override
					public void run() {
						getServer().broadcastMessage("STORING CACHE");
						translator.storeCache();
						

					}
				}, fiveminuteticks, fiveminuteticks);

	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
		getLogger().info("TestPlugin: onDisable()");
		translator.destroy();
	}
}