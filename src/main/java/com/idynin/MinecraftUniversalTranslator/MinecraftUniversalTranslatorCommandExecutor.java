package com.idynin.MinecraftUniversalTranslator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.idynin.GoogleTranslateAPI.Language;

public class MinecraftUniversalTranslatorCommandExecutor implements
		CommandExecutor {

	private MinecraftUniversalTranslator plugin;

	private final String[] commandList = { "mut" };

	public String[] getCommandList() {
		return commandList;
	}

	public MinecraftUniversalTranslatorCommandExecutor(
			MinecraftUniversalTranslator plugin) {

		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {

		plugin.getLogger().info(
				"onCommand Reached in MinecraftUniversalTranslator");

		if (command.getName().equalsIgnoreCase("mut")) {
			
			if (args.length > 1 && args[0].equalsIgnoreCase("tl")) {
				
				Language lang = Language.getBestLanguage(args[1].toLowerCase());
				plugin.getTranslator().setTargetLanguage(lang);
				plugin.getServer().broadcastMessage(
						"SET TARGET LANGUAGE TO: " + lang);

			}
			return true;
		}
		return false;
	}
}
