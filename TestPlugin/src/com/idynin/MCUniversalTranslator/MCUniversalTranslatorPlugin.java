package com.idynin.MCUniversalTranslator;

import java.util.HashSet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.idynin.googletranslateapi.Translator;

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
	
	public boolean playerEnabled(Player p){
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
		} else if (args[0].equalsIgnoreCase("list")){
			p.sendMessage(translateUsers.toString());
		}else if (args[0].equalsIgnoreCase("tl")){
			translator.setTargetLanguage(args[1].toLowerCase());
			p.sendMessage("target language set to " + args[1].toLowerCase());
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

	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
		getLogger().info("TestPlugin: onDisable()");
		translator.destroy();
	}
}
